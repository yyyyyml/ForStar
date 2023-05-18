package backend.instruction;

import backend.RISCInstruction;
import backend.riscvalues.RISCOperand;
import backend.riscvalues.RealRegister;
import ir.Instruction;

public class MvInstruction extends RISCInstruction {

    public RISCOperand op1;
    public RISCOperand op2;

    public MvInstruction(Instruction iRin) {
        super(iRin);
    }

    public MvInstruction(RealRegister reg1, RealRegister reg2) {
        this.op1=reg1;
        this.op2=reg2;
    }

    @Override
    public String emit() {
        return this.type.name()+" "+op1.emit()+","+op2.emit();
    }
}
