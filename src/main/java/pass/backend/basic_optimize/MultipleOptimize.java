package pass.backend.basic_optimize;

import backend.*;
import backend.instructions.*;
import backend.operands.*;
import pass.backend.BaseBackendPass;

import java.util.LinkedList;

public class MultipleOptimize implements BaseBackendPass {
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
                    int curIndex = instIndex+1;


                    if(preInst.type == RISCInstruction.ITYPE.li){
                        //简单乘法优化
                        if(riscInst.type == RISCInstruction.ITYPE.mul || riscInst.type == RISCInstruction.ITYPE.mulw){
                            int step = 0;
                            RISCOperand temp1 = null;
                            //RISCOperand temp2 = null;
                            Boolean xIsNeg = false;
                            LinkedList<RISCInstruction> tempInstList = new LinkedList<>();
                            int x=1;
                            RISCOperand dst = riscInst.getOperandAt(0);
                            //判断li中的dst是否为mul中的操作数
                            if(riscInst.getOperandAt(1)==preInst.getOperandAt(0)){
                                temp1 = riscInst.getOperandAt(2);
                            }
                            else if(riscInst.getOperandAt(2)==preInst.getOperandAt(0)){
                                temp1 = riscInst.getOperandAt(1);
                            }
                            else {
                                preInst = riscInst;
                                continue;
                            }
                            //临时寄存器保存
                            RealRegister tempRegister = new RealRegister(13, 11);
                            MvInstruction mvInstruction = new MvInstruction(tempRegister,temp1);
                            tempInstList.add(mvInstruction);
                            //riscInstList.add(curIndex++,mvInstruction);

                            //取出常数
                            if(preInst.getOperandAt(1) instanceof Immediate){
                                x = ((Immediate) preInst.getOperandAt(1)).getVal();
                            }
                            else if(preInst.getOperandAt(1) instanceof BigImmediate){
                                x = Math.toIntExact(((BigImmediate) preInst.getOperandAt(1)).getVal());
                            }
                            if (x < 0 ){
                                xIsNeg = true;
                                x = -x;
                            }

                            int log = RISCBasicBlock.log2(x);
                            int number = 1;
                            for (int i = 0; i < log ; i++){
                                number <<= 1;
                            }
                            int n = x - number;
                            int number2 = number<<1;
                            int n2 = number2 - x;

                            //1步替换
                            if(n <= 1){

                                //SLLI
                                SlliInstruction slliInstruction = new SlliInstruction(dst,temp1,new Immediate(log));
                                riscInstList.add(curIndex++,slliInstruction);
                                //ADD
                                AddInstruction addInstruction = new AddInstruction(dst,dst,temp1);
                                for (int i = 0; i < n ; i++){
                                    riscInstList.add(curIndex++,addInstruction);
                                }
                                //取反
                                if (xIsNeg){
                                    SubInstruction subInstruction = new SubInstruction(dst,new RealRegister(0),dst);
                                    riscInstList.add(curIndex++,subInstruction);
                                }

                                //去除li和mul
                                riscInstList.remove(instIndex );
                                riscInstList.remove(instIndex - 1);
                                curIndex -= 2;
                                //为preInst赋值
                                if (curIndex  < riscInstList.size() )
                                {
                                    preInst = riscInstList.get(curIndex );
                                }
                                instIndex = curIndex;
                                continue;
                            }
                            else if (n2 <= 1){
                                //SLLI
                                SlliInstruction slliInstruction = new SlliInstruction(dst,temp1,new Immediate(log+1));
                                riscInstList.add(curIndex++,slliInstruction);
                                //ADD
                                SubInstruction subInstruction = new SubInstruction(dst,dst,temp1);
                                for (int i = 0; i < n2 ; i++){
                                    riscInstList.add(curIndex++,subInstruction);
                                }
                                //取反
                                if (xIsNeg){
                                    SubInstruction subInstruction2 = new SubInstruction(dst,new RealRegister(0),dst);
                                    riscInstList.add(curIndex++,subInstruction2);
                                }
                                //去除li和mul
                                riscInstList.remove(instIndex );
                                riscInstList.remove(instIndex - 1);
                                curIndex -= 2;
                                //为preInst赋值
                                if (curIndex  < riscInstList.size() )
                                {
                                    preInst = riscInstList.get(curIndex );
                                }
                                instIndex = curIndex;
                                continue;

                            }
                            temp1 = tempRegister;
                            //非一步替换，多步搜索
                            Boolean isMore = false;
                            Boolean isFirst = true;
                            Boolean isSub = false;
                            if(n2 < n){isMore = true;}
                            VirtualRegister vdst = riscBB.getNewVr();

                            //非一步到位,设置步数为4
                            while (step < 4){
                                step++;
                                if(isMore){log++;}
                                //SLLI
                                SlliInstruction slliInstruction = new SlliInstruction(vdst,temp1,new Immediate(log));
                                tempInstList.add(slliInstruction);
                                //riscInstList.add(curIndex++,slliInstruction);
                                //判断是不是第一个
                                if (isFirst){
                                    MvInstruction mvInstruction2 = new MvInstruction(dst,vdst);
                                    tempInstList.add(mvInstruction2);
                                    //riscInstList.add(curIndex++,mvInstruction2);
                                    isFirst = false;
                                }
                                //判断应该增还是减
                                else if(isSub){
                                    SubInstruction subInstruction = new SubInstruction(dst,dst,vdst);
                                    tempInstList.add(subInstruction);
                                    //riscInstList.add(curIndex++,subInstruction);
                                }
                                else {
                                    AddInstruction addInstruction = new AddInstruction(dst,dst,vdst);
                                    tempInstList.add(addInstruction);
                                    //riscInstList.add(curIndex++,addInstruction);
                                }
                                //重新给乘数赋值,判断下一次操作是加还是减
                                if  (isMore){
                                    isSub = !(isSub);
                                    x = n2;
                                }
                                else {
                                    x = n;
                                }
                                //如果乘数小于等于1，停止循环
                                if(x <= 1){
                                    if(x == 1)
                                    {
                                        if (isSub) {
                                            SubInstruction subInstruction = new SubInstruction(dst, dst, temp1);
                                            tempInstList.add(subInstruction);
                                            //riscInstList.add(curIndex++,subInstruction);
                                        } else {
                                            AddInstruction addInstruction = new AddInstruction(dst, dst, temp1);
                                            tempInstList.add(addInstruction);
                                            //riscInstList.add(curIndex++,addInstruction);
                                        }
                                    }
                                    //取反
                                    if (xIsNeg){
                                        SubInstruction subInstruction2 = new SubInstruction(dst,new RealRegister(0),dst);
                                        tempInstList.add(subInstruction2);
//                                        riscInstList.add(curIndex++,subInstruction2);
                                    }
                                    for (RISCInstruction riscInstruction : tempInstList ){
                                        riscInstList.add(curIndex++,riscInstruction);
                                    }
                                    //去除li和mul
                                    riscInstList.remove(instIndex );
                                    riscInstList.remove(instIndex - 1);
                                    curIndex -= 2;
                                    //为preInst赋值
                                    if (curIndex  < riscInstList.size() )
                                    {
                                        preInst = riscInstList.get(curIndex );
                                    }
                                    instIndex = curIndex;
                                    break;

                                }
                                //重新计算log,n和n2
                                log = riscBB.log2(x);
                                number = 1;
                                for (int i = 0; i < log ; i++){
                                    number <<= 1;
                                }
                                n = x - number;
                                number2 = number<<1;
                                n2 = number2 - x;
                                if(n2 < n){isMore = true;}
                                else {isMore = false;}
                            }
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
