package backend;

import ir.values.BasicBlock;

import java.util.LinkedList;

public class RISCInstruction {
    public int operandNum = 0;
    public ITYPE type;
    private LinkedList<RISCOperand> operandList;
    private int id = -1;
    private RISCBasicBlock parentBlock;

    public RISCInstruction() {
        this.operandList = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOperandNum() {
        return operandNum;
    }

    public boolean isDef(int position) {
        return position == 0;
    }

    public LinkedList<RISCOperand> getOperandList() {
        return this.operandList;
    }

    public RISCOperand getOperandAt(int i) {
        return operandList.get(i);
//        for(RISCOperand riscOperand : operandList) {
//
//            if (riscOperand.getPosition() == i) {
////                System.out.println(riscOperand.emit()+" "+riscOperand.getPosition());
//                return riscOperand;
//            }
//        }
//        System.out.println("找不到？？？？？？？？？？？？？？？？？");
//        return null;
    }

    // 分配寄存器用的，原地修改
    public void setOpLocal(RISCOperand riscOperand, int i, int position) {
        assert i < operandNum && i >= 0;

        // 替换操作数
        operandList.set(i, riscOperand);
        riscOperand.setPosition(position);
//        System.out.println(riscOperand.getPosition());
    }

    // set之前必须先定义operandNum
    public void setOperand(RISCOperand riscOperand, int i) {
        assert i < operandNum && i >= 0;

        // 如果位置 i 没有元素，则插入新元素
        if (i >= operandList.size()) {
            while (i > operandList.size()) {
                operandList.add(null);  // 在位置 i 前插入空元素
            }
            operandList.add(riscOperand);  // 在位置 i 插入新元素
        } else {
            // 替换操作数
            operandList.set(i, riscOperand);
        }
        riscOperand.setPosition(i);
    }

    public boolean isNumber12bit() {
        return this.type == ITYPE.addi || this.type == ITYPE.xori ||
                this.type == ITYPE.lw || this.type == ITYPE.flw || this.type == ITYPE.ld || this.type == ITYPE.fld ||
                this.type == ITYPE.sw || this.type == ITYPE.fsw || this.type == ITYPE.sd;
    }


    public String emit() {
        return type.name();
    }

    public RISCBasicBlock getParentBlock() {
        return parentBlock;
    }

    public void setParentBlock(RISCBasicBlock parentBlock) {
        this.parentBlock = parentBlock;
    }

    public enum ITYPE {
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
        mv,
        addw,
        subw,
        mulw,
        call,
        lla,
        flw,
        fsw,
        fadd,
        fmul,
        fdiv,
        fsub,
        fmv,
        J,
        j,
        bge,
        bne,
        blt,
        beq,
        ble,
        bgt,
        feq,
        fne,
        fgt,
        fge,
        fle,
        flt,
        fld,
        fcvt,
        fneg,
        seq,
        sle,
        sgt,
        sge,
        seqz,
        sgtz,
        fsd, divw
    }
}
