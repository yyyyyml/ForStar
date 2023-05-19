package pass.backend;

import backend.*;
import backend.RISCOperand;
import backend.operands.VirtualRegister;

import java.util.*;

/**
 * 简单的线性扫描寄存器分配
 */
public class RegisterAllocator implements BaseBackendPass{

    private HashMap<Integer, LiveInterval> liveIntervalMapList;
    private List<Map.Entry<Integer, LiveInterval>> activeList;
    private HashMap<Integer, VirtualRegister> intMapVreg;
    private int regNum;

    public RegisterAllocator() {
        liveIntervalMapList = new HashMap<>();
        activeList = new ArrayList<>();
        intMapVreg = new HashMap<>();
        regNum = 10;
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

    // 寄存器第一次出现，设置Start，并放进Map
    public void setLiveIntervalStart(int virtualRegister, int start) {
        LiveInterval interval = new LiveInterval(start, 10000);
        addLiveInterval(virtualRegister, interval);
    }

    // Map中已有这个寄存器，直接修改它的End
    public void setLiveIntervalEnd(int virtualRegister, int end) {
        LiveInterval interval = liveIntervalMapList.get(virtualRegister);
        interval.setEnd(end);
    }

    public void addLiveInterval(int virtualRegister, LiveInterval interval) {
        liveIntervalMapList.put(virtualRegister, interval);
    }

    public List<Map.Entry<Integer, LiveInterval>> sortByStart() {
        List<Map.Entry<Integer, LiveInterval>> sortedIntervals = new ArrayList<>(liveIntervalMapList.entrySet());

        Collections.sort(sortedIntervals, Comparator.comparingInt(e -> e.getValue().getStart()));

        liveIntervalMapList.clear();

        for (Map.Entry<Integer, LiveInterval> entry : sortedIntervals) {
            liveIntervalMapList.put(entry.getKey(), entry.getValue());
        }
        return sortedIntervals;
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
                    LinkedList<RISCOperand> operandList = riscInst.getOperandList();
                    for (RISCOperand riscOp: operandList) {
                        if (riscOp.isVirtualRegister()) {
                            var name = ((VirtualRegister) riscOp).getName();
                            intMapVreg.put(name, (VirtualRegister) riscOp);
                            if (!liveIntervalMapList.containsKey(name)) {
                                // 记录Start
                                setLiveIntervalStart(name, index);
                            } else {
                                // 记录End
                                setLiveIntervalEnd(name, index + 1);
                            }
                        }
                    }
                    index += 1;
                }
            }
        }

        List<Map.Entry<Integer, LiveInterval>> sortedLiveIntervalList = sortByStart();
        for (Map.Entry<Integer, LiveInterval> entry: sortedLiveIntervalList) {
            System.out.println("Register: " + entry.getKey().toString());
            System.out.println("Start: " + entry.getValue().getStart());
            System.out.println("End: " + entry.getValue().getEnd());
            System.out.println();
        }

        // 线性扫描
        for (Map.Entry<Integer, LiveInterval> entry: sortedLiveIntervalList) {
            // 把已经不需要的变量的寄存器释放
            expireOld(entry);
            if (activeList.size() == regNum) {
                // 溢出，找End最大的
                spill(entry);
            } else {
                // 分配一个寄存器
                
                // 加入activeList，并按End排序
                activeList.add(entry);
                Collections.sort(activeList, Comparator.comparingInt(e -> e.getValue().getEnd()));
            }
        }







        for (RISCOperand variable: globalVars) {

        }
        for (RISCFunction riscFunc: funcList) {
            LinkedList<RISCBasicBlock> riscBBList = riscFunc.getBasicBlockList();
            for (RISCBasicBlock riscBB: riscBBList) {
                LinkedList<RISCInstruction> riscInstList = riscBB.getInstructionList();
                for (RISCInstruction riscInst: riscInstList) {
                    LinkedList<RISCOperand> operandList = riscInst.getOperandList();
                    for (RISCOperand riscOp: operandList) {
                        if (riscOp.isVirtualRegister()) {
                            var name = ((VirtualRegister) riscOp).getName();

                        }
                    }
                    index += 1;
                }
            }
        }


    }

    private void spill(Map.Entry<Integer, LiveInterval> curEntry) {
        // 取出最后一个，也是End最大的
        var spillEntry = activeList.get(activeList.size() - 1);
        if (spillEntry.getValue().getEnd() > curEntry.getValue().getEnd()) {
            var curVreg = intMapVreg.get(curEntry.getKey());
            var spillVreg = intMapVreg.get(spillEntry.getKey());
            // 换寄存器
            curVreg.setRealReg(spillVreg.getRealReg());

        }
    }

    private void expireOld(Map.Entry<Integer, LiveInterval> curEntry) {
        for (Map.Entry<Integer, LiveInterval> entry: activeList) {
            if (entry.getValue().getEnd() > curEntry.getValue().getStart()) {
                // 第一个的尾都比当前的头大，说明不需要删除activeList中的元素
                return;
            }
            // 这个变量已经不用了，删掉
            activeList.remove(entry);
            // 释放他的已分配的寄存器
            
        }
    }
}
