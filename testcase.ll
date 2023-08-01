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
@k = dso_local global i32 0
define dso_local float @params_f40(float %0, float %1, float %2, float %3, float %4, float %5, float %6, float %7, float %8, float %9, float %10, float %11, float %12, float %13, float %14, float %15, float %16, float %17, float %18, float %19, float %20, float %21, float %22, float %23, float %24, float %25, float %26, float %27, float %28, float %29, float %30, float %31, float %32, float %33, float %34, float %35, float %36, float %37, float %38, float %39){
	%41 = alloca [10 x float]
	%42 = sitofp i32 0 to float
	%43 = fcmp one float %39, %42
	br i1 %43, label  %44, label  %90

44:
	%45 = fadd float %0, %1
	%46 = fadd float %45, %2
	%47 = fadd float %46, %3
	%48 = fadd float %4, %5
	%49 = fadd float %48, %6
	%50 = fadd float %49, %7
	%51 = fadd float %8, %9
	%52 = fadd float %51, %10
	%53 = fadd float %52, %11
	%54 = fadd float %12, %13
	%55 = fadd float %54, %14
	%56 = fadd float %55, %15
	%57 = fadd float %16, %17
	%58 = fadd float %57, %18
	%59 = fadd float %58, %19
	%60 = fadd float %20, %21
	%61 = fadd float %60, %22
	%62 = fadd float %61, %23
	%63 = fadd float %24, %25
	%64 = fadd float %63, %26
	%65 = fadd float %64, %27
	%66 = fadd float %28, %29
	%67 = fadd float %66, %30
	%68 = fadd float %67, %31
	%69 = fadd float %32, %33
	%70 = fadd float %69, %34
	%71 = fadd float %70, %35
	%72 = fadd float %36, %37
	%73 = fadd float %72, %38
	%74 = fadd float %73, %39
	%75= getelementptr [10 x float],[10 x float]* %41, i32 0, i32 0
	%76 = bitcast float* %75 to i32*
	call void @memset(i32* %76, i32 0, i32 40)
	store float %47, float* %75
	%77= getelementptr float,float* %75, i32 1
	store float %50, float* %77
	%78= getelementptr float,float* %75, i32 2
	store float %53, float* %78
	%79= getelementptr float,float* %75, i32 3
	store float %56, float* %79
	%80= getelementptr float,float* %75, i32 4
	store float %59, float* %80
	%81= getelementptr float,float* %75, i32 5
	store float %62, float* %81
	%82= getelementptr float,float* %75, i32 6
	store float %65, float* %82
	%83= getelementptr float,float* %75, i32 7
	store float %68, float* %83
	%84= getelementptr float,float* %75, i32 8
	store float %71, float* %84
	%85= getelementptr float,float* %75, i32 9
	store float %74, float* %85
	%86= getelementptr [10 x float],[10 x float]* %41, i32 0, i32 0
	call void @putfarray(i32 10, float* %86)
	%87 = load i32, i32* @k
	%88= getelementptr [10 x float],[10 x float]* %41, i32 0, i32 %87
	%89 = load float, float* %88
	ret float %89
90:
	%91 = fadd float %0, %1
	%92 = fadd float %91, %2
	%93 = call float @params_f40(float %1, float %2, float %3, float %4, float %5, float %6, float %7, float %8, float %9, float %10, float %11, float %12, float %13, float %14, float %15, float %16, float %17, float %18, float %19, float %20, float %21, float %22, float %23, float %24, float %25, float %26, float %27, float %28, float %29, float %30, float %31, float %32, float %33, float %34, float %35, float %36, float %37, float %38, float %39, float %92)
	ret float %93
}
define dso_local float @params_f40_i24(i32 %0, i32 %1, i32 %2, float %3, i32 %4, i32 %5, i32 %6, float %7, float %8, float %9, i32 %10, float %11, float %12, i32 %13, float %14, i32 %15, float %16, float %17, float %18, float %19, float %20, float %21, i32 %22, float %23, i32 %24, i32 %25, float %26, float %27, float %28, float %29, float %30, i32 %31, float %32, i32 %33, float %34, float %35, float %36, float %37, i32 %38, i32 %39, float %40, float %41, float %42, i32 %43, float %44, i32 %45, i32 %46, float %47, float %48, float %49, float %50, i32 %51, i32 %52, i32 %53, float %54, float %55, float %56, float %57, float %58, float %59, i32 %60, float %61, i32 %62, float %63){
	%65 = alloca [8 x i32]
	%66 = alloca [10 x float]
	%67 = icmp ne i32 %0, 0
	br i1 %67, label  %68, label  %154

68:
	%69 = fadd float %42, %18
	%70 = fadd float %69, %61
	%71 = fadd float %70, %11
	%72 = fadd float %3, %36
	%73 = fadd float %72, %21
	%74 = fadd float %73, %9
	%75 = fadd float %7, %29
	%76 = fadd float %75, %41
	%77 = fadd float %76, %19
	%78 = fadd float %17, %49
	%79 = fadd float %78, %40
	%80 = fadd float %79, %8
	%81 = fadd float %35, %59
	%82 = fadd float %81, %54
	%83 = fadd float %82, %55
	%84 = fadd float %26, %34
	%85 = fadd float %84, %56
	%86 = fadd float %85, %28
	%87 = fadd float %48, %30
	%88 = fadd float %87, %57
	%89 = fadd float %88, %47
	%90 = fadd float %12, %50
	%91 = fadd float %90, %16
	%92 = fadd float %91, %44
	%93 = fadd float %23, %27
	%94 = fadd float %93, %37
	%95 = fadd float %94, %63
	%96 = fadd float %58, %14
	%97 = fadd float %96, %20
	%98 = fadd float %97, %32
	%99= getelementptr [10 x float],[10 x float]* %66, i32 0, i32 0
	%100 = bitcast float* %99 to i32*
	call void @memset(i32* %100, i32 0, i32 40)
	store float %71, float* %99
	%101= getelementptr float,float* %99, i32 1
	store float %74, float* %101
	%102= getelementptr float,float* %99, i32 2
	store float %77, float* %102
	%103= getelementptr float,float* %99, i32 3
	store float %80, float* %103
	%104= getelementptr float,float* %99, i32 4
	store float %83, float* %104
	%105= getelementptr float,float* %99, i32 5
	store float %86, float* %105
	%106= getelementptr float,float* %99, i32 6
	store float %89, float* %106
	%107= getelementptr float,float* %99, i32 7
	store float %92, float* %107
	%108= getelementptr float,float* %99, i32 8
	store float %95, float* %108
	%109= getelementptr float,float* %99, i32 9
	store float %98, float* %109
	%110 = add i32 %13, %4
	%111 = add i32 %110, %1
	%112 = add i32 %51, %5
	%113 = add i32 %112, %6
	%114 = add i32 %2, %22
	%115 = add i32 %114, %31
	%116 = add i32 %39, %24
	%117 = add i32 %116, %45
	%118 = add i32 %43, %25
	%119 = add i32 %118, %62
	%120 = add i32 %60, %46
	%121 = add i32 %120, %33
	%122 = add i32 %38, %15
	%123 = add i32 %122, %53
	%124 = add i32 %52, %10
	%125 = add i32 %124, %0
	%126= getelementptr [8 x i32],[8 x i32]* %65, i32 0, i32 0
	call void @memset(i32* %126, i32 0, i32 32)
	store i32 %111, i32* %126
	%127= getelementptr i32,i32* %126, i32 1
	store i32 %113, i32* %127
	%128= getelementptr i32,i32* %126, i32 2
	store i32 %115, i32* %128
	%129= getelementptr i32,i32* %126, i32 3
	store i32 %117, i32* %129
	%130= getelementptr i32,i32* %126, i32 4
	store i32 %119, i32* %130
	%131= getelementptr i32,i32* %126, i32 5
	store i32 %121, i32* %131
	%132= getelementptr i32,i32* %126, i32 6
	store i32 %123, i32* %132
	%133= getelementptr i32,i32* %126, i32 7
	store i32 %125, i32* %133
	%134= getelementptr [10 x float],[10 x float]* %66, i32 0, i32 0
	call void @putfarray(i32 10, float* %134)
	%135= getelementptr [8 x i32],[8 x i32]* %65, i32 0, i32 0
	call void @putarray(i32 8, i32* %135)
	br label  %136

136:
	%137 = phi i32 [0, %68], [%148, %139]
	%138 = icmp slt i32 %137, 8
	br i1 %138, label  %139, label  %149

139:
	%140= getelementptr [8 x i32],[8 x i32]* %65, i32 0, i32 %137
	%141= getelementptr [8 x i32],[8 x i32]* %65, i32 0, i32 %137
	%142 = load i32, i32* %141
	%143= getelementptr [10 x float],[10 x float]* %66, i32 0, i32 %137
	%144 = load float, float* %143
	%145 = sitofp i32 %142 to float
	%146 = fsub float %145, %144
	%147 = fptosi float %146 to i32
	store i32 %147, i32* %140
	%148 = add i32 %137, 1
	br label  %136

149:
	%150 = load i32, i32* @k
	%151= getelementptr [8 x i32],[8 x i32]* %65, i32 0, i32 %150
	%152 = load i32, i32* %151
	%153 = sitofp i32 %152 to float
	ret float %153
154:
	%155 = call float @params_f40_i24(i32 %4, i32 %1, i32 %2, float %3, i32 %4, i32 %5, i32 %6, float %7, float %8, float %9, i32 %10, float %11, float %12, i32 %13, float %14, i32 %15, float %16, float %17, float %18, float %19, float %20, float %21, i32 %22, float %23, i32 %24, i32 %25, float %26, float %27, float %28, float %29, float %30, i32 %31, float %32, i32 %33, float %34, float %35, float %36, float %37, i32 %38, i32 %39, float %40, float %41, float %42, i32 %43, float %44, i32 %45, i32 %46, float %47, float %48, float %49, float %50, i32 %51, i32 %52, i32 %53, float %54, float %55, float %56, float %57, float %58, float %59, i32 %60, float %61, i32 %62, float %63)
	ret float %155
}
define dso_local float @params_fa40(float* %0, float* %1, float* %2, float* %3, float* %4, float* %5, float* %6, float* %7, float* %8, float* %9, float* %10, float* %11, float* %12, float* %13, float* %14, float* %15, float* %16, float* %17, float* %18, float* %19, float* %20, float* %21, float* %22, float* %23, float* %24, float* %25, float* %26, float* %27, float* %28, float* %29, float* %30, float* %31, float* %32, float* %33, float* %34, float* %35, float* %36, float* %37, float* %38, float* %39){
	%41 = alloca [10 x float]
	%42 = load i32, i32* @k
	%43= getelementptr float,float* %0, i32 %42
	%44 = load float, float* %43
	%45 = load i32, i32* @k
	%46= getelementptr float,float* %1, i32 %45
	%47 = load float, float* %46
	%48 = fadd float %44, %47
	%49 = load i32, i32* @k
	%50= getelementptr float,float* %2, i32 %49
	%51 = load float, float* %50
	%52 = fadd float %48, %51
	%53 = load i32, i32* @k
	%54= getelementptr float,float* %3, i32 %53
	%55 = load float, float* %54
	%56 = fadd float %52, %55
	%57 = load i32, i32* @k
	%58= getelementptr float,float* %4, i32 %57
	%59 = load float, float* %58
	%60 = load i32, i32* @k
	%61= getelementptr float,float* %5, i32 %60
	%62 = load float, float* %61
	%63 = fadd float %59, %62
	%64 = load i32, i32* @k
	%65= getelementptr float,float* %6, i32 %64
	%66 = load float, float* %65
	%67 = fadd float %63, %66
	%68 = load i32, i32* @k
	%69= getelementptr float,float* %7, i32 %68
	%70 = load float, float* %69
	%71 = fadd float %67, %70
	%72 = load i32, i32* @k
	%73= getelementptr float,float* %8, i32 %72
	%74 = load float, float* %73
	%75 = load i32, i32* @k
	%76= getelementptr float,float* %9, i32 %75
	%77 = load float, float* %76
	%78 = fadd float %74, %77
	%79 = load i32, i32* @k
	%80= getelementptr float,float* %10, i32 %79
	%81 = load float, float* %80
	%82 = fadd float %78, %81
	%83 = load i32, i32* @k
	%84= getelementptr float,float* %11, i32 %83
	%85 = load float, float* %84
	%86 = fadd float %82, %85
	%87 = load i32, i32* @k
	%88= getelementptr float,float* %12, i32 %87
	%89 = load float, float* %88
	%90 = load i32, i32* @k
	%91= getelementptr float,float* %13, i32 %90
	%92 = load float, float* %91
	%93 = fadd float %89, %92
	%94 = load i32, i32* @k
	%95= getelementptr float,float* %14, i32 %94
	%96 = load float, float* %95
	%97 = fadd float %93, %96
	%98 = load i32, i32* @k
	%99= getelementptr float,float* %15, i32 %98
	%100 = load float, float* %99
	%101 = fadd float %97, %100
	%102 = load i32, i32* @k
	%103= getelementptr float,float* %16, i32 %102
	%104 = load float, float* %103
	%105 = load i32, i32* @k
	%106= getelementptr float,float* %17, i32 %105
	%107 = load float, float* %106
	%108 = fadd float %104, %107
	%109 = load i32, i32* @k
	%110= getelementptr float,float* %18, i32 %109
	%111 = load float, float* %110
	%112 = fadd float %108, %111
	%113 = load i32, i32* @k
	%114= getelementptr float,float* %19, i32 %113
	%115 = load float, float* %114
	%116 = fadd float %112, %115
	%117 = load i32, i32* @k
	%118= getelementptr float,float* %20, i32 %117
	%119 = load float, float* %118
	%120 = load i32, i32* @k
	%121= getelementptr float,float* %21, i32 %120
	%122 = load float, float* %121
	%123 = fadd float %119, %122
	%124 = load i32, i32* @k
	%125= getelementptr float,float* %22, i32 %124
	%126 = load float, float* %125
	%127 = fadd float %123, %126
	%128 = load i32, i32* @k
	%129= getelementptr float,float* %23, i32 %128
	%130 = load float, float* %129
	%131 = fadd float %127, %130
	%132 = load i32, i32* @k
	%133= getelementptr float,float* %24, i32 %132
	%134 = load float, float* %133
	%135 = load i32, i32* @k
	%136= getelementptr float,float* %25, i32 %135
	%137 = load float, float* %136
	%138 = fadd float %134, %137
	%139 = load i32, i32* @k
	%140= getelementptr float,float* %26, i32 %139
	%141 = load float, float* %140
	%142 = fadd float %138, %141
	%143 = load i32, i32* @k
	%144= getelementptr float,float* %27, i32 %143
	%145 = load float, float* %144
	%146 = fadd float %142, %145
	%147 = load i32, i32* @k
	%148= getelementptr float,float* %28, i32 %147
	%149 = load float, float* %148
	%150 = load i32, i32* @k
	%151= getelementptr float,float* %29, i32 %150
	%152 = load float, float* %151
	%153 = fadd float %149, %152
	%154 = load i32, i32* @k
	%155= getelementptr float,float* %30, i32 %154
	%156 = load float, float* %155
	%157 = fadd float %153, %156
	%158 = load i32, i32* @k
	%159= getelementptr float,float* %31, i32 %158
	%160 = load float, float* %159
	%161 = fadd float %157, %160
	%162 = load i32, i32* @k
	%163= getelementptr float,float* %32, i32 %162
	%164 = load float, float* %163
	%165 = load i32, i32* @k
	%166= getelementptr float,float* %33, i32 %165
	%167 = load float, float* %166
	%168 = fadd float %164, %167
	%169 = load i32, i32* @k
	%170= getelementptr float,float* %34, i32 %169
	%171 = load float, float* %170
	%172 = fadd float %168, %171
	%173 = load i32, i32* @k
	%174= getelementptr float,float* %35, i32 %173
	%175 = load float, float* %174
	%176 = fadd float %172, %175
	%177 = load i32, i32* @k
	%178= getelementptr float,float* %36, i32 %177
	%179 = load float, float* %178
	%180 = load i32, i32* @k
	%181= getelementptr float,float* %37, i32 %180
	%182 = load float, float* %181
	%183 = fadd float %179, %182
	%184 = load i32, i32* @k
	%185= getelementptr float,float* %38, i32 %184
	%186 = load float, float* %185
	%187 = fadd float %183, %186
	%188 = load i32, i32* @k
	%189= getelementptr float,float* %39, i32 %188
	%190 = load float, float* %189
	%191 = fadd float %187, %190
	%192= getelementptr [10 x float],[10 x float]* %41, i32 0, i32 0
	%193 = bitcast float* %192 to i32*
	call void @memset(i32* %193, i32 0, i32 40)
	store float %56, float* %192
	%194= getelementptr float,float* %192, i32 1
	store float %71, float* %194
	%195= getelementptr float,float* %192, i32 2
	store float %86, float* %195
	%196= getelementptr float,float* %192, i32 3
	store float %101, float* %196
	%197= getelementptr float,float* %192, i32 4
	store float %116, float* %197
	%198= getelementptr float,float* %192, i32 5
	store float %131, float* %198
	%199= getelementptr float,float* %192, i32 6
	store float %146, float* %199
	%200= getelementptr float,float* %192, i32 7
	store float %161, float* %200
	%201= getelementptr float,float* %192, i32 8
	store float %176, float* %201
	%202= getelementptr float,float* %192, i32 9
	store float %191, float* %202
	%203 = load i32, i32* @k
	%204= getelementptr float,float* %39, i32 %203
	%205 = load float, float* %204
	%206 = sitofp i32 0 to float
	%207 = fcmp one float %205, %206
	%208 = zext i1 %207 to i32
	%209 = icmp ne i32 %208, 0
	%210 = sitofp i1 %209 to float
	%211 = fcmp one float %210, 0x0
	%212 = sitofp i1 %211 to float
	%213 = fcmp one float %212, 0x0
	br i1 %213, label  %214, label  %219

214:
	%215= getelementptr [10 x float],[10 x float]* %41, i32 0, i32 0
	call void @putfarray(i32 10, float* %215)
	%216 = load i32, i32* @k
	%217= getelementptr [10 x float],[10 x float]* %41, i32 0, i32 %216
	%218 = load float, float* %217
	ret float %218
219:
	%220= getelementptr [10 x float],[10 x float]* %41, i32 0, i32 0
	%221 = call float @params_fa40(float* %1, float* %2, float* %3, float* %4, float* %5, float* %6, float* %7, float* %8, float* %9, float* %10, float* %11, float* %12, float* %13, float* %14, float* %15, float* %16, float* %17, float* %18, float* %19, float* %20, float* %21, float* %22, float* %23, float* %24, float* %25, float* %26, float* %27, float* %28, float* %29, float* %30, float* %31, float* %32, float* %33, float* %34, float* %35, float* %36, float* %37, float* %38, float* %39, float* %220)
	ret float %221
}
define dso_local i32 @params_mix(float %0, i32* %1, i32 %2, float* %3, float %4, i32 %5, float %6, float %7, float* %8, i32* %9, i32 %10, i32 %11, float* %12, i32* %13, i32* %14, i32 %15, float* %16, float* %17, float %18, float %19, float %20, float* %21, i32 %22, float %23, float %24, float %25, i32* %26, float* %27, i32* %28, i32* %29, float* %30, float %31, float %32, i32* %33, i32 %34, float* %35, float* %36, float %37, float %38, i32* %39, i32* %40, i32 %41, i32 %42, float %43, float %44, i32* %45, i32 %46, float* %47, i32 %48, i32* %49, i32* %50, float %51, float %52, float* %53, i32 %54, i32* %55, float* %56, float %57, i32 %58, float %59, float* %60, float* %61, float %62, i32 %63){
	%65 = alloca [10 x i32]
	%66 = alloca [10 x float]
	%67 = load i32, i32* @k
	%68= getelementptr float,float* %3, i32 %67
	%69 = load float, float* %68
	%70 = fadd float %0, %69
	%71 = fadd float %70, %4
	%72 = fadd float %71, %6
	%73 = load i32, i32* @k
	%74= getelementptr float,float* %8, i32 %73
	%75 = load float, float* %74
	%76 = fadd float %7, %75
	%77 = load i32, i32* @k
	%78= getelementptr float,float* %12, i32 %77
	%79 = load float, float* %78
	%80 = fadd float %76, %79
	%81 = load i32, i32* @k
	%82= getelementptr float,float* %16, i32 %81
	%83 = load float, float* %82
	%84 = fadd float %80, %83
	%85 = load i32, i32* @k
	%86= getelementptr float,float* %17, i32 %85
	%87 = load float, float* %86
	%88 = fadd float %87, %18
	%89 = fadd float %88, %19
	%90 = fadd float %89, %20
	%91 = load i32, i32* @k
	%92= getelementptr float,float* %21, i32 %91
	%93 = load float, float* %92
	%94 = fadd float %93, %23
	%95 = fadd float %94, %24
	%96 = fadd float %95, %25
	%97 = load i32, i32* @k
	%98= getelementptr float,float* %27, i32 %97
	%99 = load float, float* %98
	%100 = load i32, i32* @k
	%101= getelementptr float,float* %30, i32 %100
	%102 = load float, float* %101
	%103 = fadd float %99, %102
	%104 = fadd float %103, %31
	%105 = fadd float %104, %32
	%106 = load i32, i32* @k
	%107= getelementptr float,float* %35, i32 %106
	%108 = load float, float* %107
	%109 = load i32, i32* @k
	%110= getelementptr float,float* %36, i32 %109
	%111 = load float, float* %110
	%112 = fadd float %108, %111
	%113 = fadd float %112, %37
	%114 = fadd float %113, %38
	%115 = fadd float %43, %44
	%116 = load i32, i32* @k
	%117= getelementptr float,float* %47, i32 %116
	%118 = load float, float* %117
	%119 = fadd float %115, %118
	%120 = fadd float %119, %51
	%121 = load i32, i32* @k
	%122= getelementptr float,float* %53, i32 %121
	%123 = load float, float* %122
	%124 = fadd float %52, %123
	%125 = load i32, i32* @k
	%126= getelementptr float,float* %56, i32 %125
	%127 = load float, float* %126
	%128 = fadd float %124, %127
	%129 = fadd float %128, %57
	%130 = load i32, i32* @k
	%131= getelementptr float,float* %60, i32 %130
	%132 = load float, float* %131
	%133 = fadd float %59, %132
	%134 = load i32, i32* @k
	%135= getelementptr float,float* %61, i32 %134
	%136 = load float, float* %135
	%137 = fadd float %133, %136
	%138 = fadd float %137, %62
	%139= getelementptr [10 x float],[10 x float]* %66, i32 0, i32 0
	%140 = bitcast float* %139 to i32*
	call void @memset(i32* %140, i32 0, i32 40)
	store float %72, float* %139
	%141= getelementptr float,float* %139, i32 1
	store float %84, float* %141
	%142= getelementptr float,float* %139, i32 2
	store float %90, float* %142
	%143= getelementptr float,float* %139, i32 3
	store float %96, float* %143
	%144= getelementptr float,float* %139, i32 4
	store float %105, float* %144
	%145= getelementptr float,float* %139, i32 5
	store float %114, float* %145
	%146= getelementptr float,float* %139, i32 6
	store float %120, float* %146
	%147= getelementptr float,float* %139, i32 7
	store float %129, float* %147
	%148= getelementptr float,float* %139, i32 8
	store float %138, float* %148
	%149 = load i32, i32* @k
	%150= getelementptr i32,i32* %1, i32 %149
	%151 = load i32, i32* %150
	%152 = add i32 %151, %2
	%153 = add i32 %152, %5
	%154 = load i32, i32* @k
	%155= getelementptr i32,i32* %9, i32 %154
	%156 = load i32, i32* %155
	%157 = add i32 %156, %10
	%158 = add i32 %157, %11
	%159 = load i32, i32* @k
	%160= getelementptr i32,i32* %13, i32 %159
	%161 = load i32, i32* %160
	%162 = load i32, i32* @k
	%163= getelementptr i32,i32* %14, i32 %162
	%164 = load i32, i32* %163
	%165 = add i32 %161, %164
	%166 = add i32 %165, %15
	%167 = load i32, i32* @k
	%168= getelementptr i32,i32* %26, i32 %167
	%169 = load i32, i32* %168
	%170 = add i32 %22, %169
	%171 = load i32, i32* @k
	%172= getelementptr i32,i32* %28, i32 %171
	%173 = load i32, i32* %172
	%174 = add i32 %170, %173
	%175 = load i32, i32* @k
	%176= getelementptr i32,i32* %29, i32 %175
	%177 = load i32, i32* %176
	%178 = load i32, i32* @k
	%179= getelementptr i32,i32* %33, i32 %178
	%180 = load i32, i32* %179
	%181 = add i32 %177, %180
	%182 = add i32 %181, %34
	%183 = load i32, i32* @k
	%184= getelementptr i32,i32* %39, i32 %183
	%185 = load i32, i32* %184
	%186 = load i32, i32* @k
	%187= getelementptr i32,i32* %40, i32 %186
	%188 = load i32, i32* %187
	%189 = add i32 %185, %188
	%190 = add i32 %189, %41
	%191 = load i32, i32* @k
	%192= getelementptr i32,i32* %45, i32 %191
	%193 = load i32, i32* %192
	%194 = add i32 %42, %193
	%195 = add i32 %194, %46
	%196 = load i32, i32* @k
	%197= getelementptr i32,i32* %49, i32 %196
	%198 = load i32, i32* %197
	%199 = add i32 %48, %198
	%200 = load i32, i32* @k
	%201= getelementptr i32,i32* %50, i32 %200
	%202 = load i32, i32* %201
	%203 = add i32 %199, %202
	%204 = load i32, i32* @k
	%205= getelementptr i32,i32* %55, i32 %204
	%206 = load i32, i32* %205
	%207 = add i32 %54, %206
	%208 = add i32 %207, %58
	%209 = add i32 %208, %63
	%210= getelementptr [10 x i32],[10 x i32]* %65, i32 0, i32 0
	call void @memset(i32* %210, i32 0, i32 40)
	store i32 %153, i32* %210
	%211= getelementptr i32,i32* %210, i32 1
	store i32 %158, i32* %211
	%212= getelementptr i32,i32* %210, i32 2
	store i32 %166, i32* %212
	%213= getelementptr i32,i32* %210, i32 3
	store i32 %174, i32* %213
	%214= getelementptr i32,i32* %210, i32 4
	store i32 %182, i32* %214
	%215= getelementptr i32,i32* %210, i32 5
	store i32 %190, i32* %215
	%216= getelementptr i32,i32* %210, i32 6
	store i32 %195, i32* %216
	%217= getelementptr i32,i32* %210, i32 7
	store i32 %203, i32* %217
	%218= getelementptr i32,i32* %210, i32 8
	store i32 %209, i32* %218
	%219 = icmp ne i32 %63, 0
	br i1 %219, label  %220, label  %245

220:
	%221= getelementptr [10 x float],[10 x float]* %66, i32 0, i32 0
	call void @putfarray(i32 10, float* %221)
	%222= getelementptr [10 x i32],[10 x i32]* %65, i32 0, i32 0
	call void @putarray(i32 10, i32* %222)
	br label  %223

223:
	%224 = phi i32 [0, %220], [%235, %226]
	%225 = icmp slt i32 %224, 10
	br i1 %225, label  %226, label  %236

226:
	%227= getelementptr [10 x i32],[10 x i32]* %65, i32 0, i32 %224
	%228= getelementptr [10 x i32],[10 x i32]* %65, i32 0, i32 %224
	%229 = load i32, i32* %228
	%230= getelementptr [10 x float],[10 x float]* %66, i32 0, i32 %224
	%231 = load float, float* %230
	%232 = sitofp i32 %229 to float
	%233 = fsub float %232, %231
	%234 = fptosi float %233 to i32
	store i32 %234, i32* %227
	%235 = add i32 %224, 1
	br label  %223

236:
	%237 = load i32, i32* @k
	%238= getelementptr [10 x i32],[10 x i32]* %65, i32 0, i32 %237
	%239 = load i32, i32* %238
	%240= getelementptr [10 x float],[10 x float]* %66, i32 0, i32 8
	%241 = load float, float* %240
	%242 = sitofp i32 %239 to float
	%243 = fmul float %242, %241
	%244 = fptosi float %243 to i32
	ret i32 %244
245:
	%246= getelementptr [10 x i32],[10 x i32]* %65, i32 0, i32 0
	%247= getelementptr [10 x float],[10 x float]* %66, i32 0, i32 0
	%248 = sitofp i32 %63 to float
	%249 = fptosi float %62 to i32
	%250 = call i32 @params_mix(float %0, i32* %246, i32 %2, float* %247, float %4, i32 %5, float %6, float %7, float* %8, i32* %9, i32 %10, i32 %11, float* %12, i32* %13, i32* %14, i32 %15, float* %16, float* %17, float %18, float %19, float %20, float* %21, i32 %22, float %23, float %24, float %25, i32* %26, float* %27, i32* %28, i32* %29, float* %30, float %31, float %32, i32* %33, i32 %34, float* %35, float* %36, float %37, float %38, i32* %39, i32* %40, i32 %41, i32 %42, float %43, float %44, i32* %45, i32 %46, float* %47, i32 %48, i32* %49, i32* %50, float %51, float %52, float* %53, i32 %54, i32* %55, float* %56, float %57, i32 %58, float %59, float* %60, float* %61, float %248, i32 %249)
	ret i32 %250
}
define dso_local i32 @main(){
	%1 = alloca [24 x [3 x i32]]
	%2 = alloca [40 x [3 x float]]
	%3 = call i32 @getint()
	store i32 %3, i32* @k
	br label  %4

4:
	%5 = phi i32 [0, %0], [%11, %7]
	%6 = icmp slt i32 %5, 40
	br i1 %6, label  %7, label  %12

7:
	%8= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 %5
	%9= getelementptr [3 x float],[3 x float]* %8, i32 0, i32 0
	%10 = call i32 @getfarray(float* %9)
	%11 = add i32 %5, 1
	br label  %4

12:
	br label  %13

13:
	%14 = phi i32 [0, %12], [%20, %16]
	%15 = icmp slt i32 %14, 24
	br i1 %15, label  %16, label  %21

16:
	%17= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 %14
	%18= getelementptr [3 x i32],[3 x i32]* %17, i32 0, i32 0
	%19 = call i32 @getarray(i32* %18)
	%20 = add i32 %14, 1
	br label  %13

21:
	%22= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 0
	%23 = load i32, i32* @k
	%24= getelementptr [3 x float],[3 x float]* %22, i32 0, i32 %23
	%25 = load float, float* %24
	%26= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 1
	%27 = load i32, i32* @k
	%28= getelementptr [3 x float],[3 x float]* %26, i32 0, i32 %27
	%29 = load float, float* %28
	%30= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 2
	%31 = load i32, i32* @k
	%32= getelementptr [3 x float],[3 x float]* %30, i32 0, i32 %31
	%33 = load float, float* %32
	%34= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 3
	%35 = load i32, i32* @k
	%36= getelementptr [3 x float],[3 x float]* %34, i32 0, i32 %35
	%37 = load float, float* %36
	%38= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 4
	%39 = load i32, i32* @k
	%40= getelementptr [3 x float],[3 x float]* %38, i32 0, i32 %39
	%41 = load float, float* %40
	%42= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 5
	%43 = load i32, i32* @k
	%44= getelementptr [3 x float],[3 x float]* %42, i32 0, i32 %43
	%45 = load float, float* %44
	%46= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 6
	%47 = load i32, i32* @k
	%48= getelementptr [3 x float],[3 x float]* %46, i32 0, i32 %47
	%49 = load float, float* %48
	%50= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 7
	%51 = load i32, i32* @k
	%52= getelementptr [3 x float],[3 x float]* %50, i32 0, i32 %51
	%53 = load float, float* %52
	%54= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 8
	%55 = load i32, i32* @k
	%56= getelementptr [3 x float],[3 x float]* %54, i32 0, i32 %55
	%57 = load float, float* %56
	%58= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 9
	%59 = load i32, i32* @k
	%60= getelementptr [3 x float],[3 x float]* %58, i32 0, i32 %59
	%61 = load float, float* %60
	%62= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 10
	%63 = load i32, i32* @k
	%64= getelementptr [3 x float],[3 x float]* %62, i32 0, i32 %63
	%65 = load float, float* %64
	%66= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 11
	%67 = load i32, i32* @k
	%68= getelementptr [3 x float],[3 x float]* %66, i32 0, i32 %67
	%69 = load float, float* %68
	%70= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 12
	%71 = load i32, i32* @k
	%72= getelementptr [3 x float],[3 x float]* %70, i32 0, i32 %71
	%73 = load float, float* %72
	%74= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 13
	%75 = load i32, i32* @k
	%76= getelementptr [3 x float],[3 x float]* %74, i32 0, i32 %75
	%77 = load float, float* %76
	%78= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 14
	%79 = load i32, i32* @k
	%80= getelementptr [3 x float],[3 x float]* %78, i32 0, i32 %79
	%81 = load float, float* %80
	%82= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 15
	%83 = load i32, i32* @k
	%84= getelementptr [3 x float],[3 x float]* %82, i32 0, i32 %83
	%85 = load float, float* %84
	%86= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 16
	%87 = load i32, i32* @k
	%88= getelementptr [3 x float],[3 x float]* %86, i32 0, i32 %87
	%89 = load float, float* %88
	%90= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 17
	%91 = load i32, i32* @k
	%92= getelementptr [3 x float],[3 x float]* %90, i32 0, i32 %91
	%93 = load float, float* %92
	%94= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 18
	%95 = load i32, i32* @k
	%96= getelementptr [3 x float],[3 x float]* %94, i32 0, i32 %95
	%97 = load float, float* %96
	%98= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 19
	%99 = load i32, i32* @k
	%100= getelementptr [3 x float],[3 x float]* %98, i32 0, i32 %99
	%101 = load float, float* %100
	%102= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 20
	%103 = load i32, i32* @k
	%104= getelementptr [3 x float],[3 x float]* %102, i32 0, i32 %103
	%105 = load float, float* %104
	%106= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 21
	%107 = load i32, i32* @k
	%108= getelementptr [3 x float],[3 x float]* %106, i32 0, i32 %107
	%109 = load float, float* %108
	%110= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 22
	%111 = load i32, i32* @k
	%112= getelementptr [3 x float],[3 x float]* %110, i32 0, i32 %111
	%113 = load float, float* %112
	%114= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 23
	%115 = load i32, i32* @k
	%116= getelementptr [3 x float],[3 x float]* %114, i32 0, i32 %115
	%117 = load float, float* %116
	%118= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 24
	%119 = load i32, i32* @k
	%120= getelementptr [3 x float],[3 x float]* %118, i32 0, i32 %119
	%121 = load float, float* %120
	%122= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 25
	%123 = load i32, i32* @k
	%124= getelementptr [3 x float],[3 x float]* %122, i32 0, i32 %123
	%125 = load float, float* %124
	%126= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 26
	%127 = load i32, i32* @k
	%128= getelementptr [3 x float],[3 x float]* %126, i32 0, i32 %127
	%129 = load float, float* %128
	%130= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 27
	%131 = load i32, i32* @k
	%132= getelementptr [3 x float],[3 x float]* %130, i32 0, i32 %131
	%133 = load float, float* %132
	%134= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 28
	%135 = load i32, i32* @k
	%136= getelementptr [3 x float],[3 x float]* %134, i32 0, i32 %135
	%137 = load float, float* %136
	%138= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 29
	%139 = load i32, i32* @k
	%140= getelementptr [3 x float],[3 x float]* %138, i32 0, i32 %139
	%141 = load float, float* %140
	%142= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 30
	%143 = load i32, i32* @k
	%144= getelementptr [3 x float],[3 x float]* %142, i32 0, i32 %143
	%145 = load float, float* %144
	%146= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 31
	%147 = load i32, i32* @k
	%148= getelementptr [3 x float],[3 x float]* %146, i32 0, i32 %147
	%149 = load float, float* %148
	%150= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 32
	%151 = load i32, i32* @k
	%152= getelementptr [3 x float],[3 x float]* %150, i32 0, i32 %151
	%153 = load float, float* %152
	%154= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 33
	%155 = load i32, i32* @k
	%156= getelementptr [3 x float],[3 x float]* %154, i32 0, i32 %155
	%157 = load float, float* %156
	%158= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 34
	%159 = load i32, i32* @k
	%160= getelementptr [3 x float],[3 x float]* %158, i32 0, i32 %159
	%161 = load float, float* %160
	%162= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 35
	%163 = load i32, i32* @k
	%164= getelementptr [3 x float],[3 x float]* %162, i32 0, i32 %163
	%165 = load float, float* %164
	%166= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 36
	%167 = load i32, i32* @k
	%168= getelementptr [3 x float],[3 x float]* %166, i32 0, i32 %167
	%169 = load float, float* %168
	%170= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 37
	%171 = load i32, i32* @k
	%172= getelementptr [3 x float],[3 x float]* %170, i32 0, i32 %171
	%173 = load float, float* %172
	%174= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 38
	%175 = load i32, i32* @k
	%176= getelementptr [3 x float],[3 x float]* %174, i32 0, i32 %175
	%177 = load float, float* %176
	%178= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 39
	%179 = load i32, i32* @k
	%180= getelementptr [3 x float],[3 x float]* %178, i32 0, i32 %179
	%181 = load float, float* %180
	%182 = call float @params_f40(float %25, float %29, float %33, float %37, float %41, float %45, float %49, float %53, float %57, float %61, float %65, float %69, float %73, float %77, float %81, float %85, float %89, float %93, float %97, float %101, float %105, float %109, float %113, float %117, float %121, float %125, float %129, float %133, float %137, float %141, float %145, float %149, float %153, float %157, float %161, float %165, float %169, float %173, float %177, float %181)
	%183= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 23
	%184 = load i32, i32* @k
	%185= getelementptr [3 x i32],[3 x i32]* %183, i32 0, i32 %184
	%186 = load i32, i32* %185
	%187= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 2
	%188 = load i32, i32* @k
	%189= getelementptr [3 x i32],[3 x i32]* %187, i32 0, i32 %188
	%190 = load i32, i32* %189
	%191= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 6
	%192 = load i32, i32* @k
	%193= getelementptr [3 x i32],[3 x i32]* %191, i32 0, i32 %192
	%194 = load i32, i32* %193
	%195= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 4
	%196 = load i32, i32* @k
	%197= getelementptr [3 x float],[3 x float]* %195, i32 0, i32 %196
	%198 = load float, float* %197
	%199= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 1
	%200 = load i32, i32* @k
	%201= getelementptr [3 x i32],[3 x i32]* %199, i32 0, i32 %200
	%202 = load i32, i32* %201
	%203= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 4
	%204 = load i32, i32* @k
	%205= getelementptr [3 x i32],[3 x i32]* %203, i32 0, i32 %204
	%206 = load i32, i32* %205
	%207= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 5
	%208 = load i32, i32* @k
	%209= getelementptr [3 x i32],[3 x i32]* %207, i32 0, i32 %208
	%210 = load i32, i32* %209
	%211= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 8
	%212 = load i32, i32* @k
	%213= getelementptr [3 x float],[3 x float]* %211, i32 0, i32 %212
	%214 = load float, float* %213
	%215= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 15
	%216 = load i32, i32* @k
	%217= getelementptr [3 x float],[3 x float]* %215, i32 0, i32 %216
	%218 = load float, float* %217
	%219= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 7
	%220 = load i32, i32* @k
	%221= getelementptr [3 x float],[3 x float]* %219, i32 0, i32 %220
	%222 = load float, float* %221
	%223= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 22
	%224 = load i32, i32* @k
	%225= getelementptr [3 x i32],[3 x i32]* %223, i32 0, i32 %224
	%226 = load i32, i32* %225
	%227= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 3
	%228 = load i32, i32* @k
	%229= getelementptr [3 x float],[3 x float]* %227, i32 0, i32 %228
	%230 = load float, float* %229
	%231= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 28
	%232 = load i32, i32* @k
	%233= getelementptr [3 x float],[3 x float]* %231, i32 0, i32 %232
	%234 = load float, float* %233
	%235= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 0
	%236 = load i32, i32* @k
	%237= getelementptr [3 x i32],[3 x i32]* %235, i32 0, i32 %236
	%238 = load i32, i32* %237
	%239= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 37
	%240 = load i32, i32* @k
	%241= getelementptr [3 x float],[3 x float]* %239, i32 0, i32 %240
	%242 = load float, float* %241
	%243= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 19
	%244 = load i32, i32* @k
	%245= getelementptr [3 x i32],[3 x i32]* %243, i32 0, i32 %244
	%246 = load i32, i32* %245
	%247= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 30
	%248 = load i32, i32* @k
	%249= getelementptr [3 x float],[3 x float]* %247, i32 0, i32 %248
	%250 = load float, float* %249
	%251= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 12
	%252 = load i32, i32* @k
	%253= getelementptr [3 x float],[3 x float]* %251, i32 0, i32 %252
	%254 = load float, float* %253
	%255= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 1
	%256 = load i32, i32* @k
	%257= getelementptr [3 x float],[3 x float]* %255, i32 0, i32 %256
	%258 = load float, float* %257
	%259= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 11
	%260 = load i32, i32* @k
	%261= getelementptr [3 x float],[3 x float]* %259, i32 0, i32 %260
	%262 = load float, float* %261
	%263= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 38
	%264 = load i32, i32* @k
	%265= getelementptr [3 x float],[3 x float]* %263, i32 0, i32 %264
	%266 = load float, float* %265
	%267= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 6
	%268 = load i32, i32* @k
	%269= getelementptr [3 x float],[3 x float]* %267, i32 0, i32 %268
	%270 = load float, float* %269
	%271= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 7
	%272 = load i32, i32* @k
	%273= getelementptr [3 x i32],[3 x i32]* %271, i32 0, i32 %272
	%274 = load i32, i32* %273
	%275= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 32
	%276 = load i32, i32* @k
	%277= getelementptr [3 x float],[3 x float]* %275, i32 0, i32 %276
	%278 = load float, float* %277
	%279= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 10
	%280 = load i32, i32* @k
	%281= getelementptr [3 x i32],[3 x i32]* %279, i32 0, i32 %280
	%282 = load i32, i32* %281
	%283= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 13
	%284 = load i32, i32* @k
	%285= getelementptr [3 x i32],[3 x i32]* %283, i32 0, i32 %284
	%286 = load i32, i32* %285
	%287= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 20
	%288 = load i32, i32* @k
	%289= getelementptr [3 x float],[3 x float]* %287, i32 0, i32 %288
	%290 = load float, float* %289
	%291= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 33
	%292 = load i32, i32* @k
	%293= getelementptr [3 x float],[3 x float]* %291, i32 0, i32 %292
	%294 = load float, float* %293
	%295= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 23
	%296 = load i32, i32* @k
	%297= getelementptr [3 x float],[3 x float]* %295, i32 0, i32 %296
	%298 = load float, float* %297
	%299= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 9
	%300 = load i32, i32* @k
	%301= getelementptr [3 x float],[3 x float]* %299, i32 0, i32 %300
	%302 = load float, float* %301
	%303= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 25
	%304 = load i32, i32* @k
	%305= getelementptr [3 x float],[3 x float]* %303, i32 0, i32 %304
	%306 = load float, float* %305
	%307= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 8
	%308 = load i32, i32* @k
	%309= getelementptr [3 x i32],[3 x i32]* %307, i32 0, i32 %308
	%310 = load i32, i32* %309
	%311= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 39
	%312 = load i32, i32* @k
	%313= getelementptr [3 x float],[3 x float]* %311, i32 0, i32 %312
	%314 = load float, float* %313
	%315= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 17
	%316 = load i32, i32* @k
	%317= getelementptr [3 x i32],[3 x i32]* %315, i32 0, i32 %316
	%318 = load i32, i32* %317
	%319= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 21
	%320 = load i32, i32* @k
	%321= getelementptr [3 x float],[3 x float]* %319, i32 0, i32 %320
	%322 = load float, float* %321
	%323= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 16
	%324 = load i32, i32* @k
	%325= getelementptr [3 x float],[3 x float]* %323, i32 0, i32 %324
	%326 = load float, float* %325
	%327= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 5
	%328 = load i32, i32* @k
	%329= getelementptr [3 x float],[3 x float]* %327, i32 0, i32 %328
	%330 = load float, float* %329
	%331= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 34
	%332 = load i32, i32* @k
	%333= getelementptr [3 x float],[3 x float]* %331, i32 0, i32 %332
	%334 = load float, float* %333
	%335= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 18
	%336 = load i32, i32* @k
	%337= getelementptr [3 x i32],[3 x i32]* %335, i32 0, i32 %336
	%338 = load i32, i32* %337
	%339= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 9
	%340 = load i32, i32* @k
	%341= getelementptr [3 x i32],[3 x i32]* %339, i32 0, i32 %340
	%342 = load i32, i32* %341
	%343= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 14
	%344 = load i32, i32* @k
	%345= getelementptr [3 x float],[3 x float]* %343, i32 0, i32 %344
	%346 = load float, float* %345
	%347= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 10
	%348 = load i32, i32* @k
	%349= getelementptr [3 x float],[3 x float]* %347, i32 0, i32 %348
	%350 = load float, float* %349
	%351= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 0
	%352 = load i32, i32* @k
	%353= getelementptr [3 x float],[3 x float]* %351, i32 0, i32 %352
	%354 = load float, float* %353
	%355= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 12
	%356 = load i32, i32* @k
	%357= getelementptr [3 x i32],[3 x i32]* %355, i32 0, i32 %356
	%358 = load i32, i32* %357
	%359= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 31
	%360 = load i32, i32* @k
	%361= getelementptr [3 x float],[3 x float]* %359, i32 0, i32 %360
	%362 = load float, float* %361
	%363= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 11
	%364 = load i32, i32* @k
	%365= getelementptr [3 x i32],[3 x i32]* %363, i32 0, i32 %364
	%366 = load i32, i32* %365
	%367= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 16
	%368 = load i32, i32* @k
	%369= getelementptr [3 x i32],[3 x i32]* %367, i32 0, i32 %368
	%370 = load i32, i32* %369
	%371= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 27
	%372 = load i32, i32* @k
	%373= getelementptr [3 x float],[3 x float]* %371, i32 0, i32 %372
	%374 = load float, float* %373
	%375= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 24
	%376 = load i32, i32* @k
	%377= getelementptr [3 x float],[3 x float]* %375, i32 0, i32 %376
	%378 = load float, float* %377
	%379= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 13
	%380 = load i32, i32* @k
	%381= getelementptr [3 x float],[3 x float]* %379, i32 0, i32 %380
	%382 = load float, float* %381
	%383= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 29
	%384 = load i32, i32* @k
	%385= getelementptr [3 x float],[3 x float]* %383, i32 0, i32 %384
	%386 = load float, float* %385
	%387= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 3
	%388 = load i32, i32* @k
	%389= getelementptr [3 x i32],[3 x i32]* %387, i32 0, i32 %388
	%390 = load i32, i32* %389
	%391= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 21
	%392 = load i32, i32* @k
	%393= getelementptr [3 x i32],[3 x i32]* %391, i32 0, i32 %392
	%394 = load i32, i32* %393
	%395= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 20
	%396 = load i32, i32* @k
	%397= getelementptr [3 x i32],[3 x i32]* %395, i32 0, i32 %396
	%398 = load i32, i32* %397
	%399= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 18
	%400 = load i32, i32* @k
	%401= getelementptr [3 x float],[3 x float]* %399, i32 0, i32 %400
	%402 = load float, float* %401
	%403= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 19
	%404 = load i32, i32* @k
	%405= getelementptr [3 x float],[3 x float]* %403, i32 0, i32 %404
	%406 = load float, float* %405
	%407= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 22
	%408 = load i32, i32* @k
	%409= getelementptr [3 x float],[3 x float]* %407, i32 0, i32 %408
	%410 = load float, float* %409
	%411= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 26
	%412 = load i32, i32* @k
	%413= getelementptr [3 x float],[3 x float]* %411, i32 0, i32 %412
	%414 = load float, float* %413
	%415= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 36
	%416 = load i32, i32* @k
	%417= getelementptr [3 x float],[3 x float]* %415, i32 0, i32 %416
	%418 = load float, float* %417
	%419= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 17
	%420 = load i32, i32* @k
	%421= getelementptr [3 x float],[3 x float]* %419, i32 0, i32 %420
	%422 = load float, float* %421
	%423= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 15
	%424 = load i32, i32* @k
	%425= getelementptr [3 x i32],[3 x i32]* %423, i32 0, i32 %424
	%426 = load i32, i32* %425
	%427= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 2
	%428 = load i32, i32* @k
	%429= getelementptr [3 x float],[3 x float]* %427, i32 0, i32 %428
	%430 = load float, float* %429
	%431= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 14
	%432 = load i32, i32* @k
	%433= getelementptr [3 x i32],[3 x i32]* %431, i32 0, i32 %432
	%434 = load i32, i32* %433
	%435= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 35
	%436 = load i32, i32* @k
	%437= getelementptr [3 x float],[3 x float]* %435, i32 0, i32 %436
	%438 = load float, float* %437
	%439 = call float @params_f40_i24(i32 %186, i32 %190, i32 %194, float %198, i32 %202, i32 %206, i32 %210, float %214, float %218, float %222, i32 %226, float %230, float %234, i32 %238, float %242, i32 %246, float %250, float %254, float %258, float %262, float %266, float %270, i32 %274, float %278, i32 %282, i32 %286, float %290, float %294, float %298, float %302, float %306, i32 %310, float %314, i32 %318, float %322, float %326, float %330, float %334, i32 %338, i32 %342, float %346, float %350, float %354, i32 %358, float %362, i32 %366, i32 %370, float %374, float %378, float %382, float %386, i32 %390, i32 %394, i32 %398, float %402, float %406, float %410, float %414, float %418, float %422, i32 %426, float %430, i32 %434, float %438)
	%440= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 0
	%441= getelementptr [3 x float],[3 x float]* %440, i32 0, i32 0
	%442= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 1
	%443= getelementptr [3 x float],[3 x float]* %442, i32 0, i32 0
	%444= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 2
	%445= getelementptr [3 x float],[3 x float]* %444, i32 0, i32 0
	%446= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 3
	%447= getelementptr [3 x float],[3 x float]* %446, i32 0, i32 0
	%448= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 4
	%449= getelementptr [3 x float],[3 x float]* %448, i32 0, i32 0
	%450= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 5
	%451= getelementptr [3 x float],[3 x float]* %450, i32 0, i32 0
	%452= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 6
	%453= getelementptr [3 x float],[3 x float]* %452, i32 0, i32 0
	%454= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 7
	%455= getelementptr [3 x float],[3 x float]* %454, i32 0, i32 0
	%456= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 8
	%457= getelementptr [3 x float],[3 x float]* %456, i32 0, i32 0
	%458= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 9
	%459= getelementptr [3 x float],[3 x float]* %458, i32 0, i32 0
	%460= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 10
	%461= getelementptr [3 x float],[3 x float]* %460, i32 0, i32 0
	%462= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 11
	%463= getelementptr [3 x float],[3 x float]* %462, i32 0, i32 0
	%464= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 12
	%465= getelementptr [3 x float],[3 x float]* %464, i32 0, i32 0
	%466= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 13
	%467= getelementptr [3 x float],[3 x float]* %466, i32 0, i32 0
	%468= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 14
	%469= getelementptr [3 x float],[3 x float]* %468, i32 0, i32 0
	%470= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 15
	%471= getelementptr [3 x float],[3 x float]* %470, i32 0, i32 0
	%472= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 16
	%473= getelementptr [3 x float],[3 x float]* %472, i32 0, i32 0
	%474= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 17
	%475= getelementptr [3 x float],[3 x float]* %474, i32 0, i32 0
	%476= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 18
	%477= getelementptr [3 x float],[3 x float]* %476, i32 0, i32 0
	%478= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 19
	%479= getelementptr [3 x float],[3 x float]* %478, i32 0, i32 0
	%480= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 20
	%481= getelementptr [3 x float],[3 x float]* %480, i32 0, i32 0
	%482= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 21
	%483= getelementptr [3 x float],[3 x float]* %482, i32 0, i32 0
	%484= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 22
	%485= getelementptr [3 x float],[3 x float]* %484, i32 0, i32 0
	%486= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 23
	%487= getelementptr [3 x float],[3 x float]* %486, i32 0, i32 0
	%488= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 24
	%489= getelementptr [3 x float],[3 x float]* %488, i32 0, i32 0
	%490= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 25
	%491= getelementptr [3 x float],[3 x float]* %490, i32 0, i32 0
	%492= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 26
	%493= getelementptr [3 x float],[3 x float]* %492, i32 0, i32 0
	%494= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 27
	%495= getelementptr [3 x float],[3 x float]* %494, i32 0, i32 0
	%496= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 28
	%497= getelementptr [3 x float],[3 x float]* %496, i32 0, i32 0
	%498= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 29
	%499= getelementptr [3 x float],[3 x float]* %498, i32 0, i32 0
	%500= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 30
	%501= getelementptr [3 x float],[3 x float]* %500, i32 0, i32 0
	%502= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 31
	%503= getelementptr [3 x float],[3 x float]* %502, i32 0, i32 0
	%504= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 32
	%505= getelementptr [3 x float],[3 x float]* %504, i32 0, i32 0
	%506= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 33
	%507= getelementptr [3 x float],[3 x float]* %506, i32 0, i32 0
	%508= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 34
	%509= getelementptr [3 x float],[3 x float]* %508, i32 0, i32 0
	%510= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 35
	%511= getelementptr [3 x float],[3 x float]* %510, i32 0, i32 0
	%512= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 36
	%513= getelementptr [3 x float],[3 x float]* %512, i32 0, i32 0
	%514= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 37
	%515= getelementptr [3 x float],[3 x float]* %514, i32 0, i32 0
	%516= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 38
	%517= getelementptr [3 x float],[3 x float]* %516, i32 0, i32 0
	%518= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 39
	%519= getelementptr [3 x float],[3 x float]* %518, i32 0, i32 0
	%520 = call float @params_fa40(float* %441, float* %443, float* %445, float* %447, float* %449, float* %451, float* %453, float* %455, float* %457, float* %459, float* %461, float* %463, float* %465, float* %467, float* %469, float* %471, float* %473, float* %475, float* %477, float* %479, float* %481, float* %483, float* %485, float* %487, float* %489, float* %491, float* %493, float* %495, float* %497, float* %499, float* %501, float* %503, float* %505, float* %507, float* %509, float* %511, float* %513, float* %515, float* %517, float* %519)
	%521= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 0
	%522 = load i32, i32* @k
	%523= getelementptr [3 x float],[3 x float]* %521, i32 0, i32 %522
	%524 = load float, float* %523
	%525= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 0
	%526= getelementptr [3 x i32],[3 x i32]* %525, i32 0, i32 0
	%527= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 1
	%528 = load i32, i32* @k
	%529= getelementptr [3 x i32],[3 x i32]* %527, i32 0, i32 %528
	%530 = load i32, i32* %529
	%531= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 1
	%532= getelementptr [3 x float],[3 x float]* %531, i32 0, i32 0
	%533= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 2
	%534 = load i32, i32* @k
	%535= getelementptr [3 x float],[3 x float]* %533, i32 0, i32 %534
	%536 = load float, float* %535
	%537= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 2
	%538 = load i32, i32* @k
	%539= getelementptr [3 x i32],[3 x i32]* %537, i32 0, i32 %538
	%540 = load i32, i32* %539
	%541= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 3
	%542 = load i32, i32* @k
	%543= getelementptr [3 x float],[3 x float]* %541, i32 0, i32 %542
	%544 = load float, float* %543
	%545= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 4
	%546 = load i32, i32* @k
	%547= getelementptr [3 x float],[3 x float]* %545, i32 0, i32 %546
	%548 = load float, float* %547
	%549= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 5
	%550= getelementptr [3 x float],[3 x float]* %549, i32 0, i32 0
	%551= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 3
	%552= getelementptr [3 x i32],[3 x i32]* %551, i32 0, i32 0
	%553= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 4
	%554 = load i32, i32* @k
	%555= getelementptr [3 x i32],[3 x i32]* %553, i32 0, i32 %554
	%556 = load i32, i32* %555
	%557= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 5
	%558 = load i32, i32* @k
	%559= getelementptr [3 x i32],[3 x i32]* %557, i32 0, i32 %558
	%560 = load i32, i32* %559
	%561= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 6
	%562= getelementptr [3 x float],[3 x float]* %561, i32 0, i32 0
	%563= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 6
	%564= getelementptr [3 x i32],[3 x i32]* %563, i32 0, i32 0
	%565= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 7
	%566= getelementptr [3 x i32],[3 x i32]* %565, i32 0, i32 0
	%567= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 8
	%568 = load i32, i32* @k
	%569= getelementptr [3 x i32],[3 x i32]* %567, i32 0, i32 %568
	%570 = load i32, i32* %569
	%571= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 7
	%572= getelementptr [3 x float],[3 x float]* %571, i32 0, i32 0
	%573= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 8
	%574= getelementptr [3 x float],[3 x float]* %573, i32 0, i32 0
	%575= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 9
	%576 = load i32, i32* @k
	%577= getelementptr [3 x float],[3 x float]* %575, i32 0, i32 %576
	%578 = load float, float* %577
	%579= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 10
	%580 = load i32, i32* @k
	%581= getelementptr [3 x float],[3 x float]* %579, i32 0, i32 %580
	%582 = load float, float* %581
	%583= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 11
	%584 = load i32, i32* @k
	%585= getelementptr [3 x float],[3 x float]* %583, i32 0, i32 %584
	%586 = load float, float* %585
	%587= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 12
	%588= getelementptr [3 x float],[3 x float]* %587, i32 0, i32 0
	%589= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 9
	%590 = load i32, i32* @k
	%591= getelementptr [3 x i32],[3 x i32]* %589, i32 0, i32 %590
	%592 = load i32, i32* %591
	%593= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 13
	%594 = load i32, i32* @k
	%595= getelementptr [3 x float],[3 x float]* %593, i32 0, i32 %594
	%596 = load float, float* %595
	%597= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 14
	%598 = load i32, i32* @k
	%599= getelementptr [3 x float],[3 x float]* %597, i32 0, i32 %598
	%600 = load float, float* %599
	%601= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 15
	%602 = load i32, i32* @k
	%603= getelementptr [3 x float],[3 x float]* %601, i32 0, i32 %602
	%604 = load float, float* %603
	%605= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 10
	%606= getelementptr [3 x i32],[3 x i32]* %605, i32 0, i32 0
	%607= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 16
	%608= getelementptr [3 x float],[3 x float]* %607, i32 0, i32 0
	%609= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 11
	%610= getelementptr [3 x i32],[3 x i32]* %609, i32 0, i32 0
	%611= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 12
	%612= getelementptr [3 x i32],[3 x i32]* %611, i32 0, i32 0
	%613= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 17
	%614= getelementptr [3 x float],[3 x float]* %613, i32 0, i32 0
	%615= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 18
	%616 = load i32, i32* @k
	%617= getelementptr [3 x float],[3 x float]* %615, i32 0, i32 %616
	%618 = load float, float* %617
	%619= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 19
	%620 = load i32, i32* @k
	%621= getelementptr [3 x float],[3 x float]* %619, i32 0, i32 %620
	%622 = load float, float* %621
	%623= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 13
	%624= getelementptr [3 x i32],[3 x i32]* %623, i32 0, i32 0
	%625= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 14
	%626 = load i32, i32* @k
	%627= getelementptr [3 x i32],[3 x i32]* %625, i32 0, i32 %626
	%628 = load i32, i32* %627
	%629= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 20
	%630= getelementptr [3 x float],[3 x float]* %629, i32 0, i32 0
	%631= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 21
	%632= getelementptr [3 x float],[3 x float]* %631, i32 0, i32 0
	%633= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 22
	%634 = load i32, i32* @k
	%635= getelementptr [3 x float],[3 x float]* %633, i32 0, i32 %634
	%636 = load float, float* %635
	%637= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 23
	%638 = load i32, i32* @k
	%639= getelementptr [3 x float],[3 x float]* %637, i32 0, i32 %638
	%640 = load float, float* %639
	%641= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 15
	%642= getelementptr [3 x i32],[3 x i32]* %641, i32 0, i32 0
	%643= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 16
	%644= getelementptr [3 x i32],[3 x i32]* %643, i32 0, i32 0
	%645= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 17
	%646 = load i32, i32* @k
	%647= getelementptr [3 x i32],[3 x i32]* %645, i32 0, i32 %646
	%648 = load i32, i32* %647
	%649= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 18
	%650 = load i32, i32* @k
	%651= getelementptr [3 x i32],[3 x i32]* %649, i32 0, i32 %650
	%652 = load i32, i32* %651
	%653= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 24
	%654 = load i32, i32* @k
	%655= getelementptr [3 x float],[3 x float]* %653, i32 0, i32 %654
	%656 = load float, float* %655
	%657= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 25
	%658 = load i32, i32* @k
	%659= getelementptr [3 x float],[3 x float]* %657, i32 0, i32 %658
	%660 = load float, float* %659
	%661= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 19
	%662= getelementptr [3 x i32],[3 x i32]* %661, i32 0, i32 0
	%663= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 20
	%664 = load i32, i32* @k
	%665= getelementptr [3 x i32],[3 x i32]* %663, i32 0, i32 %664
	%666 = load i32, i32* %665
	%667= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 26
	%668= getelementptr [3 x float],[3 x float]* %667, i32 0, i32 0
	%669= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 21
	%670 = load i32, i32* @k
	%671= getelementptr [3 x i32],[3 x i32]* %669, i32 0, i32 %670
	%672 = load i32, i32* %671
	%673= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 22
	%674= getelementptr [3 x i32],[3 x i32]* %673, i32 0, i32 0
	%675= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 23
	%676= getelementptr [3 x i32],[3 x i32]* %675, i32 0, i32 0
	%677= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 27
	%678 = load i32, i32* @k
	%679= getelementptr [3 x float],[3 x float]* %677, i32 0, i32 %678
	%680 = load float, float* %679
	%681= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 28
	%682 = load i32, i32* @k
	%683= getelementptr [3 x float],[3 x float]* %681, i32 0, i32 %682
	%684 = load float, float* %683
	%685= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 29
	%686= getelementptr [3 x float],[3 x float]* %685, i32 0, i32 0
	%687= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 0
	%688 = load i32, i32* @k
	%689= getelementptr [3 x i32],[3 x i32]* %687, i32 0, i32 %688
	%690 = load i32, i32* %689
	%691= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 1
	%692= getelementptr [3 x i32],[3 x i32]* %691, i32 0, i32 0
	%693= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 30
	%694= getelementptr [3 x float],[3 x float]* %693, i32 0, i32 0
	%695= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 31
	%696 = load i32, i32* @k
	%697= getelementptr [3 x float],[3 x float]* %695, i32 0, i32 %696
	%698 = load float, float* %697
	%699= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 2
	%700 = load i32, i32* @k
	%701= getelementptr [3 x i32],[3 x i32]* %699, i32 0, i32 %700
	%702 = load i32, i32* %701
	%703= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 32
	%704 = load i32, i32* @k
	%705= getelementptr [3 x float],[3 x float]* %703, i32 0, i32 %704
	%706 = load float, float* %705
	%707= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 33
	%708= getelementptr [3 x float],[3 x float]* %707, i32 0, i32 0
	%709= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 34
	%710= getelementptr [3 x float],[3 x float]* %709, i32 0, i32 0
	%711= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %2, i32 0, i32 35
	%712 = load i32, i32* @k
	%713= getelementptr [3 x float],[3 x float]* %711, i32 0, i32 %712
	%714 = load float, float* %713
	%715= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %1, i32 0, i32 3
	%716 = load i32, i32* @k
	%717= getelementptr [3 x i32],[3 x i32]* %715, i32 0, i32 %716
	%718 = load i32, i32* %717
	%719 = call i32 @params_mix(float %524, i32* %526, i32 %530, float* %532, float %536, i32 %540, float %544, float %548, float* %550, i32* %552, i32 %556, i32 %560, float* %562, i32* %564, i32* %566, i32 %570, float* %572, float* %574, float %578, float %582, float %586, float* %588, i32 %592, float %596, float %600, float %604, i32* %606, float* %608, i32* %610, i32* %612, float* %614, float %618, float %622, i32* %624, i32 %628, float* %630, float* %632, float %636, float %640, i32* %642, i32* %644, i32 %648, i32 %652, float %656, float %660, i32* %662, i32 %666, float* %668, i32 %672, i32* %674, i32* %676, float %680, float %684, float* %686, i32 %690, i32* %692, float* %694, float %698, i32 %702, float %706, float* %708, float* %710, float %714, i32 %718)
	call void @putfloat(float %182)
	call void @putch(i32 10)
	call void @putfloat(float %439)
	call void @putch(i32 10)
	call void @putfloat(float %520)
	call void @putch(i32 10)
	call void @putint(i32 %719)
	call void @putch(i32 10)
	ret i32 0
}
