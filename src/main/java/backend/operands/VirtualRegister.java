package backend.operands;

import ir.Value;

public class VirtualRegister extends Register{
    private int name;
    private Value value;
    private int realReg;
    private int stackLocation;

    public VirtualRegister(int i, Value value) {
        this.name = i;
        this.value = value;
    }

    public VirtualRegister(int i) {
        super();
        this.name = i;
        this.value = null;
    }

    public int getName(){return name;}

    public Value getValue(){return value;}

    public int getRealReg() {
        return realReg;
    }

    public void setRealReg(int realReg) {
        this.realReg = realReg;
    }

    public String emit(){return "vr_"+name;}

    public int getStackLocation() {
        return stackLocation;
    }

    public void setStackLocation(int stackLocation) {
        this.stackLocation = stackLocation;
    }
}
