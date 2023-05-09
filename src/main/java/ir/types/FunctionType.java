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
}
