	.file	null
	.option	pic
	.text
	.align	1
	.global	fun
	.type	fun	@function
fun:
	addi sp,sp,-24
	sd ra,16(sp)
	sd s0,8(sp)
	addi s0,sp,24
	li t0,7
	sw t0,-20(s0)
	lw t0,-20(s0)
	mv a0,t0
	ld ra,16(sp)
	ld s0,8(sp)
	addi sp,sp,24
	jr ra
	.size	fun, -fun
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-24
	sd ra,16(sp)
	sd s0,8(sp)
	addi s0,sp,24
	li t0,5
	sw t0,-20(s0)
	lw t0,-20(s0)
	mv a0,t0
	ld ra,16(sp)
	ld s0,8(sp)
	addi sp,sp,24
	jr ra
	.size	main, -main
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits