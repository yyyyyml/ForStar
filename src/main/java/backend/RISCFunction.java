package backend;

import backend.riscvalues.VirtualRegister;
import ir.Value;
import ir.values.BasicBlock;
import ir.values.Function;
import util.IList;

import java.util.HashMap;
import java.util.LinkedList;

public class RISCFunction {



    public String funcName;
    private LinkedList<RISCBasicBlock> BasicBlockList;
    private  Function IRFunction ;
    public int parameterSize;
    public int virtualRegisterIndex = 0;
    public HashMap<Value, VirtualRegister> valueVRMap;


    public LinkedList<RISCBasicBlock> getBasicBlockList() {
        return BasicBlockList;
    }

    /**
     * Function生成函数
     * 遍历IrFunction的block
     * @param irFunc
     */
    public RISCFunction(Function irFunc) {
        valueVRMap=new HashMap<>();
        BasicBlockList = new LinkedList<>();
        this.IRFunction  = irFunc;
        this.funcName = irFunc.getName();
        this.parameterSize = irFunc.getParamList().size();
        RISCBasicBlock firstBB = new RISCBasicBlock(0,irFunc,this);
        BasicBlockList.add(firstBB);
        for(IList.INode<BasicBlock, Function> bbInode : irFunc.list){
            RISCBasicBlock curBB = new RISCBasicBlock(bbInode.getElement(),irFunc,this);
            BasicBlockList.add(curBB);
        }
        RISCBasicBlock lastBB = new RISCBasicBlock(1,irFunc,this);
        BasicBlockList.add(lastBB);
    }
}
