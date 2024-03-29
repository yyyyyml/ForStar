package pass.ir.multiret_eliminate;

import ir.Instruction;
import ir.Module;
import ir.values.BasicBlock;
import ir.values.Function;
import pass.ir.BaseIRPass;
import util.IList;

public class MultiRetEliminate implements BaseIRPass {
    boolean retNeedDo = false;

    @Override
    public boolean run(Module module) {
        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            if (func.isBuiltin()) continue;
            for (IList.INode<BasicBlock, Function> bbInode : func.list) {
                BasicBlock bb = bbInode.getElement();
                boolean flag = false; // 是否出现ret
                for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {
                    Instruction inst = instInode.getElement();
                    if (flag) {
                        instInode.removeSelf();
                        inst.removeAllOperand();
//                        System.out.println("删掉：" + inst);
                    }
                    if (inst.getTag() == Instruction.TAG.RET) {
                        flag = true;
                    }
                }
            }

        }
        return retNeedDo;
    }
}
