package ir;

import java.util.LinkedList;

/**
 * Value类
 * 包含type、name属性
 * useList表示所有使用这个Value的Use的链表
 * addInUseList()用于向useList中添加Use
 */
public abstract class Value {
    public Value(Type type) {
        this.type = type;
    }

    public final Type type;

    public Type getType() {
        return type;
    }

    public String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public final LinkedList<Use> useList = new LinkedList<>();

    public void addInUseList(Use use) {
        useList.add(use);
    }

    public void replaceAllUseWith(Value v) {
        for (Use use : useList) {
            use.setValue(v);
            v.useList.add(use);
        }
    }

}
