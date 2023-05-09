package ir;

public class Use {
    private int Position;
    private User u;
    private Value v;

    public Use(Value v, User u, int Position) {
        this.v = v;
        this.u = u;
        this.Position = Position;
        v.useList.add(this);
        u.operandList.add(this);
    }

    public void setValue(Value v) {this.v = v;}

    public int getPosition() {return this.Position;}

    public Value getValue() {return this.v;}

    public User getUser() {return this.u;}
}
