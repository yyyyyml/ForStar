package backend.Riscvalues;

public class Register {
    public Register(int r1) {
        switch (r1){
            case 0: type=TYPE.zero;
            case 1: type=TYPE.s0;
            case 2: type=TYPE.sp;
            case 3: type=TYPE.ra;
            case 4: type=TYPE.gp;
            case 5: type=TYPE.tp;
            case 6: type=TYPE.t0;
            case 7: type=TYPE.t1;
            case 8: type=TYPE.t2;
        }
    }

    public enum TYPE{
        zero,
        s0,
        sp,
        ra,
        gp,
        tp,
        t0,
        t1,
        t2

    }

    public TYPE type;
}
