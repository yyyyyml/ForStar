	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-144
	sd ra,136(sp)
	sd s0,128(sp)
	addi s0,sp,144
.Bmain0:
	call getint
	mv s9,a0
	call getint
	mv s8,a0
	mv a0,zero
	call _sysy_starttime
	mv s10,zero
	j .Bmain3
.Bmain3:
	blt s10,s9,.Bmain6
	j .Bmain7
.Bmain6:
	mv t4,zero
	j .Bmain8
.Bmain7:
	li t5,1
	li t2,1
	sd t2,-88(s0)
	j .Bmain27
.Bmain8:
	blt t4,s9,.Bmain11
	j .Bmain12
.Bmain11:
	mv t6,zero
	j .Bmain14
.Bmain12:
	addi s11,s10,1
	mv s10,s11
	j .Bmain3
.Bmain14:
	blt t6,s9,.Bmain17
	j .Bmain25
.Bmain17:
	lla s11,x
	li s7,1440000
	mulw s7,s7,s10
	add s5,s11,s7
	mv t3,t4
	slli s11,t3,11
	mv s6,s11
	slli s11,t3,8
	add s6,s6,s11
	slli s11,t3,6
	add s6,s6,s11
	slli s11,t3,5
	add s6,s6,s11
	add s11,s5,s6
	slli s5,t6,2
	add s4,s11,s5
	li s5,1
	sw s5,0(s4)
	lla s4,y
	li s1,1440000
	mulw s1,s1,s10
	add s5,s4,s1
	mv t3,t4
	slli s4,t3,11
	mv s2,s4
	slli s4,t3,8
	add s2,s2,s4
	slli s4,t3,6
	add s2,s2,s4
	slli s4,t3,5
	add s2,s2,s4
	add s4,s5,s2
	slli s5,t6,2
	add s11,s4,s5
	sw zero,0(s11)
	addi s4,t6,1
	mv t6,s4
	j .Bmain14
.Bmain25:
	addi s4,t4,1
	mv t4,s4
	j .Bmain8
.Bmain27:
	li s10,1
	subw s7,s9,s10
	ld t2,-88(s0)
	blt t2,s7,.Bmain32
	j .Bmain33
.Bmain32:
	li s10,1
	j .Bmain46
.Bmain33:
	mv a0,zero
	sd t5,-136(s0)
	call _sysy_stoptime
	ld t5,-136(s0)
	lla s7,x
	mv a0,s9
	addi s6,s7,0
	mv a1,s6
	sd t5,-136(s0)
	call putarray
	ld t5,-136(s0)
	srli s3,s3,63
	addw s3,s3,s9
	sraiw s3,s3,1
	lla s7,x
	li t2,1440000
	mulw t2,t2,s3
	sd t2,-120(s0)
	add s6,s7,t2
	mv t3,s3
	slli s7,t3,11
	mv t2,s7
	sd t2,-112(s0)
	slli s7,t3,8
	add t2,t2,s7
	slli s7,t3,6
	add t2,t2,s7
	slli s7,t3,5
	add t2,t2,s7
	sd t2,-112(s0)
	add s7,s6,t2
	mv a0,s9
	addi s6,s7,0
	mv a1,s6
	sd t5,-136(s0)
	call putarray
	ld t5,-136(s0)
	li s7,1
	ld t2,-88(s0)
	subw s6,t2,s7
	lla s7,x
	li t2,1440000
	mulw t2,t2,s6
	sd t2,-104(s0)
	add s6,s7,t2
	li s7,1
	subw s1,t5,s7
	mv t3,s1
	slli s7,t3,11
	mv t2,s7
	sd t2,-96(s0)
	slli s7,t3,8
	add t2,t2,s7
	slli s7,t3,6
	add t2,t2,s7
	slli s7,t3,5
	add t2,t2,s7
	sd t2,-96(s0)
	add s7,s6,t2
	mv a0,s9
	addi s6,s7,0
	mv a1,s6
	sd t5,-136(s0)
	call putarray
	ld t5,-136(s0)
	mv a0,zero
	ld ra,136(sp)
	ld s0,128(sp)
	addi sp,sp,144
	jr ra
