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
	flw vr_f0,0(vr_7)
	fsw vr_f0,-28(s0)
	lla t1,.F1
	flw vr_f1,0(vr_8)
	fsw vr_f1,-24(s0)
	flw vr_f2,-28(s0)
	flw vr_f3,-24(s0)
	fsw vr_f4,-20(s0)
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