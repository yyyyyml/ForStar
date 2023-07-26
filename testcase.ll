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
@M = dso_local global i32 0
@L = dso_local global i32 0
@N = dso_local global i32 0
define dso_local i32 @mul(float* %0, float* %1, float* %2, float* %3, float* %4, float* %5, float* %6, float* %7, float* %8){
	%10= getelementptr float,float* %6, i32 0
	%11= getelementptr float,float* %0, i32 0
	%12 = load float, float* %11
	%13= getelementptr float,float* %3, i32 0
	%14 = load float, float* %13
	%15 = fmul float %12, %14
	%16= getelementptr float,float* %0, i32 1
	%17 = load float, float* %16
	%18= getelementptr float,float* %4, i32 0
	%19 = load float, float* %18
	%20 = fmul float %17, %19
	%21 = fadd float %15, %20
	%22= getelementptr float,float* %0, i32 2
	%23 = load float, float* %22
	%24= getelementptr float,float* %5, i32 0
	%25 = load float, float* %24
	%26 = fmul float %23, %25
	%27 = fadd float %21, %26
	store float %27, float* %10
	%28= getelementptr float,float* %6, i32 1
	%29= getelementptr float,float* %0, i32 0
	%30 = load float, float* %29
	%31= getelementptr float,float* %3, i32 1
	%32 = load float, float* %31
	%33 = fmul float %30, %32
	%34= getelementptr float,float* %0, i32 1
	%35 = load float, float* %34
	%36= getelementptr float,float* %4, i32 1
	%37 = load float, float* %36
	%38 = fmul float %35, %37
	%39 = fadd float %33, %38
	%40= getelementptr float,float* %0, i32 2
	%41 = load float, float* %40
	%42= getelementptr float,float* %5, i32 1
	%43 = load float, float* %42
	%44 = fmul float %41, %43
	%45 = fadd float %39, %44
	store float %45, float* %28
	%46= getelementptr float,float* %6, i32 2
	%47= getelementptr float,float* %0, i32 0
	%48 = load float, float* %47
	%49= getelementptr float,float* %3, i32 2
	%50 = load float, float* %49
	%51 = fmul float %48, %50
	%52= getelementptr float,float* %0, i32 1
	%53 = load float, float* %52
	%54= getelementptr float,float* %4, i32 2
	%55 = load float, float* %54
	%56 = fmul float %53, %55
	%57 = fadd float %51, %56
	%58= getelementptr float,float* %0, i32 2
	%59 = load float, float* %58
	%60= getelementptr float,float* %5, i32 2
	%61 = load float, float* %60
	%62 = fmul float %59, %61
	%63 = fadd float %57, %62
	store float %63, float* %46
	%64= getelementptr float,float* %7, i32 0
	%65= getelementptr float,float* %1, i32 0
	%66 = load float, float* %65
	%67= getelementptr float,float* %3, i32 0
	%68 = load float, float* %67
	%69 = fmul float %66, %68
	%70= getelementptr float,float* %1, i32 1
	%71 = load float, float* %70
	%72= getelementptr float,float* %4, i32 0
	%73 = load float, float* %72
	%74 = fmul float %71, %73
	%75 = fadd float %69, %74
	%76= getelementptr float,float* %1, i32 2
	%77 = load float, float* %76
	%78= getelementptr float,float* %5, i32 0
	%79 = load float, float* %78
	%80 = fmul float %77, %79
	%81 = fadd float %75, %80
	store float %81, float* %64
	%82= getelementptr float,float* %7, i32 1
	%83= getelementptr float,float* %1, i32 0
	%84 = load float, float* %83
	%85= getelementptr float,float* %3, i32 1
	%86 = load float, float* %85
	%87 = fmul float %84, %86
	%88= getelementptr float,float* %1, i32 1
	%89 = load float, float* %88
	%90= getelementptr float,float* %4, i32 1
	%91 = load float, float* %90
	%92 = fmul float %89, %91
	%93 = fadd float %87, %92
	%94= getelementptr float,float* %1, i32 2
	%95 = load float, float* %94
	%96= getelementptr float,float* %5, i32 1
	%97 = load float, float* %96
	%98 = fmul float %95, %97
	%99 = fadd float %93, %98
	store float %99, float* %82
	%100= getelementptr float,float* %7, i32 2
	%101= getelementptr float,float* %1, i32 0
	%102 = load float, float* %101
	%103= getelementptr float,float* %3, i32 2
	%104 = load float, float* %103
	%105 = fmul float %102, %104
	%106= getelementptr float,float* %1, i32 1
	%107 = load float, float* %106
	%108= getelementptr float,float* %4, i32 2
	%109 = load float, float* %108
	%110 = fmul float %107, %109
	%111 = fadd float %105, %110
	%112= getelementptr float,float* %1, i32 2
	%113 = load float, float* %112
	%114= getelementptr float,float* %5, i32 2
	%115 = load float, float* %114
	%116 = fmul float %113, %115
	%117 = fadd float %111, %116
	store float %117, float* %100
	%118= getelementptr float,float* %8, i32 0
	%119= getelementptr float,float* %2, i32 0
	%120 = load float, float* %119
	%121= getelementptr float,float* %3, i32 0
	%122 = load float, float* %121
	%123 = fmul float %120, %122
	%124= getelementptr float,float* %2, i32 1
	%125 = load float, float* %124
	%126= getelementptr float,float* %4, i32 0
	%127 = load float, float* %126
	%128 = fmul float %125, %127
	%129 = fadd float %123, %128
	%130= getelementptr float,float* %2, i32 2
	%131 = load float, float* %130
	%132= getelementptr float,float* %5, i32 0
	%133 = load float, float* %132
	%134 = fmul float %131, %133
	%135 = fadd float %129, %134
	store float %135, float* %118
	%136= getelementptr float,float* %8, i32 1
	%137= getelementptr float,float* %2, i32 0
	%138 = load float, float* %137
	%139= getelementptr float,float* %3, i32 1
	%140 = load float, float* %139
	%141 = fmul float %138, %140
	%142= getelementptr float,float* %2, i32 1
	%143 = load float, float* %142
	%144= getelementptr float,float* %4, i32 1
	%145 = load float, float* %144
	%146 = fmul float %143, %145
	%147 = fadd float %141, %146
	%148= getelementptr float,float* %2, i32 2
	%149 = load float, float* %148
	%150= getelementptr float,float* %5, i32 1
	%151 = load float, float* %150
	%152 = fmul float %149, %151
	%153 = fadd float %147, %152
	store float %153, float* %136
	%154= getelementptr float,float* %8, i32 2
	%155= getelementptr float,float* %2, i32 0
	%156 = load float, float* %155
	%157= getelementptr float,float* %3, i32 2
	%158 = load float, float* %157
	%159 = fmul float %156, %158
	%160= getelementptr float,float* %2, i32 1
	%161 = load float, float* %160
	%162= getelementptr float,float* %4, i32 2
	%163 = load float, float* %162
	%164 = fmul float %161, %163
	%165 = fadd float %159, %164
	%166= getelementptr float,float* %2, i32 2
	%167 = load float, float* %166
	%168= getelementptr float,float* %5, i32 2
	%169 = load float, float* %168
	%170 = fmul float %167, %169
	%171 = fadd float %165, %170
	store float %171, float* %154
	ret i32 0
}
define dso_local i32 @main(){
	%1 = alloca [3 x float]
	%2 = alloca [3 x float]
	%3 = alloca [6 x float]
	%4 = alloca [3 x float]
	%5 = alloca [3 x float]
	%6 = alloca [3 x float]
	%7 = alloca [3 x float]
	%8 = alloca [3 x float]
	%9 = alloca [3 x float]
	store i32 3, i32* @N
	store i32 3, i32* @M
	store i32 3, i32* @L
	br label  %10

10:
	%11 = phi i32 [0, %0], [%27, %14]
	%12 = load i32, i32* @M
	%13 = icmp slt i32 %11, %12
	br i1 %13, label  %14, label  %28

14:
	%15= getelementptr [3 x float],[3 x float]* %9, i32 0, i32 %11
	%16 = sitofp i32 %11 to float
	store float %16, float* %15
	%17= getelementptr [3 x float],[3 x float]* %8, i32 0, i32 %11
	%18 = sitofp i32 %11 to float
	store float %18, float* %17
	%19= getelementptr [3 x float],[3 x float]* %7, i32 0, i32 %11
	%20 = sitofp i32 %11 to float
	store float %20, float* %19
	%21= getelementptr [3 x float],[3 x float]* %6, i32 0, i32 %11
	%22 = sitofp i32 %11 to float
	store float %22, float* %21
	%23= getelementptr [3 x float],[3 x float]* %5, i32 0, i32 %11
	%24 = sitofp i32 %11 to float
	store float %24, float* %23
	%25= getelementptr [3 x float],[3 x float]* %4, i32 0, i32 %11
	%26 = sitofp i32 %11 to float
	store float %26, float* %25
	%27 = add i32 %11, 1
	br label  %10

28:
	%29= getelementptr [3 x float],[3 x float]* %9, i32 0, i32 0
	%30= getelementptr [3 x float],[3 x float]* %8, i32 0, i32 0
	%31= getelementptr [3 x float],[3 x float]* %7, i32 0, i32 0
	%32= getelementptr [3 x float],[3 x float]* %6, i32 0, i32 0
	%33= getelementptr [3 x float],[3 x float]* %5, i32 0, i32 0
	%34= getelementptr [3 x float],[3 x float]* %4, i32 0, i32 0
	%35= getelementptr [6 x float],[6 x float]* %3, i32 0, i32 0
	%36= getelementptr [3 x float],[3 x float]* %2, i32 0, i32 0
	%37= getelementptr [3 x float],[3 x float]* %1, i32 0, i32 0
	%38 = call i32 @mul(float* %29, float* %30, float* %31, float* %32, float* %33, float* %34, float* %35, float* %36, float* %37)
	br label  %39

39:
	%40 = phi i32 [%38, %28], [%47, %43]
	%41 = load i32, i32* @N
	%42 = icmp slt i32 %40, %41
	br i1 %42, label  %43, label  %48

43:
	%44= getelementptr [6 x float],[6 x float]* %3, i32 0, i32 %40
	%45 = load float, float* %44
	%46 = fptosi float %45 to i32
	call void @putint(i32 %46)
	%47 = add i32 %40, 1
	br label  %39

48:
	call void @putch(i32 10)
	br label  %49

49:
	%50 = phi i32 [0, %48], [%57, %53]
	%51 = load i32, i32* @N
	%52 = icmp slt i32 %50, %51
	br i1 %52, label  %53, label  %58

53:
	%54= getelementptr [3 x float],[3 x float]* %2, i32 0, i32 %50
	%55 = load float, float* %54
	%56 = fptosi float %55 to i32
	call void @putint(i32 %56)
	%57 = add i32 %50, 1
	br label  %49

58:
	call void @putch(i32 10)
	br label  %59

59:
	%60 = phi i32 [0, %58], [%67, %63]
	%61 = load i32, i32* @N
	%62 = icmp slt i32 %60, %61
	br i1 %62, label  %63, label  %68

63:
	%64= getelementptr [3 x float],[3 x float]* %1, i32 0, i32 %60
	%65 = load float, float* %64
	%66 = fptosi float %65 to i32
	call void @putint(i32 %66)
	%67 = add i32 %60, 1
	br label  %59

68:
	call void @putch(i32 10)
	ret i32 0
}
