package pass.backend;

import backend.RISCClass;

/**
 * 所有后端优化方法的接口
 * 都要实现run方法
 */
public interface BaseBackendPass {
    public void run(RISCClass target);
}
