package backend.instructions;

import backend.RISCInstruction;
import backend.RISCOperand;

public class FswInstruction extends RISCInstruction {
    public RISCOperand op1;
    public RISCOperand op2;

    public FswInstruction(RISCOperand o1, RISCOperand o2) {
        this.operandNum = 2;
        this.op1 = o1;
        this.setOperand(op1, 0);
        this.op2 = o2;
        this.setOperand(op2, 1);
        this.type = RISCInstruction.ITYPE.fsw;
    }

    public boolean isDef(int position) {
        if (position == 1) return true;
        else return false;
    }

    public String emit() {
//        return this.type.name()+" "+op1.emit()+","+op2.emit();
        return this.type.name() + " " + getOperandAt(0).emit() + "," + getOperandAt(1).emit();
    }

}
