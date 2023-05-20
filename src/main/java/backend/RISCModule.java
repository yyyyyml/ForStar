package backend;


import ir.values.Function;
import ir.values.GlobalVariable;

import java.util.LinkedList;

public class RISCModule {
    public String inputFilename;
    private LinkedList<RISCFunction> functionList = new LinkedList<>();
    private LinkedList<RISCOperand> globalVars = new LinkedList<>();

    public LinkedList<RISCFunction> getFunctionList() {
        return functionList;
    }

    public LinkedList<RISCOperand> getGlobalVars() {
        return globalVars;
    }

    public void addGlobalvar(GlobalVariable gv) {
        ;
    }

    /**
     * 为MIR添加Function
     *
     * @param irFunc
     */
    public void AddFunction(Function irFunc) {
        RISCFunction newFunc = new RISCFunction(irFunc);
        functionList.add(newFunc);
    }


}
