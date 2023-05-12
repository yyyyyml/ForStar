package backend;



import ir.values.Function;
import ir.values.GlobalVariable;

import java.util.LinkedList;

public class RISCClass {
    private  LinkedList<RISCFunction> functionList = new LinkedList<>();
    private  LinkedList<RISCOperand> globalVars = new LinkedList<>();
    public String inputfilename;

    public LinkedList<RISCFunction> getFunctionList (){
            return functionList;
    }
    public void AddGlobalvar(GlobalVariable gv) {
        ;
    }

    public void AddFunction(Function IRfunc) {
        RISCFunction Afunc=new RISCFunction(IRfunc);
        functionList.add(Afunc);
    }


}
