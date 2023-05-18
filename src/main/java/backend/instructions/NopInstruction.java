package backend.instructions;

import backend.RISCInstruction;

public class NopInstruction extends RISCInstruction {
    public NopInstruction(){
        this.type=ITYPE.nop;
    }
}
