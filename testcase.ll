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
declare void @memset(i32*, i32, i32)
@a = dso_local global [100 x i32] zeroinitializer
@b = dso_local global [1 x [2 x [3 x float]]] zeroinitializer
@c1 = dso_local global [10 x [20 x [10 x i32]]] zeroinitializer
@main_aaa = dso_local constant [10 x i32] [i32 1, i32 2, i32 3, i32 0, i32 0, i32 0, i32 0, i32 0, i32 0, i32 0]
define dso_local i32 @main(){
	%1 = alloca [100 x i32]
	%2 = alloca [5 x [10 x i32]]
	%3= getelementptr [100 x i32],[100 x i32]* %1, i32 0, i32 1
	%4= getelementptr [5 x [10 x i32]],[5 x [10 x i32]]* %2, i32 0, i32 0
	%5= getelementptr [10 x i32],[10 x i32]* %4, i32 0, i32 1
	%6 = load i32, i32* %5
	store i32 %6, i32* %3
	%7= getelementptr [100 x i32],[100 x i32]* %1, i32 0, i32 2
	%8= getelementptr [100 x i32],[100 x i32]* @a, i32 0, i32 1
	%9 = load i32, i32* %8
	store i32 %9, i32* %7
	%10= getelementptr [100 x i32],[100 x i32]* %1, i32 0, i32 10
	%11 = load i32, i32* %10
	ret i32 %11
}
