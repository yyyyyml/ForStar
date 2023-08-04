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
define dso_local void @move(i32 %0, i32 %1){
	call void @putint(i32 %0)
	call void @putch(i32 32)
	call void @putint(i32 %1)
	call void @putch(i32 44)
	call void @putch(i32 32)
	ret void 
}
define dso_local void @hanoi(i32 %0, i32 %1, i32 %2, i32 %3){
	%5 = icmp eq i32 %0, 1
	br i1 %5, label  %7, label  %8

6:
	ret void 
7:
	call void @move(i32 %1, i32 %3)
	br label  %6

8:
	%9 = sub i32 %0, 1
	call void @hanoi(i32 %9, i32 %1, i32 %3, i32 %2)
	call void @move(i32 %1, i32 %3)
	%10 = sub i32 %0, 1
	call void @hanoi(i32 %10, i32 %2, i32 %1, i32 %3)
	br label  %6

}
define dso_local i32 @main(){
	%1 = call i32 @getint()
	br label  %2

2:
	%3 = phi i32 [%1, %0], [%7, %5]
	%4 = icmp sgt i32 %3, 0
	br i1 %4, label  %5, label  %8

5:
	%6 = call i32 @getint()
	call void @hanoi(i32 %6, i32 1, i32 2, i32 3)
	call void @putch(i32 10)
	%7 = sub i32 %3, 1
	br label  %2

8:
	ret i32 0
}
