package ast;

import frontend.*;

import frontend.SysY2022Parser.ReturnStmtContext;
import ir.Module;
import ir.Value;
import ir.types.*;
import ir.Type;
import ir.values.*;
import ir.Instruction;

import java.util.ArrayList;

public class Visitor extends SysY2022BaseVisitor<Void> {
    private final Scope scope = new Scope();
    private final Builder builder = new Builder();
    public Visitor (Module module) {
        Builder builder = new Builder(module);
        //this.initRuntimeFunctions();
    }

    //<editor-fold desc="Environment variables indicating the building status">
    private final boolean ON = true;
    private final boolean OFF = false;


    //判断visitor是否在在工作的一些参数
    /**
     * If the visitor is currently in a constant folding progression.
     */
    private boolean envConstFolding = OFF;

    /**
     * Set the environment variable of constant folding.
     * @param stat ON / OFF
     */
    private void setConstFolding(boolean stat) {
        envConstFolding = stat;
    }

    /**
     * If the visitor is currently in a constant folding progression.
     * @return Yes or no.
     */
    public boolean inConstFolding() {
        return envConstFolding;
    }

    /**
     * If the visitor is currently building a function call (invocation).
     */
    private boolean envBuildFCall = OFF;

    /**
     * Set the environment variable of building function call.
     * @param stat ON / OFF
     */
    private void setBuildFCall(boolean stat) {
        envBuildFCall = stat;
    }

    /**
     * If the visitor is currently building a function call (invocation).
     * @return Yes or no.
     */
    private boolean inBuildFCall() {
        return envBuildFCall;
    }

    /**
     * The enum is for indicating which data type returned from the lower layer
     * for visiting method. (INT -> read retInt_, FLT -> read retFlt_)
     */
    private enum DataType {FLT, INT}

    /**
     * Represents data type returned from the lower layer of visiting method.
     * Only for passing data in primitive types int and float (by retInt_ and retFloat_)
     */
    private DataType envConveyedType = null;

    private DataType getConveyedType() {
        return envConveyedType;
    }

    private void setConveyedType(DataType dataType) {
        envConveyedType = dataType;
    }

    //</editor-fold>

    //<editor-fold desc="Variables storing returned data from the lower layers of visiting.">
    private Value retVal_;
    private ArrayList<Value> retValList_;
    private Type retType_;
    private ArrayList<Type> retTypeList_;
    private int retInt_;
    private float retFloat_;
    //</editor-fold>

    /*重写visitor -_-|| */
    @Override
    public Void visitCompUnit(SysY2022Parser.CompUnitContext ctx) {
        super.visitCompUnit(ctx);
        return null;
    }


    @Override
    public Void visitScalarConstDef(SysY2022Parser.ScalarConstDefContext ctx) {
        // Retrieve the name of the variable defined and check for duplication.//把identifier改成了Ident（）
        String varName = ctx.Ident().getText();

        visit(ctx.constInitVal());
        Value initVal = retVal_;

        scope.addDecl(varName, initVal);

        return null;
    }


    @Override
    public Void visitScalarConstInitVal(SysY2022Parser.ScalarConstInitValContext ctx) {
        this.setConstFolding(ON);

        super.visitScalarConstInitVal(ctx);

        this.setConstFolding(OFF);

        // Convert the constant value aft folding as a Constant IR Value.
        switch (getConveyedType()) {
            case INT -> retVal_ = builder.buildConstant(retInt_);
            //case FLT -> retVal_ = builder.buildConstant(retFloat_);
        }
        return null;
    }


    /**
     * initVal : expr # scalarInitVal
     * ------------------------------------
     * expr : addExp
     */
    @Override
    public Void visitScalarInitVal(SysY2022Parser.ScalarInitValContext ctx) {
        // Turn on constant folding switch.
        if (scope.isGlobal()) {
            this.setConstFolding(ON);
        }
        super.visitScalarInitVal(ctx);
        // Turn off constant folding switch.
        if (scope.isGlobal()) {
            switch (getConveyedType()) {
                case INT -> retVal_ = builder.buildConstant(retInt_);
                //case FLT -> retVal_ = builder.buildConstant(retFloat_);
            }
            setConstFolding(OFF);
        }

        return null;
    }

