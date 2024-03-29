package pass.backend.register_allocator;

import backend.*;
import backend.instructions.LdInstruction;
import backend.instructions.SdInstruction;
import backend.operands.Memory;
import backend.operands.RealRegister;
import backend.operands.VirtualRegister;
import pass.backend.BaseBackendPass;

import java.util.*;


/**
 * 简单的线性扫描寄存器分配
 * 目前改成了以函数为单位的
 */
public class NewRegAllocator implements BaseBackendPass {

    public boolean lessRegSave = true;

    private HashMap<Integer, LiveInterval> liveIntervalMapList;
    private List<Map.Entry<Integer, LiveInterval>> sortedLiveIntervalList;
    private List<Map.Entry<Integer, LiveInterval>> activeList;
    private HashMap<Integer, VirtualRegister> intMapVreg;
    private final int regNum;

    private RegisterUsageTracker regUsageTracker;
    private int time = 0;
    private RegisterUsage curRegUsage;
    private ArrayList<RISCFunction> time2Function; // 时间点对应的函数，其实现在这个没什么用了，因为以函数为单位了，但是不改了

    private int index;
    private int minStart;
    private int maxEnd;


    public NewRegAllocator() {
        liveIntervalMapList = new HashMap<>();
        activeList = new ArrayList<>();
        intMapVreg = new HashMap<>(); // 编号对应的虚拟寄存器对象
        time2Function = new ArrayList<>();
        regNum = 14;
    }

