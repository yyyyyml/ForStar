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
	%1 = call i32 @getint()
	call void @_sysy_starttime(i32 0)
	br label  %2

2:
	%3 = phi i32 [0, %0], [%25, %19]
	%4 = phi i32 [0, %0], [%24, %19]
	%5 = icmp slt i32 %3, %1
	br i1 %5, label  %6, label  %7

6:
	br label  %8

7:
	call void @_sysy_stoptime(i32 0)
	call void @putint(i32 %4)
	call void @putch(i32 10)
	ret i32 0
8:
	%9 = phi i32 [0, %6], [%18, %12]
	%10 = phi i32 [0, %6], [%17, %12]
	%11 = icmp slt i32 %9, 300
	br i1 %11, label  %12, label  %19

12:
	%13 = mul i32 %3, 2
	%14 = sdiv i32 %13, 2
	%15 = mul i32 %14, 1000
	%16 = sdiv i32 %15, 1000
	%17 = add i32 %10, %16
	%18 = add i32 %9, 1
	br label  %8

19:
	%20 = sdiv i32 %10, 300
	%21 = add i32 %4, %20
	%22 = sdiv i32 %21, 2147385347
	%23 = mul i32 %22, 2147385347
	%24 = sub i32 %21, %23
	%25 = add i32 %3, 1
	br label  %2

}
