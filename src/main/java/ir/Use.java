package ir;

/**
 * Use类 表示User和Value的使用关系
 * 包含position 表示Value的位置顺序 因为链表不一定是有序添加的
 */
public class Use {
    private int position; // Value的位置顺序
    private User u;
    private Value v;

    public Use(User u, Value v, int position) {
        this.v = v;
        this.u = u;
        this.position = position;
        v.addInUseList(this);
        u.addInOperandList(this);
    }

    public void setValue(Value v) {this.v = v;}

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }

    public User getUser() {
        return this.u;
    }

    public Value getValue() {
        return this.v;
    }
}
