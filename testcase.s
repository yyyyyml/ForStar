	.file	null
	.option	pic
	.text
	.align	1
	.global	fun
	.type	fun	@function
fun:
	addi sp,sp,-36
	sd ra,28(sp)
	sd s0,20(sp)
	addi s0,sp,36
	sw t0,32(s0)
	li t0,0
	sw t0,24(s0)
	lw t0,32(s0)
	sw t0,32(s0)
	lw t0,24(s0)
	sw t0,-20(s0)
	sw t0,24(s0)
	lw t0,32(s0)
	sw t0,32(s0)
	lw t0,-20(s0)
	sw t0,28(s0)
	lw t0,32(s0)
	sw t0,32(s0)
	lw t0,28(s0)
	mv a0,t0
	sw t0,28(s0)
	lw t0,32(s0)
	ld ra,28(sp)
	ld s0,20(sp)
	addi sp,sp,36
	jr ra
	.size	fun, -fun
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits