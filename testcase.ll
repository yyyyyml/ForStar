@g = dso_local global i32 9
@h = dso_local global float 8.7
@i = dso_local global i32 0
define dso_local i32 @main(){
	%1 = alloca float
	%2 = alloca float
	%3 = alloca float
	%4 = alloca i32
	%5 = alloca i32
	store i32 4, i32*  %5
	store i32 3, i32*  %4
	%6 = load i32, i32*  @g
	%7 = add i32 2,%6
	%8 = load i32, i32*  %4
	%9 = add i32 %7,%8
	store i32 %9, i32*  %5
	store float 5.0, float*  %3
	store float 5.7, float*  %2
	%10 = load float, float*  @h
	%11 = load float, float*  %2
	%12 = fadd float %10,%11
	store float %12, float*  %1
	ret i32 0
}
