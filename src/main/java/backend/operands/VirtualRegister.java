package backend.operands;

import ir.Value;

public class VirtualRegister extends Register {
    private int name;
    private Value value;
    // 下面变量用于寄存器分配
    private int realReg = -1;
    private int stackLocation;
    private int spillTime = Integer.MAX_VALUE;
    private VirtualRegister vRegReplaced = null;
    public boolean isSpilled = false;

    public VirtualRegister(int i, Value value) {
        this.name = i;
        this.value = value;
    }

    public VirtualRegister(int i) {
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
        return "vr_" + name;
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

    public VirtualRegister getvRegReplaced() {
        return vRegReplaced;
    }

    public void setvRegReplaced(VirtualRegister vRegReplaced) {
        this.vRegReplaced = vRegReplaced;
    }
}