package ast;

import frontend.*;

import frontend.SysY2022Parser.ReturnStmtContext;
import ir.Module;
import ir.Type;
import ir.Value;
import ir.types.*;
import ir.Type.*;
import ir.values.*;
import ir.Instruction;
import ir.values.Constant;
import ir.Instructions.*;

import java.util.ArrayList;
import java.math.BigInteger;

public class Visitor extends SysY2022BaseVisitor<Void> {
    private final Scope scope = new Scope();
    private Builder builder;
    public Visitor (Module module) {
        this.builder = new Builder(module);
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
        // Retrieve the name of the variable defined and check for duplication.//把identifier改成了Ident（)
        String name = ctx.Ident().getText();
        if(scope.isDuplicateSymbol(name)){
            throw new RuntimeException("Duplicate definition of constant name: " + name);
        }
        visit(ctx.constInitVal());
        Value initVal = retVal_;
        String bType = ctx.getParent().getChild(1).getText();
        if(bType == "int"){
            builder.buildConstant(((Constant.ConstantInt)initVal).getVal());
        }
        scope.addSymbol(name, initVal);

        return null;
    }

    @Override
    public Void visitScalarVarDef(SysY2022Parser.ScalarVarDefContext ctx) {
        // The text of the grammar symbol bType ("int" / "float")
        String bType = ctx.getParent().getChild(0).getText();

        // Retrieve the name of the variable defined and check for duplication.
        String name = ctx.Ident().getText();
        if (scope.isDuplicateSymbol(name)) {
            throw new RuntimeException("Duplicate definition of variable name: " + name);
        }

        /*
        A global variable.
         */
        if (scope.isGlobal()) {
            GlobalVariable glbVar;

            // With initialization.
            if (ctx.initVal() != null) {
                visit(ctx.initVal());
                Value initVal = retVal_;
                // Type matching check and conversion.
                switch (bType) {
                    case "int" -> {
                        if (initVal.getType().isFloatType()) {
                            float numericVal = ((Constant.ConstantFloat) initVal).getVal();
                            initVal = builder.buildConstant((int) numericVal);
                        }
                    }
                    case "float" -> {
                        if (initVal.getType().isIntegerType()) {
                            int numericVal = ((Constant.ConstantInt) initVal).getVal();
                            initVal = builder.buildConstant((float) numericVal);
                        }
                    }
                }
                // Build the glb var.
                glbVar = builder.buildGlobalVar(name,  initVal.getType());
            }

            // W/o initialization.
            else {
                switch (bType) {
                    case "int" -> glbVar = builder.buildGlobalVar(name, IntegerType.getType());
                    case "float" -> glbVar = builder.buildGlobalVar(name, FloatType.getType());
                    default -> throw new RuntimeException("Unsupported type."); // Impossible case.
                }
            }

            // Update the symbol table.
            scope.addSymbol(name, glbVar);
        }

        /*
        A local variable.
         */
        else {
            MemoryInst.Alloca addrAllocated;
            switch (bType) {
                case "int" -> addrAllocated = builder.buildAlloca(IntegerType.getType());
                case "float" -> addrAllocated = builder.buildAlloca(FloatType.getType());
                default -> throw new RuntimeException("Unsupported type."); // Impossible case.
            }
            scope.addSymbol(name, addrAllocated);
            // If it's a definition with initialization.
            if (ctx.initVal() != null) {
                // Retrieve the Value for initialization.
                visit(ctx.initVal());
                Value initVal = retVal_;
                // Implicit type conversion.
//                if (initVal.getType().isIntegerType() && addrAllocated.allocatedType.isFloatType()) {
//                    initVal = builder.buildSitofp(initVal);
//                }
//                else if(initVal.getType().isFloatType() && addrAllocated.allocatedType.isIntegerType()) {
//                    initVal = builder.buildFptosi(initVal, (IntegerType) addrAllocated.allocatedType);
//                }
                // Assignment by building a Store inst.
                builder.buildStore(initVal, addrAllocated);
            }
        }

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
            case FLT -> retVal_ = builder.buildConstant(retFloat_);
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
                case FLT -> retVal_ = builder.buildConstant(retFloat_);
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
            case "int" -> {
                retType = ir.Type.IntegerType.getType();
                setConveyedType(DataType.INT);//我随便加了个地方set了一下，应该不太对，你后面再改
            }

            //先不着急写
            //case "float" -> retType = FloatType.getType();
            case "void" -> retType = Type.VoidType.getType();
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
        scope.addSymbol(funcName, function);

        // Insert a basic block. Then scope in.
        BasicBlock bb = builder.buildBB(funcName + "_ENTRY");
        scope.pushTable();

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
//            System.out.println("要判断类型");
            if (((FunctionType)function.getType()).getRetType().isVoidType()) {
                builder.buildRet();
//                System.out.println("类型为空");
            }
            else if (((FunctionType)function.getType()).getRetType().isIntegerType()) {
                builder.buildRet(builder.buildConstant(0)); // Return 0 by default.
//                System.out.println("类型为整数");
            }
            /*else if (function.getType().getRetType().isFloatType()) {
                builder.buildRet(builder.buildConstant(.0f)); // Return 0.0f by default.
            }*/
        }

        /*
        Scope out.
         */
        scope.popTable();

        /*
        Check the function just built.
         */
        //先不管checkFunc(builder.getCurFunc());

