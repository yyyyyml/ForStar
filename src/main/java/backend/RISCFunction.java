package backend;

import backend.operands.FloatVirtualRegister;
import backend.operands.VirtualRegister;
import ir.Instruction;
import ir.Value;
import ir.values.BasicBlock;
import ir.values.Constant;
import ir.values.Function;
import org.antlr.v4.runtime.misc.Pair;
import util.IList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class RISCFunction {


    public String funcName;
    public int parameterSize;
    public int virtualRegisterIndex = 0;
    public int floatVirtualRegisterIndex = 0;

    public HashMap<Value, RISCOperand> valueRISCOperandHashMap;
    public HashMap<RISCOperand, Value> riscOperandValueHashMap;
    public HashMap<Value, Integer> funcParameters;
    public HashMap<Value, Integer> myfuncParameters;
    public HashMap<Value,LinkedList<Value>> phiMap;
    public HashMap<Value,LinkedList<Pair<Value,Value>>> blockPhiMap;
    public int localStackIndex = 16;
    public int operandStackCounts = 0;
    public int stackSize;
    public int stackIndex;
    private LinkedList<RISCBasicBlock> BasicBlockList;
    private Function irFunction;
    public RISCModule riscModule;
    public Boolean addEndBlock = false;
    public Boolean isBuildIn = false;
    public int phiCount = 0;
    public int floatPhiCount = 0;
    public HashMap<String,RISCBasicBlock> blockHashMap;
    public boolean[] intMyparaIsDef;
    public boolean[] floatMyparaIsDef;
    //第二版phi
    public HashMap<Value,LinkedList<Value>> preBlockMap;



    /**
     * Function生成函数
     * 遍历IrFunction的block
     *
     * @param irFunc
     */
    public RISCFunction(Function irFunc, RISCModule riscModule) {

        myfuncParameters = new HashMap<>();
        funcParameters = new HashMap<>();
        phiMap = new HashMap<>();
        blockPhiMap = new HashMap<>();
        blockHashMap = new HashMap<>();
        intMyparaIsDef = new boolean[8];
        floatMyparaIsDef = new boolean[8];
//        valueFloatVrMap = new HashMap<>();
//        valueVRMap = new HashMap<>();
//        valueMemoryHashMap = new HashMap<>();

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
                if (v.getType().isIntegerType() || v.getType().isPointerType() ) {
                    if (intCount < 8) {
                        myfuncParameters.put(v, intCount++);
                    } else {
                        myfuncParameters.put(v, stackIndex++);


                    }
                } else if (v.getType().isFloatType() ) {
                    if (floatCount < 8) {
                        myfuncParameters.put(v, floatCount++);
                    } else {
                        myfuncParameters.put(v, stackIndex++);

                    }
                }
            }
        }

        //提前扫一遍，预处理
        //寻找函数内参数，以及phi内参数
        for (IList.INode<BasicBlock, Function> bbInode : irFunc.list) {
            BasicBlock irBB = bbInode.getElement();
            for (IList.INode<Instruction, BasicBlock> Inode : irBB.list) {
                Instruction curInst = Inode.getElement();
                if (curInst.getTag() == Instruction.TAG.CALL) {
                    if (curInst.getType().isIntegerType()||curInst.getType().isPointerType())
                    {
                        intMyparaIsDef[0] = true;
                    }
                    else if(curInst.getType().isFloatType()){
                        floatMyparaIsDef[0] = true;
                    }
                    int paraCount = curInst.getNumOP() - 1;
                    int intCount = 0;
                    int floatCount = 0;
                    int stackIndex = 8;
                    for (int i = 0; i <= paraCount; i++) {
                        Value v = curInst.getOperandAt(i);
                        if (v.getType().isIntegerType() || v.getType().isPointerType()) {
                            if (intCount < 8) {
                                intMyparaIsDef[intCount] = true;
                                funcParameters.put(v, intCount++);
                            } else {
                                funcParameters.put(v, stackIndex++);

                            }
                        } else if (v.getType().isFloatType()) {
                            if (floatCount < 8) {
                                floatMyparaIsDef[floatCount] = true;
                                funcParameters.put(v, floatCount++);
                            } else {
                                funcParameters.put(v, stackIndex++);

                            }
                        }
                        //计算参数所需占用的栈空间
                        operandStackCounts = Math.max(operandStackCounts, stackIndex - 8);
                    }
                }
                else if(curInst.getTag() == Instruction.TAG.PHI){




                     //phi初版
                    RISCOperand dst;
                    if(curInst.getType().isIntegerType()||curInst.getType().isPointerType()){
                        phiCount++;
                        //虚拟寄存器
                        dst = new VirtualRegister(virtualRegisterIndex++);
                        //栈
//                        localStackIndex += 8;
//                        dst = new Memory(localStackIndex, 1);
                    }
                    else {
                        floatPhiCount++;
                        //虚拟寄存器
                        dst = new FloatVirtualRegister(floatVirtualRegisterIndex++);
                        //栈
//                        localStackIndex += 8;
//                        dst = new Memory(localStackIndex, 1);
                    }
                    System.out.println(curInst.getName()+"->"+dst.emit());
                    valueRISCOperandHashMap.put(curInst,dst);
                    int paraCount = curInst.getNumOP();
                    if(paraCount % 2 != 0 ){
                        throw new RuntimeException("这个phi的操作数不是偶数"+curInst);
                    }
                    for(int i = 0 ; i < paraCount ; i = i + 2){
                        Value v1 = curInst.getOperandAt(i);
                        Value v2 = curInst.getOperandAt(i+1);
                        if(false&&(!(v1 instanceof Constant))&&(!myfuncParameters.containsKey(v1))){
                            if ((phiMap.containsKey(v1))){
                                phiMap.get(v1).add(curInst);
                            }
                            else {
                                LinkedList<Value> list = new LinkedList<>();
                                list.add(curInst);
                                phiMap.put(v1,list);
                            }

                        }
                        //判断v1为本函数参数或是常数，把curinst和v1以pair的形式存入list中
                        else {
                            //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
                            if(blockPhiMap.containsKey(v2)){
                                blockPhiMap.get(v2).add(new Pair<>(curInst,v1));
                                //System.out.println(v2.getName()+": "+v1+"->"+curInst.getName());
                            }
                            else {
                                LinkedList<Pair<Value,Value>> list = new LinkedList<>();
                                list.add(new Pair<>(curInst,v1));
                                blockPhiMap.put(v2,list);
                                //System.out.println(v2.getName()+": "+v1+"->"+curInst.getName());
                            }
                        }
                    }
                }
            }
        }
        RISCBasicBlock myParaBB = new RISCBasicBlock(2,irFunc,this);
        BasicBlockList.add(myParaBB);


        //记录结束块位置
        Stack<Integer> stack = new Stack<>();
        Integer blockIndex = 1;
        for (IList.INode<BasicBlock, Function> bbInode : irFunc.list) {
            blockIndex++;
//            System.out.println(funcName + "\t" + "localindex=" + localStackIndex);
            addEndBlock = false;
            RISCBasicBlock curBB = new RISCBasicBlock(bbInode.getElement(), irFunc, this);
            BasicBlockList.add(curBB);
            if (addEndBlock) {
//                System.out.println("!!!" + funcName + "\t" + "localindex=" + localStackIndex);
                stack.push(blockIndex);
            }
            // 加这里吗
            if (blockIndex == 2) {
                curBB.prelist.add(myParaBB);
                myParaBB.nextlist.add(curBB);
            }
        }

        for (RISCBasicBlock BB : BasicBlockList) {
            for (String BBName : BB.nextBlocknameList){
                RISCBasicBlock nextBB = blockHashMap.get(BBName);
                BB.nextlist.add(nextBB);
                nextBB.prelist.add(BB);
            }
        }
//        for (RISCBasicBlock BB : BasicBlockList) {
//            System.out.println("THE "+BB.getBlockName()+" nextlist is ");
//            for (RISCBasicBlock nextBB : BB.nextlist){
//                System.out.println(nextBB.getBlockName());
//            }
//            System.out.println("THE "+BB.getBlockName()+" prelist is ");
//            for (RISCBasicBlock preBB : BB.prelist){
//                System.out.println(preBB.getBlockName());
//            }
//        }
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
