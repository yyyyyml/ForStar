package backend;

import ir.Module;
import ir.values.Function;
import ir.values.GlobalVariable;
import util.IList;

public class RISCBuilder {

    static private final RISCBuilder builder = new RISCBuilder();

    private RISCBuilder() {}

    static public RISCBuilder get(){return builder;}

    private Module IRModule;

    private RISCClass target;

    /* Current MC function & basic block */
    private Function curIRFunc;
    private RISCFunction curFunc;
    private RISCBasicBlock curMCBB;

    /**
     * Load LLVM IR module. Separate this process to support multi-module codegen.
     * (if possible?)
     * @param m IR module
     */
    public void loadModule(Module m) {IRModule = m;}

    public RISCClass codeGeneration() {
        target = new RISCClass();
        mapGlobalVariable(IRModule, target);
        mapFunction(IRModule, target);

        return target;
    }

    private void mapGlobalVariable(Module IRModule, RISCClass target){
        for(GlobalVariable gv:IRModule.getGlobalVariableList()){
            target.AddGlobalvar(gv);
        }
    }

    private void mapFunction(Module IRModule, RISCClass target){
        for(IList.INode<Function, Module> funcInode : IRModule.functionList){
            target.AddFunction(funcInode.getElement());
        }
    }
}
