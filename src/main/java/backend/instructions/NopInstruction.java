package backend.instructions;

import backend.RISCInstruction;

public class NopInstruction extends RISCInstruction {
    @Override
    public boolean isDef(int position) {
        return false;
    }

    public NopInstruction() {
        this.type = ITYPE.nop;
    }
}
