package backend.operands;

public class FloatVirtualRegister extends Register {
    private int index;

    public FloatVirtualRegister(int i) {
        this.index = i;
    }

    public String emit() {
        return "vr_f" + index;
    }
}
