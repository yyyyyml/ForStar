define dso_local i32 @main(){
	%1 = alloca i32
	store i32 0, i32*  %1
	store i32 2, i32*  %1
	ret i32 0
}
