	.file	null
	.option	pic
	.text
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-60
	sd ra,52(sp)
	sd s0,44(sp)
	addi s0,sp,60
	li t0,4
	sw t0,-24(s0)
	li t0,5
	sw t0,-20(s0)
	lw t0,-24(s0)
	lw t0,28(s0)
	lw t0,-24(s0)
	sw t1,56(s0)
	lw t1,28(s0)
	sw t0,52(s0)
	sw t1,28(s0)
	lw t1,56(s0)
	lw t0,28(s0)
	mulw t0,t1,t0
	sw t0,28(s0)
	lw t0,52(s0)
	sw t0,52(s0)
	lw t0,28(s0)
	sw t0,t0
	sw t0,28(s0)
	lw t0,52(s0)
	lw t0,32(s0)
	lw t0,-24(s0)
	lw t0,36(s0)
	lw t0,-24(s0)
	sw t1,56(s0)
	lw t1,36(s0)
	sw t0,52(s0)
	sw t1,36(s0)
	lw t1,56(s0)
	lw t0,36(s0)
	mulw t0,t1,t0
	sw t0,36(s0)
	lw t0,52(s0)
	sw t0,52(s0)
	lw t0,36(s0)
	sw t0,t0
	sw t0,36(s0)
	lw t0,52(s0)
	sw t1,56(s0)
	lw t1,32(s0)
	sw t0,52(s0)
	sw t1,32(s0)
	lw t1,56(s0)
	lw t0,32(s0)
	mulw t0,t1,t0
	sw t0,32(s0)
	lw t0,52(s0)
	sw t0,52(s0)
	lw t0,32(s0)
	sw t0,t0
	sw t0,32(s0)
	lw t0,52(s0)
	sw t0,-24(s0)
	lw t0,-24(s0)
	lw t0,40(s0)
	lw t0,-24(s0)
	sw t1,56(s0)
	lw t1,40(s0)
	sw t0,52(s0)
	sw t1,40(s0)
	lw t1,56(s0)
	lw t0,40(s0)
	divw t0,t1,t0
	sw t0,40(s0)
	lw t0,52(s0)
	sw t0,52(s0)
	lw t0,40(s0)
	sw t0,t0
	sw t0,40(s0)
	lw t0,52(s0)
	lw t0,44(s0)
	lw t0,-24(s0)
	lw t0,48(s0)
	lw t0,-24(s0)
	sw t1,56(s0)
	lw t1,48(s0)
	sw t0,52(s0)
	sw t1,48(s0)
	lw t1,56(s0)
	lw t0,48(s0)
	divw t0,t1,t0
	sw t0,48(s0)
	lw t0,52(s0)
	sw t0,52(s0)
	lw t0,48(s0)
	sw t0,t0
	sw t0,48(s0)
	lw t0,52(s0)
	sw t1,56(s0)
	lw t1,44(s0)
	sw t0,52(s0)
	sw t1,44(s0)
	lw t1,56(s0)
	lw t0,44(s0)
	divw t0,t1,t0
	sw t0,44(s0)
	lw t0,52(s0)
	sw t0,52(s0)
	lw t0,44(s0)
	sw t0,t0
	sw t0,44(s0)
	lw t0,52(s0)
	sw t0,-24(s0)
	li a0,0
	ld ra,52(sp)
	ld s0,44(sp)
	addi sp,sp,60
	jr ra
	.size	main, -main
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits