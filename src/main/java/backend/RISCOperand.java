package backend;

import backend.operands.*;

public abstract class RISCOperand {
    private int position; // 操作数的顺序位置

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isRegister() {
        return this instanceof Register;
    }

    public boolean isRealRegister() {
        return this instanceof RealRegister;
    }

    public boolean isVirtualRegister() {
        return this instanceof VirtualRegister;
    }

    public boolean isImmediate() {
        return this instanceof Immediate;
    }
    public boolean isMemory() {
        return this instanceof Memory;
    }

    public  abstract String emit() ;
}
