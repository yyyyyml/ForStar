package pass.backend.RegisterAllocator;

import java.util.HashMap;

/**
 * 记录不同时间点的寄存器使用情况
 */
public class RegisterUsageTracker {
    private HashMap<Integer, RegisterUsage> registerUsageMap;

    public RegisterUsageTracker() {
        registerUsageMap = new HashMap<>();
    }

    public void addRegisterUsage(int timePoint, RegisterUsage registerUsage) {
        if (timePoint > 0) {
            RegisterUsage previousUsage = registerUsageMap.get(timePoint - 1);
            registerUsage.copyFrom(previousUsage); // 从前一个RegisterUsage对象复制状态
        }
        registerUsageMap.put(timePoint, registerUsage);
    }

    // 获取指定时间点的RegisterUsage对象
    public RegisterUsage getRegisterUsage(int timePoint) {
        return registerUsageMap.get(timePoint);
    }


}