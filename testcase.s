	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	spmv
	.type	spmv	@function
spmv:
	addi sp,sp,-64
	sd ra,56(sp)
	sd s0,48(sp)
	addi s0,sp,64
	mv t2,a0
	mv t3,a1
	mv t4,a2
	mv t5,a3
	mv t6,a4
	mv s10,a5
	sd s10,-40(s0)
.Bspmv6:
	mv s2,zero
	j .Bspmv7
.Bspmv7:
	mv s3,s2
	blt s3,t2,.Bspmv10
	j .Bspmv13
.Bspmv10:
	slli s4,s3,2
	ld s10,-40(s0)
	add s5,s10,s4
	sw zero,0(s5)
	addi s4,s3,1
	mv s2,s4
	j .Bspmv7
.Bspmv13:
	mv s10,zero
	sd s10,-32(s0)
	j .Bspmv14
.Bspmv14:
	ld s9,-32(s0)
	mv s10,s9
	sd s10,-24(s0)
	ld s10,-24(s0)
	blt s10,t2,.Bspmv17
	j .Bspmv20
.Bspmv17:
	ld s10,-24(s0)
	slli s5,s10,2
	add s6,t3,s5
	lw s5,0(s6)
	mv s4,s5
	j .Bspmv21
.Bspmv20:
	ld ra,56(sp)
	ld s0,48(sp)
	addi sp,sp,64
	jr ra
.Bspmv21:
	mv s5,s4
	ld s10,-24(s0)
	addi s6,s10,1
	slli s7,s6,2
	add s6,t3,s7
	lw s7,0(s6)
	blt s5,s7,.Bspmv27
	j .Bspmv39
.Bspmv27:
	slli s6,s5,2
	add s7,t4,s6
	lw s6,0(s7)
	slli s7,s6,2
	ld s10,-40(s0)
	add s6,s10,s7
	slli s7,s5,2
	add s8,t4,s7
	lw s7,0(s8)
	slli s8,s7,2
	ld s10,-40(s0)
	add s7,s10,s8
	lw s8,0(s7)
	slli s7,s5,2
	add s3,t5,s7
	lw s7,0(s3)
	addw s3,s8,s7
	sw s3,0(s6)
	addi s3,s5,1
	mv s4,s3
	j .Bspmv21
.Bspmv39:
	ld s10,-24(s0)
	slli s4,s10,2
	add s5,t3,s4
	lw s4,0(s5)
	mv s3,s4
	j .Bspmv42
.Bspmv42:
	mv s4,s3
	ld s10,-24(s0)
	addi s5,s10,1
	slli s6,s5,2
	add s5,t3,s6
	lw s6,0(s5)
	blt s4,s6,.Bspmv48
	j .Bspmv64
.Bspmv48:
	slli s5,s4,2
	add s6,t4,s5
	lw s5,0(s6)
	slli s6,s5,2
	ld s10,-40(s0)
	add s5,s10,s6
	slli s6,s4,2
	add s7,t4,s6
	lw s6,0(s7)
	slli s7,s6,2
	ld s10,-40(s0)
	add s6,s10,s7
	lw s7,0(s6)
	slli s6,s4,2
	add s8,t5,s6
	lw s6,0(s8)
	ld s10,-24(s0)
	slli s8,s10,2
	add s2,t6,s8
	lw s8,0(s2)
	li s2,1
	subw s1,s8,s2
	mulw s2,s6,s1
	addw s1,s7,s2
	sw s1,0(s5)
	addi s1,s4,1
	mv s3,s1
	j .Bspmv42
.Bspmv64:
	ld s10,-24(s0)
	addi s1,s10,1
	mv s10,s1
	sd s10,-32(s0)
	j .Bspmv14
	.size	spmv, .-spmv
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-80
	sd ra,72(sp)
	sd s0,64(sp)
	addi s0,sp,80
.Bmain0:
	lla t3,x
	addi t5,t3,0
	mv a0,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	call getarray
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv t3,a0
	li t5,1
	subw t4,t3,t5
	lla t3,y
	addi t5,t3,0
	mv a0,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	call getarray
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv t3,a0
	lla t3,v
	addi t5,t3,0
	mv a0,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	call getarray
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv t3,a0
	lla t3,a
	addi t5,t3,0
	mv a0,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	call getarray
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv t3,a0
	mv a0,zero
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	call _sysy_starttime
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv t2,zero
	j .Bmain10
.Bmain10:
	mv t3,t2
	li t5,100
	blt t3,t5,.Bmain13
	j .Bmain25
.Bmain13:
	lla t5,x
	lla t6,y
	lla s1,v
	lla s2,a
	lla s3,b
	mv a0,t4
	addi s4,t5,0
	mv a1,s4
	addi t5,t6,0
	mv a2,t5
	addi t5,s1,0
	mv a3,t5
	addi t5,s2,0
	mv a4,t5
	addi t5,s3,0
	mv a5,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd s3,-64(s0)
	call spmv
	ld s3,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	lla t5,x
	lla t6,y
	lla s1,v
	lla s2,b
	lla s3,a
	mv a0,t4
	addi s4,t5,0
	mv a1,s4
	addi t5,t6,0
	mv a2,t5
	addi t5,s1,0
	mv a3,t5
	addi t5,s2,0
	mv a4,t5
	addi t5,s3,0
	mv a5,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd s3,-64(s0)
	call spmv
	ld s3,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	addi t5,t3,1
	mv t2,t5
	j .Bmain10
.Bmain25:
	mv a0,zero
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	call _sysy_stoptime
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	lla t2,b
	mv a0,t4
	addi t3,t2,0
	mv a1,t3
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	call putarray
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv a0,zero
	ld ra,72(sp)
	ld s0,64(sp)
	addi sp,sp,80
	jr ra
	.size	main, .-main
	.global	x
	.bss
	.align	3
	.type	x,@object
	.size	x,400040
x:
	.zero	400040
	.global	y
	.bss
	.align	3
	.type	y,@object
	.size	y,12000000
y:
	.zero	12000000
	.global	v
	.bss
	.align	3
	.type	v,@object
	.size	v,12000000
v:
	.zero	12000000
	.global	a
	.bss
	.align	3
	.type	a,@object
	.size	a,400040
a:
	.zero	400040
	.global	b
	.bss
	.align	3
	.type	b,@object
	.size	b,400040
b:
	.zero	400040
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
