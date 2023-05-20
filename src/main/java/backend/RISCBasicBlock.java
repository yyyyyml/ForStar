package backend;

import backend.instructions.*;
import backend.operands.Immediate;
import backend.operands.Memory;
import backend.operands.RealRegister;
import backend.operands.VirtualRegister;
import ir.Instruction;
import ir.Type;
import ir.Value;
import ir.types.PointerType;
import ir.values.BasicBlock;
import ir.values.Constant;
import ir.values.Function;
import util.IList;

import java.util.LinkedList;

public class RISCBasicBlock {
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
            imm = riscFunction.localStackIndex;
            AddiInstruction addi1 = new AddiInstruction(new RealRegister(2), new RealRegister(2), new Immediate(-imm));
            instructionList.add(addi1);
            SdInstruction sd1 = new SdInstruction(new RealRegister(3), new Memory(imm - 8, 2));
            instructionList.add(sd1);
            SdInstruction sd2 = new SdInstruction(new RealRegister(1), new Memory(imm - 16, 2));
            instructionList.add(sd2);
            AddiInstruction addi2 = new AddiInstruction(new RealRegister(1), new RealRegister(2), new Immediate(imm));
            instructionList.add(addi2);
            if (irFunc.getParamList().size() != 0) {
                ;
            }
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
            }

        }
    }

    public LinkedList<RISCInstruction> getInstructionList() {
        return instructionList;
    }

    private void translateCaculate(Instruction curInst) {
        Value vop1 = curInst.getOperandAt(0);
        RISCOperand op1 = getOperand(vop1);
        Value vop2 = curInst.getOperandAt(1);
        RISCOperand op2 = getOperand(vop2);
        VirtualRegister temp1 = new VirtualRegister(riscFunction.virtualRegisterIndex++);
        VirtualRegister temp2 = new VirtualRegister(riscFunction.virtualRegisterIndex++);
        LwInstruction lw1 = new LwInstruction(temp1, op1);
        instructionList.add(lw1);
        LwInstruction lw2 = new LwInstruction(temp2, op2);
        instructionList.add(lw2);
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
        }
        RISCOperand dst = getOperand(curInst);
        SwInstruction sw1 = new SwInstruction(dst, temp1);
        instructionList.add(sw1);

    }

    private void translateLoad(Instruction curInst) {
        if (curInst.getType() == Type.IntegerType.getType()) {
            Value op1 = curInst.getOperandAt(0);
            RISCOperand src = getOperand(op1);
            RISCOperand dst = getOperand(curInst);
            LwInstruction lw1 = new LwInstruction(dst, src);
            instructionList.add(lw1);

        }
    }

    private void translateStore(Instruction curInst) {
        Value op1 = curInst.getOperandAt(0);
        Value op2 = curInst.getOperandAt(1);
        if (op1 instanceof Constant.ConstantInt) {
            RISCOperand rOp1 = getOperand(op1);
            RISCOperand rop2 = getOperand(op2);
            SwInstruction sw1 = new SwInstruction(rOp1, rop2);
            instructionList.add(sw1);

        } else if (op1.getType() == Type.IntegerType.getType()) {
            RISCOperand rOp1 = getOperand(op1);
            RISCOperand rop2 = getOperand(op2);
            SwInstruction sw1 = new SwInstruction(rOp1, rop2);
            instructionList.add(sw1);
        }
    }

    private void translateAlloca(Instruction curInst) {
        if (((PointerType) curInst.getType()).getPointedType() == Type.IntegerType.getType()) {
            Memory mem = new Memory(-riscFunction.localStackIndex, 1);
            riscFunction.localStackIndex += 4;
            riscFunction.valueMemoryHashMap.put(curInst, mem);
        }
    }


    private RISCOperand getOperand(Value value) {
        if (value instanceof Constant) {
            int val = ((Constant.ConstantInt) value).getVal();
            RISCOperand temp = new Immediate(val);
            return temp;
        } else if (riscFunction.valueVRMap.containsKey(value)) {

            return riscFunction.valueVRMap.get(value);
        } else if (riscFunction.valueMemoryHashMap.containsKey(value)) {
            return riscFunction.valueMemoryHashMap.get(value);
        } else {
            VirtualRegister vr = new VirtualRegister(riscFunction.virtualRegisterIndex++, value);
            riscFunction.valueVRMap.put(value, vr);
            return vr;
        }


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
                LiInstruction li1 = new LiInstruction(new RealRegister(10), op1);
                instructionList.add(li1);
            } else {
                LwInstruction lw1 = new LwInstruction(new RealRegister(10), op1);
                instructionList.add(lw1);
            }
            MvInstruction mv1 = new MvInstruction(new RealRegister(9), new RealRegister(10));
            instructionList.add(mv1);
        } else {
            RISCOperand src = getOperand(curInst.getOperandAt(0));
            LwInstruction lw1 = new LwInstruction(new RealRegister(9), src);
            instructionList.add(lw1);

        }

    }


}
