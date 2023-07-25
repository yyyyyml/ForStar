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
define dso_local i32 @bubblesort(i32* %0){
	br label  %2

2:
	%3 = phi i32* [%0, %1], [%21, %20]
	%4 = phi i32 [0, %1], [%22, %20]
	%5 = load i32, i32* @n
	%6 = sub i32 %5, 1
	%7 = icmp slt i32 %4, %6
	br i1 %7, label  %8, label  %11

8:
	%9 = phi i32* [%3, %2]
	%10 = phi i32 [%4, %2]
	br label  %12

11:
	ret i32 0
12:
	%13 = phi i32* [%9, %8], [%34, %33]
	%14 = phi i32 [0, %8], [%37, %33]
	%15 = phi i32 [%10, %8], [%36, %33]
	%16 = load i32, i32* @n
	%17 = sub i32 %16, %15
	%18 = sub i32 %17, 1
	%19 = icmp slt i32 %14, %18
	br i1 %19, label  %23, label  %20

20:
	%21 = phi i32* [%13, %12]
	%22 = add i32 %15, 1
	br label  %2

23:
	%24 = phi i32* [%13, %12]
	%25 = phi i32 [%14, %12]
	%26 = phi i32 [%15, %12]
	%27= getelementptr i32,i32* %24, i32 %25
	%28 = load i32, i32* %27
	%29 = add i32 %25, 1
	%30= getelementptr i32,i32* %24, i32 %29
	%31 = load i32, i32* %30
	%32 = icmp sgt i32 %28, %31
	br i1 %32, label  %38, label  %33

33:
	%34 = phi i32* [%39, %38], [%24, %23]
	%35 = phi i32 [%40, %38], [%25, %23]
	%36 = phi i32 [%41, %38], [%26, %23]
	%37 = add i32 %35, 1
	br label  %12

38:
	%39 = phi i32* [%24, %23]
	%40 = phi i32 [%25, %23]
	%41 = phi i32 [%26, %23]
	%42 = add i32 %40, 1
	%43= getelementptr i32,i32* %39, i32 %42
	%44 = load i32, i32* %43
	%45 = add i32 %40, 1
	%46= getelementptr i32,i32* %39, i32 %45
	%47= getelementptr i32,i32* %39, i32 %40
	%48 = load i32, i32* %47
	store i32 %48, i32* %46
	%49= getelementptr i32,i32* %39, i32 %40
	store i32 %44, i32* %49
	br label  %33

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
	%13 = call i32 @bubblesort(i32* %12)
	ret i32 0
}
