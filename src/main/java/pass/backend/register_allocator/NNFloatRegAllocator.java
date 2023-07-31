package pass.backend.register_allocator;

import backend.*;
import backend.instructions.FldInstruction;
import backend.instructions.FsdInstruction;
import backend.operands.FloatRealRegister;
import backend.operands.FloatVirtualRegister;
import backend.operands.Immediate;
import backend.operands.Memory;
import pass.backend.BaseBackendPass;

import java.util.*;


/**
 * 简单的线性扫描寄存器分配
 * 目前改成了以函数为单位的
 * 溢出的一直在栈里，使用两个临时寄存器，使用活跃变量分析
 */
public class NNFloatRegAllocator implements BaseBackendPass {

    public boolean lessRegSave = true;

    private HashMap<Integer, LiveInterval> liveIntervalMapList;
    private List<Map.Entry<Integer, LiveInterval>> sortedLiveIntervalList;
    private List<Map.Entry<Integer, LiveInterval>> activeList;
    private HashMap<Integer, FloatVirtualRegister> intMapVreg;
    private final int regNum;

    private RegisterUsageTracker regUsageTracker;
    private int time = 0;
    private RegisterUsage curRegUsage;
    private RISCFunction curFunction;

    // 活跃变量分析用的
    private Map<RISCBasicBlock, Set<Integer>> def;
    private Map<RISCBasicBlock, Set<Integer>> use;
    private Map<RISCBasicBlock, Set<Integer>> in;
    private Map<RISCBasicBlock, Set<Integer>> out;
    private Map<Integer, Set<RISCBasicBlock>> activeBlocksMap;

    public NNFloatRegAllocator() {
        liveIntervalMapList = new HashMap<>();
        activeList = new ArrayList<>();
        intMapVreg = new HashMap<>(); // 编号对应的虚拟寄存器对象
        regNum = 22;
        def = new HashMap<>();
        use = new HashMap<>();
        in = new HashMap<>();
        out = new HashMap<>();
        activeBlocksMap = new HashMap<>();
    }

    // 寄存器第一次出现，设置Start，并放进Map
    public void setLiveIntervalStart(int FloatVirtualRegister, int start) {
        LiveInterval interval = new LiveInterval(start, start);
        addLiveInterval(FloatVirtualRegister, interval);
    }

    // Map中已有这个寄存器，直接修改它的End
    public void setLiveIntervalEnd(int FloatVirtualRegister, int end) {
        LiveInterval interval = liveIntervalMapList.get(FloatVirtualRegister);
        interval.setEnd(end);
    }

    public void updateLiveInterval(int virtualRegister, int start, int end) {
        // 检查map中是否已经存在该虚拟寄存器的LiveInterval
        if (liveIntervalMapList.containsKey(virtualRegister)) {
            LiveInterval interval = liveIntervalMapList.get(virtualRegister);

            // 取并集，更新LiveInterval的起始和结束位置
            interval.setStart(Math.min(interval.getStart(), start));
            interval.setEnd(Math.max(interval.getEnd(), end));
        } else {
            // 如果map中还没有该虚拟寄存器的LiveInterval，则直接创建一个新的并添加到map中
            LiveInterval interval = new LiveInterval(start, end);
            liveIntervalMapList.put(virtualRegister, interval);
        }
    }

