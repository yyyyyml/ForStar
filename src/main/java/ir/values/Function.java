package ir.values;

import ir.Module;
import ir.Type;
import ir.Value;
import ir.types.FunctionType;
import util.IList;

import java.util.ArrayList;

/**
 * 函数类 继承自Value类
 * 待修改
 */
public class Function extends Value {
    public IList<BasicBlock, Function> list;
    public IList.INode<Function, Module> node;
    private boolean isBuiltin = false;//是否是库函数
    private ArrayList<Param> paramList;// 形参列表
    public boolean canInline = false;

    public Function(Type type, boolean isBuiltin) {
        super(type);
        list = new IList<>(this);
        node = new IList.INode<>(this, null, null);
        // 构建参数
        paramList = new ArrayList<>();
        var paramTypeList = ((FunctionType) type).getParamTypeList();
        for (int i = 0; i < paramTypeList.size(); i++) {
            paramList.add(new Param(paramTypeList.get(i), i));
        }
        this.isBuiltin = isBuiltin;
    }

    public ArrayList<Param> getParamList() {
        return this.paramList;
    }

    public boolean isBuiltin() {
        return this.isBuiltin;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(((FunctionType) this.getType()).getRetType()).append(" @").append(this.getName());

        sb.append("(");
        for (int i = 0; i < paramList.size(); i++) {
            if (this.isBuiltin()) {
                // 库函数不需要变量名
                sb.append(getParamList().get(i).getType());
            } else {
                sb.append(paramList.get(i));
            }
            if (i != paramList.size() - 1) {
                sb.append(", ");
            }

        }
        sb.append(")");

        return sb.toString();
    }

    // 形参，只记录各位置类型
    public class Param extends Value {
        private int position;

        public Param(Type type, int position) {
            super(type);
            this.position = position;
        }

        @Override
        public String toString() {
            return this.getType() + " " + this.getName();
        }
    }

}
