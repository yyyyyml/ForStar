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
	addi sp,sp,-24
	sd ra,16(sp)
	sd s0,8(sp)
	addi s0,sp,24
	li t0,0
	sw t0,-20(s0)
	li a0,0
	lw t0,-20(s0)
	ld ra,16(sp)
	ld s0,8(sp)
	addi sp,sp,24
	jr ra
	.size	main, .-main
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
