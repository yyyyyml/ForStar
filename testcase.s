	.file	null
	.option	pic
	.text
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-28
	sd ra,20(sp)
	sd s0,12(sp)
	addi s0,sp,28
	li t0,4
	sw t0,-24(s0)
	li t0,3
	sw t0,-20(s0)
	li t0,2
	li t1,3
	addw t0,t0,t1
	mv t0,t1
	lw t0,-20(s0)
	addw t1,t1,t0
	mv t1,t0
	sw t0,-24(s0)
	li a0,0
	ld ra,20(sp)
	ld s0,12(sp)
	addi sp,sp,28
	jr ra
	.size	main, -main
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits