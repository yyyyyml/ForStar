package backend;

import java.util.LinkedList;

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
        ld,
        li,
        mv
    }


    private LinkedList<RISCOperand> operandList;
    public int operandNum = 0;
    public ITYPE type;
    
    public RISCInstruction() {
        this.operandList = new LinkedList<>();
    }

    public int getOperandNum() {return operandNum;}

    public LinkedList<RISCOperand> getOperandList() {return this.operandList;}

    public RISCOperand getOperandAt(int i){
        for(RISCOperand riscOperand : operandList) {
            if (riscOperand.getPosition() == i) {
                return riscOperand;
            }
        }
        return null;
    }

    // set之前必须先定义operandNum
    public void setOperand(RISCOperand riscOperand, int i) {
        assert i < operandNum && i >= 0;

        // 这个位置有数 删掉
        for (RISCOperand riscOp : operandList) {
            if (riscOp.getPosition() == i) {
                operandList.remove(riscOp);
                break;
            }
        }

        // 没有 加上去
        this.addInOperandList(riscOperand);
        riscOperand.setPosition(i);
    }

    // 在外部不用这个添加操作数，必须用setOperand把顺序一起传进去，才能给操作数设定position
    private void addInOperandList(RISCOperand riscOperand) {
        operandList.add(riscOperand);
    }

    public String emit() {return type.name();}
}
