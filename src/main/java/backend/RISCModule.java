package backend;


import ir.values.Function;
import ir.values.GlobalVariable;

import java.util.HashMap;
import java.util.LinkedList;

public class RISCModule {
    public String inputFilename;
    private LinkedList<RISCFunction> functionList = new LinkedList<>();
    private LinkedList<RISCOperand> globalVars = new LinkedList<>();
    public HashMap<Float, RiscFloatBlock> floatMap = new HashMap<>();
    public LinkedList<RiscFloatBlock> floatBlockLinkedList = new LinkedList<>();
    private int FBindex = 0;

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
        RISCFunction newFunc = new RISCFunction(irFunc, this);
        functionList.add(newFunc);
    }


    public String getFloatBlockName(Float f) {
        if (floatMap.containsKey(f)) {
            return floatMap.get(f).getName();
        } else {
            RiscFloatBlock FB = new RiscFloatBlock(f, FBindex++);
            floatMap.put(f, FB);
            floatBlockLinkedList.add(FB);
            return FB.getName();
        }
    }
}
