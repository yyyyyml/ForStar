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
@buf = dso_local global [2 x [100 x i32]] zeroinitializer 
define dso_local void @merge_sort(i32 %0, i32 %1){
	%3 = add i32 %0, 1
	%4 = icmp sge i32 %3, %1
	br i1 %4, label  %8, label  %5

5:
	%6 = add i32 %0, %1
	%7 = sdiv i32 %6, 2
	call void @merge_sort(i32 %0, i32 %7)
	call void @merge_sort(i32 %7, i32 %1)
	br label  %9

8:
	ret void 
9:
	%10 = phi i32 [%0, %5], [%28, %27]
	%11 = phi i32 [%7, %5], [%11, %27]
	%12 = phi i32 [%7, %5], [%29, %27]
	%13 = phi i32 [%0, %5], [%30, %27]
	%14 = phi i32 [%0, %5], [%14, %27]
	%15 = phi i32 [%1, %5], [%15, %27]
	%16 = icmp slt i32 %10, %11
	br i1 %16, label  %17, label  %45

17:
	%18 = icmp slt i32 %12, %15
	br i1 %18, label  %19, label  %45

19:
	%20= getelementptr [2 x [100 x i32]],[2 x [100 x i32]]* @buf, i32 0, i32 0
	%21= getelementptr [100 x i32],[100 x i32]* %20, i32 0, i32 %10
	%22 = load i32, i32* %21
	%23= getelementptr [2 x [100 x i32]],[2 x [100 x i32]]* @buf, i32 0, i32 0
	%24= getelementptr [100 x i32],[100 x i32]* %23, i32 0, i32 %12
	%25 = load i32, i32* %24
	%26 = icmp slt i32 %22, %25
	br i1 %26, label  %31, label  %38

27:
	%28 = phi i32 [%37, %31], [%10, %38]
	%29 = phi i32 [%12, %31], [%44, %38]
	%30 = add i32 %13, 1
	br label  %9

31:
	%32= getelementptr [2 x [100 x i32]],[2 x [100 x i32]]* @buf, i32 0, i32 1
	%33= getelementptr [100 x i32],[100 x i32]* %32, i32 0, i32 %13
	%34= getelementptr [2 x [100 x i32]],[2 x [100 x i32]]* @buf, i32 0, i32 0
	%35= getelementptr [100 x i32],[100 x i32]* %34, i32 0, i32 %10
	%36 = load i32, i32* %35
	store i32 %36, i32* %33
	%37 = add i32 %10, 1
	br label  %27

38:
	%39= getelementptr [2 x [100 x i32]],[2 x [100 x i32]]* @buf, i32 0, i32 1
	%40= getelementptr [100 x i32],[100 x i32]* %39, i32 0, i32 %13
	%41= getelementptr [2 x [100 x i32]],[2 x [100 x i32]]* @buf, i32 0, i32 0
	%42= getelementptr [100 x i32],[100 x i32]* %41, i32 0, i32 %12
	%43 = load i32, i32* %42
	store i32 %43, i32* %40
	%44 = add i32 %12, 1
	br label  %27

45:
	%46 = phi i32 [%59, %53], [%10, %9], [%10, %17]
	%47 = phi i32 [%47, %53], [%11, %9], [%11, %17]
	%48 = phi i32 [%48, %53], [%12, %9], [%12, %17]
	%49 = phi i32 [%60, %53], [%13, %9], [%13, %17]
	%50 = phi i32 [%50, %53], [%14, %9], [%14, %17]
	%51 = phi i32 [%51, %53], [%15, %9], [%15, %17]
	%52 = icmp slt i32 %46, %47
	br i1 %52, label  %53, label  %61

53:
	%54= getelementptr [2 x [100 x i32]],[2 x [100 x i32]]* @buf, i32 0, i32 1
	%55= getelementptr [100 x i32],[100 x i32]* %54, i32 0, i32 %49
	%56= getelementptr [2 x [100 x i32]],[2 x [100 x i32]]* @buf, i32 0, i32 0
	%57= getelementptr [100 x i32],[100 x i32]* %56, i32 0, i32 %46
	%58 = load i32, i32* %57
	store i32 %58, i32* %55
	%59 = add i32 %46, 1
	%60 = add i32 %49, 1
	br label  %45

61:
	%62 = phi i32 [%74, %67], [%49, %45]
	%63 = phi i32 [%73, %67], [%48, %45]
	%64 = phi i32 [%64, %67], [%50, %45]
	%65 = phi i32 [%65, %67], [%51, %45]
	%66 = icmp slt i32 %63, %65
	br i1 %66, label  %67, label  %75

67:
	%68= getelementptr [2 x [100 x i32]],[2 x [100 x i32]]* @buf, i32 0, i32 1
	%69= getelementptr [100 x i32],[100 x i32]* %68, i32 0, i32 %62
	%70= getelementptr [2 x [100 x i32]],[2 x [100 x i32]]* @buf, i32 0, i32 0
	%71= getelementptr [100 x i32],[100 x i32]* %70, i32 0, i32 %63
	%72 = load i32, i32* %71
	store i32 %72, i32* %69
	%73 = add i32 %63, 1
	%74 = add i32 %62, 1
	br label  %61

75:
	%76 = phi i32 [%85, %79], [%64, %61]
	%77 = phi i32 [%77, %79], [%65, %61]
	%78 = icmp slt i32 %76, %77
	br i1 %78, label  %79, label  %86

79:
	%80= getelementptr [2 x [100 x i32]],[2 x [100 x i32]]* @buf, i32 0, i32 0
	%81= getelementptr [100 x i32],[100 x i32]* %80, i32 0, i32 %76
	%82= getelementptr [2 x [100 x i32]],[2 x [100 x i32]]* @buf, i32 0, i32 1
	%83= getelementptr [100 x i32],[100 x i32]* %82, i32 0, i32 %76
	%84 = load i32, i32* %83
	store i32 %84, i32* %81
	%85 = add i32 %76, 1
	br label  %75

86:
	ret void 
}
define dso_local i32 @main(){
	%1= getelementptr [2 x [100 x i32]],[2 x [100 x i32]]* @buf, i32 0, i32 0
	%2= getelementptr [100 x i32],[100 x i32]* %1, i32 0, i32 0
	%3 = call i32 @getarray(i32* %2)
	call void @merge_sort(i32 0, i32 %3)
	%4= getelementptr [2 x [100 x i32]],[2 x [100 x i32]]* @buf, i32 0, i32 0
	%5= getelementptr [100 x i32],[100 x i32]* %4, i32 0, i32 0
	call void @putarray(i32 %3, i32* %5)
	ret i32 0
}
