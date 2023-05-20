package ir;

import ir.values.Function;
import ir.values.GlobalVariable;
import util.IList;

import java.util.LinkedList;

/**
 * Module类 我们编译程序只需要一个Module
 * 包含functionList和globalVariableList
 * functionList使用侵入式链表实现 节点和Value紧密联系 易于遍历 且能表示父的信息
 * 大体上程序的结构可以通过
 * Module->Function->BasicBlock->Instruction遍历出所有信息
 */
public class Module {
    public final LinkedList<GlobalVariable> globalVariableList;
    public IList<Function, Module> functionList;

    public Module() {
        functionList = new IList<>(this);
        globalVariableList = new LinkedList<>();
    }

    public void addGlobalVariable(GlobalVariable glbVar) {
        globalVariableList.add(glbVar);
    }

    public LinkedList<GlobalVariable> getGlobalVariableList() {
        return globalVariableList;
    }
}
