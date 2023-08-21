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
import org.antlr.v4.runtime.misc.Pair;
import util.IList;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;

public class RISCBasicBlock {
    public int parameterStackCount = 0;
    private LinkedList<RISCInstruction> instructionList = new LinkedList<>();
    public LinkedList<Value> myParaPhi = new LinkedList<>();
    private Function irFunction;
    private RISCFunction riscFunction;
    private String blockName;
    public Boolean isEndblock = false;
    public HashMap<Value, Instruction> condInstructionMap = new HashMap<>();
    public int i;
    public boolean visitedLive = false;
    public boolean visitedLiveFloat = false;
    public boolean isPhiOld = true;
    public LinkedList<String> preBlocknameList = new LinkedList<>();
    public LinkedList<String> nextBlocknameList = new LinkedList<>();
    public LinkedList<RISCBasicBlock> prelist = new LinkedList<>();
    public LinkedList<RISCBasicBlock> nextlist = new LinkedList<>();
    public int firstId = Integer.MAX_VALUE;
    public int lastId;
    public RealRegister tempRegister = new RealRegister(13, 12);
    public FloatRealRegister floatTempRegister = new FloatRealRegister(23 + 18);
    public HashMap<Value,RISCOperand> phiVrTempMap = new HashMap<>();
    public LinkedList<Value> phiList = new LinkedList<>();
    public int firstBrposition = -1;

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
        else if (i == 1){
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
        //本块用于提前给a0-a7赋值
        else {
            isEndblock = false;
            if (irFunc.getParamList().size() != 0) {
                int intCount = 0;
                int floatCount = 0;
                int stackIndex = 8;
                for (Value v : irFunc.getParamList()) {
                    if (v.getType().isIntegerType() || v.getType().isPointerType() ) {
                        if(riscFunction.myfuncParameters.get(v) < 8 ){
                            VirtualRegister vr = getNewVr();
                            RISCOperand src = getOperand(v);
                            MvInstruction mvInstruction = new MvInstruction(vr,src);
                            instructionList.add(mvInstruction);
                            riscFunction.valueRISCOperandHashMap.put(v,vr);
                        }
                    } else if (v.getType().isFloatType() ) {
                        if(riscFunction.myfuncParameters.get(v) < 8 ){
                            FloatVirtualRegister fvr = getNewFvr();
                            RISCOperand src = getOperand(v);
                            FmvInstruction mvInstruction = new FmvInstruction(fvr,src);
                            instructionList.add(mvInstruction);
                            riscFunction.valueRISCOperandHashMap.put(v,fvr);
                        }
                    }
                }
            }
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
        this.blockName =".B"+ irFunc.getName() + irBB.getName();
        riscFunction.blockHashMap.put(blockName,this);

//        //每个块开始为phi参数中的值赋值
//        if(riscFunction.blockPhiMap.containsKey(irBB)){
//            LinkedList<Pair<Value,Value>> list = riscFunction.blockPhiMap.get(irBB);
//            for (Pair<Value,Value> pair : list){
//                Value vd = pair.a;
//                Value vs = pair.b;
//                RISCOperand dst = getOperand(vd);
//                RISCOperand src = getOperand(vs);
//                if (dst instanceof Register)
//                {
//                    if (src instanceof Immediate) {
//                        LiInstruction liInstruction = new LiInstruction(dst, src);
//                        instructionList.add(liInstruction);
//                    } else if (src instanceof FloatVirtualRegister || src instanceof FloatRealRegister) {
//                        FmvInstruction fmvInstruction = new FmvInstruction(dst, src);
//                        instructionList.add(fmvInstruction);
//                    } else if (src instanceof Memory) {
//                        LdInstruction ldInstruction = new LdInstruction(dst, src);
//                        instructionList.add(ldInstruction);
//                    } else {
//                        MvInstruction mvInstruction = new MvInstruction(dst, src);
//                        instructionList.add(mvInstruction);
//                    }
//                }
//                else if (dst instanceof Memory){
//                    if (src instanceof Immediate) {
//                        LiInstruction liInstruction = new LiInstruction(tempRegister, src);
//                        instructionList.add(liInstruction);
//                        SdInstruction sd = new SdInstruction(tempRegister,dst);
//                        instructionList.add(sd);
//                    } else if (src instanceof FloatVirtualRegister || src instanceof FloatRealRegister) {
//
//                        FsdInstruction fsdInstruction = new FsdInstruction(src,dst);
//                        instructionList.add(fsdInstruction);
//                    } else if (src instanceof Memory) {
//                        LdInstruction ldInstruction = new LdInstruction(tempRegister, src);
//                        instructionList.add(ldInstruction);
//                        SdInstruction sd = new SdInstruction(tempRegister,dst);
//                        instructionList.add(sd);
//                    } else {
//                        SdInstruction sd = new SdInstruction(src,dst);
//                        instructionList.add(sd);
//                    }
//                }
//            }
//        }

        for (IList.INode<Instruction, BasicBlock> Inode : irBB.list) {

            Instruction curInst = Inode.getElement();

            IList.INode nextNode = irBB.list.getNext(Inode);
            Instruction nextInst = null;
            IList.INode preNode = irBB.list.getPrev(Inode);
            Instruction pretInst = null;
            if(nextNode != null){
                nextInst = (Instruction) nextNode.getElement();
            }
            if(preNode != null){
                pretInst = (Instruction) preNode.getElement();
            }

            switch (curInst.getTag()) {
                case RET -> translateRet(curInst);
                case ALLOCA -> translateAlloca(curInst);
                case STORE -> translateStore(curInst);
                case LOAD -> translateLoad(curInst);
                case ADD, SUB, MUL, DIV, FADD, FSUB, FDIV, FMUL -> translateCaculate(curInst);
                case CALL -> translateCall(curInst);
                case BR -> translateBr(curInst,pretInst);
                case NE, LT, LE, GT, GE, EQ, FEQ, FGE, FGT, FLE, FLT, FNE -> translateCond(curInst,nextInst);
                case GEP -> translateGep(curInst);
                case FPTOSI -> translateFtoI(curInst);
                case SITOFP -> translateItoF(curInst);
                case ZEXT -> translateZext(curInst);
                case FNEG -> translateFneg(curInst);
                case PTRCAST -> translatePtrcast(curInst);
                case PHI -> translatePhi(curInst);
            }
            //PHI
            //以下弃用
//            if(riscFunction.phiMap.containsKey(curInst)){
//                LinkedList<Value> list = riscFunction.phiMap.get(curInst);
//                for (Value vd : list)
//                {
//                    RISCOperand dst = getOperand(vd);
//                    RISCOperand src = getOperand(curInst);
//                    //虚拟寄存器
//                    if (dst instanceof Register)
//                    {
//                        if (curInst.getType().isFloatType()) {
//                            FmvInstruction fmv = new FmvInstruction(dst, src);
//                            instructionList.add(fmv);
//                        } else {
//                            MvInstruction mvInstruction = new MvInstruction(dst, src);
//                            instructionList.add(mvInstruction);
//                        }
//                    }
//                    //Meomery
//                    else if(dst instanceof Memory){
//                        if (curInst.getType().isFloatType()) {
//                            FsdInstruction fsd = new FsdInstruction(src, dst);
//                            instructionList.add(fsd);
//                        } else {
//                            SdInstruction sd = new SdInstruction(src, dst);
//                            instructionList.add(sd);
//                        }
//                    }
//                }
//            }
        }
        for (Value phi : phiList){
            riscFunction.valueRISCOperandHashMap.put(phi, phiVrTempMap.get(phi));
        }

        //每个块在br前为phicopy
        if(firstBrposition == -1){ firstBrposition = instructionList.size();}
        if(riscFunction.blockPhiMap.containsKey(irBB)){
            LinkedList<Pair<Value,Value>> list = riscFunction.blockPhiMap.get(irBB);
            int index = 0;
            for (Pair<Value,Value> pair : list){
                Value vd = pair.a;
                Value vs = pair.b;
                RISCOperand dst = null;
                if (isPhiOld){
                    dst = getOperand(vd);
                }
                else {
                    dst = riscFunc.valueTempPhiMap.get(vd);
                }
                RISCOperand src = null;
                if(vs instanceof Constant.ConstantFloat){
                    Float f = ((Constant.ConstantFloat) vs).getVal();
                    //进下面这个函数找浮点数，没有会生成
                    String fbName = riscFunction.riscModule.getFloatBlockName(f);
                    VirtualRegister vr = getNewVr();
                    MyString ms = new MyString(fbName);
                    LlaInstruction lla = new LlaInstruction(vr, ms);
                    instructionList.add(firstBrposition + index++,lla);
                    Memory mem = new Memory(0, vr);
                    src = mem;
//                    FloatVirtualRegister fvr = getNewFvr();
//                    FlwInstruction flw = new FlwInstruction(fvr, mem);
//                    instructionList.add(firstBrposition + index++,flw);
                }
                else {
                    src = getOperand(vs);
                }

                if(dst != src)
                {
                    if (dst instanceof Register) {
                        if (src instanceof Immediate) {
                            LiInstruction liInstruction = new LiInstruction(dst, src);
                            instructionList.add(firstBrposition + index++, liInstruction);
                        } else if (src instanceof FloatVirtualRegister || src instanceof FloatRealRegister) {
                            FmvInstruction fmvInstruction = new FmvInstruction(dst, src);
                            instructionList.add(firstBrposition + index++, fmvInstruction);
                        } else if (src instanceof Memory) {
                            if(dst instanceof VirtualRegister || dst instanceof RealRegister)
                            {
                                LdInstruction ldInstruction = new LdInstruction(dst, src);
                                instructionList.add(firstBrposition + index++, ldInstruction);
                            }
                            else {
                                FlwInstruction flwInstruction = new FlwInstruction(dst, src);
                                instructionList.add(firstBrposition + index++, flwInstruction);
                            }
                        } else {
                            MvInstruction mvInstruction = new MvInstruction(dst, src);
                            instructionList.add(firstBrposition + index++, mvInstruction);
                        }
                    } else if (dst instanceof Memory) {
                        if (src instanceof Immediate) {
                            LiInstruction liInstruction = new LiInstruction(tempRegister, src);
                            instructionList.add(firstBrposition + index++, liInstruction);
                            SdInstruction sd = new SdInstruction(tempRegister, dst);
                            instructionList.add(firstBrposition + index++, sd);
                        } else if (src instanceof FloatVirtualRegister || src instanceof FloatRealRegister) {

                            FsdInstruction fsdInstruction = new FsdInstruction(src, dst);
                            instructionList.add(firstBrposition + index++, fsdInstruction);
                        } else if (src instanceof Memory) {
                            LdInstruction ldInstruction = new LdInstruction(tempRegister, src);
                            instructionList.add(firstBrposition + index++, ldInstruction);
                            SdInstruction sd = new SdInstruction(tempRegister, dst);
                            instructionList.add(firstBrposition + index++, sd);
                        } else {
                            SdInstruction sd = new SdInstruction(src, dst);
                            instructionList.add(firstBrposition + index++, sd);
                        }
                    }
                }
            }
        }
    }

    private void translatePhi(Instruction curInst) {
        if(!isPhiOld)
        {
            RISCOperand temp = riscFunction.valueTempPhiMap.get(curInst);
            if (temp instanceof Register) {
                //phiVrTempMap.put(curInst, temp);
                //phiList.add(curInst);
                //VirtualRegister vr = getNewVr();
                RISCOperand dst = getOperand(curInst);
                if(temp instanceof VirtualRegister || temp instanceof RealRegister)
                {
                    MvInstruction mv = new MvInstruction(dst, temp);
                    instructionList.add(mv);
                }
                else if(temp instanceof FloatVirtualRegister || temp instanceof FloatRealRegister ){
                    FmvInstruction fmvInstruction = new FmvInstruction(dst, temp);
                    instructionList.add(fmvInstruction);
                }
            }
        }
    }

    private void translatePtrcast(Instruction curInst) {
        Value v = curInst.getOperandAt(0);
        RISCOperand op = getOperand(v);
        riscFunction.valueRISCOperandHashMap.put(curInst,op);
    }

    private void translateFneg(Instruction curInst) {
        Value v = curInst.getOperandAt(0);
        RISCOperand rop1 = getOperand(v);
        RISCOperand dst = getOperand(curInst);
        FnegInstruction fneg = new FnegInstruction(dst, rop1);
        instructionList.add(fneg);
        riscFunction.valueRISCOperandHashMap.put(curInst, dst);
    }

    private void translateZext(Instruction curInst) {
        Value v = curInst.getOperandAt(0);
        RISCOperand rop = getOperand(v);
        RISCOperand dst = getOperand(curInst);
        //判断为call内参数
        if (riscFunction.funcParameters.containsKey(curInst)) {
            if (dst instanceof RealRegister) {
                MvInstruction mv = new MvInstruction(dst, rop);
                instructionList.add(mv);
            } else if (dst instanceof FloatRealRegister) {
                FmvInstruction mv = new FmvInstruction(dst, rop);
                instructionList.add(mv);
            } else if (dst instanceof Memory) {
                SwInstruction sw = new SwInstruction(rop, dst);
                instructionList.add(sw);
            }
        }
        riscFunction.valueRISCOperandHashMap.put(curInst, rop);
    }

    private void translateItoF(Instruction curInst) {
        Value vop1 = curInst.getOperandAt(0);
        RISCOperand src1 = getOperand(vop1);
        RISCOperand dst = getOperand(curInst);
        RISCOperand src;

        if (src1 instanceof Immediate) {
            VirtualRegister vr = getNewVr();
            LiInstruction li = new LiInstruction(vr, src1);
            instructionList.add(li);
            src = vr;
        } else if (src1 instanceof Memory) {
            VirtualRegister vr = getNewVr();
            LwInstruction lw = new LwInstruction(vr, src1);
            instructionList.add(lw);
            src = vr;
        } else {
            src = src1;
        }

        if (dst instanceof Memory) {
            VirtualRegister vr = getNewVr();
            FcvtInstruction fcvt = new FcvtInstruction(vr, src, false);
            instructionList.add(fcvt);
            SwInstruction sw = new SwInstruction(vr, dst);
            instructionList.add(sw);
        } else {
            FcvtInstruction fcvt = new FcvtInstruction(dst, src, false);
            instructionList.add(fcvt);
        }
        riscFunction.valueRISCOperandHashMap.put(curInst, dst);
    }

    private void translateFtoI(Instruction curInst) {
        Value vop1 = curInst.getOperandAt(0);
        RISCOperand src1 = getOperand(vop1);
        RISCOperand dst = getOperand(curInst);
        RISCOperand src;

        if (src1 instanceof Memory) {
            FloatVirtualRegister fvr = getNewFvr();
            FlwInstruction flw = new FlwInstruction(fvr, src1);
            instructionList.add(flw);
            src = fvr;
        } else {
            src = src1;
        }

        if (dst instanceof Memory) {
            FloatVirtualRegister fvr = getNewFvr();
            FcvtInstruction fcvt = new FcvtInstruction(fvr, src, true);
            instructionList.add(fcvt);
            SwInstruction sw = new SwInstruction(fvr, dst);
            instructionList.add(sw);
        } else {
            FcvtInstruction fcvt = new FcvtInstruction(dst, src, true);
            instructionList.add(fcvt);
        }
        riscFunction.valueRISCOperandHashMap.put(curInst, dst);
    }

    //数组
    private void translateGep(Instruction curInst) {
        int num = curInst.getNumOP();
        if (num == 3) {
            int totalSize = ((ArrayType) ((PointerType) curInst.getOperandAt(0).getType()).getPointedType()).getTotalSize();
            int selfSize = ((ArrayType) ((PointerType) curInst.getOperandAt(0).getType()).getPointedType()).getSize();
            int containedSize = totalSize / selfSize;

            Value vop1 = curInst.getOperandAt(0);
            Value vop2 = curInst.getOperandAt(1);
            Value vop3 = curInst.getOperandAt(2);



            RISCOperand basicAddress = getOperand(vop1);
            if (basicAddress instanceof Register) {
                basicAddress = new Memory(0, (Register) basicAddress);
            }
            else if(riscFunction.myfuncParameters.containsKey(vop1) && basicAddress instanceof Memory){
                VirtualRegister virtualRegister = getNewVr();
                LdInstruction ld = new LdInstruction(virtualRegister,basicAddress);
                instructionList.add(ld);
                basicAddress = new Memory(0,virtualRegister);
            }

            if (basicAddress instanceof Memory) {
                //如果第三个操作数为常数，直接计算
                //这里写的一坨大便
                if (vop3 instanceof Constant.ConstantInt) {
                    int index = ((Constant.ConstantInt) vop3).getVal();
                    int addIndex = index * containedSize * 4;
                    int newAddress = ((Memory) basicAddress).getOffset() + addIndex;
                    Memory mem = new Memory(newAddress, ((Memory) basicAddress).basicAddress);

                    if (riscFunction.funcParameters.containsKey(curInst)) {
                        RISCOperand dst = getOperand(curInst);
                        //System.out.println("dst=   " + dst.emit());

                        if (dst instanceof RealRegister) {

                            AddiInstruction addi = new AddiInstruction((Register) dst, ((Memory) basicAddress).basicAddress, new Immediate(newAddress));
                            instructionList.add(addi);

                        } else if (dst instanceof Memory) {
                            VirtualRegister vr = getNewVr();
                            AddiInstruction addi = new AddiInstruction(vr, ((Memory) basicAddress).basicAddress, new Immediate(newAddress));
                            instructionList.add(addi);
                            SdInstruction sd = new SdInstruction(vr, dst);
                            instructionList.add(sd);
                        }
                    }
                    riscFunction.valueRISCOperandHashMap.put(curInst, mem);
                }
                //如果第三个操作数为非常数
                else {
                    RISCOperand index = getOperand(vop3);
                    Register basicAdd = ((Memory) basicAddress).basicAddress;
                    RISCOperand dst = getOperand(curInst);
                    int s = containedSize * 4;
                    Register vr1 = getNewVr();
                    LiInstruction li1 = new LiInstruction(vr1, new Immediate(s));
                    instructionList.add(li1);
                    //计算地址增量，放入vr中
                    if (index instanceof Register) {
                        MulwInstruction mul1 = new MulwInstruction(vr1, vr1, index);
                        instructionList.add(mul1);
                    } else if (index instanceof Memory) {
                        VirtualRegister vr = getNewVr();
                        LwInstruction lw = new LwInstruction(vr, index);
                        instructionList.add(lw);
                        MulwInstruction mul1 = new MulwInstruction(vr1, vr1, vr);
                        instructionList.add(mul1);
                    }
                    //计算偏移量，放入vr1
                    int basicOffset = ((Memory) basicAddress).getOffset();
                    if(basicOffset != 0) {
                        AddiInstruction addi = new AddiInstruction(vr1, vr1, new Immediate(basicOffset));
                        instructionList.add(addi);
                    }
                    //计算结果
                    AddInstruction add = new AddInstruction(dst, basicAdd, vr1);
                    instructionList.add(add);

                    if (dst instanceof RealRegister) {
                        VirtualRegister vr = getNewVr();
                        MvInstruction mv = new MvInstruction(vr, dst);
                        instructionList.add(mv);
                        riscFunction.valueRISCOperandHashMap.put(curInst, new Memory(0, vr));
                    } else if (dst instanceof VirtualRegister) {
                        riscFunction.valueRISCOperandHashMap.put(curInst, new Memory(0, (Register) dst));
                    }

                }
//                System.out.println(curInst);
//                System.out.println(mem.emit());
            } else {
                System.out.println("BUG is " + curInst);
            }
        } else if (num == 2) {
            Value vop1 = curInst.getOperandAt(0);
            Value vop2 = curInst.getOperandAt(1);

            int totalSize = 1;
            if (((PointerType) vop1.getType()).getPointedType().isArrayType()) {
                totalSize = ((ArrayType) ((PointerType) curInst.getOperandAt(0).getType()).getPointedType()).getTotalSize();
            }

            RISCOperand basicAddress = getOperand(vop1);
            if (basicAddress instanceof Register) {
                basicAddress = new Memory(0, (Register) basicAddress);
            }
            else if(riscFunction.myfuncParameters.containsKey(vop1) && basicAddress instanceof Memory){
                VirtualRegister virtualRegister = getNewVr();
                LdInstruction ld = new LdInstruction(virtualRegister,basicAddress);
                instructionList.add(ld);
                basicAddress = new Memory(0,virtualRegister);
            }

            if (basicAddress instanceof Memory) {
                if (vop2 instanceof Constant.ConstantInt) {
                    int index = ((Constant.ConstantInt) vop2).getVal();
                    int addIndex = index * totalSize * 4;
                    int newAddress = ((Memory) basicAddress).getOffset() + addIndex;
                    Memory mem = new Memory(newAddress, ((Memory) basicAddress).basicAddress);

                    if (riscFunction.funcParameters.containsKey(curInst)) {
                        RISCOperand dst = getOperand(curInst);
                        if (dst instanceof RealRegister) {

                            AddiInstruction addi = new AddiInstruction((Register) dst, ((Memory) basicAddress).basicAddress, new Immediate(newAddress));
                            instructionList.add(addi);

                        } else if (dst instanceof Memory) {
                            VirtualRegister vr = getNewVr();
                            AddiInstruction addi = new AddiInstruction(vr, ((Memory) basicAddress).basicAddress, new Immediate(newAddress));
                            instructionList.add(addi);
                            SdInstruction sd = new SdInstruction(vr, dst);
                            instructionList.add(sd);
                        }
                    }
                    riscFunction.valueRISCOperandHashMap.put(curInst, mem);
                } else {
                    RISCOperand index = getOperand(vop2);
                    Register basicAdd = ((Memory) basicAddress).basicAddress;
                    RISCOperand dst = getOperand(curInst);
                    int s = 4 * totalSize;
                    Register vr1 = getNewVr();
                    LiInstruction li1 = new LiInstruction(vr1, new Immediate(s));
                    instructionList.add(li1);
                    //计算地址增量，放入vr中
                    if (index instanceof Register) {
                        MulwInstruction mul1 = new MulwInstruction(vr1, vr1, index);
                        instructionList.add(mul1);
                    } else if (index instanceof Memory) {
                        VirtualRegister vr = getNewVr();
                        LwInstruction lw = new LwInstruction(vr, index);
                        instructionList.add(lw);
                        MulwInstruction mul1 = new MulwInstruction(vr1, vr1, vr);
                        instructionList.add(mul1);
                    }
                    //计算偏移量，放入vr1
                    int basicOffset = ((Memory) basicAddress).getOffset();
                    if(basicOffset != 0 )
                    {
                        AddiInstruction addi = new AddiInstruction(vr1, vr1, new Immediate(basicOffset));
                        instructionList.add(addi);
                    }
                    //计算结果
                    AddInstruction add = new AddInstruction(dst, basicAdd, vr1);
                    instructionList.add(add);

                    if (dst instanceof RealRegister) {
                        VirtualRegister vr = getNewVr();
                        MvInstruction mv = new MvInstruction(vr, dst);
                        instructionList.add(mv);
                        riscFunction.valueRISCOperandHashMap.put(curInst, new Memory(0, vr));
                    } else if (dst instanceof VirtualRegister) {
                        riscFunction.valueRISCOperandHashMap.put(curInst, new Memory(0, (Register) dst));
                    }
                }
            } else {
                System.out.println("BUG is " + curInst);
            }
        }

    }

    private void translateCond(Instruction curInst,Instruction nextInst) {
        Instruction cond = curInst;
        Value vop1 = cond.getOperandAt(0);
        Value vop2 = cond.getOperandAt(1);
        //  System.out.println(curInst+"\n"+vop1+" "+vop2);
        //获得存放两个数的RISCOperand
        RISCOperand op1 = getOperand(vop1);
        RISCOperand temp1 = null;
        if (op1 instanceof Memory) {
            if(vop1.getType().isIntegerType())
            {
                temp1 = new VirtualRegister(riscFunction.virtualRegisterIndex++);
                LwInstruction lw1 = new LwInstruction(temp1, op1);
                instructionList.add(lw1);
            }
            else {
                temp1 = new FloatVirtualRegister(riscFunction.floatVirtualRegisterIndex++);
                FlwInstruction flw1 = new FlwInstruction(temp1,op1);
                instructionList.add(flw1);
            }
        } else if (op1 instanceof Register) {
            temp1 = op1;

        } else if (op1 instanceof Immediate) {
            VirtualRegister vr = getNewVr();
            LiInstruction li = new LiInstruction(vr, op1);
            instructionList.add(li);
            temp1 = vr;
        }
        //   System.out.println(op1.emit()+" "+temp1.emit());

        RISCOperand op2 = getOperand(vop2);
        RISCOperand temp2 = null;
        if (op2 instanceof Memory) {
            if(vop2.getType().isFloatType())
            {
                temp2 = new VirtualRegister(riscFunction.virtualRegisterIndex++, vop2);
                LwInstruction lw2 = new LwInstruction(temp2, op2);
                instructionList.add(lw2);
            }
            else {
                temp2 = new FloatVirtualRegister(riscFunction.floatVirtualRegisterIndex++,vop2);
                FlwInstruction flw2 = new FlwInstruction(temp2,op2);
                instructionList.add(flw2);
            }
        } else if (op2 instanceof Register) {
            temp2 = op2;
        } else if (op2 instanceof Immediate) {
            VirtualRegister vr = getNewVr();
            LiInstruction li = new LiInstruction(vr, op2);
            instructionList.add(li);
            temp2 = vr;
        }
        //   System.out.println(op2.emit()+" "+temp2.emit());
        RISCOperand dst = getOperand(curInst);

        switch (cond.getTag()) {
            case EQ -> {
                if(nextInst == null||!nextInst.getTag().equals(Instruction.TAG.BR)||nextInst.getNumOP()!=3)
                {
                    Register vr = tempRegister;
                    SubwInstruction sub = new SubwInstruction(vr, temp1, temp2);
                    instructionList.add(sub);
                    SeqzInstruction b = new SeqzInstruction(dst, vr);
                    instructionList.add(b);
                }
                else {
                    if(firstBrposition == -1){
                        firstBrposition = instructionList.size();
                    }
                    //获取跳转地址
                    Value v1 = nextInst.getOperandAt(1);
                    StringBuffer vName1 = new StringBuffer(v1.getName());
                    String vN1 = new String(vName1);
                    MyString dst1 = new MyString(".B" + irFunction.getName() + vN1);

                    Value v2 = nextInst.getOperandAt(2);
                    StringBuffer vName2 = new StringBuffer(v2.getName());
                    String vN2 = new String(vName2);
                    MyString dst2 = new MyString(".B" + irFunction.getName() + vN2);

                    BeqInstruction beq = new BeqInstruction(temp1, temp2, dst1,this);
                    instructionList.add(beq);

                    JInstruction j = new JInstruction(dst2,this);
                    instructionList.add(j);
                }
            }
            case NE -> {
                if(nextInst == null||!nextInst.getTag().equals(Instruction.TAG.BR)||nextInst.getNumOP()!=3)
                {
                    Register vr = tempRegister;
                    SubwInstruction sub = new SubwInstruction(vr, temp1, temp2);
                    instructionList.add(sub);
                    SeqzInstruction b = new SeqzInstruction(dst, vr);
                    instructionList.add(b);
                    XoriInstruction xor = new XoriInstruction(dst, dst, new Immediate(1));
                    instructionList.add(xor);
                }
                else {
                    if(firstBrposition == -1){
                        firstBrposition = instructionList.size();
                    }
                    System.out.println(nextInst);
                    //获取跳转地址
                    Value v1 = nextInst.getOperandAt(1);
                    StringBuffer vName1 = new StringBuffer(v1.getName());
                    String vN1 = new String(vName1);
                    MyString dst1 = new MyString(".B" + irFunction.getName() + vN1);

                    Value v2 = nextInst.getOperandAt(2);
                    StringBuffer vName2 = new StringBuffer(v2.getName());
                    String vN2 = new String(vName2);
                    MyString dst2 = new MyString(".B" + irFunction.getName() + vN2);

                    BneInstruction bne = new BneInstruction(temp1, temp2, dst1,this);
                    instructionList.add(bne);

                    JInstruction j = new JInstruction(dst2,this);
                    instructionList.add(j);
                }
            }
            case LT -> {
                if(nextInst == null||!nextInst.getTag().equals(Instruction.TAG.BR)||nextInst.getNumOP()!=3)
                {
                    if (op2 instanceof Immediate) {
                        int val = ((Immediate) op2).getVal();
                        if (val >= -2048 && val <= 2047) {
                            instructionList.removeLast();
                            SltiInstruction b = new SltiInstruction(dst, temp1, op2);
                            instructionList.add(b);
                        } else if (val >= 2048 && val <= 4095) {
                            instructionList.removeLast();
                            SltiuInstruction b = new SltiuInstruction(dst, temp1, op2);
                            instructionList.add(b);
                        } else {
                            SltInstruction b = new SltInstruction(dst, temp1, temp2);
                            instructionList.add(b);
                        }
                    } else {
                        SltInstruction b = new SltInstruction(dst, temp1, temp2);
                        instructionList.add(b);
                    }
                }
                else {
                    if(firstBrposition == -1){
                        firstBrposition = instructionList.size();
                    }
                    //获取跳转地址
                    Value v1 = nextInst.getOperandAt(1);
                    StringBuffer vName1 = new StringBuffer(v1.getName());
                    String vN1 = new String(vName1);
                    MyString dst1 = new MyString(".B" + irFunction.getName() + vN1);

                    Value v2 = nextInst.getOperandAt(2);
                    StringBuffer vName2 = new StringBuffer(v2.getName());
                    String vN2 = new String(vName2);
                    MyString dst2 = new MyString(".B" + irFunction.getName() + vN2);

                    BltInstruction blt = new BltInstruction(temp1, temp2, dst1,this);
                    instructionList.add(blt);

                    JInstruction j = new JInstruction(dst2,this);
                    instructionList.add(j);
                }

            }
            case LE -> {
                if(nextInst == null||!nextInst.getTag().equals(Instruction.TAG.BR)||nextInst.getNumOP()!=3)
                {// 小于等于 相当于大于反过来
                    Register vr = tempRegister;
                    SubwInstruction sub = new SubwInstruction(vr, temp1, temp2);
                    instructionList.add(sub);
                    SgtzInstruction b = new SgtzInstruction(dst, vr);
                    instructionList.add(b);
                    XoriInstruction xor = new XoriInstruction(dst, dst, new Immediate(1));
                    instructionList.add(xor);
                }
                else {
                    if(firstBrposition == -1){
                        firstBrposition = instructionList.size();
                    }
                    //获取跳转地址
                    Value v1 = nextInst.getOperandAt(1);
                    StringBuffer vName1 = new StringBuffer(v1.getName());
                    String vN1 = new String(vName1);
                    MyString dst1 = new MyString(".B" + irFunction.getName() + vN1);

                    Value v2 = nextInst.getOperandAt(2);
                    StringBuffer vName2 = new StringBuffer(v2.getName());
                    String vN2 = new String(vName2);
                    MyString dst2 = new MyString(".B" + irFunction.getName() + vN2);

                    BleInstruction ble = new BleInstruction(temp1, temp2, dst1,this);
                    instructionList.add(ble);

                    JInstruction j = new JInstruction(dst2,this);
                    instructionList.add(j);
                }
            }
            case GT -> {
                if(nextInst == null||!nextInst.getTag().equals(Instruction.TAG.BR)||nextInst.getNumOP()!=3)
                {
                    Register vr = tempRegister;
                    SubwInstruction sub = new SubwInstruction(vr, temp1, temp2);
                    instructionList.add(sub);
                    SgtzInstruction b = new SgtzInstruction(dst, vr);
                    instructionList.add(b);
                }
                else {
                    if(firstBrposition == -1){
                        firstBrposition = instructionList.size();
                    }
                    //获取跳转地址
                    Value v1 = nextInst.getOperandAt(1);
                    StringBuffer vName1 = new StringBuffer(v1.getName());
                    String vN1 = new String(vName1);
                    MyString dst1 = new MyString(".B" + irFunction.getName() + vN1);

                    Value v2 = nextInst.getOperandAt(2);
                    StringBuffer vName2 = new StringBuffer(v2.getName());
                    String vN2 = new String(vName2);
                    MyString dst2 = new MyString(".B" + irFunction.getName() + vN2);

                    BgtInstruction bgt = new BgtInstruction(temp1, temp2, dst1,this);
                    instructionList.add(bgt);

                    JInstruction j = new JInstruction(dst2,this);
                    instructionList.add(j);
                }
            }
            case GE -> {
                if(nextInst == null||!nextInst.getTag().equals(Instruction.TAG.BR)||nextInst.getNumOP()!=3)
                {//大于等于相当于小于反过来
                    if (op2 instanceof Immediate) {
                        int val = ((Immediate) op2).getVal();
                        if (val >= -2048 && val <= 2047) {
                            instructionList.removeLast();
                            SltiInstruction b = new SltiInstruction(dst, temp1, op2);
                            instructionList.add(b);
                        } else if (val >= 2048 && val <= 4095) {
                            instructionList.removeLast();
                            SltiuInstruction b = new SltiuInstruction(dst, temp1, op2);
                            instructionList.add(b);
                        } else {
                            SltInstruction b = new SltInstruction(dst, temp1, temp2);
                            instructionList.add(b);
                        }
                    } else {
                        SltInstruction b = new SltInstruction(dst, temp1, temp2);
                        instructionList.add(b);
                    }
                    XoriInstruction xor = new XoriInstruction(dst, dst, new Immediate(1));
                    instructionList.add(xor);
                }
                else {
                    if(firstBrposition == -1){
                        firstBrposition = instructionList.size();
                    }
                    //获取跳转地址
                    Value v1 = nextInst.getOperandAt(1);
                    StringBuffer vName1 = new StringBuffer(v1.getName());
                    String vN1 = new String(vName1);
                    MyString dst1 = new MyString(".B" + irFunction.getName() + vN1);

                    Value v2 = nextInst.getOperandAt(2);
                    StringBuffer vName2 = new StringBuffer(v2.getName());
                    String vN2 = new String(vName2);
                    MyString dst2 = new MyString(".B" + irFunction.getName() + vN2);

                    BgeInstruction bge = new BgeInstruction(temp1, temp2, dst1,this);
                    instructionList.add(bge);

                    JInstruction j = new JInstruction(dst2,this);
                    instructionList.add(j);
                }
            }
            case FEQ -> {
                FeqInstruction b = new FeqInstruction(dst, temp1, temp2);
                instructionList.add(b);
            }
            case FNE -> {
                Register vr = tempRegister;
                FeqInstruction b = new FeqInstruction(vr, temp1, temp2);
                instructionList.add(b);
                XoriInstruction xor = new XoriInstruction(dst, vr, new Immediate(1));
                instructionList.add(xor);
            }
            case FLT -> {
                FltInstruction b = new FltInstruction(dst, temp1, temp2);
                instructionList.add(b);
            }
            case FLE -> {
                FleInstruction b = new FleInstruction(dst, temp1, temp2);
                instructionList.add(b);
            }
            case FGT -> {
                FgtInstruction b = new FgtInstruction(dst, temp1, temp2);
                instructionList.add(b);
            }
            case FGE -> {
                FgeInstruction b = new FgeInstruction(dst, temp1, temp2);
                instructionList.add(b);
            }

        }
        riscFunction.valueRISCOperandHashMap.put(curInst, dst);

    }

    //控制流，
    private void translateBr(Instruction curInst,Instruction preInst) {
        int paraCount = curInst.getNumOP();
        if (paraCount == 1) {
            if(firstBrposition == -1){
                firstBrposition = instructionList.size();
            }
            Value v = curInst.getOperandAt(0);
            StringBuffer vName = new StringBuffer(v.getName());
            //vName.deleteCharAt(0);
            String vN = new String(vName);
            MyString ms = new MyString(".B" + irFunction.getName() + vN);
            JInstruction j = new JInstruction(ms,this);
            instructionList.add(j);
        } else if (paraCount == 3) {
            if((preInst.getTag() == Instruction.TAG.EQ || preInst.getTag() == Instruction.TAG.NE || preInst.getTag() == Instruction.TAG.LT || preInst.getTag() == Instruction.TAG.LE || preInst.getTag() == Instruction.TAG.GT || preInst.getTag() == Instruction.TAG.GE)){
                return;
            }
            if(firstBrposition == -1){
                firstBrposition = instructionList.size();
            }
            Value vcond = curInst.getOperandAt(0);


            if (vcond instanceof Constant) {
                if (((Constant.ConstantInt) vcond).getVal() >= 1) {
                    Value v = curInst.getOperandAt(1);
                    StringBuffer vName = new StringBuffer(v.getName());
                    String vN = new String(vName);
                    MyString ms = new MyString(".B" + irFunction.getName() + vN);
                    JInstruction j = new JInstruction(ms,this);
                    instructionList.add(j);
                } else {
                    Value v = curInst.getOperandAt(2);
                    StringBuffer vName = new StringBuffer(v.getName());
                    String vN = new String(vName);
                    MyString ms = new MyString(".B" + irFunction.getName() + vN);
                    JInstruction j = new JInstruction(ms,this);
                    instructionList.add(j);
                }
            } else {
                //获得第一操作数
                Value v0 = curInst.getOperandAt(0);
                RISCOperand rop1 = getOperand(v0);
                //获得比较操作数
//                Instruction vInst = (Instruction) vcond;
//                Value vop1 = vInst.getOperandAt(1);
//                Value vop2 = vInst.getOperandAt(2);

                //获取跳转地址
                Value v1 = curInst.getOperandAt(1);
                StringBuffer vName1 = new StringBuffer(v1.getName());
                String vN1 = new String(vName1);
                MyString dst1 = new MyString(".B" + irFunction.getName() + vN1);

                Value v2 = curInst.getOperandAt(2);
                StringBuffer vName2 = new StringBuffer(v2.getName());
                String vN2 = new String(vName2);
                MyString dst2 = new MyString(".B" + irFunction.getName() + vN2);

                BnezInstruction bne = new BnezInstruction(rop1, dst1,this);
                instructionList.add(bne);

                JInstruction j = new JInstruction(dst2,this);
                instructionList.add(j);
            }
        }
    }

    private void translateCall(Instruction curInst) {
        //   System.out.println(curInst);
        int paraCount = curInst.getNumOP();
        int intCount = 0;
        int floatCount = 0;
        int stackIndex = 8;
        //为call内所有参数赋值
        for (int i = 1; i < paraCount; i++) {
            Value v = curInst.getOperandAt(i);
            RISCOperand src = getOperand(v);
            //  System.out.println(v);
            if (src instanceof Memory) {

                if (v.getType().isIntegerType()) {
                    VirtualRegister vr = getNewVr();
                    LdInstruction ld = new LdInstruction(vr, src);
                    instructionList.add(ld);
                    src = vr;
                } else if (v.getType().isFloatType()) {
                    FloatVirtualRegister fvr = getNewFvr();
                    FldInstruction fld = new FldInstruction(fvr, src);
                    instructionList.add(fld);
                    src = fvr;
                } else if (v.getType().isPointerType()) {
                    if (riscFunction.myfuncParameters.containsKey(v)) {
                        VirtualRegister vr = getNewVr();
                        LdInstruction ld = new LdInstruction(vr, src);
                        instructionList.add(ld);
                        src = vr;
                    } else {
                        VirtualRegister vr = getNewVr();
                        int offset = ((Memory) src).getOffset();
                        Register address = ((Memory) src).basicAddress;
                        AddiInstruction addi = new AddiInstruction(vr, address, new Immediate(offset));
                        instructionList.add(addi);
                        src = vr;

                    }
                }
            }
            int opeIndex;
            if(v.getType().isIntegerType()||v.getType().isPointerType()){
                if(intCount < 8)
                {
                    opeIndex = intCount++;
                }
                else {
                    opeIndex = stackIndex++;
                }
            }
            else{
                if(floatCount < 8)
                {
                    opeIndex = floatCount++;
                }
                else {
                    opeIndex = stackIndex++;
                }
            }
            //获得目标寄存器a0-a10
            RISCOperand dst;
            if (opeIndex < 8) {
                if (v.getType().isIntegerType() || v.getType().isPointerType()) {
                    dst = new RealRegister(allocAReg(opeIndex));
                } else {
                    //fa0 = f10
                    dst = new FloatRealRegister(opeIndex + 10);
                }
            }
            //超出部分放栈里
            else {
                dst = new Memory((opeIndex - 8) * 8, 2);
            }
            //如果参数是常数
            if (v instanceof Constant) {

                if (v.getType().isIntegerType()) {
                    int val = ((Constant.ConstantInt) v).getVal();
                    if(val != 0)
                    {
                        RISCOperand temp = new Immediate(val);
                        if (dst instanceof Register) {
                            LiInstruction li = new LiInstruction(dst, temp);
                            instructionList.add(li);
                        } else if (dst instanceof Memory) {
                            Register vr = tempRegister;
                            LiInstruction li = new LiInstruction(vr, temp);
                            instructionList.add(li);
                            SdInstruction sd = new SdInstruction(vr, dst);
                            instructionList.add(sd);
                        }
                    }
                    else {
                        RISCOperand temp = new RealRegister(0);
                        if (dst instanceof Register) {
                            MvInstruction mv = new MvInstruction(dst, temp);
                            instructionList.add(mv);
                        } else if (dst instanceof Memory) {
                            SdInstruction sd = new SdInstruction(temp, dst);
                            instructionList.add(sd);
                        }
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
            //如果参数是整形或者指针
            else if (v.getType().isIntegerType() || v.getType().isPointerType()) {
                if (dst instanceof Register) {
                    MvInstruction mv = new MvInstruction(dst, src);
                    instructionList.add(mv);
                } else if (dst instanceof Memory) {
                    SdInstruction sd = new SdInstruction(src, dst);
                    instructionList.add(sd);
                }
            } else if (v.getType().isFloatType()) {
                if (dst instanceof Register) {
                    FmvInstruction mv = new FmvInstruction(dst, src);
                    instructionList.add(mv);
                } else if (dst instanceof Memory) {
                    FsdInstruction sd = new FsdInstruction(src, dst);
                    instructionList.add(sd);
                }
            }
        }
        String funcName = curInst.getOperandAt(0).getName();
        CallInstruction call1 = new CallInstruction(funcName);
        instructionList.add(call1);
        //以下操作为将a0（函数返回值）和ir中的虚拟寄存器进行对应
        if (!curInst.getType().isVoidType())
        {
            RISCOperand dst = getOperand(curInst);

            if (dst instanceof RealRegister || dst instanceof VirtualRegister) {

                RealRegister a0 = new RealRegister(allocAReg(0));
                MvInstruction mv = new MvInstruction(dst, a0);
                instructionList.add(mv);
                riscFunction.valueRISCOperandHashMap.put(curInst, dst);


            } else if (dst instanceof FloatRealRegister || dst instanceof FloatVirtualRegister) {
                FloatRealRegister Fa0 = new FloatRealRegister(10);
                FmvInstruction fmv = new FmvInstruction(dst, Fa0);
                instructionList.add(fmv);
                riscFunction.valueRISCOperandHashMap.put(curInst, dst);
            } else if (dst instanceof Memory) {
                if (curInst.getType().isIntegerType() || curInst.getType().isPointerType()) {
                    RealRegister a0 = new RealRegister(allocAReg(0));
                    SdInstruction sd = new SdInstruction(a0, dst);
                    instructionList.add(sd);
                } else if (curInst.getType().isFloatType()) {
                    FloatRealRegister Fa0 = new FloatRealRegister(10);
                    FsdInstruction fsd = new FsdInstruction(Fa0, dst);
                    instructionList.add(fsd);
                }
            }
        }

    }

    public LinkedList<RISCInstruction> getInstructionList() {
        return instructionList;
    }

    private void translateCaculate(Instruction curInst) {


        Boolean isZero = false;
        Boolean isOne = false;
        Boolean isZero2 = false;
        Boolean isOne2 = false;
        Value vop1 = curInst.getOperandAt(0);
        RISCOperand op1 = getOperand(vop1);
        //  System.out.println(vop1.getName() + " -> " + op1.emit());
        RISCOperand temp1 = null;
        if (op1 instanceof Memory) {
            if(vop1.getType().isIntegerType()) {
                temp1 = new VirtualRegister(riscFunction.virtualRegisterIndex++, vop1);
                LwInstruction lw2 = new LwInstruction(temp1, op1);
                instructionList.add(lw2);
            }
            else if(vop1.getType().isFloatType()){
                temp1 = new FloatVirtualRegister(riscFunction.floatVirtualRegisterIndex++,vop1);
                FlwInstruction lw2 = new FlwInstruction(temp1, op1);
                instructionList.add(lw2);
            }
            else if(vop1.getType().isPointerType()){
                temp1 = new VirtualRegister(riscFunction.virtualRegisterIndex++, vop1);
                LdInstruction l2 = new LdInstruction(temp1, op1);
                instructionList.add(l2);
            }
        } else if (op1 instanceof Register) {
            if(op1 instanceof RealRegister && ((RealRegister) op1).regType.name() == "zero"){
                isZero2 = true;
            }
            temp1 = op1;
            //if(riscFunction.valueVRMap.get(vop1) == (VirtualRegister) op1) {riscFunction.valueVRMap.remove(vop1);}

        } else if (op1 instanceof Immediate) {
            if (((Immediate) op1).getVal() == 1){
                isOne2 = true;
            }
            VirtualRegister vr = getNewVr();
            LiInstruction li = new LiInstruction(vr, op1);
            instructionList.add(li);
            temp1 = vr;
        }

        Value vop2 = curInst.getOperandAt(1);
        RISCOperand op2 = getOperand(vop2);
        //      System.out.println(vop2.getName() + " -> " + op2.emit());
        RISCOperand temp2 = null;
        if (op2 instanceof Memory) {
            if(vop2.getType().isIntegerType()) {
                temp2 = new VirtualRegister(riscFunction.virtualRegisterIndex++, vop2);
                LwInstruction lw2 = new LwInstruction(temp2, op2);
                instructionList.add(lw2);
            }
            else if(vop2.getType().isFloatType()){
                temp2 = new FloatVirtualRegister(riscFunction.floatVirtualRegisterIndex++,vop2);
                FlwInstruction lw2 = new FlwInstruction(temp2, op2);
                instructionList.add(lw2);
            }
            else if(vop2.getType().isPointerType()){
                temp2 = new VirtualRegister(riscFunction.virtualRegisterIndex++, vop2);
                LdInstruction l2 = new LdInstruction(temp2, op2);
                instructionList.add(l2);
            }
            //riscFunction.valueVRMap.put(vop2,(VirtualRegister) temp2);

        } else if (op2 instanceof Register) {
            if(op2 instanceof RealRegister && ((RealRegister) op2).regType.name() == "zero"){
                isZero = true;
            }
            temp2 = op2;
        } else if (op2 instanceof Immediate) {
            if (((Immediate) op2).getVal() == 1){
                isOne = true;
            }
            VirtualRegister vr = getNewVr();
            LiInstruction li = new LiInstruction(vr, op2);
            instructionList.add(li);
            temp2 = vr;
        }

        RISCOperand dst = getOperand(curInst);

        switch (curInst.getTag()) {
            case ADD -> {
                if(op2 instanceof Immediate){
                    instructionList.removeLast();
                    AddiInstruction cal = new AddiInstruction((Register) dst, (Register) temp1, (Immediate) op2);
                    instructionList.add(cal);
                }
                else
                {
                    if(isZero){
                        MvInstruction mvInstruction = new MvInstruction(dst,temp1);
                        instructionList.add(mvInstruction);
                    }
                    else if(isZero2){
                        MvInstruction mvInstruction = new MvInstruction(dst,temp2);
                        instructionList.add(mvInstruction);
                    }
                    else {
                        AddwInstruction cal = new AddwInstruction(dst, temp1, temp2);
                        instructionList.add(cal);
                    }
                }
            }
            case SUB -> {
                if(isZero){
                    MvInstruction mvInstruction = new MvInstruction(dst,temp1);
                    instructionList.add(mvInstruction);
                }
                else {
                    SubwInstruction cal = new SubwInstruction(dst, temp1, temp2);
                    instructionList.add(cal);
                }
            }
            case MUL -> {
                if(isOne){
                    if (instructionList.getLast().type == RISCInstruction.ITYPE.li)
                    {
                        instructionList.removeLast();
                    }
                    MvInstruction mvInstruction = new MvInstruction(dst,temp1);
                    instructionList.add(mvInstruction);
                }
                else if(isOne2){
                    if (instructionList.getLast().type == RISCInstruction.ITYPE.li)
                    {
                        instructionList.removeLast();
                    }
                    MvInstruction mvInstruction = new MvInstruction(dst,temp2);
                    instructionList.add(mvInstruction);
                }
                else if(isZero || isZero2){
                    MvInstruction mvInstruction = new MvInstruction(dst,new RealRegister(0));
                    instructionList.add(mvInstruction);
                }
                else {
                    MulwInstruction cal = new MulwInstruction(dst, temp1, temp2);
                    instructionList.add(cal);
                }
            }
            case DIV -> {
                if(isOne){
                    MvInstruction mvInstruction = new MvInstruction(dst,temp1);
                    instructionList.add(mvInstruction);
                }
                else if (isZero2) {
                    MvInstruction mvInstruction = new MvInstruction(dst, new RealRegister(0));
                    instructionList.add(mvInstruction);
                }
                else if(op2 instanceof Immediate){
                    instructionList.removeLast();
                    this.optimizeDiv(curInst, dst, temp1, (Immediate) op2);

                }
                else {
                    DivwInstruction cal = new DivwInstruction(dst, temp1, temp2);
                    instructionList.add(cal);
                }
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

    private void optimizeDiv(Instruction curInst, RISCOperand dst, RISCOperand src, Immediate op2) {
        int divisor = Math.abs(op2.getVal());
        RISCOperand regAns;
        if (op2.getVal() < 0) {
            regAns = getNewVr();
        } else {
            regAns = dst;
        }
        //初始化log等参数
        int log = (int) Math.max(1,Math.ceil(log2(divisor)));
        long m = (long) (1 + Math.floor(Math.pow(2,(32+log-1))/divisor));
        System.out.println(m);
        int sh = log-1;
        if(isPowerOf2(divisor)){
            //q0 = (n+(n>>(log-1))>>>(32-log))>>log;
            RISCOperand temp = null;
            if(dst.emit().equals(src.emit()) ){
                temp = getNewVr();
            }
            else {
                temp = regAns;
            }
            //64位寄存器符号位无需扩展
//            if (log != 1)
//            {
//                SraiInstruction sraiInstruction = new SraiInstruction(temp, src, new Immediate(log - 1));
//                instructionList.add(sraiInstruction);
//            }
            SrliInstruction srliInstruction = new SrliInstruction(temp,temp,new Immediate(64-log));
            instructionList.add(srliInstruction);
            AddwInstruction addwInstruction = new AddwInstruction(temp,temp,src);
            instructionList.add(addwInstruction);
            SraiwInstruction sraiwInstruction = new SraiwInstruction(regAns,temp,new Immediate(log));
            instructionList.add(sraiwInstruction);
        }
        else if(m < (1L<<31)){
//            int q0 = Math.toIntExact((m * n) >> 32 >> sh);
            RISCOperand temp = null;
            if(dst.emit().equals(src.emit())){
                temp = getNewVr();
            }
            else {
                temp = regAns;
            }
            LiInstruction liInstruction = new LiInstruction(temp,new BigImmediate(m));
            instructionList.add(liInstruction);
            MulInstruction mulInstruction = new MulInstruction(temp,temp,src);
            instructionList.add(mulInstruction);
            SraiInstruction sraiInstruction = new SraiInstruction(temp,temp,new Immediate(32+sh));
            instructionList.add(sraiInstruction);
            //q0 = q0-xsignN;
            SraiwInstruction sraiwInstruction2 = new SraiwInstruction(tempRegister, src, new Immediate(31));
            instructionList.add(sraiwInstruction2);
            SubwInstruction subwInstruction = new SubwInstruction(regAns, temp, tempRegister);
            instructionList.add(subwInstruction);
        }
        else {
            throw new RuntimeException("除法bug！！！！！！");
        }
        //如果除数小于则倒转结果
        if (op2.getVal() < 0) {
            SubInstruction subInstruction = new SubInstruction(dst, new RealRegister(0), regAns);
            instructionList.add(subInstruction);
        }
    }

//    public void optimizeDiv(Instruction curInst, RISCOperand dst, RISCOperand src, Immediate op2) {
//        int divisor = Math.abs(op2.getVal());
//        RISCOperand regAns;
//        if (op2.getVal() < 0) {
//            regAns = getNewVr();
//        } else {
//            regAns = dst;
//        }
//        //初始化log等参数
//        int l = this.log2(divisor);
//        int sh = l;
//        System.out.println("divisor is " + divisor);
//        BigInteger temp = new BigInteger("1");
//        long low = temp.shiftLeft(32 + l)
//                .divide(BigInteger.valueOf(divisor))
//                .longValue();
//        System.out.println("low is "+ low);
//        long high = temp.shiftLeft(32 + l)
//                .add(temp.shiftLeft(l + 1))
//                .divide(BigInteger.valueOf(divisor))
//                .longValue();
//        System.out.println("high is " + high);
//        while (((low / 2) < (high / 2)) && sh > 0) {
//            low /= 2;
//            high /= 2;
//            sh--;
//        }
//        if (this.isPowerOf2(divisor)) {
//            int x = this.log2(divisor);
//
//            // vr1 = srli src, (64-x)
//            // vr2 = addw src, vr1
//            // ans = sraiw vr2, x
//            //当除数为2的整数幂
//            if (x > 0 && x < 31) {
//                VirtualRegister vr = getNewVr();
//                Immediate imm64SubX = new Immediate(64 - x);
//                Immediate immX = new Immediate(x);
//                SrliInstruction srliInstruction = new SrliInstruction(tempRegister, src, imm64SubX);
//                instructionList.add(srliInstruction);
//                AddwInstruction addwInstruction = new AddwInstruction(vr, src, tempRegister);
//                instructionList.add(addwInstruction);
//                SraiwInstruction sraiwInstruction = new SraiwInstruction(regAns, vr, immX);
//                instructionList.add(sraiwInstruction);
//
//            }
//        } else {
//
//            if (high < (1L << 31)) {
//                VirtualRegister vr = getNewVr();
//                VirtualRegister vr2 = getNewVr();
//                BigImmediate bigImm = new BigImmediate(high);
//                LiInstruction liInstruction = new LiInstruction(vr, bigImm);
//                instructionList.add(liInstruction);
//                MulInstruction mulInstruction = new MulInstruction(vr, src, vr);
//                instructionList.add(mulInstruction);
//
//                Immediate imm1 = new Immediate(32 + sh);
//                SraiInstruction sraiInstruction = new SraiInstruction(vr2, vr, imm1);
//                instructionList.add(sraiInstruction);
//
//                Immediate imm2 = new Immediate(31);
//                SraiwInstruction sraiwInstruction = new SraiwInstruction(tempRegister, src, imm2);
//                instructionList.add(sraiwInstruction);
//
//                SubwInstruction subwInstruction = new SubwInstruction(regAns, vr2, tempRegister);
//                instructionList.add(subwInstruction);
//            } else {
//                high = high - (1L << 32);
//                VirtualRegister vr = getNewVr();
//                BigImmediate bigImm = new BigImmediate(high);
//                LiInstruction liInstruction = new LiInstruction(vr, bigImm);
//                instructionList.add(liInstruction);
//                MulInstruction mulInstruction = new MulInstruction(vr, src, vr);
//                instructionList.add(mulInstruction);
//
//                VirtualRegister vr2 = getNewVr();
//                Immediate imm1 = new Immediate(32);
//                SraiInstruction sraiInstruction = new SraiInstruction(vr2, vr, imm1);
//                instructionList.add(sraiInstruction);
//
//                VirtualRegister vr3 = getNewVr();
//                AddwInstruction addwInstruction = new AddwInstruction(vr3, vr2, src);
//                instructionList.add(addwInstruction);
//
//                VirtualRegister vr4 = getNewVr();
//                Immediate imm2 = new Immediate(sh);
//                SraiwInstruction sraiwInstruction = new SraiwInstruction(vr4, vr3, imm2);
//                instructionList.add(sraiwInstruction);
//
//                Immediate imm3 = new Immediate(31);
//                SraiwInstruction sraiwInstruction2 = new SraiwInstruction(tempRegister, src, imm3);
//                instructionList.add(sraiwInstruction2);
//                SubwInstruction subwInstruction = new SubwInstruction(regAns, vr4, tempRegister);
//                instructionList.add(subwInstruction);
//
//            }
//        }
//        //如果除数小于则倒转结果
//        if (op2.getVal() < 0) {
//            SubInstruction subInstruction = new SubInstruction(dst, new RealRegister(0), regAns);
//            instructionList.add(subInstruction);
//        }
//
//
//    }


    private void translateLoad(Instruction curInst) {
        //提前取出dst判断是否为寄存器
        Value v = curInst.getOperandAt(0);
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
        } else if (curInst.getType().isPointerType()) {
            Value op1 = curInst.getOperandAt(0);
            RISCOperand src = getOperand(op1);
            RISCOperand dst;
            if (Tdst instanceof Memory) {
                dst = getNewVr();
            } else {
                dst = Tdst;
            }
            if (src instanceof Memory) {
                LdInstruction l1 = new LdInstruction(dst, src);
                instructionList.add(l1);
            } else if (src instanceof Register) {
                MvInstruction mv1 = new MvInstruction(dst, src);
                instructionList.add(mv1);
            }
            riscFunction.valueRISCOperandHashMap.put(curInst, dst);
            if (Tdst instanceof Memory) {
                SdInstruction sd1 = new SdInstruction(dst, Tdst);
                instructionList.add(sd1);
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


    }

    private void translateStore(Instruction curInst) {
        Value op1 = curInst.getOperandAt(0);
        Value op2 = curInst.getOperandAt(1);
        RISCOperand rop1 = getOperand(op1);
        RISCOperand rop2 = getOperand(op2);
//        if(rop2 instanceof Register){
//            rop2 = new Memory(0, (Register) rop2);
//        }
        if (rop1 instanceof Immediate) {
            VirtualRegister vr = new VirtualRegister(riscFunction.virtualRegisterIndex++);
            LiInstruction li1 = new LiInstruction(vr, rop1);
            instructionList.add(li1);
            SwInstruction sw1 = new SwInstruction(vr, rop2);
            instructionList.add(sw1);
        } else if (rop1 instanceof Register) {
            if (op1.getType() == Type.IntegerType.getType()) {
                SwInstruction sw1 = new SwInstruction(rop1, rop2);
                instructionList.add(sw1);
            } else if (op1.getType().isPointerType()) {
                SdInstruction sd1 = new SdInstruction(rop1, rop2);
                instructionList.add(sd1);
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
            } else if (op1.getType().isPointerType()) {
                VirtualRegister vr = getNewVr();
                LdInstruction ld1 = new LdInstruction(vr, rop1);
                instructionList.add(ld1);
                SdInstruction sd = new SdInstruction(vr, rop2);
                instructionList.add(sd);
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
        } else if (curInst.getType().isPointerType()) {
            riscFunction.localStackIndex += 8;
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
//            NopInstruction nop1 = new NopInstruction();
//            instructionList.add(nop1);
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
        //查找map表
        else if (riscFunction.valueRISCOperandHashMap.containsKey(value)) {
            return riscFunction.valueRISCOperandHashMap.get(value);
        }
        //判断为call中的参数
//        else if (riscFunction.funcParameters.containsKey(value)) {
//            System.out.println(value+"i am call");
////            int opeIndex = riscFunction.funcParameters.get(value);
////            if (opeIndex < 8) {
//            if (value.getType().isIntegerType() || value.getType().isPointerType()) {
//                if(value instanceof Constant)
//
//                return getNewVr();
//            } else {
//                //fa0 = f10
//                //RISCOperand reg = new FloatRealRegister(opeIndex + 10);
//                return getNewFvr();
//            }
//            // }
//            //超出部分放栈里
////            else {
////                RISCOperand mem = new Memory((opeIndex - 8) * 8, 2);
////                return mem;
////            }
//        }
        //判断为本函数的参数
        else if (riscFunction.myfuncParameters.containsKey(value)) {
            int myPIndex = riscFunction.myfuncParameters.get(value);
            if (myPIndex < 8) {
                if (value.getType().isIntegerType() || value.getType().isPointerType()) {
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
                RISCOperand mem = new Memory((myPIndex - 8) * 8, 1);
                return mem;
            }
        }
        //判断为浮点类型
        else if (value.getType().isFloatType()) {
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
                if (val != 0)
                {
                    RISCOperand temp = new Immediate(val);
                    return temp;
                }
                else {
                    RISCOperand zero = new RealRegister(0);
                    return zero;
                }
            } else {
                VirtualRegister vr = new VirtualRegister(riscFunction.virtualRegisterIndex++, value);
                return vr;
            }
        }


    }

    private int allocAReg(int vname) {
        return 9 + vname;
    }

    public VirtualRegister getNewVr() {
        VirtualRegister vr = new VirtualRegister(riscFunction.virtualRegisterIndex++);
        return vr;
    }

    public FloatVirtualRegister getNewFvr() {
        FloatVirtualRegister fvr = new FloatVirtualRegister(riscFunction.floatVirtualRegisterIndex++);
        return fvr;
    }

    public RealRegister getTempRegister() {
        return tempRegister;
    }

    public static boolean isPowerOf2(int x) {
        return x > 0 && ((x & (x - 1)) == 0);
    }

    public static int log2(int x) {
        if (x <= 0) {
            System.out.println("BUG is log2");
            return -1;
        }
        return (int) (Math.log(x)/Math.log(2));
        //  int count = 0;
//        while (x > 1) {
//            x >>= 1;
//            count++;
//        }
//        return count;
    }
}
