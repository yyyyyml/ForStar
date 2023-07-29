declare i32 @getint()
declare i32 @getch()
declare float @getfloat()
declare i32 @getarray(i32*)
declare i32 @getfarray(float*)
declare void @putint(i32)
declare void @putch(i32)
declare void @putfloat(float)
declare void @putarray(i32, i32*)
declare void @putfarray(i32, float*)
declare void @_sysy_starttime(i32)
declare void @_sysy_stoptime(i32)
declare void @memset(i32*, i32, i32)
define dso_local float @float_abs(float %0){
	%2 = alloca float
	store float %0, float* %2
	%3 = load float, float* %2
	%4 = sitofp i32 0 to float
	%5 = fcmp olt float %3, %4
	br i1 %5, label  %8, label  %6

6:
	%7 = load float, float* %2
	ret float %7
8:
	%9 = load float, float* %2
	%10 = fneg float %9
	ret float %10
}
define dso_local float @circle_area(i32 %0){
	%2 = alloca i32
	store i32 %0, i32* %2
	%3 = load i32, i32* %2
	%4 = sitofp i32 %3 to float
	%5 = fmul float 0x400921fb60000000, %4
	%6 = load i32, i32* %2
	%7 = sitofp i32 %6 to float
	%8 = fmul float %5, %7
	%9 = load i32, i32* %2
	%10 = load i32, i32* %2
	%11 = mul i32 %9, %10
	%12 = sitofp i32 %11 to float
	%13 = fmul float %12, 0x400921fb60000000
	%14 = fadd float %8, %13
	%15 = sitofp i32 2 to float
	%16 = fdiv float %14, %15
	ret float %16
}
define dso_local i32 @float_eq(float %0, float %1){
	%3 = alloca float
	%4 = alloca float
	store float %0, float* %4
	store float %1, float* %3
	%5 = load float, float* %4
	%6 = load float, float* %3
	%7 = fsub float %5, %6
	%8 = call float @float_abs(float %7)
	%9 = fcmp olt float %8, 0x3eb0c6f7a0000000
	br i1 %9, label  %10, label  %16

10:
	%11 = sitofp i32 1 to float
	%12 = fmul float %11, 0x4000000000000000
	%13 = sitofp i32 2 to float
	%14 = fdiv float %12, %13
	%15 = fptosi float %14 to i32
	ret i32 %15
16:
	ret i32 0
}
define dso_local void @error(){
	call void @putch(i32 101)
	call void @putch(i32 114)
	call void @putch(i32 114)
	call void @putch(i32 111)
	call void @putch(i32 114)
	call void @putch(i32 10)
	ret void 
}
define dso_local void @ok(){
	call void @putch(i32 111)
	call void @putch(i32 107)
	call void @putch(i32 10)
	ret void 
}
define dso_local void @assert(i32 %0){
	%2 = alloca i32
	store i32 %0, i32* %2
	%3 = load i32, i32* %2
	%4 = icmp eq i32 0, %3
	br i1 %4, label  %6, label  %7

5:
	ret void 
6:
	call void @error()
	br label  %5

7:
	call void @ok()
	br label  %5

}
define dso_local void @assert_not(i32 %0){
	%2 = alloca i32
	store i32 %0, i32* %2
	%3 = load i32, i32* %2
	%4 = icmp ne i32 %3, 0
	br i1 %4, label  %6, label  %7

5:
	ret void 
6:
	call void @error()
	br label  %5

7:
	call void @ok()
	br label  %5

}
define dso_local i32 @main(){
	%1 = alloca float
	%2 = alloca float
	%3 = alloca float
	%4 = alloca i32
	%5 = alloca [10 x float]
	%6 = alloca i32
	%7 = alloca i32
	%8 = call i32 @float_eq(float 0x3fb4000000000000, float 0xc0e01d0000000000)
	call void @assert_not(i32 %8)
	%9 = call i32 @float_eq(float 0x4057c21fc0000000, float 0x4041475ce0000000)
	call void @assert_not(i32 %9)
	%10 = call i32 @float_eq(float 0x4041475ce0000000, float 0x4041475ce0000000)
	call void @assert(i32 %10)
	%11 = fptosi float 0x4016000000000000 to i32
	%12 = call float @circle_area(i32 %11)
	%13 = call float @circle_area(i32 5)
	%14 = call i32 @float_eq(float %12, float %13)
	call void @assert(i32 %14)
	%15 = call i32 @float_eq(float 0x406d200000000000, float 0x40affe0000000000)
	call void @assert_not(i32 %15)
	%16 = fcmp one float 0x3ff8000000000000, 0x0
	br i1 %16, label  %17, label  %18

17:
	call void @ok()
	br label  %18

18:
	%19 = fcmp oeq float 0x0, 0x400a666660000000
	%20 = zext i1 %19 to i32
	%21 = icmp eq i32 0, %20
	br i1 %21, label  %22, label  %23

22:
	call void @ok()
	br label  %23

23:
	%24 = fcmp one float 0x0, 0x0
	br i1 %24, label  %26, label  %28

25:
	call void @error()
	br label  %28

26:
	%27 = icmp ne i32 3, 0
	br i1 %27, label  %25, label  %28

28:
	%29 = icmp ne i32 0, 0
	br i1 %29, label  %37, label  %38

30:
	store i32 1, i32* %7
	store i32 0, i32* %6
	%31= getelementptr [10 x float],[10 x float]* %5, i32 0, i32 0
	%32 = bitcast float* %31 to i32*
	call void @memset(i32* %32, i32 0, i32 40)
	store float 0x3ff0000000000000, float* %31
	%33= getelementptr float,float* %31, i32 1
	%34 = sitofp i32 2 to float
	store float %34, float* %33
	%35= getelementptr [10 x float],[10 x float]* %5, i32 0, i32 0
	%36 = call i32 @getfarray(float* %35)
	store i32 %36, i32* %4
	br label  %40

37:
	call void @ok()
	br label  %30

38:
	%39 = fcmp one float 0x3fd3333340000000, 0x0
	br i1 %39, label  %37, label  %30

40:
	%41 = load i32, i32* %7
	%42 = icmp slt i32 %41, 1000000000
	br i1 %42, label  %43, label  %70

43:
	%44 = call float @getfloat()
	store float %44, float* %3
	%45 = load float, float* %3
	%46 = fmul float 0x400921fb60000000, %45
	%47 = load float, float* %3
	%48 = fmul float %46, %47
	store float %48, float* %2
	%49 = load float, float* %3
	%50 = fptosi float %49 to i32
	%51 = call float @circle_area(i32 %50)
	store float %51, float* %1
	%52 = load i32, i32* %6
	%53= getelementptr [10 x float],[10 x float]* %5, i32 0, i32 %52
	%54 = load i32, i32* %6
	%55= getelementptr [10 x float],[10 x float]* %5, i32 0, i32 %54
	%56 = load float, float* %55
	%57 = load float, float* %3
	%58 = fadd float %56, %57
	store float %58, float* %53
	%59 = load float, float* %2
	call void @putfloat(float %59)
	call void @putch(i32 32)
	%60 = load float, float* %1
	%61 = fptosi float %60 to i32
	call void @putint(i32 %61)
	call void @putch(i32 10)
	%62 = load i32, i32* %7
	%63 = fneg float 0x4024000000000000
	%64 = fneg float %63
	%65 = sitofp i32 %62 to float
	%66 = fmul float %65, %64
	%67 = fptosi float %66 to i32
	store i32 %67, i32* %7
	%68 = load i32, i32* %6
	%69 = add i32 %68, 1
	store i32 %69, i32* %6
	br label  %40

70:
	%71 = load i32, i32* %4
	%72= getelementptr [10 x float],[10 x float]* %5, i32 0, i32 0
	call void @putfarray(i32 %71, float* %72)
	ret i32 0
}
