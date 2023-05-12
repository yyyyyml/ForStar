package backend.instruction;

import backend.RISCinstruction;

public class NopInstruction extends RISCinstruction {
    public NopInstruction(){
        this.type=ITYPE.nop;
    }
}
