package pass.ir.constantexp_derivation;

import ir.values.Constant;
import pass.ir.BaseIRPass;
import ir.Instruction;
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
                        Value v = calculate(inst);
                        inst.replaceAllUseWith(v);
                        instInode.removeSelf();
                    }
                }
            }
        }
    }
    public boolean canDerive(Instruction inst){
        System.out.println(inst);
        System.out.println(inst.getOperandAt(0));
        System.out.println(inst.getOperandAt(1));
        System.out.println(calculate(inst)!=inst);
        System.out.println("-------------");
        return calculate(inst)!=inst;
    }
    static Value calculate(Instruction inst){
        var c1 = inst.getOperandAt(0);
        var c2 = inst.getOperandAt(1);

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
        /*else if(inst.getTag().equals(Instruction.TAG.LT)){

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
        }*/
        else return inst;
    }
}
