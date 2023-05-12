package backend;

import backend.instruction.*;
import ir.Instruction;
import ir.values.BasicBlock;
import ir.values.Function;
import util.IList;

import java.util.LinkedList;

public class RISCBasicBlock {
    private LinkedList<RISCinstruction> Instructions = new LinkedList<>();

    /**
     *此构造函数为创建函数的初始块和结束块
     */
    public RISCBasicBlock(int i, Function iRfunc) {
        //初始块
        if(i==0){
            int imm=0;
            imm=(iRfunc.getParamList().size()/4)*4+16;
            AddiInstruction addi1=new AddiInstruction(2,2,-imm);
            Instructions.add(addi1);
            SdInstruction sd1=new SdInstruction(1,imm-8,2);
            Instructions.add(sd1);
            AddiInstruction addi2=new AddiInstruction(1,2,imm);
            Instructions.add(addi2);
            if(iRfunc.getParamList().size()!=0){
                ;
            }
        }
        //结束块
        else{
            int imm=0;
            imm=(iRfunc.getParamList().size()/4)*4+16;
            LdInstruction ld1=new LdInstruction(1,imm-8,2);
            Instructions.add(ld1);
            AddiInstruction add1=new AddiInstruction(2,2,imm);
            Instructions.add(add1);
            JrInstruction jr1=new JrInstruction(3);
            Instructions.add(jr1);
        }
    }



    public LinkedList<RISCinstruction> getInstructions(){
        return Instructions;
    }

    public RISCBasicBlock(BasicBlock iRbb) {
            for(IList.INode<Instruction, BasicBlock> Inode : iRbb.list){
                Instruction curInst=Inode.getElement();
                switch (curInst.tag){
                    case Ret:  translateRet(curInst);
                }

            }
    }

    /**
     * 转换ir指令
     */

    private void translateRet(Instruction curInst) {
            if(true){
                NopInstruction nop1=new NopInstruction();
                Instructions.add(nop1);
            }

    }


}
