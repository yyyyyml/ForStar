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
	addi sp,sp,-40
	sd ra,32(sp)
	sd s0,24(sp)
	addi s0,sp,40
B0:
	li t0,5
	sw t0,-36(s0)
	li t0,5
	sw t0,-32(s0)
	li t0,1
	sw t0,-28(s0)
	li t0,0
	li t1,2
	subw t0,t0,t1
	sw t0,-24(s0)
	li t0,2
	sw t0,-20(s0)
	j B7
B7:
	lw t0,-24(s0)
	li t1,1
	mulw t0,t0,t1
	li t1,2
	divw t0,t0,t1
	bge t0 1 B13
	j B15
B12:
	j B27
B13:
	lw a0,-20(s0)
	call putint
	j B12
B15:
	lw t1,-36(s0)
	lw t2,-32(s0)
	subw t1,t1,t2
	bge t1 1 B20
	j B12
B20:
	lw t0,-28(s0)
	li t2,3
	addw t0,t0,t2
	li t2,2
	divw t0,t0,t2
	li t2,2
	mulw t0,t0,t2
	subw t0,t0,t0
	bge t0 1 B13
	j B12
B27:
	lw t1,-24(s0)
	li t2,2
	divw t1,t1,t2
	li t2,2
	mulw t1,t1,t2
	subw t1,t1,t1
	li t2,67
	addw t1,t1,t2
	bge t0 1 B35
	j B37
B34:
	li a0,0
B35:
	li t1,4
	sw t1,-20(s0)
	lw a0,-20(s0)
	call putint
	j B34
B37:
	lw t1,-36(s0)
	lw t2,-32(s0)
	subw t1,t1,t2
	bge t0 1 B42
	j B34
B42:
	lw t1,-28(s0)
	li t2,2
	addw t1,t1,t2
	li t2,2
	divw t1,t1,t2
	li t2,2
	mulw t1,t1,t2
	subw t1,t1,t1
	bge t0 1 B35
	j B34
	ld ra,32(sp)
	ld s0,24(sp)
	addi sp,sp,40
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
