package backend.instructions;

import backend.RISCInstruction;

public class CallInstruction extends RISCInstruction {
    public String funcName;

    public CallInstruction(String funcName) {
        this.funcName = funcName;
        this.type = ITYPE.call;
    }

    public String emit() {
        return this.type.name() + " " + funcName;
    }
}
