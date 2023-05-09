package ir;

public class Use {
    private int operandRank;
    private User u;
    private Value v;

    public Use(Value v, User u, int operandRank) {
        this.v = v;
        this.u = u;
        this.operandRank = operandRank;
//        v.useList.add(this);
//        u.operandList.add(this);
    }

    public void setV(Value v) {this.v = v;}

    public int getRank() {return operandRank;}

    public Value getV() {return v;}

    public User getU() {return u;}
}
