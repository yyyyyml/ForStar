package backend;

public abstract class RISCOperand {
    private int position; // 操作数的顺序位置

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public  abstract String emit() ;
}
