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
	%2 = alloca i32
	store i32 %0, i32* %2
	%3 = load i32, i32* %2
	%4 = icmp sge i32 %3, 48
	br i1 %4, label  %7, label  %5

5:
	ret i32 0
6:
	ret i32 1
7:
	%8 = load i32, i32* %2
	%9 = icmp sle i32 %8, 57
	br i1 %9, label  %6, label  %5

}
define dso_local i32 @power(i32 %0, i32 %1){
	%3 = alloca i32
	%4 = alloca i32
	%5 = alloca i32
	store i32 %0, i32* %5
	store i32 %1, i32* %4
	store i32 1, i32* %3
	br label  %6

6:
	%7 = load i32, i32* %4
	%8 = icmp ne i32 %7, 0
	br i1 %8, label  %9, label  %15

9:
	%10 = load i32, i32* %3
	%11 = load i32, i32* %5
	%12 = mul i32 %10, %11
	store i32 %12, i32* %3
	%13 = load i32, i32* %4
	%14 = sub i32 %13, 1
	store i32 %14, i32* %4
	br label  %6

15:
	%16 = load i32, i32* %3
	ret i32 %16
}
define dso_local i32 @getstr(i32* %0){
	%2 = alloca i32
	%3 = alloca i32
	%4 = alloca i32*
	store i32* %0, i32** %4
	%5 = call i32 @getch()
	store i32 %5, i32* %3
	store i32 0, i32* %2
	br label  %6

6:
	%7 = load i32, i32* %3
	%8 = icmp ne i32 %7, 13
	br i1 %8, label  %19, label  %17

9:
	%10 = load i32*, i32** %4
	%11 = load i32, i32* %2
	%12= getelementptr i32,i32* %10, i32 %11
	%13 = load i32, i32* %3
	store i32 %13, i32* %12
	%14 = load i32, i32* %2
	%15 = add i32 %14, 1
	store i32 %15, i32* %2
	%16 = call i32 @getch()
	store i32 %16, i32* %3
	br label  %6

17:
	%18 = load i32, i32* %2
	ret i32 %18
19:
	%20 = load i32, i32* %3
	%21 = icmp ne i32 %20, 10
	br i1 %21, label  %9, label  %17

}
define dso_local void @intpush(i32 %0){
	%2 = alloca i32
	store i32 %0, i32* %2
	%3 = load i32, i32* @intt
	%4 = add i32 %3, 1
	store i32 %4, i32* @intt
	%5 = load i32, i32* @intt
	%6= getelementptr [10000 x i32],[10000 x i32]* @ints, i32 0, i32 %5
	%7 = load i32, i32* %2
	store i32 %7, i32* %6
	ret void 
}
define dso_local void @chapush(i32 %0){
	%2 = alloca i32
	store i32 %0, i32* %2
	%3 = load i32, i32* @chat
	%4 = add i32 %3, 1
	store i32 %4, i32* @chat
	%5 = load i32, i32* @chat
	%6= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %5
	%7 = load i32, i32* %2
	store i32 %7, i32* %6
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
	%2 = alloca i32
	store i32 %0, i32* %2
	%3 = load i32, i32* @intt
	%4= getelementptr [10000 x i32],[10000 x i32]* @ints, i32 0, i32 %3
	%5 = load i32, i32* @intt
	%6= getelementptr [10000 x i32],[10000 x i32]* @ints, i32 0, i32 %5
	%7 = load i32, i32* %6
	%8 = mul i32 %7, 10
	store i32 %8, i32* %4
	%9 = load i32, i32* @intt
	%10= getelementptr [10000 x i32],[10000 x i32]* @ints, i32 0, i32 %9
	%11 = load i32, i32* @intt
	%12= getelementptr [10000 x i32],[10000 x i32]* @ints, i32 0, i32 %11
	%13 = load i32, i32* %12
	%14 = load i32, i32* %2
	%15 = add i32 %13, %14
	store i32 %15, i32* %10
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
	%1 = alloca i32
	%2 = alloca i32
	%3 = alloca i32
	%4 = alloca i32
	%5 = alloca i32
	store i32 0, i32* @intt
	store i32 0, i32* @chat
	%6= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 0
	%7 = call i32 @getstr(i32* %6)
	store i32 %7, i32* %5
	br label  %8

8:
	%9 = load i32, i32* @i
	%10 = load i32, i32* %5
	%11 = icmp slt i32 %9, %10
	br i1 %11, label  %12, label  %231

12:
	%13 = load i32, i32* @i
	%14= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %13
	%15 = load i32, i32* %14
	%16 = call i32 @isdigit(i32 %15)
	%17 = icmp eq i32 %16, 1
	br i1 %17, label  %21, label  %29

18:
	%19 = load i32, i32* @i
	%20 = add i32 %19, 1
	store i32 %20, i32* @i
	br label  %8

21:
	%22 = load i32, i32* @ii
	%23= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %22
	%24 = load i32, i32* @i
	%25= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %24
	%26 = load i32, i32* %25
	store i32 %26, i32* %23
	%27 = load i32, i32* @ii
	%28 = add i32 %27, 1
	store i32 %28, i32* @ii
	br label  %18

29:
	%30 = load i32, i32* @i
	%31= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %30
	%32 = load i32, i32* %31
	%33 = icmp eq i32 %32, 40
	br i1 %33, label  %34, label  %35

34:
	call void @chapush(i32 40)
	br label  %35

35:
	%36 = load i32, i32* @i
	%37= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %36
	%38 = load i32, i32* %37
	%39 = icmp eq i32 %38, 94
	br i1 %39, label  %40, label  %41

40:
	call void @chapush(i32 94)
	br label  %41

41:
	%42 = load i32, i32* @i
	%43= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %42
	%44 = load i32, i32* %43
	%45 = icmp eq i32 %44, 41
	br i1 %45, label  %46, label  %61

46:
	%47 = call i32 @chapop()
	store i32 %47, i32* @c
	br label  %48

48:
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
	br label  %48

61:
	%62 = load i32, i32* @i
	%63= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %62
	%64 = load i32, i32* %63
	%65 = icmp eq i32 %64, 43
	br i1 %65, label  %66, label  %100

66:
	%67 = load i32, i32* @chat
	%68= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %67
	%69 = load i32, i32* %68
	%70 = icmp eq i32 %69, 43
	br i1 %70, label  %97, label  %72

71:
	call void @chapush(i32 43)
	br label  %100

72:
	%73 = load i32, i32* @chat
	%74= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %73
	%75 = load i32, i32* %74
	%76 = icmp eq i32 %75, 45
	br i1 %76, label  %97, label  %77

77:
	%78 = load i32, i32* @chat
	%79= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %78
	%80 = load i32, i32* %79
	%81 = icmp eq i32 %80, 42
	br i1 %81, label  %97, label  %82

82:
	%83 = load i32, i32* @chat
	%84= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %83
	%85 = load i32, i32* %84
	%86 = icmp eq i32 %85, 47
	br i1 %86, label  %97, label  %87

87:
	%88 = load i32, i32* @chat
	%89= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %88
	%90 = load i32, i32* %89
	%91 = icmp eq i32 %90, 37
	br i1 %91, label  %97, label  %92

92:
	%93 = load i32, i32* @chat
	%94= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %93
	%95 = load i32, i32* %94
	%96 = icmp eq i32 %95, 94
	br i1 %96, label  %97, label  %71

97:
	%98 = call i32 @find()
	%99 = icmp eq i32 %98, 0
	br i1 %99, label  %71, label  %66

100:
	%101 = load i32, i32* @i
	%102= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %101
	%103 = load i32, i32* %102
	%104 = icmp eq i32 %103, 45
	br i1 %104, label  %105, label  %139

105:
	%106 = load i32, i32* @chat
	%107= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %106
	%108 = load i32, i32* %107
	%109 = icmp eq i32 %108, 43
	br i1 %109, label  %136, label  %111

110:
	call void @chapush(i32 45)
	br label  %139

111:
	%112 = load i32, i32* @chat
	%113= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %112
	%114 = load i32, i32* %113
	%115 = icmp eq i32 %114, 45
	br i1 %115, label  %136, label  %116

116:
	%117 = load i32, i32* @chat
	%118= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %117
	%119 = load i32, i32* %118
	%120 = icmp eq i32 %119, 42
	br i1 %120, label  %136, label  %121

121:
	%122 = load i32, i32* @chat
	%123= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %122
	%124 = load i32, i32* %123
	%125 = icmp eq i32 %124, 47
	br i1 %125, label  %136, label  %126

126:
	%127 = load i32, i32* @chat
	%128= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %127
	%129 = load i32, i32* %128
	%130 = icmp eq i32 %129, 37
	br i1 %130, label  %136, label  %131

131:
	%132 = load i32, i32* @chat
	%133= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %132
	%134 = load i32, i32* %133
	%135 = icmp eq i32 %134, 94
	br i1 %135, label  %136, label  %110

136:
	%137 = call i32 @find()
	%138 = icmp eq i32 %137, 0
	br i1 %138, label  %110, label  %105

139:
	%140 = load i32, i32* @i
	%141= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %140
	%142 = load i32, i32* %141
	%143 = icmp eq i32 %142, 42
	br i1 %143, label  %144, label  %168

144:
	%145 = load i32, i32* @chat
	%146= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %145
	%147 = load i32, i32* %146
	%148 = icmp eq i32 %147, 42
	br i1 %148, label  %165, label  %150

149:
	call void @chapush(i32 42)
	br label  %168

150:
	%151 = load i32, i32* @chat
	%152= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %151
	%153 = load i32, i32* %152
	%154 = icmp eq i32 %153, 47
	br i1 %154, label  %165, label  %155

155:
	%156 = load i32, i32* @chat
	%157= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %156
	%158 = load i32, i32* %157
	%159 = icmp eq i32 %158, 37
	br i1 %159, label  %165, label  %160

160:
	%161 = load i32, i32* @chat
	%162= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %161
	%163 = load i32, i32* %162
	%164 = icmp eq i32 %163, 94
	br i1 %164, label  %165, label  %149

165:
	%166 = call i32 @find()
	%167 = icmp eq i32 %166, 0
	br i1 %167, label  %149, label  %144

168:
	%169 = load i32, i32* @i
	%170= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %169
	%171 = load i32, i32* %170
	%172 = icmp eq i32 %171, 47
	br i1 %172, label  %173, label  %197

173:
	%174 = load i32, i32* @chat
	%175= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %174
	%176 = load i32, i32* %175
	%177 = icmp eq i32 %176, 42
	br i1 %177, label  %194, label  %179

178:
	call void @chapush(i32 47)
	br label  %197

179:
	%180 = load i32, i32* @chat
	%181= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %180
	%182 = load i32, i32* %181
	%183 = icmp eq i32 %182, 47
	br i1 %183, label  %194, label  %184

184:
	%185 = load i32, i32* @chat
	%186= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %185
	%187 = load i32, i32* %186
	%188 = icmp eq i32 %187, 37
	br i1 %188, label  %194, label  %189

189:
	%190 = load i32, i32* @chat
	%191= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %190
	%192 = load i32, i32* %191
	%193 = icmp eq i32 %192, 94
	br i1 %193, label  %194, label  %178

194:
	%195 = call i32 @find()
	%196 = icmp eq i32 %195, 0
	br i1 %196, label  %178, label  %173

197:
	%198 = load i32, i32* @i
	%199= getelementptr [10000 x i32],[10000 x i32]* @get, i32 0, i32 %198
	%200 = load i32, i32* %199
	%201 = icmp eq i32 %200, 37
	br i1 %201, label  %207, label  %202

202:
	%203 = load i32, i32* @ii
	%204= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %203
	store i32 32, i32* %204
	%205 = load i32, i32* @ii
	%206 = add i32 %205, 1
	store i32 %206, i32* @ii
	br label  %18

207:
	%208 = load i32, i32* @chat
	%209= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %208
	%210 = load i32, i32* %209
	%211 = icmp eq i32 %210, 42
	br i1 %211, label  %228, label  %213

212:
	call void @chapush(i32 37)
	br label  %202

213:
	%214 = load i32, i32* @chat
	%215= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %214
	%216 = load i32, i32* %215
	%217 = icmp eq i32 %216, 47
	br i1 %217, label  %228, label  %218

218:
	%219 = load i32, i32* @chat
	%220= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %219
	%221 = load i32, i32* %220
	%222 = icmp eq i32 %221, 37
	br i1 %222, label  %228, label  %223

223:
	%224 = load i32, i32* @chat
	%225= getelementptr [10000 x i32],[10000 x i32]* @chas, i32 0, i32 %224
	%226 = load i32, i32* %225
	%227 = icmp eq i32 %226, 94
	br i1 %227, label  %228, label  %212

228:
	%229 = call i32 @find()
	%230 = icmp eq i32 %229, 0
	br i1 %230, label  %212, label  %207

231:
	%232 = load i32, i32* @chat
	%233 = icmp sgt i32 %232, 0
	br i1 %233, label  %234, label  %244

234:
	%235 = call i32 @chapop()
	store i32 %235, i32* %4
	%236 = load i32, i32* @ii
	%237= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %236
	store i32 32, i32* %237
	%238 = load i32, i32* @ii
	%239 = add i32 %238, 1
	%240= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %239
	%241 = load i32, i32* %4
	store i32 %241, i32* %240
	%242 = load i32, i32* @ii
	%243 = add i32 %242, 2
	store i32 %243, i32* @ii
	br label  %231

244:
	%245 = load i32, i32* @ii
	%246= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %245
	store i32 64, i32* %246
	store i32 1, i32* @i
	br label  %247

247:
	%248 = load i32, i32* @i
	%249= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %248
	%250 = load i32, i32* %249
	%251 = icmp ne i32 %250, 64
	br i1 %251, label  %255, label  %252

252:
	%253= getelementptr [10000 x i32],[10000 x i32]* @ints, i32 0, i32 1
	%254 = load i32, i32* %253
	call void @putint(i32 %254)
	ret i32 0
255:
	%256 = load i32, i32* @i
	%257= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %256
	%258 = load i32, i32* %257
	%259 = icmp eq i32 %258, 43
	br i1 %259, label  %263, label  %354

260:
	%261 = load i32, i32* @i
	%262 = add i32 %261, 1
	store i32 %262, i32* @i
	br label  %247

263:
	%264 = call i32 @intpop()
	store i32 %264, i32* %3
	%265 = call i32 @intpop()
	store i32 %265, i32* %2
	%266 = load i32, i32* @i
	%267= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %266
	%268 = load i32, i32* %267
	%269 = icmp eq i32 %268, 43
	br i1 %269, label  %270, label  %274

270:
	%271 = load i32, i32* %3
	%272 = load i32, i32* %2
	%273 = add i32 %271, %272
	store i32 %273, i32* %1
	br label  %274

274:
	%275 = load i32, i32* @i
	%276= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %275
	%277 = load i32, i32* %276
	%278 = icmp eq i32 %277, 45
	br i1 %278, label  %279, label  %283

279:
	%280 = load i32, i32* %2
	%281 = load i32, i32* %3
	%282 = sub i32 %280, %281
	store i32 %282, i32* %1
	br label  %283

283:
	%284 = load i32, i32* @i
	%285= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %284
	%286 = load i32, i32* %285
	%287 = icmp eq i32 %286, 42
	br i1 %287, label  %288, label  %292

288:
	%289 = load i32, i32* %3
	%290 = load i32, i32* %2
	%291 = mul i32 %289, %290
	store i32 %291, i32* %1
	br label  %292

292:
	%293 = load i32, i32* @i
	%294= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %293
	%295 = load i32, i32* %294
	%296 = icmp eq i32 %295, 47
	br i1 %296, label  %297, label  %301

297:
	%298 = load i32, i32* %2
	%299 = load i32, i32* %3
	%300 = sdiv i32 %298, %299
	store i32 %300, i32* %1
	br label  %301

301:
	%302 = load i32, i32* @i
	%303= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %302
	%304 = load i32, i32* %303
	%305 = icmp eq i32 %304, 37
	br i1 %305, label  %306, label  %312

306:
	%307 = load i32, i32* %2
	%308 = load i32, i32* %3
	%309 = sdiv i32 %307, %308
	%310 = mul i32 %309, %308
	%311 = sub i32 %307, %310
	store i32 %311, i32* %1
	br label  %312

312:
	%313 = load i32, i32* @i
	%314= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %313
	%315 = load i32, i32* %314
	%316 = icmp eq i32 %315, 94
	br i1 %316, label  %319, label  %317

317:
	%318 = load i32, i32* %1
	call void @intpush(i32 %318)
	br label  %260

319:
	%320 = load i32, i32* %2
	%321 = load i32, i32* %3
	%322 = call i32 @power(i32 %320, i32 %321)
	store i32 %322, i32* %1
	br label  %317

323:
	%324 = load i32, i32* @i
	%325= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %324
	%326 = load i32, i32* %325
	%327 = icmp ne i32 %326, 32
	br i1 %327, label  %328, label  %260

328:
	%329 = load i32, i32* @i
	%330= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %329
	%331 = load i32, i32* %330
	%332 = sub i32 %331, 48
	call void @intpush(i32 %332)
	store i32 1, i32* @ii
	br label  %333

333:
	%334 = load i32, i32* @i
	%335 = load i32, i32* @ii
	%336 = add i32 %334, %335
	%337= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %336
	%338 = load i32, i32* %337
	%339 = icmp ne i32 %338, 32
	br i1 %339, label  %340, label  %349

340:
	%341 = load i32, i32* @i
	%342 = load i32, i32* @ii
	%343 = add i32 %341, %342
	%344= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %343
	%345 = load i32, i32* %344
	%346 = sub i32 %345, 48
	call void @intadd(i32 %346)
	%347 = load i32, i32* @ii
	%348 = add i32 %347, 1
	store i32 %348, i32* @ii
	br label  %333

349:
	%350 = load i32, i32* @i
	%351 = load i32, i32* @ii
	%352 = add i32 %350, %351
	%353 = sub i32 %352, 1
	store i32 %353, i32* @i
	br label  %260

354:
	%355 = load i32, i32* @i
	%356= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %355
	%357 = load i32, i32* %356
	%358 = icmp eq i32 %357, 45
	br i1 %358, label  %263, label  %359

359:
	%360 = load i32, i32* @i
	%361= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %360
	%362 = load i32, i32* %361
	%363 = icmp eq i32 %362, 42
	br i1 %363, label  %263, label  %364

364:
	%365 = load i32, i32* @i
	%366= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %365
	%367 = load i32, i32* %366
	%368 = icmp eq i32 %367, 47
	br i1 %368, label  %263, label  %369

369:
	%370 = load i32, i32* @i
	%371= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %370
	%372 = load i32, i32* %371
	%373 = icmp eq i32 %372, 37
	br i1 %373, label  %263, label  %374

374:
	%375 = load i32, i32* @i
	%376= getelementptr [10000 x i32],[10000 x i32]* @get2, i32 0, i32 %375
	%377 = load i32, i32* %376
	%378 = icmp eq i32 %377, 94
	br i1 %378, label  %263, label  %323

}
