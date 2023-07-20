package backend.instructions;

import backend.RISCInstruction;
import backend.RISCOperand;

public class FcvtInstruction extends RISCInstruction {

    public RISCOperand op1;
    public RISCOperand op2;
    public boolean isFtoI;

    public FcvtInstruction(RISCOperand reg1, RISCOperand reg2,boolean isFtoI) {
        this.isFtoI = isFtoI;
        this.operandNum = 2;
        this.op1 = reg1;
        this.setOperand(op1, 0);
        this.op2 = reg2;
        this.setOperand(op2, 1);
        this.type = ITYPE.fcvt;

    }

    @Override
    public String emit() {
//        return this.type.name()+" "+op1.emit()+","+op2.emit();
        if(isFtoI) {
            return this.type.name() + ".w.s " + getOperandAt(0).emit() + "," + getOperandAt(1).emit()+",rtz";
        }
        else {
            return this.type.name() + ".s.w " + getOperandAt(0).emit() + "," + getOperandAt(1).emit();
        }
    }
}