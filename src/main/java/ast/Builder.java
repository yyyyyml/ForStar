package ast;

import ir.Instructions.BinaryInst;
import ir.Instructions.MemoryInst;
import ir.Instructions.TerminatorInst;
import ir.Instructions.ConversionInst;
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

    public ConversionInst.Fptosi buildFptosi(Value preVal) {
        // Security checks.
        if (!preVal.getType().isFloatType()) {
            throw new RuntimeException("A non-floatingPoint src Value is given.");
        }
        // Construct, insert, and return.
        ConversionInst.Fptosi inst = new ConversionInst.Fptosi(preVal);
        getCurBB().list.addLast(inst.node);
        return inst;
    }

    public ConversionInst.Sitofp buildSitofp(Value preVal) {
        // Security checks.
        if (!preVal.getType().isIntegerType()) {
            throw new RuntimeException("A non-floatingPoint src Value is given.");
        }
        // Construct, insert, and return.
        ConversionInst.Sitofp inst = new ConversionInst.Sitofp(preVal);
        getCurBB().list.addLast(inst.node);
        return inst;
    }

    public BinaryInst buildAdd(Value lOp, Value rOp) {
        // Security checks.
        if (lOp.getType() != rOp.getType()) {
            throw new RuntimeException("Unmatched types: [lOp] " + lOp.getType() + ", [rOp] " + rOp.getType());
        }

        // Constructor the Add instruction (for integers and floats respectively)
        BinaryInst instAdd;
        Type type = lOp.getType();
        if (type.isIntegerType()) {
            instAdd = new BinaryInst(Type.IntegerType.getType(), ir.Instruction.TAG.ADD, lOp, rOp);
        } else if (type.isFloatType()) {
            instAdd = new BinaryInst(Type.FloatType.getType(), ir.Instruction.TAG.FADD, lOp, rOp);
        } else {
            throw new RuntimeException("Unsupported type: " + type);
        }

        // Insert and return the inst.
        curBB.list.addLast(instAdd.node);
        return instAdd;
    }

    public BinaryInst buildSub(Value lOp, Value rOp) {
        // Security checks.
        if (lOp.getType() != rOp.getType()) {
            throw new RuntimeException("Unmatched types: [lOp] " + lOp.getType() + ", [rOp] " + rOp.getType());
        }

        // Constructor the Sub instruction (for integers and floats respectively)
        BinaryInst instSub;
        Type type = lOp.getType();
        if (type.isIntegerType()) {
            instSub = new BinaryInst(Type.IntegerType.getType(), ir.Instruction.TAG.SUB, lOp, rOp);
        } else if (type.isFloatType()) {
            instSub = new BinaryInst(Type.FloatType.getType(), ir.Instruction.TAG.FSUB, lOp, rOp);
        } else {
            throw new RuntimeException("Unsupported type: " + type);
        }

        // Insert and return the inst.
        curBB.list.addLast(instSub.node);
        return instSub;
    }

    public BinaryInst buildMul(Value lOp, Value rOp) {
        // Security checks.
        if (lOp.getType() != rOp.getType()) {
            throw new RuntimeException("Unmatched types: [lOp] " + lOp.getType() + ", [rOp] " + rOp.getType());
        }

        // Constructor the Sub instruction (for integers and floats respectively)
        BinaryInst instMul;
        Type type = lOp.getType();
        if (type.isIntegerType()) {
            instMul = new BinaryInst(Type.IntegerType.getType(), ir.Instruction.TAG.MUL, lOp, rOp);
        } else if (type.isFloatType()) {
            instMul = new BinaryInst(Type.FloatType.getType(), ir.Instruction.TAG.FMUL, lOp, rOp);
        } else {
            throw new RuntimeException("Unsupported type: " + type);
        }

        // Insert and return the inst.
        curBB.list.addLast(instMul.node);
        return instMul;
    }

    public BinaryInst buildDiv(Value lOp, Value rOp) {
        // Security checks.
        if (lOp.getType() != rOp.getType()) {
            throw new RuntimeException("Unmatched types: [lOp] " + lOp.getType() + ", [rOp] " + rOp.getType());
        }

        // Constructor the Sub instruction (for integers and floats respectively)
        BinaryInst instDiv;
        Type type = lOp.getType();
        if (type.isIntegerType()) {
            instDiv = new BinaryInst(Type.IntegerType.getType(), ir.Instruction.TAG.DIV, lOp, rOp);
        } else if (type.isFloatType()) {
            instDiv = new BinaryInst(Type.FloatType.getType(), ir.Instruction.TAG.FDIV, lOp, rOp);
        } else {
            throw new RuntimeException("Unsupported type: " + type);
        }

        // Insert and return the inst.
        curBB.list.addLast(instDiv.node);
        return instDiv;
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


