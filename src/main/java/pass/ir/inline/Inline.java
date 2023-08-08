package pass.ir.inline;

import ir.Instruction;
import ir.Instructions.*;
import ir.Module;
import ir.Use;
import ir.Value;
import ir.types.FunctionType;
import ir.types.PointerType;
import ir.values.BasicBlock;
import ir.values.Function;
import pass.ir.BaseIRPass;
import util.IList;

import java.util.*;

public class Inline implements BaseIRPass {
    Map<Value, Value> valueMap = new HashMap<>();
    Map<BasicBlock, BasicBlock> bbMap = new HashMap<>();
    Function curFunc = null;
    BasicBlock curOldBB = null;
    MemoryInst.Phi returnPhi = null;
    BasicBlock curRetBB = null;

    @Override
    public void run(Module module) {
        System.out.println("Inline");
        canInline(module); // 标记能做内联的函数

        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            if (func.isBuiltin()) continue;
            if (func.canInline) continue;
            for (IList.INode<BasicBlock, Function> bbInode : func.list) {
                BasicBlock bb = bbInode.getElement();
                System.out.println("进入基本块" + bb.getName());
                boolean needAgain = true;
                while (needAgain) {
                    needAgain = false;
                    for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {

                        Instruction inst = instInode.getElement();
//                        if (inst.isDelete) continue;
                        if (inst instanceof TerminatorInst.Call callInst) {
                            var callee = (Function) callInst.getOperandAt(0);
                            if (!callee.canInline)
                                continue; // 不能内联，不管
                            else {
                                // 处理内联
                                // 先只处理单基本块的内联吧
                                curFunc = callee;
                                if (callee.list.size() == 1) {
                                    valueMap.clear();
                                    LinkedList<Instruction> newInstList = new LinkedList<>();
                                    Value valueRet = null;
                                    var oneBlock = callee.list.getFirst().getElement();
                                    for (int i = 0; i < callee.getParamList().size(); i++) {
//                                        System.out.println("put"+callee.getParamList().get(i)+"->"+callInst.getOperandAt(i + 1));
                                        valueMap.put(callee.getParamList().get(i), callInst.getOperandAt(i + 1));
                                    }
                                    for (IList.INode<Instruction, BasicBlock> oldInstInode : oneBlock.list) {
                                        Instruction oldInst = oldInstInode.getElement();
                                        // 对应原函数内的参数和实际被传入的参数
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
                                    // TODO:多基本块函数内联
//                                    boolean isFirst = false;
//                                    if (bb.list.getFirst().getElement() == inst) {
//                                        isFirst = true;
//                                    }
                                    returnPhi = null;
                                    BasicBlock returnBB = new BasicBlock("returnBB");
                                    curRetBB = returnBB;
                                    if (!((FunctionType) callee.getType()).getRetType().isVoidType()) {
                                        // 有返回值，要有phi
                                        returnPhi = new MemoryInst.Phi(((FunctionType) callee.getType()).getRetType(), 0);
                                        returnBB.list.addFirst(returnPhi.node);
                                    }
                                    // 把这个基本块里剩下的移动到returnBB中
                                    LinkedList<IList.INode<Instruction, BasicBlock>> InstToAdd = new LinkedList<>();

                                    // 把要移动的指令放入InstToAdd
                                    var curInode = instInode;
                                    while (curInode != bb.list.getTail()) {
                                        InstToAdd.add(curInode);
                                        curInode = curInode.getNext();
                                        curInode.getPrev().removeSelf();
                                    }
                                    // 将InstToAdd中的指令移动过去
                                    for (int i = 0; i < InstToAdd.size(); i++) {
                                        returnBB.list.addLast(InstToAdd.get(i));
                                    }
                                    func.list.addAfter(bbInode, returnBB.node); // 把返回块加进来

                                    // 开始复制
                                    System.out.println("开始复制");
                                    valueMap.clear();
                                    bbMap.clear();
                                    LinkedList<BasicBlock> newBlockList = new LinkedList<>();
                                    // 参数映射
                                    for (int i = 0; i < callee.getParamList().size(); i++) {
//                                        System.out.println("put"+callee.getParamList().get(i)+"->"+callInst.getOperandAt(i + 1));
                                        valueMap.put(callee.getParamList().get(i), callInst.getOperandAt(i + 1));
                                    }
                                    // 基本块映射
//                                bbMap.put(bb, returnBB);
                                    bb.replaceAllPhiUseWith(returnBB);

                                    for (IList.INode<BasicBlock, Function> oldBBInode : callee.list) {
                                        BasicBlock oldBB = oldBBInode.getElement();
                                        if (oldBBInode == callee.list.getFirst()) {
                                            bbMap.put(oldBB, bb); // 入口块接在当前块后面
                                        } else {
                                            BasicBlock newBB = new BasicBlock("new" + oldBB.getName());
                                            bbMap.put(oldBB, newBB);
                                        }

                                    }
                                    // 全部复制
                                    for (IList.INode<BasicBlock, Function> oldBBInode : callee.list) {
                                        BasicBlock oldBB = oldBBInode.getElement();
                                        curOldBB = oldBB;
                                        if (oldBBInode == callee.list.getFirst()) {
                                            for (IList.INode<Instruction, BasicBlock> oldInstInode : oldBB.list) {
                                                Instruction oldInst = oldInstInode.getElement();
                                                // 对应原函数内的参数和实际被传入的参数
                                                Instruction newInst = cloneInstruction(oldInst);
                                                newInst.isInline = true;
                                                valueMap.put(oldInst, newInst); // 新旧指令的对应

                                                bb.list.addLast(newInst.node); // 第一个块加在当前块后面

                                            }
                                        } else {
                                            for (IList.INode<Instruction, BasicBlock> oldInstInode : oldBB.list) {
                                                Instruction oldInst = oldInstInode.getElement();
                                                // 对应原函数内的参数和实际被传入的参数
                                                Instruction newInst = cloneInstruction(oldInst);
                                                valueMap.put(oldInst, newInst); // 新旧指令的对应
                                                bbMap.get(oldBB).list.addLast(newInst.node); // 直接加进新块
                                            }
                                            newBlockList.add(bbMap.get(oldBB));
                                        }

                                    }
                                    // 新基本块列表加入当前函数
                                    for (BasicBlock curBB : newBlockList) {
                                        func.list.addBefore(returnBB.node, curBB.node);
                                    }
                                    // 检查phi
                                    for (BasicBlock curBB : newBlockList) {
                                        for (IList.INode<Instruction, BasicBlock> newInstInode : curBB.list) {
                                            Instruction newInst = newInstInode.getElement();
                                            if (newInst instanceof MemoryInst.Phi && newInst.needPhiCheck)
                                                checkClone(newInst); // 检查一遍有没有未替换成功的
                                        }
                                    }

//                                if (isFirst) {
//                                    System.out.println("是第一个");
//                                    bbInode.removeSelf();
//                                }
                                    inst.replaceAllUseWith(returnPhi);
                                    inst.removeAllOperand();
                                    instInode.removeSelf();

                                    needAgain = true;
                                    break; // 跳出这个基本块
                                }

                            }
                        }


                    }
                }

            }

        }
        deleteInlineFunction(module);
        moveAlloca(module);
    }

