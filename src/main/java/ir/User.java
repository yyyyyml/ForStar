package ir;

import java.util.LinkedList;

/**
 * User类 继承自Value类
 * 包括numOP属性，表示操作数数量
 * operandList表示所有这个User使用其他Value(操作数)的Use的链表
 * addInOperandList()用于向operandList中添加Use
 */
public abstract class User extends Value {
    public LinkedList<Use> operandList = new LinkedList<>();
    private int numOP = 0;//操作数的数量

    public User(Type type) {
        super(type);
    }

    public User(Type type, int numOp) {
        super(type);
        this.numOP = numOp;
    }

    public Value getOperandAt(int i) {
        for (Use use : operandList) {
            if (use.getPosition() == i) {
                return use.getValue();
            }
        }
        return null;
    }

    public void setOperand(Value v, int i) {
        assert i < numOP && i >= 0;

        Use existingUse = null;
        for (Use use : operandList) {
            if (use.getPosition() == i) {
                existingUse = use;
                break;
            }
        }

        if (existingUse != null) { // 当前位置有操作数，替换
            existingUse.getValue().useList.remove(existingUse);
            existingUse.setValue(v);
            v.addInUseList(existingUse);
        } else { // 当前位置没有操作数，新建
            Use newUse = new Use(this, v, i);
            this.addInOperandList(newUse);
            v.addInUseList(newUse);
        }
    }

    public void addInOperandList(Use use) {
        operandList.add(use);
    }

    public int getNumOP() {
        return numOP;
    }


}
