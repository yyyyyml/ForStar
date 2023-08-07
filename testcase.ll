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
@n = dso_local global i32 0
@cnt = dso_local global [400020 x i32] zeroinitializer 
@a = dso_local global [100005 x i32] zeroinitializer 
@b = dso_local global [100005 x i32] zeroinitializer 
@c = dso_local global [100005 x i32] zeroinitializer 
define dso_local i32 @quick_read(){
	%1 = call i32 @getch()
	br label  %2

2:
	%3 = phi i32 [0, %0], [%12, %11]
	%4 = phi i32 [%1, %0], [%13, %11]
	%5 = phi i32 [0, %0], [%5, %11]
	%6 = icmp slt i32 %4, 48
	br i1 %6, label  %9, label  %7

7:
	%8 = icmp sgt i32 %4, 57
	br i1 %8, label  %9, label  %15

9:
	%10 = icmp eq i32 %4, 45
	br i1 %10, label  %14, label  %11

11:
	%12 = phi i32 [1, %14], [%3, %9]
	%13 = call i32 @getch()
	br label  %2

14:
	br label  %11

15:
	%16 = phi i32 [%16, %20], [%3, %7]
	%17 = phi i32 [%24, %20], [%4, %7]
	%18 = phi i32 [%23, %20], [%5, %7]
	%19 = icmp sge i32 %17, 48
	br i1 %19, label  %25, label  %27

20:
	%21 = mul i32 %18, 10
	%22 = add i32 %21, %17
	%23 = sub i32 %22, 48
	%24 = call i32 @getch()
	br label  %15

25:
	%26 = icmp sle i32 %17, 57
	br i1 %26, label  %20, label  %27

27:
	%28 = icmp ne i32 %16, 0
	br i1 %28, label  %29, label  %31

29:
	%30 = sub i32 0, %18
	ret i32 %30
31:
	ret i32 %18
}
define dso_local void @sortA(i32* %0){
	br label  %2

2:
	%3 = phi i32 [0, %1], [%17, %16]
	%4 = phi i32* [%0, %1], [%12, %16]
	%5 = load i32, i32* @n
	%6 = icmp slt i32 %3, %5
	br i1 %6, label  %7, label  %9

7:
	%8 = add i32 %3, 1
	br label  %10

9:
	ret void 
10:
	%11 = phi i32 [%3, %7], [%11, %24]
	%12 = phi i32* [%4, %7], [%12, %24]
	%13 = phi i32 [%8, %7], [%25, %24]
	%14 = load i32, i32* @n
	%15 = icmp slt i32 %13, %14
	br i1 %15, label  %18, label  %16

16:
	%17 = add i32 %11, 1
	br label  %2

18:
	%19= getelementptr i32,i32* %12, i32 %11
	%20 = load i32, i32* %19
	%21= getelementptr i32,i32* %12, i32 %13
	%22 = load i32, i32* %21
	%23 = icmp sgt i32 %20, %22
	br i1 %23, label  %26, label  %24

24:
	%25 = add i32 %13, 1
	br label  %10

26:
	%27= getelementptr i32,i32* %12, i32 %11
	%28 = load i32, i32* %27
	%29= getelementptr i32,i32* %12, i32 %11
	%30= getelementptr i32,i32* %12, i32 %13
	%31 = load i32, i32* %30
	store i32 %31, i32* %29
	%32= getelementptr i32,i32* %12, i32 %13
	store i32 %28, i32* %32
	br label  %24

}
define dso_local void @sortB(i32* %0){
	%2 = sub i32 0, 100
	br label  %3

3:
	%4 = phi i32 [0, %1], [%24, %22]
	%5 = phi i32 [%2, %1], [%23, %22]
	%6 = phi i32* [%0, %1], [%6, %22]
	%7 = load i32, i32* @n
	%8 = icmp slt i32 %4, %7
	br i1 %8, label  %9, label  %21

9:
	%10= getelementptr i32,i32* %6, i32 %4
	%11 = load i32, i32* %10
	%12= getelementptr [400020 x i32],[400020 x i32]* @cnt, i32 0, i32 %11
	%13= getelementptr i32,i32* %6, i32 %4
	%14 = load i32, i32* %13
	%15= getelementptr [400020 x i32],[400020 x i32]* @cnt, i32 0, i32 %14
	%16 = load i32, i32* %15
	%17 = add i32 %16, 1
	store i32 %17, i32* %12
	%18= getelementptr i32,i32* %6, i32 %4
	%19 = load i32, i32* %18
	%20 = icmp sgt i32 %19, %5
	br i1 %20, label  %25, label  %22

21:
	br label  %28

22:
	%23 = phi i32 [%27, %25], [%5, %9]
	%24 = add i32 %4, 1
	br label  %3

25:
	%26= getelementptr i32,i32* %6, i32 %4
	%27 = load i32, i32* %26
	br label  %22

28:
	%29 = phi i32 [0, %21], [%39, %49]
	%30 = phi i32 [0, %21], [%50, %49]
	%31 = phi i32 [%5, %21], [%42, %49]
	%32 = phi i32* [%6, %21], [%43, %49]
	%33 = icmp sle i32 %30, %31
	br i1 %33, label  %34, label  %37

34:
	%35= getelementptr [400020 x i32],[400020 x i32]* @cnt, i32 0, i32 %30
	%36 = load i32, i32* %35
	br label  %38

37:
	ret void 
38:
	%39 = phi i32 [%29, %34], [%47, %45]
	%40 = phi i32 [%30, %34], [%40, %45]
	%41 = phi i32 [%36, %34], [%48, %45]
	%42 = phi i32 [%31, %34], [%42, %45]
	%43 = phi i32* [%32, %34], [%43, %45]
	%44 = icmp ne i32 %41, 0
	br i1 %44, label  %45, label  %49

45:
	%46= getelementptr i32,i32* %43, i32 %39
	store i32 %40, i32* %46
	%47 = add i32 %39, 1
	%48 = sub i32 %41, 1
	br label  %38

49:
	%50 = add i32 %40, 1
	br label  %28

}
define dso_local void @sortC(i32* %0){
	br label  %2

2:
	%3 = phi i32* [%0, %1], [%13, %17]
	%4 = phi i32 [0, %1], [%24, %17]
	%5 = load i32, i32* @n
	%6 = icmp slt i32 %4, %5
	br i1 %6, label  %7, label  %9

7:
	%8 = add i32 %4, 1
	br label  %10

9:
	ret void 
10:
	%11 = phi i32 [%4, %7], [%32, %31]
	%12 = phi i32 [%8, %7], [%33, %31]
	%13 = phi i32* [%3, %7], [%13, %31]
	%14 = phi i32 [%4, %7], [%14, %31]
	%15 = load i32, i32* @n
	%16 = icmp slt i32 %12, %15
	br i1 %16, label  %25, label  %17

17:
	%18= getelementptr i32,i32* %13, i32 %14
	%19 = load i32, i32* %18
	%20= getelementptr i32,i32* %13, i32 %14
	%21= getelementptr i32,i32* %13, i32 %11
	%22 = load i32, i32* %21
	store i32 %22, i32* %20
	%23= getelementptr i32,i32* %13, i32 %11
	store i32 %19, i32* %23
	%24 = add i32 %14, 1
	br label  %2

25:
	%26= getelementptr i32,i32* %13, i32 %12
	%27 = load i32, i32* %26
	%28= getelementptr i32,i32* %13, i32 %11
	%29 = load i32, i32* %28
	%30 = icmp slt i32 %27, %29
	br i1 %30, label  %34, label  %31

31:
	%32 = phi i32 [%12, %34], [%11, %25]
	%33 = add i32 %12, 1
	br label  %10

34:
	br label  %31

}
define dso_local i32 @main(){
	%1 = call i32 @quick_read()
	store i32 %1, i32* @n
	br label  %2

2:
	%3 = phi i32 [0, %0], [%15, %6]
	%4 = load i32, i32* @n
	%5 = icmp ne i32 %3, %4
	br i1 %5, label  %6, label  %16

6:
	%7= getelementptr [100005 x i32],[100005 x i32]* @a, i32 0, i32 %3
	%8 = call i32 @quick_read()
	store i32 %8, i32* %7
	%9= getelementptr [100005 x i32],[100005 x i32]* @b, i32 0, i32 %3
	%10= getelementptr [100005 x i32],[100005 x i32]* @a, i32 0, i32 %3
	%11 = load i32, i32* %10
	store i32 %11, i32* %9
	%12= getelementptr [100005 x i32],[100005 x i32]* @c, i32 0, i32 %3
	%13= getelementptr [100005 x i32],[100005 x i32]* @b, i32 0, i32 %3
	%14 = load i32, i32* %13
	store i32 %14, i32* %12
	%15 = add i32 %3, 1
	br label  %2

16:
	%17= getelementptr [100005 x i32],[100005 x i32]* @a, i32 0, i32 0
	call void @sortA(i32* %17)
	%18= getelementptr [100005 x i32],[100005 x i32]* @b, i32 0, i32 0
	call void @sortB(i32* %18)
	%19= getelementptr [100005 x i32],[100005 x i32]* @c, i32 0, i32 0
	call void @sortC(i32* %19)
	br label  %20

20:
	%21 = phi i32 [0, %16], [%41, %25]
	%22 = load i32, i32* @n
	%23 = sub i32 %22, %21
	%24 = icmp ne i32 %23, 0
	br i1 %24, label  %25, label  %42

25:
	%26= getelementptr [100005 x i32],[100005 x i32]* @b, i32 0, i32 %21
	%27= getelementptr [100005 x i32],[100005 x i32]* @b, i32 0, i32 %21
	%28 = load i32, i32* %27
	%29= getelementptr [100005 x i32],[100005 x i32]* @a, i32 0, i32 %21
	%30 = load i32, i32* %29
	%31 = sub i32 %28, %30
	store i32 %31, i32* %26
	%32= getelementptr [100005 x i32],[100005 x i32]* @c, i32 0, i32 %21
	%33= getelementptr [100005 x i32],[100005 x i32]* @c, i32 0, i32 %21
	%34 = load i32, i32* %33
	%35= getelementptr [100005 x i32],[100005 x i32]* @b, i32 0, i32 %21
	%36 = load i32, i32* %35
	%37 = sub i32 %34, %36
	%38= getelementptr [100005 x i32],[100005 x i32]* @a, i32 0, i32 %21
	%39 = load i32, i32* %38
	%40 = sub i32 %37, %39
	store i32 %40, i32* %32
	%41 = add i32 %21, 1
	br label  %20

42:
	br label  %43

43:
	%44 = phi i32 [0, %42], [%60, %59]
	%45 = load i32, i32* @n
	%46 = sub i32 %44, %45
	%47 = icmp ne i32 %46, 0
	br i1 %47, label  %50, label  %48

48:
	%49 = sub i32 0, 123
	ret i32 %49
50:
	%51= getelementptr [100005 x i32],[100005 x i32]* @b, i32 0, i32 %44
	%52 = load i32, i32* %51
	%53 = icmp ne i32 %52, 0
	br i1 %53, label  %54, label  %55

54:
	ret i32 1
55:
	%56= getelementptr [100005 x i32],[100005 x i32]* @c, i32 0, i32 %44
	%57 = load i32, i32* %56
	%58 = icmp ne i32 %57, 0
	br i1 %58, label  %61, label  %59

59:
	%60 = add i32 %44, 1
	br label  %43

61:
	ret i32 2
}
