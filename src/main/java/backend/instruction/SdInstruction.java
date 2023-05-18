package backend.instruction;

import backend.RISCInstruction;
import backend.riscvalues.Memory;
import backend.riscvalues.RISCOperand;
import backend.riscvalues.RealRegister;
import ir.Instruction;

public class SdInstruction extends RISCInstruction {
    public RISCOperand op1;
    public RISCOperand op2;

    public SdInstruction(Instruction iRin) {
        super(iRin);
    }

    public SdInstruction(RealRegister r1, Memory memory) {
        this.op1=r1;
        this.op2=memory;
        this.type = ITYPE.sd;
    }


    public String emit() {
        return this.type.name()+" "+op1.emit()+","+op2.emit();
    }
}
