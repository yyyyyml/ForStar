package backend.operands;

import backend.RISCOperand;

public class Immediate extends RISCOperand {
    public int val;

    public Immediate(int imm) {
        super();
        val = imm;
    }

    @Override
    public String emit() {
        return String.valueOf(val);
    }
}