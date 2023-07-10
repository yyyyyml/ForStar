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
@a = dso_local constant [100 x i32] []
@b = dso_local global [1 x [2 x [3 x float]]] []
define dso_local i32 @main(){
	%1 = alloca [100 x i32]
	%2= getelementptr [100 x i32],[100 x i32]* %1, i32 0, i32 10
	%3= getelementptr [100 x i32],[100 x i32]* @a, i32 0, i32 0
	%4 = load i32, i32* %3
	store i32 %4, i32* %2
	%5= getelementptr [100 x i32],[100 x i32]* %1, i32 0, i32 10
	%6 = load i32, i32* %5
	ret i32 %6
}
