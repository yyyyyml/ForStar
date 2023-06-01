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

    public Visitor(Module module) {
        this.builder = new Builder(module);
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
                switch (ctx.unaryOp().getText()) {
                    case "-" -> retVal_ = builder.buildSub(builder.buildConstant(0), retVal_);
                    //!还没写
                    case "+" -> {}
                }
            }
            // Float.
            else {
                switch (ctx.unaryOp().getText()) {
                    case "-" -> retVal_ = builder.buildFneg(Instruction.TAG.FNEG, retVal_);
                    //!还没写
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
            //I1的转换暂时没写
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
                        lop = builder.buildSub(tmp, rop);
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
