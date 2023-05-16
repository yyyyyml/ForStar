package ir.values;

import ir.Type;
import ir.User;

/**
 * 常量类 继承自User类
 * 整型常量、浮点常量、数组常量定义在内部
 *
 * 浮点、数组待实现
 */
public class Constant extends User {
    public Constant(Type type) {
        super(type);
    }

    public Constant(Type type, int numOp) {
        super(type, numOp);
    }

    /**
     * 整数常量
     */
    public static class ConstantInt extends Constant {
        private int val;
        private static final ConstantInt zero = new ConstantInt(Type.BoolType.getType(), 0);
        private static final ConstantInt one = new ConstantInt(Type.BoolType.getType(), 1);

        public ConstantInt(Type type, int val) {
            super(type);
            this.val = val;
            this.name = String.valueOf(this.getVal());
        }

        public ConstantInt getBool(boolean val) {
            if (val) {
                return one;
            } else {
                return zero;
            }
        }

        @Override
        public String toString() {
            return this.type.toString() + this.val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public static ConstantInt getConstantInt(int val) {
            return new ConstantInt(Type.IntegerType.getType(), val);
        }
    }

    public class ConstantFloat extends Constant {
        private final float val;

        private ConstantFloat(float val) {
            super(Type.FloatType.getType());
            this.val = val;
            this.setName("0x" + Long.toHexString(Double.doubleToLongBits(val)));
        }


        public float getVal() {
            return val;
        }
    }
}
