package ir.values;

import ir.Type;
import ir.Value;
import ir.types.FunctionType;
import util.IList;

import java.util.ArrayList;
import java.util.List;

/**
 * 函数类
 * 待修改
 */
public class Function extends Value {
    private boolean isBuiltin = false;//是否是库函数
    public IList<BasicBlock, Function> list;//src/util/IList
    public IList.INode<Function, Module> node;
    private ArrayList<Function> caller;//会在函数内联的时候被用到，用来记录调用自己的函数
    private ArrayList<Function> callee;//用来记录自己调用的函数
    private ArrayList<Param> paramList;//形参列表

    public Function(Type type, boolean isBuiltin) {
        super(type);
        list = new IList<>(this);
        node = new IList.INode<>(this, null, null);
        paramList = new ArrayList<>();
        caller = new ArrayList<>();
        callee = new ArrayList<>();
        this.isBuiltin = isBuiltin;
        buildParams();
    }

    private void buildParams() {
        var funcTy = this.type;
        var arr = ((FunctionType) funcTy).getParamsTypeList();
        for (int i = 0; i < arr.size(); i++) {
            paramList.add(new Param(arr.get(i), i));
        }
    }

    public ArrayList<Param> getParamList() {
        return paramList;
    }

    public boolean isBuiltin() {
        return isBuiltin;
    }


    //形参，记录各位置类型，不含值
    public class Param extends Value {
        private List<Value> bounds;
        private int position;

        public Param(Type type, int position) {
            super(type);
            this.position = position;
        }

        public void setBounds(List<Value> bounds) {this.bounds = bounds;}

        @Override
        public String toString() {
            return this.type + " " + this.name;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(((FunctionType) this.type).getRetType())
                .append(" @")
                .append(this.name)
                .append("(");
        this.paramList.forEach(
                param -> {
                    sb.append(param).append(",");
                }
        );
        if (paramList.size() != 0) sb.deleteCharAt(sb.length() - 1);//把最后一个逗号删掉
        sb.append(")");
        return sb.toString();
    }
}
