package ir.values;

import ir.Type;
import ir.Value;
import ir.Module;
import ir.types.FunctionType;
import util.IList;

import java.util.ArrayList;
import java.util.List;

/**
 * 函数类 继承自Value类
 * 待修改
 */
public class Function extends Value {
    private boolean isBuiltin = false;//是否是库函数
    private ArrayList<Param> paramList;// 形参列表
    public IList<BasicBlock, Function> list;
    public IList.INode<Function, Module> node;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(((FunctionType) this.getType()).getRetType()).append(" @").append(this.getName());

        sb.append("(");
        for (int i = 0; i < paramList.size(); i++) {
            sb.append(paramList.get(i));
            if (i != paramList.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(")");

        return sb.toString();
    }

}
