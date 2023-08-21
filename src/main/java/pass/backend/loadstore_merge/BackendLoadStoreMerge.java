package pass.backend.loadstore_merge;

import backend.*;
import backend.instructions.LdInstruction;
import backend.instructions.SdInstruction;
import pass.backend.BaseBackendPass;

import java.util.LinkedList;

public class BackendLoadStoreMerge implements BaseBackendPass {
    @Override
    public void run(RISCModule riscModule) {
        for (RISCFunction riscFunc : riscModule.getFunctionList()) {
            if (riscFunc.isBuildIn) continue;
            for (RISCBasicBlock riscBB : riscFunc.getBasicBlockList()) {
                llssMerge(riscBB);
                lsMerge(riscBB);
            }

        }
    }

    private void lsMerge(RISCBasicBlock riscBB) {
    }

    private void llssMerge(RISCBasicBlock riscBB) {
        LinkedList<RISCInstruction> riscInstList = riscBB.getInstructionList();
        RISCOperand tempStackAddress = null;
        RISCOperand sdTempStackAddress = null;
        int lastSdIndex = -1;
        for (int instIndex = 0; instIndex < riscInstList.size(); instIndex++) {
            RISCInstruction riscInst = riscInstList.get(instIndex);
            if (riscInst instanceof LdInstruction && riscInst.getOperandAt(0).emit().equals("t2")) {
                if (tempStackAddress != null && riscInst.getOperandAt(1).emit().equals(tempStackAddress.emit())) {
                    riscInstList.remove(instIndex);
                    instIndex--;
                    continue;
                } else {
                    tempStackAddress = riscInst.getOperandAt(1);
                }
                if (sdTempStackAddress != null && riscInst.getOperandAt(1).emit().equals(sdTempStackAddress.emit())) {
                    sdTempStackAddress = null;
                    lastSdIndex = -1;
                }
            } else if (riscInst instanceof SdInstruction) {
                if (!(riscInst.getOperandAt(0).emit().equals("t2"))
                        && tempStackAddress != null
                        && riscInst.getOperandAt(1).emit().equals(tempStackAddress.emit())) {
                    tempStackAddress = null;
                } else if (riscInst.getOperandAt(0).emit().equals("t2")) {
                    if (sdTempStackAddress != null
                            && sdTempStackAddress.emit().equals(riscInst.getOperandAt(1).emit())) {
                        riscInstList.remove(lastSdIndex);
                        instIndex--;
                        lastSdIndex = instIndex;
                        sdTempStackAddress = riscInst.getOperandAt(1);
                    } else {
                        lastSdIndex = instIndex;
                        sdTempStackAddress = riscInst.getOperandAt(1);
                    }

                }
            }
        }
    }
}
