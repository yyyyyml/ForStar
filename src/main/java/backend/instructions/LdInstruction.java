package backend.instructions;

import backend.RISCInstruction;
import backend.operands.Memory;
import backend.RISCOperand;
import backend.operands.RealRegister;

public class LdInstruction extends RISCInstruction {

    public RISCOperand op1;
    public RISCOperand op2;

    public LdInstruction(RISCOperand o1, RISCOperand o2) {
        this.operandNum = 2;
        this.op1=o1;
        this.setOperand(op1, 0);
        this.op2=o2;
        this.setOperand(op2, 1);
        this.type = RISCInstruction.ITYPE.ld;
    }

    public String emit() {
        return this.type.name()+" "+op1.emit()+","+op2.emit();
    }
}