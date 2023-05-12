package backend.instruction;

import backend.RISCinstruction;
import backend.Riscvalues.Memory;
import backend.Riscvalues.Register;
import ir.Instruction;

public class SdInstruction extends RISCinstruction {
    public Register reg1;
    public Memory mem;

    public SdInstruction(Instruction iRin) {
        super(iRin);
    }

    public SdInstruction(int r1, int offset, int basicAddress) {
        this.reg1=new Register(r1);
        this.mem=new Memory(offset,basicAddress);
        this.type = ITYPE.sw;
    }


    public String emit() {
        return this.type.name()+" "+reg1.type.name()+","+mem.emit();
    }
}
