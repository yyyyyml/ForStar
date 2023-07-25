	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	get_one
	.type	get_one	@function
get_one:
	addi sp,sp,-32
	sd ra,24(sp)
	sd s0,16(sp)
	addi s0,sp,32
.Bget_one1:
	li a0,1
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
	.size	get_one, .-get_one
	.align	1
	.global	deepWhileBr
	.type	deepWhileBr	@function
deepWhileBr:
	addi sp,sp,-80
	sd ra,72(sp)
	sd s0,64(sp)
	addi s0,sp,80
.BdeepWhileBr2:
	addw t2,a0,a1
	mv t3,t2
	j .BdeepWhileBr4
.BdeepWhileBr4:
	mv t2,t3
	li t4,75
	slt t5,t3,t4
	bne t5,zero,.BdeepWhileBr7
	j .BdeepWhileBr10
.BdeepWhileBr7:
	li t4,42
	mv t3,t2
	li t5,100
	slt t6,t2,t5
	bne t6,zero,.BdeepWhileBr11
	j .BdeepWhileBr4
.BdeepWhileBr10:
	mv a0,t3
	ld ra,72(sp)
	ld s0,64(sp)
	addi sp,sp,80
	jr ra
.BdeepWhileBr11:
	addw t5,t2,t4
	mv t3,t5
	mv t6,t5
	li s1,99
	subw s2,t5,s1
	sgtz t5,s2
	bne t5,zero,.BdeepWhileBr15
	j .BdeepWhileBr4
.BdeepWhileBr15:
	mv t3,t6
	li t2,2
	mulw t5,t4,t2
	li a0,0
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	call get_one
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv t2,a0
	li s1,1
	subw s2,t2,s1
	seqz t2,s2
	bne t2,zero,.BdeepWhileBr20
	j .BdeepWhileBr4
.BdeepWhileBr20:
	li t2,2
	mulw t4,t5,t2
	mv t3,t4
	j .BdeepWhileBr4
	.size	deepWhileBr, .-deepWhileBr
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-48
	sd ra,40(sp)
	sd s0,32(sp)
	addi s0,sp,48
.Bmain0:
	li a0,2
	li a1,2
	call deepWhileBr
	mv t2,a0
	mv a0,t2
	sd t2,-32(s0)
	call putint
	ld t2,-32(s0)
	mv t2,a0
	li a0,0
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
	.size	main, .-main
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
