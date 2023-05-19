define dso_local i32 @foo(){
	%1 = alloca i32
	store i32 0, i32*  %1
	%2 = load i32, i32*  %1
	ret i32 %2
}
