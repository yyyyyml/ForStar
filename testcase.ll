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
@x = dso_local global [100010 x i32] zeroinitializer 
@y = dso_local global [3000000 x i32] zeroinitializer 
@v = dso_local global [3000000 x i32] zeroinitializer 
@a = dso_local global [100010 x i32] zeroinitializer 
@b = dso_local global [100010 x i32] zeroinitializer 
define dso_local void @spmv(i32 %0, i32* %1, i32* %2, i32* %3, i32* %4, i32* %5){
	br label  %7

7:
	%8 = phi i32 [0, %6], [%12, %10]
	%9 = icmp slt i32 %8, %0
	br i1 %9, label  %10, label  %13

10:
	%11= getelementptr i32,i32* %5, i32 %8
	store i32 0, i32* %11
	%12 = add i32 %8, 1
	br label  %7

13:
	br label  %14

14:
	%15 = phi i32 [0, %13], [%65, %64]
	%16 = icmp slt i32 %15, %0
	br i1 %16, label  %17, label  %20

17:
	%18= getelementptr i32,i32* %1, i32 %15
	%19 = load i32, i32* %18
	br label  %21

20:
	ret void 
21:
	%22 = phi i32 [%19, %17], [%38, %27]
	%23 = add i32 %15, 1
	%24= getelementptr i32,i32* %1, i32 %23
	%25 = load i32, i32* %24
	%26 = icmp slt i32 %22, %25
	br i1 %26, label  %27, label  %39

27:
	%28= getelementptr i32,i32* %2, i32 %22
	%29 = load i32, i32* %28
	%30= getelementptr i32,i32* %5, i32 %29
	%31= getelementptr i32,i32* %2, i32 %22
	%32 = load i32, i32* %31
	%33= getelementptr i32,i32* %5, i32 %32
	%34 = load i32, i32* %33
	%35= getelementptr i32,i32* %3, i32 %22
	%36 = load i32, i32* %35
	%37 = add i32 %34, %36
	store i32 %37, i32* %30
	%38 = add i32 %22, 1
	br label  %21

39:
	%40= getelementptr i32,i32* %1, i32 %15
	%41 = load i32, i32* %40
	br label  %42

42:
	%43 = phi i32 [%41, %39], [%63, %48]
	%44 = add i32 %15, 1
	%45= getelementptr i32,i32* %1, i32 %44
	%46 = load i32, i32* %45
	%47 = icmp slt i32 %43, %46
	br i1 %47, label  %48, label  %64

48:
	%49= getelementptr i32,i32* %2, i32 %43
	%50 = load i32, i32* %49
	%51= getelementptr i32,i32* %5, i32 %50
	%52= getelementptr i32,i32* %2, i32 %43
	%53 = load i32, i32* %52
	%54= getelementptr i32,i32* %5, i32 %53
	%55 = load i32, i32* %54
	%56= getelementptr i32,i32* %3, i32 %43
	%57 = load i32, i32* %56
	%58= getelementptr i32,i32* %4, i32 %15
	%59 = load i32, i32* %58
	%60 = sub i32 %59, 1
	%61 = mul i32 %57, %60
	%62 = add i32 %55, %61
	store i32 %62, i32* %51
	%63 = add i32 %43, 1
	br label  %42

64:
	%65 = add i32 %15, 1
	br label  %14

}
define dso_local i32 @main(){
	%1= getelementptr [100010 x i32],[100010 x i32]* @x, i32 0, i32 0
	%2 = call i32 @getarray(i32* %1)
	%3 = sub i32 %2, 1
	%4= getelementptr [3000000 x i32],[3000000 x i32]* @y, i32 0, i32 0
	%5 = call i32 @getarray(i32* %4)
	%6= getelementptr [3000000 x i32],[3000000 x i32]* @v, i32 0, i32 0
	%7 = call i32 @getarray(i32* %6)
	%8= getelementptr [100010 x i32],[100010 x i32]* @a, i32 0, i32 0
	%9 = call i32 @getarray(i32* %8)
	call void @_sysy_starttime(i32 0)
	br label  %10

10:
	%11 = phi i32 [0, %0], [%24, %13]
	%12 = icmp slt i32 %11, 100
	br i1 %12, label  %13, label  %25

13:
	%14= getelementptr [100010 x i32],[100010 x i32]* @x, i32 0, i32 0
	%15= getelementptr [3000000 x i32],[3000000 x i32]* @y, i32 0, i32 0
	%16= getelementptr [3000000 x i32],[3000000 x i32]* @v, i32 0, i32 0
	%17= getelementptr [100010 x i32],[100010 x i32]* @a, i32 0, i32 0
	%18= getelementptr [100010 x i32],[100010 x i32]* @b, i32 0, i32 0
	call void @spmv(i32 %3, i32* %14, i32* %15, i32* %16, i32* %17, i32* %18)
	%19= getelementptr [100010 x i32],[100010 x i32]* @x, i32 0, i32 0
	%20= getelementptr [3000000 x i32],[3000000 x i32]* @y, i32 0, i32 0
	%21= getelementptr [3000000 x i32],[3000000 x i32]* @v, i32 0, i32 0
	%22= getelementptr [100010 x i32],[100010 x i32]* @b, i32 0, i32 0
	%23= getelementptr [100010 x i32],[100010 x i32]* @a, i32 0, i32 0
	call void @spmv(i32 %3, i32* %19, i32* %20, i32* %21, i32* %22, i32* %23)
	%24 = add i32 %11, 1
	br label  %10

25:
	call void @_sysy_stoptime(i32 0)
	%26= getelementptr [100010 x i32],[100010 x i32]* @b, i32 0, i32 0
	call void @putarray(i32 %3, i32* %26)
	ret i32 0
}
