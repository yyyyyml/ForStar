package ir.values;

import ir.Type;
import ir.User;
import ir.Value;
import ir.types.ArrayType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
/**
 * 常量类 继承自User类
 * 整型常量、浮点常量、数组常量定义在内部
 * <p>
 * TODO:数组待实现
 */
public class Constant extends User {
    public Constant(Type type) {
        super(type);
    }

    public Constant(Type type, int numOp) {
        super(type, numOp);
    }

    public static class ConstantArray extends Constant {
        public ConstantArray(ArrayType arrType, ArrayList<Constant> initList){
            super(arrType, initList.size());
            for (int i = 0; i < initList.size(); i++) {
                Constant elem = initList.get(i);
                System.out.println(elem);
                //if (!elem.isZero()) {
                super.setOperand(elem,i);
                //}
            }

        }

        private static class ConstArrKey {
            public final ArrayType arrType;
            public final ArrayList<Constant> initList;

            public ConstArrKey(ArrayType arrType, ArrayList<Constant> initList) {
                this.arrType = arrType;
                this.initList = initList;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                ConstArrKey that = (ConstArrKey) o;
                return Objects.equals(arrType, that.arrType) && Objects.equals(initList, that.initList);
            }

            @Override
            public int hashCode() {
                return Objects.hash(arrType, initList);
            }
        }

        private static final HashMap<ConstArrKey, Constant.ConstantArray> pool = new HashMap<>();

        public static Constant.ConstantArray get(ArrayType arrType, ArrayList<Constant> initList) {
            // Check type.

            for (Constant elem : initList) {
                if (arrType.getElemType() != elem.getType()) {
                    throw new RuntimeException(
                            "Try to get a ConstArray with different types of constants in the initialized list."
                    );
                }
            }

            // Process initList: remove all zero values at the end.
            /*for (int i = initList.size() - 1; i >= 0; i--) {
                Constant elem = initList.get(i);
                if (!elem.isZero()) {
                    break;
                }
                initList.remove(i);
            }*/

            // Retrieve the instance and return it.
            /*var key = new ConstArrKey(arrType, initList);
            if (pool.containsKey(key)) {
                return pool.get(key);
            }*/

            Constant.ConstantArray newArr = new Constant.ConstantArray(arrType, initList);
            //System.out.println(newArr);
            //pool.put(key, newArr);
            return newArr;
        }

        public Value getElemByIndex(Iterable<Integer> indices) {
            Value elem = this;
            for (Integer idx : indices) {
                if (elem.getType().isArrayType()) {
                    elem = ((Constant.ConstantArray) elem).getOperandAt(idx);
                }
                else {
                    throw new RuntimeException("Depth of indies given is to large.");
                }
            }
            return elem;
        }

        @Override
        public String toString() {
            // e.g. [2 x i32] [i32 1, i32 2] or [2 x i32] zeroinitializer
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append(this.getType()); // "[2 x i32]"

            if (this.isZero()) { // "zeroinitializer"
                strBuilder.append(" zeroinitializer");
            }
            else { // "[i32 1, i32 2]"
                strBuilder.append(" [");
                for (int i = 0; i < this.getNumOP() ; i++) {
                    if (i != 0) {
                        strBuilder.append(", ");
                    }
                    strBuilder.append(this.getOperandAt(i));
//                    System.out.println(this.getOperandAt(i));
                }
                strBuilder.append("]");
            }

            return strBuilder.toString();
        }


    }
    /**
     * 整数常量
     */
    public static class ConstantInt extends Constant {
        private static final ConstantInt zero = new ConstantInt(Type.BoolType.getType(), 0);
        private static final ConstantInt one = new ConstantInt(Type.BoolType.getType(), 1);
        private int val;

        public ConstantInt(Type type, int val) {
            super(type);
            this.val = val;
            this.setName(String.valueOf(this.getVal()));
        }

        public static ConstantInt getConstantInt(int val) {
            return new ConstantInt(Type.IntegerType.getType(), val);
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
            return this.getType().toString() +" "+ this.val;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }
    }

    public static class ConstantFloat extends Constant {
        private float val;

        public ConstantFloat(Type type, float val) {
            super(type);
            this.val = val;
            this.setName(String.valueOf(this.getVal()));
        }

        public static ConstantFloat getConstantFloat(float value) {
            return new ConstantFloat(Type.FloatType.getType(), value);

        }

        public float getVal() {
            return val;
        }
    }

    public boolean isZero() {
        return this == this.getType().getZero();
    }

}