.Bmain46:
	li s7,1
	subw s6,s9,s7
	blt s10,s6,.Bmain50
	j .Bmain51
.Bmain50:
	li s7,1
	j .Bmain53
.Bmain51:
	ld t2,-88(s0)
	addi s6,t2,1
	mv t5,s10
	mv t2,s6
	sd t2,-88(s0)
	j .Bmain27
.Bmain53:
	li s6,1
	subw s1,s9,s6
	blt s7,s1,.Bmain57
	j .Bmain91
.Bmain57:
	lla s6,x
	li t2,1440000
	ld t3,-88(s0)
	mulw t2,t2,t3
	sd t2,-80(s0)
	add s1,s6,t2
	mv t3,s10
	slli s6,t3,11
	mv t2,s6
	sd t2,-72(s0)
	slli s6,t3,8
	add t2,t2,s6
	slli s6,t3,6
	add t2,t2,s6
	slli s6,t3,5
	add t2,t2,s6
	sd t2,-72(s0)
	add s6,s1,t2
	slli s2,s7,2
	add s4,s6,s2
	li s2,1
	ld t2,-88(s0)
	subw s5,t2,s2
	lla s2,x
	li t2,1440000
	mulw t2,t2,s5
	sd t2,-24(s0)
	add s5,s2,t2
	mv t3,s10
	slli s2,t3,11
	mv t2,s2
	sd t2,-32(s0)
	slli s2,t3,8
	add t2,t2,s2
	slli s2,t3,6
	add t2,t2,s2
	slli s2,t3,5
	add t2,t2,s2
	sd t2,-32(s0)
	add s2,s5,t2
	slli s5,s7,2
	add s11,s2,s5
	lw s2,0(s11)
	ld t2,-88(s0)
	addi s5,t2,1
	lla s11,x
	li t2,1440000
	mulw t2,t2,s5
	sd t2,-40(s0)
	add s5,s11,t2
	mv t3,s10
	slli s11,t3,11
	mv t2,s11
	sd t2,-48(s0)
	slli s11,t3,8
	add t2,t2,s11
	slli s11,t3,6
	add t2,t2,s11
	slli s11,t3,5
	add t2,t2,s11
	sd t2,-48(s0)
	add s11,s5,t2
	slli s5,s7,2
	add t6,s11,s5
	lw s5,0(t6)
	addw s11,s2,s5
	li s2,1
	subw s5,s10,s2
	mv t3,s5
	slli s2,t3,11
	mv t2,s2
	sd t2,-56(s0)
	slli s2,t3,8
	add t2,t2,s2
	slli s2,t3,6
	add t2,t2,s2
	slli s2,t3,5
	add t2,t2,s2
	sd t2,-56(s0)
	add s2,s1,t2
	slli s5,s7,2
	add t6,s2,s5
	lw s2,0(t6)
	addw s5,s11,s2
	addi s2,s10,1
	mv t3,s2
	slli s2,t3,11
	mv t2,s2
	sd t2,-64(s0)
	slli s2,t3,8
	add t2,t2,s2
	slli s2,t3,6
	add t2,t2,s2
	slli s2,t3,5
	add t2,t2,s2
	sd t2,-64(s0)
	add s2,s1,t2
	slli s1,s7,2
	add s11,s2,s1
	lw s1,0(s11)
	addw s2,s5,s1
	li s1,1
	subw s5,s7,s1
	slli s1,s5,2
	add s5,s6,s1
	lw s1,0(s5)
	addw s5,s2,s1
	addi s1,s7,1
	slli s2,s1,2
	add s11,s6,s2
	lw s6,0(s11)
	addw s2,s5,s6
	divw s6,s2,s8
	sw s6,0(s4)
	mv s7,s1
	j .Bmain53
.Bmain91:
	addi s7,s10,1
	mv s10,s7
	j .Bmain46
	.size	main, .-main
	.global	x
	.bss
	.align	3
	.type	x,@object
	.size	x,864000000
x:
	.zero	864000000
	.global	y
	.bss
	.align	3
	.type	y,@object
	.size	y,864000000
y:
	.zero	864000000
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
