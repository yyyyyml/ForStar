package backend;

import backend.operands.FloatVirtualRegister;
import backend.operands.Memory;
import backend.operands.VirtualRegister;
import ir.Value;
import ir.values.BasicBlock;
import ir.values.Function;
import util.IList;

import java.util.HashMap;
import java.util.LinkedList;

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
    public int localStackIndex = 20;
    public int operandStackCounts = 0;
    public int stackSize;
    public int stackIndex;
    private LinkedList<RISCBasicBlock> BasicBlockList;
    private Function irFunction;
    public RISCModule riscModule;

    /**
     * Function生成函数
     * 遍历IrFunction的block
     *
     * @param irFunc
     */
    public RISCFunction(Function irFunc, RISCModule riscModule) {


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


        for (IList.INode<BasicBlock, Function> bbInode : irFunc.list) {
            RISCBasicBlock curBB = new RISCBasicBlock(bbInode.getElement(), irFunc, this);
            BasicBlockList.add(curBB);
        }

        RISCBasicBlock firstBB = new RISCBasicBlock(0, irFunc, this);
        BasicBlockList.add(0, firstBB);
        RISCBasicBlock lastBB = new RISCBasicBlock(1, irFunc, this);
        BasicBlockList.add(lastBB);
    }

    public LinkedList<RISCBasicBlock> getBasicBlockList() {
        return BasicBlockList;
    }
}
