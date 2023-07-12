package backend;


import ir.types.ArrayType;
import ir.types.PointerType;
import ir.values.Constant;
import ir.values.Function;
import ir.values.GlobalVariable;

import java.util.HashMap;
import java.util.LinkedList;

public class RISCModule {
    public String inputFilename;
    private LinkedList<RISCFunction> functionList = new LinkedList<>();
    private LinkedList<RISCOperand> globalVars = new LinkedList<>();
    public HashMap<Float, RISCFloatBlock> floatMap = new HashMap<>();
    public LinkedList<RISCFloatBlock> floatBlockLinkedList = new LinkedList<>();
    private int FBindex = 0;
    public HashMap<GlobalVariable, RISCGlobalvarBlock> GlobalVarMap = new HashMap<>();
    public LinkedList<RISCGlobalvarBlock> GlobalVarList = new LinkedList<>();

    public LinkedList<RISCFunction> getFunctionList() {
        return functionList;
    }

    public LinkedList<RISCOperand> getGlobalVars() {
        return globalVars;
    }

    public void addGlobalvar(GlobalVariable gv) {
        if (((PointerType) gv.getType()).getPointedType().isArrayType()) {
            int totalSize = ((ArrayType) ((PointerType) gv.getType()).getPointedType()).getTotalSize();
            LinkedList<Integer> valList = new LinkedList<>();
            System.out.println(11111111);
            if (true) {
                RISCGlobalvarBlock GVB = new RISCGlobalvarBlock(valList, gv.getName().substring(1), totalSize);
                GlobalVarMap.put(gv, GVB);
                GlobalVarList.add(GVB);
            }

        } else {
            if (gv.init == null) {
                RISCGlobalvarBlock GVB = new RISCGlobalvarBlock(0, gv.getName().substring(1));
                GlobalVarMap.put(gv, GVB);
                GlobalVarList.add(GVB);
            } else if (gv.init.getType().isIntegerType()) {
                RISCGlobalvarBlock GVB = new RISCGlobalvarBlock(((Constant.ConstantInt) gv.init).getVal(), gv.getName().substring(1));
                GlobalVarMap.put(gv, GVB);
                GlobalVarList.add(GVB);
            } else if (gv.init.getType().isFloatType()) {
                RISCGlobalvarBlock GVB = new RISCGlobalvarBlock(((Constant.ConstantFloat) gv.init).getVal(), gv.getName().substring(1));
                GlobalVarMap.put(gv, GVB);
                GlobalVarList.add(GVB);

            }
        }

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
            RISCFloatBlock FB = new RISCFloatBlock(f, FBindex++);
            floatMap.put(f, FB);
            floatBlockLinkedList.add(FB);
            return FB.getName();
        }
    }
}
