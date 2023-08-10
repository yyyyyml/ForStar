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
 *
 */
public class Constant extends User {
    public Constant(Type type) {
        super(type);
    }

    public Constant(Type type, int numOp) {
        super(type, numOp);
    }

    public static class ConstantArray extends Constant {
        public boolean isAllZero = false;
        public ConstantArray(ArrayType arrType, ArrayList<Constant> initList){
            super(arrType, initList.size());

            if(initList.size()>0&&isZero(initList)) isAllZero = true;
            for (int i = 0; i < initList.size(); i++) {
                Constant elem = initList.get(i);
/*               if (!elem.isZero()) {*/
                super.setOperand(elem,i);
/*               }*/
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
                    throw new RuntimeException("类型错误");
                }
            }


            Constant.ConstantArray newArr = new Constant.ConstantArray(arrType, initList);

            return newArr;
        }

        public Value getElemByIndex(Iterable<Integer> indices) {
            Value elem = this;
            for (Integer idx : indices) {
                if (elem.getType().isArrayType()) {
                    elem = ((Constant.ConstantArray) elem).getOperandAt(idx);
                }
                else {
                    throw new RuntimeException("getElemByIndex错误");
                }
            }
            return elem;
        }

        @Override
        public String toString() {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append(this.getType()); // "[2 x i32]"

            if (isAllZero) { // "zeroinitializer"
                strBuilder.append(" zeroinitializer");
                System.out.println("0初始化");
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
            this.setName(String.valueOf(this.getVal()));
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ConstantInt constInt)
                return val == constInt.getVal();
            else return false;
        }
    }

    public static class ConstantFloat extends Constant {
        private float val;

        public ConstantFloat(Type type, float val) {
            super(type);
            this.val = val;
//            this.setName(String.valueOf(this.getVal()));
            this.setName("0x" + Long.toHexString(Double.doubleToLongBits(val)));
        }

        public static ConstantFloat getConstantFloat(float value) {
            return new ConstantFloat(Type.FloatType.getType(), value);
        }

        @Override
        public String toString() {

            int Value = Float.floatToRawIntBits(this.val);
            String hexString = Integer.toHexString(Value);
            return this.getType().toString() + " " + hexString;
        }

        public float getVal() {
            return val;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ConstantFloat constFloat)
                return val == constFloat.getVal();
            else return false;
        }
    }

    public boolean isZero() {
//        System.out.println("------------------"+this.getType());
//        System.out.println(this == this.getType().getZero());
        return this == this.getType().getZero();

    }
    public boolean isZero(ArrayList <Constant > initlist) {
        boolean isZ = true;
        for(int i = initlist.size()-1;i>=0;i--)
        {
            if (initlist.get(i).getName().equals("0")||initlist.get(i).getName().equals("0.0")) {
                continue;
            }
            else{
                isZ = false;
                break;
            }
        }

        return isZ;
    }

}
