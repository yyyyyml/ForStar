package ast;

import ir.Module;
import ir.Type;
import ir.Value;
import ir.types.*;
import ir.values.*;

import ir.values.Instructions.*;
import util.IList;


public class Builder {

    private Module curMdl;
    private Function curFunc;
    private BasicBlock curBB;
    public Builder(Module m) {
        this.setCurModule(m);
    }
    public Builder() {
    }
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

    public BasicBlock getCurBB() {
        return curBB;
    }

    public Constant.ConstantInt buildConstant(int i) {
        return Constant.ConstantInt.getConstantInt(i);
    }

    public Function buildFunction(String name, FunctionType type, boolean isExternal) {
        Function func = new Function(type, isExternal);
        func.setName(name);
        //这里要将func改成Node才能正常执行
        getCurModule().functionList.addLast(func.node);
        //if(isExternal) {
        //getCurModule().functionList.addBefore(getCurModule().functionList.getTail(),func);
        //}
        //else {
        // getCurModule().functions.add(func);
        // Set the pointer.
        this.setCurFunc(func);
        //}
        return func;
    }


    public BasicBlock buildBB(String bbInfo) {
        BasicBlock bb = new BasicBlock(bbInfo);
        curFunc.list.addLast(bb.node);
        // Set the pointer.
        this.setCurBB(bb);
        return bb;
    }

    /**
     * Insert a new basic block at the end of current function.
     *
     * @return Reference of the basic block created.
     */
    //public BasicBlock buildBB() {
    //  BasicBlock bb = new BasicBlock();
    // curFunc.addBB(bb);
    // Set the pointer.
    // this.setCurBB(bb);
    //  return bb;
    //}

    /**
     * Insert a Ret terminator with no return value (return void)
     * at the end of current basic block.
     * @return The terminator object inserted.
     */
    public TerminatorInst.Ret buildRet() {
        // Security checks.
        //!getCurFunc().getType().getRetType().isVoidType()
        if (!((FunctionType)getCurFunc().getType()).getRetType().isVoidType()) {
            throw new RuntimeException("Try to return void with Ret inst in a non-void function.");
        }
        // Construct, insert, and return.
        TerminatorInst.Ret ret = new TerminatorInst.Ret();
        getCurBB().list.addLast(ret.node);
        System.out.println(getCurBB().name);
        System.out.println("buildRet");
        return ret;
    }

    /**
     * Insert a Ret terminator with a specified return value
     * at the end of current basic block.
     * @param retVal The Value instance specified as return value.
     * @return The terminator object inserted.
     */
    public TerminatorInst.Ret buildRet(Value retVal) {
        // Security checks.
        //retVal.getType() != getCurFunc().getType().getRetType()
        if (retVal.getType() != ((FunctionType)getCurFunc().getType()).getRetType()) {
            throw new RuntimeException(
                    "The type of retVal doesn't match with the return type defined in the function prototype.");
        }
        // Construct, insert, and return.
        TerminatorInst.Ret ret = new TerminatorInst.Ret(retVal);
        getCurBB().list.addLast(ret.node);
        return ret;
    }

}


