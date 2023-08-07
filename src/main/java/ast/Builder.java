package ast;

import ir.Instruction;
import ir.Instructions.*;
import ir.Module;
import ir.Type;
import ir.Value;
import ir.types.ArrayType;
import ir.types.FunctionType;
import ir.types.PointerType;
import ir.values.BasicBlock;
import ir.values.Constant;
import ir.values.Function;
import ir.values.GlobalVariable;

import java.util.ArrayList;
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

    public GlobalVariable buildGlobalVar(String name, Constant init) {
        GlobalVariable glbVar = new GlobalVariable(name, init);
        getCurModule().addGlobalVariable(glbVar);
        return glbVar;
    }



    public MemoryInst.Alloca buildAlloca(Type allocatedType) {
        var entryBlk = getCurFunc().list.getFirst().getElement();
        MemoryInst.Alloca inst = new MemoryInst.Alloca(allocatedType);
        entryBlk.list.addFirst(inst.node);
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

//        getCurBB().nextList.add(bb);
//        bb.preList.add(getCurBB());
        curFunc.list.addLast(bb.node);
        this.setCurBB(bb);
        return bb;
    }

    /*public BasicBlock buildBB() {
        BasicBlock bb = new BasicBlock();
        curFunc.list.addLast(bb.node);
        this.setCurBB(bb);
        return bb;
    }*/

    public ConversionInst.Fptosi buildFptosi(Value preVal) {
        if (!preVal.getType().isFloatType()) {
            throw new RuntimeException("A non-floatingPoint src Value is given.");
        }
        ConversionInst.Fptosi inst = new ConversionInst.Fptosi(preVal);
        getCurBB().list.addLast(inst.node);
        return inst;
    }

    public ConversionInst.Sitofp buildSitofp(Value preVal) {
        if (!preVal.getType().isIntegerType()&&!preVal.getType().isBoolType()) {
            throw new RuntimeException("A non-IntPoint src Value is given.");
        }
        ConversionInst.Sitofp inst = new ConversionInst.Sitofp(preVal);
        getCurBB().list.addLast(inst.node);
        return inst;
    }

    public ConversionInst.Ptrcast buildPtrcast(Value srcVal, PointerType dstType) {

        if (!srcVal.getType().isPointerType()) {
            throw new RuntimeException("A non-pointer src Value is given.");
        }

        ConversionInst.Ptrcast ptrcast = new ConversionInst.Ptrcast(srcVal,dstType);
        getCurBB().list.addLast(ptrcast.node) ;
        return ptrcast;
    }

    public BinaryInst buildAdd(Value lOp, Value rOp) {
        if (lOp.getType() != rOp.getType()) {
            throw new RuntimeException("Unmatched types: [lOp] " + lOp.getType() + ", [rOp] " + rOp.getType());
        }
        BinaryInst instAdd;
        Type type = lOp.getType();
        if (type.isIntegerType()) {
            instAdd = new BinaryInst(Type.IntegerType.getType(), ir.Instruction.TAG.ADD, lOp, rOp);
        } else if (type.isFloatType()) {
            instAdd = new BinaryInst(Type.FloatType.getType(), ir.Instruction.TAG.FADD, lOp, rOp);
        } else {
            throw new RuntimeException("Unsupported type: " + type);
        }
        curBB.list.addLast(instAdd.node);
        return instAdd;
    }

    public BinaryInst buildSub(Value lOp, Value rOp) {
        if (lOp.getType() != rOp.getType()) {
            throw new RuntimeException("Unmatched types: [lOp] " + lOp.getType() + ", [rOp] " + rOp.getType());
        }
        BinaryInst instSub;
        Type type = lOp.getType();
        if (type.isIntegerType()) {
            instSub = new BinaryInst(Type.IntegerType.getType(), ir.Instruction.TAG.SUB, lOp, rOp);
        } else if (type.isFloatType()) {
            instSub = new BinaryInst(Type.FloatType.getType(), ir.Instruction.TAG.FSUB, lOp, rOp);
        } else {
            throw new RuntimeException("Unsupported type: " + type);
        }
        curBB.list.addLast(instSub.node);
        return instSub;
    }

    public BinaryInst buildMul(Value lOp, Value rOp) {
        if (lOp.getType() != rOp.getType()) {
            throw new RuntimeException("Unmatched types: [lOp] " + lOp.getType() + ", [rOp] " + rOp.getType());
        }
        BinaryInst instMul;
        Type type = lOp.getType();
        if (type.isIntegerType()) {
            instMul = new BinaryInst(Type.IntegerType.getType(), ir.Instruction.TAG.MUL, lOp, rOp);
        } else if (type.isFloatType()) {
            instMul = new BinaryInst(Type.FloatType.getType(), ir.Instruction.TAG.FMUL, lOp, rOp);
        } else {
            throw new RuntimeException("Unsupported type: " + type);
        }
        curBB.list.addLast(instMul.node);
        return instMul;
    }

    public BinaryInst buildDiv(Value lOp, Value rOp) {
        if (lOp.getType() != rOp.getType()) {
            throw new RuntimeException("Unmatched types: [lOp] " + lOp.getType() + ", [rOp] " + rOp.getType());
        }
        BinaryInst instDiv;
        Type type = lOp.getType();
        if (type.isIntegerType()) {
            instDiv = new BinaryInst(Type.IntegerType.getType(), ir.Instruction.TAG.DIV, lOp, rOp);
        } else if (type.isFloatType()) {
            instDiv = new BinaryInst(Type.FloatType.getType(), ir.Instruction.TAG.FDIV, lOp, rOp);
        } else {
            throw new RuntimeException("Unsupported type: " + type);
        }
        curBB.list.addLast(instDiv.node);
        return instDiv;
    }

    public TerminatorInst.Call buildCall(Function func, ArrayList<Value> args) {
        TerminatorInst.Call call = new TerminatorInst.Call(func, args);
        getCurBB().list.addLast(call.node);
        return call;
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

    public MemoryInst.GEP buildGEP(Value ptr, ArrayList<Value> indices) {
        MemoryInst.GEP gepInst = new MemoryInst.GEP(ptr, indices);
        getCurBB().list.addLast(gepInst.node) ;
        return gepInst;
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

    public FnegInst buildFneg(ir.Instruction.TAG tag, Value opd) {
        Type resType = null;
        switch (tag) {
            case FNEG -> resType = Type.FloatType.getType();
        }
        FnegInst unaryInst = new FnegInst(resType, tag,1, opd);
        getCurBB().list.addLast(unaryInst.node);
        return unaryInst;
    }

    public TerminatorInst.Br buildBr(Value cond, BasicBlock trueBlk, BasicBlock falseBlk) {

        TerminatorInst.Br condBr = new TerminatorInst.Br(cond, trueBlk, falseBlk, getCurBB());
        getCurBB().list.addLast(condBr.node);


        return condBr;
    }

    public TerminatorInst.Br buildBr(BasicBlock blk) {

        TerminatorInst.Br uncondBr = new TerminatorInst.Br(blk, getCurBB());
        getCurBB().list.addLast(uncondBr.node);


        return uncondBr;
    }

    public BinaryInst buildComparison(String opr, Value lOp, Value rOp) {

        if (lOp.getType() != rOp.getType()) {
            throw new RuntimeException("Unmatched types: [lOp] " + lOp.getType() + ", [rOp] " + rOp.getType());
        }


        BinaryInst inst = null;
        if (lOp.getType().isIntegerType()) {
            switch (opr) {
                case "<=" -> inst = new BinaryInst(Type.BoolType.getType(), Instruction.TAG.LE, lOp, rOp);
                case ">=" -> inst = new BinaryInst(Type.BoolType.getType(), Instruction.TAG.GE, lOp, rOp);
                case "<" -> inst = new BinaryInst(Type.BoolType.getType(), Instruction.TAG.LT, lOp, rOp);
                case ">" -> inst = new BinaryInst(Type.BoolType.getType(), Instruction.TAG.GT, lOp, rOp);
                case "==" -> inst = new BinaryInst(Type.BoolType.getType(), Instruction.TAG.EQ, lOp, rOp);
                case "!=" -> inst = new BinaryInst(Type.BoolType.getType(), Instruction.TAG.NE, lOp, rOp);
                default -> {}
            }
        }

        else {
            switch (opr) {
                case "<=" -> inst = new BinaryInst(Type.BoolType.getType() , Instruction.TAG.FLE, lOp, rOp);
                case ">=" -> inst = new BinaryInst(Type.BoolType.getType(), Instruction.TAG.FGE, lOp, rOp);
                case "<" -> inst = new BinaryInst(Type.BoolType.getType(), Instruction.TAG.FLT, lOp, rOp);
                case ">" -> inst = new BinaryInst(Type.BoolType.getType(), Instruction.TAG.FGT, lOp, rOp);
                case "==" -> inst = new BinaryInst(Type.BoolType.getType(), Instruction.TAG.FEQ, lOp, rOp);
                case "!=" -> inst = new BinaryInst(Type.BoolType.getType(), Instruction.TAG.FNE, lOp, rOp);
                default -> {}
            }
        }

        if (inst == null) {
            throw new RuntimeException("Operand '" + opr + "' cannot be recognized.");
        }

        getCurBB().list.addLast(inst.node) ;
        return inst;
    }

    public ConversionInst.Zext buildZExt(Value srcVal) {

        if (!srcVal.getType().isBoolType()) {
            throw new RuntimeException("A non-i1 src Value is given.");
        }

        ConversionInst.Zext zext = new ConversionInst.Zext(srcVal);
        getCurBB().list.addLast(zext.node) ;
        return zext;
    }

    public Constant.ConstantArray buildConstArr(ArrayType arrType, ArrayList<Constant> initList) {

        boolean isZ = true;
        for(int i = initList.size()-1;i>=0;i--)
        {
            if (initList.get(i).getName().equals("0")||initList.get(i).getName().equals("0.0")) {
                continue;
            }
            else{
                isZ = false;
                break;
            }
        }
        if(isZ == true)
        {
            return new Constant.ConstantArray(arrType, initList);
        }
        else{
            Type primType = arrType.getAtomType();
            Constant prim = new Constant(primType);
            while (arrType.getAtomLen() > initList.size()) {
                initList.add(prim);
            }


            if (arrType.getElemType().isArrayType()) {

                ArrayList<Constant> nestedInitList = new ArrayList<>();
                int j = 0;
                int step = arrType.getAtomLen() / arrType.getLen();
                while(j < initList.size()) {
                    nestedInitList.add(
                            buildConstArr(
                                    (ArrayType) arrType.getElemType(),
                                    new ArrayList<>(initList.subList(j, j + step))
                            )
                    );
                    j += step;
                }

                return new Constant.ConstantArray(arrType, nestedInitList);
            }
            else {
                Type elemType = arrType.getElemType();
                for (int i = 0; i < initList.size(); i++) {
                    Value elem = initList.get(i);
                    if (elem.getType() != elemType) {
                        if (elemType.isFloatType()) {
                            int numericVal = ((Constant.ConstantInt) elem).getVal();
                            initList.set(i, buildConstant((float) numericVal));
                        }
                        else if (elemType.isIntegerType()) {
                            float numericVal = ((Constant.ConstantFloat) elem).getVal();
                            initList.set(i, buildConstant((int) numericVal));
                        }
                    }
                }

                return new Constant.ConstantArray(arrType, initList);
            }
        }


    }
}


