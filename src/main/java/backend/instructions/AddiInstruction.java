package backend.instructions;

import backend.RISCInstruction;
import backend.operands.Immediate;
import backend.RISCOperand;
import backend.operands.Register;

public class AddiInstruction extends RISCInstruction {

    public RISCOperand op1;
    public RISCOperand op2;
    public Immediate imm;

    public AddiInstruction(Register r1, Register r2, Immediate imm) {
        this.operandNum = 3;
        this.op1 = r1;
        setOperand(op1, 0);
        this.op2 = r2;
        setOperand(op2, 1);
        this.imm = imm;
        setOperand(imm, 2);
        this.type = ITYPE.addi;
    }


    public String emit() {
//        return this.type.name()+" "+op1.emit()+","+op2.emit()+ ","  + imm.emit();
        return this.type.name() + " " + getOperandAt(0).emit() + "," + getOperandAt(1).emit() + "," + getOperandAt(2).emit();
    }
}
