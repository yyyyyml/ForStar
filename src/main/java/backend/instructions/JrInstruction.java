package backend.instructions;

import backend.RISCInstruction;
import backend.RISCOperand;
import backend.operands.RealRegister;

public class JrInstruction extends RISCInstruction {

    public RISCOperand op;

    public JrInstruction(RealRegister reg) {
        this.operandNum = 1;
        this.op = reg;
        this.setOperand(op, 0);
        this.type = ITYPE.jr;
    }

    public String emit() {
//        return this.type.name()+" "+op.emit();
        return this.type.name() + " " + getOperandAt(0).emit();
    }
}
