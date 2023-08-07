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
@sum = dso_local global i32 0
@array = dso_local global [20 x i32] zeroinitializer 
define dso_local i32 @f(i32 %0, i32 %1){
	%3 = load i32, i32* @sum
	%4 = add i32 %3, 1
	store i32 %4, i32* @sum
	%5 = icmp sge i32 %0, %1
	br i1 %5, label  %9, label  %10

6:
	%7= getelementptr [20 x i32],[20 x i32]* @array, i32 0, i32 %0
	store i32 1, i32* %7
	%8 = icmp eq i32 %0, 0
	br i1 %8, label  %12, label  %15

9:
	ret i32 0
10:
	%11 = icmp sge i32 %0, 20
	br i1 %11, label  %9, label  %6

12:
	%13= getelementptr [20 x i32],[20 x i32]* @array, i32 0, i32 0
	%14 = load i32, i32* %13
	ret i32 %14
15:
	%16 = sub i32 %0, 1
	%17= getelementptr [20 x i32],[20 x i32]* @array, i32 0, i32 %16
	%18 = load i32, i32* %17
	ret i32 %18
}
define dso_local i32 @g(i32 %0, i32 %1){
	%3 = load i32, i32* @sum
	%4 = add i32 %3, 2
	store i32 %4, i32* @sum
	%5 = icmp sge i32 %0, %1
	br i1 %5, label  %9, label  %10

6:
	%7= getelementptr [20 x i32],[20 x i32]* @array, i32 0, i32 %0
	store i32 0, i32* %7
	%8 = icmp eq i32 %0, 0
	br i1 %8, label  %12, label  %15

9:
	ret i32 1
10:
	%11 = icmp sge i32 %0, 20
	br i1 %11, label  %9, label  %6

12:
	%13= getelementptr [20 x i32],[20 x i32]* @array, i32 0, i32 0
	%14 = load i32, i32* %13
	ret i32 %14
15:
	%16 = sub i32 %0, 1
	%17= getelementptr [20 x i32],[20 x i32]* @array, i32 0, i32 %16
	%18 = load i32, i32* %17
	ret i32 %18
}
define dso_local i32 @h(i32 %0){
	%2 = load i32, i32* @sum
	%3 = add i32 %2, 3
	store i32 %3, i32* @sum
	%4 = icmp slt i32 %0, 0
	br i1 %4, label  %8, label  %9

5:
	%6= getelementptr [20 x i32],[20 x i32]* @array, i32 0, i32 %0
	%7 = load i32, i32* %6
	ret i32 %7
8:
	ret i32 0
9:
	%10 = icmp sge i32 %0, 20
	br i1 %10, label  %8, label  %5

}
define dso_local i32 @main(){
	br label  %1

1:
	%2 = phi i32 [0, %0], [%9, %8]
	%3 = icmp slt i32 %2, 20
	br i1 %3, label  %5, label  %4

4:
	br label  %67

5:
	%6 = call i32 @f(i32 0, i32 %2)
	%7 = icmp ne i32 %6, 0
	br i1 %7, label  %10, label  %8

8:
	%9 = add i32 %2, 1
	br label  %1

10:
	%11 = call i32 @f(i32 1, i32 %2)
	%12 = icmp ne i32 %11, 0
	br i1 %12, label  %13, label  %8

13:
	%14 = call i32 @f(i32 2, i32 %2)
	%15 = icmp ne i32 %14, 0
	br i1 %15, label  %16, label  %8

16:
	%17 = call i32 @f(i32 3, i32 %2)
	%18 = icmp ne i32 %17, 0
	br i1 %18, label  %19, label  %8

19:
	%20 = call i32 @f(i32 4, i32 %2)
	%21 = icmp ne i32 %20, 0
	br i1 %21, label  %22, label  %8

22:
	%23 = call i32 @f(i32 5, i32 %2)
	%24 = icmp ne i32 %23, 0
	br i1 %24, label  %25, label  %8

25:
	%26 = call i32 @f(i32 6, i32 %2)
	%27 = icmp ne i32 %26, 0
	br i1 %27, label  %28, label  %8

28:
	%29 = call i32 @f(i32 7, i32 %2)
	%30 = icmp ne i32 %29, 0
	br i1 %30, label  %31, label  %8

31:
	%32 = call i32 @f(i32 8, i32 %2)
	%33 = icmp ne i32 %32, 0
	br i1 %33, label  %34, label  %8

34:
	%35 = call i32 @f(i32 9, i32 %2)
	%36 = icmp ne i32 %35, 0
	br i1 %36, label  %37, label  %8

37:
	%38 = call i32 @f(i32 10, i32 %2)
	%39 = icmp ne i32 %38, 0
	br i1 %39, label  %40, label  %8

40:
	%41 = call i32 @f(i32 11, i32 %2)
	%42 = icmp ne i32 %41, 0
	br i1 %42, label  %43, label  %8

43:
	%44 = call i32 @f(i32 12, i32 %2)
	%45 = icmp ne i32 %44, 0
	br i1 %45, label  %46, label  %8

46:
	%47 = call i32 @f(i32 13, i32 %2)
	%48 = icmp ne i32 %47, 0
	br i1 %48, label  %49, label  %8

49:
	%50 = call i32 @f(i32 14, i32 %2)
	%51 = icmp ne i32 %50, 0
	br i1 %51, label  %52, label  %8

52:
	%53 = call i32 @f(i32 15, i32 %2)
	%54 = icmp ne i32 %53, 0
	br i1 %54, label  %55, label  %8

55:
	%56 = call i32 @f(i32 16, i32 %2)
	%57 = icmp ne i32 %56, 0
	br i1 %57, label  %58, label  %8

58:
	%59 = call i32 @f(i32 17, i32 %2)
	%60 = icmp ne i32 %59, 0
	br i1 %60, label  %61, label  %8

61:
	%62 = call i32 @f(i32 18, i32 %2)
	%63 = icmp ne i32 %62, 0
	br i1 %63, label  %64, label  %8

64:
	%65 = call i32 @f(i32 19, i32 %2)
	%66 = icmp ne i32 %65, 0
	br label  %8

67:
	%68 = phi i32 [0, %4], [%75, %74]
	%69 = icmp slt i32 %68, 20
	br i1 %69, label  %71, label  %70

70:
	br label  %133

71:
	%72 = call i32 @g(i32 0, i32 %68)
	%73 = icmp ne i32 %72, 0
	br i1 %73, label  %74, label  %76

74:
	%75 = add i32 %68, 1
	br label  %67

76:
	%77 = call i32 @g(i32 1, i32 %68)
	%78 = icmp ne i32 %77, 0
	br i1 %78, label  %74, label  %79

79:
	%80 = call i32 @g(i32 2, i32 %68)
	%81 = icmp ne i32 %80, 0
	br i1 %81, label  %74, label  %82

82:
	%83 = call i32 @g(i32 3, i32 %68)
	%84 = icmp ne i32 %83, 0
	br i1 %84, label  %74, label  %85

85:
	%86 = call i32 @g(i32 4, i32 %68)
	%87 = icmp ne i32 %86, 0
	br i1 %87, label  %74, label  %88

88:
	%89 = call i32 @g(i32 5, i32 %68)
	%90 = icmp ne i32 %89, 0
	br i1 %90, label  %74, label  %91

91:
	%92 = call i32 @g(i32 6, i32 %68)
	%93 = icmp ne i32 %92, 0
	br i1 %93, label  %74, label  %94

94:
	%95 = call i32 @g(i32 7, i32 %68)
	%96 = icmp ne i32 %95, 0
	br i1 %96, label  %74, label  %97

97:
	%98 = call i32 @g(i32 8, i32 %68)
	%99 = icmp ne i32 %98, 0
	br i1 %99, label  %74, label  %100

100:
	%101 = call i32 @g(i32 9, i32 %68)
	%102 = icmp ne i32 %101, 0
	br i1 %102, label  %74, label  %103

103:
	%104 = call i32 @g(i32 10, i32 %68)
	%105 = icmp ne i32 %104, 0
	br i1 %105, label  %74, label  %106

106:
	%107 = call i32 @g(i32 11, i32 %68)
	%108 = icmp ne i32 %107, 0
	br i1 %108, label  %74, label  %109

109:
	%110 = call i32 @g(i32 12, i32 %68)
	%111 = icmp ne i32 %110, 0
	br i1 %111, label  %74, label  %112

112:
	%113 = call i32 @g(i32 13, i32 %68)
	%114 = icmp ne i32 %113, 0
	br i1 %114, label  %74, label  %115

115:
	%116 = call i32 @g(i32 14, i32 %68)
	%117 = icmp ne i32 %116, 0
	br i1 %117, label  %74, label  %118

118:
	%119 = call i32 @g(i32 15, i32 %68)
	%120 = icmp ne i32 %119, 0
	br i1 %120, label  %74, label  %121

121:
	%122 = call i32 @g(i32 16, i32 %68)
	%123 = icmp ne i32 %122, 0
	br i1 %123, label  %74, label  %124

124:
	%125 = call i32 @g(i32 17, i32 %68)
	%126 = icmp ne i32 %125, 0
	br i1 %126, label  %74, label  %127

127:
	%128 = call i32 @g(i32 18, i32 %68)
	%129 = icmp ne i32 %128, 0
	br i1 %129, label  %74, label  %130

130:
	%131 = call i32 @g(i32 19, i32 %68)
	%132 = icmp ne i32 %131, 0
	br label  %74

133:
	%134 = phi i32 [1, %70], [%137, %136]
	%135 = icmp slt i32 %134, 20
	br i1 %135, label  %141, label  %138

136:
	%137 = add i32 %134, 1
	br label  %133

138:
	%139 = call i32 @h(i32 0)
	%140 = icmp ne i32 %139, 0
	br i1 %140, label  %155, label  %152

141:
	%142 = sub i32 %134, 1
	%143 = call i32 @f(i32 %142, i32 %134)
	%144 = icmp ne i32 %143, 0
	br i1 %144, label  %136, label  %138

145:
	%146 = phi i32 [1, %151], [0, %158]
	%147 = load i32, i32* @sum
	%148 = add i32 %147, %146
	%149 = call i32 @h(i32 4)
	%150 = icmp eq i32 0, %149
	br i1 %150, label  %167, label  %168

151:
	br label  %145

152:
	%153 = call i32 @h(i32 2)
	%154 = icmp eq i32 0, %153
	br i1 %154, label  %151, label  %158

155:
	%156 = call i32 @h(i32 1)
	%157 = icmp ne i32 %156, 0
	br i1 %157, label  %151, label  %152

158:
	%159 = call i32 @h(i32 3)
	%160 = icmp ne i32 %159, 0
	br i1 %160, label  %151, label  %145

161:
	%162 = phi i32 [1, %167], [0, %171]
	%163 = load i32, i32* @sum
	%164 = mul i32 %163, %162
	%165 = call i32 @h(i32 9)
	%166 = icmp ne i32 %165, 0
	br i1 %166, label  %190, label  %187

167:
	br label  %161

168:
	%169 = call i32 @h(i32 5)
	%170 = icmp ne i32 %169, 0
	br i1 %170, label  %174, label  %171

171:
	%172 = call i32 @h(i32 8)
	%173 = icmp eq i32 0, %172
	br i1 %173, label  %167, label  %161

174:
	%175 = call i32 @h(i32 6)
	%176 = icmp eq i32 0, %175
	br i1 %176, label  %177, label  %171

177:
	%178 = call i32 @h(i32 7)
	%179 = icmp ne i32 %178, 0
	br i1 %179, label  %167, label  %171

180:
	%181 = phi i32 [1, %186], [0, %199], [0, %202]
	%182 = load i32, i32* @sum
	%183 = sub i32 %182, %181
	%184 = call i32 @h(i32 0)
	%185 = icmp ne i32 %184, 0
	br i1 %185, label  %213, label  %210

186:
	br label  %180

187:
	%188 = call i32 @h(i32 11)
	%189 = icmp eq i32 0, %188
	br i1 %189, label  %186, label  %193

190:
	%191 = call i32 @h(i32 10)
	%192 = icmp eq i32 0, %191
	br i1 %192, label  %186, label  %187

193:
	%194 = call i32 @h(i32 12)
	%195 = icmp eq i32 0, %194
	br i1 %195, label  %186, label  %196

196:
	%197 = call i32 @h(i32 13)
	%198 = icmp eq i32 0, %197
	br i1 %198, label  %186, label  %199

199:
	%200 = call i32 @h(i32 14)
	%201 = icmp ne i32 %200, 0
	br i1 %201, label  %202, label  %180

202:
	%203 = call i32 @h(i32 15)
	%204 = icmp ne i32 %203, 0
	br i1 %204, label  %186, label  %180

205:
	%206 = phi i32 [1, %209], [0, %225]
	%207 = load i32, i32* @sum
	%208 = add i32 %207, %206
	call void @putint(i32 %208)
	ret i32 0
209:
	br label  %205

210:
	%211 = call i32 @h(i32 5)
	%212 = icmp ne i32 %211, 0
	br i1 %212, label  %209, label  %222

213:
	%214 = call i32 @h(i32 2)
	%215 = icmp ne i32 %214, 0
	br i1 %215, label  %216, label  %210

216:
	%217 = call i32 @h(i32 3)
	%218 = icmp eq i32 0, %217
	br i1 %218, label  %219, label  %210

219:
	%220 = call i32 @h(i32 4)
	%221 = icmp eq i32 0, %220
	br i1 %221, label  %209, label  %210

222:
	%223 = call i32 @h(i32 6)
	%224 = icmp ne i32 %223, 0
	br i1 %224, label  %228, label  %225

225:
	%226 = call i32 @h(i32 8)
	%227 = icmp ne i32 %226, 0
	br i1 %227, label  %209, label  %205

228:
	%229 = call i32 @h(i32 7)
	%230 = icmp eq i32 0, %229
	br i1 %230, label  %209, label  %225

}
