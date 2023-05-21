package backend.operands;

import backend.RISCOperand;

public class Immediate extends RISCOperand {
    private int val;

    public Immediate(int imm) {
        super();
        val = imm;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    @Override
    public String emit() {
        return String.valueOf(val);
    }
}
