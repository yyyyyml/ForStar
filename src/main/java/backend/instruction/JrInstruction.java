package backend.instruction;

import backend.RISCinstruction;
import backend.Riscvalues.Register;
import ir.Instruction;

public class JrInstruction extends RISCinstruction {

    public Register reg;

    public JrInstruction(Instruction iRin) {
        super(iRin);
    }

    public JrInstruction(int i) {
        reg=new Register(i);
        this.type = ITYPE.jr;
    }

    public String emit() {
        return this.type.name()+" "+reg.type.name();
    }
}
