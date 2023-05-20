package backend;

import java.util.LinkedList;

public class RISCInstruction {
    private LinkedList<RISCOperand> operandList;
    private int id = -1;
    public int operandNum = 0;
    public ITYPE type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
        mv,
        addw,
        subw,
        mulw,
        divw
    }
    
    public RISCInstruction() {
        this.operandList = new LinkedList<>();
    }

    public int getOperandNum() {return operandNum;}

    public LinkedList<RISCOperand> getOperandList() {return this.operandList;}

    public RISCOperand getOperandAt(int i){
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

//    public void setOperand(RISCOperand riscOperand, int i) {
//        assert i < operandNum && i >= 0;
//
//        // 这个位置有数 删掉
//        for (RISCOperand riscOp : operandList) {
//            if (riscOp.getPosition() == i) {
//                operandList.remove(riscOp);
//                break;
//            }
//        }
//
//        // 没有 加上去
//        this.addInOperandList(riscOperand);
//        riscOperand.setPosition(i);
//
//    }

    // 在外部不用这个添加操作数，必须用setOperand把顺序一起传进去，才能给操作数设定position
    private void addInOperandList(RISCOperand riscOperand) {
        operandList.add(riscOperand);
    }

    public String emit() {return type.name();}
}
