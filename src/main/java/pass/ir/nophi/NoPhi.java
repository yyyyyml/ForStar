package pass.ir.nophi;

import ir.Instruction;
import ir.Instructions.MemoryInst;
import ir.Module;
import ir.Value;
import ir.values.BasicBlock;
import ir.values.Function;
import pass.ir.BaseIRPass;
import util.IList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class NoPhi implements BaseIRPass {
    Map<MemoryInst.Phi, LinkedList<Value>> phiMapValues = new HashMap<>();

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
                        var mapList = new LinkedList<Value>();
                        for (int i = 0; i < phiInst.operandList.size(); i += 2) {
                            mapList.add(phiInst.getOperandAt(i));
                        }
                        phiMapValues.put(phiInst, mapList);
                    }

                }
            }


        }
        return false;
    }
}
