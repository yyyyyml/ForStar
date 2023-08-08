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
@a = dso_local global [30 x [30 x i32]] zeroinitializer 
@step = dso_local global [4 x [2 x i32]] [[2 x i32] [i32 1, i32 0], [2 x i32] [i32 -1, i32 0], [2 x i32] [i32 0, i32 1], [2 x i32] [i32 0, i32 -1]]
@w = dso_local global i32 0
@h = dso_local global i32 0
@x_0 = dso_local global i32 0
@y_0 = dso_local global i32 0
@x_1 = dso_local global i32 0
@y_1 = dso_local global i32 0
define dso_local i32 @search(i32 %0, i32 %1, i32 %2){
	%4 = icmp sgt i32 %2, 10
	br i1 %4, label  %6, label  %5

5:
	br label  %7

6:
	ret i32 1073741824
7:
	%8 = phi i32 [0, %5], [%80, %76], [%46, %45], [%65, %64]
	%9 = phi i32 [1073741824, %5], [%77, %76], [%9, %45], [%9, %64]
	%10 = icmp slt i32 %8, 4
	br i1 %10, label  %11, label  %82

11:
	br label  %12

12:
	%13 = phi i32 [%1, %11], [%31, %23]
	%14 = phi i32 [0, %11], [%32, %23]
	%15 = phi i32 [%0, %11], [%27, %23]
	%16= getelementptr [30 x [30 x i32]],[30 x [30 x i32]]* @a, i32 0, i32 %15
	%17= getelementptr [30 x i32],[30 x i32]* %16, i32 0, i32 %13
	%18 = load i32, i32* %17
	%19 = icmp ne i32 %18, 1
	br i1 %19, label  %20, label  %36

20:
	%21 = load i32, i32* @x_1
	%22 = icmp eq i32 %15, %21
	br i1 %22, label  %33, label  %23

23:
	%24= getelementptr [4 x [2 x i32]],[4 x [2 x i32]]* @step, i32 0, i32 %8
	%25= getelementptr [2 x i32],[2 x i32]* %24, i32 0, i32 0
	%26 = load i32, i32* %25
	%27 = add i32 %15, %26
	%28= getelementptr [4 x [2 x i32]],[4 x [2 x i32]]* @step, i32 0, i32 %8
	%29= getelementptr [2 x i32],[2 x i32]* %28, i32 0, i32 1
	%30 = load i32, i32* %29
	%31 = add i32 %13, %30
	%32 = add i32 %14, 1
	br label  %12

33:
	%34 = load i32, i32* @y_1
	%35 = icmp eq i32 %13, %34
	br i1 %35, label  %36, label  %23

36:
	%37 = load i32, i32* @x_1
	%38 = icmp eq i32 %15, %37
	br i1 %38, label  %40, label  %43

39:
	ret i32 1
40:
	%41 = load i32, i32* @y_1
	%42 = icmp eq i32 %13, %41
	br i1 %42, label  %39, label  %43

43:
	%44 = icmp sle i32 %14, 1
	br i1 %44, label  %45, label  %47

45:
	%46 = add i32 %8, 1
	br label  %7

47:
	%48 = icmp eq i32 %15, 0
	br i1 %48, label  %64, label  %66

49:
	%50= getelementptr [30 x [30 x i32]],[30 x [30 x i32]]* @a, i32 0, i32 %15
	%51= getelementptr [30 x i32],[30 x i32]* %50, i32 0, i32 %13
	store i32 0, i32* %51
	%52= getelementptr [4 x [2 x i32]],[4 x [2 x i32]]* @step, i32 0, i32 %8
	%53= getelementptr [2 x i32],[2 x i32]* %52, i32 0, i32 0
	%54 = load i32, i32* %53
	%55 = sub i32 %15, %54
	%56= getelementptr [4 x [2 x i32]],[4 x [2 x i32]]* @step, i32 0, i32 %8
	%57= getelementptr [2 x i32],[2 x i32]* %56, i32 0, i32 1
	%58 = load i32, i32* %57
	%59 = sub i32 %13, %58
	%60 = add i32 %2, 1
	%61 = call i32 @search(i32 %55, i32 %59, i32 %60)
	%62 = add i32 %61, 1
	%63 = icmp slt i32 %62, %9
	br i1 %63, label  %81, label  %76

64:
	%65 = add i32 %8, 1
	br label  %7

66:
	%67 = load i32, i32* @h
	%68 = add i32 %67, 1
	%69 = icmp eq i32 %15, %68
	br i1 %69, label  %64, label  %70

70:
	%71 = icmp eq i32 %13, 0
	br i1 %71, label  %64, label  %72

72:
	%73 = load i32, i32* @w
	%74 = add i32 %73, 1
	%75 = icmp eq i32 %13, %74
	br i1 %75, label  %64, label  %49

76:
	%77 = phi i32 [%62, %81], [%9, %49]
	%78= getelementptr [30 x [30 x i32]],[30 x [30 x i32]]* @a, i32 0, i32 %15
	%79= getelementptr [30 x i32],[30 x i32]* %78, i32 0, i32 %13
	store i32 1, i32* %79
	%80 = add i32 %8, 1
	br label  %7

81:
	br label  %76

82:
	%83 = icmp sgt i32 %9, 10
	br i1 %83, label  %85, label  %84

84:
	ret i32 %9
85:
	ret i32 1073741824
}
define dso_local i32 @main(){
	%1 = call i32 @getint()
	store i32 %1, i32* @w
	%2 = call i32 @getint()
	store i32 %2, i32* @h
	br label  %3

3:
	%4 = load i32, i32* @w
	%5 = icmp ne i32 %4, 0
	br i1 %5, label  %6, label  %7

6:
	br label  %8

7:
	ret i32 0
8:
	%9 = phi i32 [0, %6], [%21, %20]
	%10 = icmp slt i32 %9, 30
	br i1 %10, label  %11, label  %12

11:
	br label  %13

12:
	br label  %22

13:
	%14 = phi i32 [0, %11], [%19, %16]
	%15 = icmp slt i32 %14, 30
	br i1 %15, label  %16, label  %20

16:
	%17= getelementptr [30 x [30 x i32]],[30 x [30 x i32]]* @a, i32 0, i32 %9
	%18= getelementptr [30 x i32],[30 x i32]* %17, i32 0, i32 %14
	store i32 1, i32* %18
	%19 = add i32 %14, 1
	br label  %13

20:
	%21 = add i32 %9, 1
	br label  %8

22:
	%23 = phi i32 [1, %12], [%45, %44]
	%24 = load i32, i32* @h
	%25 = icmp sle i32 %23, %24
	br i1 %25, label  %26, label  %27

26:
	br label  %32

27:
	%28 = load i32, i32* @x_0
	%29 = load i32, i32* @y_0
	%30 = call i32 @search(i32 %28, i32 %29, i32 1)
	%31 = icmp sle i32 %30, 10
	br i1 %31, label  %58, label  %59

32:
	%33 = phi i32 [1, %26], [%47, %46]
	%34 = load i32, i32* @w
	%35 = icmp sle i32 %33, %34
	br i1 %35, label  %36, label  %44

36:
	%37= getelementptr [30 x [30 x i32]],[30 x [30 x i32]]* @a, i32 0, i32 %23
	%38= getelementptr [30 x i32],[30 x i32]* %37, i32 0, i32 %33
	%39 = call i32 @getint()
	store i32 %39, i32* %38
	%40= getelementptr [30 x [30 x i32]],[30 x [30 x i32]]* @a, i32 0, i32 %23
	%41= getelementptr [30 x i32],[30 x i32]* %40, i32 0, i32 %33
	%42 = load i32, i32* %41
	%43 = icmp eq i32 %42, 2
	br i1 %43, label  %48, label  %49

44:
	%45 = add i32 %23, 1
	br label  %22

46:
	%47 = add i32 %33, 1
	br label  %32

48:
	store i32 %23, i32* @x_0
	store i32 %33, i32* @y_0
	br label  %46

49:
	%50= getelementptr [30 x [30 x i32]],[30 x [30 x i32]]* @a, i32 0, i32 %23
	%51= getelementptr [30 x i32],[30 x i32]* %50, i32 0, i32 %33
	%52 = load i32, i32* %51
	%53 = icmp eq i32 %52, 3
	br i1 %53, label  %54, label  %46

54:
	store i32 %23, i32* @x_1
	store i32 %33, i32* @y_1
	br label  %46

55:
	%56 = call i32 @getint()
	store i32 %56, i32* @w
	%57 = call i32 @getint()
	store i32 %57, i32* @h
	br label  %3

58:
	call void @putint(i32 %30)
	call void @putch(i32 10)
	br label  %55

59:
	call void @putint(i32 -1)
	call void @putch(i32 10)
	br label  %55

}
