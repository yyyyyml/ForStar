package ir.values;

import ir.Type;
import ir.User;

/**
 * 常量类
 * 整型常量、浮点常量、数组常量定义在内部
 * 待修改
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

        public ConstantInt getBool(boolean val) {
            if (val) {
                return one;
            } else {
                return zero;
            }
        }

        @Override
        public String toString() {return "i32 " + this.val;}

        public void setVal(int val) {this.val = val;}

        public int getVal() {return val;}

        public ConstantInt(Type type, int val) {
            super(type);
            this.val = val;
            this.name = String.valueOf(this.getVal());
        }

        public static ConstantInt getConstantInt(int val) {
            return new ConstantInt(Type.IntegerType.getType(), val);
        }
    }
}
