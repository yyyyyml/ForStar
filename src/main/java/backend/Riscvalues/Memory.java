package backend.Riscvalues;

public class Memory {
    public Register basicAddress;
    public int offset;

    public Memory(int offset, int basicAddress) {
        this.offset=offset;
        this.basicAddress=new Register(basicAddress);
    }

    public String emit() {
        if(offset==0){
            return "("+basicAddress.type.name()+")";
        }
        else {
            return Integer.toString(offset)+"("+basicAddress.type.name()+")";
        }
    }
}
