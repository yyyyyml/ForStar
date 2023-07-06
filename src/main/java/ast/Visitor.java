package ast;

import frontend.SysY2022BaseVisitor;
import frontend.SysY2022Parser;
import frontend.SysY2022Parser.ReturnStmtContext;
import ir.Instruction;
import ir.Instructions.MemoryInst;
import ir.Module;
import ir.Type;
import ir.Type.FloatType;
import ir.Type.IntegerType;
import ir.Value;
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
                builder.buildFunction("starttime", new FunctionType(voidType, emptyArgTypeList), true)
        );
        // void stoptime()
        scope.addSymbol("stoptime",
                builder.buildFunction("stoptime", new FunctionType(voidType, emptyArgTypeList), true)
        );
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
                    while (realArg.getType() != typeRequired) {
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

        // starttime和stoptime函数？
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
            visit(ctx.unaryExp());
            if (retVal_.getType().isIntegerType()) {
                //I1的转换还没写
                if (retVal_.getType().isBoolType()) {
                    retVal_ = builder.buildZExt(retVal_);
                }
                if (retVal_.getType().isBoolType()) {
                    retVal_ = builder.buildZExt(retVal_);
                }
                switch (ctx.unaryOp().getText()) {
                    case "-" -> retVal_ = builder.buildSub(builder.buildConstant(0), retVal_);
                    case "!" -> retVal_ = builder.buildComparison("==", builder.buildConstant(0), retVal_);
                    case "+" -> {}
                }
            }
            // Float.
            else {
                switch (ctx.unaryOp().getText()) {
                    case "-" -> retVal_ = builder.buildFneg(Instruction.TAG.FNEG, retVal_);
                    case "!" -> retVal_ = builder.buildComparison("==", builder.buildConstant(0), retVal_);
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
        /*
        Store current block to add on it a Br to entryBlk.
        And then build the entry block of the condition statement.
         */
        BasicBlock preBlk = builder.getCurBB();
        BasicBlock entryBlk = builder.buildBB("_COND_ENTRY");
        // Add a Br from the old preBlk to the new entryBlk.
        builder.setCurBB(preBlk);
        builder.buildBr(entryBlk);

        /*
        Build an EXIT block no matter if it may become dead code
        that cannot be reached in the CFG.
         */
        BasicBlock exitBlk = builder.buildBB("_COND_EXIT");
        /*
        Build the TRUE branch (a block for jumping if condition is true).
        Fill it by visiting child (the 1st stmt, the true branch).
         */
        BasicBlock trueEntryBlk = builder.buildBB("_THEN");
        visit(ctx.stmt(0));
        BasicBlock trueExitBlk = builder.getCurBB();
        // Get trueBlkEndWithTerminator flag.
        Instruction trueExitBlkLastInst = trueExitBlk.getLastInst();
        boolean trueBlkEndWithTerminator = trueExitBlkLastInst != null &&
                trueExitBlkLastInst.isTerminator();
        /*
        Build the FALSE branch (a block for jumping if condition is false),
        if there is the 2nd stmt, meaning that it's an IF-ELSE statement.
        Otherwise, it's an IF statement (w/o following ELSE), and
        falseEntryBlk will remain null.

        : if(falseEntryBlk != null) -> IF-ELSE statement
        : if(falseEntryBlk == null) -> IF statement w/o ELSE
         */
        BasicBlock falseEntryBlk = null;
        BasicBlock falseExitBlk = null;
        boolean falseBlkEndWithTerminator = false;
        falseEntryBlk = builder.buildBB("_ELSE");
        visit(ctx.stmt(1)); // Fill the block by visiting child.
        falseExitBlk = builder.getCurBB();
        // Get falseBlkEndWithTerminator flag.
        Instruction falseExitBlkLastInst = falseExitBlk.getLastInst();
        falseBlkEndWithTerminator = falseExitBlkLastInst != null && falseExitBlkLastInst.isTerminator();

        /*
        Add Br terminator for trueExitBlock and falseExitBlock if needed (if both branches
        end with Ret terminators.
         */
        // The exit block will be built when:
        // "!trueBlkEndWithTerminator && !falseBlkEndWithTerminator" (under IF-ELSE)
        // or "!trueBlkEndWithTerminator && no falseBlock" (i.e. IF w/o ELSE)
        if (!trueBlkEndWithTerminator) {
            builder.setCurBB(trueExitBlk);
            builder.buildBr(exitBlk);
        }
        if (falseEntryBlk != null && !falseBlkEndWithTerminator) {
            builder.setCurBB(falseExitBlk);
            builder.buildBr(exitBlk);
        }

        /*
        Cope with the condition expression by visiting child cond.
         */
        builder.setCurBB(entryBlk);
        // Pass down blocks as inherited attributes for short-circuit evaluation.
        ctx.cond().lOrExp().trueBlk = trueEntryBlk;
        ctx.cond().lOrExp().falseBlk = (falseEntryBlk != null) ? falseEntryBlk : exitBlk;

        visit(ctx.cond());

        /*
        Force the BB pointer to point to the exitBlk, which will serve as the upstream
        block for processing the following content.
        Even if the exitBlk is a dead entry that cannot be reached, all the content will
        still be processed. These dead basic blocks can be removed in the following
        CFG analysis by the optimizer.
         */
        builder.setCurBB(exitBlk);

        return null;
    }

    @Override
    public Void visitIfStmt(SysY2022Parser.IfStmtContext ctx) {
        /*
        Store current block to add on it a Br to entryBlk.
        And then build the entry block of the condition statement.
         */
        BasicBlock preBlk = builder.getCurBB();
        BasicBlock entryBlk = builder.buildBB("_COND_ENTRY");
        // Add a Br from the old preBlk to the new entryBlk.
        builder.setCurBB(preBlk);
        builder.buildBr(entryBlk);

        /*
        Build an EXIT block no matter if it may become dead code
        that cannot be reached in the CFG.
         */
        BasicBlock exitBlk = builder.buildBB("_COND_EXIT");
        /*
        Build the TRUE branch (a block for jumping if condition is true).
        Fill it by visiting child (the 1st stmt, the true branch).
         */
        BasicBlock trueEntryBlk = builder.buildBB("_THEN");
        visit(ctx.stmt());
        BasicBlock trueExitBlk = builder.getCurBB();
        // Get trueBlkEndWithTerminator flag.
        Instruction trueExitBlkLastInst = trueExitBlk.getLastInst();
        boolean trueBlkEndWithTerminator = trueExitBlkLastInst != null &&
                trueExitBlkLastInst.isTerminator();
        /*
        Build the FALSE branch (a block for jumping if condition is false),
        if there is the 2nd stmt, meaning that it's an IF-ELSE statement.
        Otherwise, it's an IF statement (w/o following ELSE), and
        falseEntryBlk will remain null.

        : if(falseEntryBlk != null) -> IF-ELSE statement
        : if(falseEntryBlk == null) -> IF statement w/o ELSE
         */
        BasicBlock falseEntryBlk = null;
        BasicBlock falseExitBlk = null;
        boolean falseBlkEndWithTerminator = false;
        /*
        Add Br terminator for trueExitBlock and falseExitBlock if needed (if both branches
        end with Ret terminators.
         */
        // The exit block will be built when:
        // "!trueBlkEndWithTerminator && !falseBlkEndWithTerminator" (under IF-ELSE)
        // or "!trueBlkEndWithTerminator && no falseBlock" (i.e. IF w/o ELSE)
        if (!trueBlkEndWithTerminator) {
            builder.setCurBB(trueExitBlk);
            builder.buildBr(exitBlk);
        }
        if (falseEntryBlk != null && !falseBlkEndWithTerminator) {
            builder.setCurBB(falseExitBlk);
            builder.buildBr(exitBlk);
        }

        /*
        Cope with the condition expression by visiting child cond.
         */
        builder.setCurBB(entryBlk);
        // Pass down blocks as inherited attributes for short-circuit evaluation.
        ctx.cond().lOrExp().trueBlk = trueEntryBlk;
        ctx.cond().lOrExp().falseBlk = (falseEntryBlk != null) ? falseEntryBlk : exitBlk;

        visit(ctx.cond());

        /*
        Force the BB pointer to point to the exitBlk, which will serve as the upstream
        block for processing the following content.
        Even if the exitBlk is a dead entry that cannot be reached, all the content will
        still be processed. These dead basic blocks can be removed in the following
        CFG analysis by the optimizer.
         */
        builder.setCurBB(exitBlk);

        return null;
    }


    @Override
    public Void visitLOr2(SysY2022Parser.LOr2Context ctx) {
        //<editor-fold desc="For first N-1 lAndExp blocks.">

        BasicBlock curLOrBlk = builder.getCurBB();
        BasicBlock nxtLOrBlk = builder.buildBB("lornext");

            // Pass down blocks as inherited attributes for short-circuit evaluation.
        ctx.lOrExp().falseBlk = nxtLOrBlk;
        ctx.lOrExp().trueBlk = ctx.trueBlk;

        builder.setCurBB(curLOrBlk);
        visit(ctx.lOrExp());
        builder.setCurBB(nxtLOrBlk);

        //</editor-fold>


        //<editor-fold desc="For the last lAndExp block.">
        ctx.lAndExp().falseBlk = ctx.falseBlk;
        ctx.lAndExp().trueBlk = ctx.trueBlk;
        visit(ctx.lAndExp());
        //</editor-fold>

        return null;
    }

    @Override
    public Void visitLOr1(SysY2022Parser.LOr1Context ctx) {

        ctx.lAndExp().falseBlk = ctx.falseBlk;
        ctx.lAndExp().trueBlk = ctx.trueBlk;
        visit(ctx.lAndExp());

        return null;
    }



    @Override
    public Void visitLAnd2(SysY2022Parser.LAnd2Context ctx) {
        visit(ctx.lAndExp());
        if(retVal_.getType().isIntegerType()) { // i32 -> i1
                // If eqExp gives a number (i32), cast it to be a boolean by NE comparison.
            retVal_ = builder.buildComparison("!=", retVal_, Constant.ConstantInt.getConstantInt(0));
        }
        else if (retVal_.getType().isFloatType()) { // float -> i1
            retVal_ = builder.buildComparison("!=", retVal_, Constant.ConstantFloat.getConstantFloat(.0f));
        }
        /*
           Build the branching.
        */
        // For the first N-1 eqExp blocks.

        // Build following blocks for short-circuit evaluation.
        BasicBlock originBlk = builder.getCurBB();
        BasicBlock nxtAndBlk = builder.buildBB("land2nxt");
                // Add a branch instruction to terminate this block.
        builder.setCurBB(originBlk);
        builder.buildBr(retVal_, nxtAndBlk, ctx.falseBlk);
        builder.setCurBB(nxtAndBlk);

        visit(ctx.eqExp());
        if(retVal_.getType().isIntegerType() ) { // i32 -> i1
            // If eqExp gives a number (i32), cast it to be a boolean by NE comparison.
            retVal_ = builder.buildComparison("!=", retVal_, Constant.ConstantInt.getConstantInt(0));
        }
        else if (retVal_.getType().isFloatType()) { // float -> i1
            retVal_ = builder.buildComparison("!=", retVal_, Constant.ConstantFloat.getConstantFloat(.0f));
        }
        // For the last eqExp blocks.
        builder.buildBr(retVal_, ctx.trueBlk, ctx.falseBlk);

        return null;
    }

    @Override
    public Void visitLAnd1(SysY2022Parser.LAnd1Context ctx) {
        visit(ctx.eqExp());
        if(retVal_.getType().isIntegerType() ) { // i32 -> i1
            // If eqExp gives a number (i32), cast it to be a boolean by NE comparison.
            retVal_ = builder.buildComparison("!=", retVal_, Constant.ConstantInt.getConstantInt(0));
        }
        else if (retVal_.getType().isFloatType()) { // float -> i1
            retVal_ = builder.buildComparison("!=", retVal_, Constant.ConstantFloat.getConstantFloat(.0f));
        }
        // For the last eqExp blocks.
        if(ctx.getParent().getChild(1) != null)
        {
            boolean islor = ctx.getParent().getChild(1).getText().equals( "||" );
            if( islor )
            {
                builder.buildBr(retVal_, ctx.trueBlk, ctx.falseBlk);
            }
        }

        return null;
    }

    @Override
    public Void visitEq2(SysY2022Parser.Eq2Context ctx) {
        // Retrieve left operand by visiting child.
        visit(ctx.eqExp());
        Value lOp = retVal_;


            // Retrieve the next relExp as the right operand by visiting child.
        visit(ctx.relExp());
        Value rOp = retVal_;

            /*
            Implicit type conversions.
             */
        if (lOp.getType().isFloatType() && !rOp.getType().isFloatType()) {
            rOp = builder.buildSitofp(rOp);
        }
        else if (!lOp.getType().isFloatType() && rOp.getType().isFloatType()) {
            lOp = builder.buildSitofp(lOp);
        }
        else {
                // Extend if one Opd is i32 and another is i1.
            if(lOp.getType().isIntegerType() && rOp.getType().isBoolType() ) {
                rOp = builder.buildZExt(rOp);
            }
            if(rOp.getType().isIntegerType() && lOp.getType().isBoolType()) {
                lOp = builder.buildZExt(lOp);
            }
        }

            /*
            Build a comparison instruction, which yields a result
            to be the left operand for the next round.
             */
        String opr = ctx.getChild(1).getText(); // The comparison operator.
        lOp = builder.buildComparison(opr, lOp, rOp);

        // The final result is stored in the last left operand.
        retVal_ = lOp;

        return null;
    }

    @Override
    public Void visitEq1(SysY2022Parser.Eq1Context ctx) {
        visit(ctx.relExp());
        return null;
    }

    @Override
    public Void visitRel2(SysY2022Parser.Rel2Context ctx) {
        // Retrieve left operand by visiting child.
        visit(ctx.relExp());
        Value lOp = retVal_;


        // Retrieve the next addExp as the right operand by visiting child.
        visit(ctx.addExp());
        Value rOp = retVal_;

        /*
           Implicit type conversions.
        */
        if (lOp.getType().isFloatType() && !rOp.getType().isFloatType()) {
            rOp = builder.buildSitofp(rOp);
        }
        else if (!lOp.getType().isFloatType() && rOp.getType().isFloatType()) {
            lOp = builder.buildSitofp(lOp);
        }
        else {
            // Same as visitEqExp above: Extend if one Opd is i32 and another is i1.
            if (lOp.getType().isIntegerType() && rOp.getType().isBoolType()) {
                rOp = builder.buildZExt(rOp);
            }
            if (rOp.getType().isIntegerType() && lOp.getType().isBoolType()) {
                lOp = builder.buildZExt(lOp);
            }
        }

        /*
           Build a comparison instruction, which yields a result
           to be the left operand for the next round.
        */
        String opr = ctx.getChild(1).getText(); // The comparison operator.
        lOp = builder.buildComparison(opr, lOp, rOp);
        // The final result is stored in the last left operand.
        retVal_ = lOp;

        return null;
    }

    @Override
    public Void visitWhileStmt(SysY2022Parser.WhileStmtContext ctx) {
        // Deepen by one layer of nested loop.
        bpStk.push(new ArrayList<>());

        /*
        - Store current block to add on it a Br to entryBlk.
        - Start a new block as the entry of loop continuing check for
        jumping back at the end of the loop body, which is also the
        entry block of the while statement.
         */
        BasicBlock preBlk = builder.getCurBB();

        // NOTICE: A new block as condEntryBlk will be created no matter
        // whether the preBlk is empty or not.
        BasicBlock condEntryBlk = builder.buildBB("_WHILE_ENTRY");
        // Add a Br from the old preBlk to the new entryBlk.
        builder.setCurBB(preBlk);
        builder.buildBr(condEntryBlk);

        /*
        Build an EXIT block no matter if it may become dead code
        that cannot be reached in the CFG.
         */
        BasicBlock bodyEntryBlk = builder.buildBB("_WHILE_BODY");
        BasicBlock exitBlk = builder.buildBB("_WHILE_EXIT");

        /*
        Cope with the condition expression by visiting child cond.
         */
        // Pass down blocks as inherited attributes for short-circuit evaluation.
        ctx.cond().lOrExp().trueBlk = bodyEntryBlk;
        ctx.cond().lOrExp().falseBlk = exitBlk;

        builder.setCurBB(condEntryBlk);
        visit(ctx.cond());

        /*
        Build the loop BODY.
         */
        builder.setCurBB(bodyEntryBlk);
        visit(ctx.stmt());
        BasicBlock bodyExitBlk = builder.getCurBB();
        // If the loop body doesn't end with Ret,
        // add a Br jumping back to the conditional statement.
        if (bodyExitBlk.list.isEmpty() || !bodyExitBlk.getLastInst().isTerminator()) {
            builder.setCurBB(bodyExitBlk);
            builder.buildBr(condEntryBlk);
        }

        /*
        Force the BB pointer to point to the exitBlk just as the conditional
        statement regardless of dead code prevention.
         */
        builder.setCurBB(exitBlk);

        // Pop the back-patching layer out.
        for (ir.Instructions.TerminatorInst.Br br : bpStk.pop()) {
            if (br.getOperandAt(0) == BREAK) {
                br.setOperand(exitBlk,0);
            }
            else if (br.getOperandAt(0) == CONTINUE) {
                br.setOperand(condEntryBlk,0);
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
        // Add a dead block for possible remaining dead code.
        builder.buildBB("_FOLLOWING_BLK");
        return null;
    }

    /**
     * stmt : 'continue' ';' # contStmt
     */
    @Override
    public Void visitContinueStmt(SysY2022Parser.ContinueStmtContext ctx) {
        bpStk.peek().add(builder.buildBr(CONTINUE));
        // Add a dead block for possible remaining dead code.
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
            //数组的情况先不写
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
            throw new RuntimeException("Constant variable "+ctx.getChild(0).getText() +"'s"+" value can not be changed!");
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
    public Void visitAdd2(SysY2022Parser.Add2Context ctx) {
        if (this.inConstFolding()) {
            visit(ctx.addExp());
            switch(getConveyedType()){
                case INT->{
                    int lop = retInt_;
                    visit(ctx.mulExp());
                    switch(getConveyedType()){
                        case INT->{
                            int rop = retInt_;
                            if(ctx.getChild(1).getText() =="+") {
                                retInt_ = lop + rop;
                                setConveyedType(DataType.INT);
                            }
                            else if(ctx.getChild(1).getText() =="-") {
                                retInt_ = lop - rop;
                                setConveyedType(DataType.INT);
                            }
                        }
                        case FLT->{
                            float rop = retFloat_;
                            if(ctx.getChild(1).getText() =="+") {
                                retFloat_ = (float)lop + rop;
                                setConveyedType(DataType.FLT);
                            }
                            else if(ctx.getChild(1).getText() =="-") {
                                retFloat_ = (float)lop - rop;
                                setConveyedType(DataType.FLT);
                            }
                        }
                    }
                }
                case FLT->{
                    float lop = retFloat_;
                    visit(ctx.mulExp());
                    switch(getConveyedType()){
                        case INT->{
                            int rop = retInt_;
                            if(ctx.getChild(1).getText() =="+") {
                                retFloat_ = lop + (float)rop;
                                setConveyedType(DataType.FLT);
                            }
                            else if(ctx.getChild(1).getText() =="-") {
                                retFloat_ = lop - (float)rop;
                                setConveyedType(DataType.FLT);
                            }
                        }
                        case FLT->{
                            float rop = retFloat_;
                            if(ctx.getChild(1).getText() =="+") {
                                retFloat_ = lop + rop;
                                setConveyedType(DataType.FLT);
                            }
                            else if(ctx.getChild(1).getText() =="-") {
                                retFloat_ = lop - rop;
                                setConveyedType(DataType.FLT);
                            }
                        }
                    }
                }
            }
        }

        else {
            visit(ctx.addExp());
            Value lOp = retVal_;
            visit(ctx.mulExp());
            Value rOp = retVal_;
            //如果是指针类型先load出来；
            if (lOp.getType().isPointerType()) {
                lOp = builder.buildLoad(((PointerType) lOp.getType()).getPointedType(), lOp);
            }
            if (rOp.getType().isPointerType()) {
                rOp = builder.buildLoad(((PointerType) rOp.getType()).getPointedType(), rOp);
            }
            //类型转换

            if (lOp.getType().isBoolType()) {
                lOp = builder.buildZExt(lOp);
            }
            if (rOp.getType().isBoolType()) {
                rOp = builder.buildZExt(rOp);
            }
            if (lOp.getType().isIntegerType() && rOp.getType().isFloatType()) {
                lOp = builder.buildSitofp(lOp);
            } else if (lOp.getType().isFloatType() && rOp.getType().isIntegerType()) {
                rOp = builder.buildSitofp(rOp);
            }
            switch (ctx.getChild(1).getText()) {
                case "+" -> lOp = builder.buildAdd(lOp, rOp);
                case "-" -> lOp = builder.buildSub(lOp, rOp);
                default -> {
                }
            }
            retVal_ = lOp;
        }
        return null;
    }




    @Override
    public Void visitMul2(SysY2022Parser.Mul2Context ctx) {
        if (this.inConstFolding()) {
            visit(ctx.mulExp());
            switch(getConveyedType()){
                case INT->{
                    int lop = retInt_;
                    visit(ctx.unaryExp());
                    switch(getConveyedType()){
                        case INT->{
                            int rop = retInt_;
                            if(ctx.getChild(1).getText() =="*") {
                                retInt_ = lop * rop;
                                setConveyedType(DataType.INT);
                            }
                            else if(ctx.getChild(1).getText() =="/") {
                                retInt_ = lop / rop;
                                setConveyedType(DataType.INT);
                            }
                            else if(ctx.getChild(1).getText() =="%") {
                                retInt_ = lop % rop;
                                setConveyedType(DataType.INT);
                            }
                        }
                        case FLT->{
                            float rop = retFloat_;
                            if(ctx.getChild(1).getText() =="*") {
                                retFloat_ = (float)lop * rop;
                                setConveyedType(DataType.FLT);
                            }
                            else if(ctx.getChild(1).getText() =="/") {
                                retFloat_ = (float)lop / rop;
                                setConveyedType(DataType.FLT);
                            }
                            else if(ctx.getChild(1).getText() =="%"){
                                throw new RuntimeException("Float number can not use operator %!!");
                            }
                        }
                    }
                }
                case FLT->{
                    float lop = retFloat_;
                    visit(ctx.unaryExp());
                    switch(getConveyedType()){
                        case INT->{
                            int rop = retInt_;
                            if(ctx.getChild(1).getText() =="*") {
                                retFloat_ = lop * (float)rop;
                                setConveyedType(DataType.FLT);
                            }
                            else if(ctx.getChild(1).getText() =="/") {
                                retFloat_ = lop / (float)rop;
                                setConveyedType(DataType.FLT);
                            }
                            else if(ctx.getChild(1).getText() =="%"){
                                throw new RuntimeException("Float number can not use operator %!!");
                            }
                        }
                        case FLT->{
                            float rop = retFloat_;
                            if(ctx.getChild(1).getText() =="*") {
                                retFloat_ = lop * rop;
                                setConveyedType(DataType.FLT);
                            }
                            else if(ctx.getChild(1).getText() =="/") {
                                retFloat_ = lop / rop;
                                setConveyedType(DataType.FLT);
                            }
                            else if(ctx.getChild(1).getText() =="%"){
                                throw new RuntimeException("Float number can not use operator %!!");
                            }
                        }
                    }
                }
            }
        }

        else {
            visit(ctx.mulExp());
            Value lop = retVal_;
            visit(ctx.unaryExp());
            Value rop = retVal_;
            if (lop.getType().isPointerType()) {
                lop = builder.buildLoad(((PointerType) lop.getType()).getPointedType(), lop);
            }
            if (rop.getType().isPointerType()) {
                rop = builder.buildLoad(((PointerType) rop.getType()).getPointedType(), rop);
            }
            //I1的转换还没写。
            if (lop.getType().isBoolType()) {
                lop = builder.buildZExt(lop);
            }
            if (rop.getType().isBoolType()) {
                rop = builder.buildZExt(rop);
            }
            if (lop.getType().isIntegerType() && rop.getType().isFloatType()) {
                lop = builder.buildSitofp(lop);
            } else if (lop.getType().isFloatType() && rop.getType().isIntegerType()) {
                rop = builder.buildSitofp(rop);
            }
            switch (ctx.getChild(1).getText()) {
                case "*" -> lop = builder.buildMul(lop, rop);
                case "/" -> lop = builder.buildDiv(lop, rop);
                case "%" -> {
                    if(lop.getType().isIntegerType()&&rop.getType().isIntegerType()) {
                        Value tmp = lop;
                        lop = builder.buildDiv(lop, rop);
                        lop = builder.buildMul(lop, rop);
                        lop = builder.buildSub(tmp, lop);
                    }
                    else {
                        throw new RuntimeException("Float number can not use operator %!!");
                    }
                }
                default -> {
                }
            }
            retVal_ = lop;
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
                throw new RuntimeException("Unsupported folding type: " + retVal_.getType());
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
            ret = new BigInteger(ctx.IntConst().getText(), 10).intValue();
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

}
