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
@ints = dso_local global [10000 x i32] zeroinitializer 
@intt = dso_local global i32 0
@chas = dso_local global [10000 x i32] zeroinitializer 
@chat = dso_local global i32 0
@i = dso_local global i32 0
@ii = dso_local global i32 1
@c = dso_local global i32 0
@get = dso_local global [10000 x i32] zeroinitializer 
@get2 = dso_local global [10000 x i32] zeroinitializer 
define dso_local i32 @isdigit(i32 %0){
	%2 = icmp sge i32 %0, 48
	br i1 %2, label  %5, label  %3

3:
	ret i32 0
4:
	ret i32 1
5:
	%6 = icmp sle i32 %0, 57
	br i1 %6, label  %4, label  %3

}
define dso_local i32 @power(i32 %0, i32 %1){
	br label  %3

3:
	%4 = phi i32 [1, %2], [%9, %8]
	%5 = phi i32 [%1, %2], [%10, %8]
	%6 = phi i32 [%0, %2], [%6, %8]
	%7 = icmp ne i32 %5, 0
	br i1 %7, label  %8, label  %11

8:
	%9 = mul i32 %4, %6
	%10 = sub i32 %5, 1
	br label  %3

11:
	ret i32 %4
}
define dso_local i32 @getstr(i32* %0){
	%2 = call i32 @getch()
	br label  %3

3:
	%4 = phi i32 [0, %1], [%10, %8]
	%5 = phi i32 [%2, %1], [%11, %8]
	%6 = phi i32* [%0, %1], [%6, %8]
	%7 = icmp ne i32 %5, 13
	br i1 %7, label  %13, label  %12

8:
	%9= getelementptr i32,i32* %6, i32 %4
	store i32 %5, i32* %9
	%10 = add i32 %4, 1
	%11 = call i32 @getch()
	br label  %3

12:
	ret i32 %4
13:
	%14 = icmp ne i32 %5, 10
	br i1 %14, label  %8, label  %12

}
define dso_local void @intpush(i32 %0){
	%2 = load i32, i32* @intt
	%3 = add i32 %2, 1
	store i32 %3, i32* @intt
	%4 = load i32, i32* @intt
	%5= getelementptr [10000 x i32],[10000 x i32]* @ints, i32 0, i32 %4
	store i32 %0, i32* %5
	ret void 
}
define dso_local void @chapush(i32 %0){
	%2 = load i32, i32* @chat
	%3 = add i32 %2, 1
	store i32 %3, i32* @chat
	%4 = load i32, i32* @chat
	%5= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %4
	store i32 %0, i32* %5
	ret void 
}
define dso_local i32 @intpop(){
	%1 = load i32, i32* @intt
	%2 = sub i32 %1, 1
	store i32 %2, i32* @intt
	%3 = load i32, i32* @intt
	%4 = add i32 %3, 1
	%5= getelementptr [10000 x i32],[10000 x i32]* @ints, i32 0, i32 %4
	%6 = load i32, i32* %5
	ret i32 %6
}
define dso_local i32 @chapop(){
	%1 = load i32, i32* @chat
	%2 = sub i32 %1, 1
	store i32 %2, i32* @chat
	%3 = load i32, i32* @chat
	%4 = add i32 %3, 1
	%5= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %4
	%6 = load i32, i32* %5
	ret i32 %6
}
define dso_local void @intadd(i32 %0){
	%2 = load i32, i32* @intt
	%3= getelementptr [10000 x i32],[10000 x i32]* @ints, i32 0, i32 %2
	%4 = load i32, i32* @intt
	%5= getelementptr [10000 x i32],[10000 x i32]* @ints, i32 0, i32 %4
	%6 = load i32, i32* %5
	%7 = mul i32 %6, 10
	store i32 %7, i32* %3
	%8 = load i32, i32* @intt
	%9= getelementptr [10000 x i32],[10000 x i32]* @ints, i32 0, i32 %8
	%10 = load i32, i32* @intt
	%11= getelementptr [10000 x i32],[10000 x i32]* @ints, i32 0, i32 %10
	%12 = load i32, i32* %11
	%13 = add i32 %12, %0
	store i32 %13, i32* %9
	ret void 
}
define dso_local i32 @find(){
	%1 = call i32 @chapop()
	store i32 %1, i32* @c
	%2 = load i32, i32* @ii
	%3= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %2
	store i32 32, i32* %3
	%4 = load i32, i32* @ii
	%5 = add i32 %4, 1
	%6= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %5
	%7 = load i32, i32* @c
	store i32 %7, i32* %6
	%8 = load i32, i32* @ii
	%9 = add i32 %8, 2
	store i32 %9, i32* @ii
	%10 = load i32, i32* @chat
	%11 = icmp eq i32 %10, 0
	br i1 %11, label  %13, label  %12

12:
	ret i32 1
13:
	ret i32 0
}
define dso_local i32 @main(){
	store i32 0, i32* @intt
	store i32 0, i32* @chat
	%1= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 0
	%2 = call i32 @getstr(i32* %1)
	br label  %3

3:
	%4 = phi i32 [0, %0], [%15, %14]
	%5 = phi i32 [%2, %0], [%16, %14]
	%6 = load i32, i32* @i
	%7 = icmp slt i32 %6, %5
	br i1 %7, label  %8, label  %253

8:
	%9 = load i32, i32* @i
	%10= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %9
	%11 = load i32, i32* %10
	%12 = call i32 @isdigit(i32 %11)
	%13 = icmp eq i32 %12, 1
	br i1 %13, label  %19, label  %27

14:
	%15 = phi i32 [%4, %19], [%221, %220]
	%16 = phi i32 [%5, %19], [%222, %220]
	%17 = load i32, i32* @i
	%18 = add i32 %17, 1
	store i32 %18, i32* @i
	br label  %3

19:
	%20 = load i32, i32* @ii
	%21= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %20
	%22 = load i32, i32* @i
	%23= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %22
	%24 = load i32, i32* %23
	store i32 %24, i32* %21
	%25 = load i32, i32* @ii
	%26 = add i32 %25, 1
	store i32 %26, i32* @ii
	br label  %14

27:
	%28 = load i32, i32* @i
	%29= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %28
	%30 = load i32, i32* %29
	%31 = icmp eq i32 %30, 40
	br i1 %31, label  %32, label  %33

32:
	call void @chapush(i32 40)
	br label  %33

33:
	%34 = load i32, i32* @i
	%35= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %34
	%36 = load i32, i32* %35
	%37 = icmp eq i32 %36, 94
	br i1 %37, label  %38, label  %39

38:
	call void @chapush(i32 94)
	br label  %39

39:
	%40 = load i32, i32* @i
	%41= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %40
	%42 = load i32, i32* %41
	%43 = icmp eq i32 %42, 41
	br i1 %43, label  %44, label  %61

44:
	%45 = call i32 @chapop()
	store i32 %45, i32* @c
	br label  %46

46:
	%47 = phi i32 [%4, %44], [%47, %51]
	%48 = phi i32 [%5, %44], [%48, %51]
	%49 = load i32, i32* @c
	%50 = icmp ne i32 %49, 40
	br i1 %50, label  %51, label  %61

51:
	%52 = load i32, i32* @ii
	%53= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %52
	store i32 32, i32* %53
	%54 = load i32, i32* @ii
	%55 = add i32 %54, 1
	%56= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %55
	%57 = load i32, i32* @c
	store i32 %57, i32* %56
	%58 = load i32, i32* @ii
	%59 = add i32 %58, 2
	store i32 %59, i32* @ii
	%60 = call i32 @chapop()
	store i32 %60, i32* @c
	br label  %46

61:
	%62 = phi i32 [%4, %39], [%47, %46]
	%63 = phi i32 [%5, %39], [%48, %46]
	%64 = load i32, i32* @i
	%65= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %64
	%66 = load i32, i32* %65
	%67 = icmp eq i32 %66, 43
	br i1 %67, label  %68, label  %104

68:
	%69 = phi i32 [%62, %61], [%69, %101]
	%70 = phi i32 [%63, %61], [%70, %101]
	%71 = load i32, i32* @chat
	%72= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %71
	%73 = load i32, i32* %72
	%74 = icmp eq i32 %73, 43
	br i1 %74, label  %101, label  %76

75:
	call void @chapush(i32 43)
	br label  %104

76:
	%77 = load i32, i32* @chat
	%78= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %77
	%79 = load i32, i32* %78
	%80 = icmp eq i32 %79, 45
	br i1 %80, label  %101, label  %81

81:
	%82 = load i32, i32* @chat
	%83= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %82
	%84 = load i32, i32* %83
	%85 = icmp eq i32 %84, 42
	br i1 %85, label  %101, label  %86

86:
	%87 = load i32, i32* @chat
	%88= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %87
	%89 = load i32, i32* %88
	%90 = icmp eq i32 %89, 47
	br i1 %90, label  %101, label  %91

91:
	%92 = load i32, i32* @chat
	%93= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %92
	%94 = load i32, i32* %93
	%95 = icmp eq i32 %94, 37
	br i1 %95, label  %101, label  %96

96:
	%97 = load i32, i32* @chat
	%98= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %97
	%99 = load i32, i32* %98
	%100 = icmp eq i32 %99, 94
	br i1 %100, label  %101, label  %75

101:
	%102 = call i32 @find()
	%103 = icmp eq i32 %102, 0
	br i1 %103, label  %75, label  %68

104:
	%105 = phi i32 [%69, %75], [%62, %61]
	%106 = phi i32 [%70, %75], [%63, %61]
	%107 = load i32, i32* @i
	%108= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %107
	%109 = load i32, i32* %108
	%110 = icmp eq i32 %109, 45
	br i1 %110, label  %111, label  %147

111:
	%112 = phi i32 [%105, %104], [%112, %144]
	%113 = phi i32 [%106, %104], [%113, %144]
	%114 = load i32, i32* @chat
	%115= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %114
	%116 = load i32, i32* %115
	%117 = icmp eq i32 %116, 43
	br i1 %117, label  %144, label  %119

118:
	call void @chapush(i32 45)
	br label  %147

119:
	%120 = load i32, i32* @chat
	%121= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %120
	%122 = load i32, i32* %121
	%123 = icmp eq i32 %122, 45
	br i1 %123, label  %144, label  %124

124:
	%125 = load i32, i32* @chat
	%126= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %125
	%127 = load i32, i32* %126
	%128 = icmp eq i32 %127, 42
	br i1 %128, label  %144, label  %129

129:
	%130 = load i32, i32* @chat
	%131= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %130
	%132 = load i32, i32* %131
	%133 = icmp eq i32 %132, 47
	br i1 %133, label  %144, label  %134

134:
	%135 = load i32, i32* @chat
	%136= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %135
	%137 = load i32, i32* %136
	%138 = icmp eq i32 %137, 37
	br i1 %138, label  %144, label  %139

139:
	%140 = load i32, i32* @chat
	%141= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %140
	%142 = load i32, i32* %141
	%143 = icmp eq i32 %142, 94
	br i1 %143, label  %144, label  %118

144:
	%145 = call i32 @find()
	%146 = icmp eq i32 %145, 0
	br i1 %146, label  %118, label  %111

147:
	%148 = phi i32 [%112, %118], [%105, %104]
	%149 = phi i32 [%113, %118], [%106, %104]
	%150 = load i32, i32* @i
	%151= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %150
	%152 = load i32, i32* %151
	%153 = icmp eq i32 %152, 42
	br i1 %153, label  %154, label  %180

154:
	%155 = phi i32 [%148, %147], [%155, %177]
	%156 = phi i32 [%149, %147], [%156, %177]
	%157 = load i32, i32* @chat
	%158= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %157
	%159 = load i32, i32* %158
	%160 = icmp eq i32 %159, 42
	br i1 %160, label  %177, label  %162

161:
	call void @chapush(i32 42)
	br label  %180

162:
	%163 = load i32, i32* @chat
	%164= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %163
	%165 = load i32, i32* %164
	%166 = icmp eq i32 %165, 47
	br i1 %166, label  %177, label  %167

167:
	%168 = load i32, i32* @chat
	%169= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %168
	%170 = load i32, i32* %169
	%171 = icmp eq i32 %170, 37
	br i1 %171, label  %177, label  %172

172:
	%173 = load i32, i32* @chat
	%174= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %173
	%175 = load i32, i32* %174
	%176 = icmp eq i32 %175, 94
	br i1 %176, label  %177, label  %161

177:
	%178 = call i32 @find()
	%179 = icmp eq i32 %178, 0
	br i1 %179, label  %161, label  %154

180:
	%181 = phi i32 [%155, %161], [%148, %147]
	%182 = phi i32 [%156, %161], [%149, %147]
	%183 = load i32, i32* @i
	%184= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %183
	%185 = load i32, i32* %184
	%186 = icmp eq i32 %185, 47
	br i1 %186, label  %187, label  %213

187:
	%188 = phi i32 [%181, %180], [%188, %210]
	%189 = phi i32 [%182, %180], [%189, %210]
	%190 = load i32, i32* @chat
	%191= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %190
	%192 = load i32, i32* %191
	%193 = icmp eq i32 %192, 42
	br i1 %193, label  %210, label  %195

194:
	call void @chapush(i32 47)
	br label  %213

195:
	%196 = load i32, i32* @chat
	%197= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %196
	%198 = load i32, i32* %197
	%199 = icmp eq i32 %198, 47
	br i1 %199, label  %210, label  %200

200:
	%201 = load i32, i32* @chat
	%202= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %201
	%203 = load i32, i32* %202
	%204 = icmp eq i32 %203, 37
	br i1 %204, label  %210, label  %205

205:
	%206 = load i32, i32* @chat
	%207= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %206
	%208 = load i32, i32* %207
	%209 = icmp eq i32 %208, 94
	br i1 %209, label  %210, label  %194

210:
	%211 = call i32 @find()
	%212 = icmp eq i32 %211, 0
	br i1 %212, label  %194, label  %187

213:
	%214 = phi i32 [%188, %194], [%181, %180]
	%215 = phi i32 [%189, %194], [%182, %180]
	%216 = load i32, i32* @i
	%217= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %216
	%218 = load i32, i32* %217
	%219 = icmp eq i32 %218, 37
	br i1 %219, label  %227, label  %220

220:
	%221 = phi i32 [%228, %234], [%214, %213]
	%222 = phi i32 [%229, %234], [%215, %213]
	%223 = load i32, i32* @ii
	%224= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %223
	store i32 32, i32* %224
	%225 = load i32, i32* @ii
	%226 = add i32 %225, 1
	store i32 %226, i32* @ii
	br label  %14

227:
	%228 = phi i32 [%214, %213], [%228, %250]
	%229 = phi i32 [%215, %213], [%229, %250]
	%230 = load i32, i32* @chat
	%231= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %230
	%232 = load i32, i32* %231
	%233 = icmp eq i32 %232, 42
	br i1 %233, label  %250, label  %235

234:
	call void @chapush(i32 37)
	br label  %220

235:
	%236 = load i32, i32* @chat
	%237= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %236
	%238 = load i32, i32* %237
	%239 = icmp eq i32 %238, 47
	br i1 %239, label  %250, label  %240

240:
	%241 = load i32, i32* @chat
	%242= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %241
	%243 = load i32, i32* %242
	%244 = icmp eq i32 %243, 37
	br i1 %244, label  %250, label  %245

245:
	%246 = load i32, i32* @chat
	%247= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %246
	%248 = load i32, i32* %247
	%249 = icmp eq i32 %248, 94
	br i1 %249, label  %250, label  %234

250:
	%251 = call i32 @find()
	%252 = icmp eq i32 %251, 0
	br i1 %252, label  %234, label  %227

253:
	%254 = phi i32 [%254, %257], [%4, %3]
	%255 = load i32, i32* @chat
	%256 = icmp sgt i32 %255, 0
	br i1 %256, label  %257, label  %266

257:
	%258 = call i32 @chapop()
	%259 = load i32, i32* @ii
	%260= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %259
	store i32 32, i32* %260
	%261 = load i32, i32* @ii
	%262 = add i32 %261, 1
	%263= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %262
	store i32 %258, i32* %263
	%264 = load i32, i32* @ii
	%265 = add i32 %264, 2
	store i32 %265, i32* @ii
	br label  %253

266:
	%267 = load i32, i32* @ii
	%268= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %267
	store i32 64, i32* %268
	store i32 1, i32* @i
	br label  %269

269:
	%270 = phi i32 [%254, %266], [%284, %283]
	%271 = load i32, i32* @i
	%272= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %271
	%273 = load i32, i32* %272
	%274 = icmp ne i32 %273, 64
	br i1 %274, label  %278, label  %275

275:
	%276= getelementptr [10000 x i32],[10000 x i32]* @ints, i32 0, i32 1
	%277 = load i32, i32* %276
	call void @putint(i32 %277)
	ret i32 0
278:
	%279 = load i32, i32* @i
	%280= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %279
	%281 = load i32, i32* %280
	%282 = icmp eq i32 %281, 43
	br i1 %282, label  %287, label  %372

283:
	%284 = phi i32 [%337, %336], [%351, %367], [%270, %340]
	%285 = load i32, i32* @i
	%286 = add i32 %285, 1
	store i32 %286, i32* @i
	br label  %269

287:
	%288 = call i32 @intpop()
	%289 = call i32 @intpop()
	%290 = load i32, i32* @i
	%291= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %290
	%292 = load i32, i32* %291
	%293 = icmp eq i32 %292, 43
	br i1 %293, label  %294, label  %296

294:
	%295 = add i32 %288, %289
	br label  %296

296:
	%297 = phi i32 [%295, %294], [%270, %287]
	%298 = load i32, i32* @i
	%299= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %298
	%300 = load i32, i32* %299
	%301 = icmp eq i32 %300, 45
	br i1 %301, label  %302, label  %304

302:
	%303 = sub i32 %289, %288
	br label  %304

304:
	%305 = phi i32 [%303, %302], [%297, %296]
	%306 = load i32, i32* @i
	%307= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %306
	%308 = load i32, i32* %307
	%309 = icmp eq i32 %308, 42
	br i1 %309, label  %310, label  %312

310:
	%311 = mul i32 %288, %289
	br label  %312

312:
	%313 = phi i32 [%311, %310], [%305, %304]
	%314 = load i32, i32* @i
	%315= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %314
	%316 = load i32, i32* %315
	%317 = icmp eq i32 %316, 47
	br i1 %317, label  %318, label  %320

318:
	%319 = sdiv i32 %289, %288
	br label  %320

320:
	%321 = phi i32 [%319, %318], [%313, %312]
	%322 = load i32, i32* @i
	%323= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %322
	%324 = load i32, i32* %323
	%325 = icmp eq i32 %324, 37
	br i1 %325, label  %326, label  %330

326:
	%327 = sdiv i32 %289, %288
	%328 = mul i32 %327, %288
	%329 = sub i32 %289, %328
	br label  %330

330:
	%331 = phi i32 [%329, %326], [%321, %320]
	%332 = load i32, i32* @i
	%333= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %332
	%334 = load i32, i32* %333
	%335 = icmp eq i32 %334, 94
	br i1 %335, label  %338, label  %336

336:
	%337 = phi i32 [%339, %338], [%331, %330]
	call void @intpush(i32 %337)
	br label  %283

338:
	%339 = call i32 @power(i32 %289, i32 %288)
	br label  %336

340:
	%341 = load i32, i32* @i
	%342= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %341
	%343 = load i32, i32* %342
	%344 = icmp ne i32 %343, 32
	br i1 %344, label  %345, label  %283

345:
	%346 = load i32, i32* @i
	%347= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %346
	%348 = load i32, i32* %347
	%349 = sub i32 %348, 48
	call void @intpush(i32 %349)
	store i32 1, i32* @ii
	br label  %350

350:
	%351 = phi i32 [%270, %345], [%351, %358]
	%352 = load i32, i32* @i
	%353 = load i32, i32* @ii
	%354 = add i32 %352, %353
	%355= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %354
	%356 = load i32, i32* %355
	%357 = icmp ne i32 %356, 32
	br i1 %357, label  %358, label  %367

358:
	%359 = load i32, i32* @i
	%360 = load i32, i32* @ii
	%361 = add i32 %359, %360
	%362= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %361
	%363 = load i32, i32* %362
	%364 = sub i32 %363, 48
	call void @intadd(i32 %364)
	%365 = load i32, i32* @ii
	%366 = add i32 %365, 1
	store i32 %366, i32* @ii
	br label  %350

367:
	%368 = load i32, i32* @i
	%369 = load i32, i32* @ii
	%370 = add i32 %368, %369
	%371 = sub i32 %370, 1
	store i32 %371, i32* @i
	br label  %283

372:
	%373 = load i32, i32* @i
	%374= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %373
	%375 = load i32, i32* %374
	%376 = icmp eq i32 %375, 45
	br i1 %376, label  %287, label  %377

377:
	%378 = load i32, i32* @i
	%379= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %378
	%380 = load i32, i32* %379
	%381 = icmp eq i32 %380, 42
	br i1 %381, label  %287, label  %382

382:
	%383 = load i32, i32* @i
	%384= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %383
	%385 = load i32, i32* %384
	%386 = icmp eq i32 %385, 47
	br i1 %386, label  %287, label  %387

387:
	%388 = load i32, i32* @i
	%389= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %388
	%390 = load i32, i32* %389
	%391 = icmp eq i32 %390, 37
	br i1 %391, label  %287, label  %392

392:
	%393 = load i32, i32* @i
	%394= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %393
	%395 = load i32, i32* %394
	%396 = icmp eq i32 %395, 94
	br i1 %396, label  %287, label  %340

}
