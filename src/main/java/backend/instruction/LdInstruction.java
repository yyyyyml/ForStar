package backend.instruction;

import backend.RISCinstruction;
import backend.Riscvalues.Memory;
import backend.Riscvalues.Register;
import ir.Instruction;

public class LdInstruction extends RISCinstruction {

    public Register reg1;
    public Memory mem1;

    public LdInstruction(Instruction iRin) {
        super(iRin);
    }

    public LdInstruction(int r1, int offset, int basicAddress) {
        this.reg1=new Register(r1);
        this.mem1=new Memory(offset,basicAddress);
        this.type = ITYPE.lw;
    }


    public String emit() {
        return this.type.name()+" "+reg1.type.name()+","+mem1.emit();
    }
}
