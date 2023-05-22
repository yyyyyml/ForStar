define dso_local i32 @fun(){
	%1 = alloca i32
	store i32 0, i32*  %1
	%2 = load i32, i32*  %1
	ret i32 %2
}
define dso_local i32 @main(){
	%1 = alloca i32
	%2 = alloca i32
	%3 = alloca i32
	%4 = alloca i32
	%5 = alloca i32
	%6 = alloca i32
	%7 = alloca i32
	%8 = alloca i32
	%9 = alloca i32
	%10 = alloca i32
	%11 = alloca i32
	%12 = alloca i32
	%13 = alloca i32
	%14 = alloca i32
	%15 = alloca i32
	%16 = alloca i32
	%17 = alloca i32
	store i32 0, i32*  %17
	store i32 0, i32*  %16
	store i32 0, i32*  %15
	store i32 0, i32*  %14
	store i32 0, i32*  %13
	store i32 0, i32*  %12
	store i32 0, i32*  %11
	store i32 0, i32*  %10
	store i32 1, i32*  %17
	store i32 2, i32*  %16
	store i32 3, i32*  %15
	store i32 4, i32*  %14
	store i32 5, i32*  %13
	store i32 6, i32*  %12
	store i32 7, i32*  %11
	store i32 8, i32*  %10
	%18 = load i32, i32*  %10
	store i32 %18, i32*  %9
	%19 = load i32, i32*  %9
	store i32 %19, i32*  %8
	%20 = load i32, i32*  %8
	store i32 %20, i32*  %7
	%21 = load i32, i32*  %7
	store i32 %21, i32*  %6
	%22 = load i32, i32*  %6
	store i32 %22, i32*  %5
	%23 = load i32, i32*  %5
	store i32 %23, i32*  %4
	%24 = load i32, i32*  %4
	store i32 %24, i32*  %3
	%25 = load i32, i32*  %3
	store i32 %25, i32*  %2
	%26 = load i32, i32*  %9
	store i32 %26, i32*  %1
	%27 = load i32, i32*  %8
	store i32 %27, i32*  %17
	%28 = load i32, i32*  %7
	store i32 %28, i32*  %16
	%29 = load i32, i32*  %6
	store i32 %29, i32*  %15
	%30 = load i32, i32*  %5
	store i32 %30, i32*  %14
	%31 = load i32, i32*  %4
	store i32 %31, i32*  %13
	%32 = load i32, i32*  %3
	store i32 %32, i32*  %12
	%33 = load i32, i32*  %9
	store i32 %33, i32*  %11
	%34 = load i32, i32*  %17
	store i32 %34, i32*  %10
	%35 = load i32, i32*  %11
	ret i32 %35
}
