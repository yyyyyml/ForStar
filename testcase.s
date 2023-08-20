	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-80
	sd ra,72(sp)
	sd s0,64(sp)
	addi s0,sp,80
.Bmain0:
	li s10,5
	sw s10,-56(s0)
	li s10,5
	sw s10,-48(s0)
	li s10,1
	sw s10,-40(s0)
	li s10,2
	subw s9,zero,s10
	sw s9,-32(s0)
	li s10,2
	sw s10,-24(s0)
	lw s10,-32(s0)
	li s9,1
	mv s9,s10
	srli s10,s10,63
	add s10,s10,s9
	srai s10,s10,1
	blt s10,zero,.Bmain11
	j .Bmain13
.Bmain11:
	lw s10,-24(s0)
	mv a0,s10
	call putint
	j .Bmain25
.Bmain13:
	lw s10,-56(s0)
	lw s9,-48(s0)
	subw s8,s10,s9
	bne s8,zero,.Bmain18
	j .Bmain25
.Bmain18:
	lw s10,-40(s0)
	addi s9,s10,3
	srli s10,s10,63
	add s10,s10,s9
	srai s10,s10,1
	li s8,2
	mulw s7,s10,s8
	subw s10,s9,s7
	bne s10,zero,.Bmain11
	j .Bmain25
.Bmain25:
	lw s10,-32(s0)
	srli s9,s9,63
	add s9,s9,s10
	srai s9,s9,1
	li s8,2
	mulw s7,s9,s8
	subw s9,s10,s7
	addi s10,s9,67
	blt s10,zero,.Bmain33
	j .Bmain35
.Bmain32:
	mv a0,zero
	ld ra,72(sp)
	ld s0,64(sp)
	addi sp,sp,80
	jr ra
.Bmain33:
	li s10,4
	sw s10,-24(s0)
	lw s10,-24(s0)
	mv a0,s10
	call putint
	j .Bmain32
.Bmain35:
	lw s10,-56(s0)
	lw s9,-48(s0)
	subw s8,s10,s9
	bne s8,zero,.Bmain40
	j .Bmain32
.Bmain40:
	lw s10,-40(s0)
	addi s9,s10,2
	srli s10,s10,63
	add s10,s10,s9
	srai s10,s10,1
	li s8,2
	mulw s7,s10,s8
	subw s10,s9,s7
	bne s10,zero,.Bmain33
	j .Bmain32
	.size	main, .-main
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
