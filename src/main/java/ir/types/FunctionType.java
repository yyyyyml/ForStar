package ir.types;

import ir.Type;

import java.util.ArrayList;

/**
 * 函数类型
 * 包含返回类型和参数类型列表 不含值
 */
public class FunctionType extends Type {
    private Type retType;// 返回类型
    private ArrayList<Type> paramTypeList; //参数类型列表

    public FunctionType(Type retType, ArrayList<Type> paramsTypeList) {
        this.retType = retType;
        this.paramTypeList = paramsTypeList;
    }

    public Type getRetType() {
        return retType;
    }

    public ArrayList<Type> getParamTypeList() {
        return paramTypeList;
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
