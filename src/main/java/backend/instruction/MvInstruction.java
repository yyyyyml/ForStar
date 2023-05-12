package backend.instruction;

import backend.RISCinstruction;
import backend.Riscvalues.Register;
import ir.Instruction;

public class MvInstruction extends RISCinstruction {

    public Register reg1;
    public Register reg2;

    public MvInstruction(Instruction iRin) {
        super(iRin);
    }

    @Override
    public String emit() {
        return this.type.name()+" "+reg1.type.name()+","+reg2.type.name();
    }
}
