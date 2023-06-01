package ast;

import ir.Value;

import java.util.ArrayList;
import java.util.HashMap;

/*
符号表类，是visitor遍历语法树时需要查询的“字典”，具体功能如下：
1.能够区分此时是在程序的全局区域还是某个局部函数区域，从而确保函数变量的正确使用。（用ArrayList来实现）
2.能够添加新的“变量名-变量值”对进入当前层的符号表中。（用HashMap来实现，从而能实现存储键值对）
3.能够查询当前层的“变量名”具体的“变量值”
4.如果当前区域要求访问未被定义的变量或者是当前代码区域不可访问的变量需要报错。
 */

public class Scope {

    private final ArrayList<HashMap<String, Value>> symbolTables = new ArrayList<>();
    private final ArrayList<HashMap<String, String>> symbolTypes = new ArrayList<>();

    public Scope() {
        //每一个HashMap代表一层，最外层代表全局区域，初始化时添加一个新的空的当前层符号表。
        symbolTables.add(new HashMap<>());
        symbolTypes.add(new HashMap<>());
    }

    //返回当前层的符号表
    private HashMap<String, Value> curTable() {
        return symbolTables.get(symbolTables.size() - 1);
    }

    //判断当前的符号表层是否是全局的
    public boolean isGlobal() {
        return symbolTables.size() == 1;
    }

    //向符号表中添加新的一层符号表
    public void pushTable() {
        symbolTables.add(new HashMap<>());
    }

    //弹出当前符号表
    public void popTable() {
        symbolTables.remove(symbolTables.size() - 1);
    }

    //添加新符号键值对
    public void addSymbol(String name, Value value,String symbolType) {
        //先检查是否重名
        if (this.isDuplicateSymbol(name)) {
            throw new RuntimeException("Name" + "'"+name+"'"+"has already benn used!");
        }
        if(symbolType == "const")
        {
            (this.symbolTypes.get(0)).put(name,symbolType);
        }
        curTable().put(name, value);
    }

    public void addSymbol(String name, Value value) {
        //先检查是否重名
        if (this.isDuplicateSymbol(name)) {
            throw new RuntimeException("Name" + "'"+name+"'"+"has already benn used!");
        }
        curTable().put(name, value);
    }

    public boolean isDuplicateSymbol(String name) {
        return curTable().get(name) != null;
    }
    public boolean checkVarType(String name){
        String symType = symbolTypes.get(0).get(name);
        if(symType=="const") {
            return true;
        }
        else {
            return false;
        }
    }
    public Value getVal(String name) {
        //要从当前层开始搜索
        for (int i = symbolTables.size() - 1; i >= 0; i--) {
            Value val = symbolTables.get(i).get(name);

            if (val != null) return val;
        }
        return null;
    }

}

