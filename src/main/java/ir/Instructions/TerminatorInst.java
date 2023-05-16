package ir.Instructions;

import ir.Instruction;
import ir.Type;
import ir.Value;

/**
 * 表示基本块终止的指令
 * 包含Ret,Br,Call
 * 待修改
 */
public class TerminatorInst {
    /**
     * 返回指令
     */
    public static class Ret extends Instruction {
        public Ret() {
            super(Type.VoidType.getType(), Instruction.TAG.RET, 0);
            needName = false;
        }

        public Ret(Value val) {
            super(Type.VoidType.getType(), TAG.RET, 1);
            this.setOperand(val, 0);
            needName = false;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ret ");
            if (this.getNumOP() == 1) {
                sb.append(getOperandAt(0).type + " " + getOperandAt(0).name);
            } else {
                sb.append("void ");
            }
            return sb.toString();
        }
    }
}
