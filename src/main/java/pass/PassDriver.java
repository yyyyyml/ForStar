package pass;

import backend.RISCModule;
import ir.Module;
import pass.backend.BaseBackendPass;
import pass.backend.large_number.LargeNumberPass;
import pass.backend.register_allocator.FloatRegisterAllocator;
import pass.backend.register_allocator.RegisterAllocator;
import pass.ir.BaseIRPass;
import pass.ir.mem2reg.Mem2Reg;
import pass.ir.multiret_eliminate.MultiRetEliminate;

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

        irPassList.add(new MultiRetEliminate());
        irPassList.add(new Mem2Reg());
//        irPassList.add(new DeadCodeEliminate()); // TODO:好像mem2reg之前还不能做这个优化，有问题

        backendPassList.add(new RegisterAllocator());
        backendPassList.add(new FloatRegisterAllocator());
        backendPassList.add(new LargeNumberPass());
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
