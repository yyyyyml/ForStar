package backend.instructions;

import backend.RISCInstruction;

public class CallInstruction extends RISCInstruction {
    public String funcName;

    public CallInstruction(String funcName) {
        this.funcName = funcName;
        this.type = ITYPE.call;
    }

    public Boolean isCallBuildIn() {
        return funcName.equals("getint") || funcName.equals("getch") || funcName.equals("getfloat") ||
                funcName.equals("getarray") || funcName.equals("getfarray") || funcName.equals("putint") ||
                funcName.equals("putch") || funcName.equals("putfloat") || funcName.equals("putarray") ||
                funcName.equals("putfarray") || funcName.equals("_sysy_starttime") || funcName.equals("_sysy_stoptime") ||
                funcName.equals("memset");
    }

    @Override
    public boolean isDef(int position) {
        return false;
    }

    public String emit() {
        return this.type.name() + " " + funcName;
    }
}
