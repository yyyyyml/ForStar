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
@z = dso_local constant [4 x [2 x i32]] [[2 x i32] [i32 1, i32 2], [2 x i32] [i32 3, i32 4], [2 x i32] [i32 0, i32 0], [2 x i32] [i32 7, i32 0]]
@a = dso_local constant [4 x [2 x i32]] [[2 x i32] [i32 1, i32 2], [2 x i32] [i32 3, i32 4], [2 x i32] [i32 0, i32 0], [2 x i32] [i32 7, i32 0]]
define dso_local i32 @main(){
	%1 = alloca [4 x [2 x [1 x i32]]]
	%2 = alloca [4 x [2 x i32]]
	%3 = alloca [4 x [2 x i32]]
	%4 = alloca [4 x [2 x i32]]
	%5= getelementptr [4 x [2 x i32]],[4 x [2 x i32]]* %4, i32 0, i32 0
	%6= getelementptr [2 x i32],[2 x i32]* %5, i32 0, i32 0
	call void @memset(i32* %6, i32 0, i32 32)
	store i32 0, i32* %6
	%7= getelementptr i32,i32* %6, i32 1
	store i32 0, i32* %7
	%8= getelementptr i32,i32* %6, i32 2
	store i32 0, i32* %8
	%9= getelementptr i32,i32* %6, i32 3
	store i32 0, i32* %9
	%10= getelementptr i32,i32* %6, i32 4
	store i32 0, i32* %10
	%11= getelementptr i32,i32* %6, i32 5
	store i32 0, i32* %11
	%12= getelementptr i32,i32* %6, i32 6
	store i32 0, i32* %12
	%13= getelementptr i32,i32* %6, i32 7
	store i32 0, i32* %13
	%14= getelementptr [4 x [2 x i32]],[4 x [2 x i32]]* %3, i32 0, i32 0
	%15= getelementptr [2 x i32],[2 x i32]* %14, i32 0, i32 0
	call void @memset(i32* %15, i32 0, i32 32)
	store i32 1, i32* %15
	%16= getelementptr i32,i32* %15, i32 1
	store i32 2, i32* %16
	%17= getelementptr i32,i32* %15, i32 2
	store i32 3, i32* %17
	%18= getelementptr i32,i32* %15, i32 3
	store i32 4, i32* %18
	%19= getelementptr i32,i32* %15, i32 4
	store i32 5, i32* %19
	%20= getelementptr i32,i32* %15, i32 5
	store i32 6, i32* %20
	%21= getelementptr i32,i32* %15, i32 6
	store i32 7, i32* %21
	%22= getelementptr i32,i32* %15, i32 7
	store i32 8, i32* %22
	%23= getelementptr [4 x [2 x i32]],[4 x [2 x i32]]* @a, i32 0, i32 3
	%24= getelementptr [2 x i32],[2 x i32]* %23, i32 0, i32 0
	%25 = load i32, i32* %24
	%26= getelementptr [4 x [2 x i32]],[4 x [2 x i32]]* %2, i32 0, i32 0
	%27= getelementptr [2 x i32],[2 x i32]* %26, i32 0, i32 0
	call void @memset(i32* %27, i32 0, i32 32)
	store i32 1, i32* %27
	%28= getelementptr i32,i32* %27, i32 1
	store i32 2, i32* %28
	%29= getelementptr i32,i32* %27, i32 2
	store i32 3, i32* %29
	%30= getelementptr i32,i32* %27, i32 3
	store i32 0, i32* %30
	%31= getelementptr i32,i32* %27, i32 4
	store i32 5, i32* %31
	%32= getelementptr i32,i32* %27, i32 5
	store i32 0, i32* %32
	%33= getelementptr i32,i32* %27, i32 6
	store i32 %25, i32* %33
	%34= getelementptr i32,i32* %27, i32 7
	store i32 8, i32* %34
	%35= getelementptr [4 x [2 x i32]],[4 x [2 x i32]]* %2, i32 0, i32 2
	%36= getelementptr [2 x i32],[2 x i32]* %35, i32 0, i32 1
	%37 = load i32, i32* %36
	%38= getelementptr [4 x [2 x i32]],[4 x [2 x i32]]* %3, i32 0, i32 2
	%39= getelementptr [2 x i32],[2 x i32]* %38, i32 0, i32 1
	%40 = load i32, i32* %39
	%41= getelementptr [4 x [2 x [1 x i32]]],[4 x [2 x [1 x i32]]]* %1, i32 0, i32 0
	%42= getelementptr [2 x [1 x i32]],[2 x [1 x i32]]* %41, i32 0, i32 0
	%43= getelementptr [1 x i32],[1 x i32]* %42, i32 0, i32 0
	call void @memset(i32* %43, i32 0, i32 32)
	store i32 %37, i32* %43
	%44= getelementptr i32,i32* %43, i32 1
	store i32 %40, i32* %44
	%45= getelementptr i32,i32* %43, i32 2
	store i32 3, i32* %45
	%46= getelementptr i32,i32* %43, i32 3
	store i32 4, i32* %46
	%47= getelementptr i32,i32* %43, i32 4
	store i32 5, i32* %47
	%48= getelementptr i32,i32* %43, i32 5
	store i32 6, i32* %48
	%49= getelementptr i32,i32* %43, i32 6
	store i32 7, i32* %49
	%50= getelementptr i32,i32* %43, i32 7
	store i32 8, i32* %50
	%51= getelementptr [4 x [2 x [1 x i32]]],[4 x [2 x [1 x i32]]]* %1, i32 0, i32 3
	%52= getelementptr [2 x [1 x i32]],[2 x [1 x i32]]* %51, i32 0, i32 1
	%53= getelementptr [1 x i32],[1 x i32]* %52, i32 0, i32 0
	%54 = load i32, i32* %53
	%55= getelementptr [4 x [2 x [1 x i32]]],[4 x [2 x [1 x i32]]]* %1, i32 0, i32 0
	%56= getelementptr [2 x [1 x i32]],[2 x [1 x i32]]* %55, i32 0, i32 0
	%57= getelementptr [1 x i32],[1 x i32]* %56, i32 0, i32 0
	%58 = load i32, i32* %57
	%59 = add i32 %54, %58
	%60= getelementptr [4 x [2 x [1 x i32]]],[4 x [2 x [1 x i32]]]* %1, i32 0, i32 0
	%61= getelementptr [2 x [1 x i32]],[2 x [1 x i32]]* %60, i32 0, i32 1
	%62= getelementptr [1 x i32],[1 x i32]* %61, i32 0, i32 0
	%63 = load i32, i32* %62
	%64 = add i32 %59, %63
	%65= getelementptr [4 x [2 x i32]],[4 x [2 x i32]]* %2, i32 0, i32 3
	%66= getelementptr [2 x i32],[2 x i32]* %65, i32 0, i32 0
	%67 = load i32, i32* %66
	%68 = add i32 %64, %67
	ret i32 %68
}
