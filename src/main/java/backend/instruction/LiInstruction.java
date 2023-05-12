package backend.instruction;

import backend.RISCinstruction;
import ir.Instruction;

public class LiInstruction extends RISCinstruction {

    public int imm;

    public LiInstruction(Instruction iRin) {
        super(iRin);
    }


    public String emit() {
        return this.type.name()+" "+Integer.toString(imm);
    }
}
