package ir;

import ir.values.BasicBlock;
import util.IList;

/**
 * 指令类 各种指令的父类 继承于User类
 * 指令可以有操作数，操作数也是Value
 */
public class Instruction extends User{
    private TAG tag;
    public IList.INode<Instruction, BasicBlock> node;
    public boolean needName = true;

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
    public enum TAG {
        //binary
        ADD,// +
        SUB,// -
        MUL,// *
        DIV,// /
        LT, // <
        LE, // <=
        GE, // >=
        GT, // >
        EQ, // ==
        NE, // !=
        AND,// &
        OR, // |
        //terminator
        BR,
        CALL,
        RET,
        //memory
        ALLOCA,
        LOAD,
        STORE,
        GEP,
        ZEXT,
        PHI,//用于 mem2reg
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
    public boolean isAdd() {return this.tag == TAG.ADD;}

    public boolean isSub() {return this.tag == TAG.SUB;}

    public boolean isMul() {return this.tag == TAG.MUL;}

    public boolean isDiv() {return this.tag == TAG.DIV;}

    public boolean isLt() {return this.tag == TAG.LT;}

    public boolean isLe() {return this.tag == TAG.LE;}

    public boolean isGe() {return this.tag == TAG.GE;}

    public boolean isGt() {return this.tag == TAG.GT;}

    public boolean isEq() {return this.tag == TAG.EQ;}

    public boolean isNe() {return this.tag == TAG.NE;}

    public boolean isAnd() {return this.tag == TAG.AND;}

    public boolean isOr() {return this.tag == TAG.OR;}

    public boolean isGEP() {return this.tag == TAG.GEP;}

    public boolean isLoad() {return this.tag == TAG.LOAD;}

    public boolean isStore() {return this.tag == TAG.STORE;}

    public boolean isPhi() {return this.tag == TAG.PHI;}

    public boolean isRet() {return this.tag == TAG.RET;}

    public boolean isBr() {return this.tag == TAG.BR;}
}
