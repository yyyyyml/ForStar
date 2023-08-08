package pass.ir.constantexp_derivation;

import ir.Instructions.TerminatorInst;
import ir.values.Constant;
import pass.ir.BaseIRPass;
import ir.Instruction;
import ir.Instruction.* ;
import ir.Module;
import ir.Use;
import ir.values.BasicBlock;
import ir.values.Function;
import pass.ir.BaseIRPass;
import util.IList;
import ir.Value;
import ir.Value.*;

import java.util.Objects;

public class ConstantExp_Derivation implements BaseIRPass{
    @Override
    public void run(Module module){
        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            for (IList.INode<BasicBlock, Function> bbInode : func.list) {
                BasicBlock bb = bbInode.getElement();

                for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {
                    Instruction inst = instInode.getElement();
                    if(canDerive(inst)){
                        if(inst.getTag().equals(Instruction.TAG.BR) ){
                            var c1 = inst.getOperandAt(0);
                            var Tb = (ir.values.BasicBlock)inst.getOperandAt(1);
                            var Fb = (ir.values.BasicBlock)inst.getOperandAt(2);
                            //如果是条件为假
                            if(((Constant.ConstantInt )c1).getVal() == 0){
                                //删除原来br，生成新的无条件br
                                TerminatorInst.Br uncondBr = new TerminatorInst.Br(Fb, bb);
                                bb.list.addAfter(instInode,uncondBr.node);
                                instInode.removeSelf();
                                //删除使用当前bb的PHI
                                //bb.fixPhi();因为并发修改而报错，不知道怎么改
                                //删除不会进入程序块
                                var BB = bb.nextList.get(0);
                                BB.fixPhi();
                                var nextBB = BB.nextList.get(0);
                                BB.replaceAllUseWith(nextBB); // 只可能有一个后继
                                for (BasicBlock preBB : BB.preList) {
                                    preBB.nextList.remove(BB);
                                    preBB.nextList.add(nextBB);
                                    nextBB.preList.remove(BB);
                                    nextBB.preList.add(preBB);
                                }
                                BB.node.removeSelf();
                            }
                            //如果是条件为真
                            else{
                                TerminatorInst.Br uncondBr = new TerminatorInst.Br(Tb, bb);
                                bb.list.addAfter(instInode,uncondBr.node);
                                instInode.removeSelf();
                                //删除使用当前bb的PHI
                                //bb.fixPhi();因为并发修改而报错，不知道怎么改
                                //删除不会进入程序块
                                var BB = bb.nextList.get(1);
                                BB.fixPhi();
                                var nextBB = BB.nextList.get(0);
                                BB.replaceAllUseWith(nextBB); // 只可能有一个后继
                                for (BasicBlock preBB : BB.preList) {
                                    preBB.nextList.remove(BB);
                                    preBB.nextList.add(nextBB);
                                    nextBB.preList.remove(BB);
                                    nextBB.preList.add(preBB);
                                }
                                BB.node.removeSelf();
                            }
                        }
                        else {
                            Value v = calculate(inst);
                            inst.replaceAllUseWith(v);
                            instInode.removeSelf();
                        }
                    }
                }

            }
        }
    }
    public boolean canDerive(Instruction inst){

        return calculate(inst)!=inst;
    }
    static Value calculate(Instruction inst){
        var c1 = inst.getOperandAt(0);
        var c2 = inst.getOperandAt(1);
        var c3 = inst.getOperandAt(2);
        if(inst.getTag().equals(Instruction.TAG.ADD)){
            if(c1 instanceof Constant.ConstantInt && c2 instanceof Constant.ConstantInt){
                return Constant.ConstantInt.getConstantInt(((Constant.ConstantInt) c1).getVal() + ((Constant.ConstantInt) c2).getVal()) ;
            }
            else if(c1 instanceof Constant.ConstantInt && ((Constant.ConstantInt) c1).getVal()==0){
                return c2;
            }
            else if(c2 instanceof Constant.ConstantInt && ((Constant.ConstantInt) c2).getVal()==0){
                return c1;
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.FADD)){
            if(c1 instanceof Constant.ConstantFloat && c2 instanceof Constant.ConstantFloat){
                return Constant.ConstantFloat.getConstantFloat(((Constant.ConstantFloat) c1).getVal() + ((Constant.ConstantFloat) c2).getVal()) ;
            }
            else if(c1 instanceof Constant.ConstantFloat && ((Constant.ConstantFloat) c1).getVal()==0){
                return c2;
            }
            else if(c2 instanceof Constant.ConstantFloat && ((Constant.ConstantFloat) c2).getVal()==0){
                return c1;
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.SUB)){
            if(c1 instanceof Constant.ConstantInt && c2 instanceof Constant.ConstantInt){
                return Constant.ConstantInt.getConstantInt(((Constant.ConstantInt) c1).getVal() - ((Constant.ConstantInt) c2).getVal()) ;
            }

            else if(c2 instanceof Constant.ConstantInt && ((Constant.ConstantInt) c2).getVal()==0){
                return c1;
            }
            else if(Objects.equals(c1,c2)){
                return Constant.ConstantInt.getConstantInt(0);
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.FSUB)){
            if(c1 instanceof Constant.ConstantFloat && c2 instanceof Constant.ConstantFloat){
                return Constant.ConstantFloat.getConstantFloat(((Constant.ConstantFloat) c1).getVal() - ((Constant.ConstantFloat) c2).getVal()) ;
            }

            else if(c2 instanceof Constant.ConstantFloat && ((Constant.ConstantFloat) c2).getVal()==0){
                return c1;
            }
            else if(Objects.equals(c1,c2)){
                return Constant.ConstantFloat.getConstantFloat(.0f);
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.MUL)){
            if(c1 instanceof Constant.ConstantInt && c2 instanceof Constant.ConstantInt){
                return Constant.ConstantInt.getConstantInt(((Constant.ConstantInt) c1).getVal() * ((Constant.ConstantInt) c2).getVal()) ;
            }
            else if(c1 instanceof Constant.ConstantInt && ((Constant.ConstantInt) c1).getVal()==0){
                return Constant.ConstantInt.getConstantInt(0);
            }
            else if(c2 instanceof Constant.ConstantInt && ((Constant.ConstantInt) c2).getVal()==0){
                return Constant.ConstantInt.getConstantInt(0);
            }
            else if(c1 instanceof Constant.ConstantInt && ((Constant.ConstantInt) c1).getVal()==1){
                return c2;
            }
            else if(c2 instanceof Constant.ConstantInt && ((Constant.ConstantInt) c2).getVal()==1){
                return c1;
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.FMUL)){
            if(c1 instanceof Constant.ConstantFloat && c2 instanceof Constant.ConstantFloat){
                return Constant.ConstantFloat.getConstantFloat(((Constant.ConstantFloat) c1).getVal() * ((Constant.ConstantFloat) c2).getVal()) ;
            }
            else if(c1 instanceof Constant.ConstantFloat && ((Constant.ConstantFloat) c1).getVal()==0){
                return Constant.ConstantFloat.getConstantFloat(.0f);
            }
            else if(c2 instanceof Constant.ConstantFloat && ((Constant.ConstantFloat) c2).getVal()==0){
                return Constant.ConstantFloat.getConstantFloat(.0f);
            }
            else if(c1 instanceof Constant.ConstantFloat && ((Constant.ConstantFloat) c1).getVal()==1){
                return c2;
            }
            else if(c2 instanceof Constant.ConstantFloat && ((Constant.ConstantFloat) c2).getVal()==1){
                return c1;
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.DIV)){
            if(c1 instanceof Constant.ConstantInt && c2 instanceof Constant.ConstantInt){
                return Constant.ConstantInt.getConstantInt(((Constant.ConstantInt) c1).getVal() / ((Constant.ConstantInt) c2).getVal()) ;
            }
            else if(c1 instanceof Constant.ConstantInt && ((Constant.ConstantInt) c1).getVal()==0){
                return Constant.ConstantInt.getConstantInt(0);
            }
            else if(c2 instanceof Constant.ConstantInt && ((Constant.ConstantInt) c2).getVal()==1){
                return c1;
            }
            else if(Objects.equals(c1,c2)){
                return Constant.ConstantInt.getConstantInt(1);
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.FDIV)){
            if(c1 instanceof Constant.ConstantFloat && c2 instanceof Constant.ConstantFloat){
                return Constant.ConstantFloat.getConstantFloat(((Constant.ConstantFloat) c1).getVal() / ((Constant.ConstantFloat) c2).getVal()) ;
            }
            else if(c1 instanceof Constant.ConstantFloat && ((Constant.ConstantFloat) c1).getVal()==0){
                return Constant.ConstantFloat.getConstantFloat(.0f);
            }
            else if(c2 instanceof Constant.ConstantFloat && ((Constant.ConstantFloat) c2).getVal()==1){
                return c1;
            }
            else if(Objects.equals(c1,c2)){
                return Constant.ConstantFloat.getConstantFloat(1);
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.FNEG)) {
            if (c1 instanceof Constant.ConstantFloat) {
                return Constant.ConstantFloat.getConstantFloat(-((Constant.ConstantFloat) c1).getVal());
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.LT)){
            if(c1 instanceof Constant.ConstantInt && c2 instanceof Constant.ConstantInt){
                return Constant.ConstantInt.getConstantInt(((Constant.ConstantInt)c1).getVal() < ((Constant.ConstantInt)c2).getVal() ? 1 : 0) ;
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.FLT)){
            if(c1 instanceof Constant.ConstantFloat && c2 instanceof Constant.ConstantFloat){
                return Constant.ConstantInt.getConstantInt(((Constant.ConstantFloat)c1).getVal() < ((Constant.ConstantFloat)c2).getVal() ? 1 : 0) ;
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.GT)){
            if(c1 instanceof Constant.ConstantInt && c2 instanceof Constant.ConstantInt){
                return Constant.ConstantInt.getConstantInt(((Constant.ConstantInt)c1).getVal() > ((Constant.ConstantInt)c2).getVal() ? 1 : 0) ;
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.FGT)){
            if(c1 instanceof Constant.ConstantFloat && c2 instanceof Constant.ConstantFloat){
                return Constant.ConstantInt.getConstantInt(((Constant.ConstantFloat)c1).getVal() > ((Constant.ConstantFloat)c2).getVal() ? 1 : 0) ;
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.EQ)){
            if(c1 instanceof Constant.ConstantInt && c2 instanceof Constant.ConstantInt){
                return Constant.ConstantInt.getConstantInt(((Constant.ConstantInt)c1).getVal() == ((Constant.ConstantInt)c2).getVal() ? 1 : 0) ;
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.FEQ)){
            if(c1 instanceof Constant.ConstantFloat && c2 instanceof Constant.ConstantFloat){
                return Constant.ConstantInt.getConstantInt(((Constant.ConstantFloat)c1).getVal() == ((Constant.ConstantFloat)c2).getVal() ? 1 : 0) ;
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.NE)){
            System.out.println(inst);
            System.out.println(c1);
            System.out.println(c1 instanceof Constant.ConstantInt);
            if(c1 instanceof Constant.ConstantInt && c2 instanceof Constant.ConstantInt){
                return Constant.ConstantInt.getConstantInt(((Constant.ConstantInt)c1).getVal() != ((Constant.ConstantInt)c2).getVal() ? 1 : 0) ;
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.FNE)){
            if(c1 instanceof Constant.ConstantFloat && c2 instanceof Constant.ConstantFloat){
                return Constant.ConstantInt.getConstantInt(((Constant.ConstantFloat)c1).getVal() != ((Constant.ConstantFloat)c2).getVal() ? 1 : 0) ;
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.LE)){
            if(c1 instanceof Constant.ConstantInt && c2 instanceof Constant.ConstantInt){
                return Constant.ConstantInt.getConstantInt(((Constant.ConstantInt)c1).getVal() <= ((Constant.ConstantInt)c2).getVal() ? 1 : 0) ;
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.FLE)){
            if(c1 instanceof Constant.ConstantFloat && c2 instanceof Constant.ConstantFloat){
                return Constant.ConstantInt.getConstantInt(((Constant.ConstantFloat)c1).getVal() <= ((Constant.ConstantFloat)c2).getVal() ? 1 : 0) ;
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.GE)){
            if(c1 instanceof Constant.ConstantInt && c2 instanceof Constant.ConstantInt){
                return Constant.ConstantInt.getConstantInt(((Constant.ConstantInt)c1).getVal() >= ((Constant.ConstantInt)c2).getVal() ? 1 : 0) ;
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.FGE)){
            if(c1 instanceof Constant.ConstantFloat && c2 instanceof Constant.ConstantFloat){
                return Constant.ConstantInt.getConstantInt(((Constant.ConstantFloat)c1).getVal() >= ((Constant.ConstantFloat)c2).getVal() ? 1 : 0) ;
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.ZEXT)){
            if(c1 instanceof Constant.ConstantInt){
                return Constant.ConstantInt.getConstantInt(((Constant.ConstantInt)c1).getVal()) ;
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.FPTOSI)){
            if(c1 instanceof Constant.ConstantFloat){
                return Constant.ConstantInt.getConstantInt((int)((Constant.ConstantFloat) c1).getVal()) ;
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.SITOFP)){
            if(c1 instanceof Constant.ConstantInt){
                return Constant.ConstantFloat.getConstantFloat((float)((Constant.ConstantInt)c1).getVal());
            }
            else return inst;
        }
        else if(inst.getTag().equals(Instruction.TAG.BR)){

            if(c1 instanceof Constant.ConstantInt){
                return c1;//不重要，需要在优化部分重新优化，这里只需要和原来的inst不相等

            }
            else return inst;
        }
        else return inst;
    }
}
