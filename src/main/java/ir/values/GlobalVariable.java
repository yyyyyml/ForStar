package ir.values;

import ir.Type;
import ir.User;
import ir.Value;
import ir.types.PointerType;

import java.util.LinkedList;

/**
 * 全局变量类 继承自User类
 * 全局变量都是PointerType
 */
public class GlobalVariable extends User {
    public boolean isConst = false;
    public Constant init; // 初始化值

    public GlobalVariable(String name, Type type) {
        super(new PointerType(type));
        this.setName(name);
    }

    public void setConst() {this.isConst = true;}

    public void setInit(Constant init) {
        this.init = init;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName()).append(" = dso_local ");
        if (isConst) sb.append("constant ");
        else sb.append("global ");
        var tmp = ((PointerType) this.getType());
        if (tmp.getPointedType().isIntegerType()) {
            sb.append(tmp.getPointedType().toString());
            sb.append(" ");
            //没有初始化的值默认其为0
            if (this.init == null) sb.append("0 ");
            else sb.append(((Constant.ConstantInt) this.init).getVal());
        } else if (tmp.getPointedType().isFloatType()) {
            sb.append(tmp.getPointedType().toString());
            sb.append(" ");
            //没有初始化的值默认其为0
            if (this.init == null) sb.append("0.0 ");
            else sb.append(((Constant.ConstantFloat) this.init).getVal());
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
