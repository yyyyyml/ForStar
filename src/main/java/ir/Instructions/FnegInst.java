package ir.Instructions;

import ir.Instruction;
import ir.Type;
import ir.Value;

public class FnegInst extends Instruction {
    public FnegInst(Type type, TAG tag, int numOP, Value operand) {
        super(type, tag, numOP);
        this.setOperand(operand, 0);
    }

    @Override
    public String toString() {
        Value op = this.getOperandAt(0);
        // %2 = fneg float %1
        return this.getName() + " = " + "fneg " + op.getType() + " " + op.getName();
    }
}
