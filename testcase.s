	.file	"null"
	.option	pic
	.text
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-24
	sd ra,16(sp)
	sd s0,8(sp)
	addi s0,sp,24
.Bmain0:
	mv t0,t1
	sw t0,t2
	mv t0,t3
	mv a0,t0
	ld ra,16(sp)
	ld s0,8(sp)
	addi sp,sp,24
	jr ra
	.size	main, .-main
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
