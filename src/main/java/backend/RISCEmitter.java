package backend;

import java.io.FileWriter;

public class RISCEmitter {
    private String outputName;

    public RISCEmitter(String outputName) {
        this.outputName = outputName;
    }

    public void emit(RISCModule target) {
        StringBuilder out = new StringBuilder();
        out.append("\t.file\t" + '"' + target.inputFilename + '"' + '\n');
        out.append("\t.option\tpic\n");
        out.append("\t.attribute unaligned_access, 0\n");
        out.append("\t.attribute stack_align, 16\n");
        out.append("\t.text\n");
        for (RISCFunction riscFunc : target.getFunctionList()) {
            if (riscFunc.isBuildIn) {
                continue;
            }
            out.append("\t.align\t1\n");
            out.append("\t.global\t" + riscFunc.funcName + "\n");
            out.append("\t.type\t" + riscFunc.funcName + "\t@function\n");
            out.append(riscFunc.funcName + ":\n");
            for (RISCBasicBlock riscBB : riscFunc.getBasicBlockList()) {
                if (riscBB.getBlockName() != null) out.append( riscBB.getBlockName() + ":\n");
                for (RISCInstruction riscInst : riscBB.getInstructionList()) {
//                    if (riscInst.getId() >= 0) out.append(riscInst.getId());
                    out.append("\t" + riscInst.emit() + "\n");
                }
            }
            out.append("\t.size\t" + riscFunc.funcName + ", .-" + riscFunc.funcName + "\n");
        }
        for (RISCFloatBlock FB : target.floatBlockLinkedList) {
            out.append(FB.emit() + "\n");
        }
        for (RISCGlobalvarBlock GVB : target.GlobalVarList) {
            out.append(GVB.emit() + "\n");
        }
        out.append("\t.ident\t" + "\"ForStar\"\n");
        out.append("\t.section\t" + ".note.GNU-stack,\"\",@progbits" + "\n");

        System.out.println(out);

        if (outputName != null) {
            try {
                FileWriter fw = new FileWriter(outputName);
                fw.append(out);
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
