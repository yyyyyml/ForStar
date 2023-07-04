package backend;

import backend.instructions.*;
import backend.operands.*;
import ir.Instruction;
import ir.Type;
import ir.Value;
import ir.values.BasicBlock;
import ir.values.Constant;
import ir.values.Function;
import ir.values.GlobalVariable;
import util.IList;

import java.util.LinkedList;

public class RISCBasicBlock {
    public int parameterStackCount = 0;
    private LinkedList<RISCInstruction> instructionList = new LinkedList<>();
    private Function irFunction;
    private RISCFunction riscFunction;

    /**
     * 此构造函数为创建函数的初始块和结束块
     */
    public RISCBasicBlock(int i, Function irFunc, RISCFunction riscFunction) {
        this.riscFunction = riscFunction;
        this.irFunction = irFunc;
        //初始块
        if (i == 0) {
            int imm = 0;
            imm = riscFunction.localStackIndex + riscFunction.operandStackCounts * 8;
            AddiInstruction addi1 = new AddiInstruction(new RealRegister(2), new RealRegister(2), new Immediate(-imm));
            instructionList.add(addi1);
            SdInstruction sd1 = new SdInstruction(new RealRegister(3), new Memory(imm - 8, 2));
            instructionList.add(sd1);
            SdInstruction sd2 = new SdInstruction(new RealRegister(1), new Memory(imm - 16, 2));
            instructionList.add(sd2);
            AddiInstruction addi2 = new AddiInstruction(new RealRegister(1), new RealRegister(2), new Immediate(imm));
            instructionList.add(addi2);

        }
        //结束块
        else {
            int imm = 0;
            imm = riscFunction.localStackIndex;
            LdInstruction ld1 = new LdInstruction(new RealRegister(3), new Memory(imm - 8, 2));
            instructionList.add(ld1);
            LdInstruction ld2 = new LdInstruction(new RealRegister(1), new Memory(imm - 16, 2));
            instructionList.add(ld2);
            AddiInstruction add1 = new AddiInstruction(new RealRegister(2), new RealRegister(2), new Immediate(imm));
            instructionList.add(add1);
            JrInstruction jr1 = new JrInstruction(new RealRegister(3));
            instructionList.add(jr1);
        }
    }


    /**
     * 遍历BB中的指令
     * 转换为Risc-V
     *
     * @param irBB
     */
    public RISCBasicBlock(BasicBlock irBB, Function irFunc, RISCFunction riscFunc) {
        this.riscFunction = riscFunc;
        this.irFunction = irFunc;
        for (IList.INode<Instruction, BasicBlock> Inode : irBB.list) {

            Instruction curInst = Inode.getElement();
            switch (curInst.getTag()) {
                case RET -> translateRet(curInst);
                case ALLOCA -> translateAlloca(curInst);
                case STORE -> translateStore(curInst);
                case LOAD -> translateLoad(curInst);
                case ADD -> translateCaculate(curInst);
                case SUB -> translateCaculate(curInst);
                case MUL -> translateCaculate(curInst);
                case DIV -> translateCaculate(curInst);
                case FADD -> translateCaculate(curInst);
                case FSUB -> translateCaculate(curInst);
                case FMUL -> translateCaculate(curInst);
                case FDIV -> translateCaculate(curInst);
                case CALL -> translateCall(curInst);
            }

        }
    }

