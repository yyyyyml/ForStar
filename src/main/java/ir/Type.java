package ir;

import ir.types.ArrayType;
import ir.types.FunctionType;
import ir.types.PointerType;
import ir.values.Constant;

/**
 * 对于没有其他属性的Type,
 * 直接在Type类中构造单例,
 * 使用时通过getType获取,
 * ArrayType,FunctionType,PointerType等有更复杂的属性的Type,
 * 在Types文件夹中单独定义
 */
public class Type {


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

    public Constant getZero() {
        if (this.isIntegerType()) {
            return IntegerType.getIntZero();
        } else if (this.isFloatType()) {
            return FloatType.getFloatZero();
        }
//        else if(this.isArrayType()) {
//            return ((ArrayType) this).getArrayZero();
//        }
        else return null;
    }
    // NoneType
    public static class NonType extends Type {
        private static NonType type = new NonType();

        private NonType() {
        }

        public static NonType getType() {
            return type;
        }
    }

    // VoidType
    public static class VoidType extends Type {
        private static VoidType type = new VoidType();

        private VoidType() {
        }

        public static VoidType getType() {
            return type;
        }

        @Override
        public String toString() {
            return "void";
        }
    }

    // LabelType
    public static class LabelType extends Type {
        private static LabelType type = new LabelType();

        private LabelType() {
        }

        public static LabelType getType() {
            return type;
        }

        @Override
        public String toString() {
            return "label ";
        }
    }

    // IntegerType
    public static class IntegerType extends Type {
        private static IntegerType type = new IntegerType();

        private IntegerType() {
        }

        public static IntegerType getType() {
            return type;
        }

        @Override
        public String toString() {
            return "i32";
        }
        public static Constant getIntZero(){
            return Constant.ConstantInt.getConstantInt(0);
        }
    }

    // BoolType
    public static class BoolType extends Type {
        private static BoolType type = new BoolType();

        private BoolType() {
        }

        public static BoolType getType() {
            return type;
        }

        @Override
        public String toString() {
            return "i1";
        }
    }

    // FloatType
    public static class FloatType extends Type {
        private static FloatType type = new FloatType();

        private FloatType() {
        }

        public static FloatType getType() {
            return type;
        }

        @Override
        public String toString() {
            return "float";
        }

        public static Constant getFloatZero(){
            return Constant.ConstantFloat.getConstantFloat(0);
        }
    }

}
