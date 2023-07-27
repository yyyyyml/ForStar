package backend.instructions;

import backend.RISCInstruction;

public class CallInstruction extends RISCInstruction {
    public String funcName;

    public CallInstruction(String funcName) {
        this.funcName = funcName;
        this.type = ITYPE.call;
    }

    @Override
    public boolean idDef(int position) {
        return false;
    }

    public String emit() {
        return this.type.name() + " " + funcName;
    }
}
