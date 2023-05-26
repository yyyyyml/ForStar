@r = dso_local global i32 0 
define dso_local i32 @main(){
	%1 = alloca i32
	%2 = alloca i32
	%3 = add i32 4,3
	store i32 %3, i32*  %2
	store i32 3, i32*  %1
	%4 = add i32 2,5
	store i32 %4, i32*  %2
	%5 = add i32 6,3
	store i32 %5, i32*  @r
	%6 = load i32, i32*  @r
	%7 = add i32 %6,3
	store i32 %7, i32*  %2
	ret i32 0
}
