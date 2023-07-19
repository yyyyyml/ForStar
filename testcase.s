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
	li t2,0
	sw t2,-24(s0)
	j .Bbubblesort5
.Bbubblesort5:
	lw t2,-24(s0)
	lla t3,n
	lw t4,0(t3)
	li t3,1
	subw t5,t4,t3
	blt t2,t5,.Bbubblesort10
	j .Bbubblesort11
.Bbubblesort10:
	li t2,0
	sw t2,-20(s0)
	j .Bbubblesort12
.Bbubblesort11:
	li a0,0
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
.Bbubblesort12:
	lw t2,-20(s0)
	lla t3,n
	lw t4,0(t3)
	lw t3,-24(s0)
	subw t5,t4,t3
	li t3,1
	subw t4,t5,t3
	blt t2,t4,.Bbubblesort19
	j .Bbubblesort20
.Bbubblesort19:
	j .Bbubblesort23
.Bbubblesort20:
	lw t2,-24(s0)
	li t3,1
	addw t4,t2,t3
	sw t4,-24(s0)
	j .Bbubblesort5
.Bbubblesort23:
	lw t2,-20(s0)
	mv t3,t4
	lw t5,-20(s0)
	li t6,1
	addw s1,t5,t6
	mv t5,t6
	bgt t3,t5,.Bbubblesort37
	j .Bbubblesort34
.Bbubblesort34:
	lw t3,-20(s0)
	li t5,1
	addw t2,t3,t5
	sw t2,-20(s0)
	j .Bbubblesort12
.Bbubblesort37:
	lw t2,-20(s0)
	li t3,1
	addw t4,t2,t3
	mv t2,t3
	sw t2,-32(s0)
	lw t2,-20(s0)
	li t5,1
	addw t6,t2,t5
	lw t2,-20(s0)
	mv t5,s1
	sw t5,s2
	lw t5,-20(s0)
	lw t4,-32(s0)
	sw t4,t3
	j .Bbubblesort34
	.size	bubblesort, .-bubblesort
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-96
	sd ra,88(sp)
	sd s0,80(sp)
	addi s0,sp,96
.Bmain0:
	lla t2,n
	li t3,10
	sw t3,0(t2)
	li t2,4
	sw t2,-60(s0)
	li t2,3
	sw t2,-56(s0)
	li t2,9
	sw t2,-52(s0)
	li t2,2
	sw t2,-48(s0)
	li t2,0
	sw t2,-44(s0)
	li t2,1
	sw t2,-40(s0)
	li t2,6
	sw t2,-36(s0)
	li t2,5
	sw t2,-32(s0)
	li t2,7
	sw t2,-28(s0)
	li t2,8
	sw t2,-24(s0)
	addi a0,s0,-60
	sd t2,-72(s0)
	call bubblesort
	ld t2,-72(s0)
	sw a0,-20(s0)
	j .Bmain15
.Bmain15:
	lw t2,-20(s0)
	lla t3,n
	lw t4,0(t3)
	blt t2,t4,.Bmain19
	j .Bmain28
.Bmain19:
	lw t2,-20(s0)
	mv t3,t4
	sw t3,-64(s0)
	lw a0,-64(s0)
	sd t2,-72(s0)
	sd t3,-80(s0)
	sd t4,-88(s0)
	call putint
	ld t4,-88(s0)
	ld t3,-80(s0)
	ld t2,-72(s0)
	li t3,10
	sw t3,-64(s0)
	lw a0,-64(s0)
	sd t2,-72(s0)
	sd t3,-80(s0)
	sd t4,-88(s0)
	call putch
	ld t4,-88(s0)
	ld t3,-80(s0)
	ld t2,-72(s0)
	lw t3,-20(s0)
	li t2,1
	addw t4,t3,t2
	sw t4,-20(s0)
	j .Bmain15
.Bmain28:
	li a0,0
	ld ra,88(sp)
	ld s0,80(sp)
	addi sp,sp,96
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
