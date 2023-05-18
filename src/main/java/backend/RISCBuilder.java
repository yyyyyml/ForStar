package backend;

import ir.Module;
import ir.values.Function;
import ir.values.GlobalVariable;
import util.IList;

public class RISCBuilder {

    static private final RISCBuilder builder = new RISCBuilder();

    private RISCBuilder() {}

    static public RISCBuilder get(){
        return builder;
    }

    private Module IRModule;

    private RISCClass target;

    /* Current MC function & basic block */
    private Function curIRFunc;
    private RISCFunction curFunc;
    private RISCBasicBlock curMCBB;


    public void loadModule(Module m) {IRModule = m;}

    /**
     * Codegen
     * 遍历变量（还没写）
     * 遍历函数块
     * @return
     */
    public RISCClass codeGeneration() {
        target = new RISCClass();
        mapGlobalVariable(IRModule, target);
        mapFunction(IRModule, target);

        return target;
    }

    /**
     * 遍历全局变量
     * @param IRModule
     * @param target
     */
    private void mapGlobalVariable(Module IRModule, RISCClass target){
        for(GlobalVariable gv:IRModule.getGlobalVariableList()){
            target.addGlobalvar(gv);
        }
    }

    /**
     * 遍历Ir函数转换为Risc函数类
     * @param IRModule
     * @param target
     */
    private void mapFunction(Module IRModule, RISCClass target){
        for(IList.INode<Function, Module> funcInode : IRModule.functionList){
            target.AddFunction(funcInode.getElement());
        }
    }
}
