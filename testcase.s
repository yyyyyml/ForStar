	.file	"null"
	.option	pic
	.text
	.align	1
	.global	bubblesort
	.type	bubblesort	@function
bubblesort:
	addi sp,sp,-48
	sd ra,40(sp)
	sd s0,32(sp)
	addi s0,sp,48
.Bbubblesort1:
	li t0,0
	sw t0,-24(s0)
	j .Bbubblesort5
.Bbubblesort5:
	lw t0,-24(s0)
	lla t1,n
	lw t2,0(t1)
	li t1,1
	subw t3,t2,t1
	blt t0,t3,.Bbubblesort10
	j .Bbubblesort11
.Bbubblesort10:
	li t0,0
	sw t0,-20(s0)
	j .Bbubblesort12
.Bbubblesort11:
	li a0,0
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
.Bbubblesort12:
	lw t0,-20(s0)
	lla t1,n
	lw t2,0(t1)
	lw t1,-24(s0)
	subw t3,t2,t1
	li t1,1
	subw t2,t3,t1
	blt t0,t2,.Bbubblesort19
	j .Bbubblesort20
.Bbubblesort19:
	j .Bbubblesort23
.Bbubblesort20:
	lw t0,-24(s0)
	li t1,1
	addw t2,t0,t1
	sw t2,-24(s0)
	j .Bbubblesort5
.Bbubblesort23:
	lw t0,-20(s0)
	mv t1,t2
	lw t3,-20(s0)
	li t4,1
	addw t5,t3,t4
	mv t3,t4
	bgt t1,t3,.Bbubblesort37
	j .Bbubblesort34
.Bbubblesort34:
	lw t1,-20(s0)
	li t3,1
	addw t0,t1,t3
	sw t0,-20(s0)
	j .Bbubblesort12
.Bbubblesort37:
	lw t0,-20(s0)
	li t1,1
	addw t2,t0,t1
	mv t0,t1
	sw t0,-32(s0)
	lw t0,-20(s0)
	li t3,1
	addw t4,t0,t3
	lw t0,-20(s0)
	mv t3,t5
	sw t0,-40(s0)
	sw t3,t0
	sw t0,-36(s0)
	lw t0,-40(s0)
	lw t3,-20(s0)
	lw t2,-32(s0)
	sw t2,t1
	j .Bbubblesort34
	.size	bubblesort, .-bubblesort
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-32
	sd ra,24(sp)
	sd s0,16(sp)
	addi s0,sp,32
.Bmain0:
	lla t0,n
	li t1,10
	sw t1,0(t0)
	li t0,4
	sw t0,t1
	li t0,3
	sw t0,t2
	li t0,9
	sw t0,t3
	li t0,2
	sw t0,t4
	li t0,0
	sw t0,t5
	li t0,1
	sw t0,t1
	li t0,6
	sw t0,t2
	li t0,5
	sw t0,t3
	li t0,7
	sw t0,t4
	li t0,8
	sw t0,t5
	call bubblesort
	sw a0,-20(s0)
	j .Bmain15
.Bmain15:
	lw t0,-20(s0)
	lla t1,n
	lw t2,0(t1)
	blt t0,t2,.Bmain19
	j .Bmain28
.Bmain19:
	lw t0,-20(s0)
	mv t1,t2
	sw t1,-28(s0)
	lw a0,-28(s0)
	call putint
	li t1,10
	sw t1,-28(s0)
	lw a0,-28(s0)
	call putch
	lw t1,-20(s0)
	li t0,1
	addw t2,t1,t0
	sw t2,-20(s0)
	j .Bmain15
.Bmain28:
	li a0,0
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
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
