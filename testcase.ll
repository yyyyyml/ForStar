define dso_local i32 @fun(){
	%1 = alloca i32
	store i32 7, i32*  %1
	%2 = load i32, i32*  %1
	ret i32 %2
}
define dso_local i32 @main(){
	%1 = alloca i32
	store i32 5, i32*  %1
	%2 = load i32, i32*  %1
	ret i32 %2
}
