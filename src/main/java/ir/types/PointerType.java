package ir.types;

import ir.Type;
import java.util.HashMap;
import java.util.Objects;
/**
 * 指针类型 gv,alloca,getElementPtr 指令都是指针类型的
 * 包含被指的Value的类型
 */
public class PointerType extends Type {
    private Type pointedType;
    private Type rootType;

    public Type getRootType() {
        return rootType;
    }

    private final int depth;
    public PointerType(Type pointedType) {

        this.pointedType = pointedType;
        if(pointedType.isPointerType()) {
            this.rootType = ((PointerType) pointedType).getRootType();
            this.depth = ((PointerType) pointedType).depth + 1;
        }
        else {
            this.rootType = pointedType;
            this.depth = 1;
        }
    }
    private static HashMap<PointerKey, PointerType> pool = new HashMap<>();
    public static PointerType getPointerType(Type pointedType) {
        return new PointerType(pointedType);
    }

    public Type getPointedType() {
        return pointedType;
    }

    private static class PointerKey {
        public int depth;

        public Type rootType;

        public PointerKey(Type pointeeType) {
            this.depth = pointeeType.isPointerType() ? ((PointerType) pointeeType).depth + 1 : 1;
            this.rootType = pointeeType.isPointerType() ? ((PointerType) pointeeType).getRootType() : pointeeType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PointerKey that = (PointerKey) o;
            return depth == that.depth && Objects.equals(rootType, that.rootType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(depth, rootType);
        }
    }

    public static PointerType getType(Type pointedType) {
        var key = new PointerKey(pointedType);
        if (pool.containsKey(key)) {
            return pool.get(key);
        }

        var newType = new PointerType(pointedType);
        pool.put(key, newType);
        return newType;
    }

    @Override
    public String toString() {
        return pointedType.toString() + "*";
    }
}
