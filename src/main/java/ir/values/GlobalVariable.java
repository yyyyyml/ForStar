package ir.values;

import ir.Type;
import ir.User;
import ir.types.PointerType;

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
        if(type.isIntegerType()) this.init = Constant.ConstantInt.getConstantInt(0);
        else if(type.isFloatType()) this.init = Constant.ConstantFloat.getConstantFloat(0);
    }

    public GlobalVariable(String name, Constant initval) {
        super(new PointerType(initval.getType()));
        this.setName(name);
        this.init = initval;
    }
    public void setConst() {
        this.isConst = true;
    }
    public boolean isConstant() {
        return isConst;
    }
    public void setInit(Constant init) {
        this.init = init;
    }
    public Constant getInitVal() {
        return init;
    }
    public boolean isArray() {
        return ((PointerType)this.getType()).getPointedType().isArrayType();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName()).append(" = dso_local ");
        if (isConst) sb.append("constant ");
        else sb.append("global ");
        var tmp = ((PointerType) this.getType());
        if (tmp.getPointedType().isIntegerType()) {
            sb.append(tmp.getPointedType().toString()).append(" ");
            //没有初始化的值默认其为0
            if (this.init == null) sb.append("0 ");
            else sb.append(((Constant.ConstantInt) this.init).getVal());
        } else if (tmp.getPointedType().isFloatType()) {
            sb.append(tmp.getPointedType().toString()).append(" ");
            //没有初始化的值默认其为0
            if (this.init == null) sb.append("0.0 ");
            else sb.append(((Constant.ConstantFloat) this.init).getVal());
        } else if (tmp.getPointedType().isArrayType()) {
            if (this.init == null) {
                sb.append(tmp.getPointedType().toString()).append(" zeroinitializer ");
            } else sb.append(init);
        }
        return sb.toString();
    }

}
