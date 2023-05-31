package pass.backend.RegisterAllocator;

/**
 * 寄存器使用情况类
 */
public class RegisterUsage {
    private int regNum; // 寄存器数量
    private int bitmap; // 位图表示寄存器的使用情况

    public RegisterUsage(int regNum) {
        this.regNum = regNum;
        this.bitmap = 0; // 初始时所有寄存器都是空闲的
    }

    public int getBitmap() {
        return bitmap;
    }

    public int getRegNum() {
        return regNum;
    }

    public boolean isRegisterUsed(int register) {
        return ((bitmap >> register) & 1) == 1; // 检查寄存器是否被占用
    }

    public void allocateRegister(int register) {
        bitmap |= (1 << register); // 将寄存器标记为占用
    }

    public void freeRegister(int register) {
        bitmap &= ~(1 << register); // 将寄存器标记为空闲
    }

    public void copyFrom(RegisterUsage other) {
        this.bitmap = other.bitmap;
    }

    public int getNextFreeRegister() {
        int register = 0;
        while (register < regNum) {
            if (!isRegisterUsed(register)) {
                this.allocateRegister(register);
                return register; // 返回空闲寄存器
            }
            register++;
        }
        // 如果没有空闲寄存器，则返回一个特殊值（例如 -1）表示无法分配
        return -1;
    }

}
