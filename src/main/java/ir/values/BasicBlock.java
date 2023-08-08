package ir.values;

import ir.Instruction;
import ir.Instructions.MemoryInst;
import ir.Instructions.TerminatorInst;
import ir.Type;
import ir.Use;
import ir.Value;
import util.IList;

import java.util.*;

/**
 * 基本块类 继承自Value
 * 包含跳转逻辑 指令链表 自己所在的节点
 */
public class BasicBlock extends Value {
    public IList<Instruction, BasicBlock> list;
    public IList.INode<BasicBlock, Function> node;
    public ArrayList<BasicBlock> preList; // 跳转到这里的基本块列表
    public ArrayList<BasicBlock> nextList; // 能够跳转到的基本块列表

    // 用于mem2reg的
    public Set<MemoryInst.Alloca> npdVar = new HashSet<>();
    public Set<MemoryInst.Alloca> defVar = new HashSet<>();
    public Map<MemoryInst.Alloca, MemoryInst.Phi> phiMap = new HashMap<>();
    public Map<MemoryInst.Alloca, Value> nowDefMap = new HashMap<>();

    public BasicBlock(String name) {
        super(Type.LabelType.getType());
        this.preList = new ArrayList<>();
        this.nextList = new ArrayList<>();
        this.list = new IList<>(this);
        this.node = new IList.INode<>(this, null, null);
        this.setName(name);
    }

    public Instruction getLastInst() {
        if (list.isEmpty()) {
            return null;
        }
        return list.getLast().getElement();
    }

    public void fixPhi() {
        for (Use use : useList) {
            if (use.getUser() instanceof MemoryInst.Phi phiInst) {
//                int pos = phiInst.opMap.get(this); // 这个块所在的位置
//                Value val = phiInst.findValue(this); // 对应的value
                phiInst.removeMapping(this);
            }

        }
    }

    public void fixPhi(ArrayList<BasicBlock> bbList) {
        for (Use use : useList) {
            if (use.getUser() instanceof MemoryInst.Phi phiInst) {
                int pos = phiInst.opMap.get(this); // 这个块所在的位置
                Value val = phiInst.findValue(this); // 对应的value
                for (BasicBlock bb : bbList) {
                    if (bb == bbList.get(0)) {
                        use.setValue(bb); // 第一个先替换
                        // 还要处理opMap
                        phiInst.opMap.put(bb, pos);
                        phiInst.opMap.remove(this); // 移除旧的映射关系
                    } else {
                        // 如果不止一个要替换的块
                        phiInst.addMapping(bb, val);

                    }
                }
            }

        }
    }

    // 用于基本块的替换在phi出现的
    public void replaceAllPhiUseWith(BasicBlock v) {

        for (Use use : useList) {
            if (use.getUser() instanceof MemoryInst.Phi phiInst) {
                use.setValue(v);
                v.useList.add(use);
                // 还要处理opMap
                int oldPos = phiInst.opMap.get(this);
                phiInst.opMap.put(v, oldPos);
                phiInst.opMap.remove(this); // 移除旧的映射关系
            }

        }
    }

    public void replaceAllBrUseWith(BasicBlock v) {
        for (Use use : useList) {
            if (use.getUser() instanceof TerminatorInst.Br BrInst) {
                use.setValue(v);
                v.useList.add(use);
            }

        }
    }
}
