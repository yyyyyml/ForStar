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
@a = dso_local global i32 1
@b = dso_local global i32 0
@c = dso_local global i32 1
@d = dso_local global i32 2
@e = dso_local global i32 4
define dso_local i32 @main(){
	%1 = alloca i32
	store i32 0, i32* %1
	br label  %2

2:
	%3 = load i32, i32* @a
	%4 = load i32, i32* @b
	%5 = mul i32 %3, %4
	%6 = load i32, i32* @c
	%7 = sdiv i32 %5, %6
	%8 = load i32, i32* @e
	%9 = load i32, i32* @d
	%10 = add i32 %8, %9
	%11 = icmp eq i32 %7, %10
	br i1 %11, label  %27, label  %15

12:
	%13 = load i32, i32* %1
	ret i32 %13
14:
	store i32 1, i32* %1
	br label  %12

15:
	%16 = load i32, i32* @a
	%17 = load i32, i32* @b
	%18 = load i32, i32* @c
	%19 = mul i32 %17, %18
	%20 = sub i32 %16, %19
	%21 = load i32, i32* @d
	%22 = load i32, i32* @a
	%23 = load i32, i32* @c
	%24 = sdiv i32 %22, %23
	%25 = sub i32 %21, %24
	%26 = icmp eq i32 %20, %25
27:
	%28 = load i32, i32* @a
	%29 = load i32, i32* @a
	%30 = load i32, i32* @b
	%31 = add i32 %29, %30
	%32 = mul i32 %28, %31
	%33 = load i32, i32* @c
	%34 = add i32 %32, %33
	%35 = load i32, i32* @d
	%36 = load i32, i32* @e
	%37 = add i32 %35, %36
	%38 = icmp sle i32 %34, %37
	br i1 %38, label  %14, label  %15

}
