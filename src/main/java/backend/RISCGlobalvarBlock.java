package backend;

import java.util.LinkedList;

public class RISCGlobalvarBlock {
    private int value;
    private LinkedList<Integer> valList;
    private String name;
    private Boolean isArray;
    private int arraySize;
    private int zeroSize;

    public RISCGlobalvarBlock(LinkedList<Integer> valList, String name, int arraySize) {
        isArray = true;
        this.name = name;
        this.valList = valList;
        this.arraySize = arraySize;
        int listSize = valList.size();
        zeroSize = arraySize - listSize;
        //为了后续输出时判断为.data还是.bss
        if (listSize != 0) value = 1;

    }

    public RISCGlobalvarBlock(Float f, String name) {
        isArray = false;
        value = Float.floatToIntBits(f);
        this.name = name;
    }

    public RISCGlobalvarBlock(int val, String name) {
        isArray = false;
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
        if (!isArray) {
            out.append("\t.align\t2\n" + "\t.type\t" + name + ",@object\n" + "\t.size\t" + name + ",4\n");
        } else {
            out.append("\t.align\t3\n" + "\t.type\t" + name + ",@object\n" + "\t.size\t" + name + "," + arraySize * 4 + "\n");
        }
        out.append(name + ":" + "\n\t");
        if (!isArray) {
            if (value == 0) {
                out.append(".zero\t4");
            } else {
                out.append(".word\t" + value);
            }
        } else {
            for (Integer i : valList) {
                out.append(".word\t" + value);
            }
            if (zeroSize != 0) {
                out.append(".zero\t" + zeroSize * 4);
            }
        }

        String S = new String(out);

        return S;
    }

    public String getName() {
        return this.name;
    }
}
