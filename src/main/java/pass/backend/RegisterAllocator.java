package pass.backend;

import backend.*;
import backend.RISCOperand;

import java.util.LinkedList;

/**
 * 简单的线性扫描寄存器分配
 */
public class RegisterAllocator implements BaseBackendPass{

    /**
     * live interval类
     */
    public class LiveInterval {
        private int start;
        private int end;

        public LiveInterval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }
    }


    public void run(RISCModule target) {
        System.out.println("优化：分配寄存器");
        LinkedList<RISCOperand> globalVars = target.getGlobalVars();
        LinkedList<RISCFunction> funcList = target.getFunctionList();

        // 先遍历一遍 记录变量的live interval
        for (RISCOperand variable: globalVars) {

        }
        for (RISCFunction riscFunc: funcList) {
            LinkedList<RISCBasicBlock> riscBBList = riscFunc.getBasicBlockList();
            for (RISCBasicBlock riscBB: riscBBList) {
                LinkedList<RISCInstruction> riscInstList = riscBB.getInstructionList();
                for (RISCInstruction riscInst: riscInstList) {
                    LinkedList<RISCOperand> operandList = riscInst.getOperandList();
                    for (RISCOperand riscOp: operandList) {

                    }
                }
            }

        }
    }
}
