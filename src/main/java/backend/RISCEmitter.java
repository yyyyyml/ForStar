package backend;

import java.io.FileWriter;

public class RISCEmitter {
    private String outputName;
    public RISCEmitter(String outputName) {
        this.outputName = outputName;
    }

    public void emit(RISCModule target) {
        StringBuilder out = new StringBuilder();
        out.append("\t.file\t" + target.inputFilename + '\n');
        out.append("\t.option\tpic\n");
        out.append("\t.text\n");
        for (RISCFunction riscFunc : target.getFunctionList()) {
            out.append("\t.align\t1\n");
            out.append("\t.global\t" + riscFunc.funcName + "\n");
            out.append("\t.type\t" + riscFunc.funcName + "\t@function\n");
            out.append(riscFunc.funcName + ":\n");
            for (RISCBasicBlock riscBB : riscFunc.getBasicBlockList()) {
                for (RISCInstruction riscInst : riscBB.getInstructionList()) {
                    out.append("\t" + riscInst.emit() + "\n");
                }
            }
            out.append("\t.size\t" + riscFunc.funcName + ", -" + riscFunc.funcName + "\n");
        }
        out.append("\t.ident\t" + "\"ForStar\"\n");
        out.append("\t.section\t" + ".note.GNU-stack,\"\",@progbits");

        System.out.println(out);

        try {
            FileWriter fw = new FileWriter(outputName);
            fw.append(out);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
