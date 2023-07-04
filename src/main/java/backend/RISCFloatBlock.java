package backend;

public class RISCFloatBlock {
    private int value;
    private String name;

    public RISCFloatBlock(Float f, int index) {
        value = Float.floatToIntBits(f);
        name = ".F" + index;
    }

    public String emit() {
        return name + ":" + "\n\t" + ".word\t" + value;
    }

    public String getName() {
        return this.name;
    }
}
