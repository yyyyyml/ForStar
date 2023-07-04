package backend;

public class RISCGlobalvarBlock {
    private int value;
    private String name;

    public RISCGlobalvarBlock(Float f, String name) {
        value = Float.floatToIntBits(f);
        this.name = name;
    }

    public RISCGlobalvarBlock(int val, String name) {
        value = val;
        this.name = name;
    }

    public String emit() {
        return name + ":" + "\n\t" + ".word\t" + value;
    }

    public String getName() {
        return this.name;
    }
}
