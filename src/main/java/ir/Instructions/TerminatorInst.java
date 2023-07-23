package ir.Instructions;

import ir.Instruction;
import ir.Type;
import ir.Use;
import ir.Value;
import ir.types.FunctionType;
import ir.values.BasicBlock;
import ir.values.Function;

import java.util.ArrayList;

/**
 * 表示基本块终止的指令
 * 包含RET,BR,CALL
 */
public class TerminatorInst {
    /**
     * 返回指令
     */
    public static class Ret extends Instruction {
        public Ret() {
            super(Type.VoidType.getType(), Instruction.TAG.RET, 0);
            needName = false;
        }

        public Ret(Value val) {
            super(Type.VoidType.getType(), TAG.RET, 1);
            this.setOperand(val, 0);
            needName = false;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            // ret i32 0
            // ret void
            sb.append("ret ");
            if (this.getNumOP() == 1) {
                sb.append(getOperandAt(0).getType() + " " + getOperandAt(0).getName());
            } else {
                sb.append("void ");
            }
            return sb.toString();
        }
    }

    /**
     * 跳转指令
     */
    public static class Br extends Instruction {
        //有条件跳转
        public BasicBlock curBlock;

        public Br(Value cond, BasicBlock trueBlock, BasicBlock falseBlock, BasicBlock curBlock) {
            super(Type.LabelType.getType(), TAG.BR, 3);
            setOperand(cond, 0);
            setOperand(trueBlock, 1);
            setOperand(falseBlock, 2);
            needName = false;
            this.curBlock = curBlock;
            curBlock.nextList.add(trueBlock);
            trueBlock.preList.add(curBlock);
            curBlock.nextList.add(falseBlock);
            falseBlock.preList.add(curBlock);

        }

        //无条件跳转
        public Br(BasicBlock trueBlock, BasicBlock curBlock) {
            super(Type.LabelType.getType(), TAG.BR, 1);
            setOperand(trueBlock, 0);
            needName = false;
            this.curBlock = curBlock;
            curBlock.nextList.add(trueBlock);
            trueBlock.preList.add(curBlock);
        }

        @Override
        public void setOperand(Value v, int i) {
            if (numOP == 3) {
                super.setOperand(v, i);
            } else {
                assert i < numOP && i >= 0;
                var bb = (BasicBlock) v;
                if (operandList.isEmpty()) {
                    // 没有操作数，新建
                    Use newUse = new Use(this, v, i);
                } else {
                    Use existingUse = operandList.get(0); // 只可能有一个操作数
                    existingUse.getValue().removeUse(existingUse);
                    existingUse.setValue(v);
                    v.addInUseList(existingUse);

                    BasicBlock preBlock = (BasicBlock) getOperandAt(0);
                    preBlock.preList.remove(curBlock);
                    curBlock.nextList.remove(preBlock);
                    curBlock.nextList.add(bb);
                    bb.preList.add(curBlock);
                }
            }


        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            // br i1 %17, label %18, label %19
            // br label %21
            sb.append("br ");
            if (this.getNumOP() == 1) {
                sb.append(getOperandAt(0).getType()).append(" %").append(getOperandAt(0).getName());
            }
            if (this.getNumOP() == 3) {
                sb.append(getOperandAt(0).getType()).append(" ").append(getOperandAt(0).getName()).append(", ");
                sb.append(getOperandAt(1).getType()).append(" %").append(getOperandAt(1).getName()).append(", ");
                sb.append(getOperandAt(2).getType()).append(" %").append(getOperandAt(2).getName());
            }
            sb.append("\n");
            return sb.toString();
        }
    }

    /**
     * 调用函数指令
     * 需要传入参数列表
     */
    public static class Call extends Instruction {
        private boolean retVoid;

        public Call(Function function, ArrayList<Value> paramList) {
            super(((FunctionType) function.getType()).getRetType(), TAG.CALL, paramList.size() + 1);
            if (this.getType().isVoidType()) {
                needName = false;
                retVoid = true;
            } else {
                needName = true;
                retVoid = false;
            }
            setOperand(function, 0);
            for (int i = 0; i < paramList.size(); i++) {
                setOperand(paramList.get(i), i + 1);
            }
        }

        public boolean isRetVoid() {
            return retVoid;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            // call void @fun(i32 %arg1, i32 %arg2)
            // %1 = call i32 @fun(i32 %arg1, i32 %arg2)
            if (isRetVoid()) {
                sb.append("call ").append(this.getType()).
                        append(" @").append(this.getOperandAt(0).getName());
            } else {
                sb.append(this.getName()).append(" = call ").append(this.getType()).
                        append(" @").append(this.getOperandAt(0).getName());
            }

            sb.append("(");
            for (int i = 1; i < this.getNumOP(); i++) {
                sb.append(this.getOperandAt(i).getType()).append(" ").append(this.getOperandAt(i).getName());
                if (i != this.getNumOP() - 1)
                    sb.append(", ");
            }
            sb.append(")");

            return sb.toString();
        }
    }
}
