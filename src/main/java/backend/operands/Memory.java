package backend.operands;

import backend.RISCOperand;

public class Memory extends RISCOperand {
    public RealRegister basicAddress;
    public int offset;

    public Memory(int offset, int basicAddress) {
        this.offset = offset;
        this.basicAddress = new RealRegister(basicAddress);
    }

    public String emit() {
        if (offset == 0) {
            return "(" + basicAddress.regType.name() + ")";
        } else {
            return Integer.toString(offset) + "(" + basicAddress.regType.name() + ")";
        }
    }
}
