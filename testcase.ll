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
define dso_local i32 @get_one(i32 %0){
	ret i32 1
}
define dso_local i32 @deepWhileBr(i32 %0, i32 %1){
	%3 = add i32 %0, %1
	br label  %4

4:
	%5 = phi i32 [%3, %2], [%21, %20], [%8, %7], [%13, %11], [%16, %15]
	%6 = icmp slt i32 %5, 75
	br i1 %6, label  %7, label  %10

7:
	%8 = phi i32 [%5, %4]
	%9 = icmp slt i32 %8, 100
	br i1 %9, label  %11, label  %4

10:
	ret i32 %5
11:
	%12 = phi i32 [42, %7]
	%13 = add i32 %8, %12
	%14 = icmp sgt i32 %13, 99
	br i1 %14, label  %15, label  %4

15:
	%16 = phi i32 [%13, %11]
	%17 = mul i32 %12, 2
	%18 = call i32 @get_one(i32 0)
	%19 = icmp eq i32 %18, 1
	br i1 %19, label  %20, label  %4

20:
	%21 = mul i32 %17, 2
	br label  %4

}
define dso_local i32 @main(){
	%1 = call i32 @deepWhileBr(i32 2, i32 2)
	call void @putint(i32 %1)
	ret i32 0
}
