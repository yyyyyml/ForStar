package backend.operands;

import ir.Value;

public class VirtualRegister extends Register{
    private int name;
    private Value value;

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

    public String emit(){return "vr_"+name;}

}
