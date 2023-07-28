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
	%42 = alloca float
	%43 = alloca float
	%44 = alloca float
	%45 = alloca float
	%46 = alloca float
	%47 = alloca float
	%48 = alloca float
	%49 = alloca float
	%50 = alloca float
	%51 = alloca float
	%52 = alloca float
	%53 = alloca float
	%54 = alloca float
	%55 = alloca float
	%56 = alloca float
	%57 = alloca float
	%58 = alloca float
	%59 = alloca float
	%60 = alloca float
	%61 = alloca float
	%62 = alloca float
	%63 = alloca float
	%64 = alloca float
	%65 = alloca float
	%66 = alloca float
	%67 = alloca float
	%68 = alloca float
	%69 = alloca float
	%70 = alloca float
	%71 = alloca float
	%72 = alloca float
	%73 = alloca float
	%74 = alloca float
	%75 = alloca float
	%76 = alloca float
	%77 = alloca float
	%78 = alloca float
	%79 = alloca float
	%80 = alloca float
	%81 = alloca float
	store float %0, float* %81
	store float %1, float* %80
	store float %2, float* %79
	store float %3, float* %78
	store float %4, float* %77
	store float %5, float* %76
	store float %6, float* %75
	store float %7, float* %74
	store float %8, float* %73
	store float %9, float* %72
	store float %10, float* %71
	store float %11, float* %70
	store float %12, float* %69
	store float %13, float* %68
	store float %14, float* %67
	store float %15, float* %66
	store float %16, float* %65
	store float %17, float* %64
	store float %18, float* %63
	store float %19, float* %62
	store float %20, float* %61
	store float %21, float* %60
	store float %22, float* %59
	store float %23, float* %58
	store float %24, float* %57
	store float %25, float* %56
	store float %26, float* %55
	store float %27, float* %54
	store float %28, float* %53
	store float %29, float* %52
	store float %30, float* %51
	store float %31, float* %50
	store float %32, float* %49
	store float %33, float* %48
	store float %34, float* %47
	store float %35, float* %46
	store float %36, float* %45
	store float %37, float* %44
	store float %38, float* %43
	store float %39, float* %42
	%82 = load float, float* %42
	%83 = sitofp i32 0 to float
	%84 = fcmp one float %82, %83
	br i1 %84, label  %85, label  %171

85:
	%86 = load float, float* %81
	%87 = load float, float* %80
	%88 = fadd float %86, %87
	%89 = load float, float* %79
	%90 = fadd float %88, %89
	%91 = load float, float* %78
	%92 = fadd float %90, %91
	%93 = load float, float* %77
	%94 = load float, float* %76
	%95 = fadd float %93, %94
	%96 = load float, float* %75
	%97 = fadd float %95, %96
	%98 = load float, float* %74
	%99 = fadd float %97, %98
	%100 = load float, float* %73
	%101 = load float, float* %72
	%102 = fadd float %100, %101
	%103 = load float, float* %71
	%104 = fadd float %102, %103
	%105 = load float, float* %70
	%106 = fadd float %104, %105
	%107 = load float, float* %69
	%108 = load float, float* %68
	%109 = fadd float %107, %108
	%110 = load float, float* %67
	%111 = fadd float %109, %110
	%112 = load float, float* %66
	%113 = fadd float %111, %112
	%114 = load float, float* %65
	%115 = load float, float* %64
	%116 = fadd float %114, %115
	%117 = load float, float* %63
	%118 = fadd float %116, %117
	%119 = load float, float* %62
	%120 = fadd float %118, %119
	%121 = load float, float* %61
	%122 = load float, float* %60
	%123 = fadd float %121, %122
	%124 = load float, float* %59
	%125 = fadd float %123, %124
	%126 = load float, float* %58
	%127 = fadd float %125, %126
	%128 = load float, float* %57
	%129 = load float, float* %56
	%130 = fadd float %128, %129
	%131 = load float, float* %55
	%132 = fadd float %130, %131
	%133 = load float, float* %54
	%134 = fadd float %132, %133
	%135 = load float, float* %53
	%136 = load float, float* %52
	%137 = fadd float %135, %136
	%138 = load float, float* %51
	%139 = fadd float %137, %138
	%140 = load float, float* %50
	%141 = fadd float %139, %140
	%142 = load float, float* %49
	%143 = load float, float* %48
	%144 = fadd float %142, %143
	%145 = load float, float* %47
	%146 = fadd float %144, %145
	%147 = load float, float* %46
	%148 = fadd float %146, %147
	%149 = load float, float* %45
	%150 = load float, float* %44
	%151 = fadd float %149, %150
	%152 = load float, float* %43
	%153 = fadd float %151, %152
	%154 = load float, float* %42
	%155 = fadd float %153, %154
	%156= getelementptr [10 x float],[10 x float]* %41, i32 0, i32 0
	%157 = bitcast float* %156 to i32*
	call void @memset(i32* %157, i32 0, i32 40)
	store float %92, float* %156
	%158= getelementptr float,float* %156, i32 1
	store float %99, float* %158
	%159= getelementptr float,float* %156, i32 2
	store float %106, float* %159
	%160= getelementptr float,float* %156, i32 3
	store float %113, float* %160
	%161= getelementptr float,float* %156, i32 4
	store float %120, float* %161
	%162= getelementptr float,float* %156, i32 5
	store float %127, float* %162
	%163= getelementptr float,float* %156, i32 6
	store float %134, float* %163
	%164= getelementptr float,float* %156, i32 7
	store float %141, float* %164
	%165= getelementptr float,float* %156, i32 8
	store float %148, float* %165
	%166= getelementptr float,float* %156, i32 9
	store float %155, float* %166
	%167= getelementptr [10 x float],[10 x float]* %41, i32 0, i32 0
	call void @putfarray(i32 10, float* %167)
	%168 = load i32, i32* @k
	%169= getelementptr [10 x float],[10 x float]* %41, i32 0, i32 %168
	%170 = load float, float* %169
	ret float %170
