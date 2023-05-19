define dso_local i32 @main(){
	%1 = alloca i32
	%2 = alloca i32
	%3 = alloca i32
	%4 = alloca i32
	%5 = alloca i32
	%6 = alloca i32
	%7 = alloca i32
	%8 = alloca i32
	store i32 0, i32*  %8
	store i32 0, i32*  %7
	store i32 0, i32*  %6
	store i32 0, i32*  %5
	store i32 0, i32*  %4
	store i32 0, i32*  %3
	store i32 0, i32*  %2
	store i32 0, i32*  %1
	store i32 1, i32*  %8
	store i32 2, i32*  %7
	store i32 3, i32*  %6
	store i32 4, i32*  %5
	store i32 5, i32*  %4
	store i32 6, i32*  %3
	store i32 7, i32*  %2
	store i32 8, i32*  %1
	ret i32 1
}
