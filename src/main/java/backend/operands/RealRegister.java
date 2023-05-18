package backend.operands;

public class RealRegister extends Register{
    public RealRegister(int r1) {
        switch (r1){
            case 0 -> regType = REGTYPE.zero;
            case 1 -> regType = REGTYPE.s0;
            case 2 -> regType = REGTYPE.sp;
            case 3 -> regType = REGTYPE.ra;
            case 4 -> regType = REGTYPE.gp;
            case 5 -> regType = REGTYPE.tp;
            case 6 -> regType = REGTYPE.t0;
            case 7 -> regType = REGTYPE.t1;
            case 8 -> regType = REGTYPE.t2;
            case 9 -> regType = REGTYPE.a0;
            case 10 -> regType = REGTYPE.a5;

            default ->
                throw new IllegalStateException("Unexpected value: " + r1);
        }
    }

    public enum REGTYPE {
        zero,
        s0,
        sp,
        ra,
        gp,
        tp,
        t0,
        t1,
        t2,
        a0,
        a5

    }

    public REGTYPE regType;

    public String emit(){
        return regType.name();
    }
}
