	.file	null
	.option	pic
	.text
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-40
	sd ra,32(sp)
	sd s0,24(sp)
	addi s0,sp,40
	li t0,4
	sw t0,-36(s0)
	li t0,3
	sw t0,-32(s0)
	li t0,2
	li t1,3
	addw t0,t0,t1
	mv t0,t1
	lw t0,-32(s0)
	addw t1,t1,t0
	mv t1,t0
	sw t0,-36(s0)
	lla t0,.F0
	flw f0,0(t0)
	fsw f0,-28(s0)
	lla t0,.F1
	flw f0,0(t0)
	fsw f0,-24(s0)
	flw f0,-28(s0)
	flw f1,-24(s0)
	fsw f2,-20(s0)
	li a0,0
	ld ra,32(sp)
	ld s0,24(sp)
	addi sp,sp,40
	jr ra
	.size	main, -main
.F0
	.word	1084227584
.F1
	.word	1085695590
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits