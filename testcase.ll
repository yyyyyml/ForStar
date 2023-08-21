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
@x = dso_local global [600 x [600 x [600 x i32]]] zeroinitializer 
@y = dso_local global [600 x [600 x [600 x i32]]] zeroinitializer 
define dso_local i32 @main(){
	%1 = call i32 @getint()
	%2 = call i32 @getint()
	call void @_sysy_starttime(i32 0)
	br label  %3

3:
	%4 = phi i32 [0, %0], [%13, %12]
	%5 = icmp slt i32 %4, %1
	br i1 %5, label  %6, label  %7

6:
	br label  %8

7:
	br label  %27

8:
	%9 = phi i32 [0, %6], [%26, %25]
	%10 = icmp slt i32 %9, %1
	br i1 %10, label  %11, label  %12

11:
	br label  %14

12:
	%13 = add i32 %4, 1
	br label  %3

14:
	%15 = phi i32 [0, %11], [%24, %17]
	%16 = icmp slt i32 %15, %1
	br i1 %16, label  %17, label  %25

17:
	%18 = getelementptr [600 x [600 x [600 x i32]]],[600 x [600 x [600 x i32]]]* @x, i32 0, i32 %4
	%19 = getelementptr [600 x [600 x i32]],[600 x [600 x i32]]* %18, i32 0, i32 %9
	%20 = getelementptr [600 x i32],[600 x i32]* %19, i32 0, i32 %15
	store i32 1, i32* %20
	%21 = getelementptr [600 x [600 x [600 x i32]]],[600 x [600 x [600 x i32]]]* @y, i32 0, i32 %4
	%22 = getelementptr [600 x [600 x i32]],[600 x [600 x i32]]* %21, i32 0, i32 %9
	%23 = getelementptr [600 x i32],[600 x i32]* %22, i32 0, i32 %15
	store i32 0, i32* %23
	%24 = add i32 %15, 1
	br label  %14

25:
	%26 = add i32 %9, 1
	br label  %8

27:
	%28 = phi i32 [1, %7], [%47, %51]
	%29 = phi i32 [1, %7], [%52, %51]
	%30 = sub i32 %1, 1
	%31 = icmp slt i32 %29, %30
	br i1 %31, label  %32, label  %33

32:
	br label  %46

33:
	call void @_sysy_stoptime(i32 0)
	%34 = getelementptr [600 x [600 x [600 x i32]]],[600 x [600 x [600 x i32]]]* @x, i32 0, i32 0
	%35 = getelementptr [600 x [600 x i32]],[600 x [600 x i32]]* %34, i32 0, i32 0
	%36 = getelementptr [600 x i32],[600 x i32]* %35, i32 0, i32 0
	call void @putarray(i32 %1, i32* %36)
	%37 = sdiv i32 %1, 2
	%38 = getelementptr [600 x [600 x [600 x i32]]],[600 x [600 x [600 x i32]]]* @x, i32 0, i32 %37
	%39 = getelementptr [600 x [600 x i32]],[600 x [600 x i32]]* %38, i32 0, i32 %37
	%40 = getelementptr [600 x i32],[600 x i32]* %39, i32 0, i32 0
	call void @putarray(i32 %1, i32* %40)
	%41 = sub i32 %29, 1
	%42 = getelementptr [600 x [600 x [600 x i32]]],[600 x [600 x [600 x i32]]]* @x, i32 0, i32 %41
	%43 = sub i32 %28, 1
	%44 = getelementptr [600 x [600 x i32]],[600 x [600 x i32]]* %42, i32 0, i32 %43
	%45 = getelementptr [600 x i32],[600 x i32]* %44, i32 0, i32 0
	call void @putarray(i32 %1, i32* %45)
	ret i32 0
46:
	%47 = phi i32 [1, %32], [%92, %91]
	%48 = sub i32 %1, 1
	%49 = icmp slt i32 %47, %48
	br i1 %49, label  %50, label  %51

50:
	br label  %53

51:
	%52 = add i32 %29, 1
	br label  %27

53:
	%54 = phi i32 [1, %50], [%86, %57]
	%55 = sub i32 %1, 1
	%56 = icmp slt i32 %54, %55
	br i1 %56, label  %57, label  %91

57:
	%58 = getelementptr [600 x [600 x [600 x i32]]],[600 x [600 x [600 x i32]]]* @x, i32 0, i32 %29
	%59 = getelementptr [600 x [600 x i32]],[600 x [600 x i32]]* %58, i32 0, i32 %47
	%60 = getelementptr [600 x i32],[600 x i32]* %59, i32 0, i32 %54
	%61 = sub i32 %29, 1
	%62 = getelementptr [600 x [600 x [600 x i32]]],[600 x [600 x [600 x i32]]]* @x, i32 0, i32 %61
	%63 = getelementptr [600 x [600 x i32]],[600 x [600 x i32]]* %62, i32 0, i32 %47
	%64 = getelementptr [600 x i32],[600 x i32]* %63, i32 0, i32 %54
	%65 = load i32, i32* %64
	%66 = add i32 %29, 1
	%67 = getelementptr [600 x [600 x [600 x i32]]],[600 x [600 x [600 x i32]]]* @x, i32 0, i32 %66
	%68 = getelementptr [600 x [600 x i32]],[600 x [600 x i32]]* %67, i32 0, i32 %47
	%69 = getelementptr [600 x i32],[600 x i32]* %68, i32 0, i32 %54
	%70 = load i32, i32* %69
	%71 = add i32 %65, %70
	%72 = sub i32 %47, 1
	%73 = getelementptr [600 x [600 x i32]],[600 x [600 x i32]]* %58, i32 0, i32 %72
	%74 = getelementptr [600 x i32],[600 x i32]* %73, i32 0, i32 %54
	%75 = load i32, i32* %74
	%76 = add i32 %71, %75
	%77 = add i32 %47, 1
	%78 = getelementptr [600 x [600 x i32]],[600 x [600 x i32]]* %58, i32 0, i32 %77
	%79 = getelementptr [600 x i32],[600 x i32]* %78, i32 0, i32 %54
	%80 = load i32, i32* %79
	%81 = add i32 %76, %80
	%82 = sub i32 %54, 1
	%83 = getelementptr [600 x i32],[600 x i32]* %59, i32 0, i32 %82
	%84 = load i32, i32* %83
	%85 = add i32 %81, %84
	%86 = add i32 %54, 1
	%87 = getelementptr [600 x i32],[600 x i32]* %59, i32 0, i32 %86
	%88 = load i32, i32* %87
	%89 = add i32 %85, %88
	%90 = sdiv i32 %89, %2
	store i32 %90, i32* %60
	br label  %53

91:
	%92 = add i32 %47, 1
	br label  %46

}
