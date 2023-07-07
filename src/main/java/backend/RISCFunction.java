package backend;

import backend.operands.FloatVirtualRegister;
import backend.operands.Memory;
import backend.operands.VirtualRegister;
import ir.Instruction;
import ir.Value;
import ir.values.BasicBlock;
import ir.values.Function;
import util.IList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class RISCFunction {


    public String funcName;
    public int parameterSize;
    public int virtualRegisterIndex = 0;
    public int floatVirtualRegisterIndex = 0;
    public HashMap<Value, FloatVirtualRegister> valueFloatVrMap;
    public HashMap<Value, VirtualRegister> valueVRMap;
    public HashMap<Value, Memory> valueMemoryHashMap;
    public HashMap<Value, RISCOperand> valueRISCOperandHashMap;
    public HashMap<RISCOperand, Value> riscOperandValueHashMap;
    public HashMap<Value, Integer> funcParameters;
    public HashMap<Value, Integer> myfuncParameters;
    public int localStackIndex = 20;
    public int operandStackCounts = 0;
    public int stackSize;
    public int stackIndex;
    private LinkedList<RISCBasicBlock> BasicBlockList;
    private Function irFunction;
    public RISCModule riscModule;
    public Boolean addEndBlock = false;
    public Boolean isBuildIn = false;

    /**
     * Function生成函数
     * 遍历IrFunction的block
     *
     * @param irFunc
     */
    public RISCFunction(Function irFunc, RISCModule riscModule) {

        myfuncParameters = new HashMap<>();
        funcParameters = new HashMap<>();
        valueFloatVrMap = new HashMap<>();
        valueVRMap = new HashMap<>();
        valueMemoryHashMap = new HashMap<>();
        valueRISCOperandHashMap = new HashMap<>();
        riscOperandValueHashMap = new HashMap<>();
        BasicBlockList = new LinkedList<>();
        this.irFunction = irFunc;
        this.funcName = irFunc.getName();
        this.parameterSize = irFunc.getParamList().size();
        this.riscModule = riscModule;
        if (irFunc.isBuiltin()) isBuildIn = true;
        //为函数本身参数建立map
        if (irFunc.getParamList().size() != 0) {
            int intCount = 0;
            int floatCount = 0;
            int stackIndex = 8;
            for (Value v : irFunc.getParamList()) {
                if (v.getType().isIntegerType()) {
                    if (intCount < 8) {
                        myfuncParameters.put(v, intCount++);
                    } else {
                        myfuncParameters.put(v, stackIndex++);


                    }
                } else if (v.getType().isFloatType()) {
                    if (floatCount < 8) {
                        myfuncParameters.put(v, floatCount++);
                    } else {
                        myfuncParameters.put(v, stackIndex++);

                    }
                }
            }
        }


        //寻找函数内参数
        for (IList.INode<BasicBlock, Function> bbInode : irFunc.list) {
            BasicBlock irBB = bbInode.getElement();
            for (IList.INode<Instruction, BasicBlock> Inode : irBB.list) {
                Instruction curInst = Inode.getElement();
                if (curInst.getTag() == Instruction.TAG.CALL) {
                    int paraCount = curInst.getNumOP() - 1;
                    int intCount = 0;
                    int floatCount = 0;
                    int stackIndex = 8;
                    for (int i = 0; i <= paraCount; i++) {
                        Value v = curInst.getOperandAt(i);
                        if (v.getType().isIntegerType()) {
                            if (intCount < 8) {
                                funcParameters.put(v, intCount++);
                            } else {
                                funcParameters.put(v, stackIndex++);

                            }
                        } else if (v.getType().isFloatType()) {
                            if (floatCount < 8) {
                                funcParameters.put(v, floatCount++);
                            } else {
                                funcParameters.put(v, stackIndex++);

                            }
                        }
                        //计算参数所需占用的栈空间
                        operandStackCounts = Math.max(operandStackCounts, stackIndex - 8);
                    }
                }
            }
        }

        //记录结束块位置
        Stack<Integer> stack = new Stack<>();
        Integer blockIndex = 0;
        for (IList.INode<BasicBlock, Function> bbInode : irFunc.list) {
            blockIndex++;
            System.out.println(funcName + "\t" + "localindex=" + localStackIndex);
            addEndBlock = false;
            RISCBasicBlock curBB = new RISCBasicBlock(bbInode.getElement(), irFunc, this);
            BasicBlockList.add(curBB);
            if (addEndBlock) {
                System.out.println("!!!" + funcName + "\t" + "localindex=" + localStackIndex);
                stack.push(blockIndex);
            }
        }

        //倒序加入结束块
        while (!stack.empty()) {
            RISCBasicBlock lastBB = new RISCBasicBlock(1, irFunc, this);
            BasicBlockList.add(stack.pop(), lastBB);
        }

        RISCBasicBlock firstBB = new RISCBasicBlock(0, irFunc, this);
        BasicBlockList.add(0, firstBB);


    }

    public LinkedList<RISCBasicBlock> getBasicBlockList() {
        return BasicBlockList;
    }
}
