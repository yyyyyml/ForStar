	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-32
	sd ra,24(sp)
	sd s0,16(sp)
	addi s0,sp,32
.Bmain0:
	li t2,5
	j .Bmain1
.Bmain1:
	subw t4,zero,t2
	subw t5,t2,t4
	bne t5,zero,.Bmain6
	j .Bmain8
.Bmain6:
	slli t3,t2,3
	sub t3,zero,t3
	mv t2,t3
	j .Bmain1
.Bmain8:
	mv a0,t2
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
	.size	main, .-main
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
