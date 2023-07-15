package backend.instructions;

import backend.RISCInstruction;
import backend.RISCOperand;

public class SltInstruction extends RISCInstruction {
    public RISCOperand op1;
    public RISCOperand op2;
    public RISCOperand op3;

    public SltInstruction(RISCOperand op1, RISCOperand op2, RISCOperand op3) {
        this.operandNum = 3;
        this.op1 = op1;
        this.setOperand(op1, 0);
        this.op2 = op2;
        this.setOperand(op2, 1);
        this.op3 = op3;
        this.setOperand(op3, 2);
        this.type = ITYPE.slt;
    }

    @Override
    public String emit() {
        return this.type.name() + ".s " + getOperandAt(0).emit() + "," + getOperandAt(1).emit() + "," + getOperandAt(2).emit();
    }
}