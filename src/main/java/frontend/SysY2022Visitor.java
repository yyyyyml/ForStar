// Generated from java-escape by ANTLR 4.11.1
package frontend;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SysY2022Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SysY2022Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SysY2022Parser#compUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompUnit(SysY2022Parser.CompUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link SysY2022Parser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl(SysY2022Parser.DeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link SysY2022Parser#constDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstDecl(SysY2022Parser.ConstDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link SysY2022Parser#bType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBType(SysY2022Parser.BTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code scalarConstDef}
	 * labeled alternative in {@link SysY2022Parser#constDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScalarConstDef(SysY2022Parser.ScalarConstDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrConstDef}
	 * labeled alternative in {@link SysY2022Parser#constDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrConstDef(SysY2022Parser.ArrConstDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code scalarConstInitVal}
	 * labeled alternative in {@link SysY2022Parser#constInitVal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScalarConstInitVal(SysY2022Parser.ScalarConstInitValContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrConstInitVal}
	 * labeled alternative in {@link SysY2022Parser#constInitVal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrConstInitVal(SysY2022Parser.ArrConstInitValContext ctx);
	/**
	 * Visit a parse tree produced by {@link SysY2022Parser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(SysY2022Parser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code scalarVarDef}
	 * labeled alternative in {@link SysY2022Parser#varDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScalarVarDef(SysY2022Parser.ScalarVarDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrVarDef}
	 * labeled alternative in {@link SysY2022Parser#varDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrVarDef(SysY2022Parser.ArrVarDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code scalarInitVal}
	 * labeled alternative in {@link SysY2022Parser#initVal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScalarInitVal(SysY2022Parser.ScalarInitValContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrInitval}
	 * labeled alternative in {@link SysY2022Parser#initVal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrInitval(SysY2022Parser.ArrInitvalContext ctx);
	/**
	 * Visit a parse tree produced by {@link SysY2022Parser#funcDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDef(SysY2022Parser.FuncDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link SysY2022Parser#funcType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncType(SysY2022Parser.FuncTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SysY2022Parser#funcFParams}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncFParams(SysY2022Parser.FuncFParamsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code scalarFuncFParam}
	 * labeled alternative in {@link SysY2022Parser#funcFParam}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScalarFuncFParam(SysY2022Parser.ScalarFuncFParamContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrFuncFParam}
	 * labeled alternative in {@link SysY2022Parser#funcFParam}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrFuncFParam(SysY2022Parser.ArrFuncFParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link SysY2022Parser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(SysY2022Parser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link SysY2022Parser#blockItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockItem(SysY2022Parser.BlockItemContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(SysY2022Parser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpStmt(SysY2022Parser.ExpStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStmt(SysY2022Parser.BlockStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(SysY2022Parser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifelseStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfelseStmt(SysY2022Parser.IfelseStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(SysY2022Parser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStmt(SysY2022Parser.BreakStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code continueStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStmt(SysY2022Parser.ContinueStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link SysY2022Parser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(SysY2022Parser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SysY2022Parser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(SysY2022Parser.ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link SysY2022Parser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCond(SysY2022Parser.CondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code scalarLVal}
	 * labeled alternative in {@link SysY2022Parser#lVal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScalarLVal(SysY2022Parser.ScalarLValContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrLVal}
	 * labeled alternative in {@link SysY2022Parser#lVal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrLVal(SysY2022Parser.ArrLValContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primaryExp1}
	 * labeled alternative in {@link SysY2022Parser#primaryExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExp1(SysY2022Parser.PrimaryExp1Context ctx);
	/**
	 * Visit a parse tree produced by the {@code primaryExp2}
	 * labeled alternative in {@link SysY2022Parser#primaryExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExp2(SysY2022Parser.PrimaryExp2Context ctx);
	/**
	 * Visit a parse tree produced by the {@code primaryExp3}
	 * labeled alternative in {@link SysY2022Parser#primaryExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExp3(SysY2022Parser.PrimaryExp3Context ctx);
	/**
	 * Visit a parse tree produced by {@link SysY2022Parser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(SysY2022Parser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryExp1}
	 * labeled alternative in {@link SysY2022Parser#unaryExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExp1(SysY2022Parser.UnaryExp1Context ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryExp2}
	 * labeled alternative in {@link SysY2022Parser#unaryExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExp2(SysY2022Parser.UnaryExp2Context ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryExp3}
	 * labeled alternative in {@link SysY2022Parser#unaryExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExp3(SysY2022Parser.UnaryExp3Context ctx);
	/**
	 * Visit a parse tree produced by {@link SysY2022Parser#unaryOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOp(SysY2022Parser.UnaryOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link SysY2022Parser#funcRParams}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncRParams(SysY2022Parser.FuncRParamsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expRParam}
	 * labeled alternative in {@link SysY2022Parser#funcRParam}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpRParam(SysY2022Parser.ExpRParamContext ctx);
	/**
	 * Visit a parse tree produced by the {@code strRParam}
	 * labeled alternative in {@link SysY2022Parser#funcRParam}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStrRParam(SysY2022Parser.StrRParamContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mul2}
	 * labeled alternative in {@link SysY2022Parser#mulExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMul2(SysY2022Parser.Mul2Context ctx);
	/**
	 * Visit a parse tree produced by the {@code mul1}
	 * labeled alternative in {@link SysY2022Parser#mulExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMul1(SysY2022Parser.Mul1Context ctx);
	/**
	 * Visit a parse tree produced by the {@code add2}
	 * labeled alternative in {@link SysY2022Parser#addExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdd2(SysY2022Parser.Add2Context ctx);
	/**
	 * Visit a parse tree produced by the {@code add1}
	 * labeled alternative in {@link SysY2022Parser#addExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdd1(SysY2022Parser.Add1Context ctx);
	/**
	 * Visit a parse tree produced by the {@code rel2}
	 * labeled alternative in {@link SysY2022Parser#relExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRel2(SysY2022Parser.Rel2Context ctx);
	/**
	 * Visit a parse tree produced by the {@code rel1}
	 * labeled alternative in {@link SysY2022Parser#relExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRel1(SysY2022Parser.Rel1Context ctx);
	/**
	 * Visit a parse tree produced by the {@code eq1}
	 * labeled alternative in {@link SysY2022Parser#eqExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEq1(SysY2022Parser.Eq1Context ctx);
	/**
	 * Visit a parse tree produced by the {@code eq2}
	 * labeled alternative in {@link SysY2022Parser#eqExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEq2(SysY2022Parser.Eq2Context ctx);
	/**
	 * Visit a parse tree produced by the {@code lAnd2}
	 * labeled alternative in {@link SysY2022Parser#lAndExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLAnd2(SysY2022Parser.LAnd2Context ctx);
	/**
	 * Visit a parse tree produced by the {@code lAnd1}
	 * labeled alternative in {@link SysY2022Parser#lAndExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLAnd1(SysY2022Parser.LAnd1Context ctx);
	/**
	 * Visit a parse tree produced by the {@code lOr1}
	 * labeled alternative in {@link SysY2022Parser#lOrExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLOr1(SysY2022Parser.LOr1Context ctx);
	/**
	 * Visit a parse tree produced by the {@code lOr2}
	 * labeled alternative in {@link SysY2022Parser#lOrExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLOr2(SysY2022Parser.LOr2Context ctx);
	/**
	 * Visit a parse tree produced by {@link SysY2022Parser#constExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstExp(SysY2022Parser.ConstExpContext ctx);
}