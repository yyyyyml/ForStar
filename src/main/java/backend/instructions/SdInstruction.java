package backend.instructions;

import backend.RISCInstruction;
import backend.operands.Memory;
import backend.RISCOperand;
import backend.operands.RealRegister;

public class SdInstruction extends RISCInstruction {
    public RISCOperand op1;
    public RISCOperand op2;

    public SdInstruction(RealRegister r1, Memory memory) {
        this.operandNum = 2;
        this.op1 = r1;
        this.setOperand(op1, 0);
        this.op2 = memory;
        this.setOperand(op2, 1);
        this.type = ITYPE.sd;
    }


    public String emit() {
//        return this.type.name()+" "+op1.emit()+","+op2.emit();
        return this.type.name() + " " + getOperandAt(0).emit() + "," + getOperandAt(1).emit();
    }
}
