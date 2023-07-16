	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	ifElseIf
	.type	ifElseIf	@function
ifElseIf:
	addi sp,sp,-48
	sd ra,40(sp)
	sd s0,32(sp)
	addi s0,sp,48
.BifElseIf0:
	li t0,5
	sw t0,-32(s0)
	li t0,10
	sw t0,-24(s0)
	j .BifElseIf3
.BifElseIf3:
	lw t0,-32(s0)
	li t1,6
	seq.s t2,t0,t1
	bne t2,zero,.BifElseIf8
	j .BifElseIf34
.BifElseIf6:
	lw t0,-32(s0)
	mv a0,t0
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
.BifElseIf8:
	lw t0,-32(s0)
	mv a0,t0
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
.BifElseIf10:
	j .BifElseIf11
.BifElseIf11:
	lw t0,-24(s0)
	li t1,10
	seq.s t2,t0,t1
	bne t2,zero,.BifElseIf31
	j .BifElseIf16
.BifElseIf14:
	j .BifElseIf6
.BifElseIf15:
	li t0,25
	sw t0,-32(s0)
	j .BifElseIf14
.BifElseIf16:
	j .BifElseIf17
.BifElseIf17:
	lw t0,-24(s0)
	li t1,10
	seq.s t2,t0,t1
	bne t2,zero,.BifElseIf27
	j .BifElseIf24
.BifElseIf20:
	j .BifElseIf14
.BifElseIf21:
	lw t0,-32(s0)
	li t1,15
	addw t2,t0,t1
	sw t2,-32(s0)
	j .BifElseIf20
.BifElseIf24:
	lw t0,-32(s0)
	li t1,0
	subw t2,t1,t0
	sw t2,-32(s0)
	j .BifElseIf20
.BifElseIf27:
	lw t0,-32(s0)
	li t1,0
	li t2,5
	subw t3,t1,t2
	seq.s t1,t0,t3
	bne t1,zero,.BifElseIf21
	j .BifElseIf24
.BifElseIf31:
	lw t0,-32(s0)
	li t1,1
	seq.s t2,t0,t1
	bne t2,zero,.BifElseIf15
	j .BifElseIf16
.BifElseIf34:
	lw t0,-24(s0)
	li t1,11
	seq.s t2,t0,t1
	bne t2,zero,.BifElseIf8
	j .BifElseIf10
	.size	ifElseIf, .-ifElseIf
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-32
	sd ra,24(sp)
	sd s0,16(sp)
	addi s0,sp,32
.Bmain0:
	call ifElseIf
	call putint
	li a0,0
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
	.size	main, .-main
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
