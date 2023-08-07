package ast;

import frontend.SysY2022BaseVisitor;
import frontend.SysY2022Parser;
import frontend.SysY2022Parser.ReturnStmtContext;
import ir.Instruction;
import ir.Instructions.BinaryInst;
import ir.Instructions.MemoryInst;
import ir.Module;
import ir.Type;
import ir.Type.FloatType;
import ir.Type.IntegerType;
import ir.Value;
import ir.types.ArrayType;
import ir.types.FunctionType;
import ir.types.PointerType;
import ir.values.BasicBlock;
import ir.values.Constant;
import ir.values.Function;
import ir.values.GlobalVariable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Visitor extends SysY2022BaseVisitor<Void> {
    private final Scope scope = new Scope();
    String curFuncName = "";
    private final boolean ON = true;
    private final boolean OFF = false;
    private Builder builder;
    private boolean envConstFolding = OFF;
    private boolean envBuildFCall = OFF;
    private boolean isConstantVar = false;
    private enum DataType {FLT,INT}

    private DataType envConveyedType = null;
    private Value retVal_;
    private ArrayList<Value> retValList_;
    private Type retType_;
    private ArrayList<Type> retTypeList_;
    private int retInt_;
    private float retFloat_;
    Stack<ArrayList<ir.Instructions.TerminatorInst.Br>> bpStk = new Stack<>();
    private final BasicBlock BREAK = new BasicBlock("BRK_PLACEHOLDER");
    private final BasicBlock CONTINUE = new BasicBlock("CONT_PLACEHOLDER");

    public Visitor(Module module) {
        this.builder = new Builder(module);
        this.addRuntimeFunction();
    }

    private void addRuntimeFunction() {
        Type i32Type = IntegerType.getType();
        Type floatType = FloatType.getType();
        Type voidType = Type.VoidType.getType();
        Type ptrI32Type = PointerType.getPointerType(i32Type);
        Type ptrFloatType = PointerType.getPointerType(floatType);
        Type ptrVoidType = PointerType.getPointerType(voidType);

        ArrayList<Type> emptyArgTypeList = new ArrayList<>();
        ArrayList<Type> intArgTypeList = new ArrayList<>(List.of(i32Type));
        ArrayList<Type> floatArgTypeList = new ArrayList<>(List.of(floatType));
        ArrayList<Type> ptrI32ArgTypeList = new ArrayList<>(List.of(ptrI32Type));
        ArrayList<Type> ptrFloatArgTypeList = new ArrayList<>(List.of(ptrFloatType));

        // i32 getint()
        scope.addSymbol("getint",
                builder.buildFunction("getint", new FunctionType(i32Type, emptyArgTypeList), true)
        );
        // i32 getch()
        scope.addSymbol("getch",
                builder.buildFunction("getch", new FunctionType(i32Type, emptyArgTypeList), true)
        );
        // i32 getfloat()
        scope.addSymbol("getfloat",
                builder.buildFunction("getfloat", new FunctionType(floatType, emptyArgTypeList), true)
        );
        // i32 getarray(i32*)
        scope.addSymbol("getarray",
                builder.buildFunction("getarray", new FunctionType(i32Type, ptrI32ArgTypeList), true)
        );
        // i32 getfarray(float*)
        scope.addSymbol("getfarray",
                builder.buildFunction("getfarray", new FunctionType(i32Type, ptrFloatArgTypeList), true)
        );
        // void putint(i32)
        scope.addSymbol("putint",
                builder.buildFunction("putint", new FunctionType(voidType, intArgTypeList), true)
        );
        // void putch(i32)
        scope.addSymbol("putch",
                builder.buildFunction("putch", new FunctionType(voidType, intArgTypeList), true)
        );
        // void putfloat(float)
        scope.addSymbol("putfloat",
                builder.buildFunction("putfloat", new FunctionType(voidType, floatArgTypeList), true)
        );
        // void putarray(i32, i32*)
        scope.addSymbol("putarray",
                builder.buildFunction("putarray",
                        new FunctionType(voidType, new ArrayList<>(Arrays.asList(i32Type, ptrI32Type))),
                        true)
        );
        // void putfarray(i32, float*)
        scope.addSymbol("putfarray",
                builder.buildFunction("putfarray",
                        new FunctionType(voidType, new ArrayList<>(Arrays.asList(i32Type, ptrFloatType))),
                        true)
        );
        // void starttime()
        scope.addSymbol("starttime",
                builder.buildFunction("_sysy_starttime", new FunctionType(voidType, intArgTypeList), true)
        );
        // void stoptime()
        scope.addSymbol("stoptime",
                builder.buildFunction("_sysy_stoptime", new FunctionType(voidType, intArgTypeList), true)
        );

        scope.addSymbol("memset",
                builder.buildFunction("memset", new FunctionType(voidType, new ArrayList<>(Arrays.asList(ptrI32Type,i32Type,i32Type))), true)) ;
    }

    private void setConstFolding(boolean stat) {
        envConstFolding = stat;
    }

    public boolean inConstFolding() {
        return envConstFolding;
    }

    private void setBuildFCall(boolean stat) {
        envBuildFCall = stat;
    }

    private boolean inBuildFCall() {
        return envBuildFCall;
    }

    private DataType getConveyedType() {
        return envConveyedType;
    }

    private void setConveyedType(DataType dataType) {
        envConveyedType = dataType;
    }

    @Override
    public Void visitScalarConstDef(SysY2022Parser.ScalarConstDefContext ctx) {
        visit(ctx.constInitVal());
        switch(ctx.getParent().getChild(1).getText()){
            case "int" -> {
                if(getConveyedType() == DataType.FLT){
                    float tmp = ((Constant.ConstantFloat)retVal_).getVal();
                    retVal_ = builder.buildConstant((int)tmp);
                }
            }
            case "float" -> {
                if(getConveyedType() == DataType.INT){
                    int tmp = ((Constant.ConstantInt)retVal_).getVal();
                    retVal_ = builder.buildConstant((float)tmp);
                }
            }
            default->{
                throw new RuntimeException("Datatype"+"'"+ctx.getParent().getChild(1).getText()+"'"+"is not supported");
            }
        }
        scope.addSymbol(ctx.Ident().getText(), retVal_,"const");
        return null;
    }

    @Override
    public Void visitScalarVarDef(SysY2022Parser.ScalarVarDefContext ctx) {
        if (scope.isGlobal()) {
            GlobalVariable glbVar;
            if (ctx.initVal() != null) {
                visit(ctx.initVal());
                switch(ctx.getParent().getChild(0).getText()){
                    case "int" -> {
                        if(getConveyedType() == DataType.FLT){
                            float tmp = ((Constant.ConstantFloat)retVal_).getVal();
                            retVal_ = builder.buildConstant((int)tmp);
                        }
                    }
                    case "float" -> {
                        if(getConveyedType() == DataType.INT){
                            int tmp = ((Constant.ConstantInt)retVal_).getVal();
                            retVal_ = builder.buildConstant((float)tmp);
                        }
                    }
                    default->{
                        throw new RuntimeException("Datatype"+"'"+ctx.getParent().getChild(0).getText()+"'"+"is not supported");
                    }
                }
                glbVar = builder.buildGlobalVar(ctx.Ident().getText(), (Constant)retVal_);
            }
            else {
                switch (ctx.getParent().getChild(0).getText()) {
                    case "int" -> {
                        glbVar = builder.buildGlobalVar(ctx.Ident().getText(), IntegerType.getType());
                    }
                    case "float" -> {
                        glbVar = builder.buildGlobalVar(ctx.Ident().getText(), FloatType.getType());
                    }
                    default -> throw new RuntimeException("Datatype"+"'"+ctx.getParent().getChild(0).getText()+"'"+"is not supported"); // Impossible case.
                }
            }
            scope.addSymbol(ctx.Ident().getText(), glbVar);
        }
        else {
            MemoryInst.Alloca addrAllocated;
            switch (ctx.getParent().getChild(0).getText()) {
                case "int" -> {
                    addrAllocated = builder.buildAlloca(IntegerType.getType());
                }
                case "float" -> {
                    addrAllocated = builder.buildAlloca(FloatType.getType());
                }
                default -> throw new RuntimeException("Datatype"+"'"+ctx.getParent().getChild(0).getText()+"'"+"is not supported");
            }
            scope.addSymbol(ctx.Ident().getText(), addrAllocated);
            if (ctx.initVal() != null) {
                visit(ctx.initVal());
                if (retVal_.getType().isIntegerType() && addrAllocated.allocatedType.isFloatType()) {
                    retVal_ = builder.buildSitofp(retVal_);
                }
                else if(retVal_.getType().isFloatType() && addrAllocated.allocatedType.isIntegerType()) {
                    retVal_ = builder.buildFptosi(retVal_);
                }
                builder.buildStore(retVal_, addrAllocated);
            }
        }
        return null;
    }

    @Override
    public Void visitScalarConstInitVal(SysY2022Parser.ScalarConstInitValContext ctx) {
        this.setConstFolding(ON);
        super.visitScalarConstInitVal(ctx);
        this.setConstFolding(OFF);
        switch (getConveyedType()) {
            case INT -> retVal_ = builder.buildConstant(retInt_);
            case FLT -> retVal_ = builder.buildConstant(retFloat_);
        }
        return null;
    }

    @Override
    public Void visitScalarInitVal(SysY2022Parser.ScalarInitValContext ctx) {
        if (scope.isGlobal()) {
            this.setConstFolding(ON);
        }
        super.visitScalarInitVal(ctx);
        if (scope.isGlobal()) {
            switch (getConveyedType()) {
                case INT -> retVal_ = builder.buildConstant(retInt_);
                case FLT -> retVal_ = builder.buildConstant(retFloat_);
            }
            setConstFolding(OFF);
        }

        return null;
    }

    @Override
    public Void visitFuncDef(SysY2022Parser.FuncDefContext ctx) {
        String funcName = ctx.Ident().getText();
        curFuncName  = funcName;
        Type retType;
        String strRetType = ctx.funcType().getText();
        switch (strRetType) {
            case "int" -> retType = ir.Type.IntegerType.getType();
            case "float" -> retType = FloatType.getType();
            case "void" -> retType = Type.VoidType.getType();
            default -> throw new RuntimeException("Unsupported function return type.");
        }

        ArrayList<Type> argTypes = new ArrayList<>();
        if (ctx.funcFParams() != null) {
            visit(ctx.funcFParams());
            argTypes.addAll(retTypeList_);
        }
        retTypeList_ = new ArrayList<>();

        if (!scope.isGlobal()) {
            throw new RuntimeException("Nested definition of function: " + funcName);
        }

        FunctionType funcType = new FunctionType(retType, argTypes);
        Function function = builder.buildFunction(funcName, funcType, false);
        scope.addSymbol(funcName, function);
        BasicBlock bb = builder.buildBB(funcName + "_ENTRY");
        scope.pushTable();
        for (int i = 0; i < function.getParamList().size(); i++) {
            Function.Param arg = function.getParamList().get(i);
            MemoryInst.Alloca localVar = builder.buildAlloca(arg.getType());

            String argName = null;
            if (ctx.funcFParams().funcFParam(i) instanceof SysY2022Parser.ScalarFuncFParamContext) {
                var ctxArg = (SysY2022Parser.ScalarFuncFParamContext) ctx.funcFParams().funcFParam(i);
                argName = ctxArg.Ident().getText();
            } else if (ctx.funcFParams().funcFParam(i) instanceof SysY2022Parser.ArrFuncFParamContext) {
                var ctxArg = (SysY2022Parser.ArrFuncFParamContext) ctx.funcFParams().funcFParam(i);
                argName = ctxArg.Ident().getText();
            }
            scope.addSymbol(argName, localVar);
//             Copy the value to the local memory.
            builder.buildStore(arg, localVar);
        }
        visit(ctx.block());
        Instruction tailInst = builder.getCurBB().getLastInst();
        if (tailInst == null || !tailInst.isTerminator()) {

            if (((FunctionType) function.getType()).getRetType().isVoidType()) {
                builder.buildRet();

            } else if (((FunctionType) function.getType()).getRetType().isIntegerType()) {
                builder.buildRet(builder.buildConstant(0));

            }
            else if (((FunctionType) function.getType()).getRetType().isFloatType()) {
                builder.buildRet(builder.buildConstant(.0f));
            }
        }
        scope.popTable();
        return null;
    }

    @Override
    public Void visitUnaryExp2(SysY2022Parser.UnaryExp2Context ctx) {
        setBuildFCall(ON);
        String funcName = ctx.Ident().getText();
        Value val = scope.getVal(funcName);
        if (val == null) {
            throw new RuntimeException("Undefined name: " + funcName + ".");
        }
        if (!val.getType().isFunctionType()) {
            throw new RuntimeException(funcName + " is not a function and cannot be invoked.");
        }
        Function func = (Function) val;
        ArrayList<Value> args = new ArrayList<>();
        if (ctx.funcRParams() != null) {
            var argCtxs = ctx.funcRParams().funcRParam();
            ArrayList<Type> argTypes = ((FunctionType)func.getType()).getParamTypeList();
            for (int i = 0; i < argCtxs.size(); i++) {

                var argCtx = argCtxs.get(i);
                Type typeRequired = argTypes.get(i);
                visit(argCtx);
                Value realArg = retVal_;
                if (!typeRequired.isPointerType() && realArg.getType().isPointerType()) {

                    realArg = builder.buildLoad(typeRequired, realArg);
                }
                if (typeRequired.isPointerType() && realArg.getType().isPointerType()) {
                    while (((PointerType)realArg.getType() ).getPointedType()
                            != ((PointerType)typeRequired).getPointedType()) {
                        System.out.println("?");
                        realArg = builder.buildGEP(realArg, new ArrayList<>() {{
                            add(builder.buildConstant(0) );
                            add(builder.buildConstant(0));
                        }});
                        if (!((PointerType) realArg.getType()).getPointedType().isPointerType()) {
                            break;
                        }
                    }
                }
                //需要I1吗？
                if (typeRequired.isIntegerType() && realArg.getType().isFloatType()) {
                    realArg = builder.buildFptosi(realArg);
                }
                else if (typeRequired.isFloatType() && realArg.getType().isIntegerType()) {
                    realArg = builder.buildSitofp(realArg);
                }
                args.add(realArg);
            }
        }

        else if (funcName.equals("starttime") || funcName.equals("stoptime")) {
            args.add(Constant.ConstantInt.getConstantInt(0) );
        }
        retVal_ = builder.buildCall(func, args);
        setBuildFCall(OFF);
        return null;
    }

    @Override
    public Void visitUnaryExp3(SysY2022Parser.UnaryExp3Context ctx) {
        if (this.inConstFolding()) {
            visit(ctx.unaryExp());
            switch (getConveyedType()) {
                case INT -> {
                    switch (ctx.unaryOp().getText()) {
                        case "-" -> retInt_ = -retInt_;
                        case "!" -> retInt_ = (retInt_ == 0) ? 0 : 1;
                        case "+" -> {}
                    }
                }
                case FLT -> {
                    switch (ctx.unaryOp().getText()) {
                        case "-" -> retFloat_ = -retFloat_;
                        case "!" -> retFloat_ = (retFloat_ == .0f) ? 0 : 1;
                        case "+" -> {}
                    }
                }
                default -> {}
            }
        }
        else {

            System.out.println(retVal_);
            System.out.println(retVal_.getType());
            visit(ctx.unaryExp());
            if (retVal_.getType().isIntegerType()) {
                switch (ctx.unaryOp().getText()) {
                    case "-" -> retVal_ = builder.buildSub(builder.buildConstant(0), retVal_);
                    case "!" -> retVal_ = builder.buildComparison("==", builder.buildConstant(0), retVal_);
                    case "+" -> {}
                }
            }
            else if(retVal_.getType().isBoolType()){
                retVal_ = builder.buildZExt(retVal_);
                switch (ctx.unaryOp().getText()) {
                    case "-" -> retVal_ = builder.buildSub(builder.buildConstant(0), retVal_);
                    case "!" -> retVal_ = builder.buildComparison("==", builder.buildConstant(0), retVal_);
                    case "+" -> {}
                }
            }
            else{
                switch (ctx.unaryOp().getText()) {
                    case "-" -> retVal_ = builder.buildFneg(Instruction.TAG.FNEG, retVal_);
                    case "!" -> retVal_ = builder.buildComparison("==", builder.buildConstant(.0f), retVal_);
                    case "+" -> {}
                }
            }
        }
        return null;
    }

    @Override
    public Void visitFuncFParams(SysY2022Parser.FuncFParamsContext ctx) {
        retTypeList_ = new ArrayList<>();
        for (SysY2022Parser.FuncFParamContext funcFParamContext : ctx.funcFParam()) {
            visit(funcFParamContext);
            retTypeList_.add(retType_);
        }
        return null;
    }

    @Override
    public Void visitScalarFuncFParam(SysY2022Parser.ScalarFuncFParamContext ctx) {
        switch (ctx.bType().getText()) {
            case "int" -> retType_ = IntegerType.getType();
            case "float" -> retType_ = FloatType.getType();
            default -> throw new RuntimeException("Supported function argument type.");
        }
        return null;
    }

    @Override
    public Void visitBlock(SysY2022Parser.BlockContext ctx) {
        scope.pushTable();
        ctx.blockItem().forEach(this::visit);
        scope.popTable();
        return null;
    }

    @Override
    public Void visitIfelseStmt(SysY2022Parser.IfelseStmtContext ctx) {

        BasicBlock preBlk = builder.getCurBB();
        BasicBlock entryBlk = builder.buildBB("_COND_ENTRY");

        builder.setCurBB(preBlk);
        builder.buildBr(entryBlk);


        BasicBlock exitBlk = builder.buildBB("_COND_EXIT");

        BasicBlock trueEntryBlk = builder.buildBB("_THEN");
        visit(ctx.stmt(0));
        BasicBlock trueExitBlk = builder.getCurBB();

        Instruction trueExitBlkLastInst = trueExitBlk.getLastInst();
        boolean trueBlkEndWithTerminator = trueExitBlkLastInst != null &&
                trueExitBlkLastInst.isTerminator();

        BasicBlock falseEntryBlk = null;
        BasicBlock falseExitBlk = null;
        boolean falseBlkEndWithTerminator = false;
        falseEntryBlk = builder.buildBB("_ELSE");
        visit(ctx.stmt(1));
        falseExitBlk = builder.getCurBB();

        Instruction falseExitBlkLastInst = falseExitBlk.getLastInst();
        falseBlkEndWithTerminator = falseExitBlkLastInst != null && falseExitBlkLastInst.isTerminator();


        if (!trueBlkEndWithTerminator) {
            builder.setCurBB(trueExitBlk);
            builder.buildBr(exitBlk);
        }
        if (falseEntryBlk != null && !falseBlkEndWithTerminator) {
            builder.setCurBB(falseExitBlk);
            builder.buildBr(exitBlk);
        }


        builder.setCurBB(entryBlk);

        ctx.cond().lOrExp().trueBlk = trueEntryBlk;
        ctx.cond().lOrExp().falseBlk = (falseEntryBlk != null) ? falseEntryBlk : exitBlk;

        visit(ctx.cond());


        builder.setCurBB(exitBlk);

        return null;
    }

    @Override
    public Void visitIfStmt(SysY2022Parser.IfStmtContext ctx) {

        BasicBlock preBlk = builder.getCurBB();
        BasicBlock entryBlk = builder.buildBB("_COND_ENTRY");

        builder.setCurBB(preBlk);
        builder.buildBr(entryBlk);


        BasicBlock exitBlk = builder.buildBB("_COND_EXIT");

        BasicBlock trueEntryBlk = builder.buildBB("_THEN");
        visit(ctx.stmt());
        BasicBlock trueExitBlk = builder.getCurBB();

        Instruction trueExitBlkLastInst = trueExitBlk.getLastInst();
        boolean trueBlkEndWithTerminator = trueExitBlkLastInst != null &&
                trueExitBlkLastInst.isTerminator();

        BasicBlock falseEntryBlk = null;
        BasicBlock falseExitBlk = null;
        boolean falseBlkEndWithTerminator = false;

        if (!trueBlkEndWithTerminator) {
            builder.setCurBB(trueExitBlk);
            builder.buildBr(exitBlk);
        }
        if (falseEntryBlk != null && !falseBlkEndWithTerminator) {
            builder.setCurBB(falseExitBlk);
            builder.buildBr(exitBlk);
        }


        builder.setCurBB(entryBlk);

        ctx.cond().lOrExp().trueBlk = trueEntryBlk;
        ctx.cond().lOrExp().falseBlk = (falseEntryBlk != null) ? falseEntryBlk : exitBlk;

        visit(ctx.cond());


        builder.setCurBB(exitBlk);

        return null;
    }


    @Override
    public Void visitLOrExp(SysY2022Parser.LOrExpContext ctx) {

        for(int i = 0; i < ctx.lAndExp().size() - 1; i++) {
            BasicBlock curLOrBlk = builder.getCurBB();
            BasicBlock nxtLOrBlk = builder.buildBB("nxtLor");


            ctx.lAndExp(i).falseBlk = nxtLOrBlk;
            ctx.lAndExp(i).trueBlk = ctx.trueBlk;

            builder.setCurBB(curLOrBlk);
            visit(ctx.lAndExp(i));
            builder.setCurBB(nxtLOrBlk);
        }




        ctx.lAndExp(ctx.lAndExp().size() - 1).falseBlk = ctx.falseBlk;
        ctx.lAndExp(ctx.lAndExp().size() - 1).trueBlk = ctx.trueBlk;
        visit(ctx.lAndExp(ctx.lAndExp().size() - 1));


        return null;
    }




    @Override
    public Void visitLAndExp(SysY2022Parser.LAndExpContext ctx) {
        for(int i = 0; i < ctx.eqExp().size(); i++) {
            visit(ctx.eqExp(i));


            if(retVal_.getType().isIntegerType()) { // i32 -> i1

                retVal_ = builder.buildComparison("!=", retVal_, Constant.ConstantInt.getConstantInt(0) );
            }
            else if (retVal_.getType().isFloatType()) { // float -> i1
                retVal_ = builder.buildComparison("!=", retVal_, Constant.ConstantFloat.getConstantFloat(.0f));
            }


            if(i < ctx.eqExp().size() - 1) {

                BasicBlock originBlk = builder.getCurBB();
                BasicBlock nxtAndBlk = builder.buildBB("nxtLand");

                builder.setCurBB(originBlk);
                builder.buildBr(retVal_, nxtAndBlk, ctx.falseBlk);
                builder.setCurBB(nxtAndBlk);
            }

            else {
                builder.buildBr(retVal_, ctx.trueBlk, ctx.falseBlk);
            }
        }

        return null;
    }


    @Override
    public Void visitEqExp(SysY2022Parser.EqExpContext ctx) {

        visit(ctx.relExp(0));
        Value lOp = retVal_;

        for (int i = 1; i < ctx.relExp().size(); i++) {

            visit(ctx.relExp(i));
            Value rOp = retVal_;


            if (lOp.getType().isFloatType() && !rOp.getType().isFloatType()) {
                rOp = builder.buildSitofp(rOp);
            }
            else if (!lOp.getType().isFloatType() && rOp.getType().isFloatType()) {
                lOp = builder.buildSitofp(lOp);
            }
            else {

                if(lOp.getType().isIntegerType() && rOp.getType().isBoolType()) {
                    rOp = builder.buildZExt(rOp);
                }
                if(rOp.getType().isIntegerType() && lOp.getType().isBoolType()) {
                    lOp = builder.buildZExt(lOp);
                }
            }


            String opr = ctx.getChild(2 * i - 1).getText();
            lOp = builder.buildComparison(opr, lOp, rOp);
        }

        retVal_ = lOp;

        return null;
    }


    @Override
    public Void visitRelExp(SysY2022Parser.RelExpContext ctx) {

        visit(ctx.addExp(0));
        Value lOp = retVal_;

        for (int i = 1; i < ctx.addExp().size(); i++) {

            visit(ctx.addExp(i));
            Value rOp = retVal_;


            if (lOp.getType().isFloatType() && !rOp.getType().isFloatType()) {
                rOp = builder.buildSitofp(rOp);
            }
            else if (!lOp.getType().isFloatType() && rOp.getType().isFloatType()) {
                lOp = builder.buildSitofp(lOp);
            }
            else {

                if (lOp.getType().isIntegerType() && rOp.getType().isBoolType()) {
                    rOp = builder.buildZExt(rOp);
                }
                if (rOp.getType().isIntegerType() && lOp.getType().isBoolType()) {
                    lOp = builder.buildZExt(lOp);
                }
            }


            String opr = ctx.getChild(2 * i - 1).getText();
            lOp = builder.buildComparison(opr, lOp, rOp);
        }

        retVal_ = lOp;


        return null;
    }

    @Override
    public Void visitWhileStmt(SysY2022Parser.WhileStmtContext ctx) {

        bpStk.push(new ArrayList<>());




        BasicBlock preBlk = builder.getCurBB();


        BasicBlock condEntryBlk = builder.buildBB("_WHILE_ENTRY");

        builder.setCurBB(preBlk);
        builder.buildBr(condEntryBlk);


        BasicBlock bodyEntryBlk = builder.buildBB("_WHILE_BODY");
        BasicBlock exitBlk = builder.buildBB("_WHILE_EXIT");


        ctx.cond().lOrExp().trueBlk = bodyEntryBlk;
        ctx.cond().lOrExp().falseBlk = exitBlk;

        builder.setCurBB(condEntryBlk);
        visit(ctx.cond());


        builder.setCurBB(bodyEntryBlk);
        visit(ctx.stmt());
        BasicBlock bodyExitBlk = builder.getCurBB();

        if (bodyExitBlk.list.isEmpty() || !bodyExitBlk.getLastInst().isTerminator()) {
            builder.setCurBB(bodyExitBlk);
            builder.buildBr(condEntryBlk);
        }


        builder.setCurBB(exitBlk);


        for (ir.Instructions.TerminatorInst.Br br : bpStk.pop()) {
            if (br.getOperandAt(0) == BREAK) {
                br.setOperand(exitBlk, 0);
//                // 块的前后序关系
//                builder.getCurBB().nextList.add(exitBlk);
//                exitBlk.preList.add(builder.getCurBB());
            }
            else if (br.getOperandAt(0) == CONTINUE) {
                br.setOperand(condEntryBlk, 0);
//                // 块的前后序关系
//                builder.getCurBB().nextList.add(condEntryBlk);
//                condEntryBlk.preList.add(builder.getCurBB());
            }
            else {
                throw new RuntimeException("Invalid block placeholder occurs in the stack.");
            }
        }

        return null;
    }

    /**
     * stmt : 'break' ';' # breakStmt
     */
    @Override
    public Void visitBreakStmt(SysY2022Parser.BreakStmtContext ctx) {
        bpStk.peek().add(builder.buildBr(BREAK));
        builder.buildBB("_FOLLOWING_BLK");
        return null;
    }

    /**
     * stmt : 'continue' ';' # contStmt
     */
    @Override
    public Void visitContinueStmt(SysY2022Parser.ContinueStmtContext ctx) {
        bpStk.peek().add(builder.buildBr(CONTINUE));
        builder.buildBB("_FOLLOWING_BLK");
        return null;
    }

    @Override
    public Void visitScalarLVal(SysY2022Parser.ScalarLValContext ctx) {
        String name = ctx.Ident().getText();
        isConstantVar = scope.checkVarType(name);

        Value val = scope.getVal(name);
        if (val == null) {
            throw new RuntimeException("Undefined value: " + name);
        }

        //如果这个lval能被表达成primaryexp就直接返回值
        if (val.getType().isIntegerType() || val.getType().isFloatType()) {

            retVal_ = val;
        }
        //否则返回变量的地址，即一个指针变量
        else if (val.getType().isPointerType()) {
            Type pointedType = ((PointerType) val.getType()).getPointedType();
            if (pointedType.isPointerType()) {
                retVal_ = builder.buildLoad(pointedType, val);
            }
            else if (pointedType.isArrayType()) {
                retVal_ = builder.buildGEP(val, new ArrayList<>(){{
                    add(builder.buildConstant(0));
                    add(builder.buildConstant(0));
                }});
            }
            //函数的情况？？？？
            else {
                if (inBuildFCall()) {
                    val = builder.buildLoad(pointedType, val);
                }
                retVal_ = val;
            }
        }
        return null;
    }

    @Override
    public Void visitAssignment(SysY2022Parser.AssignmentContext ctx) {
        visit(ctx.lVal());
        Value addr = retVal_;
        if(isConstantVar){
            this.isConstantVar = false;
        }
        visit(ctx.exp());
        Value val = retVal_;
        Type destType = ((PointerType) addr.getType()).getPointedType();
        if (destType.isFloatType() && val.getType().isIntegerType()) {
            val = builder.buildSitofp(val);
        } else if (destType.isIntegerType() && val.getType().isFloatType()) {
            val = builder.buildFptosi(val);
        }
        builder.buildStore(val, addr);
        return null;
    }

    @Override
    public Void visitReturnStmt(ReturnStmtContext ctx) {
        if (ctx.exp() != null) {
            visit(ctx.exp());
            Value retVal = retVal_;
            Type retType = ((FunctionType) builder.getCurFunc().getType()).getRetType();
            if (retVal.getType().isIntegerType() && retType.isFloatType()) {
                retVal = builder.buildSitofp(retVal);
            } else if (retVal.getType().isFloatType() && retType.isIntegerType()) {
                retVal = builder.buildFptosi(retVal);
            }
            builder.buildRet(retVal);
        }
        else {
            builder.buildRet();
        }
        return null;
    }


    @Override
    public Void visitAddExp(SysY2022Parser.AddExpContext ctx) {
        if (this.inConstFolding()) {
            int rOpInt = 0;
            float rOpFloat = 0;
            visit(ctx.mulExp(0));
            var curType = this.getConveyedType();
            switch (curType) {
                case INT -> {
                    rOpInt = retInt_;
                    for (int i = 1; i < ctx.mulExp().size(); i++) {
                        visit(ctx.mulExp(i));
                        if (this.getConveyedType() == DataType.FLT) {
                            if (curType == DataType.INT) {
                                rOpFloat = rOpInt;
                                curType = DataType.FLT;
                            }
                            switch (ctx.getChild(i * 2 - 1).getText()) {
                                case "+" -> rOpFloat += retFloat_;
                                case "-" -> rOpFloat -= retFloat_;
                                default -> throw new RuntimeException("Unsupported operation in visitMulExp().");
                            }
                        }
                        else {
                            if (curType == DataType.INT) {
                                switch (ctx.getChild(i * 2 - 1).getText()) {
                                    case "+" -> rOpInt += retInt_;
                                    case "-" -> rOpInt -= retInt_;
                                }
                            }
                            else {
                                switch (ctx.getChild(i * 2 - 1).getText()) {
                                    case "+" -> rOpFloat += retInt_;
                                    case "-" -> rOpFloat -= retInt_;
                                    default -> throw new RuntimeException("Unsupported operation in visitMulExp().");
                                }
                            }
                        }
                    }
                }
                case FLT -> {
                    rOpFloat = retFloat_;
                    for (int i = 1; i < ctx.mulExp().size(); i++) {
                        visit(ctx.mulExp(i));

                        if (this.getConveyedType() == DataType.INT) {
                            switch (ctx.getChild(i * 2 - 1).getText()) {
                                case "+" -> rOpFloat += retInt_;
                                case "-" -> rOpFloat -= retInt_;
                            }
                        } else {
                            switch (ctx.getChild(i * 2 - 1).getText()) {
                                case "+" -> rOpFloat += retFloat_;
                                case "-" -> rOpFloat -= retFloat_;
                            }
                        }
                    }
                }
                default ->
                        throw new RuntimeException("Unsupported Datatype in visitMulExp().");
            }
            this.setConveyedType(curType);
            switch (curType) {
                case INT -> retInt_ = rOpInt;
                case FLT -> retFloat_ = rOpFloat;
            }
        }
        else {
            visit(ctx.mulExp(0));
            Value lOp = retVal_;
            for (int i = 1; i < ctx.mulExp().size(); i++) {
                visit(ctx.mulExp(i));
                Value rOp = retVal_;
                if (lOp.getType().isPointerType()) {
                    lOp = builder.buildLoad(((PointerType) lOp.getType()).getPointedType(), lOp);
                }
                if (rOp.getType().isPointerType()) {
                    rOp = builder.buildLoad(((PointerType) rOp.getType()).getPointedType(), rOp);
                }
                if (lOp.getType().isBoolType()) {
                    lOp = builder.buildZExt(lOp);
                }
                if (rOp.getType().isBoolType() ) {
                    rOp = builder.buildZExt(rOp);
                }
                if (lOp.getType().isIntegerType() && rOp.getType().isFloatType()) {
                    lOp = builder.buildSitofp(lOp);
                }
                else if (lOp.getType().isFloatType() && rOp.getType().isIntegerType()) {
                    rOp = builder.buildSitofp(rOp);
                }
                switch (ctx.getChild(2 * i - 1).getText()) {
                    case "+" -> lOp = builder.buildAdd(lOp, rOp);
                    case "-" -> lOp = builder.buildSub(lOp, rOp);
                    default -> {}
                }
            }

            retVal_ = lOp;
        }

        return null;
    }




    @Override
    public Void visitMulExp(SysY2022Parser.MulExpContext ctx) {
        if (this.inConstFolding()) {
            int rOpInt = 0;
            float rOpFloat = 0;
            visit(ctx.unaryExp(0));
            var curType = this.getConveyedType();

            switch (curType) {
                case INT -> {
                    rOpInt = retInt_;
                    for (int i = 1; i < ctx.unaryExp().size(); i++) {
                        visit(ctx.unaryExp(i));
                        if (this.getConveyedType() == DataType.FLT) {
                            if (curType == DataType.INT) {
                                rOpFloat = rOpInt;
                                curType = DataType.FLT;
                            }
                            switch (ctx.getChild(i * 2 - 1).getText()) {
                                case "*" -> rOpFloat *= retFloat_;
                                case "/" -> rOpFloat /= retFloat_;
                                default -> throw new RuntimeException("Unsupported operation in visitMulExp().");
                            }
                        }
                        else {
                            if (curType == DataType.INT) {
                                switch (ctx.getChild(i * 2 - 1).getText()) {
                                    case "*" -> rOpInt *= retInt_;
                                    case "/" -> rOpInt /= retInt_;
                                    case "%" -> rOpInt %= retInt_;
                                }
                            }
                            else {
                                switch (ctx.getChild(i * 2 - 1).getText()) {
                                    case "*" -> rOpFloat *= retInt_;
                                    case "/" -> rOpFloat /= retInt_;
                                    default -> throw new RuntimeException("Unsupported operation in visitMulExp().");
                                }
                            }
                        }
                    }
                }
                case FLT -> {
                    rOpFloat = retFloat_;
                    for (int i = 1; i < ctx.unaryExp().size(); i++) {
                        visit(ctx.unaryExp(i));

                        if (this.getConveyedType() == DataType.INT) {
                            switch (ctx.getChild(i * 2 - 1).getText()) {
                                case "*" -> rOpFloat *= retInt_;
                                case "/" -> rOpFloat /= retInt_;
                            }
                        } else {
                            switch (ctx.getChild(i * 2 - 1).getText()) {
                                case "*" -> rOpFloat *= retFloat_;
                                case "/" -> rOpFloat /= retFloat_;
                            }
                        }
                    }
                }
                default ->
                        throw new RuntimeException("Unsupported Datatype in visitMulExp().");
            }
            this.setConveyedType(curType);
            switch (curType) {
                case INT -> retInt_ = rOpInt;
                case FLT -> retFloat_ = rOpFloat;
            }
        }
        else {
            Value lOp;
            visit(ctx.unaryExp(0));
            lOp = retVal_;
            for (int i = 1; i < ctx.unaryExp().size(); i++) {
                visit(ctx.unaryExp(i));
                Value rOp = retVal_;
                if (lOp.getType().isPointerType()) {
                    lOp = builder.buildLoad(((PointerType) lOp.getType()).getPointedType(), lOp);
                }
                if (rOp.getType().isPointerType()) {
                    rOp = builder.buildLoad(((PointerType) rOp.getType()).getPointedType(), rOp);
                }
                if (lOp.getType().isBoolType()) {
                    lOp = builder.buildZExt(lOp);
                }
                if (rOp.getType().isBoolType()) {
                    rOp = builder.buildZExt(rOp);
                }
                if (lOp.getType().isIntegerType() && rOp.getType().isFloatType()) {
                    lOp = builder.buildSitofp(lOp);
                }
                else if (lOp.getType().isFloatType() && rOp.getType().isIntegerType() ) {
                    rOp = builder.buildSitofp(rOp);
                }
                switch (ctx.getChild(2 * i - 1).getText()) {
                    case "/" -> lOp = builder.buildDiv(lOp, rOp);
                    case "*" -> lOp = builder.buildMul(lOp, rOp);
                    case "%" -> {
                        BinaryInst div = builder.buildDiv(lOp, rOp); // l/r
                        BinaryInst mul = builder.buildMul(div, rOp); // (l/r)*r
                        lOp = builder.buildSub(lOp, mul);
                    }
                }
            }
            retVal_ = lOp;
        }

        return null;
    }

    @Override
    public Void visitPrimaryExp2(SysY2022Parser.PrimaryExp2Context ctx) {
        if (this.inConstFolding()) {
            visit(ctx.lVal());
            if (retVal_.getType().isIntegerType()) {
                retInt_ = ((Constant.ConstantInt) retVal_).getVal();
                setConveyedType(DataType.INT);
            }
            else if (retVal_.getType().isFloatType()) {
                retFloat_ = ((Constant.ConstantFloat) retVal_).getVal();
                setConveyedType(DataType.FLT);
            }
            else {
//                throw new RuntimeException("Unsupported folding type: " + retVal_.getType());
            }
        }
        else{
            visit(ctx.lVal());
            if (!inBuildFCall() && retVal_.getType().isPointerType()) {
                Type pointedType = ((PointerType) retVal_.getType()).getPointedType();
                retVal_ = builder.buildLoad(pointedType, retVal_);
            }

        }
        return null;
    }

    @Override
    public Void visitNumber(SysY2022Parser.NumberContext ctx) {
        if (ctx.IntConst() != null) {
            int ret = 0;
            String num = ctx.IntConst().getText();
            if(num.length()>=2){
                char num0 = num.charAt(0);
                char num1 = num.charAt(1);
                if(num0 == '0' && num1 == 'x') ret = new BigInteger(ctx.IntConst().getText().substring(2), 16).intValue();
                else if(num0 == '0') ret = new BigInteger(ctx.IntConst().getText(), 8).intValue();
                else ret = new BigInteger(ctx.IntConst().getText(), 10).intValue();
            }
            else{
                ret = new BigInteger(ctx.IntConst().getText(), 10).intValue();
            }
            setConveyedType(DataType.INT);
            retInt_ = ret;
            if (!this.inConstFolding()) {
                retVal_ = builder.buildConstant(retInt_);
            }
        } else {
            float ret = Float.parseFloat(ctx.getChild(0).getText());
            setConveyedType(DataType.FLT);
            retFloat_ = ret;
            if (!this.inConstFolding()) {
                retVal_ = builder.buildConstant(retFloat_);
            }
        }
        return null;
    }

    private int getProductOf(List<Integer> list) {
        int prod = 1;
        for (Integer i : list) {

            prod *= i;
        }
        return prod;
    }

    @Override
    public Void visitArrConstDef(SysY2022Parser.ArrConstDefContext ctx) {

        ArrayList<Integer> dimLens = new ArrayList<>();
        for (SysY2022Parser.ConstExpContext constExpContext : ctx.constExp()) {
            visit(constExpContext);
            int dimLen = ((Constant.ConstantInt) retVal_).getVal();
            dimLens.add(dimLen);
        }
        Type tmpType = null;

        String bType = ctx.getParent().getChild(1).getText();
        switch (bType) {
            case "int" -> tmpType = IntegerType.getType();
            case "float" -> tmpType = FloatType.getType();
        }
        for (int i = dimLens.size(); i > 0; i--) {
            tmpType = ArrayType.getType(tmpType, dimLens.get(i - 1));
        }
        ArrayType arrType = (ArrayType) tmpType;



        String gvName = scope.isGlobal() ? ctx.Ident().getText() : curFuncName+"_"+ ctx.Ident().getText();

        GlobalVariable arr;


        if (ctx.constInitVal() != null) {

            ctx.constInitVal().dimLens = dimLens;
            ctx.constInitVal().sizCurDepth = getProductOf(dimLens);

            this.setConstFolding(ON);
            visit(ctx.constInitVal());
            this.setConstFolding(OFF);


            ArrayList<Constant> initList = new ArrayList<>();
            for (Value val : retValList_) {
                initList.add((Constant.ConstantInt) val);
            }
            Constant.ConstantArray initArr = builder.buildConstArr(arrType, initList);

            arr = builder.buildGlobalVar(gvName, initArr);
        }

        else {
            arr = builder.buildGlobalVar(gvName, arrType);
        }


        arr.setConst();

        scope.addSymbol(ctx.Ident().getText(), arr,"const");

        return null;
    }

    @Override
    public Void visitArrConstInitVal(SysY2022Parser.ArrConstInitValContext ctx) {

        int dimLen = ctx.dimLens.get(0);

        ArrayList<Value> initArr = new ArrayList<>();
        for (SysY2022Parser.ConstInitValContext constInitValContext : ctx.constInitVal()) {

            if (constInitValContext instanceof SysY2022Parser.ArrConstInitValContext) {
                constInitValContext.dimLens = new ArrayList<>(
                        ctx.dimLens.subList(1, ctx.dimLens.size()));
                constInitValContext.sizCurDepth = ctx.sizCurDepth / dimLen;
                visit(constInitValContext);
                initArr.addAll(retValList_);
            }

            else {
                visit(constInitValContext);
                initArr.add(retVal_);
            }
        }


        if (initArr.size() > ctx.sizCurDepth) {
            throw new RuntimeException("The length of initList (" + initArr.size() + ")" +
                    " exceeds the maximum size of current depth (" + ctx.sizCurDepth + ")");
        }


        int sizToFillTo = (ctx.getParent() instanceof SysY2022Parser.ArrConstDefContext) ?
                ctx.sizCurDepth : ctx.dimLens.get(ctx.dimLens.size() - 1);
        for (int i = initArr.size(); i < sizToFillTo; i++) {
            initArr.add(builder.buildConstant(0));
        }
        retValList_ = initArr;

        return null;
    }

    private int getZeroTailLen(List<Value> list) {
        int len = 0;
        for (int i = list.size() - 1; i > 0; i--) {
            var elem = list.get(i);
            if (!(elem instanceof Constant)) {
                break;
            }
            var constElem = (Constant) elem;
            if (constElem.getType().isFloatType() && !constElem.isZero()
                    || constElem.getType().isIntegerType() && !constElem.isZero() ) {
                break;
            }
            len++;
        }
        return len;
    }

    @Override
    public Void visitArrVarDef(SysY2022Parser.ArrVarDefContext ctx) {


        ArrayList<Integer> dimLens = new ArrayList<>();
        for (SysY2022Parser.ConstExpContext constExpContext : ctx.constExp()) {
            this.setConstFolding(ON);
            visit(constExpContext);
            this.setConstFolding(OFF);

            int dimLen = retInt_;

            dimLens.add(dimLen);
        }

        Type tmpType = null;

        String bType = ctx.getParent().getChild(0).getText();
        switch (bType) {
            case "int" -> tmpType = IntegerType.getType() ;
            case "float" -> tmpType = FloatType.getType();
        }

        for (int i = dimLens.size(); i > 0; i--) {
            tmpType = ArrayType.getType(tmpType, dimLens.get(i - 1));
        }
        ArrayType arrType = (ArrayType) tmpType;


        if (scope.isGlobal()) {

            if (ctx.initVal() != null&& ctx.initVal().getChildCount() != 2 ) {

                ctx.initVal().dimLens = dimLens;
                ctx.initVal().sizCurDepth = getProductOf(dimLens);

                this.setConstFolding(ON);
                visit(ctx.initVal());
                this.setConstFolding(OFF);

                ArrayList<Constant> initList = new ArrayList<>();
                for (Value val : retValList_) {
                    initList.add((Constant)val);
                }


                Constant.ConstantArray initArr = builder.buildConstArr(arrType, initList);

                GlobalVariable arr = builder.buildGlobalVar(ctx.Ident().getText(), initArr);
                scope.addSymbol(ctx.Ident().getText(), arr, "constarray") ;
            }

            else {
                GlobalVariable arr = builder.buildGlobalVar(ctx.Ident().getText(), arrType);
                scope.addSymbol(ctx.Ident().getText(), arr,"constarray") ;
            }
        }

        else {
            var alloca = builder.buildAlloca(arrType);
            scope.addSymbol(ctx.Ident().getText(), alloca) ;


            if (ctx.initVal() != null) {

                ctx.initVal().dimLens = dimLens;
                ctx.initVal().sizCurDepth = getProductOf(dimLens);

                visit(ctx.initVal());
                int zeroTail = getZeroTailLen(retValList_);


                ArrayList<Value> zeroIndices = new ArrayList<>() {{
                    add(builder.buildConstant(0));
                    add(builder.buildConstant(0));
                }};
                MemoryInst.GEP ptr1d = builder.buildGEP(alloca, zeroIndices);
                for (int i = 1; i < dimLens.size(); i++) {
                    ptr1d = builder.buildGEP(ptr1d, zeroIndices);
                }


                MemoryInst.GEP startPoint = ptr1d;
                Value str;
                if (((PointerType)startPoint.getType()).getPointedType().isIntegerType()) {
                    str = startPoint;
                }
                else {
                    str = builder.buildPtrcast(startPoint,PointerType.getType(IntegerType.getType()));
                }
                Constant.ConstantInt c = builder.buildConstant(0);

                Constant.ConstantInt n = builder.buildConstant(4 * arrType.getAtomLen());

                builder.buildCall((Function) scope.getVal("memset") , new ArrayList<>(){{
                    add(str);
                    add(c);
                    add(n);
                }});



                MemoryInst.GEP gep = ptr1d;
                for (int i = 0; i < retValList_.size() - zeroTail; i++) {
                    Value initVal = retValList_.get(i);



                    if (initVal instanceof Constant.ConstantInt && ((Constant.ConstantInt)initVal).getVal() == 0
                            || initVal instanceof Constant.ConstantFloat && ((Constant.ConstantFloat)initVal).getVal() == 0) {
                        continue;
                    }


                    if (i > 0) {
                        int offset1d = i;
                        gep = builder.buildGEP(ptr1d, new ArrayList<>() {{
                            add(builder.buildConstant(offset1d));
                        }});
                    }


                    if (initVal.getType().isIntegerType() && arrType.getElemType().isFloatType()) {
                        initVal = builder.buildSitofp(initVal);
                    }
                    else if (initVal.getType().isFloatType() && arrType.getElemType().isIntegerType()) {
                        initVal = builder.buildFptosi(initVal);
                    }


                    builder.buildStore(initVal, gep);
                }


            }
        }
        return null;
    }


    @Override
    public Void visitArrInitval(SysY2022Parser.ArrInitvalContext ctx) {


        int curDimLen = ctx.dimLens.get(0);


        ArrayList<Value> initArr = new ArrayList<>();
        for (SysY2022Parser.InitValContext initValContext : ctx.initVal()) {

            if (initValContext instanceof SysY2022Parser.ArrInitvalContext) {
                initValContext.dimLens = new ArrayList<>(
                        ctx.dimLens.subList(1, ctx.dimLens.size()));
                initValContext.sizCurDepth = ctx.sizCurDepth / curDimLen;

                visit(initValContext);


                initArr.addAll(retValList_);
            }

            else {
                visit(initValContext);
                initArr.add(retVal_);
            }
        }


        if (initArr.size() > ctx.sizCurDepth) {
            throw new RuntimeException("The length of initList (" + initArr.size() + ")" +
                    " exceeds the maximum size of current depth (" + ctx.sizCurDepth + ")");
        }



        int sizToFillTo = (ctx.getParent() instanceof SysY2022Parser.ArrVarDefContext) ?
                ctx.sizCurDepth : ctx.dimLens.get(ctx.dimLens.size() - 1);
        for (int i = initArr.size(); i < sizToFillTo; i++) {
            initArr.add(builder.buildConstant(0));
        }
        retValList_ = initArr;

        return null;
    }

    @Override
    public Void visitArrLVal(SysY2022Parser.ArrLValContext ctx) {

        String name  = ctx.Ident().getText();
        isConstantVar = scope.checkVarType(name);
        Value val = scope.getVal(name) ;


        if (val == null) {
            throw new RuntimeException("Undefined value: " + name);
        }


        if (this.inConstFolding()) {

            if (!(val instanceof GlobalVariable)
                    || !((GlobalVariable) val).isConstant()
                    || !((GlobalVariable) val).isArray()) {
                throw new RuntimeException("Try to fold a non-constant value.");
            }

            Constant.ConstantArray arr = ((Constant.ConstantArray) ((GlobalVariable) val).getInitVal());
            ArrayList<Integer> indices = new ArrayList<>();
            for (var exprContext : ctx.exp()) {
                visit(exprContext);
                indices.add(retInt_);
            }
            retVal_ = arr.getElemByIndex(indices);
        }
        else {

            Type valType = ((PointerType) val.getType()).getPointedType();
            // An array.
            if (valType.isArrayType()) {
                for (SysY2022Parser.ExpContext exprContext : ctx.exp()) {
                    visit(exprContext);
                    val = builder.buildGEP(val, new ArrayList<>() {{
                        add(builder.buildConstant(0));
                        add(retVal_);
                    }});
                }
            }

            else {
                MemoryInst.Load load = builder.buildLoad(
                        ((PointerType) val.getType()).getPointedType(),
                        val
                );
                visit(ctx.exp(0));
                val = builder.buildGEP(load, new ArrayList<>() {{
                    add(retVal_);
                }});

                for (int i = 1; i < ctx.exp().size(); i++) {
                    visit(ctx.exp(i));
                    val = builder.buildGEP(val, new ArrayList<>() {{
                        add(builder.buildConstant(0));
                        add(retVal_);
                    }});
                }
            }

            retVal_ = val;
        }

        return null;
    }

    @Override
    public Void visitArrFuncFParam(SysY2022Parser.ArrFuncFParamContext ctx) {
        ArrayList<Integer> dimLens = new ArrayList<>();


        setConstFolding(ON);
        for (SysY2022Parser.ExpContext exprContext : ctx.exp()) {
            visit(exprContext);
            dimLens.add(retInt_);
        }
        setConstFolding(OFF);


        Type arrType;
        String bType = ctx.bType().getText();
        switch (bType) {
            case "int" -> arrType = IntegerType.getType();
            case "float" -> arrType = FloatType.getType();
            default -> throw new RuntimeException("Supported function argument type.");
        }
        for (int i = dimLens.size(); i > 0; i--) {
            arrType = ArrayType.getType(arrType, dimLens.get(i - 1));
        }

        retType_ = PointerType.getType(arrType);
        return null;
    }
}
