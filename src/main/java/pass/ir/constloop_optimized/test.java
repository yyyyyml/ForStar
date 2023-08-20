package pass.ir.constloop_optimized;

import ir.Instruction;
import ir.Instructions.BinaryInst;
import ir.Module;
import ir.values.BasicBlock;
import ir.values.Constant;
import ir.values.Function;
import pass.ir.BaseIRPass;
import util.IList;

public class test implements BaseIRPass {
    public boolean run(Module module){
        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            for (IList.INode<BasicBlock, Function> bbInode : func.list) {
                BasicBlock bb = bbInode.getElement();
                if(bb.nextList.size() ==1)
                {
                    BasicBlock entryBB = bb.nextList.get(0);
                    //首先需要确定这个块是进入while前的一个普通快同时不是while_body块
                    if(entryBB.getName().equals("_WHILE_ENTRY") && (!bb.getName().equals("_WHILE_BODY"))){
                        //

                        //加个if条件比较保险，其实可以不加
                        if(bb.nextList.get(0).nextList.size() >1 ){
                            BasicBlock bodyBB = bb.nextList.get(0).nextList.get(0);
                            BasicBlock exitBB = bb.nextList.get(0).nextList.get(1);
                            //先判断这个循环能不能展开
                            if(isAbleTo(entryBB,bodyBB)){
                                //如果能展开，先删除这块的最后一个br
                                //bb.list.getLast().removeSelf();
                                //再将while_body重复相应次数加到本块中
                                //首先获得循环次数
                                int loopNum = getLoopNum(entryBB);
                                System.out.println(loopNum);

                                //加指令
//                                for(int i=0;i<loopNum;i++)
//                                {
//                                    var a = bodyBB.list.getFirst();
//                                    for (int j =0 ;j<bodyBB.list.size();j++){
//                                        var b=a;
//                                        System.out.println(a);
//                                        bb.list.addLast(a);
//                                        a = bodyBB.list.getNext(b);
//                                    }
//                                    System.out.println("out");
//                                }
//                                //再将while_exit块里的所有指令加到本块中
//                                var c = exitBB.list.getFirst();
//                                for (int j =0 ;j<exitBB.list.size();j++){
//                                    var b=c;
//                                    System.out.println(c);
//                                    bb.list.addLast(c);
//                                    c = bodyBB.list.getNext(b);
//                                }
                                //最后再将while_entry,while_body,while_exit三个块全部删掉即可。
                                for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {
                                    System.out.println("??????????");
                                    Instruction inst = instInode.getElement();
                                    System.out.println(instInode == null);
                                    System.out.println(inst.node == null);
                                    System.out.println(inst);
                                    //bb.list.addLast(inst.node);
                                }
                                for (IList.INode<Instruction, BasicBlock> instInode : exitBB.list) {
                                    System.out.println(">>>>>>>>>>>>");
                                    Instruction inst = instInode.getElement();
                                    System.out.println(instInode == null);
                                    System.out.println(inst.node == null);
                                    System.out.println(inst);
                                    //bb.list.addLast(inst.node);
                                }
                            }
                        }
                    }
                }

            }
        }
        return false;
    }

    //判断这个循环是否可以展开
    public boolean isAbleTo(BasicBlock entryBB, BasicBlock bodyBB){
        //什么样的entry可以断定为能优化的while循环呢？
        //再常量传播以后，这个判断条件是一个变量小于一个常量的情况就是可以传播的
        boolean isEntryOk = false;
        boolean isBodyOk = false;
        for (IList.INode<Instruction, BasicBlock> instInode : entryBB.list) {
            Instruction inst = instInode.getElement();
            //只有变量小于常量这种情况可以优化
            //i<100
            if (inst.isLT()) {
                var c1 = inst.getOperandAt(0);
                var c2 = inst.getOperandAt(1);
                if (c2 instanceof Constant.ConstantInt && !(c1 instanceof Constant)) {
                    isEntryOk = true;
                }
            }
            //i<=100
            else if (inst.isLE()) {
                var c1 = inst.getOperandAt(0);
                var c2 = inst.getOperandAt(1);
                if (c2 instanceof Constant.ConstantInt && !(c1 instanceof Constant)) {
                    isEntryOk = true;
                }
            }
            //100>i
            else if (inst.isGT()) {
                var c1 = inst.getOperandAt(0);
                var c2 = inst.getOperandAt(1);
                if (c1 instanceof Constant.ConstantInt && !(c2 instanceof Constant)) {
                    isEntryOk = true;
                }
            }
            //100>=i
            else if (inst.isGE()) {
                var c1 = inst.getOperandAt(0);
                var c2 = inst.getOperandAt(1);
                if (c1 instanceof Constant.ConstantInt && !(c2 instanceof Constant)) {
                    isEntryOk = true;
                }
            }
        }

        //什么情况下while_body是可以展开的呢？只包含简单二元表达式和一个跳回去的br
        //注意：如果while包含条件语句，第一个body块还是只包含简单二元表达式和一个br，但是br并没有跳回entry
        if(bodyBB.nextList.get(0).equals(entryBB)) isBodyOk = true;

        for (IList.INode<Instruction, BasicBlock> instInode : bodyBB.list) {
            Instruction inst = instInode.getElement();
            if(inst.isBinary()||inst.isBR()) continue;
            else {
                isBodyOk = false;
                break;
            }
        }

        if(isBodyOk&&isEntryOk)  return true;
        else return false;
    }



    public int getLoopNum(BasicBlock entryBB)
    {
        int limitValue_int = -999;
        for (IList.INode<Instruction, BasicBlock> instInode : entryBB.list) {
            Instruction inst = instInode.getElement();
            if(!(inst.isLT()||inst.isLE()||inst.isGT()||inst.isGE())){
                continue;
            }
            else {
                //i<100
                if (inst.isLT()) {
                    var c1 = inst.getOperandAt(0);
                    var c2 = inst.getOperandAt(1);
                    if (c2 instanceof Constant.ConstantInt) {
                        limitValue_int = ((Constant.ConstantInt) c2).getVal();
                    }
                }
                //i<=100
                else if (inst.isLE()) {
                    var c1 = inst.getOperandAt(0);
                    var c2 = inst.getOperandAt(1);
                    if (c2 instanceof Constant.ConstantInt) {
                        limitValue_int = ((Constant.ConstantInt) c2).getVal() + 1;
                    }
                }
                //100>i
                else if (inst.isGT()) {
                    var c1 = inst.getOperandAt(0);
                    var c2 = inst.getOperandAt(1);
                    if (c1 instanceof Constant.ConstantInt) {
                        limitValue_int = ((Constant.ConstantInt) c1).getVal();
                    }
                }
                //100>=i
                else if (inst.isGE()) {
                    var c1 = inst.getOperandAt(0);
                    var c2 = inst.getOperandAt(1);
                    if (c1 instanceof Constant.ConstantInt) {
                        limitValue_int = ((Constant.ConstantInt) c1).getVal() + 1;
                    }
                }
                break;
            }
        }

        return limitValue_int;
    }

}
