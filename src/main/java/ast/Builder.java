package ast;

import ir.Instructions.MemoryInst;
import ir.Instructions.TerminatorInst;
import ir.Module;
import ir.Type;
import ir.Value;
import ir.types.FunctionType;
import ir.values.BasicBlock;
import ir.values.Constant;
import ir.values.Function;
import ir.values.GlobalVariable;


public class Builder {

    private Module curMdl;
    private Function curFunc;
    private BasicBlock curBB;

    public Builder(Module m) {
        this.setCurModule(m);
    }

    public Builder() {
    }

    public Module getCurModule() {
        return curMdl;
    }

    public void setCurModule(Module mod) {
        curMdl = mod;
    }

    public Function getCurFunc() {
        return curFunc;
    }

    public void setCurFunc(Function func) {
        curFunc = func;
    }

    public BasicBlock getCurBB() {
        return curBB;
    }

    public void setCurBB(BasicBlock bb) {
        curBB = bb;
    }

    public Constant.ConstantInt buildConstant(int i) {
        return Constant.ConstantInt.getConstantInt(i);
    }

    public Constant.ConstantFloat buildConstant(float f) {
        return Constant.ConstantFloat.getConstantFloat(f);
    }

    public GlobalVariable buildGlobalVar(String name, Type type) {
        GlobalVariable glbVar = new GlobalVariable(name, type);
        getCurModule().addGlobalVariable(glbVar);
        return glbVar;
    }

    public MemoryInst.Alloca buildAlloca(Type allocatedType) {
        MemoryInst.Alloca inst = new MemoryInst.Alloca(allocatedType);
        getCurBB().list.addFirst(inst.node);
        return inst;
    }

    public MemoryInst.Store buildStore(Value val, Value address) {
        MemoryInst.Store inst = new MemoryInst.Store(val, address);
        getCurBB().list.addLast(inst.node);
        return inst;
    }

    public MemoryInst.Load buildLoad(Type loadedType, Value addr) {
        MemoryInst.Load inst = new MemoryInst.Load(loadedType, addr);
        getCurBB().list.addLast(inst.node);
        return inst;
    }

    public Function buildFunction(String name, FunctionType type, boolean isBuiltIn) {
        Function func = new Function(type, isBuiltIn);
        func.setName(name);
        getCurModule().functionList.addLast(func.node);
        this.setCurFunc(func);
        return func;
    }

    public BasicBlock buildBB(String name) {
        BasicBlock bb = new BasicBlock(name);
        curFunc.list.addLast(bb.node);
        this.setCurBB(bb);
        return bb;
    }

    public TerminatorInst.Ret buildRet() {
        //检查函数返回类型是否是void
        if (!((FunctionType) getCurFunc().getType()).getRetType().isVoidType()) {
            throw new RuntimeException("Try to return void with Ret inst in a non-void function.");
        }
        //建立返回指令，将返回指令添加到当前块之后
        TerminatorInst.Ret ret = new TerminatorInst.Ret();
        getCurBB().list.addLast(ret.node);
        System.out.println(getCurBB().getName());
        System.out.println("buildRet");
        return ret;
    }

    public TerminatorInst.Ret buildRet(Value retVal) {
        //检查函数返回类型是否匹配
        if (retVal.getType() != ((FunctionType) getCurFunc().getType()).getRetType()) {
            throw new RuntimeException(
                    "The type of retVal doesn't match with the return type defined in the function prototype.");
        }
        //建立返回指令，将返回指令添加到当前块之后
        TerminatorInst.Ret ret = new TerminatorInst.Ret(retVal);
        getCurBB().list.addLast(ret.node);
        System.out.println(getCurBB().getName());
        System.out.println("buildRet");
        return ret;
    }

}


