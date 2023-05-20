package ir.types;

import ir.Type;

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
}
