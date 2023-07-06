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
	br i1 %11, label  %39, label  %27

12:
	%13 = load i32, i32* %1
	ret i32 %13
14:
	store i32 1, i32* %1
	br label  %12

15:
	br label  %16

16:
	%17 = load i32, i32* @a
	%18 = load i32, i32* @b
	%19 = load i32, i32* @c
	%20 = mul i32 %18, %19
	%21 = sub i32 %17, %20
	%22 = load i32, i32* @a
	%23 = icmp eq i32 %21, %22
24:
	br label  %12

25:
	store i32 2, i32* %1
	br label  %24

26:
	store i32 3, i32* %1
	br label  %24

27:
	%28 = load i32, i32* @a
	%29 = load i32, i32* @b
	%30 = load i32, i32* @c
	%31 = mul i32 %29, %30
	%32 = sub i32 %28, %31
	%33 = load i32, i32* @d
	%34 = load i32, i32* @a
	%35 = load i32, i32* @c
	%36 = sdiv i32 %34, %35
	%37 = sub i32 %33, %36
	%38 = icmp eq i32 %32, %37
	br i1 %38, label  %14, label  %15

39:
	%40 = load i32, i32* @a
	%41 = load i32, i32* @a
	%42 = load i32, i32* @b
	%43 = add i32 %41, %42
	%44 = mul i32 %40, %43
	%45 = load i32, i32* @c
	%46 = add i32 %44, %45
	%47 = load i32, i32* @d
	%48 = load i32, i32* @e
	%49 = add i32 %47, %48
	%50 = icmp sle i32 %46, %49
	br i1 %50, label  %14, label  %27

}
