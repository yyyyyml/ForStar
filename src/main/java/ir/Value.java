package ir;

import java.util.LinkedList;

public abstract class Value {
    private final Type type;

    public Value(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    private String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private final LinkedList<Use> useList = new LinkedList<>();

    public void replaceAllUseWith(Value v) {
        for (Use use : useList) {
            use.setV(v);
            v.useList.add(use);
        }
    }

}