    // 寄存器第一次出现，设置Start，并放进Map
    public void setLiveIntervalStart(int virtualRegister, int start) {
        LiveInterval interval = new LiveInterval(start, start);
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
            index = 0; // 用于记录位置，存到live interval中


            // 初始化LiveInterval
            initializeLiveInterval(riscFunc);

            // 线性扫描
            linearScan();

//            // 打印寄存器分配情况
//            for (Map.Entry<Integer, RegisterUsage> entry : regUsageTracker.getRegisterUsageMap().entrySet()) {
//                int timePoint = entry.getKey();
//                RegisterUsage registerUsage = entry.getValue();
//
//                System.out.print("Time Point: " + timePoint + " | ");
//                for (int register = 0; register < registerUsage.getRegNum(); register++) {
//                    boolean isUsed = registerUsage.isRegisterUsed(register);
//                    System.out.print(register + ": " + (isUsed ? "√" : "x") + " | ");
//                }
//                System.out.println();
//            }

            // 生成新的MIR
            renameRegister(riscFunc);
        }


    }


    private void dfs(RISCBasicBlock riscBB, RISCFunction riscFunc) {
        if (riscBB.visitedLive) {
            return;
        }
        riscBB.visitedLive = true;
        LinkedList<RISCInstruction> riscInstList = riscBB.getInstructionList();
        for (RISCInstruction riscInst : riscInstList) {
            time2Function.add(index, riscFunc);
            riscInst.setId(index); // 记录每条指令的标号
            System.out.println(riscBB.getBlockName() + "的第一个标号" + riscBB.firstId);
            LinkedList<RISCOperand> operandList = riscInst.getOperandList();
            int opIndex = 0;
            for (RISCOperand riscOp : operandList) {

                if (riscOp.isVirtualRegister()) {
//                    System.out.println("有虚拟寄存器要处理"+riscOp.emit()+"位置是"+opIndex);
                    var name = ((VirtualRegister) riscOp).getName();
                    intMapVreg.put(name, (VirtualRegister) riscOp);
                    if (!liveIntervalMapList.containsKey(name)) {
//                        System.out.println("操作数"+opIndex+"第一次出现在"+riscInst.emit());
                        // 记录Start
                        setLiveIntervalStart(name, index);

                    } else {
                        if (riscInst.isDef(opIndex)) {
                            // 又出现了定义点，希望进行dfs，看会不会回到前面的块的更小的start点
                            Visitor visitor = new Visitor();
                            minStart = liveIntervalMapList.get(name).getStart(); // 初始化为当前start
                            maxEnd = 0;
                            System.out.println("操作数" + opIndex + "出现了定义点" + riscInst.emit());
                            dfsDef(riscBB, visitor, liveIntervalMapList.get(name).getEnd());
                            setLiveIntervalStart(name, minStart);
                            setLiveIntervalEnd(name, maxEnd);
                        } else {
                            // 记录End
                            setLiveIntervalEnd(name, index + 1);
                        }


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
                opIndex += 1;
            }
            index += 1;
        }

        // Recursively process successors in depth-first manner
        for (RISCBasicBlock succ : riscBB.nextlist) {
            dfs(succ, riscFunc);
        }

    }

    private void dfsFirst(RISCBasicBlock riscBB, Visitor visitor) {
        if (visitor.isVisited(riscBB)) {
            return;
        }
        visitor.markVisited(riscBB);

        riscBB.firstId = index; // 记录块的起始点
        LinkedList<RISCInstruction> riscInstList = riscBB.getInstructionList();
        for (RISCInstruction riscInst : riscInstList) {
            riscInst.setId(index); // 记录每条指令的标号
            index += 1;
        }

        riscBB.lastId = index - 1; // 记录块的结束点
        for (RISCBasicBlock nextBB : riscBB.nextlist) {
            dfsFirst(nextBB, visitor);
        }

    }

    private void dfsDef(RISCBasicBlock riscBB, Visitor visitor, int curMaxEnd) {
        if (visitor.isVisited(riscBB)) {
            return;
        }
        visitor.markVisited(riscBB);
//        System.out.println("dfsDef:"+riscBB.getBlockName()+":"+riscBB.firstId+"-curMaxEnd:"+curMaxEnd);
        if (riscBB.firstId < minStart) {
            System.out.println("minStart改变：" + minStart + "->" + riscBB.firstId);
            System.out.println("curMaxEnd改变：" + maxEnd + "->" + Math.max(maxEnd, curMaxEnd));
            minStart = riscBB.firstId;
            maxEnd = Math.max(maxEnd, curMaxEnd); // 说明这条支路能回来，路上经过的maxEnd可能要更新
        }
        for (RISCBasicBlock nextBB : riscBB.nextlist) {
            dfsDef(nextBB, visitor, Math.max(nextBB.lastId, curMaxEnd));
        }


    }

    private void initializeLiveInterval(RISCFunction riscFunc) {


        // 先遍历一遍 记录变量的live interval
        riscFunc.stackSize = riscFunc.localStackIndex + 16;
        riscFunc.stackIndex = riscFunc.localStackIndex + 16;
        // System.out.println("zhan----------------------" + riscFunc.stackSize);

        System.out.println(riscFunc.stackSize);
        LinkedList<RISCBasicBlock> riscBBList = riscFunc.getBasicBlockList();
        Visitor visitor = new Visitor();

        index = 0;
        for (RISCBasicBlock riscBB : riscBBList)
            dfsFirst(riscBB, visitor);// 先记录一下id


        index = 0;
        for (RISCBasicBlock riscBB : riscBBList)
            dfs(riscBB, riscFunc);


        sortedLiveIntervalList = sortByStart();
        for (Map.Entry<Integer, LiveInterval> entry : sortedLiveIntervalList) {
            System.out.println("Register: vr_" + entry.getKey().toString());
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
                regUsageTracker.add(freeRegister, entry.getValue());//直接分所有时间的
                System.out.println("Time: " + time + " Allocating register: vr_" + entry.getKey() + " -> " + freeRegister);
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
            // 删掉之前的寄存器分配记录
//            regUsageTracker.delete(spillVreg.getRealReg(), spillEntry.getValue());
            regUsageTracker.add(spillVreg.getRealReg(), curEntry.getValue());
            // 分配栈地址
            var curFunc = time2Function.get(time);
            spillVreg.setStackLocation(curFunc.stackIndex);
            System.out.println("Time: " + time + " Spilling: vr_" + spillEntry.getKey() + " -> stack " + curFunc.stackIndex);
            System.out.println("Time: " + time + " Allocating register: vr_" + curEntry.getKey() + " -> " + spillVreg.getRealReg());
            curFunc.stackSize += 8;
            curFunc.stackIndex += 8;
            // 记录spillTime
            spillVreg.setSpillTime(0);
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
            System.out.println("Time: " + time + " Spilling: vr_" + curEntry.getKey() + " -> stack " + curFunc.stackIndex);
            curFunc.stackSize += 8;
            curFunc.stackIndex += 8;
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
            System.out.println("Time: " + time + " Freeing register: " + reg.getRealReg());
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
                boolean is13TempEmpty = true;
                boolean is14TempEmpty = true;
                int tempIdFromZero = 0;
                int tempStackIndex = riscFunc.stackIndex; // 保存这条指令分配临时栈空间之前的栈位置，用于恢复
                boolean[] visitVReg = new boolean[1000010]; // 这条指令中visit过哪些虚拟寄存器
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
                                System.out.println("put" + name + "--" + newReg.emit());

                            } else {
                                // 说明这个变量一直在栈中
                                // 需要暂时替换一个寄存器，用完后再换回
                                var curRegUsage = regUsageTracker.getPreRegisterUsage(position);
                                int tempRegID;
                                if (is14TempEmpty) {
                                    tempRegID = 15;
                                    is14TempEmpty = false;
                                } else if (is13TempEmpty) {
                                    tempRegID = 14;
                                    is13TempEmpty = false;
                                } else {
                                    tempRegID = curRegUsage.getNextFreeRegister();
                                }

                                if (tempRegID != -1) {
                                    // 有空闲，分配成功
                                    RealRegister tempReg = new RealRegister(tempRegID, 11);
                                    if (!listHasReg(nowRealRegList, tempReg))
                                        nowRealRegList.add(tempReg); // 加入当前在用的寄存器
                                    // 替换内存的basicAddress
                                    mem.basicAddress = tempReg;
                                    // 记录替换成了哪个
                                    nameMapReg.put(name, tempReg);
                                    System.out.println("put" + name + "--" + tempReg.emit());
                                    // 添加写回内存的指令 sd
                                    var stack = new Memory(-vReg.getStackLocation(), 1); // 临时栈
                                    RISCInstruction ldInst = new LdInstruction(tempReg, stack); // 存入溢出的值
                                    ldInst.setId(-2);
                                    RISCInstruction sdInst = new SdInstruction(tempReg, stack); // 写回溢出值
                                    sdInst.setId(-2);
                                    if (vReg.getSpillTime() < position) {
                                        riscInstList.add(instIndex + 1, sdInst);
                                        riscInstList.add(instIndex, ldInst); // 之前存过才需要这个
                                        instIndex += 1; // 跳过加的指令
                                    } else {
                                        riscInstList.add(instIndex + 1, sdInst);
                                    }
//                                    System.out.println(sdInst.emit());

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
                                    riscFunc.stackIndex += 8; // 开辟出临时保存寄存器值的位置
//                                    System.out.println("开辟了新的栈 " + riscFunc.stackIndex);
                                    if (riscFunc.stackSize < riscFunc.stackIndex)
                                        riscFunc.stackSize = riscFunc.stackIndex; // 容量是否需要更新
                                    var tempStack = new Memory(-riscFunc.stackIndex, 1); // 临时栈
                                    var tempStack_same = new Memory(-riscFunc.stackIndex, 1); // 临时栈

                                    var spillStack = new Memory(-vReg.getStackLocation(), 1); // 之前溢出保存的栈
                                    var spillStack_same = new Memory(-vReg.getStackLocation(), 1); // 之前溢出保存的栈
                                    RISCInstruction inst1 = new SdInstruction(tempReg, tempStack); // 保存原值
                                    RISCInstruction inst2 = new LdInstruction(tempReg, spillStack); // 存入溢出的值

                                    RISCInstruction inst3 = new SdInstruction(tempReg, spillStack_same); // 写回溢出值
                                    RISCInstruction inst4 = new LdInstruction(tempReg, tempStack_same); // 恢复原值

                                    inst1.setId(-2);
                                    inst2.setId(-2);
                                    inst3.setId(-2);
                                    inst4.setId(-2);

                                    mem.basicAddress = tempReg; // 当前指令
                                    nameMapReg.put(name, tempReg); // 记录替换成了哪个
                                    System.out.println("put" + name + "--" + tempReg.emit());

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
                            System.out.println("put" + name + "--" + newReg.emit());

                        } else {
                            // 说明这个变量一直在栈中
                            // 需要暂时替换一个寄存器，用完后再换回
                            var curRegUsage = regUsageTracker.getPreRegisterUsage(position);
                            int tempRegID;
                            if (is14TempEmpty) {
                                tempRegID = 15;
                                is14TempEmpty = false;
                            } else if (is13TempEmpty) {
                                tempRegID = 14;
                                is13TempEmpty = false;
                            } else {
                                tempRegID = curRegUsage.getNextFreeRegister();
                            }

                            if (tempRegID != -1) {
                                // 有空闲，分配成功
                                System.out.println("有空闲，空闲的是：" + tempRegID + "  当前位置：" + riscInst.emit());
                                RealRegister tempReg = new RealRegister(tempRegID, 11);
                                if (!listHasReg(nowRealRegList, tempReg))
                                    nowRealRegList.add(tempReg); // 加入当前在用的寄存器
                                // 替换操作数
                                riscInst.setOpLocal(tempReg, opIndex, opPosition);
                                // 记录替换成了哪个
                                nameMapReg.put(name, tempReg);
                                System.out.println("put" + name + "--" + tempReg.emit());
                                // 添加写回内存的指令 sd
                                var stack = new Memory(-vReg.getStackLocation(), 1); // 临时栈
                                RISCInstruction ldInst = new LdInstruction(tempReg, stack); // 存入溢出的值
                                RISCInstruction sdInst = new SdInstruction(tempReg, stack); // 写回溢出值
                                ldInst.setId(-2);
                                sdInst.setId(-2);
                                if (vReg.getSpillTime() < position) {
                                    riscInstList.add(instIndex + 1, sdInst);
                                    riscInstList.add(instIndex, ldInst); // 之前存过才需要这个
                                    instIndex += 1; // 跳过加的指令
                                } else {
                                    riscInstList.add(instIndex + 1, sdInst);
                                }
//                                System.out.println(sdInst.emit());

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
                                riscFunc.stackIndex += 8; // 开辟出临时保存寄存器值的位置
//                                System.out.println("开辟了新的栈 " + riscFunc.stackIndex);
                                if (riscFunc.stackSize < riscFunc.stackIndex)
                                    riscFunc.stackSize = riscFunc.stackIndex; // 容量是否需要更新
                                var tempStack = new Memory(-riscFunc.stackIndex, 1); // 临时栈
                                var tempStack_same = new Memory(-riscFunc.stackIndex, 1); // 临时栈

                                var spillStack = new Memory(-vReg.getStackLocation(), 1); // 之前溢出保存的栈
                                var spillStack_same = new Memory(-vReg.getStackLocation(), 1); // 之前溢出保存的栈
                                RISCInstruction inst1 = new SdInstruction(tempReg, tempStack); // 保存原值
                                RISCInstruction inst2 = new LdInstruction(tempReg, spillStack); // 存入溢出的值

                                RISCInstruction inst3 = new SdInstruction(tempReg, spillStack_same); // 写回溢出值
                                RISCInstruction inst4 = new LdInstruction(tempReg, tempStack_same); // 恢复原值

                                inst1.setId(-2);
                                inst2.setId(-2);
                                inst3.setId(-2);
                                inst4.setId(-2);

                                riscInst.setOpLocal(tempReg, opIndex, opPosition); // 当前指令
                                nameMapReg.put(name, tempReg); // 记录替换成了哪个
                                System.out.println("put" + name + "--" + tempReg.emit());

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
                }
                // 找call指令，处理寄存器的保存
                if (riscInst.type == RISCInstruction.ITYPE.call) {
                    //
                    if (lessRegSave) { //可以保存更少的寄存器，但是TLE了一个？
                        var curRegUsage = regUsageTracker.getPreRegisterUsage(position);
                        for (int i = 0; i < curRegUsage.getRegNum(); i++) {
                            if (curRegUsage.isRegisterUsed(i)) {
                                RealRegister reg = new RealRegister(i, 11);
                                riscFunc.stackIndex += 8; // 开辟出临时保存寄存器值的位置
//                                System.out.println("开辟了新的栈 " + riscFunc.stackIndex);
                                if (riscFunc.stackSize < riscFunc.stackIndex)
                                    riscFunc.stackSize = riscFunc.stackIndex; // 容量是否需要更新
                                var tempStack = new Memory(-riscFunc.stackIndex, 1); // 临时栈
                                var tempStack_same = new Memory(-riscFunc.stackIndex, 1); // 临时栈

                                RISCInstruction inst1 = new SdInstruction(reg, tempStack); // 保存原值

                                RISCInstruction inst2 = new LdInstruction(reg, tempStack_same); // 恢复原值

                                inst1.setId(-2);
                                inst2.setId(-2);

                                riscInstList.add(instIndex, inst1);
                                instIndex += 1; // 跳过加在前面的指令
                                riscInstList.add(instIndex + 1, inst2);
                            }
                        }
                    } else {
                        for (RealRegister reg : nowRealRegList) {
                            riscFunc.stackIndex += 8; // 开辟出临时保存寄存器值的位置
//                                System.out.println("开辟了新的栈 " + riscFunc.stackIndex);
                            if (riscFunc.stackSize < riscFunc.stackIndex)
                                riscFunc.stackSize = riscFunc.stackIndex; // 容量是否需要更新
                            var tempStack = new Memory(-riscFunc.stackIndex, 1); // 临时栈
                            var tempStack_same = new Memory(-riscFunc.stackIndex, 1); // 临时栈

                            RISCInstruction inst1 = new SdInstruction(reg, tempStack); // 保存原值

                            RISCInstruction inst2 = new LdInstruction(reg, tempStack_same); // 恢复原值

                            inst1.setId(-2);
                            inst2.setId(-2);

                            riscInstList.add(instIndex, inst1);
                            instIndex += 1; // 跳过加在前面的指令
                            riscInstList.add(instIndex + 1, inst2);
                        }
                    }


                }

                riscFunc.stackIndex = tempStackIndex; // 恢复栈的位置

            }
        }


    }

    private boolean listHasReg(ArrayList<RealRegister> list, RealRegister tempReg) {
        return true;
//        for (RealRegister reg : list) {
//            if (tempReg.regType == reg.regType) return true;
//        }
//        return false;
    }


}
