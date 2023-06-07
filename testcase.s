	.file	null
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
	.size	getint, -getint
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
	.size	getch, -getch
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
	.size	getfloat, -getfloat
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
	.size	getarray, -getarray
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
	.size	getfarray, -getfarray
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
	.size	putint, -putint
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
	.size	putch, -putch
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
	.size	putfloat, -putfloat
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
	.size	putarray, -putarray
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
	.size	putfarray, -putfarray
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
	.size	starttime, -starttime
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
	.size	stoptime, -stoptime
	.align	1
	.global	fun1
	.type	fun1	@function
fun1:
	addi sp,sp,-60
	sd ra,52(sp)
	sd s0,44(sp)
	addi s0,sp,60
	sw a0,-56(s0)
	sw a1,-52(s0)
	sw a2,-48(s0)
	sw a3,-44(s0)
	sw a4,-40(s0)
	sw a5,-36(s0)
	sw a6,-32(s0)
	sw a7,-28(s0)
	lw t0,0(sp)
	sw t0,-24(s0)
	lw t0,8(sp)
	sw t0,-20(s0)
	lw t0,-56(s0)
	lw t1,-52(s0)
	addw t0,t0,t1
	lw t1,-48(s0)
	addw t0,t0,t1
	lw t1,-44(s0)
	addw t0,t0,t1
	lw t1,-40(s0)
	addw t0,t0,t1
	lw t1,-36(s0)
	addw t0,t0,t1
	lw t1,-32(s0)
	addw t0,t0,t1
	lw t1,-28(s0)
	addw t0,t0,t1
	lw t1,-24(s0)
	addw t0,t0,t1
	mv a0,t0
	ld ra,52(sp)
	ld s0,44(sp)
	addi sp,sp,60
	jr ra
	.size	fun1, -fun1
	.align	1
	.global	fun2
	.type	fun2	@function
fun2:
	addi sp,sp,-60
	sd ra,52(sp)
	sd s0,44(sp)
	addi s0,sp,60
	fsw f10,-56(s0)
	fsw f11,-52(s0)
	fsw f12,-48(s0)
	fsw f13,-44(s0)
	fsw f14,-40(s0)
	fsw f15,-36(s0)
	fsw f16,-32(s0)
	fsw f17,-28(s0)
	flw f0,0(sp)
	fsw f0,-24(s0)
	flw f0,8(sp)
	fsw f0,-20(s0)
	flw f0,-56(s0)
	flw f1,-52(s0)
	fadd.s f0,f0,f1
	flw f1,-48(s0)
	fadd.s f0,f0,f1
	flw f1,-44(s0)
	fadd.s f0,f0,f1
	flw f1,-40(s0)
	fadd.s f0,f0,f1
	flw f1,-36(s0)
	fadd.s f0,f0,f1
	flw f1,-32(s0)
	fadd.s f0,f0,f1
	flw f1,-28(s0)
	fadd.s f0,f0,f1
	flw f1,-24(s0)
	fadd.s f0,f0,f1
	mv a0,f0
	ld ra,52(sp)
	ld s0,44(sp)
	addi sp,sp,60
	jr ra
	.size	fun2, -fun2
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-120
	sd ra,112(sp)
	sd s0,104(sp)
	addi s0,sp,120
	call getint
	sw a0,-116(s0)
	lw a0,-116(s0)
	call putint
	li t0,4
	sw t0,-112(s0)
	li t0,3
	sw t0,-108(s0)
	li t0,4
	sw t0,-104(s0)
	li t0,3
	sw t0,-100(s0)
	li t0,4
	sw t0,-96(s0)
	li t0,3
	sw t0,-92(s0)
	li t0,4
	sw t0,-88(s0)
	li t0,3
	sw t0,-84(s0)
	li t0,3
	sw t0,-80(s0)
	li t0,3
	sw t0,-76(s0)
	lla t0,g
	lw t1,0(t0)
	lw t0,-108(s0)
	mulw t1,t1,t0
	li t0,2
	addw t0,t0,t1
	lla t1,i
	lw t2,0(t1)
	addw t0,t0,t2
	sw t0,-72(s0)
	lla t0,.F0
	flw f0,0(t0)
	fsw f0,-68(s0)
	lla t0,.F1
	flw f0,0(t0)
	fsw f0,-64(s0)
	lla t0,.F0
	flw f0,0(t0)
	fsw f0,-60(s0)
	lla t0,.F1
	flw f0,0(t0)
	fsw f0,-56(s0)
	lla t0,.F0
	flw f0,0(t0)
	fsw f0,-52(s0)
	lla t0,.F1
	flw f0,0(t0)
	fsw f0,-48(s0)
	lla t0,.F0
	flw f0,0(t0)
	fsw f0,-44(s0)
	lla t0,.F1
	flw f0,0(t0)
	fsw f0,-40(s0)
	lla t0,.F2
	flw f0,0(t0)
	fsw f0,-36(s0)
	lla t0,.F2
	flw f0,0(t0)
	fsw f0,-32(s0)
	lla t0,h
	flw f0,0(t0)
	lla t0,g
	lw t1,0(t0)
	fadd.s f0,f0,f1
	fsw f0,-28(s0)
	lw a0,-112(s0)
	lw a1,-108(s0)
	lw a2,-104(s0)
	lw a3,-100(s0)
	lw a4,-96(s0)
	lw a5,-92(s0)
	lw a6,-88(s0)
	lw a7,-84(s0)
	lw 0(sp),-80(s0)
	lw 8(sp),-76(s0)
	call fun1
	sw a0,-24(s0)
	flw f10,-68(s0)
	flw f11,-64(s0)
	flw f12,-60(s0)
	flw f13,-56(s0)
	flw f14,-52(s0)
	flw f15,-48(s0)
	flw f16,-44(s0)
	flw f17,-40(s0)
	flw 0(sp),-36(s0)
	flw 8(sp),-32(s0)
	call fun2
	fsw f0,-20(s0)
	li a0,0
	ld ra,112(sp)
	ld s0,104(sp)
	addi sp,sp,120
	jr ra
	.size	main, -main
.F0
	.word	1084227584
.F1
	.word	1085695590
.F2
	.word	1084647014
g
	.word	1
h
	.word	1091253043
i
	.word	0
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits