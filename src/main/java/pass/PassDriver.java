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
import pass.ir.addmerge.AddSameMerge;
import pass.ir.blockmerge.BlockMerge;
import pass.ir.constantexp_derivation.ConstantExp_Derivation;
import pass.ir.cse.CommonSubexpressionElimination;
import pass.ir.deadcode_eliminate.DeadCodeEliminate;
import pass.ir.globalvariablederive.GlobalVariableDerive;
import pass.ir.inline.Inline;
import pass.ir.mem2reg.Mem2Reg;
import pass.ir.multiret_eliminate.MultiRetEliminate;
import pass.ir.phimerge.PhiMerge;

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

        irPassList.add(new MultiRetEliminate()); // 消除没用的ret
        irPassList.add(new BlockMerge()); // 先做基本块合并，有phi以后比较麻烦
        if (isPass) irPassList.add(new Mem2Reg()); // 有phi了
        if (isPass) irPassList.add(new PhiMerge()); // 合并一下可合并的phi
        if (isPass) irPassList.add(new ConstantExp_Derivation()); // 常量传播
        if (isPass) irPassList.add(new AddConstMerge()); // 多个常数相加
        if (isPass) irPassList.add(new GlobalVariableDerive()); // 只初始化一次的全局变量当作常量传播
        if (isPass) irPassList.add(new CommonSubexpressionElimination()); // 简单的子表达式消除
        if (isPass) irPassList.add(new Inline()); // 隐藏功能用例36CE,但性能用例全AC
        if (isPass) irPassList.add(new CommonSubexpressionElimination()); // 内联后再做一次简单的子表达式消除
        if (isPass) irPassList.add(new AddSameMerge()); // 相同的数连续相加改为乘法
        if (isPass) irPassList.add(new PhiMerge()); // 再合并一下phi
        if (isPass) irPassList.add(new DeadCodeEliminate()); // 可以用了，但效果似乎一般
//        if (isPass) irPassList.add(new BlockMergeWithPhi()); // 用不了，有的不可以消掉

        if (isPass) backendPassList.add(new NNRegAllocator()); // 有活跃变量分析的寄存器分配
        if (isPass) backendPassList.add(new NNFloatRegAllocator()); // 有活跃变量分析的寄存器分配
//       backendPassList.add(new NewRegAllocator());
        if (!isPass) backendPassList.add(new RegisterAllocator()); // 普通的寄存器分配
//       backendPassList.add(new NewFloatRegAllocator());
        if (!isPass) backendPassList.add(new FloatRegisterAllocator()); // 普通的寄存器分配
        backendPassList.add(new LargeNumberPass()); // 一些指令12位立即数的限制处理
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
