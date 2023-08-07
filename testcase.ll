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
	%1 = icmp eq i32 1, 2
	br i1 %1, label  %2, label  %3

2:
	br label  %3

3:
	%4 = phi i32 [2, %2], [1, %0]
	%5 = icmp ne i32 %4, 2
	br i1 %5, label  %6, label  %7

6:
	br label  %7

7:
	%8 = phi i32 [2, %6], [%4, %3]
	%9 = icmp sgt i32 %8, 2
	br i1 %9, label  %10, label  %11

10:
	br label  %11

11:
	%12 = phi i32 [2, %10], [%8, %7]
	%13 = icmp slt i32 %12, 2
	br i1 %13, label  %14, label  %15

14:
	br label  %15

15:
	%16 = fcmp ogt float 0x400c000000000000, 0x4012000000000000
	br i1 %16, label  %18, label  %17

17:
	ret i32 0
18:
	br label  %17

}