        return null;
    }



    @Override
    public Void visitBlock(SysY2022Parser.BlockContext ctx) {
        scope.pushTable(); // Add a new layer of scope (a new symbol table).
        ctx.blockItem().forEach(this::visit);
        scope.popTable(); // Pop it out before exiting the scope.
        return null;
    }

    @Override
    public Void visitScalarLVal(SysY2022Parser.ScalarLValContext ctx) {
        /*
        Retrieve the value defined previously from the symbol table.
         */
        String name  = ctx.Ident().getText();
        Value val = scope.getVal(name);

        /*
        If the value does not exist, report the semantic error.
         */
        if (val == null) {
            throw new RuntimeException("Undefined value: " + name);
        }

        /*
        There are two cases for lVal as a grammar symbol:
        1.  If a lVal can be reduced to a primaryExp,
            in this case it is a scalar value (IntegerType or FloatType)
            thus the value can be returned directly, which will then
            be handled by visitPrimExpr2().
        2.  Otherwise, a lVal represents a left value,
            which generates an address (PointerType Value)
            designating a memory block for assignment.
         */
        // Case 1, return directly.
        if (val.getType().isIntegerType() || val.getType().isFloatType()) {
            retVal_ = val;
            return null;
        }
//        // Case 2, return a PointerType Value.
//        if (val.getType().isPointerType()) {
//            Type pointeeType = ((PointerType) val.getType()).getPointeeType();
//            // i32**: Return i32*.
//            if (pointeeType.isPointerType()) {
//                retVal_ = builder.buildLoad(pointeeType, val);
//            }
//            // [2 x i32]*: Return i32*
//            else if (pointeeType.isArrayType()) {
//                retVal_ = builder.buildGEP(val, new ArrayList<>(){{
//                    add(builder.buildConstant(0));
//                    add(builder.buildConstant(0));
//                }});
//            }
//            // i32* / float*.
//            else {
//                // Load it up when being a real argument of a function call.
//                // Otherwise, return directly for being a left value.
//                if (inBuildFCall()) {
//                    val = builder.buildLoad(pointeeType, val);
//                }
//                retVal_ = val;
//            }
//            return null;
//        }
        return null;
    }


    @Override
    public Void visitAssignment(SysY2022Parser.AssignmentContext ctx) {
        // Retrieve left value (the address to store) by visiting child.
        // Retrieve the value to be stored by visiting child.
        visit(ctx.lVal());
        Value addr = retVal_;
        visit(ctx.exp());
        Value val = retVal_;

        // Type matching check and implicit type conversions.
//        Type destType = ((PointerType) addr.getType()).getPointeType();
//        if (destType.isFloatType() && val.getType().isIntegerType()) {
//            val = builder.buildSitofp(val);
//        }
//        else if (destType.isIntegerType() && val.getType().isFloatType()) {
//            val = builder.buildFptosi(val, (IntegerType) destType);
//        }

        // Build the Store instruction.
        builder.buildStore(val, addr);
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
            //Type retType = ((FunctionType)builder.getCurFunc().getType()).getRetType(); // The return type defined in the prototype.builder.getCurFunc().getType().getRetType()
            //if (retVal.getType().isIntegerType() && retType.isFloatType()) {
                //retVal = builder.buildSitofp(retVal);
            //}
            //else if (retVal.getType().isFloatType() && retType.isIntegerType()) {
               //retVal = builder.buildFptosi(retVal, (IntegerType) retType);
            //}

            builder.buildRet(retVal);
        }
        // If not, return void.
        else {
            builder.buildRet();
        }
        // Add a dead block for possible remaining dead code.
//        builder.buildBB("_FOLLOWING_BLK");
        return null;
    }


    @Override
    public Void visitConstExp(SysY2022Parser.ConstExpContext ctx) {
        visit(ctx.addExp());
        Value lOp = retVal_;
        retVal_ = lOp;
        return null;
    }

    @Override
    public Void visitAdd1(SysY2022Parser.Add1Context ctx) {
        visit(ctx.mulExp());
        Value lOp = retVal_;
        retVal_ = lOp;
        return null;
    }
    @Override
    public Void visitMul1(SysY2022Parser.Mul1Context ctx) {
        visit(ctx.unaryExp());
        Value lOp = retVal_;
        retVal_ = lOp;
        return null;
    }


    @Override
    public Void visitUnaryExp1(SysY2022Parser.UnaryExp1Context ctx) {
        visit(ctx.primaryExp());
        Value lOp = retVal_;
        retVal_ = lOp;
        return null;
    }

    @Override public Void visitPrimaryExp3(SysY2022Parser.PrimaryExp3Context ctx) {
        visit(ctx.number());
        Value lOp = retVal_;
        retVal_ = lOp;
        return null;
    }
    @Override
    public Void visitNumber(SysY2022Parser.NumberContext ctx) {
        int ret = 0;
        if (ctx.IntConst() != null) {
            ret = new BigInteger(ctx.IntConst().getText(), 10).intValue();
        }
        setConveyedType(DataType.INT);
        retInt_ = ret;
        //我觉得这应该访问子节点 判断那个数是啥
        //然后把retInt_ set成那个数
        //然后这再build进去就行了
        if (!this.inConstFolding()) {
            switch (getConveyedType()) {
                case INT -> retVal_ = builder.buildConstant(retInt_);
                //case FLT -> retVal_ = builder.buildConstant(retFloat_);
            }
        }


        return null;
    }
}
