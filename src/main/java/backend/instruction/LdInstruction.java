package backend.instruction;

import backend.RISCInstruction;
import backend.riscvalues.Memory;
import backend.riscvalues.RISCOperand;
import backend.riscvalues.RealRegister;
import ir.Instruction;

public class LdInstruction extends RISCInstruction {

    public RISCOperand op1;
    public RISCOperand op2;

    public LdInstruction(Instruction iRin) {
        super(iRin);
    }

    public LdInstruction(RealRegister r1, Memory memory) {
        this.op1=r1;
        this.op2=memory;
        this.type = ITYPE.ld;
    }


    public String emit() {
        return this.type.name()+" "+op1.emit()+","+op2.emit();
    }
}
