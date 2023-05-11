package ir;

import ir.types.*;

/**
 * 对于没有其他属性的Type,
 * 直接在Type类中构造单例,
 * 使用时通过getType获取,
 * ArrayType,FunctionType,PointerType等有更复杂的属性的Type,
 * 在Types文件夹中单独定义
 */
public class Type {

    private static final ir.Type.IntegerType i32 = new ir.Type.IntegerType();
    public static ir.Type.IntegerType getI32() {
        return i32;
    }

    // NoneType
    public static class NonType extends Type {
        private NonType() {}

        private static NonType type = new NonType();

        public static NonType getType() {return type;}
    }

    // VoidType
    public static class VoidType extends Type {
        private VoidType() {}

        private static VoidType type = new VoidType();

        public static VoidType getType() {return type;}

        @Override
        public String toString() {return "void";}
    }

    // LabelType
    public static class LabelType extends Type {
        private LabelType() {}

        private static LabelType type = new LabelType();

        public static LabelType getType() {return type;}

        @Override
        public String toString() {return "label ";}
    }

    // IntegerType
    public static class IntegerType extends Type {
        private IntegerType() {}

        private static IntegerType type = new IntegerType();

        public static IntegerType getType() {return type;}

        @Override
        public String toString() {return "i32 ";}
    }

    // BoolType
    public static class BoolType extends Type {
        private BoolType() {}

        private static BoolType type = new BoolType();

        public static BoolType getType() {return type;}

        @Override
        public String toString() {return "i1 ";}
    }

    // FloatType
    public static class FloatType extends Type {
        private FloatType() {}

        private static FloatType type = new FloatType();

        public static FloatType getType() {return type;}

        @Override
        public String toString() {return "float ";}
    }

    public boolean isNonType() {
        return (this instanceof NonType);
    }

    public boolean isVoidType() {
        return (this instanceof VoidType);
    }

    public boolean isLabelType() {
        return (this instanceof LabelType);
    }

    public boolean isFunctionType() {
        return (this instanceof FunctionType);
    }

    public boolean isPointerType() {
        return (this instanceof PointerType);
    }

    public boolean isArrayType() {
        return (this instanceof ArrayType);
    }

    public boolean isFloatType() {
        return (this instanceof FloatType);
    }

    public boolean isIntegerType() {
        return (this instanceof IntegerType);
    }

    public boolean isBoolType() {
        return (this instanceof BoolType);
    }
}
