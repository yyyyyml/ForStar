@g = dso_local global i32 1
@h = dso_local global float 8.7
@i = dso_local global i32 0
define dso_local i32 @fun1(i32 %0,i32 %1,i32 %2,i32 %3,i32 %4,i32 %5,i32 %6,i32 %7,i32 %8,i32 %9){
	%11 = alloca i32
	%12 = alloca i32
	%13 = alloca i32
	%14 = alloca i32
	%15 = alloca i32
	%16 = alloca i32
	%17 = alloca i32
	%18 = alloca i32
	%19 = alloca i32
	%20 = alloca i32
	store i32 %0, i32*  %20
	store i32 %1, i32*  %19
	store i32 %2, i32*  %18
	store i32 %3, i32*  %17
	store i32 %4, i32*  %16
	store i32 %5, i32*  %15
	store i32 %6, i32*  %14
	store i32 %7, i32*  %13
	store i32 %8, i32*  %12
	store i32 %9, i32*  %11
	%21 = load i32, i32*  %20
	%22 = load i32, i32*  %19
	%23 = add i32 %21,%22
	%24 = load i32, i32*  %18
	%25 = add i32 %23,%24
	%26 = load i32, i32*  %17
	%27 = add i32 %25,%26
	%28 = load i32, i32*  %16
	%29 = add i32 %27,%28
	%30 = load i32, i32*  %15
	%31 = add i32 %29,%30
	%32 = load i32, i32*  %14
	%33 = add i32 %31,%32
	%34 = load i32, i32*  %13
	%35 = add i32 %33,%34
	%36 = load i32, i32*  %12
	%37 = add i32 %35,%36
	ret i32 %37
}
define dso_local float @fun2(float %0,float %1,float %2,float %3,float %4,float %5,float %6,float %7,float %8,float %9){
	%11 = alloca float
	%12 = alloca float
	%13 = alloca float
	%14 = alloca float
	%15 = alloca float
	%16 = alloca float
	%17 = alloca float
	%18 = alloca float
	%19 = alloca float
	%20 = alloca float
	store float %0, float*  %20
	store float %1, float*  %19
	store float %2, float*  %18
	store float %3, float*  %17
	store float %4, float*  %16
	store float %5, float*  %15
	store float %6, float*  %14
	store float %7, float*  %13
	store float %8, float*  %12
	store float %9, float*  %11
	%21 = load float, float*  %20
	%22 = load float, float*  %19
	%23 = fadd float %21,%22
	%24 = load float, float*  %18
	%25 = fadd float %23,%24
	%26 = load float, float*  %17
	%27 = fadd float %25,%26
	%28 = load float, float*  %16
	%29 = fadd float %27,%28
	%30 = load float, float*  %15
	%31 = fadd float %29,%30
	%32 = load float, float*  %14
	%33 = fadd float %31,%32
	%34 = load float, float*  %13
	%35 = fadd float %33,%34
	%36 = load float, float*  %12
	%37 = fadd float %35,%36
	ret float %37
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
	%13 = alloca float
	%14 = alloca i32
	%15 = alloca i32
	%16 = alloca i32
	%17 = alloca i32
	%18 = alloca i32
	%19 = alloca i32
	%20 = alloca i32
	%21 = alloca i32
	%22 = alloca i32
	%23 = alloca i32
	%24 = alloca i32
	store i32 4, i32*  %24
	store i32 3, i32*  %23
	store i32 4, i32*  %22
	store i32 3, i32*  %21
	store i32 4, i32*  %20
	store i32 3, i32*  %19
	store i32 4, i32*  %18
	store i32 3, i32*  %17
	store i32 3, i32*  %16
	store i32 3, i32*  %15
	%25 = load i32, i32*  @g
	%26 = load i32, i32*  %23
	%27 = mul i32 %25,%26
	%28 = add i32 2,%27
	%29 = load i32, i32*  @i
	%30 = add i32 %28,%29
	store i32 %30, i32*  %14
	store float 5.0, float*  %13
	store float 5.7, float*  %12
	store float 5.0, float*  %11
	store float 5.7, float*  %10
	store float 5.0, float*  %9
	store float 5.7, float*  %8
	store float 5.0, float*  %7
	store float 5.7, float*  %6
	store float 5.2, float*  %5
	store float 5.2, float*  %4
	%31 = load float, float*  @h
	%32 = load i32, i32*  @g
	%33 = sitofp i32 %32 to float
	%34 = fadd float %31,%33
	store float %34, float*  %3
	%35 = load i32, i32*  %24
	%36 = load i32, i32*  %23
	%37 = load i32, i32*  %22
	%38 = load i32, i32*  %21
	%39 = load i32, i32*  %20
	%40 = load i32, i32*  %19
	%41 = load i32, i32*  %18
	%42 = load i32, i32*  %17
	%43 = load i32, i32*  %16
	%44 = load i32, i32*  %15
	%45 = call i32 @fun1(i32 %35, i32 %36, i32 %37, i32 %38, i32 %39, i32 %40, i32 %41, i32 %42, i32 %43, i32 %44)
	store i32 %45, i32*  %2
	%46 = load float, float*  %13
	%47 = load float, float*  %12
	%48 = load float, float*  %11
	%49 = load float, float*  %10
	%50 = load float, float*  %9
	%51 = load float, float*  %8
	%52 = load float, float*  %7
	%53 = load float, float*  %6
	%54 = load float, float*  %5
	%55 = load float, float*  %4
	%56 = call float @fun2(float %46, float %47, float %48, float %49, float %50, float %51, float %52, float %53, float %54, float %55)
	store float %56, float*  %1
	ret i32 0
}
