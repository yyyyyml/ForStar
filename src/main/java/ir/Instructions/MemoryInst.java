package ir.Instructions;

import ir.Instruction;
import ir.Type;
import ir.Value;
import ir.types.ArrayType;
import ir.types.PointerType;

import java.util.ArrayList;

/**
 * 表示内存有关的指令
 * 包含ALLOCA,LOAD,STORE,GEP,PHI
 * 待实现PHI mem2reg时再说
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
            sb.append(this.getName()).append(" = load ").append(this.getType()).append(", ").
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
                    append(val.getType()).append(" ").append(val.getName()).append(", ").
                    append(pointer.getType()).append(" ").append(pointer.getName());
            return sb.toString();
        }
    }

    /**
     * GEP指令
     */
    public static class GEP extends Instruction {
        private final Type RetType; // 这条指令的Value指向的Value的类型

        public GEP(Value ptr, ArrayList<Value> indices) {
            super(new PointerType(getRetType(ptr, indices)), TAG.GEP, indices.size() + 1);
            setOperand(ptr, 0);
            for (int i = 0; i < indices.size(); i++) {
                setOperand(indices.get(i), i + 1);
            }
            RetType = getRetType(ptr, indices);
        }

        // 遍历一下，确定GEP指令的类型是什么，即它是个什么类型的指针，一层一层找
        private static Type getRetType(Value ptr, ArrayList<Value> indices) {
            assert ptr.getType().isPointerType();

            Type retType = ((PointerType) ptr.getType()).getPointedType();

            if (retType.isIntegerType() || retType.isFloatType())
                return retType;
            else if (retType.isArrayType()) {
                for (int i = 1; i < indices.size(); i++) {
                    retType = ((ArrayType) retType).getContainedType();
                }
                return retType;
            }
            return retType;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            // %2 = getelementptr [2 x i32], [2 x i32]* %1, i64 0, i64 1
            Value ptr = getOperandAt(0); // 第一个操作数 指针
            sb.append(this.getName()).append("= getelementptr ").
                    append(((PointerType) ptr.getType()).getPointedType()).append(",")
                    .append(ptr.getType()).append(" ").append(ptr.getName());
            // , i64 0, i64 1
            for (int i = 1; i < getNumOP(); i++) {
                sb.append(", ").append(getOperandAt(i).getType()).append(" ").append(getOperandAt(i).getName());
            }
            return sb.toString();
        }

    }

    /**
     * phi指令
     * 等到mem2reg再完成
     */
    public static class Phi extends Instruction {
        public Phi(Type type, int numOP) {
            super(type, TAG.PHI, numOP);
        }

    }
}
