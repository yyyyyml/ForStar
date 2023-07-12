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
@n = dso_local global i32 0
define dso_local i32 @bubblesort(i32* %0){
	%2 = alloca i32
	%3 = alloca i32
	%4 = alloca i32*
	store i32* %0, i32** %4
	store i32 0, i32* %3
	br label  %5

5:
	%6 = load i32, i32* %3
	%7 = load i32, i32* @n
	%8 = sub i32 %7, 1
	%9 = icmp slt i32 %6, %8
	br i1 %9, label  %10, label  %11

10:
	store i32 0, i32* %2
	br label  %12

11:
	ret i32 0
12:
	%13 = load i32, i32* %2
	%14 = load i32, i32* @n
	%15 = load i32, i32* %3
	%16 = sub i32 %14, %15
	%17 = sub i32 %16, 1
	%18 = icmp slt i32 %13, %17
	br i1 %18, label  %19, label  %20

19:
	br label  %23

20:
	%21 = load i32, i32* %3
	%22 = add i32 %21, 1
	store i32 %22, i32* %3
	br label  %5

23:
	%24 = load i32*, i32** %4
	%25 = load i32, i32* %2
	%26= getelementptr i32,i32* %24, i32 %25
	%27 = load i32, i32* %26
	%28 = load i32*, i32** %4
	%29 = load i32, i32* %2
	%30 = add i32 %29, 1
	%31= getelementptr i32,i32* %28, i32 %30
	%32 = load i32, i32* %31
	%33 = icmp sgt i32 %27, %32
	br i1 %33, label  %37, label  %34

34:
	%35 = load i32, i32* %2
	%36 = add i32 %35, 1
	store i32 %36, i32* %2
	br label  %12

37:
	%38 = alloca i32
	%39 = load i32*, i32** %4
	%40 = load i32, i32* %2
	%41 = add i32 %40, 1
	%42= getelementptr i32,i32* %39, i32 %41
	%43 = load i32, i32* %42
	store i32 %43, i32* %38
	%44 = load i32*, i32** %4
	%45 = load i32, i32* %2
	%46 = add i32 %45, 1
	%47= getelementptr i32,i32* %44, i32 %46
	%48 = load i32*, i32** %4
	%49 = load i32, i32* %2
	%50= getelementptr i32,i32* %48, i32 %49
	%51 = load i32, i32* %50
	store i32 %51, i32* %47
	%52 = load i32*, i32** %4
	%53 = load i32, i32* %2
	%54= getelementptr i32,i32* %52, i32 %53
	%55 = load i32, i32* %38
	store i32 %55, i32* %54
	br label  %34

}
define dso_local i32 @main(){
	%1 = alloca i32
	%2 = alloca [10 x i32]
	store i32 10, i32* @n
	%3= getelementptr [10 x i32],[10 x i32]* %2, i32 0, i32 0
	store i32 4, i32* %3
	%4= getelementptr [10 x i32],[10 x i32]* %2, i32 0, i32 1
	store i32 3, i32* %4
	%5= getelementptr [10 x i32],[10 x i32]* %2, i32 0, i32 2
	store i32 9, i32* %5
	%6= getelementptr [10 x i32],[10 x i32]* %2, i32 0, i32 3
	store i32 2, i32* %6
	%7= getelementptr [10 x i32],[10 x i32]* %2, i32 0, i32 4
	store i32 0, i32* %7
	%8= getelementptr [10 x i32],[10 x i32]* %2, i32 0, i32 5
	store i32 1, i32* %8
	%9= getelementptr [10 x i32],[10 x i32]* %2, i32 0, i32 6
	store i32 6, i32* %9
	%10= getelementptr [10 x i32],[10 x i32]* %2, i32 0, i32 7
	store i32 5, i32* %10
	%11= getelementptr [10 x i32],[10 x i32]* %2, i32 0, i32 8
	store i32 7, i32* %11
	%12= getelementptr [10 x i32],[10 x i32]* %2, i32 0, i32 9
	store i32 8, i32* %12
	%13 = load [10 x i32], [10 x i32]* %2
	%14 = call i32 @bubblesort([10 x i32] %13)
	store i32 %14, i32* %1
	br label  %15

15:
	%16 = load i32, i32* %1
	%17 = load i32, i32* @n
	%18 = icmp slt i32 %16, %17
	br i1 %18, label  %19, label  %28

19:
	%20 = alloca i32
	%21 = load i32, i32* %1
	%22= getelementptr [10 x i32],[10 x i32]* %2, i32 0, i32 %21
	%23 = load i32, i32* %22
	store i32 %23, i32* %20
	%24 = load i32, i32* %20
	call void @putint(i32 %24)
	store i32 10, i32* %20
	%25 = load i32, i32* %20
	call void @putch(i32 %25)
	%26 = load i32, i32* %1
	%27 = add i32 %26, 1
	store i32 %27, i32* %1
	br label  %15

28:
	ret i32 0
}
