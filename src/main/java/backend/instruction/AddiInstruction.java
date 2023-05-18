package backend.instruction;

import backend.RISCInstruction;
import backend.riscvalues.Immediate;
import backend.riscvalues.RISCOperand;
import backend.riscvalues.RealRegister;
import ir.Instruction;

public class AddiInstruction extends RISCInstruction {

    public RISCOperand op1;
    public RISCOperand op2;
    public Immediate imm;

    public AddiInstruction(Instruction iRin) {
        super(iRin);
    }

    public AddiInstruction(RealRegister r1, RealRegister r2, Immediate imm) {
        this.op1=r1;
        this.op2=r2;
        this.imm=imm;
        this.type=ITYPE.addi;
    }


    public String emit() {
        return this.type.name()+" "+op1.emit()+","+op2.emit()+","+imm.val;
    }
}
