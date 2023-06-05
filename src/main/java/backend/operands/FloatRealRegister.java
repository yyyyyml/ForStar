package backend.operands;

public class FloatRealRegister extends Register {
    public REGTYPE regType;

    public FloatRealRegister(int r1) {
        switch (r1) {
            case 0 -> regType = REGTYPE.f0;
            case 1 -> regType = REGTYPE.f1;
            case 2 -> regType = REGTYPE.f2;
            case 3 -> regType = REGTYPE.f3;
            case 4 -> regType = REGTYPE.f4;
            case 5 -> regType = REGTYPE.f5;
            case 6 -> regType = REGTYPE.f6;
            case 7 -> regType = REGTYPE.f7;
            case 8 -> regType = REGTYPE.f8;
            case 9 -> regType = REGTYPE.f9;
            case 10 -> regType = REGTYPE.f10;
            case 11 -> regType = REGTYPE.f11;
            case 12 -> regType = REGTYPE.f12;
            case 13 -> regType = REGTYPE.f13;
            case 14 -> regType = REGTYPE.f14;
            case 15 -> regType = REGTYPE.f15;
            case 16 -> regType = REGTYPE.f16;
            case 17 -> regType = REGTYPE.f17;
            case 18 -> regType = REGTYPE.f18;
            case 19 -> regType = REGTYPE.f19;
            case 20 -> regType = REGTYPE.f20;
            case 21 -> regType = REGTYPE.f21;
            case 22 -> regType = REGTYPE.f22;
            case 23 -> regType = REGTYPE.f23;
            case 24 -> regType = REGTYPE.f24;
            case 25 -> regType = REGTYPE.f25;
            case 26 -> regType = REGTYPE.f26;
            case 27 -> regType = REGTYPE.f27;
            case 28 -> regType = REGTYPE.f28;
            case 29 -> regType = REGTYPE.f29;
            case 30 -> regType = REGTYPE.f30;
            case 31 -> regType = REGTYPE.f31;


            default -> throw new IllegalStateException("Unexpected value: " + r1);
        }
    }

    public String emit() {
        return regType.name();
    }

    public enum REGTYPE {
        f0,
        f1,
        f2,
        f3,
        f4,
        f5,
        f6,
        f7,
        f8,
        f9,
        f10,
        f11,
        f12,
        f13,
        f14,
        f15,
        f16,
        f17,
        f18,
        f19,
        f20,
        f21,
        f22,
        f23,
        f24,
        f25,
        f26,
        f27,
        f28,
        f29,
        f30,
        f31

    }
}
