package backend;

import ir.values.BasicBlock;
import ir.values.Function;
import util.IList;

import java.util.LinkedList;

public class RISCFunction {



    public String funcname;
    private LinkedList<RISCBasicBlock> BasicBlockList;
    private  Function IRFunction ;
    public int parameterSize;

    public LinkedList<RISCBasicBlock> getBasicBlockList() {
        return BasicBlockList;
    }

    public RISCFunction(Function iRfunc) {
        BasicBlockList = new LinkedList<>();
        this.IRFunction=iRfunc;
        this.funcname=iRfunc.getName();
        this.parameterSize=iRfunc.getParamList().size();
        RISCBasicBlock firstbb=new RISCBasicBlock(0,iRfunc);
        BasicBlockList.add(firstbb);
        for(IList.INode<BasicBlock, Function> bbInode : iRfunc.list){
            RISCBasicBlock curbb=new RISCBasicBlock(bbInode.getElement());
            BasicBlockList.add(curbb);
        }
        RISCBasicBlock lastbb=new RISCBasicBlock(1,iRfunc);
        BasicBlockList.add(lastbb);
    }
}
