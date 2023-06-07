package ir.Instructions;

import ir.Instruction;
import ir.Type;
import ir.Value;

/**
 * 二元操作指令，加减乘除比较指令
 * ADD OR需要实现吗?
 */
public class BinaryInst extends Instruction {
    public BinaryInst(Type type, TAG tag, Value left, Value right) {
        super(type, tag, 2);
        this.setOperand(left, 0);
        this.setOperand(right, 1);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        // %10 = fadd float %8, %9
        sb.append(this.getName()).append(" = ");
        sb.append(switch (this.getTag()) {
            // i32
            case ADD -> "add i32 ";
            case SUB -> "sub i32 ";
            case MUL -> "mul i32 ";
            case DIV -> "sdiv i32 ";
            // float
            case FADD -> "fadd float ";
            case FSUB -> "fsub float ";
            case FMUL -> "fmul float ";
            case FDIV -> "fdiv float ";
            // i32
            case LT -> "icmp slt " + this.getOperandAt(0).getType() + " ";
            case LE -> "icmp sle " + this.getOperandAt(0).getType() + " ";
            case GE -> "icmp sge " + this.getOperandAt(0).getType() + " ";
            case GT -> "icmp sgt " + this.getOperandAt(0).getType() + " ";
            case EQ -> "icmp eq " + this.getOperandAt(0).getType() + " ";
            case NE -> "icmp ne " + this.getOperandAt(0).getType() + " ";
            // float
            case FLT -> "fcmp olt " + this.getOperandAt(0).getType() + " ";
            case FLE -> "fcmp ole " + this.getOperandAt(0).getType() + " ";
            case FGE -> "fcmp oge " + this.getOperandAt(0).getType() + " ";
            case FGT -> "fcmp ogt " + this.getOperandAt(0).getType() + " ";
            case FEQ -> "fcmp oeq " + this.getOperandAt(0).getType() + " ";
            case FNE -> "fcmp one " + this.getOperandAt(0).getType() + " ";
            // Error.
            default -> "";
        });
        sb.append(this.getOperandAt(0).getName()).append(", ").append(this.getOperandAt(1).getName());
        return sb.toString();
    }
}
