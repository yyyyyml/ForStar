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
@a = dso_local global [10000 x i32] zeroinitializer 
define dso_local i32 @main(){
	%1 = alloca [31 x i32]
	%2 = call i32 @getint()
	%3 = call i32 @getint()
	call void @_sysy_starttime(i32 0)
	br label  %4

4:
	%5 = phi i32 [%3, %0], [%31, %158]
	%6 = phi i32 [%2, %0], [%9, %158]
	%7 = icmp sgt i32 %6, 0
	br i1 %7, label  %8, label  %159

8:
	%9 = sub i32 %6, 1
	%10 = mul i32 %5, 19971231
	%11 = add i32 %10, 19981013
	%12 = sdiv i32 %11, 1000000007
	%13 = mul i32 %12, 1000000007
	%14 = sub i32 %11, %13
	%15 = icmp slt i32 %14, 0
	br i1 %15, label  %18, label  %16

16:
	%17 = phi i32 [%19, %18], [%14, %8]
	br label  %20

18:
	%19 = add i32 1000000007, %14
	br label  %16

20:
	%21 = sdiv i32 %17, 300000
	%22 = mul i32 %21, 300000
	%23 = sub i32 %17, %22
	%24 = mul i32 %17, 19971231
	%25 = add i32 %24, 19981013
	%26 = sdiv i32 %25, 1000000007
	%27 = mul i32 %26, 1000000007
	%28 = sub i32 %25, %27
	%29 = icmp slt i32 %28, 0
	br i1 %29, label  %32, label  %30

30:
	%31 = phi i32 [%28, %20], [%33, %32]
	br label  %34

32:
	%33 = add i32 1000000007, %28
	br label  %30

34:
	%35 = sdiv i32 %31, 2
	%36 = mul i32 %35, 2
	%37 = sub i32 %31, %36
	%38 = getelementptr [10000 x i32],[10000 x i32]* @a, i32 0, i32 0
	%39 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 0
	call void @memset(i32* %39, i32 0, i32 124)
	%40 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 0
	store i32 1, i32* %40
	%41 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 1
	%42 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 0
	%43 = load i32, i32* %42
	%44 = mul i32 %43, 2
	store i32 %44, i32* %41
	%45 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 2
	%46 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 1
	%47 = load i32, i32* %46
	%48 = mul i32 %47, 2
	store i32 %48, i32* %45
	%49 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 3
	%50 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 2
	%51 = load i32, i32* %50
	%52 = mul i32 %51, 2
	store i32 %52, i32* %49
	%53 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 4
	%54 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 3
	%55 = load i32, i32* %54
	%56 = mul i32 %55, 2
	store i32 %56, i32* %53
	%57 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 5
	%58 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 4
	%59 = load i32, i32* %58
	%60 = mul i32 %59, 2
	store i32 %60, i32* %57
	%61 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 6
	%62 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 5
	%63 = load i32, i32* %62
	%64 = mul i32 %63, 2
	store i32 %64, i32* %61
	%65 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 7
	%66 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 6
	%67 = load i32, i32* %66
	%68 = mul i32 %67, 2
	store i32 %68, i32* %65
	%69 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 8
	%70 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 7
	%71 = load i32, i32* %70
	%72 = mul i32 %71, 2
	store i32 %72, i32* %69
	%73 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 9
	%74 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 8
	%75 = load i32, i32* %74
	%76 = mul i32 %75, 2
	store i32 %76, i32* %73
	%77 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 10
	%78 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 9
	%79 = load i32, i32* %78
	%80 = mul i32 %79, 2
	store i32 %80, i32* %77
	br label  %81

81:
	%82 = phi i32 [%85, %84], [10, %34]
	%83 = icmp slt i32 %82, 30
	br i1 %83, label  %84, label  %91

84:
	%85 = add i32 %82, 1
	%86 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 %85
	%87 = sub i32 %85, 1
	%88 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 %87
	%89 = load i32, i32* %88
	%90 = mul i32 %89, 2
	store i32 %90, i32* %86
	br label  %81

91:
	%92 = sdiv i32 %23, 30
	%93 = icmp sge i32 %92, 10000
	br i1 %93, label  %158, label  %94

94:
	%95 = sdiv i32 %23, 30
	%96 = getelementptr i32,i32* %38, i32 %95
	%97 = load i32, i32* %96
	%98 = mul i32 %95, 30
	%99 = sub i32 %23, %98
	%100 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 %99
	%101 = load i32, i32* %100
	%102 = sdiv i32 %97, %101
	%103 = sdiv i32 %102, 2
	%104 = mul i32 %103, 2
	%105 = sub i32 %102, %104
	%106 = icmp ne i32 %105, %37
	br i1 %106, label  %114, label  %107

107:
	%108 = phi i32 [0, %94], [%136, %149], [%136, %135], [%157, %151]
	%109 = sdiv i32 %23, 30
	%110 = getelementptr i32,i32* %38, i32 %109
	%111 = getelementptr i32,i32* %38, i32 %109
	%112 = load i32, i32* %111
	%113 = add i32 %112, %108
	store i32 %113, i32* %110
	br label  %158

114:
	%115 = sdiv i32 %23, 30
	%116 = getelementptr i32,i32* %38, i32 %115
	%117 = load i32, i32* %116
	%118 = mul i32 %115, 30
	%119 = sub i32 %23, %118
	%120 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 %119
	%121 = load i32, i32* %120
	%122 = sdiv i32 %117, %121
	%123 = sdiv i32 %122, 2
	%124 = mul i32 %123, 2
	%125 = sub i32 %122, %124
	%126 = icmp eq i32 %125, 0
	br i1 %126, label  %127, label  %135

127:
	%128 = icmp eq i32 %37, 1
	br i1 %128, label  %129, label  %135

129:
	%130 = sdiv i32 %23, 30
	%131 = mul i32 %130, 30
	%132 = sub i32 %23, %131
	%133 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 %132
	%134 = load i32, i32* %133
	br label  %135

135:
	%136 = phi i32 [0, %127], [0, %114], [%134, %129]
	%137 = sdiv i32 %23, 30
	%138 = getelementptr i32,i32* %38, i32 %137
	%139 = load i32, i32* %138
	%140 = mul i32 %137, 30
	%141 = sub i32 %23, %140
	%142 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 %141
	%143 = load i32, i32* %142
	%144 = sdiv i32 %139, %143
	%145 = sdiv i32 %144, 2
	%146 = mul i32 %145, 2
	%147 = sub i32 %144, %146
	%148 = icmp eq i32 %147, 1
	br i1 %148, label  %149, label  %107

149:
	%150 = icmp eq i32 %37, 0
	br i1 %150, label  %151, label  %107

151:
	%152 = sdiv i32 %23, 30
	%153 = mul i32 %152, 30
	%154 = sub i32 %23, %153
	%155 = getelementptr [31 x i32],[31 x i32]* %1, i32 0, i32 %154
	%156 = load i32, i32* %155
	%157 = sub i32 %136, %156
	br label  %107

158:
	br label  %4

159:
	call void @_sysy_stoptime(i32 0)
	%160 = getelementptr [10000 x i32],[10000 x i32]* @a, i32 0, i32 0
	call void @putarray(i32 10000, i32* %160)
	ret i32 0
}
