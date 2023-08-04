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
@program_length = dso_local global i32 0
@program = dso_local global [65536 x i32] zeroinitializer 
@tape = dso_local global [65536 x i32] zeroinitializer 
@input = dso_local global [65536 x i32] zeroinitializer 
@input_length = dso_local global i32 0
@output = dso_local global [65536 x i32] zeroinitializer 
@output_length = dso_local global i32 0
define dso_local i32 @get_bf_char(){
	%1 = call i32 @getch()
	br label  %2

2:
	%3 = phi i32 [%1, %0], [%6, %5]
	%4 = icmp ne i32 %3, 62
	br i1 %4, label  %8, label  %7

5:
	%6 = call i32 @getch()
	br label  %2

7:
	ret i32 %3
8:
	%9 = icmp ne i32 %3, 60
	br i1 %9, label  %10, label  %7

10:
	%11 = icmp ne i32 %3, 43
	br i1 %11, label  %12, label  %7

12:
	%13 = icmp ne i32 %3, 45
	br i1 %13, label  %14, label  %7

14:
	%15 = icmp ne i32 %3, 91
	br i1 %15, label  %16, label  %7

16:
	%17 = icmp ne i32 %3, 93
	br i1 %17, label  %18, label  %7

18:
	%19 = icmp ne i32 %3, 46
	br i1 %19, label  %20, label  %7

20:
	%21 = icmp ne i32 %3, 44
	br i1 %21, label  %22, label  %7

22:
	%23 = icmp ne i32 %3, 35
	br i1 %23, label  %5, label  %7

}
define dso_local void @read_program(){
	%1 = call i32 @get_bf_char()
	br label  %2

2:
	%3 = phi i32 [%1, %0], [%8, %5]
	%4 = icmp ne i32 %3, 35
	br i1 %4, label  %5, label  %11

5:
	%6 = load i32, i32* @program_length
	%7= getelementptr [65536 x i32],[65536 x i32]* @program, i32 0, i32 %6
	store i32 %3, i32* %7
	%8 = call i32 @get_bf_char()
	%9 = load i32, i32* @program_length
	%10 = add i32 %9, 1
	store i32 %10, i32* @program_length
	br label  %2

11:
	%12 = call i32 @getch()
	%13 = icmp ne i32 %12, 105
	br i1 %13, label  %17, label  %14

14:
	%15 = call i32 @getint()
	store i32 %15, i32* @input_length
	%16 = call i32 @getch()
	br label  %18

17:
	ret void 
18:
	%19 = phi i32 [0, %14], [%25, %22]
	%20 = load i32, i32* @input_length
	%21 = icmp slt i32 %19, %20
	br i1 %21, label  %22, label  %26

22:
	%23= getelementptr [65536 x i32],[65536 x i32]* @input, i32 0, i32 %19
	%24 = call i32 @getch()
	store i32 %24, i32* %23
	%25 = add i32 %19, 1
	br label  %18

26:
	ret void 
}
define dso_local void @run_program(){
	%1 = alloca [512 x i32]
	%2= getelementptr [512 x i32],[512 x i32]* %1, i32 0, i32 0
	call void @memset(i32* %2, i32 0, i32 2048)
	store i32 0, i32* @output_length
	br label  %3

3:
	%4 = phi i32 [0, %0], [%20, %15]
	%5 = phi i32 [0, %0], [%17, %15]
	%6 = phi i32 [0, %0], [%18, %15]
	%7 = phi i32 [0, %0], [%19, %15]
	%8 = load i32, i32* @program_length
	%9 = icmp slt i32 %4, %8
	br i1 %9, label  %10, label  %14

10:
	%11= getelementptr [65536 x i32],[65536 x i32]* @program, i32 0, i32 %4
	%12 = load i32, i32* %11
	%13 = icmp eq i32 %12, 62
	br i1 %13, label  %21, label  %23

14:
	ret void 
15:
	%16 = phi i32 [%4, %21], [%4, %25], [%4, %29], [%4, %36], [%4, %47], [%52, %51], [%4, %78], [%83, %80], [%4, %86], [%4, %93], [%4, %98], [%4, %100]
	%17 = phi i32 [%22, %21], [%26, %25], [%5, %29], [%5, %36], [%5, %47], [%53, %51], [%5, %78], [%5, %80], [%5, %86], [%5, %93], [%5, %98], [%5, %100]
	%18 = phi i32 [%6, %21], [%6, %25], [%6, %29], [%6, %36], [%6, %47], [%54, %51], [%6, %78], [%6, %80], [%6, %86], [%6, %93], [%6, %98], [%104, %100]
	%19 = phi i32 [%7, %21], [%7, %25], [%7, %29], [%7, %36], [%49, %47], [%55, %51], [%79, %78], [%7, %80], [%7, %86], [%7, %93], [%7, %98], [%7, %100]
	%20 = add i32 %16, 1
	br label  %3

21:
	%22 = add i32 %5, 1
	br label  %15

23:
	%24 = icmp eq i32 %12, 60
	br i1 %24, label  %25, label  %27

25:
	%26 = sub i32 %5, 1
	br label  %15

27:
	%28 = icmp eq i32 %12, 43
	br i1 %28, label  %29, label  %34

29:
	%30= getelementptr [65536 x i32],[65536 x i32]* @tape, i32 0, i32 %5
	%31= getelementptr [65536 x i32],[65536 x i32]* @tape, i32 0, i32 %5
	%32 = load i32, i32* %31
	%33 = add i32 %32, 1
	store i32 %33, i32* %30
	br label  %15

34:
	%35 = icmp eq i32 %12, 45
	br i1 %35, label  %36, label  %41

36:
	%37= getelementptr [65536 x i32],[65536 x i32]* @tape, i32 0, i32 %5
	%38= getelementptr [65536 x i32],[65536 x i32]* @tape, i32 0, i32 %5
	%39 = load i32, i32* %38
	%40 = sub i32 %39, 1
	store i32 %40, i32* %37
	br label  %15

41:
	%42 = icmp eq i32 %12, 91
	br i1 %42, label  %43, label  %72

43:
	%44= getelementptr [65536 x i32],[65536 x i32]* @tape, i32 0, i32 %5
	%45 = load i32, i32* %44
	%46 = icmp ne i32 %45, 0
	br i1 %46, label  %47, label  %50

47:
	%48= getelementptr [512 x i32],[512 x i32]* %1, i32 0, i32 %7
	store i32 %4, i32* %48
	%49 = add i32 %7, 1
	br label  %15

50:
	br label  %51

51:
	%52 = phi i32 [%4, %50], [%59, %70], [%59, %65]
	%53 = phi i32 [%5, %50], [%53, %70], [%53, %65]
	%54 = phi i32 [%6, %50], [%54, %70], [%54, %65]
	%55 = phi i32 [%7, %50], [%55, %70], [%55, %65]
	%56 = phi i32 [1, %50], [%71, %70], [%66, %65]
	%57 = icmp sgt i32 %56, 0
	br i1 %57, label  %58, label  %15

58:
	%59 = add i32 %52, 1
	%60= getelementptr [65536 x i32],[65536 x i32]* @program, i32 0, i32 %59
	%61 = load i32, i32* %60
	%62 = icmp eq i32 %61, 93
	br i1 %62, label  %63, label  %65

63:
	%64 = sub i32 %56, 1
	br label  %65

65:
	%66 = phi i32 [%64, %63], [%56, %58]
	%67= getelementptr [65536 x i32],[65536 x i32]* @program, i32 0, i32 %59
	%68 = load i32, i32* %67
	%69 = icmp eq i32 %68, 91
	br i1 %69, label  %70, label  %51

70:
	%71 = add i32 %66, 1
	br label  %51

72:
	%73 = icmp eq i32 %12, 93
	br i1 %73, label  %74, label  %84

74:
	%75= getelementptr [65536 x i32],[65536 x i32]* @tape, i32 0, i32 %5
	%76 = load i32, i32* %75
	%77 = icmp eq i32 %76, 0
	br i1 %77, label  %78, label  %80

78:
	%79 = sub i32 %7, 1
	br label  %15

80:
	%81 = sub i32 %7, 1
	%82= getelementptr [512 x i32],[512 x i32]* %1, i32 0, i32 %81
	%83 = load i32, i32* %82
	br label  %15

84:
	%85 = icmp eq i32 %12, 46
	br i1 %85, label  %86, label  %93

86:
	%87 = load i32, i32* @output_length
	%88= getelementptr [65536 x i32],[65536 x i32]* @output, i32 0, i32 %87
	%89= getelementptr [65536 x i32],[65536 x i32]* @tape, i32 0, i32 %5
	%90 = load i32, i32* %89
	store i32 %90, i32* %88
	%91 = load i32, i32* @output_length
	%92 = add i32 %91, 1
	store i32 %92, i32* @output_length
	br label  %15

93:
	%94 = icmp eq i32 %12, 44
	br i1 %94, label  %95, label  %15

95:
	%96 = load i32, i32* @input_length
	%97 = icmp sge i32 %6, %96
	br i1 %97, label  %98, label  %100

98:
	%99= getelementptr [65536 x i32],[65536 x i32]* @tape, i32 0, i32 %5
	store i32 0, i32* %99
	br label  %15

100:
	%101= getelementptr [65536 x i32],[65536 x i32]* @tape, i32 0, i32 %5
	%102= getelementptr [65536 x i32],[65536 x i32]* @input, i32 0, i32 %6
	%103 = load i32, i32* %102
	store i32 %103, i32* %101
	%104 = add i32 %6, 1
	br label  %15

}
define dso_local void @output_(){
	br label  %1

1:
	%2 = phi i32 [0, %0], [%8, %5]
	%3 = load i32, i32* @output_length
	%4 = icmp slt i32 %2, %3
	br i1 %4, label  %5, label  %9

5:
	%6= getelementptr [65536 x i32],[65536 x i32]* @output, i32 0, i32 %2
	%7 = load i32, i32* %6
	call void @putch(i32 %7)
	%8 = add i32 %2, 1
	br label  %1

9:
	ret void 
}
define dso_local i32 @main(){
	call void @read_program()
	call void @_sysy_starttime(i32 0)
	call void @run_program()
	call void @_sysy_stoptime(i32 0)
	call void @output_()
	ret i32 0
}
