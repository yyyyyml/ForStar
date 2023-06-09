package backend;

import backend.instructions.*;
import backend.operands.*;
import ir.Instruction;
import ir.Type;
import ir.Value;
import ir.types.ArrayType;
import ir.types.PointerType;
import ir.values.BasicBlock;
import ir.values.Constant;
import ir.values.Function;
import ir.values.GlobalVariable;
import util.IList;

import java.util.HashMap;
import java.util.LinkedList;

public class RISCBasicBlock {
    public int parameterStackCount = 0;
    private LinkedList<RISCInstruction> instructionList = new LinkedList<>();
    private Function irFunction;
    private RISCFunction riscFunction;
    private String blockName;
    public Boolean isEndblock = false;
    public HashMap<Value, Instruction> condInstructionMap = new HashMap<>();


    public String getBlockName() {
        return blockName;
    }

    /**
     * 此构造函数为创建函数的初始块和结束块
     */
    public RISCBasicBlock(int i, Function irFunc, RISCFunction riscFunction) {
        this.riscFunction = riscFunction;
        this.irFunction = irFunc;
        //初始块
        if (i == 0) {
//            System.out.println(riscFunction.funcName + "\t" + "localindex=" + riscFunction.localStackIndex);
            int imm = 0;
            imm = riscFunction.localStackIndex + riscFunction.operandStackCounts * 8;
            imm = imm + (16 - imm % 16) % 16;
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
//            System.out.println(riscFunction.funcName + "\t" + "localindex=" + riscFunction.localStackIndex);
            isEndblock = true;
            int imm = 0;
            imm = riscFunction.localStackIndex + riscFunction.operandStackCounts * 8;
            imm = imm + (16 - imm % 16) % 16;
            //System.out.println(riscFunction.funcName+"\t"+riscFunction.operandStackCounts);
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
        this.blockName = irFunc.getName() + irBB.getName();
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
                case BR -> translateBr(curInst);
                case NE -> translateCond(curInst);
                case FNE -> translateCond(curInst);
                case GEP -> translateGep(curInst);

            }

        }
    }

    //数组
    private void translateGep(Instruction curInst) {
        //System.out.println(curInst);
        int num = curInst.getNumOP();
        if (num == 3) {
            int totalSize = ((ArrayType) ((PointerType) curInst.getOperandAt(0).getType()).getPointedType()).getTotalSize();
            int selfSize = ((ArrayType) ((PointerType) curInst.getOperandAt(0).getType()).getPointedType()).getSize();
            int containedSize = totalSize / selfSize;

//        System.out.println("Tsize="+totalSize);
//        System.out.println("Ssize="+selfSize);
            Value vop1 = curInst.getOperandAt(0);
            Value vop2 = curInst.getOperandAt(1);
            Value vop3 = curInst.getOperandAt(2);

            RISCOperand basicAddress = getOperand(vop1);

            if (basicAddress instanceof Memory) {
                if (vop3 instanceof Constant.ConstantInt) {
                    int index = ((Constant.ConstantInt) vop3).getVal();
                    int addIndex = index * containedSize * 4;
                    int newAddress = ((Memory) basicAddress).getOffset() + addIndex;
                    Memory mem = new Memory(newAddress, ((Memory) basicAddress).basicAddress);

                    if (riscFunction.funcParameters.containsKey(curInst)) {
                        RISCOperand dst = getOperand(curInst);
                        System.out.println("dst=   " + dst.emit());

                        if (dst instanceof RealRegister) {

                            AddiInstruction addi = new AddiInstruction((Register) dst, ((Memory) basicAddress).basicAddress, new Immediate(newAddress));
                            instructionList.add(addi);

                        } else if (dst instanceof Memory) {
                            VirtualRegister vr = getNewVr();
                            AddiInstruction addi = new AddiInstruction(vr, ((Memory) basicAddress).basicAddress, new Immediate(newAddress));
                            instructionList.add(addi);
                            SwInstruction sw = new SwInstruction(vr, dst);
                            instructionList.add(sw);
                        }
                    }
                    riscFunction.valueRISCOperandHashMap.put(curInst, mem);
                } else {

                }
//                System.out.println(curInst);
//                System.out.println(mem.emit());
            } else {
                System.out.println("BUG is " + curInst);
            }
        } else if (num == 2) {
            Value vop1 = curInst.getOperandAt(0);
            Value vop2 = curInst.getOperandAt(1);


            RISCOperand basicAddress = getOperand(vop1);

            if (basicAddress instanceof Memory) {

                int index = ((Constant.ConstantInt) vop2).getVal();
                int addIndex = index * 4;
                int newAddress = ((Memory) basicAddress).getOffset() + addIndex;
                Memory mem = new Memory(newAddress, ((Memory) basicAddress).basicAddress);

                if (riscFunction.funcParameters.containsKey(curInst)) {
                    RISCOperand dst = getOperand(curInst);
                    System.out.println("dst=   " + dst.emit());

                    if (dst instanceof RealRegister) {

                        AddiInstruction addi = new AddiInstruction((Register) dst, ((Memory) basicAddress).basicAddress, new Immediate(newAddress));
                        instructionList.add(addi);

                    } else if (dst instanceof Memory) {
                        VirtualRegister vr = getNewVr();
                        AddiInstruction addi = new AddiInstruction(vr, ((Memory) basicAddress).basicAddress, new Immediate(newAddress));
                        instructionList.add(addi);
                        SwInstruction sw = new SwInstruction(vr, dst);
                        instructionList.add(sw);
                    }
                }
                riscFunction.valueRISCOperandHashMap.put(curInst, mem);
                System.out.println(curInst);
                System.out.println(mem.emit());
            } else {
                System.out.println("BUG is " + curInst);
            }
        }

    }

    //控制流NE和FNE为两数相减，若两数相等，结果为0
    private void translateCond(Instruction curInst) {
        condInstructionMap.put(curInst, curInst);
//        Value vop1 = curInst.getOperandAt(0);
//        RISCOperand op1 = getOperand(vop1);
//        RISCOperand temp1 = null;
//        if (op1 instanceof Memory) {
//            temp1 = new VirtualRegister(riscFunction.virtualRegisterIndex++);
//            LwInstruction lw1 = new LwInstruction(temp1, op1);
//            instructionList.add(lw1);
//        } else if (op1 instanceof Register) {
//            temp1 = op1;
//            //if(riscFunction.valueVRMap.get(vop1) == (VirtualRegister) op1) {riscFunction.valueVRMap.remove(vop1);}
//
//        } else if (op1 instanceof Immediate) {
//            VirtualRegister vr = getNewVr();
//            LiInstruction li = new LiInstruction(vr, op1);
//            instructionList.add(li);
//            temp1 = vr;
//        }
//
//        Value vop2 = curInst.getOperandAt(1);
//        RISCOperand op2 = getOperand(vop2);
//        RISCOperand temp2 = null;
//        if (op2 instanceof Memory) {
//            temp2 = new VirtualRegister(riscFunction.virtualRegisterIndex++, vop2);
//            //riscFunction.valueVRMap.put(vop2,(VirtualRegister) temp2);
//            LwInstruction lw2 = new LwInstruction(temp2, op2);
//            instructionList.add(lw2);
//        } else if (op2 instanceof Register) {
//            temp2 = op2;
//        } else if (op2 instanceof Immediate) {
//            VirtualRegister vr = getNewVr();
//            LiInstruction li = new LiInstruction(vr, op2);
//            instructionList.add(li);
//            temp2 = vr;
//        }
//
//        RISCOperand dst = getOperand(curInst);
//
//        if (curInst.getTag() == Instruction.TAG.NE) {
//            SubwInstruction cal = new SubwInstruction(dst, temp1, temp2);
//            instructionList.add(cal);
//        } else if (curInst.getTag() == Instruction.TAG.FNE) {
//            FsubInstruction cal = new FsubInstruction(dst, temp1, temp2);
//            instructionList.add(cal);
//        }
//
//        riscFunction.valueRISCOperandHashMap.put(curInst,dst);
//
////        if (temp1 instanceof VirtualRegister) {
////            riscFunction.valueVRMap.put(curInst, (VirtualRegister) temp1);
////        } else if (temp1 instanceof FloatVirtualRegister) {
////            riscFunction.valueFloatVrMap.put(curInst, (FloatVirtualRegister) temp1);
////        }
////        if (riscFunction.funcParameters.containsKey(curInst)) {
////            MvInstruction mv = new MvInstruction(dst, temp1);
////            instructionList.add(mv);
////        }
    }

    //控制流，使用bne reg，zero，label实现
    private void translateBr(Instruction curInst) {
        int paraCount = curInst.getNumOP();
        if (paraCount == 1) {
            Value v = curInst.getOperandAt(0);
            StringBuffer vName = new StringBuffer(v.getName());
            //vName.deleteCharAt(0);
            String vN = new String(vName);
            MyString ms = new MyString(".B" + irFunction.getName() + vN);
            JInstruction j = new JInstruction(ms);
            instructionList.add(j);
        } else if (paraCount == 3) {
            Value vcond = curInst.getOperandAt(0);


            if (vcond instanceof Constant) {
                if (((Constant.ConstantInt) vcond).getVal() >= 1) {
                    Value v = curInst.getOperandAt(1);
                    StringBuffer vName = new StringBuffer(v.getName());
                    String vN = new String(vName);
                    MyString ms = new MyString(".B" + irFunction.getName() + vN);
                    JInstruction j = new JInstruction(ms);
                    instructionList.add(j);
                } else {
                    Value v = curInst.getOperandAt(2);
                    StringBuffer vName = new StringBuffer(v.getName());
                    String vN = new String(vName);
                    MyString ms = new MyString(".B" + irFunction.getName() + vN);
                    JInstruction j = new JInstruction(ms);
                    instructionList.add(j);
                }
            } else {
//                if(!condInstructionMap.containsKey(vcond)){
//                    System.out.println(curInst);
//                    return;
//                }
                Instruction cond = (Instruction) vcond;
                Value vop1 = cond.getOperandAt(0);
                Value vop2 = cond.getOperandAt(1);

                //获得存放两个数的RISCOperand
                RISCOperand op1 = getOperand(vop1);
                RISCOperand temp1 = null;
                if (op1 instanceof Memory) {
                    temp1 = new VirtualRegister(riscFunction.virtualRegisterIndex++);
                    LwInstruction lw1 = new LwInstruction(temp1, op1);
                    instructionList.add(lw1);
                } else if (op1 instanceof Register) {
                    temp1 = op1;

                } else if (op1 instanceof Immediate) {
                    VirtualRegister vr = getNewVr();
                    LiInstruction li = new LiInstruction(vr, op1);
                    instructionList.add(li);
                    temp1 = vr;
                }

                RISCOperand op2 = getOperand(vop2);
                RISCOperand temp2 = null;
                if (op2 instanceof Memory) {
                    temp2 = new VirtualRegister(riscFunction.virtualRegisterIndex++, vop2);
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

                //获取跳转地址
                Value v1 = curInst.getOperandAt(1);
                StringBuffer vName1 = new StringBuffer(v1.getName());
                String vN1 = new String(vName1);
                MyString dst1 = new MyString(".B" + irFunction.getName() + vN1);

                Value v2 = curInst.getOperandAt(2);
                StringBuffer vName2 = new StringBuffer(v2.getName());
                String vN2 = new String(vName2);
                MyString dst2 = new MyString(".B" + irFunction.getName() + vN2);

                switch (cond.getTag()) {
                    case EQ -> {
                        BeqInstruction b = new BeqInstruction(temp1, temp2, dst1);
                        instructionList.add(b);
                    }
                    case NE -> {
                        BneInstruction b = new BneInstruction(temp1, temp2, dst1);
                        instructionList.add(b);
                    }
                    case LT -> {
                        BltInstruction b = new BltInstruction(temp1, temp2, dst1);
                        instructionList.add(b);
                    }
                    case LE -> {
                        BleInstruction b = new BleInstruction(temp1, temp2, dst1);
                        instructionList.add(b);
                    }
                    case GT -> {
                        BgtInstruction b = new BgtInstruction(temp1, temp2, dst1);
                        instructionList.add(b);
                    }
                    case GE -> {
                        BgeInstruction b = new BgeInstruction(temp1, temp2, dst1);
                        instructionList.add(b);
                    }
                    case FEQ -> {
                        VirtualRegister vr = getNewVr();
                        FeqInstruction f = new FeqInstruction(vr, temp1, temp2);
                        instructionList.add(f);
                        BeqInstruction b = new BeqInstruction(vr, new RealRegister(0), dst1);
                        instructionList.add(b);
                    }
                    case FNE -> {
                        VirtualRegister vr = getNewVr();
                        FneInstruction f = new FneInstruction(vr, temp1, temp2);
                        instructionList.add(f);
                        BeqInstruction b = new BeqInstruction(vr, new RealRegister(0), dst1);
                        instructionList.add(b);
                    }
                    case FLT -> {
                        VirtualRegister vr = getNewVr();
                        FltInstruction f = new FltInstruction(vr, temp1, temp2);
                        instructionList.add(f);
                        BeqInstruction b = new BeqInstruction(vr, new RealRegister(0), dst1);
                        instructionList.add(b);
                    }
                    case FLE -> {
                        VirtualRegister vr = getNewVr();
                        FleInstruction f = new FleInstruction(vr, temp1, temp2);
                        instructionList.add(f);
                        BeqInstruction b = new BeqInstruction(vr, new RealRegister(0), dst1);
                        instructionList.add(b);
                    }
                    case FGT -> {
                        VirtualRegister vr = getNewVr();
                        FgtInstruction f = new FgtInstruction(vr, temp1, temp2);
                        instructionList.add(f);
                        BeqInstruction b = new BeqInstruction(vr, new RealRegister(0), dst1);
                        instructionList.add(b);
                    }
                    case FGE -> {
                        VirtualRegister vr = getNewVr();
                        FgeInstruction f = new FgeInstruction(vr, temp1, temp2);
                        instructionList.add(f);
                        BeqInstruction b = new BeqInstruction(vr, new RealRegister(0), dst1);
                        instructionList.add(b);
                    }

                }

                JInstruction j = new JInstruction(dst2);
                instructionList.add(j);
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



        Value vop1 = curInst.getOperandAt(0);
        RISCOperand op1 = getOperand(vop1);
        System.out.println(vop1.getName() + " -> " + op1.emit());
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
        System.out.println(vop2.getName() + " -> " + op2.emit());
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

        RISCOperand dst = getOperand(curInst);

        switch (curInst.getTag()) {
            case ADD -> {
                AddwInstruction cal = new AddwInstruction(dst, temp1, temp2);
                instructionList.add(cal);
            }
            case SUB -> {
                SubwInstruction cal = new SubwInstruction(dst, temp1, temp2);
                instructionList.add(cal);
            }
            case MUL -> {
                MulwInstruction cal = new MulwInstruction(dst, temp1, temp2);
                instructionList.add(cal);
            }
            case DIV -> {
                DivwInstruction cal = new DivwInstruction(dst, temp1, temp2);
                instructionList.add(cal);
            }
            case FADD -> {
                FaddInstruction cal = new FaddInstruction(dst, temp1, temp2);
                instructionList.add(cal);
            }
            case FSUB -> {
                FsubInstruction cal = new FsubInstruction(dst, temp1, temp2);
                instructionList.add(cal);
            }
            case FMUL -> {
                FmulInstruction cal = new FmulInstruction(dst, temp1, temp2);
                instructionList.add(cal);
            }
            case FDIV -> {
                FdivInstruction cal = new FdivInstruction(dst, temp1, temp2);
                instructionList.add(cal);
            }
        }
        riscFunction.valueRISCOperandHashMap.put(curInst, dst);



//        if (temp1 instanceof VirtualRegister) {
//            riscFunction.valueVRMap.remove(vop1);
//            riscFunction.valueVRMap.put(curInst, (VirtualRegister) temp1);
//        } else if (temp1 instanceof FloatVirtualRegister) {
//            riscFunction.valueFloatVrMap.remove(vop1);
//            riscFunction.valueFloatVrMap.put(curInst, (FloatVirtualRegister) temp1);
//        }
//        if (riscFunction.funcParameters.containsKey(curInst)) {
//            RISCOperand dst = getOperand(curInst);
//            MvInstruction mv = new MvInstruction(dst, temp1);
//            instructionList.add(mv);
//        }

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
            riscFunction.valueRISCOperandHashMap.put(curInst, dst);
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
            riscFunction.valueRISCOperandHashMap.put(curInst, dst);
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

        Memory mem;
        if (((PointerType) curInst.getType()).getPointedType().isArrayType()) {
            int tsize = ((ArrayType) ((PointerType) curInst.getType()).getPointedType()).getTotalSize();

            riscFunction.localStackIndex += (tsize * 4);
            //System.out.println(riscFunction.localStackIndex);
            mem = new Memory(-riscFunction.localStackIndex, 1);
        } else {
            riscFunction.localStackIndex += 4;
            mem = new Memory(-riscFunction.localStackIndex, 1);
        }
        System.out.println(curInst + " " + mem.emit());

        riscFunction.valueRISCOperandHashMap.put(curInst, mem);

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

        riscFunction.addEndBlock = true;

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
        } else if (riscFunction.valueRISCOperandHashMap.containsKey(value)) {
            return riscFunction.valueRISCOperandHashMap.get(value);
        }
        //判断为call中的参数
        else if (riscFunction.funcParameters.containsKey(value)) {
            int opeIndex = riscFunction.funcParameters.get(value);
            if (opeIndex < 8) {
                if (value.getType().isIntegerType() || value.getType().isPointerType()) {
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
        }
        //判断为本函数的参数
        else if (riscFunction.myfuncParameters.containsKey(value)) {
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
            } else {
                FloatVirtualRegister fvr = new FloatVirtualRegister(riscFunction.floatVirtualRegisterIndex++);

                return fvr;
            }
        } else {
            if (value instanceof Constant) {
                int val = ((Constant.ConstantInt) value).getVal();
                RISCOperand temp = new Immediate(val);
                return temp;
            } else {
                VirtualRegister vr = new VirtualRegister(riscFunction.virtualRegisterIndex++, value);
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
