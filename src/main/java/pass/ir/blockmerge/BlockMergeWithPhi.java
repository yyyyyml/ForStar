package pass.ir.blockmerge;

import ir.Instruction;
import ir.Instructions.TerminatorInst;
import ir.Module;
import ir.values.BasicBlock;
import ir.values.Function;
import pass.ir.BaseIRPass;
import util.IList;

public class BlockMergeWithPhi implements BaseIRPass {
    @Override
    public void run(Module module) {
        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            if (func.isBuiltin()) continue;
            // 先消除一下死基本块
            deadBlockEliminate(func);
            // 把单br的块合并
//            onlyBrCombine(func); // 不是都能合，先不管

        }
    }

    private void deadBlockEliminate(Function func) {

        boolean processed = true;
        // 要迭代消除，直到没有可消除的
        while (processed) {
            boolean visitFirst = false;
            processed = false;
            for (IList.INode<BasicBlock, Function> bbInode : func.list) {
                BasicBlock bb = bbInode.getElement();
                // 跳过入口
                if (!visitFirst) {
                    visitFirst = true;
                    continue;
                }
                if (bb.preList.size() == 0) {
                    processed = true;
                    for (BasicBlock nextBB : bb.nextList) {
                        nextBB.preList.remove(bb);
                        bb.fixPhiInBlock(nextBB);
                    }
                    bb.node.removeSelf();
                }
            }
        }

    }

    private void onlyBrCombine(Function func) {
        boolean needDo = true;
        while (needDo) {
            needDo = false;
            for (IList.INode<BasicBlock, Function> bbInode : func.list) {
                BasicBlock bb = bbInode.getElement();
                // 判断是否存在只有一条br的块
                if (bb.list.size() == 1 && func.list.getFirst() != bbInode &&
                        bb.list.getFirst().getElement().getTag() == Instruction.TAG.BR) {
                    removeOnlyBrBlock(bb);
                    needDo = true;
                }
            }
        }

        // 单br合并后，可能有br到两个一样的块的情况，改
        for (IList.INode<BasicBlock, Function> bbInode : func.list) {
            BasicBlock bb = bbInode.getElement();
            // 判断是否有br到两个相同块的块

            if (bb.nextList.size() == 2 && bb.nextList.get(0) == bb.nextList.get(1)) {
                var nextBB = bb.nextList.get(1);
                // 都删掉，重新加
                nextBB.preList.remove(bb);
                bb.nextList.remove(nextBB);
                nextBB.preList.remove(bb);
                bb.nextList.remove(nextBB);
                // 改Br
                var brInst = bb.list.getLast().getElement();
                var op1 = brInst.getOperandAt(0);
                var op2 = brInst.getOperandAt(0);
                var op3 = brInst.getOperandAt(0);
                op1.removeUse(brInst.operandList.get(0));
                op2.removeUse(brInst.operandList.get(1));
                op3.removeUse(brInst.operandList.get(2));
                var newBrInst = new TerminatorInst.Br(nextBB, bb);
                brInst.node.removeSelf();
                bb.list.addLast(newBrInst.node);
            }
        }
    }

    private void removeOnlyBrBlock(BasicBlock bb) {
        System.out.println("消除块有用了！");
        var nextBB = bb.nextList.get(0);
        bb.replaceAllBrUseWith(nextBB); // 只可能有一个后继,换Br
        bb.fixPhi(bb.preList); // 修正所有用到这个块的phi
        for (BasicBlock preBB : bb.preList) {
            preBB.nextList.remove(bb);
            preBB.nextList.add(nextBB);
            nextBB.preList.remove(bb);
            nextBB.preList.add(preBB);
        }
        bb.node.removeSelf();
    }
}
