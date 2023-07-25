	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	bubblesort
	.type	bubblesort	@function
bubblesort:
	addi sp,sp,-32
	sd ra,24(sp)
	sd s0,16(sp)
	addi s0,sp,32
.Bbubblesort1:
	mv t2,a0
	li t3,0
	j .Bbubblesort2
.Bbubblesort2:
	mv t4,t2
	mv t5,t3
	lla t6,n
	lw s1,0(t6)
	li t6,1
	subw s2,s1,t6
	slt t6,t3,s2
	bne t6,zero,.Bbubblesort8
	j .Bbubblesort11
.Bbubblesort8:
	li t6,0
	mv s1,t4
	mv t4,t5
	j .Bbubblesort12
.Bbubblesort11:
	li a0,0
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
.Bbubblesort12:
	mv t5,s1
	mv s2,s1
	mv s3,t6
	mv s4,t4
	lla s5,n
	lw s6,0(s5)
	subw s5,s6,t4
	li s6,1
	subw s7,s5,s6
	slt s5,t6,s7
	bne s5,zero,.Bbubblesort23
	j .Bbubblesort20
.Bbubblesort20:
	mv t2,t5
	addi t2,t4,1
	mv t3,t2
	j .Bbubblesort2
.Bbubblesort23:
	mv t2,s2
	mv t3,s2
	mv t5,s3
	mv s5,s3
	mv s6,s4
	mv s7,s4
	li s4,4
	mulw s4,s4,s3
	addi s4,s4,0
	add s8,s2,s4
	lw s4,0(s8)
	addi s8,s3,1
	li s3,4
	mulw s3,s3,s8
	addi s3,s3,0
	add s8,s2,s3
	lw s2,0(s8)
	subw s3,s4,s2
	sgtz s2,s3
	bne s2,zero,.Bbubblesort38
	j .Bbubblesort33
.Bbubblesort33:
	mv s1,t2
	mv t4,s6
	addi t4,t5,1
	mv t6,t4
	j .Bbubblesort12
.Bbubblesort38:
	mv t2,t3
	mv t5,s5
	mv s6,s7
	addi t2,s5,1
	li t4,4
	mulw t4,t4,t2
	addi t4,t4,0
	add t2,t3,t4
	lw t4,0(t2)
	addi t2,s5,1
	li t5,4
	mulw t5,t5,t2
	addi t5,t5,0
	add t2,t3,t5
	li t5,4
	mulw t5,t5,s5
	addi t5,t5,0
	add t6,t3,t5
	lw t5,0(t6)
	sw t5,0(t2)
	li t2,4
	mulw t2,t2,s5
	addi t2,t2,0
	add t5,t3,t2
	sw t4,0(t5)
	j .Bbubblesort33
	.size	bubblesort, .-bubblesort
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-80
	sd ra,72(sp)
	sd s0,64(sp)
	addi s0,sp,80
.Bmain0:
	lla t2,n
	li t3,10
	sw t3,0(t2)
	li t2,4
	sw t2,-56(s0)
	li t2,3
	sw t2,-52(s0)
	li t2,9
	sw t2,-48(s0)
	li t2,2
	sw t2,-44(s0)
	li t2,0
	sw t2,-40(s0)
	li t2,1
	sw t2,-36(s0)
	li t2,6
	sw t2,-32(s0)
	li t2,5
	sw t2,-28(s0)
	li t2,7
	sw t2,-24(s0)
	li t2,8
	sw t2,-20(s0)
	addi t2,s0,-56
	mv a0,t2
	sd t2,-72(s0)
	call bubblesort
	ld t2,-72(s0)
	mv t2,a0
	li a0,0
	ld ra,72(sp)
	ld s0,64(sp)
	addi sp,sp,80
	jr ra
	.size	main, .-main
	.global	n
	.bss
	.align	2
	.type	n,@object
	.size	n,4
n:
	.zero	4
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
