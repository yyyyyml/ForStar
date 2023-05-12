package ast;

import ir.Instruction;
import ir.Value;
import ir.values.BasicBlock;
import ir.values.Function;
import ir.values.GlobalVariable;
import ir.Module;
import util.IList;

import java.io.FileWriter;

public class Emitter {
    private String outputName = "out.ll";
    private StringBuilder sb;

    public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public Emitter() {
        sb = new StringBuilder();
    }

    public Emitter(String outputName) {
        this.outputName = outputName;
        sb = new StringBuilder();
    }

    private int nameCounter = 0;

    private String newName() {
        String name = String.valueOf(nameCounter);
        nameCounter++;
        return name;
    }

    private void nameAll(Module m) {
        for (GlobalVariable gv : m.globalVariableList) {
            if (!gv.name.startsWith("@")) {
                gv.name = "@" + gv.name;
            }
        }
        for (IList.INode<Function, Module> funcInode : m.functionList) {
            nameCounter = 0;
            Function func = funcInode.getElement();
            if (!func.isBuiltin()) {
                for (Function.Param arg : func.getParamList()) {
                    arg.name = "%" + newName();
                }
                for (IList.INode<BasicBlock, Function> bbInode : func.list) {
                    bbInode.getElement().name = newName();
                    for (IList.INode<Instruction, BasicBlock> instInode : bbInode.getElement().list) {
                        if (instInode.getElement().needName) {
                            instInode.getElement().name = "%" + newName();
                        }
                    }
                }
            } else {
                sb.append("declare ")
                        .append(func)
                        .append("\n");
            }
        }
    }

    public void emit(Module m) {
        nameAll(m);
        for (GlobalVariable gb : m.globalVariableList) {
            sb.append(gb).append("\n");
        }
        for (IList.INode<Function, Module> funcInode : m.functionList) {
            Function func = funcInode.getElement();
            if (!func.isBuiltin()) {
                sb.append("define dso_local ")
                        .append(func)
                        .append("{");
                sb.append("\n");

                for(IList.INode<BasicBlock, Function> bbInode : func.list) {
                    BasicBlock bb = bbInode.getElement();
                    if (!func.list.getFirst().equals(bbInode)) {
                        sb.append(bb.name);
                        sb.append(":");
                        sb.append("\n");
                    }
                    for (IList.INode<Instruction, BasicBlock> instInode : bbInode.getElement().list) {
                        Instruction inst = instInode.getElement();
                        sb.append("\t");
                        sb.append(inst.toString());
                        sb.append("\n");
                    }
                }
                sb.append("}\n");
            } else {
                // do something else
            }
        }

        System.out.println(sb);

        try {
            FileWriter fw = new FileWriter(outputName);
            fw.append(sb);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
