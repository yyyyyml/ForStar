package ir.types;

import ir.Type;

import java.util.ArrayList;

public class FunctionType extends Type {
    private Type retType;// 返回类型

    public Type getRetType() {return retType;}

    private ArrayList<Type> paramsTypeList; //参数类型列表

    public ArrayList<Type> getParamsTypeList() {return paramsTypeList;}

    public FunctionType(Type retType, ArrayList<Type> paramsTypeList) {
        this.retType = retType;
        this.paramsTypeList = paramsTypeList;
    }
    /*public static FunctionType getType(Type retType, ArrayList<Type> argTypes) {
        var key = new PrototypeKey(retType, argTypes);
        if (pool.containsKey(key)) {
            return pool.get(key);
        }

        var newType = new FunctionType(retType, argTypes);
        pool.put(key, newType);
        return newType;
    }*/
}
