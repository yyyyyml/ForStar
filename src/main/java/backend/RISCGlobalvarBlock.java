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
        StringBuffer out = new StringBuffer();
        out.append("\t.global\t" + name + "\n");
        if (value == 0) {
            out.append("\t.bss\n");
        } else {
            out.append("\t.data\n");
        }
        out.append("\t.align\t2\n" + "\t.type\t" + name + ",@object\n" + "\t.size\t" + name + ",4\n");
        out.append(name + ":" + "\n\t");
        if (value == 0) {
            out.append(".zero\t4");
        } else {
            out.append(".word\t" + value);
        }

        String S = new String(out);

        return S;
    }

    public String getName() {
        return this.name;
    }
}
