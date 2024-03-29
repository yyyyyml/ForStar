package pass.ir.merge;

import ir.Instruction;
import ir.Instructions.MemoryInst;
import ir.Instructions.TerminatorInst;
import ir.Module;
import ir.Use;
import ir.values.BasicBlock;
import ir.values.Function;
import pass.ir.BaseIRPass;
import util.IList;

import java.util.LinkedList;

public class BlockMergeWithPhi implements BaseIRPass {
    boolean retNeedDo = false;

    @Override
    public boolean run(Module module) {

        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            if (func.isBuiltin()) continue;
            // 先消除一下死基本块
            deadBlockEliminate(func);
            // 把单br的块合并
            onlyBrCombine(func); // 不是都能合，先不管

            // 普通合并，那些前后块都为1的
            normalCombine(func); // 加入了处理phi

        }
        return retNeedDo;
    }

    private void normalCombine(Function func) {
        for (IList.INode<BasicBlock, Function> bbInode : func.list) {
            BasicBlock bb = bbInode.getElement();
            // 常规合并，只合并pre,next都为1的
            if (canCombinePre(bb)) {
                System.out.println("能合并");
                var preBB = bb.preList.get(0);
                combine(preBB, bb);
            }
        }
    }

    private boolean canCombinePre(BasicBlock block) {
        if (block.preList.size() != 1) return false;
        if (block.list.getFirst().getElement() instanceof MemoryInst.Phi) return false;
        var preBlock = (BasicBlock) block.preList.get(0);
        return preBlock.nextList.size() == 1;
    }

    private void combine(BasicBlock preBB, BasicBlock followingBB) {
        LinkedList<IList.INode<Instruction, BasicBlock>> InstToAdd = new LinkedList<>();
        // 要移除Br
        preBB.getLastInst().node.removeSelf();
        // 把要移动的指令放入InstToAdd
        for (IList.INode<Instruction, BasicBlock> instInode : followingBB.list) {
            InstToAdd.add(instInode);
        }
        // 将InstToAdd中的指令移动过去
        for (int i = 0; i < InstToAdd.size(); i++) {
            Instruction inst = InstToAdd.get(i).getElement();
            if (inst instanceof MemoryInst.Phi) {
//                preBB.list.addFirst(InstToAdd.get(i)); // 加在前面
                throw new RuntimeException("不能处理phi");

            } else {
                preBB.list.addLast(InstToAdd.get(i));
            }

        }
        // 处理使用这个块的phi

        followingBB.replaceAllPhiUseWith(preBB);


        // 处理块的前驱后继关系
        preBB.nextList = followingBB.nextList;
        for (BasicBlock next2BB : preBB.nextList) {
            next2BB.preList.remove(followingBB);
            next2BB.preList.add(preBB);
        }
        // 删掉那个块
        followingBB.node.removeSelf();
        System.out.println("合并完");
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
                // 跳过删过的
                if (bb.isDelete) continue;
                if (bb.preList.size() == 0) {
                    // 消除没有前驱的块
                    retNeedDo = true;
                    processed = true;
                    for (BasicBlock nextBB : bb.nextList) {
                        nextBB.preList.remove(bb);
                        bb.fixPhiInBlock(nextBB);
                    }
                    bb.removeSelfIncludingInst();
                } else if (bb.preList.size() == 1) {
                    // 检查是不是两个相互循环，其他块无法到达的块
                    var preBB = bb.preList.get(0);
                    if (preBB.preList.size() == 1 && preBB.preList.get(0) == bb) {
                        retNeedDo = true;
                        processed = true;
                        for (BasicBlock nextBB : bb.nextList) {
                            nextBB.preList.remove(bb);
                            bb.fixPhiInBlock(nextBB);
                        }
                        bb.removeSelfIncludingInst();
                        for (BasicBlock nextBB : preBB.nextList) {
                            nextBB.preList.remove(bb);
                            preBB.fixPhiInBlock(nextBB);
                        }
                        preBB.removeSelfIncludingInst();
                    }
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
                        bb.list.getFirst().getElement().getTag() == Instruction.TAG.BR &&
                        !usedByPhi(bb)) {
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

    private boolean usedByPhi(BasicBlock bb) {
        for (int i = 0; i < bb.useList.size(); i++) {
            Use use = bb.useList.get(i);
            if (use.getUser() instanceof MemoryInst.Phi) {
                System.out.println("有phi不能消除基本块");
                return true;
            }
            ;
        }
        return false;
    }

    private void removeOnlyBrBlock(BasicBlock bb) {
        System.out.println("消除单br基本块");
        var nextBB = bb.nextList.get(0);
        bb.replaceAllBrUseWith(nextBB); // 只可能有一个后继,换Br
        bb.fixPhi(bb.preList); // 修正所有用到这个块的phi
        for (BasicBlock preBB : bb.preList) {
            preBB.nextList.remove(bb);
            preBB.nextList.add(nextBB);
            nextBB.preList.remove(bb);
            nextBB.preList.add(preBB);
        }
        bb.removeSelfIncludingInst();
    }
}
