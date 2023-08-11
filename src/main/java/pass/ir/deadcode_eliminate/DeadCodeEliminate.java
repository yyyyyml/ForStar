package pass.ir.deadcode_eliminate;

import ir.Instruction;
import ir.Instructions.TerminatorInst;
import ir.Module;
import ir.values.BasicBlock;
import ir.values.Function;
import pass.ir.BaseIRPass;
import util.IList;

public class DeadCodeEliminate implements BaseIRPass {
    boolean retNeedDo = false;

    @Override
    public boolean run(Module module) {
        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            if (func.isBuiltin()) continue;
            boolean needEliminate = true;
            int i = 0;
            // 不断消除，直到没有可消的
            while (needEliminate) {
                needEliminate = false;
                for (IList.INode<BasicBlock, Function> bbInode : func.list) {
                    BasicBlock bb = bbInode.getElement();

                    for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {
                        Instruction inst = instInode.getElement();

                        boolean removeInst = false;
                        if (inst.needName && inst.useList.isEmpty() && !(inst instanceof TerminatorInst.Call)) {
                            // 看没有使用这个指令结果的，没有说明这个指令没用，删
                            removeInst = true;
                        }

                        if (removeInst) {
                            inst.removeAllOperand();
                            instInode.removeSelf();
                            needEliminate = true;
                            retNeedDo = true;

                            System.out.println("删掉：" + inst);
//                            throw new RuntimeException("优化了");
                        }
                        i++;


                    }
                }
            }


        }
        return retNeedDo;
    }
}