    private void translateCall(Instruction curInst) {
        int paraCount = curInst.getNumOP() - 1;
        for (int i = 0; i <= paraCount; i++) {
            Value v = curInst.getOperandAt(i);
            if (v instanceof Constant) {
                RISCOperand dst = getOperand(v);
                if (v.getType().isIntegerType()) {
                    int val = ((Constant.ConstantInt) v).getVal();
                    RISCOperand temp = new Immediate(val);
                    if (dst instanceof Register) {
                        LiInstruction li = new LiInstruction(dst, temp);
                        instructionList.add(li);
                    } else if (dst instanceof Memory) {
                        VirtualRegister vr = getNewVr();
                        LiInstruction li = new LiInstruction(vr, temp);
                        instructionList.add(li);
                        SwInstruction sw = new SwInstruction(vr, dst);
                        instructionList.add(sw);
                    }

                } else if (v.getType().isFloatType()) {
                    Float f = ((Constant.ConstantFloat) v).getVal();
                    //进下面这个函数找浮点数，没有会生成
                    String fbName = riscFunction.riscModule.getFloatBlockName(f);
                    VirtualRegister vr = getNewVr();
                    MyString ms = new MyString(fbName);
                    LlaInstruction lla = new LlaInstruction(vr, ms);
                    instructionList.add(lla);
                    Memory mem = new Memory(0, vr);
                    FloatVirtualRegister fvr = getNewFvr();
                    FlwInstruction flw = new FlwInstruction(fvr, mem);
                    instructionList.add(flw);
                    if (dst instanceof Register) {
                        FmvInstruction fmv = new FmvInstruction(dst, fvr);
                        instructionList.add(fmv);
                    } else if (dst instanceof Memory) {
                        FsdInstruction fsd = new FsdInstruction(fvr, dst);
                        instructionList.add(fsd);
                    }
                }
            }
        }
        String funcName = curInst.getOperandAt(0).getName();
        CallInstruction call1 = new CallInstruction(funcName);
        instructionList.add(call1);
        //以下操作为将a0（函数返回值）和ir中的虚拟寄存器进行对应
        RealRegister a0 = new RealRegister(allocAReg(0));
        //删除原先hashmap中的对应关系
        if (riscFunction.valueRISCOperandHashMap.containsValue(a0)) {
            Value key = riscFunction.riscOperandValueHashMap.get(a0);
            riscFunction.valueRISCOperandHashMap.remove(key);

        }
        riscFunction.valueRISCOperandHashMap.put(curInst, a0);
        riscFunction.riscOperandValueHashMap.put(a0, curInst);

    }

    public LinkedList<RISCInstruction> getInstructionList() {
        return instructionList;
    }

    private void translateCaculate(Instruction curInst) {

        boolean needVr = false;

        Value vop1 = curInst.getOperandAt(0);
        RISCOperand op1 = getOperand(vop1);
        RISCOperand temp1 = null;
        if (op1 instanceof Memory) {
            temp1 = new VirtualRegister(riscFunction.virtualRegisterIndex++);
            LwInstruction lw1 = new LwInstruction(temp1, op1);
            instructionList.add(lw1);
        } else if (op1 instanceof Register) {
            temp1 = op1;
            //if(riscFunction.valueVRMap.get(vop1) == (VirtualRegister) op1) {riscFunction.valueVRMap.remove(vop1);}

        } else if (op1 instanceof Immediate) {
            VirtualRegister vr = getNewVr();
            LiInstruction li = new LiInstruction(vr, op1);
            instructionList.add(li);
            temp1 = vr;
        }

        Value vop2 = curInst.getOperandAt(1);
        RISCOperand op2 = getOperand(vop2);
        RISCOperand temp2 = null;
        if (op2 instanceof Memory) {
            temp2 = new VirtualRegister(riscFunction.virtualRegisterIndex++, vop2);
            //riscFunction.valueVRMap.put(vop2,(VirtualRegister) temp2);
            LwInstruction lw2 = new LwInstruction(temp2, op2);
            instructionList.add(lw2);
        } else if (op2 instanceof Register) {
            temp2 = op2;
        } else if (op2 instanceof Immediate) {
            VirtualRegister vr = getNewVr();
            LiInstruction li = new LiInstruction(vr, op2);
            instructionList.add(li);
            temp2 = vr;
        }

        switch (curInst.getTag()) {
            case ADD -> {
                AddwInstruction cal = new AddwInstruction(temp1, temp1, temp2);
                instructionList.add(cal);
            }
            case SUB -> {
                SubwInstruction cal = new SubwInstruction(temp1, temp1, temp2);
                instructionList.add(cal);
            }
            case MUL -> {
                MulwInstruction cal = new MulwInstruction(temp1, temp1, temp2);
                instructionList.add(cal);
            }
            case DIV -> {
                DivwInstruction cal = new DivwInstruction(temp1, temp1, temp2);
                instructionList.add(cal);
            }
            case FADD -> {
                FaddInstruction cal = new FaddInstruction(temp1, temp1, temp2);
                instructionList.add(cal);
            }
            case FSUB -> {
                FsubInstruction cal = new FsubInstruction(temp1, temp1, temp2);
                instructionList.add(cal);
            }
            case FMUL -> {
                FmulInstruction cal = new FmulInstruction(temp1, temp1, temp2);
                instructionList.add(cal);
            }
            case FDIV -> {
                FdivInstruction cal = new FdivInstruction(temp1, temp1, temp2);
                instructionList.add(cal);
            }
        }
        RISCOperand dst = getOperand(curInst);
        if (temp1 instanceof VirtualRegister) {
            riscFunction.valueVRMap.put(curInst, (VirtualRegister) temp1);
        } else if (temp1 instanceof FloatVirtualRegister) {
            riscFunction.valueFloatVrMap.put(curInst, (FloatVirtualRegister) temp1);
        }
        if (riscFunction.funcParameters.containsKey(curInst)) {

            MvInstruction mv = new MvInstruction(dst, temp1);
            instructionList.add(mv);
        }

    }



