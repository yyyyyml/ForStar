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
@n = dso_local global i32 0
define dso_local i32 @QuickSort(i32* %0, i32 %1, i32 %2){
	%4 = icmp slt i32 %1, %2
	br i1 %4, label  %6, label  %5

5:
	ret i32 0
6:
	%7= getelementptr i32,i32* %0, i32 %1
	%8 = load i32, i32* %7
	br label  %9

9:
	%10 = phi i32 [%8, %6], [%46, %61], [%46, %59]
	%11 = phi i32 [%2, %6], [%47, %61], [%47, %59]
	%12 = phi i32 [%1, %6], [%48, %61], [%48, %59]
	%13 = phi i32* [%0, %6], [%49, %61], [%49, %59]
	%14 = phi i32 [%2, %6], [%65, %61], [%50, %59]
	%15 = phi i32 [%1, %6], [%51, %61], [%51, %59]
	%16 = icmp slt i32 %12, %14
	br i1 %16, label  %23, label  %17

17:
	%18= getelementptr i32,i32* %13, i32 %12
	store i32 %10, i32* %18
	%19 = sub i32 %12, 1
	%20 = call i32 @QuickSort(i32* %13, i32 %15, i32 %19)
	%21 = add i32 %12, 1
	%22 = call i32 @QuickSort(i32* %13, i32 %21, i32 %11)
	br label  %5

23:
	%24 = phi i32 [%24, %31], [%10, %9]
	%25 = phi i32 [%25, %31], [%11, %9]
	%26 = phi i32 [%26, %31], [%12, %9]
	%27 = phi i32* [%27, %31], [%13, %9]
	%28 = phi i32 [%32, %31], [%14, %9]
	%29 = phi i32 [%29, %31], [%15, %9]
	%30 = icmp slt i32 %26, %28
	br i1 %30, label  %33, label  %38

31:
	%32 = sub i32 %28, 1
	br label  %23

33:
	%34= getelementptr i32,i32* %27, i32 %28
	%35 = load i32, i32* %34
	%36 = sub i32 %24, 1
	%37 = icmp sgt i32 %35, %36
	br i1 %37, label  %31, label  %38

38:
	%39 = icmp slt i32 %26, %28
	br i1 %39, label  %40, label  %45

40:
	%41= getelementptr i32,i32* %27, i32 %26
	%42= getelementptr i32,i32* %27, i32 %28
	%43 = load i32, i32* %42
	store i32 %43, i32* %41
	%44 = add i32 %26, 1
	br label  %45

45:
	%46 = phi i32 [%46, %53], [%24, %40], [%24, %38]
	%47 = phi i32 [%47, %53], [%25, %40], [%25, %38]
	%48 = phi i32 [%54, %53], [%44, %40], [%26, %38]
	%49 = phi i32* [%49, %53], [%27, %40], [%27, %38]
	%50 = phi i32 [%50, %53], [%28, %40], [%28, %38]
	%51 = phi i32 [%51, %53], [%29, %40], [%29, %38]
	%52 = icmp slt i32 %48, %50
	br i1 %52, label  %55, label  %59

53:
	%54 = add i32 %48, 1
	br label  %45

55:
	%56= getelementptr i32,i32* %49, i32 %48
	%57 = load i32, i32* %56
	%58 = icmp slt i32 %57, %46
	br i1 %58, label  %53, label  %59

59:
	%60 = icmp slt i32 %48, %50
	br i1 %60, label  %61, label  %9

61:
	%62= getelementptr i32,i32* %49, i32 %50
	%63= getelementptr i32,i32* %49, i32 %48
	%64 = load i32, i32* %63
	store i32 %64, i32* %62
	%65 = sub i32 %50, 1
	br label  %9

}
define dso_local i32 @main(){
	%1 = alloca [10 x i32]
	store i32 10, i32* @n
	%2 = load i32, i32* @n
	%3 = add i32 %2, 0
	%4= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 0
	store i32 4, i32* %4
	%5= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 1
	store i32 3, i32* %5
	%6= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 2
	store i32 9, i32* %6
	%7= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 3
	store i32 2, i32* %7
	%8= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 4
	store i32 0, i32* %8
	%9= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 5
	store i32 1, i32* %9
	%10= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 6
	store i32 6, i32* %10
	%11= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 7
	store i32 5, i32* %11
	%12= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 8
	store i32 7, i32* %12
	%13= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 9
	store i32 8, i32* %13
	%14= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 0
	%15 = call i32 @QuickSort(i32* %14, i32 0, i32 9)
	br label  %16

16:
	%17 = phi i32 [%15, %0], [%23, %20]
	%18 = load i32, i32* @n
	%19 = icmp slt i32 %17, %18
	br i1 %19, label  %20, label  %24

20:
	%21= getelementptr [10 x i32],[10 x i32]* %1, i32 0, i32 %17
	%22 = load i32, i32* %21
	call void @putint(i32 %22)
	call void @putch(i32 10)
	%23 = add i32 %17, 1
	br label  %16

24:
	ret i32 0
}
