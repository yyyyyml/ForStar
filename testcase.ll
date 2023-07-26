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
	%2 = sitofp i32 0 to float
	%3 = fcmp olt float %0, %2
	br i1 %3, label  %6, label  %4

4:
	%5 = phi float [%0, %1]
	ret float %5
6:
	%7 = phi float [%0, %1]
	%8 = fneg float %7
	ret float %8
}
define dso_local float @circle_area(i32 %0){
	%2 = sitofp i32 %0 to float
	%3 = fmul float 0x400921fb60000000, %2
	%4 = sitofp i32 %0 to float
	%5 = fmul float %3, %4
	%6 = mul i32 %0, %0
	%7 = sitofp i32 %6 to float
	%8 = fmul float %7, 0x400921fb60000000
	%9 = fadd float %5, %8
	%10 = sitofp i32 2 to float
	%11 = fdiv float %9, %10
	ret float %11
}
define dso_local i32 @float_eq(float %0, float %1){
	%3 = fsub float %0, %1
	%4 = call float @float_abs(float %3)
	%5 = fcmp olt float %4, 0x3eb0c6f7a0000000
	br i1 %5, label  %6, label  %12

6:
	%7 = sitofp i32 1 to float
	%8 = fmul float %7, 0x4000000000000000
	%9 = sitofp i32 2 to float
	%10 = fdiv float %8, %9
	%11 = fptosi float %10 to i32
	ret i32 %11
12:
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
	%2 = icmp eq i32 0, %0
	br i1 %2, label  %4, label  %5

3:
	ret void 
4:
	call void @error()
	br label  %3

5:
	call void @ok()
	br label  %3

}
define dso_local void @assert_not(i32 %0){
	%2 = icmp ne i32 %0, 0
	br i1 %2, label  %4, label  %5

3:
	ret void 
4:
	call void @error()
	br label  %3

5:
	call void @ok()
	br label  %3

}
define dso_local i32 @main(){
	%1 = alloca [10 x float]
	%2 = call i32 @float_eq(float 0x3fb4000000000000, float 0xc0e01d0000000000)
	call void @assert_not(i32 %2)
	%3 = call i32 @float_eq(float 0x4057c21fc0000000, float 0x4041475ce0000000)
	call void @assert_not(i32 %3)
	%4 = call i32 @float_eq(float 0x4041475ce0000000, float 0x4041475ce0000000)
	call void @assert(i32 %4)
	%5 = fptosi float 0x4016000000000000 to i32
	%6 = call float @circle_area(i32 %5)
	%7 = call float @circle_area(i32 5)
	%8 = call i32 @float_eq(float %6, float %7)
	call void @assert(i32 %8)
	%9 = call i32 @float_eq(float 0x406d200000000000, float 0x40affe0000000000)
	call void @assert_not(i32 %9)
	%10 = fcmp one float 0x3ff8000000000000, 0x0
	br i1 %10, label  %11, label  %12

11:
	call void @ok()
	br label  %12

12:
	%13 = fcmp oeq float 0x0, 0x400a666660000000
	%14 = zext i1 %13 to i32
	%15 = icmp eq i32 0, %14
	br i1 %15, label  %16, label  %17

16:
	call void @ok()
	br label  %17

17:
	%18 = fcmp one float 0x0, 0x0
	br i1 %18, label  %20, label  %22

19:
	call void @error()
	br label  %22

20:
	%21 = icmp ne i32 3, 0
	br i1 %21, label  %19, label  %22

22:
	%23 = icmp ne i32 0, 0
	br i1 %23, label  %31, label  %32

24:
	%25= getelementptr [10 x float],[10 x float]* %1, i32 0, i32 0
	%26 = bitcast float* %25 to i32*
	call void @memset(i32* %26, i32 0, i32 40)
	store float 0x3ff0000000000000, float* %25
	%27= getelementptr float,float* %25, i32 1
	%28 = sitofp i32 2 to float
	store float %28, float* %27
	%29= getelementptr [10 x float],[10 x float]* %1, i32 0, i32 0
	%30 = call i32 @getfarray(float* %29)
	br label  %34

31:
	call void @ok()
	br label  %24

32:
	%33 = fcmp one float 0x3fd3333340000000, 0x0
	br i1 %33, label  %31, label  %24

34:
	%35 = phi i32 [0, %24], [%58, %39]
	%36 = phi i32 [%30, %24], [%41, %39]
	%37 = phi i32 [1, %24], [%57, %39]
	%38 = icmp slt i32 %37, 1000000000
	br i1 %38, label  %39, label  %59

39:
	%40 = phi i32 [%35, %34]
	%41 = phi i32 [%36, %34]
	%42 = phi i32 [%37, %34]
	%43 = call float @getfloat()
	%44 = fmul float 0x400921fb60000000, %43
	%45 = fmul float %44, %43
	%46 = fptosi float %43 to i32
	%47 = call float @circle_area(i32 %46)
	%48= getelementptr [10 x float],[10 x float]* %1, i32 0, i32 %40
	%49= getelementptr [10 x float],[10 x float]* %1, i32 0, i32 %40
	%50 = load float, float* %49
	%51 = fadd float %50, %43
	store float %51, float* %48
	call void @putfloat(float %45)
	call void @putch(i32 32)
	%52 = fptosi float %47 to i32
	call void @putint(i32 %52)
	call void @putch(i32 10)
	%53 = fneg float 0x4024000000000000
	%54 = fneg float %53
	%55 = sitofp i32 %42 to float
	%56 = fmul float %55, %54
	%57 = fptosi float %56 to i32
	%58 = add i32 %40, 1
	br label  %34

59:
	%60 = phi i32 [%36, %34]
	%61= getelementptr [10 x float],[10 x float]* %1, i32 0, i32 0
	call void @putfarray(i32 %60, float* %61)
	ret i32 0
}
