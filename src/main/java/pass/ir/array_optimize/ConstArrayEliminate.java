package pass.ir.array_optimize;

import ir.Instruction;
import ir.Instructions.MemoryInst;
import ir.Module;
import ir.Use;
import ir.User;
import ir.types.ArrayType;
import ir.values.Constant;
import pass.ir.BaseIRPass;

public class ConstArrayEliminate implements BaseIRPass {
    @Override
    public boolean run(Module module) {
        for (int i = 0; i < module.globalVariableList.size(); i++) {
            var gv = module.globalVariableList.get(i);
            if (gv.init instanceof Constant.ConstantArray initArr) {
                // 是全局数组
                if (canEliminate(gv)) {
                    System.out.println("可以消除常数数组");
                    // 没有被重新赋值过，可以消除
                    doEliminate(gv, initArr);
//                    module.globalVariableList.remove(i);
//                    i--;
                }

            }
        }
        return false;
    }

    private void doEliminate(User addr, Constant.ConstantArray curArr) {
        System.out.println("doEliminate");
        ArrayType arrType = (ArrayType) curArr.getType();
        System.out.println(addr.useList.size());
        for (int i = 0; i < addr.useList.size(); i++) {
            Use use = addr.useList.get(i);
            Instruction gep = (Instruction) use.getUser();
            // addr一定会被GEP使用
            if (gep instanceof MemoryInst.GEP gepInst) {
                Constant.ConstantInt gepIndexValue = (Constant.ConstantInt) gepInst.getOperandAt(2); // 必须是常数
                int gepIndex = gepIndexValue.getVal();
                // 对于每个GEP，load使用gep
                for (int j = 0; j < gepInst.useList.size(); j++) {
                    Use useOfGEP = gepInst.useList.get(j);
                    Instruction user = (Instruction) useOfGEP.getUser();
                    if (user instanceof MemoryInst.Load loadInst) {
                        // 说明addr是最后一层数组
                        loadInst.replaceAllUseWith(curArr.getOperandAt(gepIndex)); // 把load替换成那个数组中的常数元素
                        loadInst.removeAllOperand();
                        loadInst.node.removeSelf();
                    }
//                    if (user instanceof MemoryInst.GEP) {
//                        // 说明addr还嵌套有其他数组，需要递归调用
//                    }
                }
//                gepInst.removeAllOperand(); // 加这个也会错
                gep.node.removeSelf();
            }
        }

    }

    private boolean canEliminate(User addr) {
        for (int i = 0; i < addr.useList.size(); i++) {
            Use use = addr.useList.get(i);
            Instruction gep = (Instruction) use.getUser();
            if (gep instanceof MemoryInst.GEP gepInst) {
                System.out.println("gep: " + gepInst);
                if (!(gepInst.getOperandAt(2) instanceof Constant.ConstantInt)) {
                    System.out.println("不是常数索引");
                    return false; // 如果不是常数索引，不能处理
                }
                for (int j = 0; j < gepInst.useList.size(); j++) {
                    Use useOfGEP = gepInst.useList.get(j);
                    Instruction user = (Instruction) useOfGEP.getUser();
                    if (user instanceof MemoryInst.Store) return false; // 说明addr是最后一层数组，且被赋值
                    if (user instanceof MemoryInst.GEP) {
                        System.out.println("多层数组" + user);
                        return false; // 先只处理单层数组，多层不能处理
//                        return canEliminate(gepInst); // 说明addr还嵌套有其他数组，需要递归调用
                    }
                }
            }
        }
        return true;
    }
}
