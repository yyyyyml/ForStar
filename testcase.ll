define dso_local i32 @main(){
	%1 = alloca i32
	%2 = alloca float
	%3 = alloca float
	%4 = alloca float
	%5 = alloca i32
	store i32 4, i32*  %5
	store float 5.5, float*  %4
	store float 3.3, float*  %3
	store float 4.4, float*  %2
	store i32 5, i32*  %1
	%6 = load i32, i32*  %5
	%7 = load i32, i32*  %5
	%8 = mul i32 %6,%7
	%9 = load i32, i32*  %5
	%10 = load i32, i32*  %5
	%11 = mul i32 %9,%10
	%12 = mul i32 %8,%11
	store i32 %12, i32*  %5
	%13 = load i32, i32*  %5
	%14 = load i32, i32*  %5
	%15 = sdiv i32 %13,%14
	%16 = load i32, i32*  %5
	%17 = load i32, i32*  %5
	%18 = sdiv i32 %16,%17
	%19 = sdiv i32 %15,%18
	store i32 %19, i32*  %5
	ret i32 0
}
