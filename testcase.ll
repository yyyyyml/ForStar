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
@n = dso_local global i32 0
define dso_local i32 @select_sort(i32* %0, i32 %1){
	br label  %3

3:
	%4 = phi i32* [%0, %2], [%13, %31]
	%5 = phi i32 [0, %2], [%32, %31]
	%6 = phi i32 [%1, %2], [%16, %31]
	%7 = sub i32 %6, 1
	%8 = icmp slt i32 %5, %7
	br i1 %8, label  %9, label  %11

9:
	%10 = add i32 %5, 1
	br label  %12

11:
	ret i32 0
12:
	%13 = phi i32* [%4, %9], [%13, %25]
	%14 = phi i32 [%5, %9], [%14, %25]
	%15 = phi i32 [%5, %9], [%26, %25]
	%16 = phi i32 [%6, %9], [%16, %25]
	%17 = phi i32 [%10, %9], [%27, %25]
	%18 = icmp slt i32 %17, %16
	br i1 %18, label  %19, label  %29

19:
	%20= getelementptr i32,i32* %13, i32 %15
	%21 = load i32, i32* %20
	%22= getelementptr i32,i32* %13, i32 %17
	%23 = load i32, i32* %22
	%24 = icmp sgt i32 %21, %23
	br i1 %24, label  %28, label  %25

25:
	%26 = phi i32 [%17, %28], [%15, %19]
	%27 = add i32 %17, 1
	br label  %12

28:
	br label  %25

29:
	%30 = icmp ne i32 %15, %14
	br i1 %30, label  %33, label  %31

31:
	%32 = add i32 %14, 1
	br label  %3

33:
	%34= getelementptr i32,i32* %13, i32 %15
	%35 = load i32, i32* %34
	%36= getelementptr i32,i32* %13, i32 %15
	%37= getelementptr i32,i32* %13, i32 %14
	%38 = load i32, i32* %37
	store i32 %38, i32* %36
	%39= getelementptr i32,i32* %13, i32 %14
	store i32 %35, i32* %39
	br label  %31

}
define dso_local i32 @main(){
	%1 = alloca [10 x i32]
	store i32 10, i32* @n
	%2= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 0
	store i32 4, i32* %2
	%3= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 1
	store i32 3, i32* %3
	%4= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 2
	store i32 9, i32* %4
	%5= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 3
	store i32 2, i32* %5
	%6= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 4
	store i32 0, i32* %6
	%7= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 5
	store i32 1, i32* %7
	%8= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 6
	store i32 6, i32* %8
	%9= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 7
	store i32 5, i32* %9
	%10= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 8
	store i32 7, i32* %10
	%11= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 9
	store i32 8, i32* %11
	%12= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 0
	%13 = load i32, i32* @n
	%14 = call i32 @select_sort(i32* %12, i32 %13)
	br label  %15

15:
	%16 = phi i32 [%14, %0], [%22, %19]
	%17 = load i32, i32* @n
	%18 = icmp slt i32 %16, %17
	br i1 %18, label  %19, label  %23

19:
	%20= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 %16
	%21 = load i32, i32* %20
	call void @putint(i32 %21)
	call void @putch(i32 10)
	%22 = add i32 %16, 1
	br label  %15

23:
	ret i32 0
}
