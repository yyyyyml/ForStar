package backend;

import ir.Instruction;

public class RISCinstruction {
    public enum ITYPE{
        addi,
        slti,
        sltiu,
        xori,
        ori,
        andi,
        slli,
        srli,
        srai,
        add,
        sub,
        sll,
        slt,
        sltu,
        xor,
        srl,
        sra,
        or,
        and,
        mul,
        div,
        rem,
        lb,
        lh,
        lw,
        lbu,
        lhu,
        lwu,
        sb,
        sh,
        sw,
        jr,
        nop,
        sd,
        ld
    }



    public ITYPE type;
    public String emit(){return "\t"+type.name()+"\n";}
    public RISCinstruction(Instruction iRin) {
        ;
    }
    public RISCinstruction() {
        ;
    }
}
