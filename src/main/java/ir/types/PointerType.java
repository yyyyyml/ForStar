package ir.types;

import ir.Type;

/**
 * 指针类型 gv,alloca,getElementPtr 指令都是指针类型的
 * 包含被指的Value的类型
 */
public class PointerType extends Type {
    private Type pointedType;

    public static PointerType getPointerType(Type pointedType){return new PointerType(pointedType);}

    public PointerType(Type pointedType) {this.pointedType = pointedType;}

    public Type getPointedType() {return pointedType;}

    @Override
    public String toString() {
        return pointedType.toString() + "* ";
    }
}