    private void translateLoad(Instruction curInst) {
        //提前取出dst判断是否为寄存器
        RISCOperand Tdst = getOperand(curInst);

        if (curInst.getType() == Type.IntegerType.getType()) {
            Value op1 = curInst.getOperandAt(0);
            RISCOperand src = getOperand(op1);
            RISCOperand dst;
            if (Tdst instanceof Memory) {
                dst = getNewVr();
            } else {
                dst = Tdst;
            }
            if (src instanceof Memory) {
                LwInstruction lw1 = new LwInstruction(dst, src);
                instructionList.add(lw1);
            } else if (src instanceof Register) {
                MvInstruction mv1 = new MvInstruction(dst, src);
                instructionList.add(mv1);
            }
            if (Tdst instanceof Memory) {
                SwInstruction sw1 = new SwInstruction(dst, Tdst);
                instructionList.add(sw1);
            }
        } else if (curInst.getType() == Type.FloatType.getType()) {
            Value op1 = curInst.getOperandAt(0);
            RISCOperand src = getOperand(op1);
            //判断Tdst是否为mem
            RISCOperand dst;
            if (Tdst instanceof Memory) {
                dst = getNewFvr();
            } else {
                dst = Tdst;
            }
            if (src instanceof Memory) {
                FlwInstruction flw1 = new FlwInstruction(dst, src);
                instructionList.add(flw1);
            } else if (src instanceof Register) {
                ;
            }
            if (Tdst instanceof Memory) {
                FswInstruction fsw1 = new FswInstruction(dst, Tdst);
                instructionList.add(fsw1);
            }
        }


        //弃用
//        //判断为传递参数的load
//        else if (false) {
//            Value op1 = curInst.getOperandAt(0);
//            RISCOperand src = getOperand(op1);
//            if (operandIndex < 8) {
//                RISCOperand dst = new RealRegister(allocAReg(operandIndex++));
//                if (src instanceof Memory) {
//                    LwInstruction lw1 = new LwInstruction(dst, src);
//                    instructionList.add(lw1);
//                } else if (src instanceof Register) {
//                    MvInstruction mv1 = new MvInstruction(dst, src);
//                    instructionList.add(mv1);
//                }
//
//            } else {
//                RISCOperand dst = new Memory((operandIndex - 8) * 8, 2);
//
//                SdInstruction sd1 = new SdInstruction(src, dst);
//                instructionList.add(sd1);
//                riscFunction.operandStackCounts = Math.max(operandIndex - 8, riscFunction.operandStackCounts);
//                operandIndex++;
//            }
//
//        }
    }

