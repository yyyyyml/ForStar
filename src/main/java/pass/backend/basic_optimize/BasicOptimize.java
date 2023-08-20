package pass.backend.basic_optimize;

import backend.*;
import backend.instructions.*;
import backend.operands.*;
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
                if(riscInstList.size()!=0){preInst = riscInstList.get(0);}
                for (int instIndex = 0; instIndex < riscInstList.size(); instIndex++) {

                    RISCInstruction riscInst = riscInstList.get(instIndex);
                    //删除不必要的ld和sd
                    if(preInst.type == RISCInstruction.ITYPE.ld){
                        if(riscInst.type == RISCInstruction.ITYPE.sd  && preInst.getOperandAt(0).emit().equals(riscInst.getOperandAt(0).emit() )&& preInst.getOperandAt(1).emit().equals(riscInst.getOperandAt(1).emit() )){
                            riscInstList.remove(instIndex);
                            instIndex-- ;
                            continue;
                        }
                        //ld s10,address  mv vr,s10 -> ld vr,address
                        else if( riscInst.type == RISCInstruction.ITYPE.mv && preInst.getOperandAt(0).emit().equals("s10") && preInst.getOperandAt(0).emit().equals(riscInst.getOperandAt(1).emit()))
                        {
                            preInst.setOperand(riscInst.getOperandAt(0),0);
                            riscInstList.remove(instIndex);
                            instIndex-- ;
                            continue;
                        }
                    }
                    else if(preInst.type == RISCInstruction.ITYPE.sd){
                        if(riscInst.type == RISCInstruction.ITYPE.ld && preInst.getOperandAt(0).emit().equals(riscInst.getOperandAt(0).emit() )&& preInst.getOperandAt(1).emit().equals(riscInst.getOperandAt(1).emit() )){
                            riscInstList.remove(instIndex);
                            instIndex-- ;
                            continue;
                        }
                        else if(riscInst.isDef(0)&&riscInst.getOperandAt(0).emit().equals("t2")&&riscInst.getOperandAt(1).emit().equals("t2")&&preInst.getOperandAt(0).emit().equals("t2")){
                            if(instIndex-2 >= 0 )
                            {
                                preInst = riscInstList.get(instIndex - 2);
                            }
                            else {
                                preInst = null;
                            }
                            riscInstList.remove(instIndex-1);
                            instIndex-- ;
                            continue;
                        }
                    }
                    else if(preInst.type == RISCInstruction.ITYPE.lw){
                        if(riscInst.type == RISCInstruction.ITYPE.sw && preInst.getOperandAt(0).emit().equals(riscInst.getOperandAt(0).emit() )&& preInst.getOperandAt(1).emit().equals(riscInst.getOperandAt(1).emit() )){
                            riscInstList.remove(instIndex);
                            instIndex-- ;
                            continue;
                        }
                    }
                    else if(preInst.type == RISCInstruction.ITYPE.sw){
                        if(riscInst.type == RISCInstruction.ITYPE.lw && preInst.getOperandAt(0).emit().equals(riscInst.getOperandAt(0).emit() )&& preInst.getOperandAt(1).emit().equals(riscInst.getOperandAt(1).emit() )){
                            riscInstList.remove(instIndex);
                            instIndex-- ;
                            continue;
                        }
                    }
                    //mv s10,vr sd s10,address -> sd vr,address
                    else if(preInst.type == RISCInstruction.ITYPE.mv){
                        if( riscInst.type == RISCInstruction.ITYPE.sd && preInst.getOperandAt(0).emit().equals("s10") && preInst.getOperandAt(0).emit().equals(riscInst.getOperandAt(0).emit())){
                            riscInst.setOperand(preInst.getOperandAt(1),0);
                            riscInstList.remove(instIndex-1);
                            instIndex-- ;
                            preInst = riscInst;
                            continue;
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
