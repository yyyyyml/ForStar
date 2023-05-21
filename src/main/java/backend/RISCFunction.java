package backend;

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
    public HashMap<Value, VirtualRegister> valueVRMap;
    public HashMap<Value, Memory> valueMemoryHashMap;
    public int localStackIndex = 20;
    private LinkedList<RISCBasicBlock> BasicBlockList;
    private Function irFunction;
    public int stackSize;
    public int stackIndex;



    /**
     * Function生成函数
     * 遍历IrFunction的block
     *
     * @param irFunc
     */
    public RISCFunction(Function irFunc) {
        valueVRMap = new HashMap<>();
        valueMemoryHashMap = new HashMap<>();
        BasicBlockList = new LinkedList<>();
        this.irFunction = irFunc;
        this.funcName = irFunc.getName();
        this.parameterSize = irFunc.getParamList().size();

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
