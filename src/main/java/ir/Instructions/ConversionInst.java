package ir.Instructions;

import ir.Instruction;
import ir.Type;
import ir.Value;
import ir.types.PointerType;

/**
 * 表示转换有关的指令
 * 包含ZEXT,FPTOSI,SITOFP,PTRCAST
 * 不太清楚是不是这样用PTRCAST
 */
public class ConversionInst {
    /**
     * 整数的0拓展
     */
    public static class Zext extends Instruction {
        public Zext(Value cur) {
            super(Type.IntegerType.getType(), TAG.ZEXT, 1);
            this.setOperand(cur, 0);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            // "%1 = zext i1 %0 to i32"
            sb.append(this.getName()).append(" = zext i1 ").
                    append(this.getOperandAt(0).getName()).append(" to i32");
            return sb.toString();
        }
    }

    /**
     * 浮点转整数
     */
    public static class Fptosi extends Instruction {
        public Fptosi(Value cur) {
            super(Type.IntegerType.getType(), TAG.FPTOSI, 1);
            this.setOperand(cur, 0);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            // "%1 = fptosi float %0 to i32"
            sb.append(this.getName()).append(" = fptosi float ").
                    append(this.getOperandAt(0).getName()).append(" to i32");
            return sb.toString();
        }
    }


    /**
     * 整数转浮点
     */
    public static class Sitofp extends Instruction {
        public Sitofp(Value cur) {
            super(Type.FloatType.getType(), TAG.SITOFP, 1);
            this.setOperand(cur, 0);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            // "%1 = sitofp i32 %0 to float"
            sb.append(this.getName()).append(" = sitofp i32 ").
                    append(this.getOperandAt(0).getName()).append(" to float");
            return sb.toString();
        }
    }

    /**
     * 指针转换 整数指针转浮点指针？
     */
    public static class Ptrcast extends Instruction {
        public Ptrcast(Value cur) {
            super(new PointerType(Type.FloatType.getType()), TAG.PTRCAST, 1);
            this.setOperand(cur, 0);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            // "%1 = ptrcast i32* %0 to float*"
            sb.append(this.getName()).append(" = ptrcast i32* ").
                    append(this.getOperandAt(0).getName()).append(" to float*");
            return sb.toString();
        }
    }
}
