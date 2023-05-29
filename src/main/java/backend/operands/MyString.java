package backend.operands;

import backend.RISCOperand;

public class MyString extends RISCOperand {
    public String name;

    public MyString(String s) {
        this.name = s;
    }

    @Override
    public String emit() {
        return name;
    }


}
