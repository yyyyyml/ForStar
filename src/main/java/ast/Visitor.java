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
    private Builder builder;
    public Visitor(Module module) {
        this.builder = new Builder(module);
    }
    private final boolean ON = true;
    private final boolean OFF = false;
    private boolean envConstFolding = OFF;
    private void setConstFolding(boolean stat) {
        envConstFolding = stat;
    }
    public boolean inConstFolding() {
        return envConstFolding;
    }
    private boolean envBuildFCall = OFF;

    private void setBuildFCall(boolean stat) {
        envBuildFCall = stat;
    }
    private boolean inBuildFCall() {
        return envBuildFCall;
    }
    private enum DataType {FLT, INT}
    private DataType envConveyedType = null;
    private DataType getConveyedType() {
        return envConveyedType;
    }
    private void setConveyedType(DataType dataType) {
        envConveyedType = dataType;
    }

    private Value retVal_;
    private ArrayList<Value> retValList_;
    private Type retType_;
    private ArrayList<Type> retTypeList_;
    private int retInt_;
    private float retFloat_;



    @Override
    public Void visitCompUnit(SysY2022Parser.CompUnitContext ctx) {
        super.visitCompUnit(ctx);
        return null;
    }


    @Override
    public Void visitScalarConstDef(SysY2022Parser.ScalarConstDefContext ctx) {
        // Retrieve the name of the variable defined and check for duplication.//把identifier改成了Ident（)
        String name = ctx.Ident().getText();
        if (scope.isDuplicateSymbol(name)) {
            throw new RuntimeException("Duplicate definition of constant name: " + name);
        }
        visit(ctx.constInitVal());
        Value initVal = retVal_;
        String bType = ctx.getParent().getChild(1).getText();
        if (bType == "int") {
            builder.buildConstant(((Constant.ConstantInt) initVal).getVal());
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
                    case "int" -> {
                        glbVar = builder.buildGlobalVar(name, IntegerType.getType());

                    }
                    case "float" -> {
                        glbVar = builder.buildGlobalVar(name, FloatType.getType());

                    }
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
                case "int" -> {
                    addrAllocated = builder.buildAlloca(IntegerType.getType());

                }
                case "float" -> {
                    addrAllocated = builder.buildAlloca(FloatType.getType());

                }
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

        String funcName = ctx.Ident().getText();
        Type retType;
        String strRetType = ctx.funcType().getText();
        switch (strRetType) {
            case "int" -> {
                retType = ir.Type.IntegerType.getType();
                setConveyedType(DataType.INT);//我随便加了个地方set了一下，应该不太对，你后面再改
            }

            case "float" -> retType = FloatType.getType();
            case "void" -> retType = Type.VoidType.getType();
            default -> throw new RuntimeException("Unsupported function return type.");
        }

        ArrayList<Type> argTypes = new ArrayList<>();
        if (ctx.funcFParams() != null) {
            visit(ctx.funcFParams());
            argTypes.addAll(retTypeList_);
        }
        retTypeList_ = new ArrayList<>(); // Clear the list for next func def.

        if (!scope.isGlobal()) {
            throw new RuntimeException("Nested definition of function: " + funcName);
        }

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
            //MemoryInst.Alloca localVar = builder.buildAlloca(arg.getType());

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
            if (((FunctionType) function.getType()).getRetType().isVoidType()) {
                builder.buildRet();
//                System.out.println("类型为空");
            } else if (((FunctionType) function.getType()).getRetType().isIntegerType()) {
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
        String name = ctx.Ident().getText();
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
        // Case 2, return a PointerType Value.
        if (val.getType().isPointerType()) {
            Type pointedType = ((PointerType) val.getType()).getPointedType();
            // i32**: Return i32*.
            if (pointedType.isPointerType()) {
                retVal_ = builder.buildLoad(pointedType, val);
            }
            // [2 x i32]*: Return i32*
//            else if (pointedType.isArrayType()) {
//                retVal_ = builder.buildGEP(val, new ArrayList<>(){{
//                    add(builder.buildConstant(0));
//                    add(builder.buildConstant(0));
//                }});
//            }
            // i32* / float*.
            else {
                // Load it up when being a real argument of a function call.
                // Otherwise, return directly for being a left value.
                if (inBuildFCall()) {
                    val = builder.buildLoad(pointedType, val);
                }
                retVal_ = val;
            }
            return null;
        }
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
        Type destType = ((PointerType) addr.getType()).getPointedType();
        if (destType.isFloatType() && val.getType().isIntegerType()) {
            val = builder.buildSitofp(val);
        }
        else if (destType.isIntegerType() && val.getType().isFloatType()) {
            val = builder.buildFptosi(val);
        }

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
            Type retType = ((FunctionType)builder.getCurFunc().getType()).getRetType(); // The return type defined in the prototype.builder.getCurFunc().getType().getRetType()
            if (retVal.getType().isIntegerType() && retType.isFloatType()) {
            retVal = builder.buildSitofp(retVal);
            }
            else if (retVal.getType().isFloatType() && retType.isIntegerType()) {
            retVal = builder.buildFptosi(retVal);
            }

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
    public Void visitAdd2(SysY2022Parser.Add2Context ctx) {
        /*
        Global expression: Compute value of the expr w/o instruction generation.
         */
        if (this.inConstFolding()) {
        }

        /*
        Local expression: Instructions will be generated.
         */
        else {
            // Retrieve the 1st mulExp (as the left operand) by visiting child.
            visit(ctx.addExp());
            Value lOp = retVal_;

            // The 2nd and possibly more MulExp.
            // Retrieve the next mulExp (as the right operand) by visiting child.
            visit(ctx.mulExp());
            Value rOp = retVal_;

            // Check if the lOp/rOp is a pointer. if it is, load it up.
            if (lOp.getType().isPointerType()) {
                lOp = builder.buildLoad(((PointerType) lOp.getType()).getPointedType(), lOp);
            }
            if (rOp.getType().isPointerType()) {
                rOp = builder.buildLoad(((PointerType) rOp.getType()).getPointedType(), rOp);
            }


            // Auto type promotion. (i1 -> i32, i32 -> float)
           /* if (lOp.getType().isI1()) {
                lOp = builder.buildZExt(lOp);
            }
            if (rOp.getType().isI1()) {
                rOp = builder.buildZExt(rOp);
            }*/
            if (lOp.getType().isIntegerType() && rOp.getType().isFloatType()) {
                lOp = builder.buildSitofp(lOp);
            }
            else if (lOp.getType().isFloatType() && rOp.getType().isIntegerType()) {
                rOp = builder.buildSitofp(rOp);
            }

            // Generate an instruction to compute result of left and right operands
            // as the new left operand for the next round.
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
        /*
        Global expression: Compute value of the expr w/o instruction generation.
         */
        if (this.inConstFolding()) {
        }

        /*
        Local expression: Instructions will be generated.
         */
        else {
            // Retrieve the 1st mulExp (as the left operand) by visiting child.
            visit(ctx.mulExp());
            Value lOp = retVal_;

            // The 2nd and possibly more MulExp.
            // Retrieve the next mulExp (as the right operand) by visiting child.
            visit(ctx.unaryExp());
            Value rOp = retVal_;

            // Check if the lOp/rOp is a pointer. if it is, load it up.
            if (lOp.getType().isPointerType()) {
                lOp = builder.buildLoad(((PointerType) lOp.getType()).getPointedType(), lOp);
            }
            if (rOp.getType().isPointerType()) {
                rOp = builder.buildLoad(((PointerType) rOp.getType()).getPointedType(), rOp);
            }


            // Auto type promotion. (i1 -> i32, i32 -> float)
           /* if (lOp.getType().isI1()) {
                lOp = builder.buildZExt(lOp);
            }
            if (rOp.getType().isI1()) {
                rOp = builder.buildZExt(rOp);
            }*/
            if (lOp.getType().isIntegerType() && rOp.getType().isFloatType()) {
                lOp = builder.buildSitofp(lOp);
            }
            else if (lOp.getType().isFloatType() && rOp.getType().isIntegerType()) {
                rOp = builder.buildSitofp(rOp);
            }

            // Generate an instruction to compute result of left and right operands
            // as the new left operand for the next round.
            switch (ctx.getChild(1).getText()) {
                case "*" -> lOp = builder.buildMul(lOp, rOp);
                case "/" -> lOp = builder.buildDiv(lOp, rOp);
                //还差一个%运算
                default -> {
                }
            }
            retVal_ = lOp;
        }

        return null;
    }



    @Override
    public Void visitPrimaryExp2(SysY2022Parser.PrimaryExp2Context ctx) {

        visit(ctx.lVal());
        // If it's not in a function call,
        // load the memory block pointed by the PointerType Value retrieved from lVal.
        if (!inBuildFCall() && retVal_.getType().isPointerType()) {
            Type pointedType = ((PointerType) retVal_.getType()).getPointedType();
            retVal_ = builder.buildLoad(pointedType, retVal_);
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
        } else {
            float ret = Float.parseFloat(ctx.getChild(0).getText());
            setConveyedType(DataType.FLT);
            retFloat_ = ret;
        }

        //我觉得这应该访问子节点 判断那个数是啥
        //然后把retInt_ set成那个数
        //然后这再build进去就行了
        if (!this.inConstFolding()) {
            switch (getConveyedType()) {
                case INT -> retVal_ = builder.buildConstant(retInt_);
                case FLT -> retVal_ = builder.buildConstant(retFloat_);
            }
        }


        return null;
    }
}
