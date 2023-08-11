package pass.ir.phimerge;

import ir.Instruction;
import ir.Instructions.MemoryInst;
import ir.Module;
import ir.Value;
import ir.values.BasicBlock;
import ir.values.Function;
import pass.ir.BaseIRPass;
import util.IList;

public class PhiMerge implements BaseIRPass {
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
                    if (inst instanceof MemoryInst.Phi phiInst) {
                        int count = 0;
                        Value newValue = null;
                        for (int i = 0; i < phiInst.operandList.size(); i++) {
                            if (i % 2 == 1) continue;
                            var op = phiInst.getOperandAt(i);
                            if (!op.equals(phiInst)) {
                                count++; // 每遇到一个非自己的就加
                                if (count > 1) break;
                                newValue = op; // 保存这个值
                            }
                        }
                        if (count == 1) {
                            // 单phi也不一定可以，要判断这种情况
                            // %4 = phi i32 [%7, %6]
                            // %7 = add i32 %4, 2
//                            for (int i = 0; i < newValue.)
                            retNeedDo = true;
                            phiInst.replaceAllUseWith(newValue);
                            phiInst.removeAllOperand();
                            phiInst.node.removeSelf();
                        }
                    }


                }
            }

        }
        return retNeedDo;
    }
}
