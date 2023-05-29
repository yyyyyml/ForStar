define dso_local i32 @main(){
	%1 = alloca float
	%2 = alloca float
	%3 = alloca float
	%4 = alloca i32
	%5 = alloca i32
	store i32 4, i32*  %5
	store i32 3, i32*  %4
	%6 = add i32 2,3
	%7 = load i32, i32*  %4
	%8 = add i32 %6,%7
	store i32 %8, i32*  %5
	store float 5.0, float*  %3
	store float 5.7, float*  %2
	%9 = load float, float*  %3
	%10 = load float, float*  %2
	%11 = fadd float %9,%10
	store float %11, float*  %1
	ret i32 0
}
