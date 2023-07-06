package backend.instructions;

import backend.RISCInstruction;
import backend.RISCOperand;

public class JInstruction extends RISCInstruction {
    public RISCOperand op1;

    public JInstruction(RISCOperand op1) {
        this.operandNum = 1;
        this.op1 = op1;
        this.setOperand(op1, 0);
        this.type = ITYPE.j;
    }

    @Override
    public String emit() {
        return this.type.name() + " " + getOperandAt(0).emit();
    }
}
