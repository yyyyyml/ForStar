package pass.ir.merge;

import ir.Instruction;
import ir.Instructions.BinaryInst;
import ir.Module;
import ir.Type;
import ir.Value;
import ir.values.BasicBlock;
import ir.values.Function;
import pass.ir.BaseIRPass;
import util.IList;

import static ir.values.Constant.ConstantInt.getConstantInt;

public class AddSameMerge implements BaseIRPass {
    boolean retNeedDo = false;

    @Override
    public boolean run(Module module) {
        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            if (func.isBuiltin()) continue;
            for (IList.INode<BasicBlock, Function> bbInode : func.list) {
                BasicBlock bb = bbInode.getElement();

                int count = 0;
                Value tempOp = null;
                Type tempType = null;
                for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {
                    Instruction inst = instInode.getElement();
                    if (count > 0) {
                        // count不是0,如果是add
                        if (inst.isADD()) {
                            Instruction nextInst = instInode.getNext().getElement();
                            if (nextInst.isADD() && inst.useList.size() == 1 &&
                                    inst.getOperandAt(1).equals(nextInst.getOperandAt(1)) &&
                                    inst == nextInst.getOperandAt(0)) {
                                // 连续两个add且前一个只会在后一个中用到
                                // 判断两次加的数是不是一样的
                                count++;
                                inst.removeAllOperand();
                                instInode.removeSelf();

                            } else {
                                if (tempOp != null) {
                                    // 要多加一次
                                    var mulInst = new BinaryInst(tempType, Instruction.TAG.MUL, inst.getOperandAt(1), getConstantInt(count + 1));
                                    var addInst = new BinaryInst(tempType, Instruction.TAG.ADD, tempOp, mulInst);
                                    // 先乘再加
                                    bb.list.addAfter(instInode, addInst.node);
                                    bb.list.addAfter(instInode, mulInst.node);
                                    inst.replaceAllUseWith(addInst); // 替换
                                    inst.removeAllOperand();
                                    instInode.removeSelf();
                                    count = 0;
                                    tempOp = null;
                                    tempType = null;
                                } else {
                                    // 不需要多加
                                    var mulInst = new BinaryInst(tempType, Instruction.TAG.MUL, inst.getOperandAt(1), getConstantInt(count + 1));
                                    bb.list.addAfter(instInode, mulInst.node);
                                    inst.replaceAllUseWith(mulInst); // 替换
                                    inst.removeAllOperand();
                                    instInode.removeSelf();
                                    count = 0;
                                    tempOp = null;
                                    tempType = null;
                                }
                            }
                        } else System.out.println("???????????????????????");
                    } else {
                        // count是0,如果是add
                        if (inst.isADD()) {
                            Instruction nextInst = instInode.getNext().getElement();
                            if (nextInst.isADD() && inst.useList.size() == 1) {
                                // 连续两个add且前一个只会在后一个中用到
                                // 判断两次加的数是不是一样的
                                if (inst.getOperandAt(1).equals(nextInst.getOperandAt(1)) && inst == nextInst.getOperandAt(0)) {
                                    // 可以转为乘法
                                    if (inst.getOperandAt(0).equals(inst.getOperandAt(1))) {
                                        // 第一次加就是这两个相同的，不用保存第一个加的数
                                        count++;

                                    } else {
                                        // 需要保存第一个加的数
                                        tempOp = inst.getOperandAt(0);
                                    }
                                    count++;
                                    tempType = inst.getType();
                                    inst.removeAllOperand();
                                    instInode.removeSelf();
                                    retNeedDo = true;
                                }
                            }
                        }
                    }


                }
            }

        }
        return retNeedDo;
    }
}
