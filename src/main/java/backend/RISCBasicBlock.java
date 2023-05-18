package backend;

import backend.operands.*;
import backend.instructions.*;
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
     *此构造函数为创建函数的初始块和结束块
     */
    public RISCBasicBlock(int i, Function irFunc, RISCFunction riscFunction) {
        this.riscFunction = riscFunction;
        this.irFunction = irFunc;
        //初始块
        if(i==0){
            int imm = 0;
            imm = riscFunction.localStackIndex;
            AddiInstruction addi1=new AddiInstruction(new RealRegister(2),new RealRegister(2),new Immediate(-imm));
            instructionList.add(addi1);
            SdInstruction sd1=new SdInstruction(new RealRegister(3),new Memory(imm-8,2));
            instructionList.add(sd1);
            SdInstruction sd2=new SdInstruction(new RealRegister(1),new Memory(imm-16,2));
            instructionList.add(sd2);
            AddiInstruction addi2=new AddiInstruction(new RealRegister(1),new RealRegister(2),new Immediate(imm));
            instructionList.add(addi2);
            if(irFunc.getParamList().size()!=0){
                ;
            }
        }
        //结束块
        else{
            int imm=0;
            imm = riscFunction.localStackIndex;
            LdInstruction ld1=new LdInstruction(new RealRegister(3),new Memory(imm-8,2));
            instructionList.add(ld1);
            LdInstruction ld2=new LdInstruction(new RealRegister(1),new Memory(imm-16,2));
            instructionList.add(ld2);
            AddiInstruction add1=new AddiInstruction(new RealRegister(2),new RealRegister(2),new Immediate(imm));
            instructionList.add(add1);
            JrInstruction jr1=new JrInstruction(new RealRegister(3));
            instructionList.add(jr1);
        }
    }



    public LinkedList<RISCInstruction> getInstructionList(){
        return instructionList;
    }

    /**
     * 遍历BB中的指令
     * 转换为Risc-V
     * @param iRbb
     */
    public RISCBasicBlock(BasicBlock iRbb,Function iRfunc,RISCFunction RiscFunction) {
        this.riscFunction =RiscFunction;
            this.irFunction =iRfunc;
            for(IList.INode<Instruction, BasicBlock> Inode : iRbb.list){
                Instruction curInst=Inode.getElement();
                switch (curInst.getTag()){
                    case RET ->  translateRet(curInst);
                    case ALLOCA -> translateAlloca(curInst);
                    case STORE -> translateStore(curInst);
                }

            }
    }

    private void translateStore(Instruction curInst) {
        Value op1 = curInst.getOperandAt(0);
        Value op2 = curInst.getOperandAt(1);
        if(op1 instanceof Constant.ConstantInt){
            RISCOperand rOp1 = getOperand(op1);
            RISCOperand rop2 = getOperand(op2);
            SwInstruction sw1 = new SwInstruction(rOp1,rop2);
            instructionList.add(sw1);

        }
    }

    private void translateAlloca(Instruction curInst) {
        if(((PointerType) curInst.getType()).getPointedType() == Type.IntegerType.getType()) {
            VirtualRegister vr = new VirtualRegister(riscFunction.virtualRegisterIndex++,curInst );
            riscFunction.valueVRMap.put(curInst,vr);
            AddiInstruction add1 = new AddiInstruction(vr,new RealRegister(1),new Immediate(-riscFunction.localStackIndex));
            instructionList.add(add1);
            riscFunction.localStackIndex+=4;


        }
    }



    private RISCOperand getOperand(Value value){
        if(value instanceof Constant){
            int val=((Constant.ConstantInt)value).getVal();
            RISCOperand temp=new Immediate(val);
            return temp;
        }
        else if(riscFunction.valueVRMap.containsValue(value)){
            return riscFunction.valueVRMap.get(value);
        }
        else{
            VirtualRegister vr=new VirtualRegister(riscFunction.virtualRegisterIndex++,value);
            riscFunction.valueVRMap.put(value,vr);
            return vr;
        }





    }



    /**
     * RET
     * @param curInst
     */
    private void translateRet(Instruction curInst) {
            if(curInst.getNumOP()==0){
                NopInstruction nop1=new NopInstruction();
                instructionList.add(nop1);
            }

            else if(curInst.getOperandAt(0).getType().isIntegerType()){
                int imm=((Constant.ConstantInt)curInst.getOperandAt(0)).getVal();
                LiInstruction li1=new LiInstruction(new RealRegister(10),new Immediate(imm));
                instructionList.add(li1);
                MvInstruction mv1=new MvInstruction(new RealRegister(9),new RealRegister(10));
                instructionList.add(mv1);
            }

    }


}
