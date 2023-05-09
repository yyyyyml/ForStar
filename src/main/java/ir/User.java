package ir;

import java.util.LinkedList;

/**
 * 待修改
 */
public abstract class User extends Value {
    public LinkedList<Use> operandList = new LinkedList<>();//联系 User 与 Value 的 Use list,是乱序的，顺序通过 Use 的成员变量 operandRank 来体现
    private int numOP = 0;//操作数的数量

    public Value getOP(int i) {//这是个 O(n) 的操作，所幸 n 不会太大，而且operands是基本有序的
        for (Use use : operandList) {
            if (use.getPosition() == i) {
                return use.getValue();
            }
        }
        return null;
    }

    public void addOperand(Value v) {
        operandList.add(new Use(v, this, numOP++));
    }

    public void setOperand(Value v, int i) {
        assert i < numOP && i >= 0;
        //有就替换，无就分一个新的对象
        for (Use use : operandList) {
            if (use.getPosition() == i) {
                use.getValue().useList.removeIf(h -> h.equals(use));//从原Value中删去这条use
                use.setValue(v);
                v.useList.add(use);
                return;
            }
        }
        Use newUse = new Use(v, this, i);
    }

    public int getNumOP() {return numOP;}

    public User(Type type) {
        super(type);
    }

    public User(Type type, int numOp) {
        super(type);
        this.numOP = numOp;
    }


}
