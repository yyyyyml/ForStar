package backend.instruction;

import backend.RISCInstruction;
import backend.riscvalues.Immediate;
import backend.riscvalues.RISCOperand;
import backend.riscvalues.RealRegister;
import ir.Instruction;

public class LiInstruction extends RISCInstruction {

    public Immediate imm;
    public RISCOperand op1;

    public LiInstruction(Instruction iRin) {
        super(iRin);
    }

    public LiInstruction(RealRegister r1, Immediate imm) {
        this.op1=r1;
        this.imm=imm;
    }


    public String emit() {
        return this.type.name()+" "+op1.emit()+','+imm.emit();
    }
}
