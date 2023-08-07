package pass;

import backend.RISCModule;
import ir.Module;
import pass.backend.BaseBackendPass;
import pass.backend.large_number.LargeNumberPass;
import pass.backend.register_allocator.FloatRegisterAllocator;
import pass.backend.register_allocator.NNFloatRegAllocator;
import pass.backend.register_allocator.NNRegAllocator;
import pass.backend.register_allocator.RegisterAllocator;
import pass.ir.BaseIRPass;
import pass.ir.addmerge.AddConstMerge;
import pass.ir.blockmerge.BlockMerge;
import pass.ir.cse.CommonSubexpressionElimination;
import pass.ir.globalvariablederive.GlobalVariableDerive;
import pass.ir.inline.Inline;
import pass.ir.mem2reg.Mem2Reg;
import pass.ir.multiret_eliminate.MultiRetEliminate;

import java.util.ArrayList;

/**
 * 优化器类
 */
public class PassDriver {
    ArrayList<BaseIRPass> irPassList;
    ArrayList<BaseBackendPass> backendPassList;

    public PassDriver(boolean isPass) {
        isPass = true;
        irPassList = new ArrayList<>();
        backendPassList = new ArrayList<>();

        irPassList.add(new MultiRetEliminate());
        irPassList.add(new BlockMerge());
        if (isPass) irPassList.add(new Mem2Reg());
        if (isPass) irPassList.add(new AddConstMerge());
        if (isPass) irPassList.add(new GlobalVariableDerive());
        if (isPass) irPassList.add(new CommonSubexpressionElimination());
        if (isPass) irPassList.add(new Inline());
        if (isPass) irPassList.add(new CommonSubexpressionElimination());
//        if (isPass) irPassList.add(new AddSameMerge());
//        irPassList.add(new DeadCodeEliminate()); // TODO:有问题,不能用

        if (isPass) backendPassList.add(new NNRegAllocator());
        if (isPass) backendPassList.add(new NNFloatRegAllocator());
//       backendPassList.add(new NewRegAllocator());
        if (!isPass) backendPassList.add(new RegisterAllocator());
//       backendPassList.add(new NewFloatRegAllocator());
        if (!isPass) backendPassList.add(new FloatRegisterAllocator());
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
