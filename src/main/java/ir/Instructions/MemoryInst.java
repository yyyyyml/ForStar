package ir.Instructions;

import ir.Instruction;
import ir.Type;
import ir.Value;
import ir.types.PointerType;

/**
 * 表示内存有关的指令
 * 包含ALLOCA,LOAD,STORE,GEP,PHI
 * 待实现GEP,PHI
 */
public class MemoryInst {
    /**
     * alloca指令的类型是指针 需要知道分配的类型 没有操作数
     */
    public static class Alloca extends Instruction {
        public final Type allocatedType;

        public Alloca(Type allocatedType) {
            super(new PointerType(allocatedType), TAG.ALLOCA, 0);
            this.allocatedType = allocatedType;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            // %4 = alloca float
            sb.append(this.getName()).append(" = alloca ").append(allocatedType);
            return sb.toString();
        }
    }

    /**
     * load指令用于从内存读出值，它的类型是i32或float
     */
    public static class Load extends Instruction {

        public Load(Type type, Value v) {
            super(type, TAG.LOAD, 1);
            this.setOperand(v, 0);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Value operand = this.getOperandAt(0);
            // %8 = load float, float* %4
            sb.append(this.getName()).append(" = load ").append(this.getType()).
                    append(", ").
                    append(operand.getType()).append(" ").append(operand.getName());
            return sb.toString();
        }
    }

    /**
     * store 指令用于将值存入内存 不需要名字
     */
    public static class Store extends Instruction {
        public Store(Value val, Value pointer) {
            super(Type.VoidType.getType(), TAG.STORE, 2);
            setOperand(val, 0);
            setOperand(pointer, 1);
            this.needName = false;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Value val = getOperandAt(0);
            Value pointer = getOperandAt(1);
            // store float %0, float* %4
            sb.append("store ").
                    append(val.getType()).append(" ").append(val.getName()).
                    append(", ").
                    append(pointer.getType()).append(" ").append(pointer.getName());
            return sb.toString();
        }
    }
}
