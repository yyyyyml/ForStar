package pass.backend.RegisterAllocator;

import backend.*;
import backend.instructions.LwInstruction;
import backend.instructions.SwInstruction;
import backend.operands.Immediate;
import backend.operands.Memory;
import backend.operands.RealRegister;
import backend.operands.VirtualRegister;
import pass.backend.BaseBackendPass;

import java.util.*;


/**
 * 简单的线性扫描寄存器分配
 * 目前改成了以函数为单位的
 */
public class RegisterAllocator implements BaseBackendPass {

    private HashMap<Integer, LiveInterval> liveIntervalMapList;
    private List<Map.Entry<Integer, LiveInterval>> sortedLiveIntervalList;
    private List<Map.Entry<Integer, LiveInterval>> activeList;
    private HashMap<Integer, VirtualRegister> intMapVreg;
    private final int regNum;

    private RegisterUsageTracker regUsageTracker;
    private int time = 0;
    private RegisterUsage curRegUsage;
    private ArrayList<RISCFunction> time2Function; // 时间点对应的函数，其实现在这个没什么用了，因为以函数为单位了，但是不改了


    public RegisterAllocator() {
        liveIntervalMapList = new HashMap<>();
        activeList = new ArrayList<>();
        intMapVreg = new HashMap<>(); // 编号对应的虚拟寄存器对象
        time2Function = new ArrayList<>();
        regNum = 15;
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

    /**
     * 添加寄存器的LiveInterval到Map中
     *
     * @param virtualRegister 虚拟寄存器
     * @param interval        寄存器的LiveInterval
     */
    public void addLiveInterval(int virtualRegister, LiveInterval interval) {
        liveIntervalMapList.put(virtualRegister, interval);
    }

    /**
     * 按照LiveInterval的Start值进行排序
     *
     * @return 排序后的LiveInterval列表
     */
    public List<Map.Entry<Integer, LiveInterval>> sortByStart() {
        List<Map.Entry<Integer, LiveInterval>> sortedIntervals = new ArrayList<>(liveIntervalMapList.entrySet());

        sortedIntervals.sort(Comparator.comparingInt(e -> e.getValue().getStart()));

        liveIntervalMapList.clear();

        for (Map.Entry<Integer, LiveInterval> entry : sortedIntervals) {
            liveIntervalMapList.put(entry.getKey(), entry.getValue());
        }
        return sortedIntervals;
    }


    // 运行优化全过程
    @Override
    public void run(RISCModule riscModule) {
        System.out.println("优化：分配寄存器");
        LinkedList<RISCOperand> globalVars = riscModule.getGlobalVars();
        LinkedList<RISCFunction> funcList = riscModule.getFunctionList();

        for (RISCFunction riscFunc : funcList) {
            // 先清空这些结构
            liveIntervalMapList.clear();
            activeList.clear();
            intMapVreg.clear();
            time2Function.clear();
            regUsageTracker = new RegisterUsageTracker(regNum);


            // 初始化LiveInterval
            initializeLiveInterval(riscFunc);

            // 线性扫描
            linearScan();

//            // 打印寄存器分配情况
//            for (Map.Entry<Integer, RegisterUsage> entry : regUsageTracker.getRegisterUsageMap().entrySet()) {
//                int timePoint = entry.getKey();
//                RegisterUsage registerUsage = entry.getValue();
//
//                System.out.println("Time Point: " + timePoint);
//                for (int register = 0; register < registerUsage.getRegNum(); register++) {
//                    boolean isUsed = registerUsage.isRegisterUsed(register);
//                    System.out.println("Register " + register + ": " + (isUsed ? "Used" : "Free"));
//                }
//                System.out.println("-------------------");
//            }

            // 生成新的MIR
            renameRegister(riscFunc);
        }


    }


    private void initializeLiveInterval(RISCFunction riscFunc) {
        int index = 0; // 用于记录位置，存到live interval中

        // 先遍历一遍 记录变量的live interval
        riscFunc.stackSize = riscFunc.localStackIndex + 8 * riscFunc.operandStackCounts + 4;
        riscFunc.stackIndex = riscFunc.localStackIndex + 8 * riscFunc.operandStackCounts + 4;
        System.out.println("zhan----------------------" + riscFunc.stackSize);

        System.out.println(riscFunc.stackSize);
        LinkedList<RISCBasicBlock> riscBBList = riscFunc.getBasicBlockList();
        for (RISCBasicBlock riscBB : riscBBList) {
            LinkedList<RISCInstruction> riscInstList = riscBB.getInstructionList();
            for (RISCInstruction riscInst : riscInstList) {
                time2Function.add(index, riscFunc);
                riscInst.setId(index); // 记录每条指令的标号
                LinkedList<RISCOperand> operandList = riscInst.getOperandList();
                for (RISCOperand riscOp : operandList) {
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
                    } else if (riscOp.isMemory()) {
                        var mem = (Memory) riscOp;
                        if (mem.basicAddress.isVirtualRegister()) {
                            var name = ((VirtualRegister) mem.basicAddress).getName();
                            intMapVreg.put(name, (VirtualRegister) mem.basicAddress);
                            if (!liveIntervalMapList.containsKey(name)) {
                                // 记录Start
                                setLiveIntervalStart(name, index);
                            } else {
                                // 记录End
                                setLiveIntervalEnd(name, index + 1);
                            }
                        }
                    }
                }
                index += 1;
            }
        }

        sortedLiveIntervalList = sortByStart();
//        for (Map.Entry<Integer, LiveInterval> entry : sortedLiveIntervalList) {
//            System.out.println("Register: vr_" + entry.getKey().toString());
//            System.out.println("Start: " + entry.getValue().getStart());
//            System.out.println("End: " + entry.getValue().getEnd());
//            System.out.println();
//        }
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
//                System.out.println("Time: " + time + " Allocating register: vr_" + entry.getKey() + " -> " + freeRegister);
                curVreg.setRealReg(freeRegister);
                // 加入activeList，并按End排序
                activeList.add(entry);
                activeList.sort(Comparator.comparingInt(e -> e.getValue().getEnd()));
            }
        }
    }

    private void spill(Map.Entry<Integer, LiveInterval> curEntry) {
        // 取出最后一个，也是End最大的
        boolean directSpill = false;
        Map.Entry<Integer, LiveInterval> spillEntry = null;
        if (activeList.size() == 0) {
            directSpill = true;
        } else {
            spillEntry = activeList.get(activeList.size() - 1);
        }

        // 找到当前对应的虚拟寄存器
        var curVreg = intMapVreg.get(curEntry.getKey());

        if (!directSpill && spillEntry.getValue().getEnd() > curEntry.getValue().getEnd()) {
            // 如果需要把activeList中的spill
            // 找到他们的虚拟寄存器
            var spillVreg = intMapVreg.get(spillEntry.getKey());
            // 换寄存器
            curVreg.setRealReg(spillVreg.getRealReg());
            //
            curVreg.setvRegReplaced(spillVreg);
            // 分配栈地址
            var curFunc = time2Function.get(time);
            spillVreg.setStackLocation(curFunc.stackIndex);
//            System.out.println("Time: " + time + " Spilling: vr_" + spillEntry.getKey() + " -> stack " + curFunc.stackIndex);
//            System.out.println("Time: " + time + " Allocating register: vr_" + curEntry.getKey() + " -> " + spillVreg.getRealReg());
            curFunc.stackSize += 4;
            curFunc.stackIndex += 4;
            // 记录spillTime
            spillVreg.setSpillTime(time);
            // 从activeList移除spill
            activeList.remove(spillEntry);
            // 将curEntry加入activeList，并按End排序
            activeList.add(curEntry);
            activeList.sort(Comparator.comparingInt(e -> e.getValue().getEnd()));

        } else {
            // 如果需要把当前的spill
            // 分配栈地址
            var curFunc = time2Function.get(time);

            curVreg.setStackLocation(curFunc.stackIndex);
//            System.out.println("Time: " + time + " Spilling: vr_" + curEntry.getKey() + " -> stack " + curFunc.stackIndex);
            curFunc.stackSize += 4;
            curFunc.stackIndex += 4;
            // 记录spillTime
            curVreg.setSpillTime(time);

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
//            System.out.println("Time: " + time + " Freeing register: " + reg.getRealReg());
            return true;
        });
    }

    private void renameRegister(RISCFunction riscFunc) {

        LinkedList<RISCBasicBlock> riscBBList = riscFunc.getBasicBlockList();
        ArrayList<RealRegister> nowRealRegList = new ArrayList<>();

        for (RISCBasicBlock riscBB : riscBBList) {
            LinkedList<RISCInstruction> riscInstList = riscBB.getInstructionList();

            // 这里用instIndex循环，因为后面要添加或修改指令
            for (int instIndex = 0; instIndex < riscInstList.size(); instIndex++) {
                RISCInstruction riscInst = riscInstList.get(instIndex);
//                System.out.println(riscInst.emit());

                int position = riscInst.getId(); // 当前指令位置号
                if (position == -1) {
                    // 新添加的指令，第一次没遍历过，不需要处理
                    continue;
                }
                LinkedList<RISCOperand> operandList = riscInst.getOperandList();
                int tempIdFromZero = 0;
                int tempStackIndex = riscFunc.stackIndex; // 保存这条指令分配临时栈空间之前的栈位置，用于恢复
                boolean[] visitVReg = new boolean[100010]; // 这条指令中visit过哪些虚拟寄存器
                HashMap<Integer, RealRegister> nameMapReg = new HashMap<>();

                for (int opIndex = 0; opIndex < operandList.size(); opIndex++) {
                    var riscOp = operandList.get(opIndex);
                    int opPosition = riscOp.getPosition();

                    if (riscOp.isMemory()) { // 在内存表示中可能会用到虚拟寄存器，所以要处理
                        var mem = (Memory) riscOp;
                        if (mem.basicAddress.isVirtualRegister()) {
                            var vReg = (VirtualRegister) mem.basicAddress;
                            int name = vReg.getName();
                            if (visitVReg[name]) {
                                //这个指令已经处理过这个虚拟寄存器，直接给它那个寄存器就可以
                                var register = nameMapReg.get(name);
//                                System.out.println("get" + name + "--" + register);
                                // 替换操作数
                                riscInst.setOpLocal(register, opIndex, opPosition);
                                continue;
                            } else {
                                visitVReg[name] = true; // visit过
//                            System.out.println("visit" + name + "--" + register);
                            }

                            // 判断这个时刻当前vReg要使用的寄存器是不是之前有别的vReg在用，如果是需要把之前的值溢出到栈中
                            var vRegReplaced = vReg.getvRegReplaced();
                            if (vRegReplaced != null && vRegReplaced.getSpillTime() == position && vRegReplaced.getRealReg() != -1) {
                                // 说明这时需要把那个被替换的寄存器溢出到相应栈中
                                // 需要被换的寄存器
                                RealRegister realReg = new RealRegister(vRegReplaced.getRealReg(), 11);
                                // 添加写回内存的指令 sw
                                var stack = new Memory(-vRegReplaced.getStackLocation(), 1); // 临时栈
                                RISCInstruction swInst = new SwInstruction(realReg, stack); // 存入栈
                                riscInstList.add(instIndex, swInst); // 在当前这条指令之前，入栈
                                instIndex++; // 跳过加的指令

                            }

                            if (vReg.getSpillTime() > position) {
                                // spillTime > 当前位置，说明分配过寄存器
                                var id = vReg.getRealReg();
                                var newReg = new RealRegister(id, 11);
                                if (!listHasReg(nowRealRegList, newReg))
                                    nowRealRegList.add(newReg); // 加入当前在用的寄存器
                                // 替换内存的basicAddress
                                mem.basicAddress = newReg;
                                // 记录替换成了哪个
                                nameMapReg.put(name, newReg);
//                                System.out.println("put" + name + "--" + newReg);

                            } else {
                                // 说明此时这个虚拟寄存器中的变量已经在栈中（spillTime < 当前位置）
                                // 或是第一次出现就需要溢出到栈里，即没有分配过寄存器（spillTime == 当前位置）
                                // 需要暂时替换一个寄存器，用完后再换回
                                var curRegUsage = regUsageTracker.getPreRegisterUsage(position);
                                var tempRegID = curRegUsage.getNextFreeRegister();

                                if (tempRegID != -1) {
                                    // 有空闲，分配成功
                                    RealRegister tempReg = new RealRegister(tempRegID, 11);
                                    if (!listHasReg(nowRealRegList, tempReg))
                                        nowRealRegList.add(tempReg); // 加入当前在用的寄存器
                                    // 替换内存的basicAddress
                                    mem.basicAddress = tempReg;
                                    // 记录替换成了哪个
                                    nameMapReg.put(name, tempReg);
//                                    System.out.println("put" + name + "--" + tempReg);
                                    // 添加写回内存的指令 sw
                                    var stack = new Memory(-vReg.getStackLocation(), 1); // 临时栈
                                    RISCInstruction lwInst = new LwInstruction(tempReg, stack); // 存入溢出的值
                                    RISCInstruction swInst = new SwInstruction(tempReg, stack); // 写回溢出值
                                    if (vReg.getSpillTime() < position) {
                                        riscInstList.add(instIndex + 1, swInst);
                                        riscInstList.add(instIndex, lwInst); // 之前存过才需要这个
                                        instIndex += 1; // 跳过加的指令
                                    } else {
                                        riscInstList.add(instIndex + 1, swInst);
                                    }
//                                    System.out.println(swInst.emit());

                                } else {
                                    // 没有空闲，需要临时替换一个，保存里面的值再替换回去
                                    // 在同一指令中，直接从第一个寄存器开始递增
                                    // 但是，注意不能使用这个指令中要用的其他寄存器
                                    // 应该不会有其他内存，所以不判断内存中的虚拟寄存器了，有bug再改
                                    for (int opIndexIn = 0; opIndexIn < operandList.size(); opIndexIn++) {
                                        var riscOpIn = operandList.get(opIndexIn);
                                        if (riscOpIn.isVirtualRegister()) {
                                            VirtualRegister vRegIn = (VirtualRegister) riscOpIn;
                                            if (tempIdFromZero == vRegIn.getRealReg()) {
                                                tempIdFromZero++;
                                                opIndexIn = 0;
                                            }
                                        }
                                    }

                                    var tempReg = new RealRegister(tempIdFromZero++, 11);
                                    if (!listHasReg(nowRealRegList, tempReg))
                                        nowRealRegList.add(tempReg); // 加入当前在用的寄存器
//                                    System.out.println(tempIdFromZero);

                                    var tempStack = new Memory(-riscFunc.stackIndex, 1); // 临时栈
                                    riscFunc.stackIndex += 4; // 开辟出临时保存寄存器值的位置
//                                    System.out.println("开辟了新的栈 " + riscFunc.stackIndex);
                                    if (riscFunc.stackSize < riscFunc.stackIndex)
                                        riscFunc.stackSize = riscFunc.stackIndex; // 容量是否需要更新
                                    var spillStack = new Memory(-vReg.getStackLocation(), 1); // 之前溢出保存的栈
                                    RISCInstruction inst1 = new SwInstruction(tempReg, tempStack); // 保存原值
                                    RISCInstruction inst2 = new LwInstruction(tempReg, spillStack); // 存入溢出的值

                                    RISCInstruction inst3 = new SwInstruction(tempReg, spillStack); // 写回溢出值
                                    RISCInstruction inst4 = new LwInstruction(tempReg, tempStack); // 恢复原值

                                    mem.basicAddress = tempReg; // 当前指令
                                    nameMapReg.put(name, tempReg); // 记录替换成了哪个
//                                    System.out.println("put" + name + "--" + tempReg);

                                    riscInstList.add(instIndex + 1, inst4);
                                    riscInstList.add(instIndex + 1, inst3);
                                    if (vReg.getSpillTime() < position) {
                                        riscInstList.add(instIndex, inst2); // 之前存过才需要这个
                                        riscInstList.add(instIndex, inst1);
                                        instIndex += 2; // 跳过加在前面的指令
                                    } else {
                                        riscInstList.add(instIndex, inst1);
                                        instIndex += 1; // 跳过加在前面的指令
                                    }


                                }
                            }
                        }
                    } else if (riscOp.isVirtualRegister()) {
                        var vReg = (VirtualRegister) riscOp;
                        int name = vReg.getName();
                        if (visitVReg[name]) {
                            //这个指令已经处理过这个虚拟寄存器，直接给它那个寄存器就可以
                            var register = nameMapReg.get(name);
//                            System.out.println("get" + name + "--" + register);
                            // 替换操作数
                            riscInst.setOpLocal(register, opIndex, opPosition);
                            continue;
                        } else {
                            visitVReg[name] = true; // visit过
//                            System.out.println("visit" + name + "--" + register);
                        }


                        // 判断这个时刻当前vReg要使用的寄存器是不是之前有别的vReg在用，如果是需要把之前的值溢出到栈中
                        var vRegReplaced = vReg.getvRegReplaced();
                        if (vRegReplaced != null && vRegReplaced.getSpillTime() == position && vRegReplaced.getRealReg() != -1) {
                            // 说明这时需要把那个被替换的寄存器溢出到相应栈中
                            // 需要被换的寄存器
                            RealRegister realReg = new RealRegister(vRegReplaced.getRealReg(), 11);
                            // 添加写回内存的指令 sw
                            var stack = new Memory(-vRegReplaced.getStackLocation(), 1); // 临时栈
                            RISCInstruction swInst = new SwInstruction(realReg, stack); // 存入栈
                            riscInstList.add(instIndex, swInst); // 在当前这条指令之前，入栈
                            instIndex++; // 跳过加的指令

                        }

                        if (vReg.getSpillTime() > position) {
                            // spillTime > 当前位置，说明分配过寄存器
                            var id = vReg.getRealReg();
                            var newReg = new RealRegister(id, 11);
                            if (!listHasReg(nowRealRegList, newReg))
                                nowRealRegList.add(newReg); // 加入当前在用的寄存器
                            // 替换操作数
                            riscInst.setOpLocal(newReg, opIndex, opPosition);
                            // 记录替换成了哪个
                            nameMapReg.put(name, newReg);
//                            System.out.println("put" + name + "--" + newReg);

                        } else {
                            // 说明此时这个虚拟寄存器中的变量已经在栈中（spillTime < 当前位置）
                            // 或是第一次出现就需要溢出到栈里，即没有分配过寄存器（spillTime == 当前位置）
                            // 需要暂时替换一个寄存器，用完后再换回
                            var curRegUsage = regUsageTracker.getPreRegisterUsage(position);
                            var tempRegID = curRegUsage.getNextFreeRegister();

                            if (tempRegID != -1) {
                                // 有空闲，分配成功
                                RealRegister tempReg = new RealRegister(tempRegID, 11);
                                if (!listHasReg(nowRealRegList, tempReg))
                                    nowRealRegList.add(tempReg); // 加入当前在用的寄存器
                                // 替换操作数
                                riscInst.setOpLocal(tempReg, opIndex, opPosition);
                                // 记录替换成了哪个
                                nameMapReg.put(name, tempReg);
//                                System.out.println("put" + name + "--" + tempReg);
                                // 添加写回内存的指令 sw
                                var stack = new Memory(-vReg.getStackLocation(), 1); // 临时栈
                                RISCInstruction lwInst = new LwInstruction(tempReg, stack); // 存入溢出的值
                                RISCInstruction swInst = new SwInstruction(tempReg, stack); // 写回溢出值
                                if (vReg.getSpillTime() < position) {
                                    riscInstList.add(instIndex + 1, swInst);
                                    riscInstList.add(instIndex, lwInst); // 之前存过才需要这个
                                    instIndex += 1; // 跳过加的指令
                                } else {
                                    riscInstList.add(instIndex + 1, swInst);
                                }
//                                System.out.println(swInst.emit());

                            } else {
                                // 没有空闲，需要临时替换一个，保存里面的值再替换回去
                                // 在同一指令中，直接从第一个寄存器开始递增
                                // 但是，注意不能使用这个指令中要用的其他寄存器
                                // TODO:没判断内存中是否存在虚拟寄存器的情况，先当作没有，有再debug
                                for (int opIndexIn = 0; opIndexIn < operandList.size(); opIndexIn++) {
                                    var riscOpIn = operandList.get(opIndexIn);
                                    if (riscOpIn.isVirtualRegister()) {
                                        VirtualRegister vRegIn = (VirtualRegister) riscOpIn;
                                        if (tempIdFromZero == vRegIn.getRealReg()) {
                                            tempIdFromZero++;
                                            opIndexIn = 0;
                                        }
                                    }
                                }

                                var tempReg = new RealRegister(tempIdFromZero++, 11);
                                if (!listHasReg(nowRealRegList, tempReg))
                                    nowRealRegList.add(tempReg); // 加入当前在用的寄存器
//                                System.out.println(tempIdFromZero);

                                var tempStack = new Memory(-riscFunc.stackIndex, 1); // 临时栈
                                riscFunc.stackIndex += 4; // 开辟出临时保存寄存器值的位置
//                                System.out.println("开辟了新的栈 " + riscFunc.stackIndex);
                                if (riscFunc.stackSize < riscFunc.stackIndex)
                                    riscFunc.stackSize = riscFunc.stackIndex; // 容量是否需要更新
                                var spillStack = new Memory(-vReg.getStackLocation(), 1); // 之前溢出保存的栈
                                RISCInstruction inst1 = new SwInstruction(tempReg, tempStack); // 保存原值
                                RISCInstruction inst2 = new LwInstruction(tempReg, spillStack); // 存入溢出的值

                                RISCInstruction inst3 = new SwInstruction(tempReg, spillStack); // 写回溢出值
                                RISCInstruction inst4 = new LwInstruction(tempReg, tempStack); // 恢复原值

                                riscInst.setOpLocal(tempReg, opIndex, opPosition); // 当前指令
                                nameMapReg.put(name, tempReg); // 记录替换成了哪个
//                                System.out.println("put" + name + "--" + tempReg);

                                riscInstList.add(instIndex + 1, inst4);
                                riscInstList.add(instIndex + 1, inst3);
                                if (vReg.getSpillTime() < position) {
                                    riscInstList.add(instIndex, inst2); // 之前存过才需要这个
                                    riscInstList.add(instIndex, inst1);
                                    instIndex += 2; // 跳过加在前面的指令
                                } else {
                                    riscInstList.add(instIndex, inst1);
                                    instIndex += 1; // 跳过加在前面的指令
                                }


                            }
                        }
                    }
//                        for (RISCOperand op : operandList) {
//                            System.out.println(op.emit() + " " + op.getPosition());
//                        }
//                        System.out.println(opPosition);
//                        System.out.println("---------------------" + riscInst.emit());
                }

                // 找return位置，修改return的上面3条有关栈空间的指令
                if (instIndex == riscInstList.size() - 1 && riscInst.type == RISCInstruction.ITYPE.jr) {
                    if (riscFunc.stackSize % 16 == 12) {
                        RISCInstruction ldraInst = riscInstList.get(instIndex - 3);
                        Memory raMemory = (Memory) ldraInst.getOperandAt(1);
                        raMemory.setOffset(riscFunc.stackSize - 8 + 4);

                        RISCInstruction lds0Inst = riscInstList.get(instIndex - 2);
                        Memory s0Memory = (Memory) lds0Inst.getOperandAt(1);
                        s0Memory.setOffset(riscFunc.stackSize - 16 + 4);

                        RISCInstruction addiInst = riscInstList.get(instIndex - 1);
                        Immediate addiMemory = (Immediate) addiInst.getOperandAt(2);
                        addiMemory.setVal(riscFunc.stackSize + 4);
                    } else if (riscFunc.stackSize % 16 == 8) {
                        RISCInstruction ldraInst = riscInstList.get(instIndex - 3);
                        Memory raMemory = (Memory) ldraInst.getOperandAt(1);
                        raMemory.setOffset(riscFunc.stackSize - 8 + 8);

                        RISCInstruction lds0Inst = riscInstList.get(instIndex - 2);
                        Memory s0Memory = (Memory) lds0Inst.getOperandAt(1);
                        s0Memory.setOffset(riscFunc.stackSize - 16 + 8);

                        RISCInstruction addiInst = riscInstList.get(instIndex - 1);
                        Immediate addiMemory = (Immediate) addiInst.getOperandAt(2);
                        addiMemory.setVal(riscFunc.stackSize + 8);
                    } else if (riscFunc.stackSize % 16 == 4) {
                        RISCInstruction ldraInst = riscInstList.get(instIndex - 3);
                        Memory raMemory = (Memory) ldraInst.getOperandAt(1);
                        raMemory.setOffset(riscFunc.stackSize - 8 + 12);

                        RISCInstruction lds0Inst = riscInstList.get(instIndex - 2);
                        Memory s0Memory = (Memory) lds0Inst.getOperandAt(1);
                        s0Memory.setOffset(riscFunc.stackSize - 16 + 12);

                        RISCInstruction addiInst = riscInstList.get(instIndex - 1);
                        Immediate addiMemory = (Immediate) addiInst.getOperandAt(2);
                        addiMemory.setVal(riscFunc.stackSize + 12);
                    } else {
                        RISCInstruction ldraInst = riscInstList.get(instIndex - 3);
                        Memory raMemory = (Memory) ldraInst.getOperandAt(1);
                        raMemory.setOffset(riscFunc.stackSize - 8);

                        RISCInstruction lds0Inst = riscInstList.get(instIndex - 2);
                        Memory s0Memory = (Memory) lds0Inst.getOperandAt(1);
                        s0Memory.setOffset(riscFunc.stackSize - 16);

                        RISCInstruction addiInst = riscInstList.get(instIndex - 1);
                        Immediate addiMemory = (Immediate) addiInst.getOperandAt(2);
                        addiMemory.setVal(riscFunc.stackSize);
                    }

                }

                // 找call指令，处理寄存器的保存
                if (riscInst.type == RISCInstruction.ITYPE.call) {
                    // TODO:记录当前时刻用到的寄存器
                    for (RealRegister reg : nowRealRegList) {
                        var tempStack = new Memory(-riscFunc.stackIndex, 1); // 临时栈
                        riscFunc.stackIndex += 4; // 开辟出临时保存寄存器值的位置
//                                System.out.println("开辟了新的栈 " + riscFunc.stackIndex);
                        if (riscFunc.stackSize < riscFunc.stackIndex)
                            riscFunc.stackSize = riscFunc.stackIndex; // 容量是否需要更新
                        RISCInstruction inst1 = new SwInstruction(reg, tempStack); // 保存原值

                        RISCInstruction inst2 = new LwInstruction(reg, tempStack); // 恢复原值

                        riscInstList.add(instIndex, inst1);
                        instIndex += 1; // 跳过加在前面的指令
                        riscInstList.add(instIndex + 1, inst2);
                    }
                }

                riscFunc.stackIndex = tempStackIndex; // 恢复栈的位置
            }
        }

        // 修改函数的前4条指令
        var firstInstList = riscBBList.get(0).getInstructionList();

        if (riscFunc.stackSize % 16 == 12) {
            RISCInstruction addispInst = firstInstList.get(0);
            Immediate addisp = (Immediate) addispInst.getOperandAt(2);
            addisp.setVal(-riscFunc.stackSize - 4);

            RISCInstruction ldraInst = firstInstList.get(1);
            Memory raMemory = (Memory) ldraInst.getOperandAt(1);
            raMemory.setOffset(riscFunc.stackSize - 8 + 4);

            RISCInstruction lds0Inst = firstInstList.get(2);
            Memory s0Memory = (Memory) lds0Inst.getOperandAt(1);
            s0Memory.setOffset(riscFunc.stackSize - 16 + 4);

            RISCInstruction addis0Inst = firstInstList.get(3);
            Immediate addis0 = (Immediate) addis0Inst.getOperandAt(2);
            addis0.setVal(riscFunc.stackSize + 4);
        } else if (riscFunc.stackSize % 16 == 8) {
            RISCInstruction addispInst = firstInstList.get(0);
            Immediate addisp = (Immediate) addispInst.getOperandAt(2);
            addisp.setVal(-riscFunc.stackSize - 8);

            RISCInstruction ldraInst = firstInstList.get(1);
            Memory raMemory = (Memory) ldraInst.getOperandAt(1);
            raMemory.setOffset(riscFunc.stackSize - 8 + 8);

            RISCInstruction lds0Inst = firstInstList.get(2);
            Memory s0Memory = (Memory) lds0Inst.getOperandAt(1);
            s0Memory.setOffset(riscFunc.stackSize - 16 + 8);

            RISCInstruction addis0Inst = firstInstList.get(3);
            Immediate addis0 = (Immediate) addis0Inst.getOperandAt(2);
            addis0.setVal(riscFunc.stackSize + 8);
        } else if (riscFunc.stackSize % 16 == 4) {
            RISCInstruction addispInst = firstInstList.get(0);
            Immediate addisp = (Immediate) addispInst.getOperandAt(2);
            addisp.setVal(-riscFunc.stackSize - 12);

            RISCInstruction ldraInst = firstInstList.get(1);
            Memory raMemory = (Memory) ldraInst.getOperandAt(1);
            raMemory.setOffset(riscFunc.stackSize - 8 + 12);

            RISCInstruction lds0Inst = firstInstList.get(2);
            Memory s0Memory = (Memory) lds0Inst.getOperandAt(1);
            s0Memory.setOffset(riscFunc.stackSize - 16 + 12);

            RISCInstruction addis0Inst = firstInstList.get(3);
            Immediate addis0 = (Immediate) addis0Inst.getOperandAt(2);
            addis0.setVal(riscFunc.stackSize + 12);
        } else {
            RISCInstruction addispInst = firstInstList.get(0);
            Immediate addisp = (Immediate) addispInst.getOperandAt(2);
            addisp.setVal(-riscFunc.stackSize);

            RISCInstruction ldraInst = firstInstList.get(1);
            Memory raMemory = (Memory) ldraInst.getOperandAt(1);
            raMemory.setOffset(riscFunc.stackSize - 8);

            RISCInstruction lds0Inst = firstInstList.get(2);
            Memory s0Memory = (Memory) lds0Inst.getOperandAt(1);
            s0Memory.setOffset(riscFunc.stackSize - 16);

            RISCInstruction addis0Inst = firstInstList.get(3);
            Immediate addis0 = (Immediate) addis0Inst.getOperandAt(2);
            addis0.setVal(riscFunc.stackSize);
        }

    }

    private boolean listHasReg(ArrayList<RealRegister> list, RealRegister tempReg) {
        for (RealRegister reg : list) {
            if (tempReg.regType == reg.regType) return true;
        }
        return false;
    }


}
