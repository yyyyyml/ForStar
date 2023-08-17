package pass.backend.loadstore_merge;

import backend.RISCBasicBlock;
import backend.RISCFunction;
import backend.RISCInstruction;
import backend.RISCModule;
import backend.instructions.LdInstruction;
import backend.instructions.SdInstruction;
import pass.backend.BaseBackendPass;

import java.util.LinkedList;

public class LoadStoreMerge implements BaseBackendPass {
    @Override
    public void run(RISCModule riscModule) {
        for (RISCFunction riscFunc : riscModule.getFunctionList()) {
            if (riscFunc.isBuildIn) continue;
            for (RISCBasicBlock riscBB : riscFunc.getBasicBlockList()) {
                LinkedList<RISCInstruction> riscInstList = riscBB.getInstructionList();
                for (int instIndex = 0; instIndex < riscInstList.size(); instIndex++) {
                    if (instIndex == riscInstList.size() - 1) continue;
                    RISCInstruction riscInst = riscInstList.get(instIndex);
                    RISCInstruction nextInst = riscInstList.get(instIndex + 1);
                    if (riscInst instanceof SdInstruction && nextInst instanceof LdInstruction) {
                        var op1 = riscInst.getOperandAt(0);
                        var op2 = riscInst.getOperandAt(1);
                        var nextOp1 = nextInst.getOperandAt(0);
                        var nextOp2 = nextInst.getOperandAt(1);
                        if (op1.equals(op2) && nextOp1.equals(nextOp2)) {
                            // 删了
                            riscInstList.remove(instIndex);
                            riscInstList.remove(instIndex);
                            instIndex--;
                        }

                    }
                }
            }
        }
    }
}
