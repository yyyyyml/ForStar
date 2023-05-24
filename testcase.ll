define dso_local i32 @main(){
	%1 = alloca i32
	%2 = alloca i32
	store i32 4, i32*  %2
	store i32 5, i32*  %1
	%3 = load i32, i32*  %2
	%4 = load i32, i32*  %2
	%5 = mul i32 %3,%4
	%6 = load i32, i32*  %2
	%7 = load i32, i32*  %2
	%8 = mul i32 %6,%7
	%9 = mul i32 %5,%8
	store i32 %9, i32*  %2
	%10 = load i32, i32*  %2
	%11 = load i32, i32*  %2
	%12 = sdiv i32 %10,%11
	%13 = load i32, i32*  %2
	%14 = load i32, i32*  %2
	%15 = sdiv i32 %13,%14
	%16 = sdiv i32 %12,%15
	store i32 %16, i32*  %2
	ret i32 0
}
