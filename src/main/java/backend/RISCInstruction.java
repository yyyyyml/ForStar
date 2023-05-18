package backend;

import ir.Instruction;

public class RISCInstruction {
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
    public String emit(){return type.name();}
    public RISCInstruction(Instruction iRin) {
        ;
    }
    public RISCInstruction() {
        ;
    }
}
