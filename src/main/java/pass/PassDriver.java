package pass;

import backend.RISCModule;
import ir.Module;
import pass.backend.BaseBackendPass;
import pass.backend.RegisterAllocator;
import pass.ir.BaseIRPass;

import java.util.ArrayList;

/**
 * 优化器类
 */
public class PassDriver {
    ArrayList<BaseIRPass> irPassList;
    ArrayList<BaseBackendPass> backendPassList;

    public PassDriver() {
        irPassList = new ArrayList<>();
        backendPassList = new ArrayList<>();

        backendPassList.add(new RegisterAllocator());
    }

    public void runIR(Module module) {
        for (BaseIRPass passClass : irPassList) {
            passClass.run(module);
        }
    }

    public void runBackend(RISCModule riscModule) {
        for (BaseBackendPass passClass : backendPassList) {
            passClass.run(riscModule);
        }
    }


}
