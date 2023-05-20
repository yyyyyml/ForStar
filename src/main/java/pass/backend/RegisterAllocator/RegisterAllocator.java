package pass.backend.RegisterAllocator;

import backend.*;
import backend.RISCOperand;
import backend.instructions.LwInstruction;
import backend.instructions.SwInstruction;
import backend.operands.RealRegister;
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
    private RegisterUsage curRegUsage;

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

        // 初始化LiveInterval
        initializeLiveInterval(globalVars, funcList);

        // 线性扫描
        linearScan();

        // 打印寄存器分配情况
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
                // 这里用instIndex循环，因为后面要添加或修改指令
                for (int instIndex = 0; instIndex < riscInstList.size(); instIndex++) {
                    RISCInstruction riscInst = riscInstList.get(instIndex);
                    instIndex = riscInstList.indexOf(riscInst);
                    int position = riscInst.getId(); // 当前指令位置号
                    if (position == -1) {
                        // 新添加的指令，第一次没遍历过，不需要处理
                        continue;
                    }
                    LinkedList<RISCOperand> operandList = riscInst.getOperandList();
                    int tempRegId = 0;
                    for (int opIndex = 0; opIndex < operandList.size(); opIndex++) {
                        var riscOp = operandList.get(opIndex);
                        int opPosition = riscOp.getPosition();
                        if (riscOp.isVirtualRegister()) {
                            VirtualRegister vReg = (VirtualRegister) riscOp;
                            var name = vReg.getName();
                            if (vReg.getSpillTime() > position) {
                                // spillTime > 当前位置，说明分配过寄存器
                                var id = vReg.getRealReg();
                                var newReg = new RealRegister(id, 11);
                                // 替换操作数
                                riscInst.setOpLocal(newReg, opIndex, opPosition);

                            } else {
                                // spillTime <= 当前位置，说明此时这个虚拟寄存器中的变量在栈中，需要暂时替换一个寄存器，用完后再换回
                                var curRegUsage = regUsageTracker.getPreRegisterUsage(position);
                                var tempRegID = curRegUsage.getNextFreeRegister();

                                if (tempRegID != -1) {
                                    // 有空闲，分配成功
                                    RealRegister tempReg = new RealRegister(tempRegID, 11);
                                    // 替换操作数
                                    riscInst.setOpLocal(tempReg, opIndex, opPosition);
                                    // 添加写回内存的指令 sw
                                    // TODO: 改成真正的栈地址
                                    RISCInstruction swInst = new SwInstruction(tempReg, tempReg);
                                    riscInstList.add(instIndex + 1, swInst);

                                } else {
                                    // 没有空闲，需要临时替换一个，保存里面的值再替换回去
                                    // TODO: 处理临时替换寄存器的操作 改成真正的栈地址
                                    // 在同一指令中，直接从第一个寄存器开始递增
                                    RealRegister tempReg = new RealRegister(tempRegId++, 11);
                                    RISCInstruction inst1 = new SwInstruction(tempReg, tempReg); // 保存原值
                                    RISCInstruction inst2 = new LwInstruction(tempReg, tempReg); // 存入临时值

                                    RISCInstruction inst3 = new SwInstruction(tempReg, tempReg); // 写回临时值
                                    RISCInstruction inst4 = new LwInstruction(tempReg, tempReg); // 恢复原值

                                    riscInst.setOpLocal(tempReg, opIndex, opPosition); // 当前指令

                                    riscInstList.add(instIndex + 1, inst4);
                                    riscInstList.add(instIndex + 1, inst3);
                                    riscInstList.add(instIndex, inst2);
                                    riscInstList.add(instIndex, inst1);

                                }
                            }
                        }
                        for (RISCOperand op : operandList) {
                            System.out.println(op.emit() + " " + op.getPosition());
                        }
                        System.out.println(opPosition);
                        System.out.println("---------------------" + riscInst.emit());
                    }
                }
            }
        }


    }

    private void initializeLiveInterval(LinkedList<RISCOperand> globalVars, LinkedList<RISCFunction> funcList) {
        int index = 0; // 用于记录位置，存到live interval中

        // 先遍历一遍 记录变量的live interval
        for (RISCOperand variable: globalVars) {

        }
        for (RISCFunction riscFunc: funcList) {
            LinkedList<RISCBasicBlock> riscBBList = riscFunc.getBasicBlockList();
            for (RISCBasicBlock riscBB: riscBBList) {
                LinkedList<RISCInstruction> riscInstList = riscBB.getInstructionList();
                for (RISCInstruction riscInst: riscInstList) {
                    riscInst.setId(index);
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
    }

    private void linearScan() {
        for (Map.Entry<Integer, LiveInterval> entry : sortedLiveIntervalList) {
            // 更新time
            time = entry.getValue().getStart();
            curRegUsage = regUsageTracker.getRegisterUsage(time);
            // 把已经不需要的变量的寄存器释放
            expireOld(entry);
            if (activeList.size() == regNum) {
                // 溢出，找End最大的
                spill(entry);
            } else {
                // 分配一个寄存器
                var curVreg = intMapVreg.get(entry.getKey());
                int freeRegister = curRegUsage.getNextFreeRegister();
                System.out.println("Time: " + time + " Allocating register: " + entry.getKey() + " -> " + freeRegister);
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
            System.out.println("Time: " + time + " Spilling: " + spillEntry.getKey() + " -> stack");
            System.out.println("Time: " + time + " Allocating register: " + curEntry.getKey() + " -> " + spillVreg.getRealReg());
        } else {
            // 如果需要把当前的spill
            // 分配栈地址
            curVreg.setStackLocation(4);
            // 记录spillTime
            curVreg.setSpillTime(time);
            System.out.println("Time: " + time + " Spilling: " + curEntry.getKey() + " -> stack");
        }
    }

    private void expireOld(Map.Entry<Integer, LiveInterval> curEntry) {
        // 不能直接在activeList中删除元素，会影响循环，报错
        activeList.removeIf(entry -> {
            if (entry.getValue().getEnd() > curEntry.getValue().getStart()) {
                // 这一个的尾都比当前的头大，说明不需要删除这个元素后面activeList中的元素
                return false;
            }
            // 这个变量的尾都比当前的头小，已经不用了，删掉
            // 释放他的已分配的寄存器
            var reg = intMapVreg.get(entry.getKey());
            curRegUsage.freeRegister(reg.getRealReg());
            System.out.println("Time: " + time + " Freeing register: " + reg.getRealReg());
            return true;
        });
    }


}
