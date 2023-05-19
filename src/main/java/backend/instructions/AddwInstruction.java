package backend.instructions;

import backend.RISCInstruction;
import backend.RISCOperand;
import backend.operands.Immediate;
import backend.operands.Register;

public class AddwInstruction extends RISCInstruction {
    public RISCOperand op1;
    public RISCOperand op2;
    public RISCOperand op3;

    public AddwInstruction(RISCOperand o1, RISCOperand o2, RISCOperand o3) {
        this.operandNum = 3;
        this.op1 = o1;
        this.setOperand(op1, 0);
        this.op2 = o2;
        this.setOperand(op2, 1);
        this.op3 = o3;
        this.setOperand(op3, 2);
        this.type = ITYPE.addw;
    }


    public String emit() {
        return this.type.name()+" "+op1.emit()+","+op2.emit()+","+op3.emit();
    }
}
