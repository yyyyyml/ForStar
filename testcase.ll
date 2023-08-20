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
	%1 = alloca i32
	%2 = alloca i32
	%3 = alloca i32
	%4 = alloca i32
	%5 = alloca i32
	store i32 5, i32* %5
	store i32 5, i32* %4
	store i32 1, i32* %3
	%6 = sub i32 0, 2
	store i32 %6, i32* %2
	store i32 2, i32* %1
	%7 = load i32, i32* %2
	%8 = mul i32 %7, 1
	%9 = sdiv i32 %8, 2
	%10 = icmp slt i32 %9, 0
	br i1 %10, label  %11, label  %13

11:
	%12 = load i32, i32* %1
	call void @putint(i32 %12)
	br label  %25

13:
	%14 = load i32, i32* %5
	%15 = load i32, i32* %4
	%16 = sub i32 %14, %15
	%17 = icmp ne i32 %16, 0
	br i1 %17, label  %18, label  %25

18:
	%19 = load i32, i32* %3
	%20 = add i32 %19, 3
	%21 = sdiv i32 %20, 2
	%22 = mul i32 %21, 2
	%23 = sub i32 %20, %22
	%24 = icmp ne i32 %23, 0
	br i1 %24, label  %11, label  %25

25:
	%26 = load i32, i32* %2
	%27 = sdiv i32 %26, 2
	%28 = mul i32 %27, 2
	%29 = sub i32 %26, %28
	%30 = add i32 %29, 67
	%31 = icmp slt i32 %30, 0
	br i1 %31, label  %33, label  %35

32:
	ret i32 0
33:
	store i32 4, i32* %1
	%34 = load i32, i32* %1
	call void @putint(i32 %34)
	br label  %32

35:
	%36 = load i32, i32* %5
	%37 = load i32, i32* %4
	%38 = sub i32 %36, %37
	%39 = icmp ne i32 %38, 0
	br i1 %39, label  %40, label  %32

40:
	%41 = load i32, i32* %3
	%42 = add i32 %41, 2
	%43 = sdiv i32 %42, 2
	%44 = mul i32 %43, 2
	%45 = sub i32 %42, %44
	%46 = icmp ne i32 %45, 0
	br i1 %46, label  %33, label  %32

}
