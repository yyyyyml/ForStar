package backend.instructions;

import backend.RISCBasicBlock;
import backend.RISCInstruction;
import backend.RISCOperand;

public class JInstruction extends RISCInstruction {
    public RISCOperand op1;

    public JInstruction(RISCOperand op1, RISCBasicBlock BB) {
        this.operandNum = 1;
        this.op1 = op1;
        this.setOperand(op1, 0);
        this.type = ITYPE.j;
        System.out.println("ADD TO LIST " + op1.emit() );
        BB.nextBlocknameList.add(op1.emit());
    }

    @Override
    public String emit() {
        return this.type.name() + " " + getOperandAt(0).emit();
    }
}
