package ir;

import ir.values.Function;
import ir.values.GlobalVariable;
import util.IList;

import java.util.LinkedList;

public class Module {
    public IList<Function, Module> functionList;

    public final LinkedList<GlobalVariable> globalVariableList = new LinkedList<>();

    public void addGlobalVariable(GlobalVariable glbVar) {
        globalVariableList.add(glbVar);
    }

    public LinkedList<GlobalVariable> getGlobalVariableList() {
        return globalVariableList;
    }
}
