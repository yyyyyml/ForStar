package pass.ir.deadcode_eliminate;

import ir.Instruction;
import ir.Module;
import ir.Use;
import ir.values.BasicBlock;
import ir.values.Function;
import pass.ir.BaseIRPass;
import util.IList;

public class DeadCodeEliminate implements BaseIRPass {
    @Override
    public void run(Module module) {
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
                        if (inst.needName && inst.useList.isEmpty()) {
                            // 看没有使用这个指令结果的，没有说明这个指令没用，删
                            removeInst = true;

                            if (!inst.operandList.isEmpty()) {
                                // 有操作数，从useList中移除
                                for (Use opUse : inst.operandList) {
                                    var val = opUse.getValue();
//                                    System.out.println(val);
                                    val.removeUse(opUse);
                                }
                            }
                        }

                        if (removeInst) {
                            instInode.removeSelf();
//                            needEliminate = true;
                            System.out.println("删掉：" + i);
                        }
                        i++;


                    }
                }
            }


        }
    }
}
