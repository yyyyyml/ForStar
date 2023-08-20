package pass.ir.merge;

import ir.Instruction;
import ir.Instructions.MemoryInst;
import ir.Instructions.TerminatorInst;
import ir.Module;
import ir.Value;
import ir.values.BasicBlock;
import ir.values.Function;
import ir.values.GlobalVariable;
import pass.ir.BaseIRPass;
import util.IList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LoadStoreMerge implements BaseIRPass {
    Map<Value, Value> addrMapVal = new HashMap<>(); // addrMapVal保存当前这个addr中的值
    Set<Value> changedAddr = new HashSet<>(); // changedAddr保存改变了的全局变量
    Value savedAddr = null;

    @Override
    public boolean run(Module module) {
        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            for (IList.INode<BasicBlock, Function> bbInode : func.list) {
                BasicBlock bb = bbInode.getElement();
                // 以基本块为单位优化
                addrMapVal.clear();
                changedAddr.clear();
                savedAddr = null;
                doMerge(bb);
            }
        }
        return false;
    }

    private void doMerge(BasicBlock bb) {
        for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {
            Instruction inst = instInode.getElement();

            if (inst instanceof MemoryInst.Store) {
                var value = inst.getOperandAt(0);
                var addr = inst.getOperandAt(1);
                // 是全局变量，还是数组
                if (!(addr instanceof GlobalVariable)) {
                    if (savedAddr != null) {
                        addrMapVal.remove(savedAddr); // 已有，需要更新
                    }
                    addrMapVal.put(addr, value);
                    savedAddr = addr;
                } else {
                    addrMapVal.put(addr, value);
                    changedAddr.add(addr);
                    instInode.removeSelf();
                }
            }
            if (inst instanceof MemoryInst.Load) {
                // 合并load，如果addrrMapVal里有的话，就不需要这个load了，直接用里面的值
                var addr = inst.getOperandAt(0);
                if (addrMapVal.containsKey(addr)) {
                    inst.replaceAllUseWith(addrMapVal.get(addr));
                    instInode.removeSelf();
                } else {
                    addrMapVal.put(addr, inst);
                }
            }


            if (inst.isTerminator() || inst instanceof TerminatorInst.Call) {
                // 去别的块前要把merge掉的store加回来，防止出问题
                for (Map.Entry<Value, Value> entry : addrMapVal.entrySet()) {
                    Value addr = entry.getKey();
                    Value value = entry.getValue();
                    if (changedAddr.contains(addr)) {
                        MemoryInst.Store storeInst = new MemoryInst.Store(value, addr);
                        bb.list.addBefore(instInode, storeInst.node);
                    }
                }
                addrMapVal.clear();
                changedAddr.clear();
            }
        }
    }

}
