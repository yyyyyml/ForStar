package pass.ir.merge;

import ir.Instruction;
import ir.Instructions.MemoryInst;
import ir.Module;
import ir.values.BasicBlock;
import ir.values.Function;
import pass.ir.BaseIRPass;
import util.IList;

import java.util.HashSet;
import java.util.Set;

public class GEPMerge implements BaseIRPass {
    Set<MemoryInst.GEP> gepSet = new HashSet<>();

    @Override
    public boolean run(Module module) {
        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            for (IList.INode<BasicBlock, Function> bbInode : func.list) {
                BasicBlock bb = bbInode.getElement();
                gepSet.clear(); // 以基本块为单位？
                for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {
                    Instruction inst = instInode.getElement();
                    if (inst instanceof MemoryInst.GEP gepInst) {
                        boolean replace = false;
                        for (MemoryInst.GEP gepInSet : gepSet) {
                            if (isSame(gepInst, gepInSet)) {
                                // 进行替换
                                gepInst.replaceAllUseWith(gepInSet);
                                instInode.removeSelf();
                                replace = true;
                            }
                        }
                        // 不存在这个GEP
                        if (!replace) gepSet.add(gepInst);

                    }
                }
            }
        }
        return false;
    }

    private boolean isSame(MemoryInst.GEP gepInst, MemoryInst.GEP gepInSet) {
        if (gepInst.numOP != gepInSet.numOP) return false;
        for (int i = 0; i < gepInst.numOP; i++) {
            if (!(gepInst.getOperandAt(i).equals(gepInSet.getOperandAt(i)))) {
                return false;
            }
        }
        System.out.println("same");
        return true;
    }
}
