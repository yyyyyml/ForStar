package pass.ir.mem2reg;

import ir.Instruction;
import ir.Instructions.MemoryInst;
import ir.Module;
import ir.Use;
import ir.Value;
import ir.values.BasicBlock;
import ir.values.Constant;
import ir.values.Function;
import pass.ir.BaseIRPass;
import util.IList;

import java.util.*;

public class Mem2Reg implements BaseIRPass {
    boolean retNeedDo = false;
    private Set<MemoryInst.Alloca> allocaSet;

    public Mem2Reg() {
        allocaSet = new LinkedHashSet<>();
    }

    @Override
    public boolean run(Module module) {
        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            if (func.isBuiltin()) continue;
            System.out.println("对" + func + "mem2reg");

            // 获取所有可提升的Alloca
            allocaSet.clear();
            getPromotableAllocaSet(func);
//            System.out.println(allocaSet);
            if (allocaSet.isEmpty()) {
                System.out.println("没有需要提升的alloca");
                continue;
            }

            // 要清空每个块的相关属性信息，可能多次mem2reg
            initBasicBlock(func);

            // 收集每个基本块中的使用和定义信息
            collectInformation(func);

            // 向每个基本块插入空的phi指令
            insertEmptyPhi(func);

            // 处理基本块内的指令，替换load指令的使用，并记录store指令的结果
            processInstructions(func);

            // 完成phi指令的填充
            fillEmptyPhi(func);
//            System.out.println("已完成phi指令的填充");

            // 移除基本块中多余的load和store指令，然后移除入口块多余的alloca指令
            removeInst(func);

            // 消除单分支的phi
            // TODO:貌似不可行
            deriveSinglePhi(func);
        }
        return true;
    }

    private void initBasicBlock(Function func) {
        for (IList.INode<BasicBlock, Function> bbInode : func.list) {
            var bb = bbInode.getElement();
            bb.npdVar.clear();
            bb.defVar.clear();
            bb.phiMap.clear();
            bb.nowDefMap.clear();
        }
    }


    private void getPromotableAllocaSet(Function func) {
        var firstBBInode = func.list.getFirst();
        BasicBlock firstBB = firstBBInode.getElement();
        // alloca全加入
        for (var instInode : firstBB.list) {
            var inst = instInode.getElement();
            if (inst instanceof MemoryInst.Alloca) {
                allocaSet.add((MemoryInst.Alloca) inst);
            } else {
                break;
            }
        }
        // 去掉不可提升的，其他指令需要用地址的
        for (IList.INode<BasicBlock, Function> bbInode : func.list) {
            var bb = bbInode.getElement();
            for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {
                var inst = instInode.getElement();

                if (!(inst instanceof MemoryInst.Load) && !(inst instanceof MemoryInst.Store)) {
                    for (Use opUse : inst.operandList) {
                        if (opUse.getValue() instanceof MemoryInst.Alloca) {
                            allocaSet.remove((MemoryInst.Alloca) opUse.getValue());
                        }
                    }

                }
            }
        }
    }

    private void collectInformation(Function func) {
        Map<MemoryInst.Alloca, Queue<BasicBlock>> blocksToSpread = new LinkedHashMap<>();
        allocaSet.forEach(variable -> blocksToSpread.put(variable, new LinkedList<>()));

        // 遍历每个基本块，收集每个基本块中可提升变量的信息
        for (IList.INode<BasicBlock, Function> bbInode : func.list) {
            var bb = bbInode.getElement();
            for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {
                var inst = instInode.getElement();
                if (inst instanceof MemoryInst.Store) {
                    // 处理store指令
                    var address = inst.getOperandAt(1);
                    if (address instanceof MemoryInst.Alloca && allocaSet.contains((MemoryInst.Alloca) address)) {
                        // 将定义的变量加入基本块的definedVar集合中
                        bb.defVar.add((MemoryInst.Alloca) address);
                        System.out.println("要加入defVar");
                    }
                }
                if (inst instanceof MemoryInst.Load) {
                    // 处理load指令
                    var address = inst.getOperandAt(0);
                    if (address instanceof MemoryInst.Alloca && allocaSet.contains((MemoryInst.Alloca) address)
                            && !bb.defVar.contains((MemoryInst.Alloca) address)) {
                        // 将需要在前一个基本块中定义的变量加入基本块的npdVar集合中，并将该基本块加入传播集合中
                        bb.npdVar.add((MemoryInst.Alloca) address);
                        System.out.println("要加入npdVar");
                        blocksToSpread.get((MemoryInst.Alloca) address).add(bb);
                    }
                }
            }
        }

        // 传播需要在前一个基本块中定义的变量
        for (Map.Entry<MemoryInst.Alloca, Queue<BasicBlock>> entry : blocksToSpread.entrySet()) {
            MemoryInst.Alloca variable = entry.getKey();
            Queue<BasicBlock> basicBlocks = entry.getValue();

            while (!basicBlocks.isEmpty()) {
                BasicBlock basicBlock = basicBlocks.remove();
                for (BasicBlock previousBasicBlock : basicBlock.preList) {
                    if (previousBasicBlock.defVar.contains(variable) || previousBasicBlock.npdVar.contains(variable)) {
                        continue;
                    }
                    // 将需要在前一个基本块中定义的变量加入前一个基本块的 npdVar 集合中，并继续传播
                    System.out.println("传播要加入npdVar");
                    previousBasicBlock.npdVar.add(variable);
                    // 将前一个基本块添加到 newBasicBlocks 中，待下一轮继续传播
                    basicBlocks.add(previousBasicBlock);
                }
            }

        }
    }


    private void insertEmptyPhi(Function func) {
        for (IList.INode<BasicBlock, Function> bbInode : func.list) {
            var bb = bbInode.getElement();
            // 判断是否为入口块，如果是，则不应该包含phi指令
            if (bb.preList.size() == 0) {
                // 入口块不应该包含phi指令
                // 如果npdVar的大小不为0，可能引发未定义行为
                // 尽管代码可能是良定义的，但如果包含复杂的分支，编译器可能无法检测该问题
                // 在这种情况下，我们假设初始值为0
                for (MemoryInst.Alloca npdVar : bb.npdVar) {
                    Constant constant;
                    if (npdVar.allocatedType.isIntegerType()) {
                        constant = Constant.ConstantInt.getConstantInt(0);
                    } else if (npdVar.allocatedType.isFloatType()) {
                        constant = Constant.ConstantFloat.getConstantFloat(0);
                    } else {
                        throw new RuntimeException("基本块关系有问题"); // 不会有这种情况
                    }
                    bb.nowDefMap.put(npdVar, constant);
                }
            } else {
                for (MemoryInst.Alloca npdVar : bb.npdVar) {
                    System.out.println("需要插入空phi");
                    // 在基本块的开头插入一个新的phi指令，类型为npdVar的类型
                    var phiInst = new MemoryInst.Phi(npdVar.allocatedType, 0);
//                    System.out.println("phi:" + phiInst);
                    bb.list.addFirst(phiInst.node);
                    // 将phi指令加入phiMap
                    bb.phiMap.put(npdVar, phiInst);
                    // npdVar的最新定义现在为phi指令
                    bb.nowDefMap.put(npdVar, phiInst);
                }
            }
        }
    }


    private void processInstructions(Function func) {
        for (IList.INode<BasicBlock, Function> bbInode : func.list) {
            var bb = bbInode.getElement();
            for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {
                var inst = instInode.getElement();

                // 将所有load指令的Use替换为寄存器，意思是其他要用这个load值的inst的操作数都会被换成register
                if (inst instanceof MemoryInst.Load) {
                    var address = inst.getOperandAt(0);
                    if (address instanceof MemoryInst.Alloca && bb.nowDefMap.containsKey(address)) {
                        var register = bb.nowDefMap.get(address);
//                        inst.useList.forEach(use -> use.setValue(register));
                        inst.replaceAllUseWith(register); // 我感觉这样应该可以
//                        System.out.println("load变为nowDefMap中的变量:" + inst + "->" + register);
                    }
                }
                // 记录store指令的结果
                if (inst instanceof MemoryInst.Store) {
                    var value = inst.getOperandAt(0);
                    var address = inst.getOperandAt(1);
                    if (address instanceof MemoryInst.Alloca) {
//                        System.out.println("store存入nowDefMap:" + "map对(" + (MemoryInst.Alloca) address + "," + value + ")");
                        bb.nowDefMap.put((MemoryInst.Alloca) address, value);
                    }
                }
            }
        }
    }


    private void fillEmptyPhi(Function func) {
        for (IList.INode<BasicBlock, Function> bbInode : func.list) {
            var bb = bbInode.getElement();
            for (Map.Entry<MemoryInst.Alloca, MemoryInst.Phi> entry : bb.phiMap.entrySet()) {
                var alloca = entry.getKey();
                var phiInst = entry.getValue();
                for (BasicBlock previousBasicBlock : bb.preList) {
                    System.out.println("fillEmptyPhi中，要把map对(%" + previousBasicBlock.getName() + "," + previousBasicBlock.nowDefMap.get(alloca) + ")加入phi");
                    phiInst.addMapping(previousBasicBlock, previousBasicBlock.nowDefMap.get(alloca));
                }
            }
        }
    }


    private void removeInst(Function func) {
        // 删掉load,store
        for (IList.INode<BasicBlock, Function> bbInode : func.list) {
            var bb = bbInode.getElement();
            for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {
                var inst = instInode.getElement();
                if (inst instanceof MemoryInst.Load) {
                    var address = inst.getOperandAt(0);
                    if (address instanceof MemoryInst.Alloca && allocaSet.contains(address)) {
                        // 移除load
                        instInode.removeSelf();
                        inst.removeAllOperand();
                    }
                }
                if (inst instanceof MemoryInst.Store) {
                    var address = inst.getOperandAt(1);
                    if (address instanceof MemoryInst.Alloca && allocaSet.contains(address)) {
                        // 移除store
                        instInode.removeSelf();
                        inst.removeAllOperand();
                    }
                }
            }
        }
        // 删掉alloca
        var firstBBInode = func.list.getFirst();
        var firstBB = firstBBInode.getElement();
        for (IList.INode<Instruction, BasicBlock> instInode : firstBB.list) {
            var inst = instInode.getElement();
            if (inst instanceof MemoryInst.Alloca && allocaSet.contains(inst)) {
                // 移除alloca
                instInode.removeSelf();
                inst.removeAllOperand();
            }
        }
    }

    private void deriveSinglePhi(Function func) {
        boolean needDerive = true;
        while (needDerive) {
            needDerive = false;
            for (IList.INode<BasicBlock, Function> bbInode : func.list) {
                var bb = bbInode.getElement();
                for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {
                    var inst = instInode.getElement();
                    // 找单分支的phi
                    if (inst.getTag() == Instruction.TAG.PHI) {
                        var newVal = inst.getOperandAt(0);
                        var newBB = (BasicBlock) inst.getOperandAt(1);
                        if (canDerive(inst)) {
                            inst.replaceAllUseWith(newVal);
                            inst.removeAllOperand();
                            inst.node.removeSelf();
                            needDerive = true;
                        }

                    }
                }
            }
        }

    }

    private boolean canDerive(Instruction inst) {
        if (inst.getNumOP() == 2) return true;
        Value v = null;
        for (Use opUse : inst.operandList) {
            if (opUse.getPosition() % 2 != 0) continue;
            if (v == null) v = opUse.getValue();
            else if (v != opUse.getValue()) return false;

        }
        System.out.println("phi可消除：" + inst);
        return true;
    }

}
