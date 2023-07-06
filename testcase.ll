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
declare void @starttime()
declare void @stoptime()
@f = dso_local global float 0.0
@z = dso_local global i32 0
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
	br label  %7

7:
	%8 = load i32, i32* %2
	%9 = mul i32 %8, 1
	%10 = sdiv i32 %9, 2
	%11 = icmp slt i32 %10, 0
	br i1 %11, label  %13, label  %15

12:
	br label  %27

13:
	%14 = load i32, i32* %1
	call void @putint(i32 %14)
	br label  %12

15:
	%16 = load i32, i32* %5
	%17 = load i32, i32* %4
	%18 = sub i32 %16, %17
	%19 = icmp ne i32 %18, 0
	br i1 %19, label  %20, label  %12

20:
	%21 = load i32, i32* %3
	%22 = add i32 %21, 3
	%23 = sdiv i32 %22, 2
	%24 = mul i32 %23, 2
	%25 = sub i32 %22, %24
	%26 = icmp ne i32 %25, 0
	br i1 %26, label  %13, label  %12

27:
	%28 = load i32, i32* %2
	%29 = sdiv i32 %28, 2
	%30 = mul i32 %29, 2
	%31 = sub i32 %28, %30
	%32 = add i32 %31, 67
	%33 = icmp slt i32 %32, 0
	br i1 %33, label  %35, label  %37

34:
	ret i32 0
35:
	store i32 4, i32* %1
	%36 = load i32, i32* %1
	call void @putint(i32 %36)
	br label  %34

37:
	%38 = load i32, i32* %5
	%39 = load i32, i32* %4
	%40 = sub i32 %38, %39
	%41 = icmp ne i32 %40, 0
	br i1 %41, label  %42, label  %34

42:
	%43 = load i32, i32* %3
	%44 = add i32 %43, 2
	%45 = sdiv i32 %44, 2
	%46 = mul i32 %45, 2
	%47 = sub i32 %44, %46
	%48 = icmp ne i32 %47, 0
	br i1 %48, label  %35, label  %34

}
