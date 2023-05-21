package backend.operands;

import backend.RISCOperand;

public class Memory extends RISCOperand {
    public RealRegister basicAddress;
    private int offset;

    public Memory(int offset, int basicAddress) {
        this.offset = offset;
        this.basicAddress = new RealRegister(basicAddress);
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String emit() {
        if (offset == 0) {
            return "(" + basicAddress.regType.name() + ")";
        } else {
            return offset + "(" + basicAddress.regType.name() + ")";
        }
    }
}
