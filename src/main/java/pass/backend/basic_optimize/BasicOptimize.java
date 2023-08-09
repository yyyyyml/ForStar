package pass.backend.basic_optimize;

import backend.*;
import backend.instructions.AddInstruction;
import backend.instructions.SlliInstruction;
import backend.instructions.SubwInstruction;
import backend.operands.BigImmediate;
import backend.operands.Immediate;
import backend.operands.RealRegister;
import pass.backend.BaseBackendPass;

import java.util.LinkedList;

public class BasicOptimize implements BaseBackendPass {
    public int position = 0; // 表示当前t0的值为s0加position
    public RealRegister nowBasicAddress;


    @Override
    public void run(RISCModule riscModule) {
        for (RISCFunction riscFunc : riscModule.getFunctionList()) {
            if (riscFunc.isBuildIn) continue;
            for (RISCBasicBlock riscBB : riscFunc.getBasicBlockList()) {
                position = 0;
                LinkedList<RISCInstruction> riscInstList = riscBB.getInstructionList();
                RISCInstruction preInst = null;
                if (riscInstList.size() != 0) {
                    preInst = riscInstList.get(0);
                }
                for (int instIndex = 0; instIndex < riscInstList.size(); instIndex++) {
                    RISCInstruction riscInst = riscInstList.get(instIndex);
                    //删除不必要的ld和sd
                    if (preInst.type == RISCInstruction.ITYPE.ld) {
                        if (riscInst.type == RISCInstruction.ITYPE.sd && preInst.getOperandAt(0) == riscInst.getOperandAt(0) && preInst.getOperandAt(1) == riscInst.getOperandAt(1)) {
                            riscInstList.remove(instIndex);
                            instIndex--;
                            continue;
                        }
                    } else if (preInst.type == RISCInstruction.ITYPE.sd) {
                        if (riscInst.type == RISCInstruction.ITYPE.ld && preInst.getOperandAt(0) == riscInst.getOperandAt(0) && preInst.getOperandAt(1) == riscInst.getOperandAt(1)) {
                            riscInstList.remove(instIndex);
                            instIndex--;
                            continue;
                        }
                    } else if (preInst.type == RISCInstruction.ITYPE.lw) {
                        if (riscInst.type == RISCInstruction.ITYPE.sw && preInst.getOperandAt(0) == riscInst.getOperandAt(0) && preInst.getOperandAt(1) == riscInst.getOperandAt(1)) {
                            riscInstList.remove(instIndex);
                            instIndex--;
                            continue;
                        }
                    } else if (preInst.type == RISCInstruction.ITYPE.sw) {
                        if (riscInst.type == RISCInstruction.ITYPE.lw && preInst.getOperandAt(0) == riscInst.getOperandAt(0) && preInst.getOperandAt(1) == riscInst.getOperandAt(1)) {
                            riscInstList.remove(instIndex);
                            instIndex--;
                            continue;
                        }
                    }


                    if (preInst.type == RISCInstruction.ITYPE.li) {
                        //简单乘法优化
                        if (riscInst.type == RISCInstruction.ITYPE.mul || riscInst.type == RISCInstruction.ITYPE.mulw) {
                            RISCOperand temp1 = null;
                            RISCOperand temp2 = null;
                            int x = 1;
                            RISCOperand dst = riscInst.getOperandAt(0);
                            //判断li中的dst是否为mul中的操作数
                            if (riscInst.getOperandAt(1) == preInst.getOperandAt(0)) {
                                temp1 = riscInst.getOperandAt(2);
                            } else if (riscInst.getOperandAt(2) == preInst.getOperandAt(0)) {
                                temp1 = riscInst.getOperandAt(1);
                            } else {
                                preInst = riscInst;
                                continue;
                            }
                            //取出常数
                            if (preInst.getOperandAt(1) instanceof Immediate) {
                                x = ((Immediate) preInst.getOperandAt(1)).getVal();
                            } else if (preInst.getOperandAt(1) instanceof BigImmediate) {
                                x = Math.toIntExact(((BigImmediate) preInst.getOperandAt(1)).getVal());
                            }

                            int log = riscBB.log2(x);
                            int number = 1;
                            for (int i = 0; i < log; i++) {
                                number <<= 1;
                            }
                            int n = x - number;
                            //小于5步替换
                            if (n <= 4) {
                                if (instIndex + 1 < riscInstList.size()) {
                                    preInst = riscInstList.get(instIndex + 1);
                                }
                                AddInstruction addInstruction = new AddInstruction(dst, dst, temp1);
                                for (int i = 0; i < n; i++) {
                                    riscInstList.add(instIndex, addInstruction);
                                }
                                SlliInstruction slliInstruction = new SlliInstruction(dst, dst, new Immediate(log));
                                riscInstList.add(instIndex, slliInstruction);
                                //去除li和mul
                                riscInstList.remove(instIndex + n + 1);
                                riscInstList.remove(instIndex - 1);
                                instIndex += (n);
                                continue;
                            } else {
                                number <<= 1;
                                n = number - x;
                                if (n <= 4) {
                                    if (instIndex + 1 < riscInstList.size()) {
                                        preInst = riscInstList.get(instIndex + 1);
                                    }
                                    SubwInstruction subwInstruction = new SubwInstruction(dst, dst, temp1);
                                    for (int i = 0; i < n; i++) {
                                        riscInstList.add(instIndex, subwInstruction);
                                    }
                                    SlliInstruction slliInstruction = new SlliInstruction(dst, dst, new Immediate(log + 1));
                                    riscInstList.add(instIndex, slliInstruction);
                                    //去除li和mul
                                    riscInstList.remove(instIndex + n + 1);
                                    riscInstList.remove(instIndex - 1);
                                    instIndex += (n);
                                    continue;
                                }

                            }

                        }
                    }


                    preInst = riscInst;
                }
            }

        }
    }

    public boolean isLegal(int num, int base) {
        return num - base < 2047 && num - base > -2048;
    }
}
