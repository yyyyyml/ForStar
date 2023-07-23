package ir.values;

import ir.Instruction;
import ir.Instructions.MemoryInst;
import ir.Type;
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


}
