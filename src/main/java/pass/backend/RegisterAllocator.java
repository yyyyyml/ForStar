package pass.backend;

import backend.*;
import backend.RISCOperand;
import backend.operands.VirtualRegister;

import java.util.*;

/**
 * 简单的线性扫描寄存器分配
 */
public class RegisterAllocator implements BaseBackendPass{

    private HashMap<Integer, LiveInterval> liveIntervals;

    public RegisterAllocator() {
        liveIntervals = new HashMap<>();
    }

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

    public void setLiveIntervalStart(int virtualRegister, int start) {
        LiveInterval interval = new LiveInterval(start, 10000);
        addLiveInterval(virtualRegister, interval);
    }

    public void setLiveIntervalEnd(int virtualRegister, int end) {
        LiveInterval interval = liveIntervals.get(virtualRegister);
        interval.setEnd(end);
    }

    public void addLiveInterval(int virtualRegister, LiveInterval interval) {
        liveIntervals.put(virtualRegister, interval);
    }

    public void sortByStart() {
        List<Map.Entry<Integer, LiveInterval>> sortedIntervals = new ArrayList<>(liveIntervals.entrySet());

        Collections.sort(sortedIntervals, Comparator.comparingInt(e -> e.getValue().getStart()));

        liveIntervals.clear();

        for (Map.Entry<Integer, LiveInterval> entry : sortedIntervals) {
            liveIntervals.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void run(RISCModule riscModule) {
        System.out.println("优化：分配寄存器");
        LinkedList<RISCOperand> globalVars = riscModule.getGlobalVars();
        LinkedList<RISCFunction> funcList = riscModule.getFunctionList();

        int index = 0; // 用于记录位置，存到live interval中

        // 先遍历一遍 记录变量的live interval
        for (RISCOperand variable: globalVars) {

        }
        for (RISCFunction riscFunc: funcList) {
            LinkedList<RISCBasicBlock> riscBBList = riscFunc.getBasicBlockList();
            for (RISCBasicBlock riscBB: riscBBList) {
                LinkedList<RISCInstruction> riscInstList = riscBB.getInstructionList();
                for (RISCInstruction riscInst: riscInstList) {
                    index += 1;
                    LinkedList<RISCOperand> operandList = riscInst.getOperandList();
                    for (RISCOperand riscOp: operandList) {
                        if (riscOp.isVirtualRegister()) {
                            var name = ((VirtualRegister) riscOp).getName();
                            if (!liveIntervals.containsKey(name)) {
                                setLiveIntervalStart(name, index);
                            }
                        }
                    }
                }
            }

        }
    }
}
