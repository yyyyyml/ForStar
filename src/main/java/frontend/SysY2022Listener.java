// Generated from E:/ForStar/src/main/java/frontend\SysY2022.g4 by ANTLR 4.12.0
package frontend;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SysY2022Parser}.
 */
public interface SysY2022Listener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SysY2022Parser#compUnit}.
	 *
	 * @param ctx the parse tree
	 */
	void enterCompUnit(SysY2022Parser.CompUnitContext ctx);

	/**
	 * Exit a parse tree produced by {@link SysY2022Parser#compUnit}.
	 *
	 * @param ctx the parse tree
	 */
	void exitCompUnit(SysY2022Parser.CompUnitContext ctx);

	/**
	 * Enter a parse tree produced by {@link SysY2022Parser#decl}.
	 *
	 * @param ctx the parse tree
	 */
	void enterDecl(SysY2022Parser.DeclContext ctx);

	/**
	 * Exit a parse tree produced by {@link SysY2022Parser#decl}.
	 *
	 * @param ctx the parse tree
	 */
	void exitDecl(SysY2022Parser.DeclContext ctx);

	/**
	 * Enter a parse tree produced by {@link SysY2022Parser#constDecl}.
	 *
	 * @param ctx the parse tree
	 */
	void enterConstDecl(SysY2022Parser.ConstDeclContext ctx);

	/**
	 * Exit a parse tree produced by {@link SysY2022Parser#constDecl}.
	 *
	 * @param ctx the parse tree
	 */
	void exitConstDecl(SysY2022Parser.ConstDeclContext ctx);

	/**
	 * Enter a parse tree produced by {@link SysY2022Parser#bType}.
	 *
	 * @param ctx the parse tree
	 */
	void enterBType(SysY2022Parser.BTypeContext ctx);

	/**
	 * Exit a parse tree produced by {@link SysY2022Parser#bType}.
	 *
	 * @param ctx the parse tree
	 */
	void exitBType(SysY2022Parser.BTypeContext ctx);

	/**
	 * Enter a parse tree produced by the {@code scalarConstDef}
	 * labeled alternative in {@link SysY2022Parser#constDef}.
	 *
	 * @param ctx the parse tree
	 */
	void enterScalarConstDef(SysY2022Parser.ScalarConstDefContext ctx);

	/**
	 * Exit a parse tree produced by the {@code scalarConstDef}
	 * labeled alternative in {@link SysY2022Parser#constDef}.
	 *
	 * @param ctx the parse tree
	 */
	void exitScalarConstDef(SysY2022Parser.ScalarConstDefContext ctx);

	/**
	 * Enter a parse tree produced by the {@code arrConstDef}
	 * labeled alternative in {@link SysY2022Parser#constDef}.
	 *
	 * @param ctx the parse tree
	 */
	void enterArrConstDef(SysY2022Parser.ArrConstDefContext ctx);

	/**
	 * Exit a parse tree produced by the {@code arrConstDef}
	 * labeled alternative in {@link SysY2022Parser#constDef}.
	 *
	 * @param ctx the parse tree
	 */
	void exitArrConstDef(SysY2022Parser.ArrConstDefContext ctx);

	/**
	 * Enter a parse tree produced by the {@code scalarConstInitVal}
	 * labeled alternative in {@link SysY2022Parser#constInitVal}.
	 *
	 * @param ctx the parse tree
	 */
	void enterScalarConstInitVal(SysY2022Parser.ScalarConstInitValContext ctx);

	/**
	 * Exit a parse tree produced by the {@code scalarConstInitVal}
	 * labeled alternative in {@link SysY2022Parser#constInitVal}.
	 *
	 * @param ctx the parse tree
	 */
	void exitScalarConstInitVal(SysY2022Parser.ScalarConstInitValContext ctx);

	/**
	 * Enter a parse tree produced by the {@code arrConstInitVal}
	 * labeled alternative in {@link SysY2022Parser#constInitVal}.
	 *
	 * @param ctx the parse tree
	 */
	void enterArrConstInitVal(SysY2022Parser.ArrConstInitValContext ctx);

	/**
	 * Exit a parse tree produced by the {@code arrConstInitVal}
	 * labeled alternative in {@link SysY2022Parser#constInitVal}.
	 *
	 * @param ctx the parse tree
	 */
	void exitArrConstInitVal(SysY2022Parser.ArrConstInitValContext ctx);

	/**
	 * Enter a parse tree produced by {@link SysY2022Parser#varDecl}.
	 *
	 * @param ctx the parse tree
	 */
	void enterVarDecl(SysY2022Parser.VarDeclContext ctx);

	/**
	 * Exit a parse tree produced by {@link SysY2022Parser#varDecl}.
	 *
	 * @param ctx the parse tree
	 */
	void exitVarDecl(SysY2022Parser.VarDeclContext ctx);

	/**
	 * Enter a parse tree produced by the {@code scalarVarDef}
	 * labeled alternative in {@link SysY2022Parser#varDef}.
	 *
	 * @param ctx the parse tree
	 */
	void enterScalarVarDef(SysY2022Parser.ScalarVarDefContext ctx);

	/**
	 * Exit a parse tree produced by the {@code scalarVarDef}
	 * labeled alternative in {@link SysY2022Parser#varDef}.
	 *
	 * @param ctx the parse tree
	 */
	void exitScalarVarDef(SysY2022Parser.ScalarVarDefContext ctx);

	/**
	 * Enter a parse tree produced by the {@code arrVarDef}
	 * labeled alternative in {@link SysY2022Parser#varDef}.
	 *
	 * @param ctx the parse tree
	 */
	void enterArrVarDef(SysY2022Parser.ArrVarDefContext ctx);

	/**
	 * Exit a parse tree produced by the {@code arrVarDef}
	 * labeled alternative in {@link SysY2022Parser#varDef}.
	 *
	 * @param ctx the parse tree
	 */
	void exitArrVarDef(SysY2022Parser.ArrVarDefContext ctx);

	/**
	 * Enter a parse tree produced by the {@code scalarInitVal}
	 * labeled alternative in {@link SysY2022Parser#initVal}.
	 *
	 * @param ctx the parse tree
	 */
	void enterScalarInitVal(SysY2022Parser.ScalarInitValContext ctx);

	/**
	 * Exit a parse tree produced by the {@code scalarInitVal}
	 * labeled alternative in {@link SysY2022Parser#initVal}.
	 *
	 * @param ctx the parse tree
	 */
	void exitScalarInitVal(SysY2022Parser.ScalarInitValContext ctx);

	/**
	 * Enter a parse tree produced by the {@code arrInitval}
	 * labeled alternative in {@link SysY2022Parser#initVal}.
	 *
	 * @param ctx the parse tree
	 */
	void enterArrInitval(SysY2022Parser.ArrInitvalContext ctx);

	/**
	 * Exit a parse tree produced by the {@code arrInitval}
	 * labeled alternative in {@link SysY2022Parser#initVal}.
	 *
	 * @param ctx the parse tree
	 */
	void exitArrInitval(SysY2022Parser.ArrInitvalContext ctx);

	/**
	 * Enter a parse tree produced by {@link SysY2022Parser#funcDef}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFuncDef(SysY2022Parser.FuncDefContext ctx);

	/**
	 * Exit a parse tree produced by {@link SysY2022Parser#funcDef}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFuncDef(SysY2022Parser.FuncDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link SysY2022Parser#funcType}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFuncType(SysY2022Parser.FuncTypeContext ctx);

	/**
	 * Exit a parse tree produced by {@link SysY2022Parser#funcType}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFuncType(SysY2022Parser.FuncTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link SysY2022Parser#funcFParams}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFuncFParams(SysY2022Parser.FuncFParamsContext ctx);

	/**
	 * Exit a parse tree produced by {@link SysY2022Parser#funcFParams}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFuncFParams(SysY2022Parser.FuncFParamsContext ctx);

	/**
	 * Enter a parse tree produced by the {@code scalarFuncFParam}
	 * labeled alternative in {@link SysY2022Parser#funcFParam}.
	 *
	 * @param ctx the parse tree
	 */
	void enterScalarFuncFParam(SysY2022Parser.ScalarFuncFParamContext ctx);

	/**
	 * Exit a parse tree produced by the {@code scalarFuncFParam}
	 * labeled alternative in {@link SysY2022Parser#funcFParam}.
	 *
	 * @param ctx the parse tree
	 */
	void exitScalarFuncFParam(SysY2022Parser.ScalarFuncFParamContext ctx);

	/**
	 * Enter a parse tree produced by the {@code arrFuncFParam}
	 * labeled alternative in {@link SysY2022Parser#funcFParam}.
	 *
	 * @param ctx the parse tree
	 */
	void enterArrFuncFParam(SysY2022Parser.ArrFuncFParamContext ctx);

	/**
	 * Exit a parse tree produced by the {@code arrFuncFParam}
	 * labeled alternative in {@link SysY2022Parser#funcFParam}.
	 *
	 * @param ctx the parse tree
	 */
	void exitArrFuncFParam(SysY2022Parser.ArrFuncFParamContext ctx);

	/**
	 * Enter a parse tree produced by {@link SysY2022Parser#block}.
	 *
	 * @param ctx the parse tree
	 */
	void enterBlock(SysY2022Parser.BlockContext ctx);

	/**
	 * Exit a parse tree produced by {@link SysY2022Parser#block}.
	 *
	 * @param ctx the parse tree
	 */
	void exitBlock(SysY2022Parser.BlockContext ctx);

	/**
	 * Enter a parse tree produced by {@link SysY2022Parser#blockItem}.
	 *
	 * @param ctx the parse tree
	 */
	void enterBlockItem(SysY2022Parser.BlockItemContext ctx);

	/**
	 * Exit a parse tree produced by {@link SysY2022Parser#blockItem}.
	 *
	 * @param ctx the parse tree
	 */
	void exitBlockItem(SysY2022Parser.BlockItemContext ctx);

	/**
	 * Enter a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 *
	 * @param ctx the parse tree
	 */
	void enterAssignment(SysY2022Parser.AssignmentContext ctx);

	/**
	 * Exit a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 *
	 * @param ctx the parse tree
	 */
	void exitAssignment(SysY2022Parser.AssignmentContext ctx);

	/**
	 * Enter a parse tree produced by the {@code expStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 *
	 * @param ctx the parse tree
	 */
	void enterExpStmt(SysY2022Parser.ExpStmtContext ctx);

	/**
	 * Exit a parse tree produced by the {@code expStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 *
	 * @param ctx the parse tree
	 */
	void exitExpStmt(SysY2022Parser.ExpStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 *
	 * @param ctx the parse tree
	 */
	void enterBlockStmt(SysY2022Parser.BlockStmtContext ctx);

	/**
	 * Exit a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 *
	 * @param ctx the parse tree
	 */
	void exitBlockStmt(SysY2022Parser.BlockStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 *
	 * @param ctx the parse tree
	 */
	void enterIfStmt(SysY2022Parser.IfStmtContext ctx);

	/**
	 * Exit a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 *
	 * @param ctx the parse tree
	 */
	void exitIfStmt(SysY2022Parser.IfStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ifelseStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 *
	 * @param ctx the parse tree
	 */
	void enterIfelseStmt(SysY2022Parser.IfelseStmtContext ctx);

	/**
	 * Exit a parse tree produced by the {@code ifelseStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 *
	 * @param ctx the parse tree
	 */
	void exitIfelseStmt(SysY2022Parser.IfelseStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 *
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(SysY2022Parser.WhileStmtContext ctx);

	/**
	 * Exit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 *
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(SysY2022Parser.WhileStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 *
	 * @param ctx the parse tree
	 */
	void enterBreakStmt(SysY2022Parser.BreakStmtContext ctx);

	/**
	 * Exit a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 *
	 * @param ctx the parse tree
	 */
	void exitBreakStmt(SysY2022Parser.BreakStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code continueStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 *
	 * @param ctx the parse tree
	 */
	void enterContinueStmt(SysY2022Parser.ContinueStmtContext ctx);

	/**
	 * Exit a parse tree produced by the {@code continueStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 *
	 * @param ctx the parse tree
	 */
	void exitContinueStmt(SysY2022Parser.ContinueStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 *
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(SysY2022Parser.ReturnStmtContext ctx);

	/**
	 * Exit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 *
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(SysY2022Parser.ReturnStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link SysY2022Parser#exp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterExp(SysY2022Parser.ExpContext ctx);

	/**
	 * Exit a parse tree produced by {@link SysY2022Parser#exp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitExp(SysY2022Parser.ExpContext ctx);

	/**
	 * Enter a parse tree produced by {@link SysY2022Parser#cond}.
	 *
	 * @param ctx the parse tree
	 */
	void enterCond(SysY2022Parser.CondContext ctx);

	/**
	 * Exit a parse tree produced by {@link SysY2022Parser#cond}.
	 *
	 * @param ctx the parse tree
	 */
	void exitCond(SysY2022Parser.CondContext ctx);

	/**
	 * Enter a parse tree produced by the {@code scalarLVal}
	 * labeled alternative in {@link SysY2022Parser#lVal}.
	 *
	 * @param ctx the parse tree
	 */
	void enterScalarLVal(SysY2022Parser.ScalarLValContext ctx);

	/**
	 * Exit a parse tree produced by the {@code scalarLVal}
	 * labeled alternative in {@link SysY2022Parser#lVal}.
	 *
	 * @param ctx the parse tree
	 */
	void exitScalarLVal(SysY2022Parser.ScalarLValContext ctx);

	/**
	 * Enter a parse tree produced by the {@code arrLVal}
	 * labeled alternative in {@link SysY2022Parser#lVal}.
	 *
	 * @param ctx the parse tree
	 */
	void enterArrLVal(SysY2022Parser.ArrLValContext ctx);

	/**
	 * Exit a parse tree produced by the {@code arrLVal}
	 * labeled alternative in {@link SysY2022Parser#lVal}.
	 *
	 * @param ctx the parse tree
	 */
	void exitArrLVal(SysY2022Parser.ArrLValContext ctx);

	/**
	 * Enter a parse tree produced by the {@code primaryExp1}
	 * labeled alternative in {@link SysY2022Parser#primaryExp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterPrimaryExp1(SysY2022Parser.PrimaryExp1Context ctx);

	/**
	 * Exit a parse tree produced by the {@code primaryExp1}
	 * labeled alternative in {@link SysY2022Parser#primaryExp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitPrimaryExp1(SysY2022Parser.PrimaryExp1Context ctx);

	/**
	 * Enter a parse tree produced by the {@code primaryExp2}
	 * labeled alternative in {@link SysY2022Parser#primaryExp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterPrimaryExp2(SysY2022Parser.PrimaryExp2Context ctx);

	/**
	 * Exit a parse tree produced by the {@code primaryExp2}
	 * labeled alternative in {@link SysY2022Parser#primaryExp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitPrimaryExp2(SysY2022Parser.PrimaryExp2Context ctx);

	/**
	 * Enter a parse tree produced by the {@code primaryExp3}
	 * labeled alternative in {@link SysY2022Parser#primaryExp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterPrimaryExp3(SysY2022Parser.PrimaryExp3Context ctx);

	/**
	 * Exit a parse tree produced by the {@code primaryExp3}
	 * labeled alternative in {@link SysY2022Parser#primaryExp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitPrimaryExp3(SysY2022Parser.PrimaryExp3Context ctx);

	/**
	 * Enter a parse tree produced by {@link SysY2022Parser#number}.
	 *
	 * @param ctx the parse tree
	 */
	void enterNumber(SysY2022Parser.NumberContext ctx);

	/**
	 * Exit a parse tree produced by {@link SysY2022Parser#number}.
	 *
	 * @param ctx the parse tree
	 */
	void exitNumber(SysY2022Parser.NumberContext ctx);

	/**
	 * Enter a parse tree produced by the {@code unaryExp1}
	 * labeled alternative in {@link SysY2022Parser#unaryExp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterUnaryExp1(SysY2022Parser.UnaryExp1Context ctx);

	/**
	 * Exit a parse tree produced by the {@code unaryExp1}
	 * labeled alternative in {@link SysY2022Parser#unaryExp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitUnaryExp1(SysY2022Parser.UnaryExp1Context ctx);

	/**
	 * Enter a parse tree produced by the {@code unaryExp2}
	 * labeled alternative in {@link SysY2022Parser#unaryExp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterUnaryExp2(SysY2022Parser.UnaryExp2Context ctx);

	/**
	 * Exit a parse tree produced by the {@code unaryExp2}
	 * labeled alternative in {@link SysY2022Parser#unaryExp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitUnaryExp2(SysY2022Parser.UnaryExp2Context ctx);

	/**
	 * Enter a parse tree produced by the {@code unaryExp3}
	 * labeled alternative in {@link SysY2022Parser#unaryExp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterUnaryExp3(SysY2022Parser.UnaryExp3Context ctx);

	/**
	 * Exit a parse tree produced by the {@code unaryExp3}
	 * labeled alternative in {@link SysY2022Parser#unaryExp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitUnaryExp3(SysY2022Parser.UnaryExp3Context ctx);

	/**
	 * Enter a parse tree produced by {@link SysY2022Parser#unaryOp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterUnaryOp(SysY2022Parser.UnaryOpContext ctx);

	/**
	 * Exit a parse tree produced by {@link SysY2022Parser#unaryOp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitUnaryOp(SysY2022Parser.UnaryOpContext ctx);

	/**
	 * Enter a parse tree produced by {@link SysY2022Parser#funcRParams}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFuncRParams(SysY2022Parser.FuncRParamsContext ctx);

	/**
	 * Exit a parse tree produced by {@link SysY2022Parser#funcRParams}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFuncRParams(SysY2022Parser.FuncRParamsContext ctx);

	/**
	 * Enter a parse tree produced by the {@code expRParam}
	 * labeled alternative in {@link SysY2022Parser#funcRParam}.
	 *
	 * @param ctx the parse tree
	 */
	void enterExpRParam(SysY2022Parser.ExpRParamContext ctx);

	/**
	 * Exit a parse tree produced by the {@code expRParam}
	 * labeled alternative in {@link SysY2022Parser#funcRParam}.
	 *
	 * @param ctx the parse tree
	 */
	void exitExpRParam(SysY2022Parser.ExpRParamContext ctx);

	/**
	 * Enter a parse tree produced by the {@code strRParam}
	 * labeled alternative in {@link SysY2022Parser#funcRParam}.
	 *
	 * @param ctx the parse tree
	 */
	void enterStrRParam(SysY2022Parser.StrRParamContext ctx);

	/**
	 * Exit a parse tree produced by the {@code strRParam}
	 * labeled alternative in {@link SysY2022Parser#funcRParam}.
	 *
	 * @param ctx the parse tree
	 */
	void exitStrRParam(SysY2022Parser.StrRParamContext ctx);

	/**
	 * Enter a parse tree produced by the {@code mul2}
	 * labeled alternative in {@link SysY2022Parser#mulExp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterMul2(SysY2022Parser.Mul2Context ctx);

	/**
	 * Exit a parse tree produced by the {@code mul2}
	 * labeled alternative in {@link SysY2022Parser#mulExp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitMul2(SysY2022Parser.Mul2Context ctx);

	/**
	 * Enter a parse tree produced by the {@code mul1}
	 * labeled alternative in {@link SysY2022Parser#mulExp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterMul1(SysY2022Parser.Mul1Context ctx);

	/**
	 * Exit a parse tree produced by the {@code mul1}
	 * labeled alternative in {@link SysY2022Parser#mulExp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitMul1(SysY2022Parser.Mul1Context ctx);

	/**
	 * Enter a parse tree produced by the {@code add2}
	 * labeled alternative in {@link SysY2022Parser#addExp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterAdd2(SysY2022Parser.Add2Context ctx);

	/**
	 * Exit a parse tree produced by the {@code add2}
	 * labeled alternative in {@link SysY2022Parser#addExp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitAdd2(SysY2022Parser.Add2Context ctx);

	/**
	 * Enter a parse tree produced by the {@code add1}
	 * labeled alternative in {@link SysY2022Parser#addExp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterAdd1(SysY2022Parser.Add1Context ctx);

	/**
	 * Exit a parse tree produced by the {@code add1}
	 * labeled alternative in {@link SysY2022Parser#addExp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitAdd1(SysY2022Parser.Add1Context ctx);

	/**
	 * Enter a parse tree produced by the {@code rel2}
	 * labeled alternative in {@link SysY2022Parser#relExp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterRel2(SysY2022Parser.Rel2Context ctx);

	/**
	 * Exit a parse tree produced by the {@code rel2}
	 * labeled alternative in {@link SysY2022Parser#relExp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitRel2(SysY2022Parser.Rel2Context ctx);

	/**
	 * Enter a parse tree produced by the {@code rel1}
	 * labeled alternative in {@link SysY2022Parser#relExp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterRel1(SysY2022Parser.Rel1Context ctx);

	/**
	 * Exit a parse tree produced by the {@code rel1}
	 * labeled alternative in {@link SysY2022Parser#relExp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitRel1(SysY2022Parser.Rel1Context ctx);

	/**
	 * Enter a parse tree produced by the {@code eq1}
	 * labeled alternative in {@link SysY2022Parser#eqExp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterEq1(SysY2022Parser.Eq1Context ctx);

	/**
	 * Exit a parse tree produced by the {@code eq1}
	 * labeled alternative in {@link SysY2022Parser#eqExp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitEq1(SysY2022Parser.Eq1Context ctx);

	/**
	 * Enter a parse tree produced by the {@code eq2}
	 * labeled alternative in {@link SysY2022Parser#eqExp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterEq2(SysY2022Parser.Eq2Context ctx);

	/**
	 * Exit a parse tree produced by the {@code eq2}
	 * labeled alternative in {@link SysY2022Parser#eqExp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitEq2(SysY2022Parser.Eq2Context ctx);

	/**
	 * Enter a parse tree produced by the {@code lAnd2}
	 * labeled alternative in {@link SysY2022Parser#lAndExp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterLAnd2(SysY2022Parser.LAnd2Context ctx);

	/**
	 * Exit a parse tree produced by the {@code lAnd2}
	 * labeled alternative in {@link SysY2022Parser#lAndExp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitLAnd2(SysY2022Parser.LAnd2Context ctx);

	/**
	 * Enter a parse tree produced by the {@code lAnd1}
	 * labeled alternative in {@link SysY2022Parser#lAndExp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterLAnd1(SysY2022Parser.LAnd1Context ctx);

	/**
	 * Exit a parse tree produced by the {@code lAnd1}
	 * labeled alternative in {@link SysY2022Parser#lAndExp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitLAnd1(SysY2022Parser.LAnd1Context ctx);

	/**
	 * Enter a parse tree produced by the {@code lOr1}
	 * labeled alternative in {@link SysY2022Parser#lOrExp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterLOr1(SysY2022Parser.LOr1Context ctx);

	/**
	 * Exit a parse tree produced by the {@code lOr1}
	 * labeled alternative in {@link SysY2022Parser#lOrExp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitLOr1(SysY2022Parser.LOr1Context ctx);

	/**
	 * Enter a parse tree produced by the {@code lOr2}
	 * labeled alternative in {@link SysY2022Parser#lOrExp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterLOr2(SysY2022Parser.LOr2Context ctx);

	/**
	 * Exit a parse tree produced by the {@code lOr2}
	 * labeled alternative in {@link SysY2022Parser#lOrExp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitLOr2(SysY2022Parser.LOr2Context ctx);

	/**
	 * Enter a parse tree produced by {@link SysY2022Parser#constExp}.
	 *
	 * @param ctx the parse tree
	 */
	void enterConstExp(SysY2022Parser.ConstExpContext ctx);

	/**
	 * Exit a parse tree produced by {@link SysY2022Parser#constExp}.
	 *
	 * @param ctx the parse tree
	 */
	void exitConstExp(SysY2022Parser.ConstExpContext ctx);
}