package ast;

import ir.Module;
import ir.Value;
import ir.types.*;
import ir.values.*;

import ir.Instructions.*;


public class Builder {

    private Module curMdl;
    private Function curFunc;
    private BasicBlock curBB;
    public Builder(Module m) { this.setCurModule(m); }
    public Builder() { }
    public void setCurModule(Module mod) {
        curMdl = mod;
    }

    public void setCurFunc(Function func) {
        curFunc = func;
    }

    public void setCurBB(BasicBlock bb) {
        curBB = bb;
    }

    public Module getCurModule() {
        return curMdl;
    }

    public Function getCurFunc() {
        return curFunc;
    }

    public BasicBlock getCurBB() { return curBB; }

    public Constant.ConstantInt buildConstant(int i) {
        return Constant.ConstantInt.getConstantInt(i);
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
        if (!((FunctionType)getCurFunc().getType()).getRetType().isVoidType()) {
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
        if (retVal.getType() != ((FunctionType)getCurFunc().getType()).getRetType()) {
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


