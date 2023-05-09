package ir.values;

import ir.Type;
import ir.User;
import ir.Value;
import ir.types.PointerType;

import java.util.LinkedList;

/**
 * 全局变量都是PointerType
 * 待修改
 */
public class GlobalVariable extends User {
    public boolean isConst = false;
    public Constant init;//初始化值

    public void setConst() {this.isConst = true;}

    public GlobalVariable(String name, Type type) {
        super(new PointerType(type));
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name).append(" = dso_local ");
        if (isConst) sb.append("constant ");
        else sb.append("global ");
        var tmp = ((PointerType) this.type);
        if (tmp.getPointedType().isIntegerType()) {
            sb.append(tmp.getPointedType().toString());
            sb.append(" ");
            //没有初始化的值默认其为0
            sb.append(this.init == null ? "0 " : ((Constant.ConstantInt) this.init).getVal());
        } else if (tmp.getPointedType().isArrayType()) {
            if (this.init == null) {
                sb.append(tmp.getPointedType().toString());
                sb.append(" ");
                sb.append("zeroinitializer ");
            } else sb.append(init);
        }
        return sb.toString();
    }

}
