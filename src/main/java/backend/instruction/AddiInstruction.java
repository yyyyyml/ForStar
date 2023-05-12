package backend.instruction;

import backend.RISCinstruction;
import backend.Riscvalues.Register;
import ir.Instruction;

public class AddiInstruction extends RISCinstruction {

    public Register reg1;
    public Register reg2;
    public int imm;

    public AddiInstruction(Instruction iRin) {
        super(iRin);
    }

    public AddiInstruction(int r1, int r2, int imm) {
        this.reg1=new Register(r1);
        this.reg2=new Register(r2);
        this.imm=imm;
        this.type=ITYPE.addi;
    }


    public String emit() {
        return this.type.name()+" "+reg1.type.name()+","+reg2.type.name()+Integer.toString(imm);
    }
}
