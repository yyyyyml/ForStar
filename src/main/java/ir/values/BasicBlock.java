package ir.values;

import ir.Instruction;
import ir.Type;
import ir.Value;
import util.IList;

import java.util.ArrayList;

/**
 * 基本块类
 * 待修改
 */
public class BasicBlock extends Value {
    private ArrayList<BasicBlock> preList;//跳转到这里的基本块列表
    private ArrayList<BasicBlock> nextList;//能够跳转到的基本块列表
    public IList<Instruction, BasicBlock> list;
    public IList.INode<BasicBlock, Function> node;

    public BasicBlock(String name) {
        super(Type.LabelType.getType());
        this.preList = new ArrayList<>();
        this.nextList = new ArrayList<>();
        list = new IList<>(this);
        node = new IList.INode<BasicBlock, Function>(this, null, null);
    }
    //新加函数
    public void insertAtEnd(Instruction inst) {
        //Security checks.
        //if (inst.getBB() != null) {
        //   throw new RuntimeException("Try to insert an Inst that has already belonged to another BB.");
        //}
        //if (this.list.size() != 0 && this.getLastInst().getTag().isTerminator()) {
        //throw new RuntimeException("Try to insert an Inst to a BB which has already ended with a Terminator.");
        //
        // Insertion.
        this.list.addAfter(inst.node.getPrev(),inst.node);
    }

    public Instruction getLastInst() {
        return this.list.isEmpty() ? null : this.list.getLast().getElement();
    }
}
