package backend.instruction;

import backend.RISCInstruction;
import backend.riscvalues.RISCOperand;
import backend.riscvalues.RealRegister;
import ir.Instruction;

public class JrInstruction extends RISCInstruction {

    public RISCOperand op;

    public JrInstruction(Instruction iRin) {
        super(iRin);
    }

    public JrInstruction(RealRegister reg) {
        op=reg;
        this.type = ITYPE.jr;
    }

    public String emit() {
        return this.type.name()+" "+op.emit();
    }
}