    private void translateStore(Instruction curInst) {
        Value op1 = curInst.getOperandAt(0);
        Value op2 = curInst.getOperandAt(1);
        RISCOperand rop1 = getOperand(op1);
        RISCOperand rop2 = getOperand(op2);
        if (op1 instanceof Constant.ConstantInt) {
            VirtualRegister vr = new VirtualRegister(riscFunction.virtualRegisterIndex++);
            LiInstruction li1 = new LiInstruction(vr, rop1);
            instructionList.add(li1);
            SwInstruction sw1 = new SwInstruction(vr, rop2);
            instructionList.add(sw1);
        } else if (rop1 instanceof Register) {
            if (op1.getType() == Type.IntegerType.getType()) {
                SwInstruction sw1 = new SwInstruction(rop1, rop2);
                instructionList.add(sw1);
            } else if (op1.getType() == Type.FloatType.getType()) {
                FswInstruction fsw = new FswInstruction(rop1, rop2);
                instructionList.add(fsw);
            }
        } else if (rop1 instanceof Memory) {
            if (op1.getType() == Type.IntegerType.getType()) {
                VirtualRegister vr = getNewVr();
                LwInstruction lw1 = new LwInstruction(vr, rop1);
                instructionList.add(lw1);
                SwInstruction sw = new SwInstruction(vr, rop2);
                instructionList.add(sw);
            } else if (op1.getType() == Type.FloatType.getType()) {
                FloatVirtualRegister fvr = getNewFvr();
                FlwInstruction flw = new FlwInstruction(fvr, rop1);
                instructionList.add(flw);
                FswInstruction fsw = new FswInstruction(fvr, rop2);
                instructionList.add(fsw);
            }
        }
    }

    private void translateAlloca(Instruction curInst) {

            Memory mem = new Memory(-riscFunction.localStackIndex, 1);
            riscFunction.localStackIndex += 4;
            riscFunction.valueMemoryHashMap.put(curInst, mem);

    }

    /**
     * RET
     *
     * @param curInst
     */
    private void translateRet(Instruction curInst) {
        if (curInst.getNumOP() == 0) {
            NopInstruction nop1 = new NopInstruction();
            instructionList.add(nop1);
        } else if (curInst.getOperandAt(0).getType().isIntegerType()) {
            RISCOperand op1 = getOperand(curInst.getOperandAt(0));
            if (op1 instanceof Immediate) {
                LiInstruction li1 = new LiInstruction(new RealRegister(9), op1);
                instructionList.add(li1);
            } else if (op1 instanceof Register) {
                MvInstruction mv1 = new MvInstruction(new RealRegister(9), op1);
                instructionList.add(mv1);
            } else if (op1 instanceof Memory) {
                LwInstruction lw1 = new LwInstruction(new RealRegister(9), op1);
                instructionList.add(lw1);
            }

        } else {
            RISCOperand src = getOperand(curInst.getOperandAt(0));
            FmvInstruction fmv1 = new FmvInstruction(new FloatRealRegister(10), src);
            instructionList.add(fmv1);

        }

    }

