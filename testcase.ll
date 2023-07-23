declare i32 @getint()
declare i32 @getch()
declare float @getfloat()
declare i32 @getarray(i32*)
declare i32 @getfarray(float*)
declare void @putint(i32)
declare void @putch(i32)
declare void @putfloat(float)
declare void @putarray(i32, i32*)
declare void @putfarray(i32, float*)
declare void @_sysy_starttime(i32)
declare void @_sysy_stoptime(i32)
declare void @memset(i32*, i32, i32)
@loopCount = dso_local global i32 0
define dso_local i32 @func(i32 %0, i32 %1){
	%3 = alloca i32
	%4 = alloca i32
	store i32 %0, i32* %4
	store i32 %1, i32* %3
	%5 = load i32, i32* %4
	%6 = add i32 %5, 1
	store i32 %6, i32* %4
	%7 = load i32, i32* %4
	%8 = load i32, i32* %3
	%9 = sub i32 %7, %8
	store i32 %9, i32* %4
	%10 = load i32, i32* %4
	ret i32 %10
}
define dso_local i32 @main(){
	%1 = alloca i32
	%2 = alloca i32
	%3 = alloca i32
	%4 = alloca i32
	store i32 0, i32* %4
	store i32 0, i32* %3
	%5 = call i32 @getint()
	store i32 %5, i32* @loopCount
	call void @_sysy_starttime(i32 0)
	br label  %6

6:
	%7 = load i32, i32* %3
	%8 = load i32, i32* @loopCount
	%9 = icmp slt i32 %7, %8
	br i1 %9, label  %10, label  %11

10:
	store i32 0, i32* %2
	store i32 0, i32* %1
	br label  %13

11:
	call void @_sysy_stoptime(i32 0)
	%12 = load i32, i32* %4
	call void @putint(i32 %12)
	call void @putch(i32 10)
	ret i32 0
13:
	%14 = load i32, i32* %1
	%15 = icmp slt i32 %14, 60
	br i1 %15, label  %16, label  %23

16:
	%17 = load i32, i32* %2
	%18 = load i32, i32* %3
	%19 = call i32 @func(i32 %18, i32 10000)
	%20 = add i32 %17, %19
	store i32 %20, i32* %2
	%21 = load i32, i32* %1
	%22 = add i32 %21, 1
	store i32 %22, i32* %1
	br label  %13

23:
	%24 = load i32, i32* %2
	%25 = sdiv i32 %24, 60
	store i32 %25, i32* %2
	%26 = load i32, i32* %4
	%27 = load i32, i32* %2
	%28 = add i32 %26, %27
	store i32 %28, i32* %4
	%29 = load i32, i32* %4
	%30 = sdiv i32 %29, 536854529
	%31 = mul i32 %30, 536854529
	%32 = sub i32 %29, %31
	store i32 %32, i32* %4
	%33 = load i32, i32* %3
	%34 = add i32 %33, 1
	store i32 %34, i32* %3
	br label  %6

}
