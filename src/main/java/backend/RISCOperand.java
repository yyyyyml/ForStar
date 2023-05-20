package backend;

import backend.operands.*;

/**
 * 注意：position变量目前已被废弃
 * 因为同个寄存器可能在不同指令有不同位置
 * 暂时这个变量没什么用 但是之前的一些get set先不改了
 * 现在将operandList直接按顺序保存
 */
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
