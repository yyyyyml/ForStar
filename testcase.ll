@g = dso_local global i32 1
@h = dso_local global float 8.7
@i = dso_local global i32 0
define dso_local i32 @fun1(i32 %0,i32 %1,i32 %2,i32 %3,i32 %4,i32 %5,i32 %6,i32 %7,i32 %8){
	%10 = alloca i32
	%11 = alloca i32
	%12 = alloca i32
	%13 = alloca i32
	%14 = alloca i32
	%15 = alloca i32
	%16 = alloca i32
	%17 = alloca i32
	%18 = alloca i32
	store i32 %0, i32*  %18
	store i32 %1, i32*  %17
	store i32 %2, i32*  %16
	store i32 %3, i32*  %15
	store i32 %4, i32*  %14
	store i32 %5, i32*  %13
	store i32 %6, i32*  %12
	store i32 %7, i32*  %11
	store i32 %8, i32*  %10
	%19 = load i32, i32*  %18
	%20 = load i32, i32*  %17
	%21 = add i32 %19,%20
	%22 = load i32, i32*  %16
	%23 = add i32 %21,%22
	%24 = load i32, i32*  %15
	%25 = add i32 %23,%24
	%26 = load i32, i32*  %14
	%27 = add i32 %25,%26
	%28 = load i32, i32*  %13
	%29 = add i32 %27,%28
	%30 = load i32, i32*  %12
	%31 = add i32 %29,%30
	%32 = load i32, i32*  %11
	%33 = add i32 %31,%32
	%34 = load i32, i32*  %10
	%35 = add i32 %33,%34
	ret i32 %35
}
define dso_local float @fun2(float %0,float %1,float %2,float %3,float %4,float %5,float %6,float %7,float %8){
	%10 = alloca float
	%11 = alloca float
	%12 = alloca float
	%13 = alloca float
	%14 = alloca float
	%15 = alloca float
	%16 = alloca float
	%17 = alloca float
	%18 = alloca float
	store float %0, float*  %18
	store float %1, float*  %17
	store float %2, float*  %16
	store float %3, float*  %15
	store float %4, float*  %14
	store float %5, float*  %13
	store float %6, float*  %12
	store float %7, float*  %11
	store float %8, float*  %10
	%19 = load float, float*  %18
	%20 = load float, float*  %17
	%21 = fadd float %19,%20
	%22 = load float, float*  %16
	%23 = fadd float %21,%22
	%24 = load float, float*  %15
	%25 = fadd float %23,%24
	%26 = load float, float*  %14
	%27 = fadd float %25,%26
	%28 = load float, float*  %13
	%29 = fadd float %27,%28
	%30 = load float, float*  %12
	%31 = fadd float %29,%30
	%32 = load float, float*  %11
	%33 = fadd float %31,%32
	%34 = load float, float*  %10
	%35 = fadd float %33,%34
	ret float %35
}
define dso_local i32 @main(){
	%1 = alloca float
	%2 = alloca i32
	%3 = alloca float
	%4 = alloca float
	%5 = alloca float
	%6 = alloca float
	%7 = alloca float
	%8 = alloca float
	%9 = alloca float
	%10 = alloca float
	%11 = alloca float
	%12 = alloca float
	%13 = alloca i32
	%14 = alloca i32
	%15 = alloca i32
	%16 = alloca i32
	%17 = alloca i32
	%18 = alloca i32
	%19 = alloca i32
	%20 = alloca i32
	%21 = alloca i32
	%22 = alloca i32
	store i32 4, i32*  %22
	store i32 3, i32*  %21
	store i32 4, i32*  %20
	store i32 3, i32*  %19
	store i32 4, i32*  %18
	store i32 3, i32*  %17
	store i32 4, i32*  %16
	store i32 3, i32*  %15
	store i32 3, i32*  %14
	%23 = load i32, i32*  @g
	%24 = load i32, i32*  %21
	%25 = mul i32 %23,%24
	%26 = add i32 2,%25
	%27 = load i32, i32*  @i
	%28 = add i32 %26,%27
	store i32 %28, i32*  %13
	store float 5.0, float*  %12
	store float 5.7, float*  %11
	store float 5.0, float*  %10
	store float 5.7, float*  %9
	store float 5.0, float*  %8
	store float 5.7, float*  %7
	store float 5.0, float*  %6
	store float 5.7, float*  %5
	store float 5.2, float*  %4
	%29 = load float, float*  @h
	%30 = load i32, i32*  @g
	%31 = sitofp i32 %30 to float
	%32 = fadd float %29,%31
	store float %32, float*  %3
	%33 = load i32, i32*  %22
	%34 = load i32, i32*  %21
	%35 = load i32, i32*  %20
	%36 = load i32, i32*  %19
	%37 = load i32, i32*  %18
	%38 = load i32, i32*  %17
	%39 = load i32, i32*  %16
	%40 = load i32, i32*  %15
	%41 = load i32, i32*  %14
	%42 = call i32 @fun1(i32 %33, i32 %34, i32 %35, i32 %36, i32 %37, i32 %38, i32 %39, i32 %40, i32 %41)
	store i32 %42, i32*  %2
	%43 = load float, float*  %12
	%44 = load float, float*  %11
	%45 = load float, float*  %10
	%46 = load float, float*  %9
	%47 = load float, float*  %8
	%48 = load float, float*  %7
	%49 = load float, float*  %6
	%50 = load float, float*  %5
	%51 = load float, float*  %4
	%52 = call float @fun2(float %43, float %44, float %45, float %46, float %47, float %48, float %49, float %50, float %51)
	store float %52, float*  %1
	ret i32 0
}
