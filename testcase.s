	.file	"null"
	.option	pic
	.text
	.align	1
	.global	getint
	.type	getint	@function
getint:
	addi sp,sp,-20
	sd ra,12(sp)
	sd s0,4(sp)
	addi s0,sp,20
	ld ra,12(sp)
	ld s0,4(sp)
	addi sp,sp,20
	jr ra
	.size	getint, .-getint
	.align	1
	.global	getch
	.type	getch	@function
getch:
	addi sp,sp,-20
	sd ra,12(sp)
	sd s0,4(sp)
	addi s0,sp,20
	ld ra,12(sp)
	ld s0,4(sp)
	addi sp,sp,20
	jr ra
	.size	getch, .-getch
	.align	1
	.global	getfloat
	.type	getfloat	@function
getfloat:
	addi sp,sp,-20
	sd ra,12(sp)
	sd s0,4(sp)
	addi s0,sp,20
	ld ra,12(sp)
	ld s0,4(sp)
	addi sp,sp,20
	jr ra
	.size	getfloat, .-getfloat
	.align	1
	.global	getarray
	.type	getarray	@function
getarray:
	addi sp,sp,-20
	sd ra,12(sp)
	sd s0,4(sp)
	addi s0,sp,20
	ld ra,12(sp)
	ld s0,4(sp)
	addi sp,sp,20
	jr ra
	.size	getarray, .-getarray
	.align	1
	.global	getfarray
	.type	getfarray	@function
getfarray:
	addi sp,sp,-20
	sd ra,12(sp)
	sd s0,4(sp)
	addi s0,sp,20
	ld ra,12(sp)
	ld s0,4(sp)
	addi sp,sp,20
	jr ra
	.size	getfarray, .-getfarray
	.align	1
	.global	putint
	.type	putint	@function
putint:
	addi sp,sp,-20
	sd ra,12(sp)
	sd s0,4(sp)
	addi s0,sp,20
	ld ra,12(sp)
	ld s0,4(sp)
	addi sp,sp,20
	jr ra
	.size	putint, .-putint
	.align	1
	.global	putch
	.type	putch	@function
putch:
	addi sp,sp,-20
	sd ra,12(sp)
	sd s0,4(sp)
	addi s0,sp,20
	ld ra,12(sp)
	ld s0,4(sp)
	addi sp,sp,20
	jr ra
	.size	putch, .-putch
	.align	1
	.global	putfloat
	.type	putfloat	@function
putfloat:
	addi sp,sp,-20
	sd ra,12(sp)
	sd s0,4(sp)
	addi s0,sp,20
	ld ra,12(sp)
	ld s0,4(sp)
	addi sp,sp,20
	jr ra
	.size	putfloat, .-putfloat
	.align	1
	.global	putarray
	.type	putarray	@function
putarray:
	addi sp,sp,-20
	sd ra,12(sp)
	sd s0,4(sp)
	addi s0,sp,20
	ld ra,12(sp)
	ld s0,4(sp)
	addi sp,sp,20
	jr ra
	.size	putarray, .-putarray
	.align	1
	.global	putfarray
	.type	putfarray	@function
putfarray:
	addi sp,sp,-20
	sd ra,12(sp)
	sd s0,4(sp)
	addi s0,sp,20
	ld ra,12(sp)
	ld s0,4(sp)
	addi sp,sp,20
	jr ra
	.size	putfarray, .-putfarray
	.align	1
	.global	starttime
	.type	starttime	@function
starttime:
	addi sp,sp,-20
	sd ra,12(sp)
	sd s0,4(sp)
	addi s0,sp,20
	ld ra,12(sp)
	ld s0,4(sp)
	addi sp,sp,20
	jr ra
	.size	starttime, .-starttime
	.align	1
	.global	stoptime
	.type	stoptime	@function
stoptime:
	addi sp,sp,-20
	sd ra,12(sp)
	sd s0,4(sp)
	addi s0,sp,20
	ld ra,12(sp)
	ld s0,4(sp)
	addi sp,sp,20
	jr ra
	.size	stoptime, .-stoptime
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-28
	sd ra,20(sp)
	sd s0,12(sp)
	addi s0,sp,28
B0:
	li t0,0
	sw t0,-24(s0)
	li t0,0
	sw t0,-20(s0)
	j B3
B3:
	lw t0,-24(s0)
	bge t1 1 B6
	j B7
B6:
	j B8
B7:
	li a0,0
B8:
	lw t2,-20(s0)
	bge t3 1 B12
	j B11
B11:
	j B3
B12:
	j B3
B13:
	j B11
	ld ra,20(sp)
	ld s0,12(sp)
	addi sp,sp,28
	jr ra
	.size	main, .-main
	.global	f
	.bss
	.align	2
	.type	f,@object
	.size	f,4
f:
	.zero	4
	.global	z
	.bss
	.align	2
	.type	z,@object
	.size	z,4
z:
	.zero	4
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
