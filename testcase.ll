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
	br label  %1

1:
	%2 = phi i32 [5, %0], [%7, %6]
	%3 = sub i32 0, %2
	%4 = sub i32 %2, %3
	%5 = icmp ne i32 %4, 0
	br i1 %5, label  %6, label  %8

6:
	%7 = mul i32 %2, -8
	br label  %1

8:
	ret i32 %2
}
