package backend;

public class RISCEmitter {

    public static RISCEmitter emitter=new RISCEmitter();

    public StringBuilder emit(RISCClass target){
        StringBuilder out=new StringBuilder();
        out.append("\t.file\t" + target.inputfilename+'\n');
        out.append("\t.option\tpic\n");
        out.append("\t.text\n");
        for(RISCFunction Rfunc: target.getFunctionList()){
                out.append("\t.align\t1\n");
                out.append("\t.global\t"+Rfunc.funcname+"\n");
                out.append("\t.type\t"+Rfunc.funcname+"\t@function\n");
                out.append(Rfunc.funcname+":\n");
                for(RISCBasicBlock Rbb:Rfunc.getBasicBlockList()){
                    for(RISCinstruction Rinstruction:Rbb.getInstructions()){
                        out.append("\t"+Rinstruction.emit()+"\n");
                    }
                }
                out.append("\t.size\t"+Rfunc.funcname+", -"+Rfunc.funcname+"\n");
        }
        out.append("\t.ident\t"+"\"ForStar\"\n");
        out.append("\t.section\t"+".note.GNU-stack,\"\",@progbits");
        return out;
    }

}
