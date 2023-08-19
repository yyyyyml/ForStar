package pass;

import backend.RISCModule;
import ir.Module;
import pass.backend.BaseBackendPass;
import pass.backend.basic_optimize.BasicOptimize;
import pass.backend.basic_optimize.MultipleOptimize;
import pass.backend.large_number.LargeNumberPass;
import pass.backend.loadstore_merge.LoadStoreMerge;
import pass.backend.register_allocator.FloatRegisterAllocator;
import pass.backend.register_allocator.NNFloatRegAllocator;
import pass.backend.register_allocator.NNRegAllocator;
import pass.backend.register_allocator.RegisterAllocator;
import pass.ir.BaseIRPass;
import pass.ir.addmerge.AddConstMerge;
import pass.ir.addmerge.AddSameMerge;
import pass.ir.blockmerge.BlockMerge;
import pass.ir.blockmerge.BlockMergeWithPhi;
import pass.ir.constantexp_derivation.ConstantExp_Derivation;
import pass.ir.constarray_eliminate.ConstArrayEliminate;
import pass.ir.cse.CommonSubexpressionElimination;
import pass.ir.deadcode_eliminate.DeadCodeEliminate;
import pass.ir.global2local.GlobalToLocal;
import pass.ir.globalvariablederive.GlobalVariableDerive;
import pass.ir.inline.Inline;
import pass.ir.mem2reg.Mem2Reg;
import pass.ir.multiret_eliminate.MultiRetEliminate;
import pass.ir.phimerge.PhiMerge;

import java.util.ArrayList;

/**
 * 优化器类
 * 驱动所有优化
 * 按加入顺序执行优化
 */
public class PassDriver {
    ArrayList<BaseIRPass> irPassList;
    ArrayList<BaseBackendPass> backendPassList;
    boolean isPass;

    public PassDriver(boolean isPass) {
        this.isPass = isPass;
        this.isPass = true;
        irPassList = new ArrayList<>();
        backendPassList = new ArrayList<>();
    }

    public void runIR(Module module) {
        if (isPass) {
            new MultiRetEliminate().run(module); // 消除没用的ret
            new BlockMerge().run(module); // 先做基本块合并，有phi以后比较麻烦
            new Mem2Reg().run(module); // 有phi了
            new PhiMerge().run(module); // 合并一下可合并的phi
            // Inline前不太能做很多优化，会导致Inline出问题
            new Inline().run(module); // 隐藏功能用例36CE,但性能用例全AC ——> 已经没问题了，现在全部AC
            new GlobalVariableDerive().run(module); // 只初始化一次的全局变量当作常量传播
            new GlobalToLocal().run(module); // 全局变量转局部,要inline后做，里面自带一个mem2reg和phiMerge
            new ConstArrayEliminate().run(module); // 一维常量数组展开
            basicSimplify(module); // 基础优化（常量传播，死块消除，连续加法，phi合并，死代码消除）


//            new NoPhi().run(module); // 后端前最后一步，尝试把phi替换掉，不SSA
        } else {
            new MultiRetEliminate().run(module); // 消除没用的ret
            new BlockMerge().run(module); // 先做基本块合并，有phi以后比较麻烦
        }
//        for (BaseIRPass passClass : irPassList) {
//            passClass.run(module);
//        }
    }

    public void runBackend(RISCModule riscModule) {
        if (isPass) {
            // 后端
            new MultipleOptimize().run(riscModule);
            new NNRegAllocator().run(riscModule); // 有活跃变量分析的寄存器分配
            new NNFloatRegAllocator().run(riscModule); // 有活跃变量分析的寄存器分配
            new LargeNumberPass().run(riscModule); // 一些指令12位立即数的限制处理
            new LoadStoreMerge().run(riscModule);
            new BasicOptimize().run(riscModule);
        } else {
            new RegisterAllocator().run(riscModule); // 普通的寄存器分配
            new FloatRegisterAllocator().run(riscModule); // 普通的寄存器分配
            new LargeNumberPass().run(riscModule); // 一些指令12位立即数的限制处理
        }
//        for (BaseBackendPass passClass : backendPassList) {
//            passClass.run(riscModule);
//        }

    }

    // 都是些可以重复做的优化
    private void basicSimplify(Module module) {
        boolean needDo = true;
        while (needDo) {
            needDo = false;
            needDo |= new ConstantExp_Derivation().run(module); // 常量传播
            needDo |= new BlockMergeWithPhi().run(module); // 常量传播后可以用一下，目前只是消除死基本块
            needDo |= new AddConstMerge().run(module); // 多个常数相加
            needDo |= new CommonSubexpressionElimination().run(module); // 简单的子表达式消除
            needDo |= new AddSameMerge().run(module); // 相同的数连续相加改为乘法
            needDo |= new PhiMerge().run(module); // 合并一下可合并的phi
            needDo |= new DeadCodeEliminate().run(module); // 可以用了，但效果似乎一般
//            needDo = false;
        }

    }


}
