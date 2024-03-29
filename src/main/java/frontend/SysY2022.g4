grammar SysY2022;
import Lex;

compUnit : (decl | funcDef)* EOF;

decl : constDecl | varDecl;

constDecl : 'const' bType constDef (',' constDef)* ';';

bType : ('int' | 'float');

constDef
    : Ident '=' constInitVal                            # scalarConstDef
    | Ident ('[' constExp ']')+ '=' constInitVal        # arrConstDef
    ;

constInitVal
    : constExp                                          # scalarConstInitVal
    | '{' (constInitVal (',' constInitVal)*)? '}'       # arrConstInitVal
    ;

varDecl : bType varDef (',' varDef)* ';';

varDef
    : Ident ('=' initVal)?                              # scalarVarDef
    | Ident ('[' constExp ']')+ ('=' initVal)?          # arrVarDef
    ;

initVal
    : exp                                               # scalarInitVal
    | '{' (initVal (',' initVal)*)? '}'                 # arrInitval
    ;

funcDef : funcType Ident '(' (funcFParams)? ')' block;

funcType : ('void' | 'int' | 'float');

funcFParams : funcFParam (',' funcFParam)*;

funcFParam
    : bType Ident                                       # scalarFuncFParam
    | bType Ident '[' ']' ('[' exp ']')*                # arrFuncFParam
    ;

block : '{' (blockItem)* '}';

blockItem : decl | stmt;

stmt
    : lVal '=' exp ';'                                  # assignment
    | exp? ';'                                          # expStmt
    | block                                             # blockStmt
    | 'if' '(' cond ')' stmt                            # ifStmt
    | 'if' '(' cond ')' stmt 'else' stmt                # ifelseStmt
    | 'while' '(' cond ')' stmt                         # whileStmt
    | 'break' ';'                                       # breakStmt
    | 'continue' ';'                                    # continueStmt
    | 'return' (exp)? ';'                               # returnStmt
    ;

exp : addExp;

cond : lOrExp;

lVal
    : Ident                                             # scalarLVal
    | Ident ('[' exp ']')+                              # arrLVal
    ;

primaryExp
    : '(' exp ')'                                       # primaryExp1
    | lVal                                              # primaryExp2
    | number                                            # primaryExp3
    ;

number : (IntConst | FloatConst);

unaryExp
    : primaryExp                                        # unaryExp1
    | Ident '(' (funcRParams)? ')'                      # unaryExp2
    | unaryOp unaryExp                                  # unaryExp3
    ;

unaryOp : ('+' | '-' | '!');

funcRParams : funcRParam (',' funcRParam)*;

funcRParam
    : exp                                               # expRParam
    | STRING                                            # strRParam
    ;

mulExp
    : unaryExp (('*' | '/' | '%') unaryExp)*
    ;

addExp
    : mulExp (('+' | '-') mulExp)*
    ;

relExp
    : addExp (('<' | '>' | '<=' | '>=') addExp)*
    ;

eqExp
    : relExp (('==' | '!=') relExp)*
    ;

lAndExp
    : eqExp ('&&' eqExp)*
    ;

lOrExp
    : lAndExp ('||' lAndExp)*
    ;

constExp : addExp;

