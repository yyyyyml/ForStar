package backend.instructions;

import backend.RISCBasicBlock;
import backend.RISCInstruction;
import backend.RISCOperand;

public class BnezInstruction extends RISCInstruction {
    public RISCOperand op1;
    public RISCOperand op2;


    public BnezInstruction(RISCOperand op1, RISCOperand op2,RISCBasicBlock BB) {
        this.operandNum = 2;
        this.op1 = op1;
        this.setOperand(op1, 0);
        this.op2 = op2;
        this.setOperand(op2, 1);
        this.type = ITYPE.bnez;
        //  System.out.println("ADD TO LIST " + op3.emit() );
        BB.nextBlocknameList.add(op2.emit());
    }

    @Override
    public boolean isDef(int position) {
        return false;
    }

    @Override
    public String emit() {
        return this.type.name() + " " + getOperandAt(0).emit() + "," + getOperandAt(1).emit() ;
    }
}
