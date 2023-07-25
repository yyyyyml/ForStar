	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	ifElseIf
	.type	ifElseIf	@function
ifElseIf:
	addi sp,sp,-32
	sd ra,24(sp)
	sd s0,16(sp)
	addi s0,sp,32
.BifElseIf0:
	li t2,5
	li t3,10
	li t4,5
	li t5,5
	li t6,6
	subw s1,t5,t6
	seqz t5,s1
	bne t5,zero,.BifElseIf4
	j .BifElseIf28
.BifElseIf2:
	mv a0,t5
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
.BifElseIf4:
	mv a0,t2
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
.BifElseIf6:
	mv s1,t6
	mv s2,t6
	mv s4,s3
	mv s5,s3
	li s6,10
	subw s7,t6,s6
	seqz s6,s7
	bne s6,zero,.BifElseIf24
	j .BifElseIf11
.BifElseIf10:
	li t5,25
	j .BifElseIf2
.BifElseIf11:
	mv s6,s4
	mv s7,s4
	li s8,10
	subw s9,s1,s8
	seqz s8,s9
	bne s8,zero,.BifElseIf20
	j .BifElseIf17
.BifElseIf15:
	addi s8,s7,15
	mv t5,s8
	j .BifElseIf2
.BifElseIf17:
	li s8,0
	subw s9,s8,s6
	mv t5,s9
	j .BifElseIf2
.BifElseIf20:
	mv s6,s7
	li t5,0
	li s6,5
	subw s8,t5,s6
	subw t5,s7,s8
	seqz s6,t5
	bne s6,zero,.BifElseIf15
	j .BifElseIf17
.BifElseIf24:
	mv s1,s2
	mv s4,s5
	li t5,1
	subw s1,s5,t5
	seqz t5,s1
	bne t5,zero,.BifElseIf10
	j .BifElseIf11
.BifElseIf28:
	mv t6,t3
	mv t2,t4
	mv s3,t4
	li t2,11
	subw t4,t3,t2
	seqz t2,t4
	bne t2,zero,.BifElseIf4
	j .BifElseIf6
	.size	ifElseIf, .-ifElseIf
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-48
	sd ra,40(sp)
	sd s0,32(sp)
	addi s0,sp,48
.Bmain0:
	call ifElseIf
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
