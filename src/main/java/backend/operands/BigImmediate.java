package backend.operands;

import backend.RISCOperand;

public class BigImmediate extends RISCOperand {
    private long val;

    public BigImmediate(long imm) {
        super();
        val = imm;
    }

    public long getVal() {
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