    @Override
    public Void visitFuncDef(SysY2022Parser.FuncDefContext ctx) {

        /*
        Collect object info.
         */

        // Get the function name.
        String funcName = ctx.Ident().getText();

        // Get the return type. (funcType identifier)
        Type retType;
        String strRetType = ctx.funcType().getText();
        switch (strRetType) {
            case "int" -> retType = ir.Type.IntegerType.getI32();
            //先不着急写
            //case "float" -> retType = FloatType.getType();
            //case "void" -> retType = VoidType.getType();
            default -> throw new RuntimeException("Unsupported function return type.");
        }

        // Get the argument list. (Visiting child)
        ArrayList<Type> argTypes = new ArrayList<>();
        if (ctx.funcFParams() != null) {
            visit(ctx.funcFParams());
            argTypes.addAll(retTypeList_);
        }
        retTypeList_ = new ArrayList<>(); // Clear the list for next func def.

        /*
        Build IR.
         */
        // Security check (allow no nested definition of functions)
        if (!scope.isGlobal()) {
            throw new RuntimeException("Nested definition of function: " + funcName);
        }
        // Insert a function into the module and symbol table.
        //FunctionType funcType = FunctionType.getType(retType, argTypes);
        FunctionType funcType = new FunctionType(retType, argTypes);
        Function function = builder.buildFunction(funcName, funcType, false);
        scope.addDecl(funcName, function);

        // Insert a basic block. Then scope in.
        BasicBlock bb = builder.buildBB(funcName + "_ENTRY");
        scope.scopeIn();

        /*
        Allocate all the formal arguments INSIDE the scope of the function.
         */
        for (int i = 0; i < function.getParamList().size(); i++) {
            Function.Param arg = function.getParamList().get(i);
            // Allocate a local memory on the stack for the arg.
            //MemoryInst.Alloca localVar = builder.buildAlloca(arg.getType());
            // Add the memory allocated to the symbol table.
            // It's an ugly way to retrieve the name of the args
            // since no elegant way is found so far.
            String argName = null;
            if (ctx.funcFParams().funcFParam(i) instanceof SysY2022Parser.ScalarFuncFParamContext) {
                var ctxArg = (SysY2022Parser.ScalarFuncFParamContext) ctx.funcFParams().funcFParam(i);
                argName = ctxArg.Ident().getText();
            } else if (ctx.funcFParams().funcFParam(i) instanceof SysY2022Parser.ArrFuncFParamContext) {
                var ctxArg = (SysY2022Parser.ArrFuncFParamContext) ctx.funcFParams().funcFParam(i);
                argName = ctxArg.Ident().getText();
            }
            //scope.addDecl(argName, localVar);
            // Copy the value to the local memory.
            //builder.buildStore(arg, localVar);
        }

        /*
        Process function body. (Visiting child)
         */
        visit(ctx.block());

        /*
        Check the last basic block of the function to see if there is a
        return statement given in the source.
        If not, insert a terminator to the end of it.
         */
        Instruction tailInst = builder.getCurBB().getLastInst();
        // If no instruction in the bb, or the last instruction is not a terminator.
        if (tailInst == null || !tailInst.isTerminator()) {
            //function.getType().getRetType().isFloatType()这下面三个在Function类完善后可能要把type分成paramtype和returntype
            if (function.getType().isVoidType()) {
                builder.buildRet();
            }
            else if (function.getType().isIntegerType()) {
                builder.buildRet(builder.buildConstant(0)); // Return 0 by default.
            }
            /*else if (function.getType().getRetType().isFloatType()) {
                builder.buildRet(builder.buildConstant(.0f)); // Return 0.0f by default.
            }*/
        }

        /*
        Scope out.
         */
        scope.scopeOut();

        /*
        Check the function just built.
         */
        //先不管checkFunc(builder.getCurFunc());

        return null;
    }



    @Override
    public Void visitBlock(SysY2022Parser.BlockContext ctx) {
        scope.scopeIn(); // Add a new layer of scope (a new symbol table).
        ctx.blockItem().forEach(this::visit);
        scope.scopeOut(); // Pop it out before exiting the scope.
        return null;
    }

    /**
     * stmt : 'return' (expr)? ';'
     */
    @Override
    public Void visitReturnStmt(ReturnStmtContext ctx) {
        // If there is an expression component to be returned,
        // visit child to retrieve it.
        if (ctx.exp() != null) {
            visit(ctx.exp());

            // Return type matching check and conversion.
            Value retVal = retVal_;
            //Type retType = builder.getCurFunc().getType(); // The return type defined in the prototype.builder.getCurFunc().getType().getRetType()
            //if (retVal.getType().isIntegerType() && retType.isFloatType()) {
             //   retVal = builder.buildSitofp(retVal);
           // }
            //else if (retVal.getType().isFloatType() && retType.isIntegerType()) {
             //   retVal = builder.buildFptosi(retVal, (IntegerType) retType);
           // }

            builder.buildRet(retVal);
        }
        // If not, return void.
        else {
            builder.buildRet();
        }
        // Add a dead block for possible remaining dead code.
        builder.buildBB("_FOLLOWING_BLK");
        return null;
    }

    /**
     * number
     *     : intConst
     *     | floatConst
     * -------------------------------------
     * primaryExp : number # primExpr3
     */
    @Override
    public Void visitNumber(SysY2022Parser.NumberContext ctx) {
        super.visitNumber(ctx);
        if (!this.inConstFolding()) {
            switch (getConveyedType()) {
                case INT -> retVal_ = builder.buildConstant(retInt_);
                //case FLT -> retVal_ = builder.buildConstant(retFloat_);
            }
        }
        return null;
    }
}
