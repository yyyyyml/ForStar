package pass.backend.RegisterAllocator;

import java.util.HashMap;

/**
 * 记录不同时间点的寄存器使用情况
 */
public class RegisterUsageTracker {
    private HashMap<Integer, RegisterUsage> registerUsageMap;
    private int regNum;

    public RegisterUsageTracker(int regNum) {
        registerUsageMap = new HashMap<>();
        RegisterUsage registerUsage = new RegisterUsage(regNum);
        registerUsageMap.put(0, registerUsage);
        this.regNum = regNum;
    }

    public HashMap<Integer, RegisterUsage> getRegisterUsageMap() {
        return registerUsageMap;
    }

    // 找这个时间点的上一个时间点的RegisterUsage
    public RegisterUsage getPreRegisterUsage(int timePoint) {
        RegisterUsage registerUsage = registerUsageMap.get(timePoint - 1);
        if (registerUsage == null) return getPreRegisterUsage(timePoint - 1);
        else return registerUsage;
    }

    public RegisterUsage getRegisterUsage(int timePoint) {
        // 找当前时间点有没有
        RegisterUsage registerUsage = registerUsageMap.get(timePoint);
        if (registerUsage == null) {
            // 没有找到，新建
            RegisterUsage previousUsage = getPreRegisterUsage(timePoint);
            registerUsage = new RegisterUsage(regNum); // 创建新的RegisterUsage对象
            registerUsage.copyFrom(previousUsage); // 从前一个RegisterUsage对象复制状态
            registerUsageMap.put(timePoint, registerUsage);
        }
        // 找到了，返回
        return registerUsage;
    }
}
