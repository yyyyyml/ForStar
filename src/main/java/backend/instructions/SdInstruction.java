package backend.instructions;

import backend.RISCInstruction;
import backend.RISCOperand;

public class SdInstruction extends RISCInstruction {
    public RISCOperand op1;
    public RISCOperand op2;

    public SdInstruction(RISCOperand r1, RISCOperand memory) {
        this.operandNum = 2;
        this.op1 = r1;
        this.setOperand(op1, 0);
        this.op2 = memory;
        this.setOperand(op2, 1);
        this.type = ITYPE.sd;
    }

    @Override
    public boolean isDef(int position) {
        if (position == 1) return true;
        else return false;
    }

    public String emit() {
//        return this.type.name()+" "+op1.emit()+","+op2.emit();
        return this.type.name() + " " + getOperandAt(0).emit() + "," + getOperandAt(1).emit();
    }
}
