lexer grammar Lex;

//INT

IntConst
    : DecIntConst
    | OctIntConst
    | HexIntConst
    ;

fragment DecIntConst : [1-9] [0-9]*;

fragment OctIntConst : '0' [0-7]*;

fragment HexIntConst : ('0x'|'0X') HexDigitSeq;

fragment HexDigitSeq : [0-9a-fA-F]+;

//FLOAT

FloatConst
    : DecFloatConst
    | HexFloatConst
    ;

fragment
DecFloatConst
    : FracConst Exp? FloatSuffix?
    | DigitSeq Exp FloatSuffix?
    ;

fragment
HexFloatConst
    : ('0x'|'0X') (HexDigitSeq|HexFracConst) BinaryExp FloatSuffix?
    ;

fragment
FracConst
    : DigitSeq? '.' DigitSeq
    | DigitSeq '.'
    ;

fragment Exp : [eE] Sign? DigitSeq;

fragment Sign : [+-];

fragment DigitSeq : [0-9]+;

fragment
HexFracConst
    : HexDigitSeq? '.' HexDigitSeq
    | HexDigitSeq '.'
    ;

fragment BinaryExp : [pP] Sign? DigitSeq;

fragment FloatSuffix : [flFL];


Ident : [a-zA-Z_] [a-zA-Z_0-9]* ;

STRING : '"'(ESC|.)*?'"';
fragment ESC : '\\"'|'\\\\';

LINE_COMMENT : '//' .*? '\r'? '\n' -> skip;
COMMENT : '//' .*? '\n' -> skip;

WS : [ \t\n\r]+ -> skip;

