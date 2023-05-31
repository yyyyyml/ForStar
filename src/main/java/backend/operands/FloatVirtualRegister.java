package backend.operands;

import ir.Value;

public class FloatVirtualRegister extends Register {
    private int name;
    private Value value;
    // 下面变量用于寄存器分配
    private int realReg = -1;
    private int stackLocation;
    private int spillTime = Integer.MAX_VALUE;
    private FloatVirtualRegister vRegReplaced = null;

    public FloatVirtualRegister(int i, Value value) {
        this.name = i;
        this.value = value;
    }

    public FloatVirtualRegister(int i) {
        super();
        this.name = i;
        this.value = null;
    }

    public int getName() {
        return name;
    }

    public Value getValue() {
        return value;
    }

    public int getRealReg() {
        return realReg;
    }

    public void setRealReg(int realReg) {
        this.realReg = realReg;
    }

    public String emit() {
        return "vr_f" + name;
    }

    public int getStackLocation() {
        return stackLocation;
    }

    public void setStackLocation(int stackLocation) {
        this.stackLocation = stackLocation;
    }

    public int getSpillTime() {
        return spillTime;
    }

    public void setSpillTime(int spillTime) {
        this.spillTime = spillTime;
    }

    public FloatVirtualRegister getvRegReplaced() {
        return vRegReplaced;
    }

    public void setvRegReplaced(FloatVirtualRegister vRegReplaced) {
        this.vRegReplaced = vRegReplaced;
    }
}