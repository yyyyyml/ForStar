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
define dso_local i32 @main(){
	%1 = alloca i32
	store i32 0, i32* %1
	br label  %2

2:
	br i32 1, label  %3, label  %4

3:
	br label  %5

4:
	ret i32 0
5:
	%6 = load i32, i32* %1
	br i32 %6, label  %8, label  %10

7:
	br label  %2

8:
	br label  %2

9:
	br label  %7

10:
	br label  %4

11:
	br label  %7

}
