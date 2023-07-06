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
	%2 = alloca i32
	store i32 5, i32* %2
	store i32 10, i32* %1
	br label  %3

3:
	%4 = load i32, i32* %2
	%5 = icmp eq i32 %4, 5
	br i1 %5, label  %8, label  %9

6:
	%7 = load i32, i32* %2
	ret i32 %7
8:
	store i32 3, i32* %2
	br label  %6

9:
	store i32 3, i32* %1
	br label  %6

}
