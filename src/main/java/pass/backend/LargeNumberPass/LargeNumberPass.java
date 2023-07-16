package pass.backend.LargeNumberPass;

import backend.*;
import backend.instructions.AddInstruction;
import backend.instructions.LiInstruction;
import backend.operands.Immediate;
import backend.operands.Memory;
import backend.operands.RealRegister;
import pass.backend.BaseBackendPass;

import java.util.LinkedList;

public class LargeNumberPass implements BaseBackendPass {
    public int position = 0; // 表示当前t0的值为s0加多少

    @Override
    public void run(RISCModule riscModule) {
        for (RISCFunction riscFunc : riscModule.getFunctionList()) {
            if (riscFunc.isBuildIn) continue;
            if (riscFunc.stackSize < 2048) continue;
            for (RISCBasicBlock riscBB : riscFunc.getBasicBlockList()) {
                LinkedList<RISCInstruction> riscInstList = riscBB.getInstructionList();
                for (int instIndex = 0; instIndex < riscInstList.size(); instIndex++) {
                    RISCInstruction riscInst = riscInstList.get(instIndex);
                    if (!riscInst.isNumber12bit()) continue; // 没有12bit数的不需要处理
                    LinkedList<RISCOperand> operandList = riscInst.getOperandList();
                    for (int opIndex = 0; opIndex < operandList.size(); opIndex++) {
                        var riscOp = operandList.get(opIndex);
                        int opPosition = riscOp.getPosition();

                        // 如果是地址
                        if (riscOp.isMemory()) {

                            Memory memOp = (Memory) riscOp; // 转换类型
                            int imm = memOp.getOffset(); // s0/sp的原始偏移量

                            // 如果合法，不用处理
                            if (isLegal(imm, 0)) {
                                continue;
                            } else {

                                if (isLegal(imm, this.position)) { // 直接用t0
                                    int offset = imm - this.position; // 新的偏移量
                                    memOp.setOffset(offset); // 设置新的偏移量
                                    memOp.basicAddress = new RealRegister(0, 9); // t0为基地址

                                } else { //需要更新t0
                                    this.position = imm; // 修改t0
                                    var t0 = new RealRegister(0, 9);

                                    RISCInstruction liInst = new LiInstruction(t0, new Immediate(imm));
                                    RISCInstruction addInst = new AddInstruction(t0, t0, memOp.basicAddress);
                                    memOp.setOffset(0); // 设置新的偏移量
                                    memOp.basicAddress = t0;
                                    riscInstList.add(instIndex, addInst);
                                    riscInstList.add(instIndex, liInst);
                                    instIndex += 2; // 跳过加在前面的指令

                                }
                            }

                        }
                        // 如果是立即数
                        else if (riscOp.isImmediate()) {

                            int imm = ((Immediate) riscOp).getVal(); // 获取值
                            // 这个操作数是数字且大于2047或小于-2048
                            if (isLegal(imm, 0)) {
                                continue;
                            } else {

                                // 把对应的i指令转为r指令，如addi->add
                                if (riscInst.type == RISCInstruction.ITYPE.addi) {
                                    var t1 = new RealRegister(1, 9);
                                    RISCInstruction liInst = new LiInstruction(t1, new Immediate(imm));
                                    var op1 = riscInst.getOperandAt(0);
                                    var op2 = riscInst.getOperandAt(1);
                                    RISCInstruction addInst = new AddInstruction(op1, op2, t1);
                                    riscInstList.add(instIndex, addInst);
                                    riscInstList.add(instIndex, liInst);

                                    riscInstList.remove(instIndex + 2); // 删掉自己
                                    instIndex++; // 跳过加在前面的指令，相当于+2-1

                                }

                            }
                        }


                    }

                }
            }

        }
    }

    public boolean isLegal(int num, int base) {
        return num - base < 2047 && num - base > -2048;
    }
}
