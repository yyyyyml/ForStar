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
declare void @starttime()
declare void @stoptime()
declare void @memset(i32*, i32, i32)
define dso_local i32 @ifElseIf(){
	%1 = alloca i32
	%2 = alloca i32
	store i32 5, i32* %2
	store i32 10, i32* %1
	br label  %3

3:
	%4 = load i32, i32* %2
	%5 = icmp eq i32 %4, 6
	br i1 %5, label  %8, label  %34

6:
	%7 = load i32, i32* %2
	ret i32 %7
8:
	%9 = load i32, i32* %2
	ret i32 %9
10:
	br label  %11

11:
	%12 = load i32, i32* %1
	%13 = icmp eq i32 %12, 10
	br i1 %13, label  %31, label  %16

14:
	br label  %6

15:
	store i32 25, i32* %2
	br label  %14

16:
	br label  %17

17:
	%18 = load i32, i32* %1
	%19 = icmp eq i32 %18, 10
	br i1 %19, label  %27, label  %24

20:
	br label  %14

21:
	%22 = load i32, i32* %2
	%23 = add i32 %22, 15
	store i32 %23, i32* %2
	br label  %20

24:
	%25 = load i32, i32* %2
	%26 = sub i32 0, %25
	store i32 %26, i32* %2
	br label  %20

27:
	%28 = load i32, i32* %2
	%29 = sub i32 0, 5
	%30 = icmp eq i32 %28, %29
	br i1 %30, label  %21, label  %24

31:
	%32 = load i32, i32* %2
	%33 = icmp eq i32 %32, 1
	br i1 %33, label  %15, label  %16

34:
	%35 = load i32, i32* %1
	%36 = icmp eq i32 %35, 11
	br i1 %36, label  %8, label  %10

}
define dso_local i32 @main(){
	%1 = call i32 @ifElseIf()
	call void @putint(i32 %1)
	ret i32 0
}
