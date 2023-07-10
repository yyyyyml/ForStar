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
	store i32 0, i32* %2
	store i32 0, i32* %1
	br label  %3

3:
	%4 = load i32, i32* %2
	%5 = icmp ne i32 %4, 0
	br i1 %5, label  %6, label  %7

6:
	br label  %8

7:
	ret i32 0
8:
	%9 = load i32, i32* %1
	%10 = icmp ne i32 %9, 0
	br i1 %10, label  %12, label  %11

11:
	br label  %3

12:
	br label  %3

13:
	br label  %11

}