    private void moveAlloca(Module module) {
        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            LinkedList<IList.INode> allocaToMove = new LinkedList<>();
            for (IList.INode<BasicBlock, Function> bbInode : func.list) {
                BasicBlock bb = bbInode.getElement();
                for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {
                    Instruction inst = instInode.getElement();

                    if (inst instanceof MemoryInst.Alloca) {
                        System.out.println("移动" + inst);
                        instInode.removeSelf();
                        allocaToMove.add(instInode);

//                        func.list.getFirst().getElement().list.addFirst(instInode);
                    }
                }
            }
            for (IList.INode allocaInode : allocaToMove) {
                func.list.getFirst().getElement().list.addFirst(allocaInode);
            }

        }
    }

    private void checkClone(Instruction newInst) {
        for (int i = 0; i < newInst.numOP; i++) {
            var op = newInst.getOperandAt(i);
            if (op instanceof BasicBlock) continue;
            if (valueMap.get(op) != null) {
                newInst.needPhiCheck = false;
                newInst.setOperand(valueMap.get(op), i);
            }
        }
    }

    private void deleteInlineFunction(Module module) {
        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            if (func.canInline) { // && func.list.size() == 1
                funcInode.removeSelf();
            }
        }
    }

    private Map<Integer, Value> cloneOps(Instruction oldInst) {
        Map<Integer, Value> map = new HashMap<>();
        for (Use use : oldInst.operandList) {
            var usee = use.getValue();
            Value value;
            if (usee instanceof BasicBlock) {
                value = bbMap.get(usee);
            } else {
                value = valueMap.getOrDefault(usee, usee); // 找旧的value对应的新value
            }
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
        if (oldInst instanceof MemoryInst.Phi) {
            var oldPhi = (MemoryInst.Phi) oldInst;
            var phi = new MemoryInst.Phi(type, 0);
            for (BasicBlock entry : oldPhi.opMap.keySet()) {
                var value = oldPhi.findValue(entry);
                phi.addMapping(bbMap.get(entry), valueMap.getOrDefault(value, value));
            }
            return phi;
        }
        if (oldInst instanceof TerminatorInst.Br) {
            if (oldInst.numOP == 3) {
                return new TerminatorInst.Br(ops.get(0), (BasicBlock) ops.get(1), (BasicBlock) ops.get(2),
                        bbMap.get(curOldBB));
            } else {
                return new TerminatorInst.Br((BasicBlock) ops.get(0),
                        bbMap.get(curOldBB));
            }
        }
        if (oldInst instanceof TerminatorInst.Ret oldRetInst) {
            if (curFunc.list.size() == 1) {
                // 单基本块的情况，不用Br
                if (oldRetInst.numOP == 0) return null;
                else return new TerminatorInst.Ret(ops.get(0));
            } else {
                // 多基本块
                if (returnPhi != null) {
                    returnPhi.addMapping(bbMap.get(curOldBB), ops.get(0));
                }
                return new TerminatorInst.Br(curRetBB, bbMap.get(curOldBB));
            }

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
                            System.out.println("有递归");
                            goOn = false;
                            break;
                        }
                    }
                }
                if (!goOn) break;
            }
            if (goOn) func.canInline = true; // 说明可以内联

        }
    }
}
