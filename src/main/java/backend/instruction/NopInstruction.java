package backend.instruction;

import backend.RISCInstruction;

public class NopInstruction extends RISCInstruction {
    public NopInstruction(){
        this.type=ITYPE.nop;
    }
}
