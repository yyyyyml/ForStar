package pass.ir.addmerge;

import ir.Instruction;
import ir.Module;
import ir.values.BasicBlock;
import ir.values.Constant;
import ir.values.Function;
import pass.ir.BaseIRPass;
import util.IList;

import static ir.values.Constant.ConstantInt.getConstantInt;

public class AddConstMerge implements BaseIRPass {
    boolean retNeedDo = false;

    @Override
    public boolean run(Module module) {

        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            if (func.isBuiltin()) continue;
            for (IList.INode<BasicBlock, Function> bbInode : func.list) {
                BasicBlock bb = bbInode.getElement();

                for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {
                    Instruction inst = instInode.getElement();
                    // 如果是add
                    if (inst.isADD()) {
                        Instruction nextInst = instInode.getNext().getElement();
                        if (nextInst.isADD() && inst.useList.size() == 1) {
                            // 连续两个add
                            if (inst.getOperandAt(1) instanceof Constant.ConstantInt thisRightOp
                                    && nextInst.getOperandAt(1) instanceof Constant.ConstantInt nextRightOp
                                    && inst == nextInst.getOperandAt(0)) {
                                retNeedDo = true;
                                // 可以合并
//                                System.out.println("被使用次数：" + inst.useList.size());
//                                System.out.println(inst.useList.getFirst().getUser());
//                                System.out.println(inst.useList.getLast().getUser());
                                var thisLeftOp = inst.getOperandAt(0);
                                var nextLeftOp = nextInst.getOperandAt(0);
                                Constant.ConstantInt newRightOp = getConstantInt(thisRightOp.getVal() + nextRightOp.getVal());
                                nextInst.setOperand(newRightOp, 1); // 更新右值
                                nextLeftOp.replaceAllUseWith(thisLeftOp); // 替换左值
//                                inst.removeAllOperand();//加了会错？
                                instInode.removeSelf();

                            }
                        }
                    }

                }
            }

        }
        return retNeedDo;
    }
}
