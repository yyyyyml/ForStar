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
define dso_local i32 @main(){
	%1 = alloca [2048 x float]
	%2 = alloca [2048 x float]
	%3 = call i32 @getint()
	call void @_sysy_starttime(i32 0)
	br label  %4

4:
	%5 = phi float [0x0, %0], [%56, %55]
	%6 = phi i32 [0, %0], [%57, %55]
	%7 = phi i32 [%3, %0], [%28, %55]
	%8 = phi float [0x0, %0], [%27, %55]
	%9 = phi i32 [0, %0], [%29, %55]
	%10 = phi float [0x3ff0000000000000, %0], [%30, %55]
	%11 = icmp slt i32 %6, 500000
	br i1 %11, label  %15, label  %12

12:
	call void @_sysy_stoptime(i32 0)
	%13 = fsub float %5, 0x4314537800000000
	%14 = fcmp ole float %13, 0x3eb0c6f7a0000000
	br i1 %14, label  %58, label  %60

15:
	%16 = sdiv i32 %6, 8
	%17 = mul i32 %16, 8
	%18 = sub i32 %6, %17
	%19 = icmp ne i32 %18, 0
	br i1 %19, label  %20, label  %21

20:
	br label  %24

21:
	%22 = fadd float %8, 0x3fb99999a0000000
	%23 = fadd float %10, 0x3fc99999a0000000
	br label  %24

24:
	%25 = phi float [%25, %32], [%5, %20], [%5, %21]
	%26 = phi i32 [%26, %32], [%6, %20], [%6, %21]
	%27 = phi float [%27, %32], [0x0, %20], [%22, %21]
	%28 = phi i32 [%28, %32], [%7, %20], [%7, %21]
	%29 = phi i32 [%39, %32], [%9, %20], [%9, %21]
	%30 = phi float [%30, %32], [0x3ff0000000000000, %20], [%23, %21]
	%31 = icmp slt i32 %29, %28
	br i1 %31, label  %32, label  %40

32:
	%33 = getelementptr [2048 x float],[2048 x float]* %1, i32 0, i32 %29
	%34 = sitofp i32 %29 to float
	%35 = fadd float %27, %34
	store float %35, float* %33
	%36 = getelementptr [2048 x float],[2048 x float]* %2, i32 0, i32 %29
	%37 = sitofp i32 %29 to float
	%38 = fadd float %30, %37
	store float %38, float* %36
	%39 = add i32 %29, 1
	br label  %24

40:
	%41 = getelementptr [2048 x float],[2048 x float]* %1, i32 0, i32 0
	%42 = getelementptr [2048 x float],[2048 x float]* %2, i32 0, i32 0
	br label  %43

43:
	%44 = phi i32 [0, %40], [%54, %47]
	%45 = phi float [0x0, %40], [%53, %47]
	%46 = icmp slt i32 %44, %28
	br i1 %46, label  %47, label  %55

47:
	%48 = getelementptr float,float* %41, i32 %44
	%49 = load float, float* %48
	%50 = getelementptr float,float* %42, i32 %44
	%51 = load float, float* %50
	%52 = fmul float %49, %51
	%53 = fadd float %45, %52
	%54 = add i32 %44, 1
	br label  %43

55:
	%56 = fadd float %25, %45
	%57 = add i32 %26, 1
	br label  %4

58:
	call void @putint(i32 0)
	ret i32 0
59:
	call void @putint(i32 1)
	ret i32 1
60:
	%61 = fsub float %5, 0x4314537800000000
	%62 = fcmp oge float %61, 0xbeb0c6f7a0000000
	br i1 %62, label  %58, label  %59

}
