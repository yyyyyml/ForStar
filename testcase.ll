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
define dso_local i32 @ifElseIf(){
	%1 = icmp eq i32 5, 6
	br i1 %1, label  %4, label  %28

2:
	%3 = phi i32 [25, %10], [%16, %15], [%19, %17]
	ret i32 %3
4:
	%5 = phi i32 [%30, %28], [5, %0]
	ret i32 %5
6:
	%7 = phi i32 [%29, %28]
	%8 = phi i32 [%30, %28]
	%9 = icmp eq i32 %7, 10
	br i1 %9, label  %24, label  %11

10:
	br label  %2

11:
	%12 = phi i32 [%7, %6], [%25, %24]
	%13 = phi i32 [%8, %6], [%26, %24]
	%14 = icmp eq i32 %12, 10
	br i1 %14, label  %20, label  %17

15:
	%16 = add i32 %21, 15
	br label  %2

17:
	%18 = phi i32 [%13, %11], [%21, %20]
	%19 = sub i32 0, %18
	br label  %2

20:
	%21 = phi i32 [%13, %11]
	%22 = sub i32 0, 5
	%23 = icmp eq i32 %21, %22
	br i1 %23, label  %15, label  %17

24:
	%25 = phi i32 [%7, %6]
	%26 = phi i32 [%8, %6]
	%27 = icmp eq i32 %26, 1
	br i1 %27, label  %10, label  %11

28:
	%29 = phi i32 [10, %0]
	%30 = phi i32 [5, %0]
	%31 = icmp eq i32 %29, 11
	br i1 %31, label  %4, label  %6

}
define dso_local i32 @main(){
	%1 = call i32 @ifElseIf()
	call void @putint(i32 %1)
	ret i32 0
}
