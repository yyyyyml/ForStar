package pass.ir.global2local;

import ir.Instruction;
import ir.Instructions.MemoryInst;
import ir.Module;
import ir.types.PointerType;
import ir.values.BasicBlock;
import ir.values.Constant;
import ir.values.Function;
import ir.values.GlobalVariable;
import pass.ir.BaseIRPass;
import pass.ir.mem2reg.Mem2Reg;
import pass.ir.phimerge.PhiMerge;
import util.IList;

import java.util.HashMap;
import java.util.Map;

public class GlobalToLocal implements BaseIRPass {
    Map<GlobalVariable, Instruction> gvToMove = new HashMap<>();

    @Override
    public void run(Module module) {
        if (module.functionList.size() != 14) {
//            System.out.println("函数数量："+module.functionList.size());
//            for (IList.INode<Function, Module> funcInode : module.functionList) {
//                Function func = funcInode.getElement();
//                System.out.println(func);
//            }
            return; // 如果除了main还有别的函数，不做这个优化
        }
        System.out.println("可以做GlobalToLocal");
        boolean needMem2reg = false;
        for (int i = 0; i < module.globalVariableList.size(); i++) {
            var gv = module.globalVariableList.get(i);
            PointerType gvType = (PointerType) gv.getType();
            Constant init = gv.init;
            // 处理int和float全局变量
            if (gvType.getPointedType().isIntegerType() || gvType.getPointedType().isFloatType()) {
//                Value valueToReplace = new Instruction(Type.IntegerType.getType(), );
                MemoryInst.Alloca allocInst = new MemoryInst.Alloca(gvType.getPointedType());
                gvToMove.put(gv, allocInst); // 先存初始值
                module.globalVariableList.remove(i);
                i--;
                needMem2reg = true;
            }
        }
        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            if (func.isBuiltin()) continue;
            BasicBlock fisrtBB = func.list.getFirst().getElement();
            for (Map.Entry<GlobalVariable, Instruction> entry : gvToMove.entrySet()) {
                GlobalVariable gv = entry.getKey();
                Instruction allocInst = entry.getValue();
                Constant init = gv.init;
                Instruction storeInst = new MemoryInst.Store(init, allocInst);
                fisrtBB.list.addFirst(storeInst.node); // 先把初始值存了
            }
            for (Map.Entry<GlobalVariable, Instruction> entry : gvToMove.entrySet()) {
                GlobalVariable gv = entry.getKey();
                Instruction allocInst = entry.getValue();
                fisrtBB.list.addFirst(allocInst.node); // 再存alloca,保证alloca都在最前面
                gv.replaceAllUseWith(allocInst);
            }


//            for (IList.INode<BasicBlock, Function> bbInode : func.list) {
//                BasicBlock bb = bbInode.getElement();
//                for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {
//                    Instruction inst = instInode.getElement();
////                    // 如果有load要转化的全局变量的情况
////                    if (inst instanceof MemoryInst.Load loadInst && gvToMove.containsKey(loadInst.getOperandAt(0))) {
////                        // 可能需要phi？
//
//                    }
//                }
//            }
        }

        // mem2reg
        if (needMem2reg) {
            var mem2reg = new Mem2Reg();
            var phiMerge = new PhiMerge();
            mem2reg.run(module);
            phiMerge.run(module);

        }


    }
}
