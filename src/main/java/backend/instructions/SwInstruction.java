package backend.instructions;

import backend.RISCInstruction;
import backend.RISCOperand;

public class SwInstruction extends RISCInstruction {
    public RISCOperand op1;
    public RISCOperand op2;

    public SwInstruction(RISCOperand op1, RISCOperand op2) {
        this.operandNum = 2;
        this.op1 = op1;
        this.setOperand(op1, 0);
        this.op2 = op2;
        this.setOperand(op2, 1);
        this.type = RISCInstruction.ITYPE.sw;
    }

    public String emit() {
//        return this.type.name()+" "+op1.emit()+","+op2.emit();
        return this.type.name() + " " + getOperandAt(0).emit() + "," + getOperandAt(1).emit();
    }
}
