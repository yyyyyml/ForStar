package backend.operands;

public class FloatRealRegister extends Register {
    private int index;

    public FloatRealRegister(int i) {
        this.index = i;
    }

    public String emit() {
        return "f" + index;
    }
}