    /**
     * 添加寄存器的LiveInterval到Map中
     *
     * @param FloatVirtualRegister 虚拟寄存器
     * @param interval             寄存器的LiveInterval
     */
    public void addLiveInterval(int FloatVirtualRegister, LiveInterval interval) {
        liveIntervalMapList.put(FloatVirtualRegister, interval);
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
            if (riscFunc.isBuildIn) continue;
            // 先清空这些结构
            liveIntervalMapList.clear();
            activeList.clear();
            intMapVreg.clear();
            regUsageTracker = new RegisterUsageTracker(regNum);

            def.clear();
            use.clear();
            in.clear();
            out.clear();
            activeBlocksMap.clear();
            curFunction = riscFunc;

            // 初始化def use
            fisrtProcess(riscFunc);
            // 活跃变量分析
            livenessAnalysis(riscFunc);
            // 得到每个寄存器的活跃基本块
            activeBlocksForVariable(riscFunc);
            // 输出活跃基本块信息
//            printActiveBlocksForVariable(activeBlocksMap);


            // 初始化LiveInterval
            initializeLiveInterval(riscFunc);

            // 线性扫描
            linearScan();

            // 生成新的MIR
            renameRegister(riscFunc);

            updateStackSize(riscFunc);

            // 打印寄存器分配情况
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
        }


    }

    private void fisrtProcess(RISCFunction riscFunc) {

        LinkedList<RISCBasicBlock> riscBBList = riscFunc.getBasicBlockList();
        for (RISCBasicBlock riscBB : riscBBList) {
            var curDefSet = new HashSet<Integer>();
            var curUseSet = new HashSet<Integer>();
            LinkedList<RISCInstruction> riscInstList = riscBB.getInstructionList();
            for (RISCInstruction riscInst : riscInstList) {

                LinkedList<RISCOperand> operandList = riscInst.getOperandList();
                int opIndex = 0;
                int defOpName = -1;
                for (RISCOperand riscOp : operandList) {
                    if (riscOp.isFloatVirtualRegister()) {
                        var name = ((FloatVirtualRegister) riscOp).getName();
                        intMapVreg.put(name, (FloatVirtualRegister) riscOp);
                        if (riscInst.isDef(opIndex)) {
                            // 定义点，前面如果没有使用点则加入def
                            if (!curUseSet.contains(name)) {
                                curDefSet.add(name);
                            }
                        } else {
                            // 使用点，前面如果没有定义点则加入use i=i+1?同时加入使用点和定义点
                            if (!curDefSet.contains(name) || name == defOpName) {
                                curUseSet.add(name);
                                curDefSet.remove(name);
                            }
                        }

                    } else if (riscOp.isMemory()) {
                        var mem = (Memory) riscOp;
                        if (mem.basicAddress.isFloatVirtualRegister()) {
                            var name = ((FloatVirtualRegister) mem.basicAddress).getName();
                            intMapVreg.put(name, (FloatVirtualRegister) mem.basicAddress);
                            // 使用点，前面如果没有定义点则加入use i=i+1?
                            if (!curDefSet.contains(name)) {
                                curUseSet.add(name);
                            }

                        }
                    }
                    opIndex += 1;
                }
            }
            def.put(riscBB, curDefSet);
            use.put(riscBB, curUseSet);
        }
//        for (RISCBasicBlock riscBB : riscBBList) {
//            System.out.println("Basic Block: " + riscBB.getBlockName());
//
//            System.out.println("Def:");
//            for (Integer defVar : def.get(riscBB)) {
//                System.out.println("  vr_f" + defVar);
//            }
//
//            System.out.println("Use:");
//            for (Integer useVar : use.get(riscBB)) {
//                System.out.println("  vr_f" + useVar);
//            }
//
//            System.out.println("--------------------");
//        }
    }

    private void activeBlocksForVariable(RISCFunction riscFunc) {
        // 遍历每个基本块B
        for (RISCBasicBlock riscBB : riscFunc.getBasicBlockList()) {
            // 在IN[B]集合中的所有活跃变量
            Set<Integer> activeInBB = in.get(riscBB);
            for (int variable : activeInBB) {
                // 将变量添加到Map中
                activeBlocksMap.putIfAbsent(variable, new HashSet<>());
                activeBlocksMap.get(variable).add(riscBB);
                updateLiveInterval(variable, riscBB.firstId, riscBB.lastId + 1); // 在这直接更新生存周期

            }

            // 在OUT[B]集合中的所有活跃变量
            Set<Integer> activeOutBB = out.get(riscBB);
            for (int variable : activeOutBB) {
                // 将变量添加到Map中
                activeBlocksMap.putIfAbsent(variable, new HashSet<>());
                activeBlocksMap.get(variable).add(riscBB);
                updateLiveInterval(variable, riscBB.firstId, riscBB.lastId + 1); // 在这直接更新生存周期
            }
        }
    }

    private void printActiveBlocksForVariable(Map<Integer, Set<RISCBasicBlock>> activeBlocksMap) {

        // 遍历活跃块Map，打印每个变量及其对应的活跃基本块集合
//        for (Map.Entry<Integer, Set<RISCBasicBlock>> entry : activeBlocksMap.entrySet()) {
//            int variable = entry.getKey();
//            Set<RISCBasicBlock> activeBlocks = entry.getValue();
//
//            System.out.println("Variable vr_f" + variable + " is active in the following basic blocks:");
//            for (RISCBasicBlock riscBB : activeBlocks) {
//                System.out.println("  " + riscBB.getBlockName());
//            }
//            System.out.println();
//        }
    }

    private void livenessAnalysis(RISCFunction riscFunc) {
        // 初始化IN和OUT集合
        for (RISCBasicBlock riscBB : riscFunc.getBasicBlockList()) {
            in.put(riscBB, new HashSet<>());
            out.put(riscBB, new HashSet<>());
        }
        // 初始化EXIT节点的IN集合为Φ

        boolean changed = true;
        while (changed) {
            changed = false;
            // 对于除EXIT之外的每个基本块B
            for (RISCBasicBlock riscBB : riscFunc.getBasicBlockList()) {

                // 计算OUT[B] = ∪S是B的一个后继IN[S]
                Set<Integer> newOut = new HashSet<>();
                for (RISCBasicBlock succBB : riscBB.nextlist) {
                    newOut.addAll(in.get(succBB));
                }

                // 更新OUT集合
                if (!newOut.equals(out.get(riscBB))) {
                    out.put(riscBB, newOut);
                    changed = true;
                }

                // 计算IN[B] = useB ∪ (OUT[B] - defB)
                Set<Integer> newIn = new HashSet<>(use.get(riscBB));
                newIn.addAll(out.get(riscBB));
                newIn.removeAll(def.get(riscBB));

                // 更新IN集合
                if (!newIn.equals(in.get(riscBB))) {
                    in.put(riscBB, newIn);
                    changed = true;
                }
            }
        }
//        for (RISCBasicBlock riscBB : riscFunc.getBasicBlockList()) {
//            System.out.println("Basic Block: " + riscBB.getBlockName());
//
//            System.out.println("IN:");
//            for (Integer defVar : in.get(riscBB)) {
//                System.out.println("  vr_f" + defVar);
//            }
//
//            System.out.println("OUT:");
//            for (Integer useVar : out.get(riscBB)) {
//                System.out.println("  vr_f" + useVar);
//            }
//
//            System.out.println("--------------------");
//        }
    }

    private void initializeLiveInterval(RISCFunction riscFunc) {


        // 先遍历一遍 记录变量的live interval 这里用的是寄存器分配后新的stackSize
        riscFunc.stackSize += 8;
        riscFunc.stackIndex = riscFunc.stackSize;

        System.out.println(riscFunc.stackSize);
        LinkedList<RISCBasicBlock> riscBBList = riscFunc.getBasicBlockList();
        for (RISCBasicBlock riscBB : riscBBList) {
            LinkedList<RISCInstruction> riscInstList = riscBB.getInstructionList();
            int instSeq = 0; // 块内顺序
            for (RISCInstruction riscInst : riscInstList) {
                int index = riscInst.getId(); // 获取id
//                riscInst.setId(index); // 记录每条指令的标号
                LinkedList<RISCOperand> operandList = riscInst.getOperandList();
                for (RISCOperand riscOp : operandList) {
                    if (riscOp.isFloatVirtualRegister()) {
                        var name = ((FloatVirtualRegister) riscOp).getName();
                        intMapVreg.put(name, (FloatVirtualRegister) riscOp);
                        if (!activeBlocksMap.containsKey(name)) {
                            // 生存周期在同一个块内
                            if (!liveIntervalMapList.containsKey(name)) {
                                // 记录Start
                                setLiveIntervalStart(name, index);
                            } else {
                                // 记录End
                                setLiveIntervalEnd(name, index + 1);
                            }
                        }
                    } else if (riscOp.isMemory()) {
                        var mem = (Memory) riscOp;
                        if (mem.basicAddress.isFloatVirtualRegister()) {
                            var name = ((FloatVirtualRegister) mem.basicAddress).getName();
                            intMapVreg.put(name, (FloatVirtualRegister) mem.basicAddress);
                            if (!activeBlocksMap.containsKey(name)) {
                                // 生存周期在同一个块内
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
                }
            }
        }

        sortedLiveIntervalList = sortByStart();
//        for (Map.Entry<Integer, LiveInterval> entry : sortedLiveIntervalList) {
//            System.out.println("Register: vr_f" + entry.getKey().toString());
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
                regUsageTracker.add(freeRegister, entry.getValue());//直接分所有时间的
//                System.out.println("Time: " + time + " Allocating register: vr_f" + entry.getKey() + " -> " + freeRegister);
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
            regUsageTracker.delete(spillVreg.getRealReg(), spillEntry.getValue());
            regUsageTracker.add(spillVreg.getRealReg(), curEntry.getValue());
            // 分配栈地址
            var curFunc = curFunction;
            spillVreg.setStackLocation(curFunc.stackIndex);
//            System.out.println("Time: " + time + " Spilling: vr_f" + spillEntry.getKey() + " -> stack " + curFunc.stackIndex);
//            System.out.println("Time: " + time + " Allocating register: vr_f" + curEntry.getKey() + " -> " + spillVreg.getRealReg());
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
            var curFunc = curFunction;

            curVreg.setStackLocation(curFunc.stackIndex);
//            System.out.println("Time: " + time + " Spilling: vr_f" + curEntry.getKey() + " -> stack " + curFunc.stackIndex);
            curFunc.stackSize += 8;
            curFunc.stackIndex += 8;
            // 记录spillTime
            curVreg.setSpillTime(0);

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
        ArrayList<FloatRealRegister> nowRealRegList = new ArrayList<>();

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
                boolean is22TempEmpty = true;
                boolean is23TempEmpty = true;
                int tempIdFromZero = 0;
                int tempStackIndex = riscFunc.stackIndex; // 保存这条指令分配临时栈空间之前的栈位置，用于恢复
                boolean[] visitVReg = new boolean[100010]; // 这条指令中visit过哪些虚拟寄存器
                HashMap<Integer, FloatRealRegister> nameMapReg = new HashMap<>();

                for (int opIndex = 0; opIndex < operandList.size(); opIndex++) {
                    var riscOp = operandList.get(opIndex);
                    int opPosition = riscOp.getPosition();

                    if (riscOp.isMemory()) { // 在内存表示中可能会用到虚拟寄存器，所以要处理
                        var mem = (Memory) riscOp;
                        if (mem.basicAddress.isFloatVirtualRegister()) {
                            var vReg = (FloatVirtualRegister) mem.basicAddress;
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
                                var newReg = new FloatRealRegister(id + 18);
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
                                int tempRegID;
                                if (is22TempEmpty) {
                                    tempRegID = 22;
                                    is22TempEmpty = false;
                                } else if (is23TempEmpty) {
                                    tempRegID = 23;
                                    is23TempEmpty = false;
                                } else {
                                    tempRegID = curRegUsage.getNextFreeRegister();
                                }

                                if (tempRegID != -1) {
                                    // 有空闲，分配成功
                                    FloatRealRegister tempReg = new FloatRealRegister(tempRegID + 18);
                                    if (!listHasReg(nowRealRegList, tempReg))
                                        nowRealRegList.add(tempReg); // 加入当前在用的寄存器
                                    // 替换内存的basicAddress
                                    mem.basicAddress = tempReg;
                                    // 记录替换成了哪个
                                    nameMapReg.put(name, tempReg);
//                                    System.out.println("put" + name + "--" + tempReg);
                                    // 添加写回内存的指令 sd
                                    var stack = new Memory(-vReg.getStackLocation(), 1); // 临时栈
                                    RISCInstruction ldInst = new FldInstruction(tempReg, stack); // 存入溢出的值
                                    RISCInstruction sdInst = new FsdInstruction(tempReg, stack); // 写回溢出值
                                    if (riscInst.isDef(opIndex)) {
                                        // 如果是定义点，只需要用后store
                                        riscInstList.add(instIndex + 1, sdInst);
                                    } else {
                                        // 如果是使用点，只需要用前load
                                        riscInstList.add(instIndex, ldInst); // 之前存过才需要这个
                                        instIndex += 1; // 跳过加的指令
                                    }
//                                    System.out.println(sdInst.emit());

                                } else {
                                    // 没有空闲，需要临时替换一个，保存里面的值再替换回去
                                    // 在同一指令中，直接从第一个寄存器开始递增
                                    // 但是，注意不能使用这个指令中要用的其他寄存器
                                    // 应该不会有其他内存，所以不判断内存中的虚拟寄存器了，有bug再改
                                    for (int opIndexIn = 0; opIndexIn < operandList.size(); opIndexIn++) {
                                        var riscOpIn = operandList.get(opIndexIn);
                                        if (riscOpIn.isFloatVirtualRegister()) {
                                            FloatVirtualRegister vRegIn = (FloatVirtualRegister) riscOpIn;
                                            if (tempIdFromZero == vRegIn.getRealReg()) {
                                                tempIdFromZero++;
                                                opIndexIn = 0;
                                            }
                                        }
                                    }

                                    var tempReg = new FloatRealRegister(18 + tempIdFromZero++);
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
                                    RISCInstruction inst1 = new FsdInstruction(tempReg, tempStack); // 保存原值
                                    RISCInstruction inst2 = new FldInstruction(tempReg, spillStack); // 存入溢出的值

                                    RISCInstruction inst3 = new FsdInstruction(tempReg, spillStack_same); // 写回溢出值
                                    RISCInstruction inst4 = new FldInstruction(tempReg, tempStack_same); // 恢复原值

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
                    } else if (riscOp.isFloatVirtualRegister()) {
                        var vReg = (FloatVirtualRegister) riscOp;
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
                            var newReg = new FloatRealRegister(id + 18);
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
                            int tempRegID;
                            if (is22TempEmpty) {
                                tempRegID = 22;
                                is22TempEmpty = false;
                            } else if (is23TempEmpty) {
                                tempRegID = 23;
                                is23TempEmpty = false;
                            } else {
                                tempRegID = curRegUsage.getNextFreeRegister();
                            }

                            if (tempRegID != -1) {
                                // 有空闲，分配成功
                                FloatRealRegister tempReg = new FloatRealRegister(tempRegID + 18);
                                if (!listHasReg(nowRealRegList, tempReg))
                                    nowRealRegList.add(tempReg); // 加入当前在用的寄存器
                                // 替换操作数
                                riscInst.setOpLocal(tempReg, opIndex, opPosition);
                                // 记录替换成了哪个
                                nameMapReg.put(name, tempReg);
//                                System.out.println("put" + name + "--" + tempReg);
                                // 添加写回内存的指令 sd
                                var stack = new Memory(-vReg.getStackLocation(), 1); // 临时栈
                                RISCInstruction ldInst = new FldInstruction(tempReg, stack); // 存入溢出的值
                                RISCInstruction sdInst = new FsdInstruction(tempReg, stack); // 写回溢出值
                                if (riscInst.isDef(opIndex)) {
                                    // 如果是定义点，只需要用后store
                                    riscInstList.add(instIndex + 1, sdInst);
                                } else {
                                    // 如果是使用点，只需要用前load
                                    riscInstList.add(instIndex, ldInst); // 之前存过才需要这个
                                    instIndex += 1; // 跳过加的指令
                                }
//                                System.out.println(sdInst.emit());

                            } else {
                                // 没有空闲，需要临时替换一个，保存里面的值再替换回去
                                // 在同一指令中，直接从第一个寄存器开始递增
                                // 但是，注意不能使用这个指令中要用的其他寄存器
                                // TODO:没判断内存中是否存在虚拟寄存器的情况，先当作没有，有再debug
                                for (int opIndexIn = 0; opIndexIn < operandList.size(); opIndexIn++) {
                                    var riscOpIn = operandList.get(opIndexIn);
                                    if (riscOpIn.isFloatVirtualRegister()) {
                                        FloatVirtualRegister vRegIn = (FloatVirtualRegister) riscOpIn;
                                        if (tempIdFromZero == vRegIn.getRealReg()) {
                                            tempIdFromZero++;
                                            opIndexIn = 0;
                                        }
                                    }
                                }

                                var tempReg = new FloatRealRegister(18 + tempIdFromZero++);
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
                                RISCInstruction inst1 = new FsdInstruction(tempReg, tempStack); // 保存原值
                                RISCInstruction inst2 = new FldInstruction(tempReg, spillStack); // 存入溢出的值

                                RISCInstruction inst3 = new FsdInstruction(tempReg, spillStack_same); // 写回溢出值
                                RISCInstruction inst4 = new FldInstruction(tempReg, tempStack_same); // 恢复原值

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


                // 找call指令，处理寄存器的保存
                if (riscInst.type == RISCInstruction.ITYPE.call) {

                    if (lessRegSave) { //可以保存更少的寄存器，但是TLE了一个？
                        var curRegUsage = regUsageTracker.getPreRegisterUsage(position);
                        for (int i = 0; i < curRegUsage.getRegNum(); i++) {
                            if (curRegUsage.isRegisterUsed(i)) {
                                FloatRealRegister reg = new FloatRealRegister(i + 18);
                                riscFunc.stackIndex += 8; // 开辟出临时保存寄存器值的位置
//                                System.out.println("开辟了新的栈 " + riscFunc.stackIndex);
                                if (riscFunc.stackSize < riscFunc.stackIndex)
                                    riscFunc.stackSize = riscFunc.stackIndex; // 容量是否需要更新
                                var tempStack = new Memory(-riscFunc.stackIndex, 1); // 临时栈
                                var tempStack_same = new Memory(-riscFunc.stackIndex, 1); // 临时栈

                                RISCInstruction inst1 = new FsdInstruction(reg, tempStack); // 保存原值

                                RISCInstruction inst2 = new FldInstruction(reg, tempStack_same); // 恢复原值

                                riscInstList.add(instIndex, inst1);
                                instIndex += 1; // 跳过加在前面的指令
                                riscInstList.add(instIndex + 1, inst2);
                            }
                        }
                    } else {
                        for (FloatRealRegister reg : nowRealRegList) {
                            riscFunc.stackIndex += 8; // 开辟出临时保存寄存器值的位置
//                                System.out.println("开辟了新的栈 " + riscFunc.stackIndex);
                            if (riscFunc.stackSize < riscFunc.stackIndex)
                                riscFunc.stackSize = riscFunc.stackIndex; // 容量是否需要更新
                            var tempStack = new Memory(-riscFunc.stackIndex, 1); // 临时栈
                            var tempStack_same = new Memory(-riscFunc.stackIndex, 1); // 临时栈

                            RISCInstruction inst1 = new FsdInstruction(reg, tempStack); // 保存原值

                            RISCInstruction inst2 = new FldInstruction(reg, tempStack_same); // 恢复原值

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

    private boolean listHasReg(ArrayList<FloatRealRegister> list, FloatRealRegister tempReg) {
        return true;
//        for (FloatRealRegister reg : list) {
//            if (tempReg.regType == reg.regType) return true;
//        }
//        return false;
    }

    private void updateStackSize(RISCFunction riscFunc) {
        var riscBBList = riscFunc.getBasicBlockList();
        for (RISCBasicBlock riscBB : riscBBList) {

            LinkedList<RISCInstruction> riscInstList = riscBB.getInstructionList();
            for (int instIndex = 0; instIndex < riscInstList.size(); instIndex++) {
                RISCInstruction riscInst = riscInstList.get(instIndex);

                // 找return位置，修改return的上面3条有关栈空间的指令
                if (instIndex == riscInstList.size() - 1 && riscInst.type == RISCInstruction.ITYPE.jr) {
                    riscFunc.stackSize += 8 * riscFunc.operandStackCounts; // 给参数留位置
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
                    riscFunc.stackSize -= 8 * riscFunc.operandStackCounts; // 恢复


                }

            }
        }
        // 修改函数的前4条指令
        var firstInstList = riscBBList.get(0).getInstructionList();

        riscFunc.stackSize += 8 * riscFunc.operandStackCounts; // 给参数留位置
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
        riscFunc.stackSize -= 8 * riscFunc.operandStackCounts; // 恢复
    }


}
