package ir;

import ir.values.BasicBlock;
import util.IList;

/**
 * 指令类 各种指令的父类 继承于User类
 * 指令可以有操作数，操作数也是Value
 */
public class Instruction extends User {
    public IList.INode<Instruction, BasicBlock> node;
    public boolean needName = true;
    private TAG tag;

    public Instruction(Type type, TAG tag, int numOP) {
        super(type, numOP);
        this.tag = tag;
        this.node = new IList.INode<>(this, null, null);
    }

    public TAG getTag() {
        return this.tag;
    }

    public BasicBlock getBB() {
        return this.node.getParentList().getVal();
    }

    public boolean isBinary() {
        return this.tag.ordinal() <= TAG.OR.ordinal()
                && this.tag.ordinal() <= TAG.GE.ordinal();
    }

    public boolean isRelationBinary() {
        return this.tag.ordinal() >= TAG.LT.ordinal()
                && this.tag.ordinal() <= TAG.NE.ordinal();
    }

    public boolean isArithmeticBinary() {
        return this.tag.ordinal() >= TAG.ADD.ordinal()
                && this.tag.ordinal() <= TAG.DIV.ordinal();
    }

    public boolean isLogicalBinary() {
        return this.tag.ordinal() >= TAG.LT.ordinal()
                && this.tag.ordinal() <= TAG.OR.ordinal();
    }

    public boolean isTerminator() {
        return this.tag.ordinal() >= TAG.BR.ordinal()
                && this.tag.ordinal() <= TAG.RET.ordinal();
    }

    public boolean isMemory() {
        return this.tag.ordinal() >= TAG.ALLOCA.ordinal()
                && this.tag.ordinal() <= TAG.PHI.ordinal();
    }

    //判断当前指令是什么
    public boolean isADD() {
        return this.tag == TAG.ADD;
    }

    public boolean isSUB() {
        return this.tag == TAG.SUB;
    }

    public boolean isMUL() {
        return this.tag == TAG.MUL;
    }

    public boolean isDIV() {
        return this.tag == TAG.DIV;
    }

    public boolean isFADD() {
        return this.tag == TAG.FADD;
    }

    public boolean isFSUB() {
        return this.tag == TAG.FSUB;
    }

    public boolean isFMUL() {
        return this.tag == TAG.FMUL;
    }

    public boolean isFDIV() {
        return this.tag == TAG.FDIV;
    }

    public boolean isLT() {
        return this.tag == TAG.LT;
    }

    public boolean isLE() {
        return this.tag == TAG.LE;
    }

    public boolean isGE() {
        return this.tag == TAG.GE;
    }

    public boolean isGT() {
        return this.tag == TAG.GT;
    }

    public boolean isEQ() {
        return this.tag == TAG.EQ;
    }

    public boolean isNE() {
        return this.tag == TAG.NE;
    }

    public boolean isFLT() {
        return this.tag == TAG.FLT;
    }

    public boolean isFLE() {
        return this.tag == TAG.FLE;
    }

    public boolean isFGE() {
        return this.tag == TAG.FGE;
    }

    public boolean isFGT() {
        return this.tag == TAG.FGT;
    }

    public boolean isFEQ() {
        return this.tag == TAG.FEQ;
    }

    public boolean isFNE() {
        return this.tag == TAG.FNE;
    }

    public boolean isAND() {
        return this.tag == TAG.AND;
    }

    public boolean isOR() {
        return this.tag == TAG.OR;
    }

    public boolean isGEP() {
        return this.tag == TAG.GEP;
    }

    public boolean isLOAD() {
        return this.tag == TAG.LOAD;
    }

    public boolean isSTORE() {
        return this.tag == TAG.STORE;
    }

    public boolean isPHI() {
        return this.tag == TAG.PHI;
    }

    public boolean isRET() {
        return this.tag == TAG.RET;
    }

    public boolean isBR() {
        return this.tag == TAG.BR;
    }

    public enum TAG {
        //ArithmeticBinary i32
        ADD, // +
        SUB, // -
        MUL, // *
        DIV, // /
        // ArithmeticBinary float
        FADD, // +
        FSUB, // -
        FMUL, // *
        FDIV, // /
        // RelationBinary i32
        LT, // <
        LE, // <=
        GE, // >=
        GT, // >
        EQ, // ==
        NE, // !=
        // RelationBinary float
        FLT, // <
        FLE, // <=
        FGE, // >=
        FGT, // >
        FEQ, // ==
        FNE, // !=
        // LogicalBinary
        AND,// &
        OR, // |
        // Terminator
        BR,
        CALL,
        RET,
        // Memory
        ALLOCA,
        LOAD,
        STORE,
        // Conversion
        ZEXT, // 整数0拓展
        FPTOSI, // float->int
        SITOFP, //int->float
        PTRCAST, //指针转换
        GEP,
        PHI,// 用于mem2reg
        //unary
        FNEG,
    }
}
