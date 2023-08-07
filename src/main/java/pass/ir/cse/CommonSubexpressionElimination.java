package pass.ir.cse;

import ir.Instruction;
import ir.Module;
import ir.values.BasicBlock;
import ir.values.Function;
import pass.ir.BaseIRPass;
import util.IList;

import java.util.HashSet;
import java.util.Set;

public class CommonSubexpressionElimination implements BaseIRPass {
    @Override
    public void run(Module module) {
        System.out.println("CSE");
        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            if (func.isBuiltin()) continue;
            for (IList.INode<BasicBlock, Function> bbInode : func.list) {
                BasicBlock bb = bbInode.getElement();
                Set<Instruction> instSet = new HashSet<>(); // 储存这个块内出现的表达式
                for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {
                    Instruction inst = instInode.getElement();
                    if (!inst.isBinary()) continue; // 不是二元运算不管

//                    System.out.println(inst);
                    boolean hasSame = false;
                    var left = inst.getOperandAt(0);
                    var right = inst.getOperandAt(1);
                    var tag = inst.getTag();
                    Instruction instToUse = null;
                    for (Instruction curInst : instSet) {
                        if (curInst.getOperandAt(0).equals(left) && curInst.getOperandAt(1).equals(right) && curInst.getTag() == tag) {
                            instToUse = curInst; // 记录之前出现的相同表达式的指令
                            hasSame = true;
                            break;
                        } else {
//                            System.out.println("哪里不同");
//                            System.out.println(curInst.getOperandAt(0) == left);
//                            System.out.println(curInst.getOperandAt(1) == right);
//                            System.out.println(curInst.getTag() == tag);
                        }
                    }
                    if (hasSame) {
                        // 有相同子表达式
                        inst.replaceAllUseWith(instToUse);
                        inst.removeAllOperand();
                        inst.node.removeSelf();
                    } else {
                        // 没有相同子表达式，加入集合
                        instSet.add(inst);
//                        System.out.println("加入了"+inst);
                    }


                }
            }

        }
    }


}
