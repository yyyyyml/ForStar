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
@a = dso_local global [30000010 x i32] zeroinitializer 
@ans = dso_local global i32 0
define dso_local void @radixSort(i32 %0, i32* %1, i32 %2, i32 %3){
	%5 = alloca [16 x i32]
	%6 = alloca [16 x i32]
	%7 = alloca [16 x i32]
	%8 = getelementptr [16 x i32],[16 x i32]* %5, i32 0, i32 0
	call void @memset(i32* %8, i32 0, i32 64)
	%9 = getelementptr [16 x i32],[16 x i32]* %6, i32 0, i32 0
	call void @memset(i32* %9, i32 0, i32 64)
	%10 = getelementptr [16 x i32],[16 x i32]* %7, i32 0, i32 0
	call void @memset(i32* %10, i32 0, i32 64)
	%11 = icmp eq i32 %0, -1
	br i1 %11, label  %13, label  %14

12:
	br label  %17

13:
	ret void 
14:
	%15 = add i32 %2, 1
	%16 = icmp sge i32 %15, %3
	br i1 %16, label  %13, label  %12

17:
	%18 = phi i32 [%2, %12], [%53, %49]
	%19 = icmp slt i32 %18, %3
	br i1 %19, label  %20, label  %54

20:
	%21 = getelementptr i32,i32* %1, i32 %18
	%22 = load i32, i32* %21
	br label  %23

23:
	%24 = phi i32 [%22, %20], [%28, %27]
	%25 = phi i32 [0, %20], [%29, %27]
	%26 = icmp slt i32 %25, %0
	br i1 %26, label  %27, label  %30

27:
	%28 = sdiv i32 %24, 16
	%29 = add i32 %25, 1
	br label  %23

30:
	%31 = sdiv i32 %24, 16
	%32 = mul i32 %31, 16
	%33 = sub i32 %24, %32
	br label  %34

34:
	%35 = getelementptr [16 x i32],[16 x i32]* %7, i32 0, i32 %33
	%36 = getelementptr i32,i32* %1, i32 %18
	%37 = load i32, i32* %36
	br label  %38

38:
	%39 = phi i32 [%37, %34], [%43, %42]
	%40 = phi i32 [0, %34], [%44, %42]
	%41 = icmp slt i32 %40, %0
	br i1 %41, label  %42, label  %45

42:
	%43 = sdiv i32 %39, 16
	%44 = add i32 %40, 1
	br label  %38

45:
	%46 = sdiv i32 %39, 16
	%47 = mul i32 %46, 16
	%48 = sub i32 %39, %47
	br label  %49

49:
	%50 = getelementptr [16 x i32],[16 x i32]* %7, i32 0, i32 %48
	%51 = load i32, i32* %50
	%52 = add i32 %51, 1
	store i32 %52, i32* %35
	%53 = add i32 %18, 1
	br label  %17

54:
	%55 = getelementptr [16 x i32],[16 x i32]* %5, i32 0, i32 0
	store i32 %2, i32* %55
	%56 = getelementptr [16 x i32],[16 x i32]* %6, i32 0, i32 0
	%57 = getelementptr [16 x i32],[16 x i32]* %7, i32 0, i32 0
	%58 = load i32, i32* %57
	%59 = add i32 %2, %58
	store i32 %59, i32* %56
	br label  %60

60:
	%61 = phi i32 [1, %54], [%72, %63]
	%62 = icmp slt i32 %61, 16
	br i1 %62, label  %63, label  %73

63:
	%64 = getelementptr [16 x i32],[16 x i32]* %5, i32 0, i32 %61
	%65 = sub i32 %61, 1
	%66 = getelementptr [16 x i32],[16 x i32]* %6, i32 0, i32 %65
	%67 = load i32, i32* %66
	store i32 %67, i32* %64
	%68 = getelementptr [16 x i32],[16 x i32]* %6, i32 0, i32 %61
	%69 = getelementptr [16 x i32],[16 x i32]* %7, i32 0, i32 %61
	%70 = load i32, i32* %69
	%71 = add i32 %67, %70
	store i32 %71, i32* %68
	%72 = add i32 %61, 1
	br label  %60

73:
	br label  %74

74:
	%75 = phi i32 [0, %73], [%95, %94]
	%76 = icmp slt i32 %75, 16
	br i1 %76, label  %83, label  %77

77:
	%78 = getelementptr [16 x i32],[16 x i32]* %5, i32 0, i32 0
	store i32 %2, i32* %78
	%79 = getelementptr [16 x i32],[16 x i32]* %6, i32 0, i32 0
	%80 = getelementptr [16 x i32],[16 x i32]* %7, i32 0, i32 0
	%81 = load i32, i32* %80
	%82 = add i32 %2, %81
	store i32 %82, i32* %79
	br label  %176

83:
	%84 = getelementptr [16 x i32],[16 x i32]* %5, i32 0, i32 %75
	%85 = load i32, i32* %84
	%86 = getelementptr [16 x i32],[16 x i32]* %6, i32 0, i32 %75
	%87 = load i32, i32* %86
	%88 = icmp slt i32 %85, %87
	br i1 %88, label  %89, label  %94

89:
	%90 = getelementptr [16 x i32],[16 x i32]* %5, i32 0, i32 %75
	%91 = load i32, i32* %90
	%92 = getelementptr i32,i32* %1, i32 %91
	%93 = load i32, i32* %92
	br label  %96

94:
	%95 = add i32 %75, 1
	br label  %74

96:
	%97 = phi i32 [%93, %89], [%127, %167]
	br label  %98

98:
	%99 = phi i32 [%97, %96], [%103, %102]
	%100 = phi i32 [0, %96], [%104, %102]
	%101 = icmp slt i32 %100, %0
	br i1 %101, label  %102, label  %105

102:
	%103 = sdiv i32 %99, 16
	%104 = add i32 %100, 1
	br label  %98

105:
	%106 = sdiv i32 %99, 16
	%107 = mul i32 %106, 16
	%108 = sub i32 %99, %107
	br label  %109

109:
	%110 = icmp ne i32 %108, %75
	br i1 %110, label  %111, label  %171

111:
	br label  %112

112:
	%113 = phi i32 [%97, %111], [%117, %116]
	%114 = phi i32 [0, %111], [%118, %116]
	%115 = icmp slt i32 %114, %0
	br i1 %115, label  %116, label  %119

116:
	%117 = sdiv i32 %113, 16
	%118 = add i32 %114, 1
	br label  %112

119:
	%120 = sdiv i32 %113, 16
	%121 = mul i32 %120, 16
	%122 = sub i32 %113, %121
	br label  %123

123:
	%124 = getelementptr [16 x i32],[16 x i32]* %5, i32 0, i32 %122
	%125 = load i32, i32* %124
	%126 = getelementptr i32,i32* %1, i32 %125
	%127 = load i32, i32* %126
	br label  %128

128:
	%129 = phi i32 [%97, %123], [%133, %132]
	%130 = phi i32 [0, %123], [%134, %132]
	%131 = icmp slt i32 %130, %0
	br i1 %131, label  %132, label  %135

132:
	%133 = sdiv i32 %129, 16
	%134 = add i32 %130, 1
	br label  %128

135:
	%136 = sdiv i32 %129, 16
	%137 = mul i32 %136, 16
	%138 = sub i32 %129, %137
	br label  %139

139:
	%140 = getelementptr [16 x i32],[16 x i32]* %5, i32 0, i32 %138
	%141 = load i32, i32* %140
	%142 = getelementptr i32,i32* %1, i32 %141
	store i32 %97, i32* %142
	br label  %143

143:
	%144 = phi i32 [%97, %139], [%148, %147]
	%145 = phi i32 [0, %139], [%149, %147]
	%146 = icmp slt i32 %145, %0
	br i1 %146, label  %147, label  %150

147:
	%148 = sdiv i32 %144, 16
	%149 = add i32 %145, 1
	br label  %143

150:
	%151 = sdiv i32 %144, 16
	%152 = mul i32 %151, 16
	%153 = sub i32 %144, %152
	br label  %154

154:
	%155 = getelementptr [16 x i32],[16 x i32]* %5, i32 0, i32 %153
	br label  %156

156:
	%157 = phi i32 [%97, %154], [%161, %160]
	%158 = phi i32 [0, %154], [%162, %160]
	%159 = icmp slt i32 %158, %0
	br i1 %159, label  %160, label  %163

160:
	%161 = sdiv i32 %157, 16
	%162 = add i32 %158, 1
	br label  %156

163:
	%164 = sdiv i32 %157, 16
	%165 = mul i32 %164, 16
	%166 = sub i32 %157, %165
	br label  %167

167:
	%168 = getelementptr [16 x i32],[16 x i32]* %5, i32 0, i32 %166
	%169 = load i32, i32* %168
	%170 = add i32 %169, 1
	store i32 %170, i32* %155
	br label  %96

171:
	%172 = getelementptr [16 x i32],[16 x i32]* %5, i32 0, i32 %75
	%173 = load i32, i32* %172
	%174 = getelementptr i32,i32* %1, i32 %173
	store i32 %97, i32* %174
	%175 = add i32 %173, 1
	store i32 %175, i32* %172
	br label  %83

176:
	%177 = phi i32 [0, %77], [%188, %182]
	%178 = icmp slt i32 %177, 16
	br i1 %178, label  %180, label  %179

179:
	ret void 
180:
	%181 = icmp sgt i32 %177, 0
	br i1 %181, label  %189, label  %182

182:
	%183 = sub i32 %0, 1
	%184 = getelementptr [16 x i32],[16 x i32]* %5, i32 0, i32 %177
	%185 = load i32, i32* %184
	%186 = getelementptr [16 x i32],[16 x i32]* %6, i32 0, i32 %177
	%187 = load i32, i32* %186
	call void @radixSort(i32 %183, i32* %1, i32 %185, i32 %187)
	%188 = add i32 %177, 1
	br label  %176

189:
	%190 = getelementptr [16 x i32],[16 x i32]* %5, i32 0, i32 %177
	%191 = sub i32 %177, 1
	%192 = getelementptr [16 x i32],[16 x i32]* %6, i32 0, i32 %191
	%193 = load i32, i32* %192
	store i32 %193, i32* %190
	%194 = getelementptr [16 x i32],[16 x i32]* %6, i32 0, i32 %177
	%195 = getelementptr [16 x i32],[16 x i32]* %7, i32 0, i32 %177
	%196 = load i32, i32* %195
	%197 = add i32 %193, %196
	store i32 %197, i32* %194
	br label  %182

}
define dso_local i32 @main(){
	%1 = getelementptr [30000010 x i32],[30000010 x i32]* @a, i32 0, i32 0
	%2 = call i32 @getarray(i32* %1)
	call void @_sysy_starttime(i32 0)
	call void @radixSort(i32 8, i32* %1, i32 0, i32 %2)
	br label  %3

3:
	%4 = phi i32 [0, %0], [%16, %6]
	%5 = icmp slt i32 %4, %2
	br i1 %5, label  %6, label  %17

6:
	%7 = load i32, i32* @ans
	%8 = getelementptr [30000010 x i32],[30000010 x i32]* @a, i32 0, i32 %4
	%9 = load i32, i32* %8
	%10 = add i32 2, %4
	%11 = sdiv i32 %9, %10
	%12 = mul i32 %11, %10
	%13 = sub i32 %9, %12
	%14 = mul i32 %4, %13
	%15 = add i32 %7, %14
	%16 = add i32 %4, 1
	store i32 %15, i32* @ans
	br label  %3

17:
	%18 = load i32, i32* @ans
	%19 = icmp slt i32 %18, 0
	br i1 %19, label  %22, label  %20

20:
	call void @_sysy_stoptime(i32 0)
	%21 = load i32, i32* @ans
	call void @putint(i32 %21)
	call void @putch(i32 10)
	ret i32 0
22:
	%23 = load i32, i32* @ans
	%24 = sub i32 0, %23
	store i32 %24, i32* @ans
	br label  %20

}
