package backend.operands;

public class RealRegister extends Register {
    public REGTYPE regType;

    public RealRegister(int r1) {
        switch (r1) {
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
            case 10 -> regType = REGTYPE.a1;
            case 11 -> regType = REGTYPE.a2;
            case 12 -> regType = REGTYPE.a3;
            case 13 -> regType = REGTYPE.a4;
            case 14 -> regType = REGTYPE.a5;
            case 15 -> regType = REGTYPE.a6;
            case 16 -> regType = REGTYPE.a7;

            default -> throw new IllegalStateException("Unexpected value: " + r1);
        }
    }


    public RealRegister(int r1, int offset) {
        switch (r1 + offset + 2) {
            case 0 -> regType = REGTYPE.zero;
            case 1 -> regType = REGTYPE.s0;
            case 2 -> regType = REGTYPE.sp;
            case 3 -> regType = REGTYPE.ra;
            case 4 -> regType = REGTYPE.gp;
            case 5 -> regType = REGTYPE.tp;
            case 6, 11 -> regType = REGTYPE.t0;
            case 7, 12 -> regType = REGTYPE.t1;
            case 8, 13 -> regType = REGTYPE.t2;
            case 9 -> regType = REGTYPE.a0;
            case 10 -> regType = REGTYPE.a5;
            case 14 -> regType = REGTYPE.t3;
            case 15 -> regType = REGTYPE.t4;
            case 16 -> regType = REGTYPE.t5;
            case 17 -> regType = REGTYPE.t6;
            case 18 -> regType = REGTYPE.s1;
            case 19 -> regType = REGTYPE.s2;
            case 20 -> regType = REGTYPE.s3;
            case 21 -> regType = REGTYPE.s4;
            case 22 -> regType = REGTYPE.s5;
            case 23 -> regType = REGTYPE.s6;
            case 24 -> regType = REGTYPE.s7;
            case 25 -> regType = REGTYPE.s8;
            case 26 -> regType = REGTYPE.s9;
            case 27 -> regType = REGTYPE.s10;
            case 28 -> regType = REGTYPE.s11;
            case 29 -> regType = REGTYPE.x18;
            case 30 -> regType = REGTYPE.x19;
            case 31 -> regType = REGTYPE.x20;
            case 32 -> regType = REGTYPE.x21;
            case 33 -> regType = REGTYPE.x22;
            case 34 -> regType = REGTYPE.x23;
            case 35 -> regType = REGTYPE.x24;
            case 36 -> regType = REGTYPE.x25;
            case 37 -> regType = REGTYPE.x26;
            case 38 -> regType = REGTYPE.x27;
            case 39 -> regType = REGTYPE.x28;
            case 40 -> regType = REGTYPE.x29;
            case 41 -> regType = REGTYPE.x30;
            case 42 -> regType = REGTYPE.x31;
            case 43 -> regType = REGTYPE.STACK;
            case 44 -> regType = REGTYPE.STACK2;

            default -> throw new IllegalStateException("Unexpected value: " + r1);
        }
    }

    public String emit() {
        return regType.name();
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
        a5,

        t3,
        t4,
        t5,
        t6,
        x7,
        x8,
        x9,
        x10,
        x11,
        x12,
        x13,
        x14,
        x15,
        x16,
        x17,
        x18,
        x19,
        x20,
        x21,
        x22,
        x23,
        x24,
        x25,
        x26,
        x27,
        x28,
        x29,
        x30,
        x31,
        STACK,
        STACK2,
        a1,
        a2,
        a3,
        a4,
        a6,
        a7,
        s1,
        s2,
        s3,
        s4,
        s5,
        s6,
        s7,
        s8,
        s9,
        s10,
        s11


    }
}
