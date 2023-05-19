package backend.instructions;

import backend.RISCInstruction;
import backend.RISCOperand;
import backend.operands.RealRegister;

public class MvInstruction extends RISCInstruction {

    public RISCOperand op1;
    public RISCOperand op2;

    public MvInstruction(RealRegister reg1, RealRegister reg2) {
        this.operandNum = 2;
        this.op1=reg1;
        this.setOperand(op1, 0);
        this.op2=reg2;
        this.setOperand(op2, 1);
        this.type = ITYPE.mv;

    }

    @Override
    public String emit() {
        return this.type.name()+" "+op1.emit()+","+op2.emit();
    }
}