    private RISCOperand getOperand(Value value) {

        StringBuffer vName = new StringBuffer(value.getName());
        vName.deleteCharAt(0);
        String valueName = new String(vName);
        //判断为全局变量
        if (value instanceof GlobalVariable) {
            if (riscFunction.riscModule.GlobalVarMap.containsKey((GlobalVariable) value)) {
                VirtualRegister vr = getNewVr();
                MyString ms = new MyString(value.getName().substring(1));
                LlaInstruction lla = new LlaInstruction(vr, ms);
                instructionList.add(lla);
                return new Memory(0, vr);
            } else {
                throw new RuntimeException("哈哈哈哈哈，你又出BUG了\n");
            }
        }
        //判断为call中的参数
        else if (riscFunction.funcParameters.containsKey(value)) {
            int opeIndex = riscFunction.funcParameters.get(value);
            if (opeIndex < 8) {
                if (value.getType().isIntegerType()) {
                    RISCOperand reg = new RealRegister(allocAReg(opeIndex));
                    return reg;
                } else {
                    //fa0 = f10
                    RISCOperand reg = new FloatRealRegister(opeIndex + 10);
                    return reg;
                }
            }
            //超出部分放栈里
            else {
                RISCOperand mem = new Memory((opeIndex - 8) * 8, 2);
                return mem;
            }
        } else if (riscFunction.myfuncParameters.containsKey(value)) {
            int myPIndex = riscFunction.myfuncParameters.get(value);
            if (myPIndex < 8) {
                if (value.getType().isIntegerType()) {
                    RISCOperand reg = new RealRegister(allocAReg(myPIndex));
                    return reg;
                } else {
                    //fa0 = f10
                    RISCOperand reg = new FloatRealRegister(myPIndex + 10);
                    return reg;
                }
            }
            //超出部分放栈里
            else {
                RISCOperand mem = new Memory((myPIndex - 8) * 8, 2);
                return mem;
            }
        } else if (value.getType().isFloatType()) {
            if (value instanceof Constant) {
                Float f = ((Constant.ConstantFloat) value).getVal();
                //进下面这个函数找浮点数，没有会生成
                String fbName = riscFunction.riscModule.getFloatBlockName(f);
                VirtualRegister vr = getNewVr();
                MyString ms = new MyString(fbName);
                LlaInstruction lla = new LlaInstruction(vr, ms);
                instructionList.add(lla);
                Memory mem = new Memory(0, vr);
                FloatVirtualRegister fvr = getNewFvr();
                FlwInstruction flw = new FlwInstruction(fvr, mem);
                instructionList.add(flw);
                return fvr;
            }
            if (riscFunction.valueFloatVrMap.containsKey(value)) {
                return riscFunction.valueFloatVrMap.get(value);
            } else {
                FloatVirtualRegister fvr = new FloatVirtualRegister(riscFunction.floatVirtualRegisterIndex++);
                riscFunction.valueFloatVrMap.put(value, fvr);
                return fvr;
            }
        } else {
            if (value instanceof Constant) {
                int val = ((Constant.ConstantInt) value).getVal();
                RISCOperand temp = new Immediate(val);
                return temp;
            } else if (riscFunction.valueVRMap.containsKey(value)) {
                return riscFunction.valueVRMap.get(value);
            } else if (riscFunction.valueMemoryHashMap.containsKey(value)) {
                return riscFunction.valueMemoryHashMap.get(value);
            } else if (riscFunction.valueRISCOperandHashMap.containsKey(value)) {
                return riscFunction.valueRISCOperandHashMap.get(value);
            }
//            else if (Integer.parseInt(valueName) < riscFunction.parameterSize) {
//
//                int vname = Integer.parseInt(valueName);
//                System.out.println(vname);
//                if (vname <= 8) {
//                    RealRegister reg = new RealRegister(allocAReg(vname));
//                    return reg;
//                } else {
//                    VirtualRegister vr = new VirtualRegister(riscFunction.virtualRegisterIndex++);
//                    LdInstruction ld1 = new LdInstruction(vr, new Memory(((8 - vname) * 8), 2));
//                    instructionList.add(ld1);
//                    riscFunction.valueVRMap.put(value, vr);
//                    return vr;
//                }
//            }
            else {
                VirtualRegister vr = new VirtualRegister(riscFunction.virtualRegisterIndex++, value);
                riscFunction.valueVRMap.put(value, vr);
                return vr;
            }
        }


    }

    private int allocAReg(int vname) {
        return 9 + vname;
    }

    private VirtualRegister getNewVr() {
        VirtualRegister vr = new VirtualRegister(riscFunction.virtualRegisterIndex++);
        return vr;
    }

    public FloatVirtualRegister getNewFvr() {
        FloatVirtualRegister fvr = new FloatVirtualRegister(riscFunction.floatVirtualRegisterIndex++);
        return fvr;
    }
}
