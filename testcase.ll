define dso_local i32 @main(){
	%1 = alloca i32
	%2 = alloca i32
	store i32 4, i32*  %2
	store i32 3, i32*  %1
	%3 = add i32 2,3
	%4 = load i32, i32*  %1
	%5 = add i32 %3,%4
	store i32 %5, i32*  %2
	ret i32 0
}
