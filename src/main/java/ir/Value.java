package ir;

import java.util.LinkedList;

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

    public void replaceAllUseWith(Value v) {
        for (Use use : useList) {
            use.setValue(v);
            v.useList.add(use);
        }
    }

}
