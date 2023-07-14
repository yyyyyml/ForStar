package backend;

import ir.Value;
import ir.values.Constant;

import java.util.LinkedList;

public class RISCGlobalvarBlock {
    private int value = 0;
    private LinkedList<Integer> valList;
    private String name;
    private Boolean isArray;
    private int arraySize;
    private int zeroSize;
    public Constant init;

    public RISCGlobalvarBlock(LinkedList<Integer> valList, String name, int arraySize, Constant init) {
        isArray = true;
        this.name = name;
        this.valList = valList;
        this.arraySize = arraySize;
        int listSize = valList.size();
        this.init = init;
        //zeroSize = arraySize - listSize;
        //为了后续输出时判断为.data还是.bss
        if (init != null) value = 1;

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
        out.append(name + ":" + "\n");
        if (!isArray) {
            if (value == 0) {
                out.append("\t.zero\t4");
            } else {
                out.append("\t.word\t" + value);
            }
        } else {
            if (init == null) {
                out.append("\t.zero\t" + arraySize * 4);
            } else {
                out.append(arrToString(this.init, arraySize));
            }
        }
        String S = new String(out);
        return S;
    }

    private String arrToString(Constant init, int size) {
        int operandSize = init.getNumOP();
        System.out.println(init);
        StringBuffer out = new StringBuffer();
        if (init instanceof Constant.ConstantArray && ((Constant.ConstantArray) init).isAllZero) {
            out.append("\t.zero\t" + size * 4 + "\n");

        } else {
            int zeroNum = 0;
            for (int i = 0; i < operandSize; i++) {
                Value v = init.getOperandAt(i);

                if (v instanceof Constant.ConstantInt) {

                    int val = ((Constant.ConstantInt) v).getVal();
                    if (val == 0) zeroNum++;
                    else {
                        if (zeroNum != 0) {
                            out.append("\t.zero\t" + zeroNum * 4 + "\n");
                            zeroNum = 0;
                        }
                        out.append("\t.word\t" + val + "\n");
                    }
                } else if (v instanceof Constant.ConstantInt) {

                    int val = ((Constant.ConstantInt) v).getVal();
                    if (val == 0) zeroNum++;
                    else {
                        if (zeroNum != 0) {
                            out.append("\t.zero\t" + zeroNum * 4 + "\n");
                            zeroNum = 0;
                        }
                        out.append("\t.word\t" + Float.floatToIntBits(val) + "\n");
                    }
                } else if (v instanceof Constant.ConstantArray) {
                    out.append(arrToString((Constant) v, size / (operandSize)));
                }

            }
            if (zeroNum != 0) {
                out.append("\t.zero\t" + zeroNum * 4 + "\n");
            }
        }
        String S = new String(out);
        return S;
    }

    public String getName() {
        return this.name;
    }
}
