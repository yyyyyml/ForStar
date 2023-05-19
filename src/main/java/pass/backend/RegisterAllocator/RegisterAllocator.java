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
    private List<Map.Entry<Integer, LiveInterval>> sortedLiveIntervalList;
    private List<Map.Entry<Integer, LiveInterval>> activeList;
    private HashMap<Integer, VirtualRegister> intMapVreg;
    private int regNum;

    private RegisterUsageTracker regUsageTracker;
    private int time = 0;

    public RegisterAllocator() {
        liveIntervalMapList = new HashMap<>();
        activeList = new ArrayList<>();
        intMapVreg = new HashMap<>();
        regNum = 5;
        regUsageTracker = new RegisterUsageTracker(regNum);
    }

    // 寄存器第一次出现，设置Start，并放进Map
    public void setLiveIntervalStart(int virtualRegister, int start) {
        LiveInterval interval = new LiveInterval(start, start + 10);
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

        sortedLiveIntervalList = sortByStart();
        for (Map.Entry<Integer, LiveInterval> entry: sortedLiveIntervalList) {
            System.out.println("Register: " + entry.getKey().toString());
            System.out.println("Start: " + entry.getValue().getStart());
            System.out.println("End: " + entry.getValue().getEnd());
            System.out.println();
        }

        // 线性扫描
        linearScan();

        for (Map.Entry<Integer, RegisterUsage> entry : regUsageTracker.getRegisterUsageMap().entrySet()) {
            int timePoint = entry.getKey();
            RegisterUsage registerUsage = entry.getValue();

            System.out.println("Time Point: " + timePoint);
            for (int register = 0; register < registerUsage.getRegNum(); register++) {
                boolean isUsed = registerUsage.isRegisterUsed(register);
                System.out.println("Register " + register + ": " + (isUsed ? "Used" : "Free"));
            }
            System.out.println("-------------------");
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

    private void linearScan() {
        for (Map.Entry<Integer, LiveInterval> entry : sortedLiveIntervalList) {
            // 更新time
            time = entry.getValue().getStart();
            // 把已经不需要的变量的寄存器释放
            expireOld(entry);
            if (activeList.size() == regNum) {
                // 溢出，找End最大的
                spill(entry);
            } else {
                // 分配一个寄存器
                var curVreg = intMapVreg.get(entry.getKey());
                var curRegUsage = regUsageTracker.getRegisterUsage(time);
                int freeRegister = curRegUsage.getNextFreeRegister();
                System.out.println("Allocating register: " + entry.getKey() + " -> " + freeRegister);
                curVreg.setRealReg(freeRegister);
                // 加入activeList，并按End排序
                activeList.add(entry);
                Collections.sort(activeList, Comparator.comparingInt(e -> e.getValue().getEnd()));
            }
        }
    }

    private void spill(Map.Entry<Integer, LiveInterval> curEntry) {
        // 取出最后一个，也是End最大的
        var spillEntry = activeList.get(activeList.size() - 1);
        // 找到当前对应的虚拟寄存器
        var curVreg = intMapVreg.get(curEntry.getKey());

        if (spillEntry.getValue().getEnd() > curEntry.getValue().getEnd()) {
            // 如果需要把activeList中的spill
            // 找到他们的虚拟寄存器
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
            Collections.sort(activeList, Comparator.comparingInt(e -> e.getValue().getEnd()));
            System.out.println("Spilling: " + spillEntry.getKey() + " -> stack");
            System.out.println("Allocating register: " + curEntry.getKey() + " -> " + spillVreg.getRealReg());
        } else {
            // 如果需要把当前的spill
            // 分配栈地址
            curVreg.setStackLocation(4);
            // 记录spillTime
            curVreg.setSpillTime(time);
            System.out.println("Spilling: " + curEntry.getKey() + " -> stack");
        }
    }

    private void expireOld(Map.Entry<Integer, LiveInterval> curEntry) {
        // 不能直接在activeList中删除元素，会影响循环，报错
        List<Map.Entry<Integer, LiveInterval>> entriesToRemove = new ArrayList<>();

        for (Map.Entry<Integer, LiveInterval> entry : activeList) {
            if (entry.getValue().getEnd() > curEntry.getValue().getStart()) {
                // 第一个的尾都比当前的头大，说明不需要删除activeList中的元素
                return;
            }
            entriesToRemove.add(entry); // 添加到待删除列表中
            // 释放他的已分配的寄存器
            var curRegUsage = regUsageTracker.getRegisterUsage(time);
            curRegUsage.freeRegister(entry.getKey());
            System.out.println("Freeing register: " + entry.getKey());
        }

        // 在循环结束后进行删除
        activeList.removeAll(entriesToRemove);
    }


}
