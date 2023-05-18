package backend.instructions;

import backend.RISCInstruction;
import backend.operands.Immediate;
import backend.RISCOperand;
import backend.operands.RealRegister;

public class LiInstruction extends RISCInstruction {
    public RISCOperand op1;
    public Immediate imm;

    public LiInstruction(RealRegister r1, Immediate imm) {
        this.operandNum = 2;
        this.op1=r1;
        this.setOperand(op1, 0);;
        this.imm=imm;
        this.setOperand(imm, 1);;
        this.type = ITYPE.li;
    }


    public String emit() {
        return this.type.name()+" "+op1.emit()+','+imm.emit();
    }
}