171:
	%172 = load float, float* %80
	%173 = load float, float* %79
	%174 = load float, float* %78
	%175 = load float, float* %77
	%176 = load float, float* %76
	%177 = load float, float* %75
	%178 = load float, float* %74
	%179 = load float, float* %73
	%180 = load float, float* %72
	%181 = load float, float* %71
	%182 = load float, float* %70
	%183 = load float, float* %69
	%184 = load float, float* %68
	%185 = load float, float* %67
	%186 = load float, float* %66
	%187 = load float, float* %65
	%188 = load float, float* %64
	%189 = load float, float* %63
	%190 = load float, float* %62
	%191 = load float, float* %61
	%192 = load float, float* %60
	%193 = load float, float* %59
	%194 = load float, float* %58
	%195 = load float, float* %57
	%196 = load float, float* %56
	%197 = load float, float* %55
	%198 = load float, float* %54
	%199 = load float, float* %53
	%200 = load float, float* %52
	%201 = load float, float* %51
	%202 = load float, float* %50
	%203 = load float, float* %49
	%204 = load float, float* %48
	%205 = load float, float* %47
	%206 = load float, float* %46
	%207 = load float, float* %45
	%208 = load float, float* %44
	%209 = load float, float* %43
	%210 = load float, float* %42
	%211 = load float, float* %81
	%212 = load float, float* %80
	%213 = fadd float %211, %212
	%214 = load float, float* %79
	%215 = fadd float %213, %214
	%216 = call float @params_f40(float %172, float %173, float %174, float %175, float %176, float %177, float %178, float %179, float %180, float %181, float %182, float %183, float %184, float %185, float %186, float %187, float %188, float %189, float %190, float %191, float %192, float %193, float %194, float %195, float %196, float %197, float %198, float %199, float %200, float %201, float %202, float %203, float %204, float %205, float %206, float %207, float %208, float %209, float %210, float %215)
	ret float %216
}
define dso_local float @params_f40_i24(i32 %0, i32 %1, i32 %2, float %3, i32 %4, i32 %5, i32 %6, float %7, float %8, float %9, i32 %10, float %11, float %12, i32 %13, float %14, i32 %15, float %16, float %17, float %18, float %19, float %20, float %21, i32 %22, float %23, i32 %24, i32 %25, float %26, float %27, float %28, float %29, float %30, i32 %31, float %32, i32 %33, float %34, float %35, float %36, float %37, i32 %38, i32 %39, float %40, float %41, float %42, i32 %43, float %44, i32 %45, i32 %46, float %47, float %48, float %49, float %50, i32 %51, i32 %52, i32 %53, float %54, float %55, float %56, float %57, float %58, float %59, i32 %60, float %61, i32 %62, float %63){
	%65 = alloca i32
	%66 = alloca [8 x i32]
	%67 = alloca [10 x float]
	%68 = alloca float
	%69 = alloca i32
	%70 = alloca float
	%71 = alloca i32
	%72 = alloca float
	%73 = alloca float
	%74 = alloca float
	%75 = alloca float
	%76 = alloca float
	%77 = alloca float
	%78 = alloca i32
	%79 = alloca i32
	%80 = alloca i32
	%81 = alloca float
	%82 = alloca float
	%83 = alloca float
	%84 = alloca float
	%85 = alloca i32
	%86 = alloca i32
	%87 = alloca float
	%88 = alloca i32
	%89 = alloca float
	%90 = alloca float
	%91 = alloca float
	%92 = alloca i32
	%93 = alloca i32
	%94 = alloca float
	%95 = alloca float
	%96 = alloca float
	%97 = alloca float
	%98 = alloca i32
	%99 = alloca float
	%100 = alloca i32
	%101 = alloca float
	%102 = alloca float
	%103 = alloca float
	%104 = alloca float
	%105 = alloca float
	%106 = alloca i32
	%107 = alloca i32
	%108 = alloca float
	%109 = alloca i32
	%110 = alloca float
	%111 = alloca float
	%112 = alloca float
	%113 = alloca float
	%114 = alloca float
	%115 = alloca float
	%116 = alloca i32
	%117 = alloca float
	%118 = alloca i32
	%119 = alloca float
	%120 = alloca float
	%121 = alloca i32
	%122 = alloca float
	%123 = alloca float
	%124 = alloca float
	%125 = alloca i32
	%126 = alloca i32
	%127 = alloca i32
	%128 = alloca float
	%129 = alloca i32
	%130 = alloca i32
	%131 = alloca i32
	store i32 %0, i32* %131
	store i32 %1, i32* %130
	store i32 %2, i32* %129
	store float %3, float* %128
	store i32 %4, i32* %127
	store i32 %5, i32* %126
	store i32 %6, i32* %125
	store float %7, float* %124
	store float %8, float* %123
	store float %9, float* %122
	store i32 %10, i32* %121
	store float %11, float* %120
	store float %12, float* %119
	store i32 %13, i32* %118
	store float %14, float* %117
	store i32 %15, i32* %116
	store float %16, float* %115
	store float %17, float* %114
	store float %18, float* %113
	store float %19, float* %112
	store float %20, float* %111
	store float %21, float* %110
	store i32 %22, i32* %109
	store float %23, float* %108
	store i32 %24, i32* %107
	store i32 %25, i32* %106
	store float %26, float* %105
	store float %27, float* %104
	store float %28, float* %103
	store float %29, float* %102
	store float %30, float* %101
	store i32 %31, i32* %100
	store float %32, float* %99
	store i32 %33, i32* %98
	store float %34, float* %97
	store float %35, float* %96
	store float %36, float* %95
	store float %37, float* %94
	store i32 %38, i32* %93
	store i32 %39, i32* %92
	store float %40, float* %91
	store float %41, float* %90
	store float %42, float* %89
	store i32 %43, i32* %88
	store float %44, float* %87
	store i32 %45, i32* %86
	store i32 %46, i32* %85
	store float %47, float* %84
	store float %48, float* %83
	store float %49, float* %82
	store float %50, float* %81
	store i32 %51, i32* %80
	store i32 %52, i32* %79
	store i32 %53, i32* %78
	store float %54, float* %77
	store float %55, float* %76
	store float %56, float* %75
	store float %57, float* %74
	store float %58, float* %73
	store float %59, float* %72
	store i32 %60, i32* %71
	store float %61, float* %70
	store i32 %62, i32* %69
	store float %63, float* %68
	%132 = load i32, i32* %131
	%133 = icmp ne i32 %132, 0
	br i1 %133, label  %134, label  %288

134:
	%135 = load float, float* %89
	%136 = load float, float* %113
	%137 = fadd float %135, %136
	%138 = load float, float* %70
	%139 = fadd float %137, %138
	%140 = load float, float* %120
	%141 = fadd float %139, %140
	%142 = load float, float* %128
	%143 = load float, float* %95
	%144 = fadd float %142, %143
	%145 = load float, float* %110
	%146 = fadd float %144, %145
	%147 = load float, float* %122
	%148 = fadd float %146, %147
	%149 = load float, float* %124
	%150 = load float, float* %102
	%151 = fadd float %149, %150
	%152 = load float, float* %90
	%153 = fadd float %151, %152
	%154 = load float, float* %112
	%155 = fadd float %153, %154
	%156 = load float, float* %114
	%157 = load float, float* %82
	%158 = fadd float %156, %157
	%159 = load float, float* %91
	%160 = fadd float %158, %159
	%161 = load float, float* %123
	%162 = fadd float %160, %161
	%163 = load float, float* %96
	%164 = load float, float* %72
	%165 = fadd float %163, %164
	%166 = load float, float* %77
	%167 = fadd float %165, %166
	%168 = load float, float* %76
	%169 = fadd float %167, %168
	%170 = load float, float* %105
	%171 = load float, float* %97
	%172 = fadd float %170, %171
	%173 = load float, float* %75
	%174 = fadd float %172, %173
	%175 = load float, float* %103
	%176 = fadd float %174, %175
	%177 = load float, float* %83
	%178 = load float, float* %101
	%179 = fadd float %177, %178
	%180 = load float, float* %74
	%181 = fadd float %179, %180
	%182 = load float, float* %84
	%183 = fadd float %181, %182
	%184 = load float, float* %119
	%185 = load float, float* %81
	%186 = fadd float %184, %185
	%187 = load float, float* %115
	%188 = fadd float %186, %187
	%189 = load float, float* %87
	%190 = fadd float %188, %189
	%191 = load float, float* %108
	%192 = load float, float* %104
	%193 = fadd float %191, %192
	%194 = load float, float* %94
	%195 = fadd float %193, %194
	%196 = load float, float* %68
	%197 = fadd float %195, %196
	%198 = load float, float* %73
	%199 = load float, float* %117
	%200 = fadd float %198, %199
	%201 = load float, float* %111
	%202 = fadd float %200, %201
	%203 = load float, float* %99
	%204 = fadd float %202, %203
	%205= getelementptr [10 x float],[10 x float]* %67, i32 0, i32 0
	%206 = bitcast float* %205 to i32*
	call void @memset(i32* %206, i32 0, i32 40)
	store float %141, float* %205
	%207= getelementptr float,float* %205, i32 1
	store float %148, float* %207
	%208= getelementptr float,float* %205, i32 2
	store float %155, float* %208
	%209= getelementptr float,float* %205, i32 3
	store float %162, float* %209
	%210= getelementptr float,float* %205, i32 4
	store float %169, float* %210
	%211= getelementptr float,float* %205, i32 5
	store float %176, float* %211
	%212= getelementptr float,float* %205, i32 6
	store float %183, float* %212
	%213= getelementptr float,float* %205, i32 7
	store float %190, float* %213
	%214= getelementptr float,float* %205, i32 8
	store float %197, float* %214
	%215= getelementptr float,float* %205, i32 9
	store float %204, float* %215
	%216 = load i32, i32* %118
	%217 = load i32, i32* %127
	%218 = add i32 %216, %217
	%219 = load i32, i32* %130
	%220 = add i32 %218, %219
	%221 = load i32, i32* %80
	%222 = load i32, i32* %126
	%223 = add i32 %221, %222
	%224 = load i32, i32* %125
	%225 = add i32 %223, %224
	%226 = load i32, i32* %129
	%227 = load i32, i32* %109
	%228 = add i32 %226, %227
	%229 = load i32, i32* %100
	%230 = add i32 %228, %229
	%231 = load i32, i32* %92
	%232 = load i32, i32* %107
	%233 = add i32 %231, %232
	%234 = load i32, i32* %86
	%235 = add i32 %233, %234
	%236 = load i32, i32* %88
	%237 = load i32, i32* %106
	%238 = add i32 %236, %237
	%239 = load i32, i32* %69
	%240 = add i32 %238, %239
	%241 = load i32, i32* %71
	%242 = load i32, i32* %85
	%243 = add i32 %241, %242
	%244 = load i32, i32* %98
	%245 = add i32 %243, %244
	%246 = load i32, i32* %93
	%247 = load i32, i32* %116
	%248 = add i32 %246, %247
	%249 = load i32, i32* %78
	%250 = add i32 %248, %249
	%251 = load i32, i32* %79
	%252 = load i32, i32* %121
	%253 = add i32 %251, %252
	%254 = load i32, i32* %131
	%255 = add i32 %253, %254
	%256= getelementptr [8 x i32],[8 x i32]* %66, i32 0, i32 0
	call void @memset(i32* %256, i32 0, i32 32)
	store i32 %220, i32* %256
	%257= getelementptr i32,i32* %256, i32 1
	store i32 %225, i32* %257
	%258= getelementptr i32,i32* %256, i32 2
	store i32 %230, i32* %258
	%259= getelementptr i32,i32* %256, i32 3
	store i32 %235, i32* %259
	%260= getelementptr i32,i32* %256, i32 4
	store i32 %240, i32* %260
	%261= getelementptr i32,i32* %256, i32 5
	store i32 %245, i32* %261
	%262= getelementptr i32,i32* %256, i32 6
	store i32 %250, i32* %262
	%263= getelementptr i32,i32* %256, i32 7
	store i32 %255, i32* %263
	%264= getelementptr [10 x float],[10 x float]* %67, i32 0, i32 0
	call void @putfarray(i32 10, float* %264)
	%265= getelementptr [8 x i32],[8 x i32]* %66, i32 0, i32 0
	call void @putarray(i32 8, i32* %265)
	store i32 0, i32* %65
	br label  %266

266:
	%267 = load i32, i32* %65
	%268 = icmp slt i32 %267, 8
	br i1 %268, label  %269, label  %283

269:
	%270 = load i32, i32* %65
	%271= getelementptr [8 x i32],[8 x i32]* %66, i32 0, i32 %270
	%272 = load i32, i32* %65
	%273= getelementptr [8 x i32],[8 x i32]* %66, i32 0, i32 %272
	%274 = load i32, i32* %273
	%275 = load i32, i32* %65
	%276= getelementptr [10 x float],[10 x float]* %67, i32 0, i32 %275
	%277 = load float, float* %276
	%278 = sitofp i32 %274 to float
	%279 = fsub float %278, %277
	%280 = fptosi float %279 to i32
	store i32 %280, i32* %271
	%281 = load i32, i32* %65
	%282 = add i32 %281, 1
	store i32 %282, i32* %65
	br label  %266

283:
	%284 = load i32, i32* @k
	%285= getelementptr [8 x i32],[8 x i32]* %66, i32 0, i32 %284
	%286 = load i32, i32* %285
	%287 = sitofp i32 %286 to float
	ret float %287
288:
	%289 = load i32, i32* %127
	%290 = load i32, i32* %130
	%291 = load i32, i32* %129
	%292 = load float, float* %128
	%293 = load i32, i32* %127
	%294 = load i32, i32* %126
	%295 = load i32, i32* %125
	%296 = load float, float* %124
	%297 = load float, float* %123
	%298 = load float, float* %122
	%299 = load i32, i32* %121
	%300 = load float, float* %120
	%301 = load float, float* %119
	%302 = load i32, i32* %118
	%303 = load float, float* %117
	%304 = load i32, i32* %116
	%305 = load float, float* %115
	%306 = load float, float* %114
	%307 = load float, float* %113
	%308 = load float, float* %112
	%309 = load float, float* %111
	%310 = load float, float* %110
	%311 = load i32, i32* %109
	%312 = load float, float* %108
	%313 = load i32, i32* %107
	%314 = load i32, i32* %106
	%315 = load float, float* %105
	%316 = load float, float* %104
	%317 = load float, float* %103
	%318 = load float, float* %102
	%319 = load float, float* %101
	%320 = load i32, i32* %100
	%321 = load float, float* %99
	%322 = load i32, i32* %98
	%323 = load float, float* %97
	%324 = load float, float* %96
	%325 = load float, float* %95
	%326 = load float, float* %94
	%327 = load i32, i32* %93
	%328 = load i32, i32* %92
	%329 = load float, float* %91
	%330 = load float, float* %90
	%331 = load float, float* %89
	%332 = load i32, i32* %88
	%333 = load float, float* %87
	%334 = load i32, i32* %86
	%335 = load i32, i32* %85
	%336 = load float, float* %84
	%337 = load float, float* %83
	%338 = load float, float* %82
	%339 = load float, float* %81
	%340 = load i32, i32* %80
	%341 = load i32, i32* %79
	%342 = load i32, i32* %78
	%343 = load float, float* %77
	%344 = load float, float* %76
	%345 = load float, float* %75
	%346 = load float, float* %74
	%347 = load float, float* %73
	%348 = load float, float* %72
	%349 = load i32, i32* %71
	%350 = load float, float* %70
	%351 = load i32, i32* %69
	%352 = load float, float* %68
	%353 = call float @params_f40_i24(i32 %289, i32 %290, i32 %291, float %292, i32 %293, i32 %294, i32 %295, float %296, float %297, float %298, i32 %299, float %300, float %301, i32 %302, float %303, i32 %304, float %305, float %306, float %307, float %308, float %309, float %310, i32 %311, float %312, i32 %313, i32 %314, float %315, float %316, float %317, float %318, float %319, i32 %320, float %321, i32 %322, float %323, float %324, float %325, float %326, i32 %327, i32 %328, float %329, float %330, float %331, i32 %332, float %333, i32 %334, i32 %335, float %336, float %337, float %338, float %339, i32 %340, i32 %341, i32 %342, float %343, float %344, float %345, float %346, float %347, float %348, i32 %349, float %350, i32 %351, float %352)
	ret float %353
}
define dso_local float @params_fa40(float* %0, float* %1, float* %2, float* %3, float* %4, float* %5, float* %6, float* %7, float* %8, float* %9, float* %10, float* %11, float* %12, float* %13, float* %14, float* %15, float* %16, float* %17, float* %18, float* %19, float* %20, float* %21, float* %22, float* %23, float* %24, float* %25, float* %26, float* %27, float* %28, float* %29, float* %30, float* %31, float* %32, float* %33, float* %34, float* %35, float* %36, float* %37, float* %38, float* %39){
	%41 = alloca [10 x float]
	%42 = alloca float*
	%43 = alloca float*
	%44 = alloca float*
	%45 = alloca float*
	%46 = alloca float*
	%47 = alloca float*
	%48 = alloca float*
	%49 = alloca float*
	%50 = alloca float*
	%51 = alloca float*
	%52 = alloca float*
	%53 = alloca float*
	%54 = alloca float*
	%55 = alloca float*
	%56 = alloca float*
	%57 = alloca float*
	%58 = alloca float*
	%59 = alloca float*
	%60 = alloca float*
	%61 = alloca float*
	%62 = alloca float*
	%63 = alloca float*
	%64 = alloca float*
	%65 = alloca float*
	%66 = alloca float*
	%67 = alloca float*
	%68 = alloca float*
	%69 = alloca float*
	%70 = alloca float*
	%71 = alloca float*
	%72 = alloca float*
	%73 = alloca float*
	%74 = alloca float*
	%75 = alloca float*
	%76 = alloca float*
	%77 = alloca float*
	%78 = alloca float*
	%79 = alloca float*
	%80 = alloca float*
	%81 = alloca float*
	store float* %0, float** %81
	store float* %1, float** %80
	store float* %2, float** %79
	store float* %3, float** %78
	store float* %4, float** %77
	store float* %5, float** %76
	store float* %6, float** %75
	store float* %7, float** %74
	store float* %8, float** %73
	store float* %9, float** %72
	store float* %10, float** %71
	store float* %11, float** %70
	store float* %12, float** %69
	store float* %13, float** %68
	store float* %14, float** %67
	store float* %15, float** %66
	store float* %16, float** %65
	store float* %17, float** %64
	store float* %18, float** %63
	store float* %19, float** %62
	store float* %20, float** %61
	store float* %21, float** %60
	store float* %22, float** %59
	store float* %23, float** %58
	store float* %24, float** %57
	store float* %25, float** %56
	store float* %26, float** %55
	store float* %27, float** %54
	store float* %28, float** %53
	store float* %29, float** %52
	store float* %30, float** %51
	store float* %31, float** %50
	store float* %32, float** %49
	store float* %33, float** %48
	store float* %34, float** %47
	store float* %35, float** %46
	store float* %36, float** %45
	store float* %37, float** %44
	store float* %38, float** %43
	store float* %39, float** %42
	%82 = load float*, float** %81
	%83 = load i32, i32* @k
	%84= getelementptr float,float* %82, i32 %83
	%85 = load float, float* %84
	%86 = load float*, float** %80
	%87 = load i32, i32* @k
	%88= getelementptr float,float* %86, i32 %87
	%89 = load float, float* %88
	%90 = fadd float %85, %89
	%91 = load float*, float** %79
	%92 = load i32, i32* @k
	%93= getelementptr float,float* %91, i32 %92
	%94 = load float, float* %93
	%95 = fadd float %90, %94
	%96 = load float*, float** %78
	%97 = load i32, i32* @k
	%98= getelementptr float,float* %96, i32 %97
	%99 = load float, float* %98
	%100 = fadd float %95, %99
	%101 = load float*, float** %77
	%102 = load i32, i32* @k
	%103= getelementptr float,float* %101, i32 %102
	%104 = load float, float* %103
	%105 = load float*, float** %76
	%106 = load i32, i32* @k
	%107= getelementptr float,float* %105, i32 %106
	%108 = load float, float* %107
	%109 = fadd float %104, %108
	%110 = load float*, float** %75
	%111 = load i32, i32* @k
	%112= getelementptr float,float* %110, i32 %111
	%113 = load float, float* %112
	%114 = fadd float %109, %113
	%115 = load float*, float** %74
	%116 = load i32, i32* @k
	%117= getelementptr float,float* %115, i32 %116
	%118 = load float, float* %117
	%119 = fadd float %114, %118
	%120 = load float*, float** %73
	%121 = load i32, i32* @k
	%122= getelementptr float,float* %120, i32 %121
	%123 = load float, float* %122
	%124 = load float*, float** %72
	%125 = load i32, i32* @k
	%126= getelementptr float,float* %124, i32 %125
	%127 = load float, float* %126
	%128 = fadd float %123, %127
	%129 = load float*, float** %71
	%130 = load i32, i32* @k
	%131= getelementptr float,float* %129, i32 %130
	%132 = load float, float* %131
	%133 = fadd float %128, %132
	%134 = load float*, float** %70
	%135 = load i32, i32* @k
	%136= getelementptr float,float* %134, i32 %135
	%137 = load float, float* %136
	%138 = fadd float %133, %137
	%139 = load float*, float** %69
	%140 = load i32, i32* @k
	%141= getelementptr float,float* %139, i32 %140
	%142 = load float, float* %141
	%143 = load float*, float** %68
	%144 = load i32, i32* @k
	%145= getelementptr float,float* %143, i32 %144
	%146 = load float, float* %145
	%147 = fadd float %142, %146
	%148 = load float*, float** %67
	%149 = load i32, i32* @k
	%150= getelementptr float,float* %148, i32 %149
	%151 = load float, float* %150
	%152 = fadd float %147, %151
	%153 = load float*, float** %66
	%154 = load i32, i32* @k
	%155= getelementptr float,float* %153, i32 %154
	%156 = load float, float* %155
	%157 = fadd float %152, %156
	%158 = load float*, float** %65
	%159 = load i32, i32* @k
	%160= getelementptr float,float* %158, i32 %159
	%161 = load float, float* %160
	%162 = load float*, float** %64
	%163 = load i32, i32* @k
	%164= getelementptr float,float* %162, i32 %163
	%165 = load float, float* %164
	%166 = fadd float %161, %165
	%167 = load float*, float** %63
	%168 = load i32, i32* @k
	%169= getelementptr float,float* %167, i32 %168
	%170 = load float, float* %169
	%171 = fadd float %166, %170
	%172 = load float*, float** %62
	%173 = load i32, i32* @k
	%174= getelementptr float,float* %172, i32 %173
	%175 = load float, float* %174
	%176 = fadd float %171, %175
	%177 = load float*, float** %61
	%178 = load i32, i32* @k
	%179= getelementptr float,float* %177, i32 %178
	%180 = load float, float* %179
	%181 = load float*, float** %60
	%182 = load i32, i32* @k
	%183= getelementptr float,float* %181, i32 %182
	%184 = load float, float* %183
	%185 = fadd float %180, %184
	%186 = load float*, float** %59
	%187 = load i32, i32* @k
	%188= getelementptr float,float* %186, i32 %187
	%189 = load float, float* %188
	%190 = fadd float %185, %189
	%191 = load float*, float** %58
	%192 = load i32, i32* @k
	%193= getelementptr float,float* %191, i32 %192
	%194 = load float, float* %193
	%195 = fadd float %190, %194
	%196 = load float*, float** %57
	%197 = load i32, i32* @k
	%198= getelementptr float,float* %196, i32 %197
	%199 = load float, float* %198
	%200 = load float*, float** %56
	%201 = load i32, i32* @k
	%202= getelementptr float,float* %200, i32 %201
	%203 = load float, float* %202
	%204 = fadd float %199, %203
	%205 = load float*, float** %55
	%206 = load i32, i32* @k
	%207= getelementptr float,float* %205, i32 %206
	%208 = load float, float* %207
	%209 = fadd float %204, %208
	%210 = load float*, float** %54
	%211 = load i32, i32* @k
	%212= getelementptr float,float* %210, i32 %211
	%213 = load float, float* %212
	%214 = fadd float %209, %213
	%215 = load float*, float** %53
	%216 = load i32, i32* @k
	%217= getelementptr float,float* %215, i32 %216
	%218 = load float, float* %217
	%219 = load float*, float** %52
	%220 = load i32, i32* @k
	%221= getelementptr float,float* %219, i32 %220
	%222 = load float, float* %221
	%223 = fadd float %218, %222
	%224 = load float*, float** %51
	%225 = load i32, i32* @k
	%226= getelementptr float,float* %224, i32 %225
	%227 = load float, float* %226
	%228 = fadd float %223, %227
	%229 = load float*, float** %50
	%230 = load i32, i32* @k
	%231= getelementptr float,float* %229, i32 %230
	%232 = load float, float* %231
	%233 = fadd float %228, %232
	%234 = load float*, float** %49
	%235 = load i32, i32* @k
	%236= getelementptr float,float* %234, i32 %235
	%237 = load float, float* %236
	%238 = load float*, float** %48
	%239 = load i32, i32* @k
	%240= getelementptr float,float* %238, i32 %239
	%241 = load float, float* %240
	%242 = fadd float %237, %241
	%243 = load float*, float** %47
	%244 = load i32, i32* @k
	%245= getelementptr float,float* %243, i32 %244
	%246 = load float, float* %245
	%247 = fadd float %242, %246
	%248 = load float*, float** %46
	%249 = load i32, i32* @k
	%250= getelementptr float,float* %248, i32 %249
	%251 = load float, float* %250
	%252 = fadd float %247, %251
	%253 = load float*, float** %45
	%254 = load i32, i32* @k
	%255= getelementptr float,float* %253, i32 %254
	%256 = load float, float* %255
	%257 = load float*, float** %44
	%258 = load i32, i32* @k
	%259= getelementptr float,float* %257, i32 %258
	%260 = load float, float* %259
	%261 = fadd float %256, %260
	%262 = load float*, float** %43
	%263 = load i32, i32* @k
	%264= getelementptr float,float* %262, i32 %263
	%265 = load float, float* %264
	%266 = fadd float %261, %265
	%267 = load float*, float** %42
	%268 = load i32, i32* @k
	%269= getelementptr float,float* %267, i32 %268
	%270 = load float, float* %269
	%271 = fadd float %266, %270
	%272= getelementptr [10 x float],[10 x float]* %41, i32 0, i32 0
	%273 = bitcast float* %272 to i32*
	call void @memset(i32* %273, i32 0, i32 40)
	store float %100, float* %272
	%274= getelementptr float,float* %272, i32 1
	store float %119, float* %274
	%275= getelementptr float,float* %272, i32 2
	store float %138, float* %275
	%276= getelementptr float,float* %272, i32 3
	store float %157, float* %276
	%277= getelementptr float,float* %272, i32 4
	store float %176, float* %277
	%278= getelementptr float,float* %272, i32 5
	store float %195, float* %278
	%279= getelementptr float,float* %272, i32 6
	store float %214, float* %279
	%280= getelementptr float,float* %272, i32 7
	store float %233, float* %280
	%281= getelementptr float,float* %272, i32 8
	store float %252, float* %281
	%282= getelementptr float,float* %272, i32 9
	store float %271, float* %282
	%283 = load float*, float** %42
	%284 = load i32, i32* @k
	%285= getelementptr float,float* %283, i32 %284
	%286 = load float, float* %285
	%287 = sitofp i32 0 to float
	%288 = fcmp one float %286, %287
	%289 = zext i1 %288 to i32
	%290 = icmp ne i32 %289, 0
	%291 = sitofp i1 %290 to float
	%292 = fcmp one float %291, 0x0
	%293 = sitofp i1 %292 to float
	%294 = fcmp one float %293, 0x0
	br i1 %294, label  %295, label  %300

295:
	%296= getelementptr [10 x float],[10 x float]* %41, i32 0, i32 0
	call void @putfarray(i32 10, float* %296)
	%297 = load i32, i32* @k
	%298= getelementptr [10 x float],[10 x float]* %41, i32 0, i32 %297
	%299 = load float, float* %298
	ret float %299
300:
	%301 = load float*, float** %80
	%302 = load float*, float** %79
	%303 = load float*, float** %78
	%304 = load float*, float** %77
	%305 = load float*, float** %76
	%306 = load float*, float** %75
	%307 = load float*, float** %74
	%308 = load float*, float** %73
	%309 = load float*, float** %72
	%310 = load float*, float** %71
	%311 = load float*, float** %70
	%312 = load float*, float** %69
	%313 = load float*, float** %68
	%314 = load float*, float** %67
	%315 = load float*, float** %66
	%316 = load float*, float** %65
	%317 = load float*, float** %64
	%318 = load float*, float** %63
	%319 = load float*, float** %62
	%320 = load float*, float** %61
	%321 = load float*, float** %60
	%322 = load float*, float** %59
	%323 = load float*, float** %58
	%324 = load float*, float** %57
	%325 = load float*, float** %56
	%326 = load float*, float** %55
	%327 = load float*, float** %54
	%328 = load float*, float** %53
	%329 = load float*, float** %52
	%330 = load float*, float** %51
	%331 = load float*, float** %50
	%332 = load float*, float** %49
	%333 = load float*, float** %48
	%334 = load float*, float** %47
	%335 = load float*, float** %46
	%336 = load float*, float** %45
	%337 = load float*, float** %44
	%338 = load float*, float** %43
	%339 = load float*, float** %42
	%340= getelementptr [10 x float],[10 x float]* %41, i32 0, i32 0
	%341 = call float @params_fa40(float* %301, float* %302, float* %303, float* %304, float* %305, float* %306, float* %307, float* %308, float* %309, float* %310, float* %311, float* %312, float* %313, float* %314, float* %315, float* %316, float* %317, float* %318, float* %319, float* %320, float* %321, float* %322, float* %323, float* %324, float* %325, float* %326, float* %327, float* %328, float* %329, float* %330, float* %331, float* %332, float* %333, float* %334, float* %335, float* %336, float* %337, float* %338, float* %339, float* %340)
	ret float %341
}
define dso_local i32 @params_mix(float %0, i32* %1, i32 %2, float* %3, float %4, i32 %5, float %6, float %7, float* %8, i32* %9, i32 %10, i32 %11, float* %12, i32* %13, i32* %14, i32 %15, float* %16, float* %17, float %18, float %19, float %20, float* %21, i32 %22, float %23, float %24, float %25, i32* %26, float* %27, i32* %28, i32* %29, float* %30, float %31, float %32, i32* %33, i32 %34, float* %35, float* %36, float %37, float %38, i32* %39, i32* %40, i32 %41, i32 %42, float %43, float %44, i32* %45, i32 %46, float* %47, i32 %48, i32* %49, i32* %50, float %51, float %52, float* %53, i32 %54, i32* %55, float* %56, float %57, i32 %58, float %59, float* %60, float* %61, float %62, i32 %63){
	%65 = alloca i32
	%66 = alloca [10 x i32]
	%67 = alloca [10 x float]
	%68 = alloca i32
	%69 = alloca float
	%70 = alloca float*
	%71 = alloca float*
	%72 = alloca float
	%73 = alloca i32
	%74 = alloca float
	%75 = alloca float*
	%76 = alloca i32*
	%77 = alloca i32
	%78 = alloca float*
	%79 = alloca float
	%80 = alloca float
	%81 = alloca i32*
	%82 = alloca i32*
	%83 = alloca i32
	%84 = alloca float*
	%85 = alloca i32
	%86 = alloca i32*
	%87 = alloca float
	%88 = alloca float
	%89 = alloca i32
	%90 = alloca i32
	%91 = alloca i32*
	%92 = alloca i32*
	%93 = alloca float
	%94 = alloca float
	%95 = alloca float*
	%96 = alloca float*
	%97 = alloca i32
	%98 = alloca i32*
	%99 = alloca float
	%100 = alloca float
	%101 = alloca float*
	%102 = alloca i32*
	%103 = alloca i32*
	%104 = alloca float*
	%105 = alloca i32*
	%106 = alloca float
	%107 = alloca float
	%108 = alloca float
	%109 = alloca i32
	%110 = alloca float*
	%111 = alloca float
	%112 = alloca float
	%113 = alloca float
	%114 = alloca float*
	%115 = alloca float*
	%116 = alloca i32
	%117 = alloca i32*
	%118 = alloca i32*
	%119 = alloca float*
	%120 = alloca i32
	%121 = alloca i32
	%122 = alloca i32*
	%123 = alloca float*
	%124 = alloca float
	%125 = alloca float
	%126 = alloca i32
	%127 = alloca float
	%128 = alloca float*
	%129 = alloca i32
	%130 = alloca i32*
	%131 = alloca float
	store float %0, float* %131
	store i32* %1, i32** %130
	store i32 %2, i32* %129
	store float* %3, float** %128
	store float %4, float* %127
	store i32 %5, i32* %126
	store float %6, float* %125
	store float %7, float* %124
	store float* %8, float** %123
	store i32* %9, i32** %122
	store i32 %10, i32* %121
	store i32 %11, i32* %120
	store float* %12, float** %119
	store i32* %13, i32** %118
	store i32* %14, i32** %117
	store i32 %15, i32* %116
	store float* %16, float** %115
	store float* %17, float** %114
	store float %18, float* %113
	store float %19, float* %112
	store float %20, float* %111
	store float* %21, float** %110
	store i32 %22, i32* %109
	store float %23, float* %108
	store float %24, float* %107
	store float %25, float* %106
	store i32* %26, i32** %105
	store float* %27, float** %104
	store i32* %28, i32** %103
	store i32* %29, i32** %102
	store float* %30, float** %101
	store float %31, float* %100
	store float %32, float* %99
	store i32* %33, i32** %98
	store i32 %34, i32* %97
	store float* %35, float** %96
	store float* %36, float** %95
	store float %37, float* %94
	store float %38, float* %93
	store i32* %39, i32** %92
	store i32* %40, i32** %91
	store i32 %41, i32* %90
	store i32 %42, i32* %89
	store float %43, float* %88
	store float %44, float* %87
	store i32* %45, i32** %86
	store i32 %46, i32* %85
	store float* %47, float** %84
	store i32 %48, i32* %83
	store i32* %49, i32** %82
	store i32* %50, i32** %81
	store float %51, float* %80
	store float %52, float* %79
	store float* %53, float** %78
	store i32 %54, i32* %77
	store i32* %55, i32** %76
	store float* %56, float** %75
	store float %57, float* %74
	store i32 %58, i32* %73
	store float %59, float* %72
	store float* %60, float** %71
	store float* %61, float** %70
	store float %62, float* %69
	store i32 %63, i32* %68
	%132 = load float, float* %131
	%133 = load float*, float** %128
	%134 = load i32, i32* @k
	%135= getelementptr float,float* %133, i32 %134
	%136 = load float, float* %135
	%137 = fadd float %132, %136
	%138 = load float, float* %127
	%139 = fadd float %137, %138
	%140 = load float, float* %125
	%141 = fadd float %139, %140
	%142 = load float, float* %124
	%143 = load float*, float** %123
	%144 = load i32, i32* @k
	%145= getelementptr float,float* %143, i32 %144
	%146 = load float, float* %145
	%147 = fadd float %142, %146
	%148 = load float*, float** %119
	%149 = load i32, i32* @k
	%150= getelementptr float,float* %148, i32 %149
	%151 = load float, float* %150
	%152 = fadd float %147, %151
	%153 = load float*, float** %115
	%154 = load i32, i32* @k
	%155= getelementptr float,float* %153, i32 %154
	%156 = load float, float* %155
	%157 = fadd float %152, %156
	%158 = load float*, float** %114
	%159 = load i32, i32* @k
	%160= getelementptr float,float* %158, i32 %159
	%161 = load float, float* %160
	%162 = load float, float* %113
	%163 = fadd float %161, %162
	%164 = load float, float* %112
	%165 = fadd float %163, %164
	%166 = load float, float* %111
	%167 = fadd float %165, %166
	%168 = load float*, float** %110
	%169 = load i32, i32* @k
	%170= getelementptr float,float* %168, i32 %169
	%171 = load float, float* %170
	%172 = load float, float* %108
	%173 = fadd float %171, %172
	%174 = load float, float* %107
	%175 = fadd float %173, %174
	%176 = load float, float* %106
	%177 = fadd float %175, %176
	%178 = load float*, float** %104
	%179 = load i32, i32* @k
	%180= getelementptr float,float* %178, i32 %179
	%181 = load float, float* %180
	%182 = load float*, float** %101
	%183 = load i32, i32* @k
	%184= getelementptr float,float* %182, i32 %183
	%185 = load float, float* %184
	%186 = fadd float %181, %185
	%187 = load float, float* %100
	%188 = fadd float %186, %187
	%189 = load float, float* %99
	%190 = fadd float %188, %189
	%191 = load float*, float** %96
	%192 = load i32, i32* @k
	%193= getelementptr float,float* %191, i32 %192
	%194 = load float, float* %193
	%195 = load float*, float** %95
	%196 = load i32, i32* @k
	%197= getelementptr float,float* %195, i32 %196
	%198 = load float, float* %197
	%199 = fadd float %194, %198
	%200 = load float, float* %94
	%201 = fadd float %199, %200
	%202 = load float, float* %93
	%203 = fadd float %201, %202
	%204 = load float, float* %88
	%205 = load float, float* %87
	%206 = fadd float %204, %205
	%207 = load float*, float** %84
	%208 = load i32, i32* @k
	%209= getelementptr float,float* %207, i32 %208
	%210 = load float, float* %209
	%211 = fadd float %206, %210
	%212 = load float, float* %80
	%213 = fadd float %211, %212
	%214 = load float, float* %79
	%215 = load float*, float** %78
	%216 = load i32, i32* @k
	%217= getelementptr float,float* %215, i32 %216
	%218 = load float, float* %217
	%219 = fadd float %214, %218
	%220 = load float*, float** %75
	%221 = load i32, i32* @k
	%222= getelementptr float,float* %220, i32 %221
	%223 = load float, float* %222
	%224 = fadd float %219, %223
	%225 = load float, float* %74
	%226 = fadd float %224, %225
	%227 = load float, float* %72
	%228 = load float*, float** %71
	%229 = load i32, i32* @k
	%230= getelementptr float,float* %228, i32 %229
	%231 = load float, float* %230
	%232 = fadd float %227, %231
	%233 = load float*, float** %70
	%234 = load i32, i32* @k
	%235= getelementptr float,float* %233, i32 %234
	%236 = load float, float* %235
	%237 = fadd float %232, %236
	%238 = load float, float* %69
	%239 = fadd float %237, %238
	%240= getelementptr [10 x float],[10 x float]* %67, i32 0, i32 0
	%241 = bitcast float* %240 to i32*
	call void @memset(i32* %241, i32 0, i32 40)
	store float %141, float* %240
	%242= getelementptr float,float* %240, i32 1
	store float %157, float* %242
	%243= getelementptr float,float* %240, i32 2
	store float %167, float* %243
	%244= getelementptr float,float* %240, i32 3
	store float %177, float* %244
	%245= getelementptr float,float* %240, i32 4
	store float %190, float* %245
	%246= getelementptr float,float* %240, i32 5
	store float %203, float* %246
	%247= getelementptr float,float* %240, i32 6
	store float %213, float* %247
	%248= getelementptr float,float* %240, i32 7
	store float %226, float* %248
	%249= getelementptr float,float* %240, i32 8
	store float %239, float* %249
	%250 = load i32*, i32** %130
	%251 = load i32, i32* @k
	%252= getelementptr i32,i32* %250, i32 %251
	%253 = load i32, i32* %252
	%254 = load i32, i32* %129
	%255 = add i32 %253, %254
	%256 = load i32, i32* %126
	%257 = add i32 %255, %256
	%258 = load i32*, i32** %122
	%259 = load i32, i32* @k
	%260= getelementptr i32,i32* %258, i32 %259
	%261 = load i32, i32* %260
	%262 = load i32, i32* %121
	%263 = add i32 %261, %262
	%264 = load i32, i32* %120
	%265 = add i32 %263, %264
	%266 = load i32*, i32** %118
	%267 = load i32, i32* @k
	%268= getelementptr i32,i32* %266, i32 %267
	%269 = load i32, i32* %268
	%270 = load i32*, i32** %117
	%271 = load i32, i32* @k
	%272= getelementptr i32,i32* %270, i32 %271
	%273 = load i32, i32* %272
	%274 = add i32 %269, %273
	%275 = load i32, i32* %116
	%276 = add i32 %274, %275
	%277 = load i32, i32* %109
	%278 = load i32*, i32** %105
	%279 = load i32, i32* @k
	%280= getelementptr i32,i32* %278, i32 %279
	%281 = load i32, i32* %280
	%282 = add i32 %277, %281
	%283 = load i32*, i32** %103
	%284 = load i32, i32* @k
	%285= getelementptr i32,i32* %283, i32 %284
	%286 = load i32, i32* %285
	%287 = add i32 %282, %286
	%288 = load i32*, i32** %102
	%289 = load i32, i32* @k
	%290= getelementptr i32,i32* %288, i32 %289
	%291 = load i32, i32* %290
	%292 = load i32*, i32** %98
	%293 = load i32, i32* @k
	%294= getelementptr i32,i32* %292, i32 %293
	%295 = load i32, i32* %294
	%296 = add i32 %291, %295
	%297 = load i32, i32* %97
	%298 = add i32 %296, %297
	%299 = load i32*, i32** %92
	%300 = load i32, i32* @k
	%301= getelementptr i32,i32* %299, i32 %300
	%302 = load i32, i32* %301
	%303 = load i32*, i32** %91
	%304 = load i32, i32* @k
	%305= getelementptr i32,i32* %303, i32 %304
	%306 = load i32, i32* %305
	%307 = add i32 %302, %306
	%308 = load i32, i32* %90
	%309 = add i32 %307, %308
	%310 = load i32, i32* %89
	%311 = load i32*, i32** %86
	%312 = load i32, i32* @k
	%313= getelementptr i32,i32* %311, i32 %312
	%314 = load i32, i32* %313
	%315 = add i32 %310, %314
	%316 = load i32, i32* %85
	%317 = add i32 %315, %316
	%318 = load i32, i32* %83
	%319 = load i32*, i32** %82
	%320 = load i32, i32* @k
	%321= getelementptr i32,i32* %319, i32 %320
	%322 = load i32, i32* %321
	%323 = add i32 %318, %322
	%324 = load i32*, i32** %81
	%325 = load i32, i32* @k
	%326= getelementptr i32,i32* %324, i32 %325
	%327 = load i32, i32* %326
	%328 = add i32 %323, %327
	%329 = load i32, i32* %77
	%330 = load i32*, i32** %76
	%331 = load i32, i32* @k
	%332= getelementptr i32,i32* %330, i32 %331
	%333 = load i32, i32* %332
	%334 = add i32 %329, %333
	%335 = load i32, i32* %73
	%336 = add i32 %334, %335
	%337 = load i32, i32* %68
	%338 = add i32 %336, %337
	%339= getelementptr [10 x i32],[10 x i32]* %66, i32 0, i32 0
	call void @memset(i32* %339, i32 0, i32 40)
	store i32 %257, i32* %339
	%340= getelementptr i32,i32* %339, i32 1
	store i32 %265, i32* %340
	%341= getelementptr i32,i32* %339, i32 2
	store i32 %276, i32* %341
	%342= getelementptr i32,i32* %339, i32 3
	store i32 %287, i32* %342
	%343= getelementptr i32,i32* %339, i32 4
	store i32 %298, i32* %343
	%344= getelementptr i32,i32* %339, i32 5
	store i32 %309, i32* %344
	%345= getelementptr i32,i32* %339, i32 6
	store i32 %317, i32* %345
	%346= getelementptr i32,i32* %339, i32 7
	store i32 %328, i32* %346
	%347= getelementptr i32,i32* %339, i32 8
	store i32 %338, i32* %347
	%348 = load i32, i32* %68
	%349 = icmp ne i32 %348, 0
	br i1 %349, label  %350, label  %379

350:
	%351= getelementptr [10 x float],[10 x float]* %67, i32 0, i32 0
	call void @putfarray(i32 10, float* %351)
	%352= getelementptr [10 x i32],[10 x i32]* %66, i32 0, i32 0
	call void @putarray(i32 10, i32* %352)
	store i32 0, i32* %65
	br label  %353

353:
	%354 = load i32, i32* %65
	%355 = icmp slt i32 %354, 10
	br i1 %355, label  %356, label  %370

356:
	%357 = load i32, i32* %65
	%358= getelementptr [10 x i32],[10 x i32]* %66, i32 0, i32 %357
	%359 = load i32, i32* %65
	%360= getelementptr [10 x i32],[10 x i32]* %66, i32 0, i32 %359
	%361 = load i32, i32* %360
	%362 = load i32, i32* %65
	%363= getelementptr [10 x float],[10 x float]* %67, i32 0, i32 %362
	%364 = load float, float* %363
	%365 = sitofp i32 %361 to float
	%366 = fsub float %365, %364
	%367 = fptosi float %366 to i32
	store i32 %367, i32* %358
	%368 = load i32, i32* %65
	%369 = add i32 %368, 1
	store i32 %369, i32* %65
	br label  %353

370:
	%371 = load i32, i32* @k
	%372= getelementptr [10 x i32],[10 x i32]* %66, i32 0, i32 %371
	%373 = load i32, i32* %372
	%374= getelementptr [10 x float],[10 x float]* %67, i32 0, i32 8
	%375 = load float, float* %374
	%376 = sitofp i32 %373 to float
	%377 = fmul float %376, %375
	%378 = fptosi float %377 to i32
	ret i32 %378
379:
	%380 = load float, float* %131
	%381= getelementptr [10 x i32],[10 x i32]* %66, i32 0, i32 0
	%382 = load i32, i32* %129
	%383= getelementptr [10 x float],[10 x float]* %67, i32 0, i32 0
	%384 = load float, float* %127
	%385 = load i32, i32* %126
	%386 = load float, float* %125
	%387 = load float, float* %124
	%388 = load float*, float** %123
	%389 = load i32*, i32** %122
	%390 = load i32, i32* %121
	%391 = load i32, i32* %120
	%392 = load float*, float** %119
	%393 = load i32*, i32** %118
	%394 = load i32*, i32** %117
	%395 = load i32, i32* %116
	%396 = load float*, float** %115
	%397 = load float*, float** %114
	%398 = load float, float* %113
	%399 = load float, float* %112
	%400 = load float, float* %111
	%401 = load float*, float** %110
	%402 = load i32, i32* %109
	%403 = load float, float* %108
	%404 = load float, float* %107
	%405 = load float, float* %106
	%406 = load i32*, i32** %105
	%407 = load float*, float** %104
	%408 = load i32*, i32** %103
	%409 = load i32*, i32** %102
	%410 = load float*, float** %101
	%411 = load float, float* %100
	%412 = load float, float* %99
	%413 = load i32*, i32** %98
	%414 = load i32, i32* %97
	%415 = load float*, float** %96
	%416 = load float*, float** %95
	%417 = load float, float* %94
	%418 = load float, float* %93
	%419 = load i32*, i32** %92
	%420 = load i32*, i32** %91
	%421 = load i32, i32* %90
	%422 = load i32, i32* %89
	%423 = load float, float* %88
	%424 = load float, float* %87
	%425 = load i32*, i32** %86
	%426 = load i32, i32* %85
	%427 = load float*, float** %84
	%428 = load i32, i32* %83
	%429 = load i32*, i32** %82
	%430 = load i32*, i32** %81
	%431 = load float, float* %80
	%432 = load float, float* %79
	%433 = load float*, float** %78
	%434 = load i32, i32* %77
	%435 = load i32*, i32** %76
	%436 = load float*, float** %75
	%437 = load float, float* %74
	%438 = load i32, i32* %73
	%439 = load float, float* %72
	%440 = load float*, float** %71
	%441 = load float*, float** %70
	%442 = load i32, i32* %68
	%443 = sitofp i32 %442 to float
	%444 = load float, float* %69
	%445 = fptosi float %444 to i32
	%446 = call i32 @params_mix(float %380, i32* %381, i32 %382, float* %383, float %384, i32 %385, float %386, float %387, float* %388, i32* %389, i32 %390, i32 %391, float* %392, i32* %393, i32* %394, i32 %395, float* %396, float* %397, float %398, float %399, float %400, float* %401, i32 %402, float %403, float %404, float %405, i32* %406, float* %407, i32* %408, i32* %409, float* %410, float %411, float %412, i32* %413, i32 %414, float* %415, float* %416, float %417, float %418, i32* %419, i32* %420, i32 %421, i32 %422, float %423, float %424, i32* %425, i32 %426, float* %427, i32 %428, i32* %429, i32* %430, float %431, float %432, float* %433, i32 %434, i32* %435, float* %436, float %437, i32 %438, float %439, float* %440, float* %441, float %443, i32 %445)
	ret i32 %446
}
define dso_local i32 @main(){
	%1 = alloca i32
	%2 = alloca float
	%3 = alloca float
	%4 = alloca float
	%5 = alloca i32
	%6 = alloca [24 x [3 x i32]]
	%7 = alloca [40 x [3 x float]]
	%8 = call i32 @getint()
	store i32 %8, i32* @k
	store i32 0, i32* %5
	br label  %9

9:
	%10 = load i32, i32* %5
	%11 = icmp slt i32 %10, 40
	br i1 %11, label  %12, label  %19

12:
	%13 = load i32, i32* %5
	%14= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 %13
	%15= getelementptr [3 x float],[3 x float]* %14, i32 0, i32 0
	%16 = call i32 @getfarray(float* %15)
	%17 = load i32, i32* %5
	%18 = add i32 %17, 1
	store i32 %18, i32* %5
	br label  %9

19:
	store i32 0, i32* %5
	br label  %20

20:
	%21 = load i32, i32* %5
	%22 = icmp slt i32 %21, 24
	br i1 %22, label  %23, label  %30

23:
	%24 = load i32, i32* %5
	%25= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 %24
	%26= getelementptr [3 x i32],[3 x i32]* %25, i32 0, i32 0
	%27 = call i32 @getarray(i32* %26)
	%28 = load i32, i32* %5
	%29 = add i32 %28, 1
	store i32 %29, i32* %5
	br label  %20

30:
	%31= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 0
	%32 = load i32, i32* @k
	%33= getelementptr [3 x float],[3 x float]* %31, i32 0, i32 %32
	%34 = load float, float* %33
	%35= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 1
	%36 = load i32, i32* @k
	%37= getelementptr [3 x float],[3 x float]* %35, i32 0, i32 %36
	%38 = load float, float* %37
	%39= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 2
	%40 = load i32, i32* @k
	%41= getelementptr [3 x float],[3 x float]* %39, i32 0, i32 %40
	%42 = load float, float* %41
	%43= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 3
	%44 = load i32, i32* @k
	%45= getelementptr [3 x float],[3 x float]* %43, i32 0, i32 %44
	%46 = load float, float* %45
	%47= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 4
	%48 = load i32, i32* @k
	%49= getelementptr [3 x float],[3 x float]* %47, i32 0, i32 %48
	%50 = load float, float* %49
	%51= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 5
	%52 = load i32, i32* @k
	%53= getelementptr [3 x float],[3 x float]* %51, i32 0, i32 %52
	%54 = load float, float* %53
	%55= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 6
	%56 = load i32, i32* @k
	%57= getelementptr [3 x float],[3 x float]* %55, i32 0, i32 %56
	%58 = load float, float* %57
	%59= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 7
	%60 = load i32, i32* @k
	%61= getelementptr [3 x float],[3 x float]* %59, i32 0, i32 %60
	%62 = load float, float* %61
	%63= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 8
	%64 = load i32, i32* @k
	%65= getelementptr [3 x float],[3 x float]* %63, i32 0, i32 %64
	%66 = load float, float* %65
	%67= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 9
	%68 = load i32, i32* @k
	%69= getelementptr [3 x float],[3 x float]* %67, i32 0, i32 %68
	%70 = load float, float* %69
	%71= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 10
	%72 = load i32, i32* @k
	%73= getelementptr [3 x float],[3 x float]* %71, i32 0, i32 %72
	%74 = load float, float* %73
	%75= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 11
	%76 = load i32, i32* @k
	%77= getelementptr [3 x float],[3 x float]* %75, i32 0, i32 %76
	%78 = load float, float* %77
	%79= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 12
	%80 = load i32, i32* @k
	%81= getelementptr [3 x float],[3 x float]* %79, i32 0, i32 %80
	%82 = load float, float* %81
	%83= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 13
	%84 = load i32, i32* @k
	%85= getelementptr [3 x float],[3 x float]* %83, i32 0, i32 %84
	%86 = load float, float* %85
	%87= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 14
	%88 = load i32, i32* @k
	%89= getelementptr [3 x float],[3 x float]* %87, i32 0, i32 %88
	%90 = load float, float* %89
	%91= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 15
	%92 = load i32, i32* @k
	%93= getelementptr [3 x float],[3 x float]* %91, i32 0, i32 %92
	%94 = load float, float* %93
	%95= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 16
	%96 = load i32, i32* @k
	%97= getelementptr [3 x float],[3 x float]* %95, i32 0, i32 %96
	%98 = load float, float* %97
	%99= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 17
	%100 = load i32, i32* @k
	%101= getelementptr [3 x float],[3 x float]* %99, i32 0, i32 %100
	%102 = load float, float* %101
	%103= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 18
	%104 = load i32, i32* @k
	%105= getelementptr [3 x float],[3 x float]* %103, i32 0, i32 %104
	%106 = load float, float* %105
	%107= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 19
	%108 = load i32, i32* @k
	%109= getelementptr [3 x float],[3 x float]* %107, i32 0, i32 %108
	%110 = load float, float* %109
	%111= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 20
	%112 = load i32, i32* @k
	%113= getelementptr [3 x float],[3 x float]* %111, i32 0, i32 %112
	%114 = load float, float* %113
	%115= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 21
	%116 = load i32, i32* @k
	%117= getelementptr [3 x float],[3 x float]* %115, i32 0, i32 %116
	%118 = load float, float* %117
	%119= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 22
	%120 = load i32, i32* @k
	%121= getelementptr [3 x float],[3 x float]* %119, i32 0, i32 %120
	%122 = load float, float* %121
	%123= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 23
	%124 = load i32, i32* @k
	%125= getelementptr [3 x float],[3 x float]* %123, i32 0, i32 %124
	%126 = load float, float* %125
	%127= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 24
	%128 = load i32, i32* @k
	%129= getelementptr [3 x float],[3 x float]* %127, i32 0, i32 %128
	%130 = load float, float* %129
	%131= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 25
	%132 = load i32, i32* @k
	%133= getelementptr [3 x float],[3 x float]* %131, i32 0, i32 %132
	%134 = load float, float* %133
	%135= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 26
	%136 = load i32, i32* @k
	%137= getelementptr [3 x float],[3 x float]* %135, i32 0, i32 %136
	%138 = load float, float* %137
	%139= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 27
	%140 = load i32, i32* @k
	%141= getelementptr [3 x float],[3 x float]* %139, i32 0, i32 %140
	%142 = load float, float* %141
	%143= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 28
	%144 = load i32, i32* @k
	%145= getelementptr [3 x float],[3 x float]* %143, i32 0, i32 %144
	%146 = load float, float* %145
	%147= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 29
	%148 = load i32, i32* @k
	%149= getelementptr [3 x float],[3 x float]* %147, i32 0, i32 %148
	%150 = load float, float* %149
	%151= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 30
	%152 = load i32, i32* @k
	%153= getelementptr [3 x float],[3 x float]* %151, i32 0, i32 %152
	%154 = load float, float* %153
	%155= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 31
	%156 = load i32, i32* @k
	%157= getelementptr [3 x float],[3 x float]* %155, i32 0, i32 %156
	%158 = load float, float* %157
	%159= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 32
	%160 = load i32, i32* @k
	%161= getelementptr [3 x float],[3 x float]* %159, i32 0, i32 %160
	%162 = load float, float* %161
	%163= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 33
	%164 = load i32, i32* @k
	%165= getelementptr [3 x float],[3 x float]* %163, i32 0, i32 %164
	%166 = load float, float* %165
	%167= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 34
	%168 = load i32, i32* @k
	%169= getelementptr [3 x float],[3 x float]* %167, i32 0, i32 %168
	%170 = load float, float* %169
	%171= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 35
	%172 = load i32, i32* @k
	%173= getelementptr [3 x float],[3 x float]* %171, i32 0, i32 %172
	%174 = load float, float* %173
	%175= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 36
	%176 = load i32, i32* @k
	%177= getelementptr [3 x float],[3 x float]* %175, i32 0, i32 %176
	%178 = load float, float* %177
	%179= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 37
	%180 = load i32, i32* @k
	%181= getelementptr [3 x float],[3 x float]* %179, i32 0, i32 %180
	%182 = load float, float* %181
	%183= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 38
	%184 = load i32, i32* @k
	%185= getelementptr [3 x float],[3 x float]* %183, i32 0, i32 %184
	%186 = load float, float* %185
	%187= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 39
	%188 = load i32, i32* @k
	%189= getelementptr [3 x float],[3 x float]* %187, i32 0, i32 %188
	%190 = load float, float* %189
	%191 = call float @params_f40(float %34, float %38, float %42, float %46, float %50, float %54, float %58, float %62, float %66, float %70, float %74, float %78, float %82, float %86, float %90, float %94, float %98, float %102, float %106, float %110, float %114, float %118, float %122, float %126, float %130, float %134, float %138, float %142, float %146, float %150, float %154, float %158, float %162, float %166, float %170, float %174, float %178, float %182, float %186, float %190)
	store float %191, float* %4
	%192= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 23
	%193 = load i32, i32* @k
	%194= getelementptr [3 x i32],[3 x i32]* %192, i32 0, i32 %193
	%195 = load i32, i32* %194
	%196= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 2
	%197 = load i32, i32* @k
	%198= getelementptr [3 x i32],[3 x i32]* %196, i32 0, i32 %197
	%199 = load i32, i32* %198
	%200= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 6
	%201 = load i32, i32* @k
	%202= getelementptr [3 x i32],[3 x i32]* %200, i32 0, i32 %201
	%203 = load i32, i32* %202
	%204= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 4
	%205 = load i32, i32* @k
	%206= getelementptr [3 x float],[3 x float]* %204, i32 0, i32 %205
	%207 = load float, float* %206
	%208= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 1
	%209 = load i32, i32* @k
	%210= getelementptr [3 x i32],[3 x i32]* %208, i32 0, i32 %209
	%211 = load i32, i32* %210
	%212= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 4
	%213 = load i32, i32* @k
	%214= getelementptr [3 x i32],[3 x i32]* %212, i32 0, i32 %213
	%215 = load i32, i32* %214
	%216= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 5
	%217 = load i32, i32* @k
	%218= getelementptr [3 x i32],[3 x i32]* %216, i32 0, i32 %217
	%219 = load i32, i32* %218
	%220= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 8
	%221 = load i32, i32* @k
	%222= getelementptr [3 x float],[3 x float]* %220, i32 0, i32 %221
	%223 = load float, float* %222
	%224= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 15
	%225 = load i32, i32* @k
	%226= getelementptr [3 x float],[3 x float]* %224, i32 0, i32 %225
	%227 = load float, float* %226
	%228= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 7
	%229 = load i32, i32* @k
	%230= getelementptr [3 x float],[3 x float]* %228, i32 0, i32 %229
	%231 = load float, float* %230
	%232= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 22
	%233 = load i32, i32* @k
	%234= getelementptr [3 x i32],[3 x i32]* %232, i32 0, i32 %233
	%235 = load i32, i32* %234
	%236= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 3
	%237 = load i32, i32* @k
	%238= getelementptr [3 x float],[3 x float]* %236, i32 0, i32 %237
	%239 = load float, float* %238
	%240= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 28
	%241 = load i32, i32* @k
	%242= getelementptr [3 x float],[3 x float]* %240, i32 0, i32 %241
	%243 = load float, float* %242
	%244= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 0
	%245 = load i32, i32* @k
	%246= getelementptr [3 x i32],[3 x i32]* %244, i32 0, i32 %245
	%247 = load i32, i32* %246
	%248= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 37
	%249 = load i32, i32* @k
	%250= getelementptr [3 x float],[3 x float]* %248, i32 0, i32 %249
	%251 = load float, float* %250
	%252= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 19
	%253 = load i32, i32* @k
	%254= getelementptr [3 x i32],[3 x i32]* %252, i32 0, i32 %253
	%255 = load i32, i32* %254
	%256= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 30
	%257 = load i32, i32* @k
	%258= getelementptr [3 x float],[3 x float]* %256, i32 0, i32 %257
	%259 = load float, float* %258
	%260= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 12
	%261 = load i32, i32* @k
	%262= getelementptr [3 x float],[3 x float]* %260, i32 0, i32 %261
	%263 = load float, float* %262
	%264= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 1
	%265 = load i32, i32* @k
	%266= getelementptr [3 x float],[3 x float]* %264, i32 0, i32 %265
	%267 = load float, float* %266
	%268= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 11
	%269 = load i32, i32* @k
	%270= getelementptr [3 x float],[3 x float]* %268, i32 0, i32 %269
	%271 = load float, float* %270
	%272= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 38
	%273 = load i32, i32* @k
	%274= getelementptr [3 x float],[3 x float]* %272, i32 0, i32 %273
	%275 = load float, float* %274
	%276= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 6
	%277 = load i32, i32* @k
	%278= getelementptr [3 x float],[3 x float]* %276, i32 0, i32 %277
	%279 = load float, float* %278
	%280= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 7
	%281 = load i32, i32* @k
	%282= getelementptr [3 x i32],[3 x i32]* %280, i32 0, i32 %281
	%283 = load i32, i32* %282
	%284= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 32
	%285 = load i32, i32* @k
	%286= getelementptr [3 x float],[3 x float]* %284, i32 0, i32 %285
	%287 = load float, float* %286
	%288= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 10
	%289 = load i32, i32* @k
	%290= getelementptr [3 x i32],[3 x i32]* %288, i32 0, i32 %289
	%291 = load i32, i32* %290
	%292= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 13
	%293 = load i32, i32* @k
	%294= getelementptr [3 x i32],[3 x i32]* %292, i32 0, i32 %293
	%295 = load i32, i32* %294
	%296= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 20
	%297 = load i32, i32* @k
	%298= getelementptr [3 x float],[3 x float]* %296, i32 0, i32 %297
	%299 = load float, float* %298
	%300= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 33
	%301 = load i32, i32* @k
	%302= getelementptr [3 x float],[3 x float]* %300, i32 0, i32 %301
	%303 = load float, float* %302
	%304= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 23
	%305 = load i32, i32* @k
	%306= getelementptr [3 x float],[3 x float]* %304, i32 0, i32 %305
	%307 = load float, float* %306
	%308= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 9
	%309 = load i32, i32* @k
	%310= getelementptr [3 x float],[3 x float]* %308, i32 0, i32 %309
	%311 = load float, float* %310
	%312= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 25
	%313 = load i32, i32* @k
	%314= getelementptr [3 x float],[3 x float]* %312, i32 0, i32 %313
	%315 = load float, float* %314
	%316= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 8
	%317 = load i32, i32* @k
	%318= getelementptr [3 x i32],[3 x i32]* %316, i32 0, i32 %317
	%319 = load i32, i32* %318
	%320= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 39
	%321 = load i32, i32* @k
	%322= getelementptr [3 x float],[3 x float]* %320, i32 0, i32 %321
	%323 = load float, float* %322
	%324= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 17
	%325 = load i32, i32* @k
	%326= getelementptr [3 x i32],[3 x i32]* %324, i32 0, i32 %325
	%327 = load i32, i32* %326
	%328= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 21
	%329 = load i32, i32* @k
	%330= getelementptr [3 x float],[3 x float]* %328, i32 0, i32 %329
	%331 = load float, float* %330
	%332= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 16
	%333 = load i32, i32* @k
	%334= getelementptr [3 x float],[3 x float]* %332, i32 0, i32 %333
	%335 = load float, float* %334
	%336= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 5
	%337 = load i32, i32* @k
	%338= getelementptr [3 x float],[3 x float]* %336, i32 0, i32 %337
	%339 = load float, float* %338
	%340= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 34
	%341 = load i32, i32* @k
	%342= getelementptr [3 x float],[3 x float]* %340, i32 0, i32 %341
	%343 = load float, float* %342
	%344= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 18
	%345 = load i32, i32* @k
	%346= getelementptr [3 x i32],[3 x i32]* %344, i32 0, i32 %345
	%347 = load i32, i32* %346
	%348= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 9
	%349 = load i32, i32* @k
	%350= getelementptr [3 x i32],[3 x i32]* %348, i32 0, i32 %349
	%351 = load i32, i32* %350
	%352= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 14
	%353 = load i32, i32* @k
	%354= getelementptr [3 x float],[3 x float]* %352, i32 0, i32 %353
	%355 = load float, float* %354
	%356= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 10
	%357 = load i32, i32* @k
	%358= getelementptr [3 x float],[3 x float]* %356, i32 0, i32 %357
	%359 = load float, float* %358
	%360= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 0
	%361 = load i32, i32* @k
	%362= getelementptr [3 x float],[3 x float]* %360, i32 0, i32 %361
	%363 = load float, float* %362
	%364= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 12
	%365 = load i32, i32* @k
	%366= getelementptr [3 x i32],[3 x i32]* %364, i32 0, i32 %365
	%367 = load i32, i32* %366
	%368= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 31
	%369 = load i32, i32* @k
	%370= getelementptr [3 x float],[3 x float]* %368, i32 0, i32 %369
	%371 = load float, float* %370
	%372= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 11
	%373 = load i32, i32* @k
	%374= getelementptr [3 x i32],[3 x i32]* %372, i32 0, i32 %373
	%375 = load i32, i32* %374
	%376= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 16
	%377 = load i32, i32* @k
	%378= getelementptr [3 x i32],[3 x i32]* %376, i32 0, i32 %377
	%379 = load i32, i32* %378
	%380= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 27
	%381 = load i32, i32* @k
	%382= getelementptr [3 x float],[3 x float]* %380, i32 0, i32 %381
	%383 = load float, float* %382
	%384= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 24
	%385 = load i32, i32* @k
	%386= getelementptr [3 x float],[3 x float]* %384, i32 0, i32 %385
	%387 = load float, float* %386
	%388= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 13
	%389 = load i32, i32* @k
	%390= getelementptr [3 x float],[3 x float]* %388, i32 0, i32 %389
	%391 = load float, float* %390
	%392= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 29
	%393 = load i32, i32* @k
	%394= getelementptr [3 x float],[3 x float]* %392, i32 0, i32 %393
	%395 = load float, float* %394
	%396= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 3
	%397 = load i32, i32* @k
	%398= getelementptr [3 x i32],[3 x i32]* %396, i32 0, i32 %397
	%399 = load i32, i32* %398
	%400= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 21
	%401 = load i32, i32* @k
	%402= getelementptr [3 x i32],[3 x i32]* %400, i32 0, i32 %401
	%403 = load i32, i32* %402
	%404= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 20
	%405 = load i32, i32* @k
	%406= getelementptr [3 x i32],[3 x i32]* %404, i32 0, i32 %405
	%407 = load i32, i32* %406
	%408= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 18
	%409 = load i32, i32* @k
	%410= getelementptr [3 x float],[3 x float]* %408, i32 0, i32 %409
	%411 = load float, float* %410
	%412= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 19
	%413 = load i32, i32* @k
	%414= getelementptr [3 x float],[3 x float]* %412, i32 0, i32 %413
	%415 = load float, float* %414
	%416= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 22
	%417 = load i32, i32* @k
	%418= getelementptr [3 x float],[3 x float]* %416, i32 0, i32 %417
	%419 = load float, float* %418
	%420= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 26
	%421 = load i32, i32* @k
	%422= getelementptr [3 x float],[3 x float]* %420, i32 0, i32 %421
	%423 = load float, float* %422
	%424= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 36
	%425 = load i32, i32* @k
	%426= getelementptr [3 x float],[3 x float]* %424, i32 0, i32 %425
	%427 = load float, float* %426
	%428= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 17
	%429 = load i32, i32* @k
	%430= getelementptr [3 x float],[3 x float]* %428, i32 0, i32 %429
	%431 = load float, float* %430
	%432= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 15
	%433 = load i32, i32* @k
	%434= getelementptr [3 x i32],[3 x i32]* %432, i32 0, i32 %433
	%435 = load i32, i32* %434
	%436= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 2
	%437 = load i32, i32* @k
	%438= getelementptr [3 x float],[3 x float]* %436, i32 0, i32 %437
	%439 = load float, float* %438
	%440= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 14
	%441 = load i32, i32* @k
	%442= getelementptr [3 x i32],[3 x i32]* %440, i32 0, i32 %441
	%443 = load i32, i32* %442
	%444= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 35
	%445 = load i32, i32* @k
	%446= getelementptr [3 x float],[3 x float]* %444, i32 0, i32 %445
	%447 = load float, float* %446
	%448 = call float @params_f40_i24(i32 %195, i32 %199, i32 %203, float %207, i32 %211, i32 %215, i32 %219, float %223, float %227, float %231, i32 %235, float %239, float %243, i32 %247, float %251, i32 %255, float %259, float %263, float %267, float %271, float %275, float %279, i32 %283, float %287, i32 %291, i32 %295, float %299, float %303, float %307, float %311, float %315, i32 %319, float %323, i32 %327, float %331, float %335, float %339, float %343, i32 %347, i32 %351, float %355, float %359, float %363, i32 %367, float %371, i32 %375, i32 %379, float %383, float %387, float %391, float %395, i32 %399, i32 %403, i32 %407, float %411, float %415, float %419, float %423, float %427, float %431, i32 %435, float %439, i32 %443, float %447)
	store float %448, float* %3
	%449= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 0
	%450= getelementptr [3 x float],[3 x float]* %449, i32 0, i32 0
	%451= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 1
	%452= getelementptr [3 x float],[3 x float]* %451, i32 0, i32 0
	%453= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 2
	%454= getelementptr [3 x float],[3 x float]* %453, i32 0, i32 0
	%455= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 3
	%456= getelementptr [3 x float],[3 x float]* %455, i32 0, i32 0
	%457= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 4
	%458= getelementptr [3 x float],[3 x float]* %457, i32 0, i32 0
	%459= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 5
	%460= getelementptr [3 x float],[3 x float]* %459, i32 0, i32 0
	%461= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 6
	%462= getelementptr [3 x float],[3 x float]* %461, i32 0, i32 0
	%463= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 7
	%464= getelementptr [3 x float],[3 x float]* %463, i32 0, i32 0
	%465= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 8
	%466= getelementptr [3 x float],[3 x float]* %465, i32 0, i32 0
	%467= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 9
	%468= getelementptr [3 x float],[3 x float]* %467, i32 0, i32 0
	%469= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 10
	%470= getelementptr [3 x float],[3 x float]* %469, i32 0, i32 0
	%471= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 11
	%472= getelementptr [3 x float],[3 x float]* %471, i32 0, i32 0
	%473= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 12
	%474= getelementptr [3 x float],[3 x float]* %473, i32 0, i32 0
	%475= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 13
	%476= getelementptr [3 x float],[3 x float]* %475, i32 0, i32 0
	%477= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 14
	%478= getelementptr [3 x float],[3 x float]* %477, i32 0, i32 0
	%479= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 15
	%480= getelementptr [3 x float],[3 x float]* %479, i32 0, i32 0
	%481= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 16
	%482= getelementptr [3 x float],[3 x float]* %481, i32 0, i32 0
	%483= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 17
	%484= getelementptr [3 x float],[3 x float]* %483, i32 0, i32 0
	%485= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 18
	%486= getelementptr [3 x float],[3 x float]* %485, i32 0, i32 0
	%487= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 19
	%488= getelementptr [3 x float],[3 x float]* %487, i32 0, i32 0
	%489= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 20
	%490= getelementptr [3 x float],[3 x float]* %489, i32 0, i32 0
	%491= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 21
	%492= getelementptr [3 x float],[3 x float]* %491, i32 0, i32 0
	%493= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 22
	%494= getelementptr [3 x float],[3 x float]* %493, i32 0, i32 0
	%495= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 23
	%496= getelementptr [3 x float],[3 x float]* %495, i32 0, i32 0
	%497= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 24
	%498= getelementptr [3 x float],[3 x float]* %497, i32 0, i32 0
	%499= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 25
	%500= getelementptr [3 x float],[3 x float]* %499, i32 0, i32 0
	%501= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 26
	%502= getelementptr [3 x float],[3 x float]* %501, i32 0, i32 0
	%503= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 27
	%504= getelementptr [3 x float],[3 x float]* %503, i32 0, i32 0
	%505= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 28
	%506= getelementptr [3 x float],[3 x float]* %505, i32 0, i32 0
	%507= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 29
	%508= getelementptr [3 x float],[3 x float]* %507, i32 0, i32 0
	%509= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 30
	%510= getelementptr [3 x float],[3 x float]* %509, i32 0, i32 0
	%511= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 31
	%512= getelementptr [3 x float],[3 x float]* %511, i32 0, i32 0
	%513= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 32
	%514= getelementptr [3 x float],[3 x float]* %513, i32 0, i32 0
	%515= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 33
	%516= getelementptr [3 x float],[3 x float]* %515, i32 0, i32 0
	%517= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 34
	%518= getelementptr [3 x float],[3 x float]* %517, i32 0, i32 0
	%519= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 35
	%520= getelementptr [3 x float],[3 x float]* %519, i32 0, i32 0
	%521= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 36
	%522= getelementptr [3 x float],[3 x float]* %521, i32 0, i32 0
	%523= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 37
	%524= getelementptr [3 x float],[3 x float]* %523, i32 0, i32 0
	%525= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 38
	%526= getelementptr [3 x float],[3 x float]* %525, i32 0, i32 0
	%527= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 39
	%528= getelementptr [3 x float],[3 x float]* %527, i32 0, i32 0
	%529 = call float @params_fa40(float* %450, float* %452, float* %454, float* %456, float* %458, float* %460, float* %462, float* %464, float* %466, float* %468, float* %470, float* %472, float* %474, float* %476, float* %478, float* %480, float* %482, float* %484, float* %486, float* %488, float* %490, float* %492, float* %494, float* %496, float* %498, float* %500, float* %502, float* %504, float* %506, float* %508, float* %510, float* %512, float* %514, float* %516, float* %518, float* %520, float* %522, float* %524, float* %526, float* %528)
	store float %529, float* %2
	%530= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 0
	%531 = load i32, i32* @k
	%532= getelementptr [3 x float],[3 x float]* %530, i32 0, i32 %531
	%533 = load float, float* %532
	%534= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 0
	%535= getelementptr [3 x i32],[3 x i32]* %534, i32 0, i32 0
	%536= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 1
	%537 = load i32, i32* @k
	%538= getelementptr [3 x i32],[3 x i32]* %536, i32 0, i32 %537
	%539 = load i32, i32* %538
	%540= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 1
	%541= getelementptr [3 x float],[3 x float]* %540, i32 0, i32 0
	%542= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 2
	%543 = load i32, i32* @k
	%544= getelementptr [3 x float],[3 x float]* %542, i32 0, i32 %543
	%545 = load float, float* %544
	%546= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 2
	%547 = load i32, i32* @k
	%548= getelementptr [3 x i32],[3 x i32]* %546, i32 0, i32 %547
	%549 = load i32, i32* %548
	%550= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 3
	%551 = load i32, i32* @k
	%552= getelementptr [3 x float],[3 x float]* %550, i32 0, i32 %551
	%553 = load float, float* %552
	%554= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 4
	%555 = load i32, i32* @k
	%556= getelementptr [3 x float],[3 x float]* %554, i32 0, i32 %555
	%557 = load float, float* %556
	%558= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 5
	%559= getelementptr [3 x float],[3 x float]* %558, i32 0, i32 0
	%560= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 3
	%561= getelementptr [3 x i32],[3 x i32]* %560, i32 0, i32 0
	%562= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 4
	%563 = load i32, i32* @k
	%564= getelementptr [3 x i32],[3 x i32]* %562, i32 0, i32 %563
	%565 = load i32, i32* %564
	%566= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 5
	%567 = load i32, i32* @k
	%568= getelementptr [3 x i32],[3 x i32]* %566, i32 0, i32 %567
	%569 = load i32, i32* %568
	%570= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 6
	%571= getelementptr [3 x float],[3 x float]* %570, i32 0, i32 0
	%572= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 6
	%573= getelementptr [3 x i32],[3 x i32]* %572, i32 0, i32 0
	%574= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 7
	%575= getelementptr [3 x i32],[3 x i32]* %574, i32 0, i32 0
	%576= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 8
	%577 = load i32, i32* @k
	%578= getelementptr [3 x i32],[3 x i32]* %576, i32 0, i32 %577
	%579 = load i32, i32* %578
	%580= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 7
	%581= getelementptr [3 x float],[3 x float]* %580, i32 0, i32 0
	%582= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 8
	%583= getelementptr [3 x float],[3 x float]* %582, i32 0, i32 0
	%584= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 9
	%585 = load i32, i32* @k
	%586= getelementptr [3 x float],[3 x float]* %584, i32 0, i32 %585
	%587 = load float, float* %586
	%588= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 10
	%589 = load i32, i32* @k
	%590= getelementptr [3 x float],[3 x float]* %588, i32 0, i32 %589
	%591 = load float, float* %590
	%592= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 11
	%593 = load i32, i32* @k
	%594= getelementptr [3 x float],[3 x float]* %592, i32 0, i32 %593
	%595 = load float, float* %594
	%596= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 12
	%597= getelementptr [3 x float],[3 x float]* %596, i32 0, i32 0
	%598= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 9
	%599 = load i32, i32* @k
	%600= getelementptr [3 x i32],[3 x i32]* %598, i32 0, i32 %599
	%601 = load i32, i32* %600
	%602= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 13
	%603 = load i32, i32* @k
	%604= getelementptr [3 x float],[3 x float]* %602, i32 0, i32 %603
	%605 = load float, float* %604
	%606= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 14
	%607 = load i32, i32* @k
	%608= getelementptr [3 x float],[3 x float]* %606, i32 0, i32 %607
	%609 = load float, float* %608
	%610= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 15
	%611 = load i32, i32* @k
	%612= getelementptr [3 x float],[3 x float]* %610, i32 0, i32 %611
	%613 = load float, float* %612
	%614= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 10
	%615= getelementptr [3 x i32],[3 x i32]* %614, i32 0, i32 0
	%616= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 16
	%617= getelementptr [3 x float],[3 x float]* %616, i32 0, i32 0
	%618= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 11
	%619= getelementptr [3 x i32],[3 x i32]* %618, i32 0, i32 0
	%620= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 12
	%621= getelementptr [3 x i32],[3 x i32]* %620, i32 0, i32 0
	%622= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 17
	%623= getelementptr [3 x float],[3 x float]* %622, i32 0, i32 0
	%624= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 18
	%625 = load i32, i32* @k
	%626= getelementptr [3 x float],[3 x float]* %624, i32 0, i32 %625
	%627 = load float, float* %626
	%628= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 19
	%629 = load i32, i32* @k
	%630= getelementptr [3 x float],[3 x float]* %628, i32 0, i32 %629
	%631 = load float, float* %630
	%632= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 13
	%633= getelementptr [3 x i32],[3 x i32]* %632, i32 0, i32 0
	%634= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 14
	%635 = load i32, i32* @k
	%636= getelementptr [3 x i32],[3 x i32]* %634, i32 0, i32 %635
	%637 = load i32, i32* %636
	%638= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 20
	%639= getelementptr [3 x float],[3 x float]* %638, i32 0, i32 0
	%640= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 21
	%641= getelementptr [3 x float],[3 x float]* %640, i32 0, i32 0
	%642= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 22
	%643 = load i32, i32* @k
	%644= getelementptr [3 x float],[3 x float]* %642, i32 0, i32 %643
	%645 = load float, float* %644
	%646= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 23
	%647 = load i32, i32* @k
	%648= getelementptr [3 x float],[3 x float]* %646, i32 0, i32 %647
	%649 = load float, float* %648
	%650= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 15
	%651= getelementptr [3 x i32],[3 x i32]* %650, i32 0, i32 0
	%652= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 16
	%653= getelementptr [3 x i32],[3 x i32]* %652, i32 0, i32 0
	%654= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 17
	%655 = load i32, i32* @k
	%656= getelementptr [3 x i32],[3 x i32]* %654, i32 0, i32 %655
	%657 = load i32, i32* %656
	%658= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 18
	%659 = load i32, i32* @k
	%660= getelementptr [3 x i32],[3 x i32]* %658, i32 0, i32 %659
	%661 = load i32, i32* %660
	%662= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 24
	%663 = load i32, i32* @k
	%664= getelementptr [3 x float],[3 x float]* %662, i32 0, i32 %663
	%665 = load float, float* %664
	%666= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 25
	%667 = load i32, i32* @k
	%668= getelementptr [3 x float],[3 x float]* %666, i32 0, i32 %667
	%669 = load float, float* %668
	%670= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 19
	%671= getelementptr [3 x i32],[3 x i32]* %670, i32 0, i32 0
	%672= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 20
	%673 = load i32, i32* @k
	%674= getelementptr [3 x i32],[3 x i32]* %672, i32 0, i32 %673
	%675 = load i32, i32* %674
	%676= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 26
	%677= getelementptr [3 x float],[3 x float]* %676, i32 0, i32 0
	%678= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 21
	%679 = load i32, i32* @k
	%680= getelementptr [3 x i32],[3 x i32]* %678, i32 0, i32 %679
	%681 = load i32, i32* %680
	%682= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 22
	%683= getelementptr [3 x i32],[3 x i32]* %682, i32 0, i32 0
	%684= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 23
	%685= getelementptr [3 x i32],[3 x i32]* %684, i32 0, i32 0
	%686= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 27
	%687 = load i32, i32* @k
	%688= getelementptr [3 x float],[3 x float]* %686, i32 0, i32 %687
	%689 = load float, float* %688
	%690= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 28
	%691 = load i32, i32* @k
	%692= getelementptr [3 x float],[3 x float]* %690, i32 0, i32 %691
	%693 = load float, float* %692
	%694= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 29
	%695= getelementptr [3 x float],[3 x float]* %694, i32 0, i32 0
	%696= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 0
	%697 = load i32, i32* @k
	%698= getelementptr [3 x i32],[3 x i32]* %696, i32 0, i32 %697
	%699 = load i32, i32* %698
	%700= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 1
	%701= getelementptr [3 x i32],[3 x i32]* %700, i32 0, i32 0
	%702= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 30
	%703= getelementptr [3 x float],[3 x float]* %702, i32 0, i32 0
	%704= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 31
	%705 = load i32, i32* @k
	%706= getelementptr [3 x float],[3 x float]* %704, i32 0, i32 %705
	%707 = load float, float* %706
	%708= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 2
	%709 = load i32, i32* @k
	%710= getelementptr [3 x i32],[3 x i32]* %708, i32 0, i32 %709
	%711 = load i32, i32* %710
	%712= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 32
	%713 = load i32, i32* @k
	%714= getelementptr [3 x float],[3 x float]* %712, i32 0, i32 %713
	%715 = load float, float* %714
	%716= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 33
	%717= getelementptr [3 x float],[3 x float]* %716, i32 0, i32 0
	%718= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 34
	%719= getelementptr [3 x float],[3 x float]* %718, i32 0, i32 0
	%720= getelementptr [40 x [3 x float]],[40 x [3 x float]]* %7, i32 0, i32 35
	%721 = load i32, i32* @k
	%722= getelementptr [3 x float],[3 x float]* %720, i32 0, i32 %721
	%723 = load float, float* %722
	%724= getelementptr [24 x [3 x i32]],[24 x [3 x i32]]* %6, i32 0, i32 3
	%725 = load i32, i32* @k
	%726= getelementptr [3 x i32],[3 x i32]* %724, i32 0, i32 %725
	%727 = load i32, i32* %726
	%728 = call i32 @params_mix(float %533, i32* %535, i32 %539, float* %541, float %545, i32 %549, float %553, float %557, float* %559, i32* %561, i32 %565, i32 %569, float* %571, i32* %573, i32* %575, i32 %579, float* %581, float* %583, float %587, float %591, float %595, float* %597, i32 %601, float %605, float %609, float %613, i32* %615, float* %617, i32* %619, i32* %621, float* %623, float %627, float %631, i32* %633, i32 %637, float* %639, float* %641, float %645, float %649, i32* %651, i32* %653, i32 %657, i32 %661, float %665, float %669, i32* %671, i32 %675, float* %677, i32 %681, i32* %683, i32* %685, float %689, float %693, float* %695, i32 %699, i32* %701, float* %703, float %707, i32 %711, float %715, float* %717, float* %719, float %723, i32 %727)
	store i32 %728, i32* %1
	%729 = load float, float* %4
	call void @putfloat(float %729)
	call void @putch(i32 10)
	%730 = load float, float* %3
	call void @putfloat(float %730)
	call void @putch(i32 10)
	%731 = load float, float* %2
	call void @putfloat(float %731)
	call void @putch(i32 10)
	%732 = load i32, i32* %1
	call void @putint(i32 %732)
	call void @putch(i32 10)
	ret i32 0
}
