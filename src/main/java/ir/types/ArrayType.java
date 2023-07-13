package ir.types;

import ir.Type;
import ir.values.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
/**
 * 数组类型
 * 嵌套形式[2 x[2 x [2 x i32/float]]]
 */
public class ArrayType extends Type {
    private int totalSize; // 总共元素数
    private Type containedType; // 这层数组的元素的类型，可能还是数组，或整数、浮点
    private int size;// 这一层的元素数

    public ArrayType(Type containedType, int size) {
        assert size > 0;

        this.containedType = containedType;
        this.size = size;
        if (containedType.isIntegerType() || containedType.isFloatType()) {
            totalSize = size;
        } else {
            totalSize = ((ArrayType) containedType).totalSize * size;
        }
    }

    public int getTotalSize() {
        return totalSize;
    }

    public Type getContainedType() {
        return containedType;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        // [2 x[2 x [2 x i32]]]
        return "[" + size + " x " + containedType.toString() + "]";
    }

    private static class DimInfoKey {
        public final Type elemType;
        public final int len;


        public DimInfoKey(Type elemType, int len) {
            this.elemType = elemType;
            this.len = len;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DimInfoKey that = (DimInfoKey) o;
            return len == that.len && Objects.equals(elemType, that.elemType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(elemType, len);
        }
    }

    private final static HashMap<DimInfoKey, ArrayType> pool = new HashMap<>();

    public static ArrayType getType(Type elemType, int len) {
        var key = new DimInfoKey(elemType, len);
        if (pool.containsKey(key)) {
            return pool.get(key);
        }

        var newType = new ArrayType(elemType, len);
        pool.put(key, newType);
        return newType;
    }

    public Type getElemType() {
        return containedType;
    }
    public Type getAtomType() {
        Type tmp = this;
        while (tmp.isArrayType()) {
            tmp = ((ArrayType) tmp).getElemType();
        }
        return tmp;
    }

    public int getLen() {
        return size;
    }

    public int getAtomLen() {
        int size = 1;
        for (Type tmp = this; tmp.isArrayType(); tmp = ((ArrayType) tmp).getElemType())
            size *= ((ArrayType) tmp).getLen();
        return size;
    }

    public Constant getZero() {
        System.out.println();
        return new Constant.ConstantArray(this, new ArrayList<Constant>());

    }
}
