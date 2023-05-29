package backend.operands;

import backend.RISCOperand;

public class Memory extends RISCOperand {
    public Register basicAddress;
    private int offset;

    public Memory(int offset, int basicAddress) {
        this.offset = offset;
        this.basicAddress = new RealRegister(basicAddress);
    }

    public Memory(int offset, Register basicAddress) {
        this.basicAddress = basicAddress;
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String emit() {
        return offset + "(" + basicAddress.emit() + ")";

    }
}
