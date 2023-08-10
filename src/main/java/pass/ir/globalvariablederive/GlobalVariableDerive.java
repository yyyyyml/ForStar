package pass.ir.globalvariablederive;

import ir.Instruction;
import ir.Instructions.MemoryInst;
import ir.Module;
import ir.Use;
import ir.values.Constant;
import pass.ir.BaseIRPass;

public class GlobalVariableDerive implements BaseIRPass {
    @Override
    public void run(Module module) {
        boolean isStore = false;
        for (int i = 0; i < module.globalVariableList.size(); i++) {
            var gv = module.globalVariableList.get(i);
            isStore = false;
            for (Use use : gv.useList) {
                // 如果被另外赋值过，不能传播
                if (use.getUser() instanceof MemoryInst.Store ||
                        use.getUser() instanceof MemoryInst.GEP) {
                    isStore = true;
                    break;
                }
            }
            if (isStore) continue; // 不能传播
            for (Use use : gv.useList) {
                // 进行传播
                var ldInst = (Instruction) use.getUser();
                Constant newConst = gv.init;
                ldInst.replaceAllUseWith(newConst);
//                ldInst.removeAllOperand();//TODO:我不知道为什么加这个会错
                ldInst.node.removeSelf();
            }
            module.globalVariableList.remove(i);
            i--;
        }
    }
}
