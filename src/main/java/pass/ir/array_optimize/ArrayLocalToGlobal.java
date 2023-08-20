package pass.ir.array_optimize;

import ir.*;
import ir.Instructions.MemoryInst;
import ir.Module;
import ir.Instructions.TerminatorInst;
import ir.types.ArrayType;
import ir.values.BasicBlock;
import ir.values.Constant;
import ir.values.Function;
import ir.values.GlobalVariable;
import pass.ir.BaseIRPass;
import util.IList;

import java.util.ArrayList;

public class ArrayLocalToGlobal implements BaseIRPass {
    int nameCounter = 0;
    ArrayList<Constant> initList = new ArrayList<>();

    @Override
    public boolean run(Module module) {
        for (IList.INode<Function, Module> funcInode : module.functionList) {
            Function func = funcInode.getElement();
            for (IList.INode<BasicBlock, Function> bbInode : func.list) {
                BasicBlock bb = bbInode.getElement();
                for (IList.INode<Instruction, BasicBlock> instInode : bb.list) {
                    Instruction inst = instInode.getElement();
                    if (inst instanceof MemoryInst.Alloca allocaInst &&
                            allocaInst.allocatedType instanceof ArrayType arrType) {
                        if (isSingleArray(allocaInst)) {
                            initList.clear();
                            int len = arrType.getLen();
                            for (int i = 0; i < len; i++) {
                                if (arrType.getContainedType() instanceof Type.IntegerType)
                                    initList.add(Constant.ConstantInt.getConstantInt(0));
                                else
                                    initList.add(Constant.ConstantFloat.getConstantFloat(0));
                            }
                            doMotion(allocaInst);
                            Constant.ConstantArray init = new Constant.ConstantArray(arrType, initList);
                            GlobalVariable newGv = new GlobalVariable(newName(), init);
                            module.globalVariableList.add(newGv);
                            allocaInst.replaceAllUseWith(newGv);
                        }
                    }
                }
            }
        }
        return false;
    }

    private void doMotion(MemoryInst.Alloca allocaInst) {
        for (int i = 0; i < allocaInst.useList.size(); i++) {
//            System.out.println("doMotion: "+allocaInst.useList.size());
            Use use = allocaInst.useList.get(i);
            Instruction gep = (Instruction) use.getUser();
            if (gep instanceof MemoryInst.GEP gepInst) {
                System.out.println("gep: " + gepInst);
                if (!(gepInst.getOperandAt(2) instanceof Constant.ConstantInt)) {
                    System.out.println("不是常数索引");
                    continue;
//                    return false; // 如果不是常数索引，不能处理
                }

                for (int j = 0; j < gepInst.useList.size(); j++) {
//                    System.out.println("gepInst.useList.size() "+gepInst.useList.size());
                    Use useOfGEP = gepInst.useList.get(j);
                    Constant.ConstantInt gepIndexValue = (Constant.ConstantInt) gepInst.getOperandAt(2);
                    int gepIndex = gepIndexValue.getVal();
                    Instruction user = (Instruction) useOfGEP.getUser();
                    // 先处理store？
                    if (user instanceof MemoryInst.Store storeInst) {
                        var storeValue = storeInst.getOperandAt(0);
//                        System.out.println("storeValue: "+storeValue);
                        if (storeValue instanceof Constant constVal) {
                            initList.set(gepIndex, constVal);
                            storeInst.node.removeSelf();
                            storeInst.removeAllOperand();
//                            System.out.println("处理store: ");
                        }
                    }
                    if (user instanceof TerminatorInst.Call callInst) {
                        Function calledFunc = (Function) callInst.getOperandAt(0);
                        if (calledFunc.getName().equals("memset")) {
                            callInst.node.removeSelf();
                        }
                    }
                    if (user instanceof MemoryInst.Load loadInst) {
//
//                        loadInst.replaceAllUseWith(initList.get(gepIndex)); // 把load替换成那个数组中的常数元素
//                        loadInst.removeAllOperand();
//                        loadInst.node.removeSelf();
                    }

                }
            }
        }
    }

    private boolean isSingleArray(User addr) {
        for (int i = 0; i < addr.useList.size(); i++) {
            Use use = addr.useList.get(i);
            Instruction gep = (Instruction) use.getUser();
            if (gep instanceof MemoryInst.GEP gepInst) {
                System.out.println("gep: " + gepInst);
                if (!(gepInst.getOperandAt(2) instanceof Constant.ConstantInt)) {
                    System.out.println("不是常数索引");
//                    return false; // 如果不是常数索引，不能处理
                }
                for (int j = 0; j < gepInst.useList.size(); j++) {
                    Use useOfGEP = gepInst.useList.get(j);
                    Instruction user = (Instruction) useOfGEP.getUser();
//                    if (user instanceof MemoryInst.Store) return false; // 说明addr是最后一层数组，且被赋值
                    if (user instanceof MemoryInst.GEP) {
                        System.out.println("多层数组" + user);
                        return false; // 先只处理单层数组，多层不能处理
//                        return canEliminate(gepInst); // 说明addr还嵌套有其他数组，需要递归调用
                    }
                }
            }
        }
        return true;
    }

    private String newName() {
        String name = String.valueOf(nameCounter);
        nameCounter++;
        return name;
    }
}
