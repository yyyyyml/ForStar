package pass.backend.RegisterAllocator;

import backend.*;
import backend.RISCOperand;
import backend.operands.VirtualRegister;
import pass.backend.BaseBackendPass;

import java.util.*;


/**
 * 简单的线性扫描寄存器分配
 */
public class RegisterAllocator implements BaseBackendPass {

    private HashMap<Integer, LiveInterval> liveIntervalMapList;
    private List<Map.Entry<Integer, LiveInterval>> activeList;
    private HashMap<Integer, VirtualRegister> intMapVreg;
    private int regNum;
    private RegisterUsage regUsage;
    private RegisterUsageTracker regUsageTracker;

    public RegisterAllocator() {
        liveIntervalMapList = new HashMap<>();
        activeList = new ArrayList<>();
        intMapVreg = new HashMap<>();
        regNum = 10;
        regUsage = new RegisterUsage(regNum);
        regUsageTracker = new RegisterUsageTracker();
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
                var curVreg = intMapVreg.get(entry.getKey());
                curVreg.setRealReg(regUsage.getRandomFreeRegister());
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

    /**
     * 处理溢出情况
     * @param curEntry 当前处理到的Map对
     */
    private void spill(Map.Entry<Integer, LiveInterval> curEntry) {
        // 取出最后一个，也是End最大的
        var spillEntry = activeList.get(activeList.size() - 1);
        // 记录当前的index这里是time，以便存到spillTime
        var time = curEntry.getValue().getStart();
        if (spillEntry.getValue().getEnd() > curEntry.getValue().getEnd()) {
            // 如果需要把activeList中的spill
            // 找到他们的虚拟寄存器
            var curVreg = intMapVreg.get(curEntry.getKey());
            var spillVreg = intMapVreg.get(spillEntry.getKey());
            // 换寄存器
            curVreg.setRealReg(spillVreg.getRealReg());
            // 分配栈地址
            spillVreg.setStackLocation(4);
            // 记录spillTime
            spillVreg.setSpillTime(time);
            // 从activeList移除spill
            activeList.remove(spillEntry);
            // 将curEntry加入activeList，并按End排序
            activeList.add(curEntry);
        } else {
            // 如果需要把当前的spill
            // 分配栈地址
            var curVreg = intMapVreg.get(curEntry.getKey());
            curVreg.setStackLocation(4);
            // 记录spillTime
            curVreg.setSpillTime(time);
        }
    }

    /**
     * 判断并处理是否有一些虚拟寄存器已经不再live
     * @param curEntry
     */
    private void expireOld(Map.Entry<Integer, LiveInterval> curEntry) {
        for (Map.Entry<Integer, LiveInterval> entry: activeList) {
            if (entry.getValue().getEnd() > curEntry.getValue().getStart()) {
                // 第一个的尾都比当前的头大，说明不需要删除activeList中的元素
                return;
            }
            // 这个变量已经不用了，删掉
            activeList.remove(entry);
            // 释放他的已分配的寄存器
            regUsage.freeRegister(entry.getKey());
        }
    }
}
