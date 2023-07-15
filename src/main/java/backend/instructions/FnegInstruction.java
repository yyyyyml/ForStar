package backend.instructions;

import backend.RISCInstruction;
import backend.RISCOperand;

public class FnegInstruction extends RISCInstruction {

    public RISCOperand op1;
    public RISCOperand op2;

    public FnegInstruction(RISCOperand reg1, RISCOperand reg2) {
        this.operandNum = 2;
        this.op1 = reg1;
        this.setOperand(op1, 0);
        this.op2 = reg2;
        this.setOperand(op2, 1);
        this.type = ITYPE.fneg;

    }

    @Override
    public String emit() {
//        return this.type.name()+" "+op1.emit()+","+op2.emit();
        return this.type.name() + ".s " + getOperandAt(0).emit() + "," + getOperandAt(1).emit();
    }
}