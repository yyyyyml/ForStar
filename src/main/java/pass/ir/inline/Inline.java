package pass.ir.inline;

import ir.Instruction;
import ir.Instructions.*;
import ir.Module;
import ir.Use;
import ir.Value;
import ir.types.PointerType;
import ir.values.BasicBlock;
import ir.values.Function;
import pass.ir.BaseIRPass;
import util.IList;

import java.util.*;

public class Inline implements BaseIRPass {
    Map<Value, Value> valueMap = new HashMap<>();

    @Override
    public void run(Module module) {
        System.out.println("Inline");
        canInline(module); // 标记能做内联的函数

        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            if (func.isBuiltin()) continue;
            for (IList.INode<BasicBlock, Function> bbInode : func.list) {
                BasicBlock bb = bbInode.getElement();
                for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {
                    Instruction inst = instInode.getElement();
                    if (inst instanceof TerminatorInst.Call callInst) {
                        var callee = (Function) callInst.getOperandAt(0);
                        if (!callee.canInline)
                            continue; // 不能内联，不管
                        else {
                            // 处理内联
                            // 先只处理单基本块的内联吧
                            if (callee.list.size() == 1) {
                                valueMap.clear();
                                LinkedList<Instruction> newInstList = new LinkedList<>();
                                Value valueRet = null;
                                var oneBlock = callee.list.getFirst().getElement();
                                for (IList.INode<Instruction, BasicBlock> oldInstInode : oneBlock.list) {
                                    Instruction oldInst = oldInstInode.getElement();
                                    // 对应原函数内的参数和实际被传入的参数
                                    for (int i = 0; i < callee.getParamList().size(); i++) {
//                                        System.out.println("put"+callee.getParamList().get(i)+"->"+callInst.getOperandAt(i + 1));
                                        valueMap.put(callee.getParamList().get(i), callInst.getOperandAt(i + 1));
                                    }
                                    Instruction newInst = cloneInstruction(oldInst);
                                    if (newInst == null) {
                                        continue;
                                    }
                                    if (newInst instanceof TerminatorInst.Ret) {
                                        valueRet = newInst.getOperandAt(0);
                                        newInst.removeAllOperand(); // 删掉这个Use
                                    } else {
                                        valueMap.put(oldInst, newInst); // 新旧指令的对应
                                        newInstList.add(newInst);
                                    }

                                }
                                // 插入内联代码
                                IList.INode nextInode = instInode.getNext();
                                for (Instruction instToAdd : newInstList) {
                                    bb.list.addBefore(nextInode, instToAdd.node);
                                }
                                inst.replaceAllUseWith(valueRet);
                                inst.removeAllOperand();
                                instInode.removeSelf();
                            } else {
                                // TODO:多基本块内联
                            }

                        }
                    }


                }
            }

        }
        deleteInlineFunction(module);
    }

    private void deleteInlineFunction(Module module) {
        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            if (func.canInline && func.list.size() == 1) {
                funcInode.removeSelf();
            }
        }
    }

    private Map<Integer, Value> cloneOps(Instruction oldInst) {
        Map<Integer, Value> map = new HashMap<>();
        for (Use use : oldInst.operandList) {
            var usee = use.getValue();
            Value value;
            value = valueMap.getOrDefault(usee, usee); // 找旧的value对应的新value
//            System.out.println(value);
            map.put(use.getPosition(), value);
        }
        return map;
    }

    private Instruction cloneInstruction(Instruction oldInst) {
        var type = oldInst.getType();
        var tag = oldInst.getTag();
        var ops = cloneOps(oldInst);
        if (oldInst instanceof FnegInst) {
            return new FnegInst(type, tag, 1, ops.get(0));
        }
        if (oldInst instanceof BinaryInst) {
            return new BinaryInst(type, tag, ops.get(0), ops.get(1));
        }
        if (oldInst instanceof ConversionInst.Zext) {
            return new ConversionInst.Zext(ops.get(0));
        }
        if (oldInst instanceof ConversionInst.Fptosi) {
            return new ConversionInst.Fptosi(ops.get(0));
        }
        if (oldInst instanceof ConversionInst.Sitofp) {
            return new ConversionInst.Sitofp(ops.get(0));
        }
        if (oldInst instanceof ConversionInst.Ptrcast) {
            return new ConversionInst.Ptrcast(ops.get(0), (PointerType) type);
        }

        if (oldInst instanceof MemoryInst.GEP) {
            var indexNum = ops.size() - 1;
            ArrayList<Value> indices = new ArrayList<>();
            for (int i = 1; i <= indexNum; i++) {
                indices.add(ops.get(i));
            }
            return new MemoryInst.GEP(ops.get(0), indices);
        }
        if (oldInst instanceof MemoryInst.Store) {
            return new MemoryInst.Store(ops.get(0), ops.get(1));
        }
        if (oldInst instanceof MemoryInst.Load) {
            return new MemoryInst.Load(type, ops.get(0));
        }
        if (oldInst instanceof MemoryInst.Alloca) {
            return new MemoryInst.Alloca(((PointerType) type).getPointedType());
        }
        if (oldInst instanceof TerminatorInst.Ret oldRetInst) {
            if (oldRetInst.numOP == 0) return null;
            else return new TerminatorInst.Ret(ops.get(0));
        }
        if (oldInst instanceof TerminatorInst.Call) {
            int argNum = ops.size() - 1;
            ArrayList<Value> args = new ArrayList<>();
            for (int i = 1; i <= argNum; i++) {
                args.add(ops.get(i));
            }
            return new TerminatorInst.Call((Function) ops.get(0), args);
        }

        throw new RuntimeException("Unable to clone instruction of type " + oldInst.getClass());

    }

    private void canInline(Module module) {
        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            if (func.isBuiltin()) continue;
            if (Objects.equals(func.getName(), "main")) continue;

            boolean goOn = true; // 是否继续遍历
            for (IList.INode<BasicBlock, Function> bbInode : func.list) {
                BasicBlock bb = bbInode.getElement();
                for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {
                    Instruction inst = instInode.getElement();
                    if (inst instanceof TerminatorInst.Call callInst) {
                        var callee = (Function) callInst.getOperandAt(0);
                        if (callee == func) {
                            goOn = false;
                            break;
                        }
                    }
                }
                if (!goOn) break;
            }
            func.canInline = true; // 说明可以内联

        }
    }
}
