	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	select_sort
	.type	select_sort	@function
select_sort:
	addi sp,sp,-80
	sd ra,72(sp)
	sd s0,64(sp)
	addi s0,sp,80
.Bselect_sort2:
	mv t2,a0
	mv t3,zero
	mv t4,a1
	j .Bselect_sort3
.Bselect_sort3:
	li s6,1
	subw s7,t4,s6
	slt s6,t3,s7
	bne s6,zero,.Bselect_sort9
	j .Bselect_sort11
.Bselect_sort9:
	addi s7,t3,1
	mv s10,t2
	sd s10,-48(s0)
	mv s10,t3
	sd s10,-40(s0)
	mv s10,t3
	sd s10,-32(s0)
	mv s10,t4
	sd s10,-24(s0)
	mv s8,s7
	j .Bselect_sort12
.Bselect_sort11:
	mv a0,zero
	ld ra,72(sp)
	ld s0,64(sp)
	addi sp,sp,80
	jr ra
.Bselect_sort12:
	ld s10,-24(s0)
	slt s7,s8,s10
	bne s7,zero,.Bselect_sort19
	j .Bselect_sort29
.Bselect_sort19:
	li t5,4
	ld s10,-32(s0)
	mulw t5,t5,s10
	ld s10,-48(s0)
	add s6,s10,t5
	lw s5,0(s6)
	li t6,4
	mulw t6,t6,s8
	ld s10,-48(s0)
	add s6,s10,t6
	lw s4,0(s6)
	subw s6,s5,s4
	sgtz s4,s6
	ld s10,-32(s0)
	mv s7,s10
	bne s4,zero,.Bselect_sort28
	j .Bselect_sort25
.Bselect_sort25:
	addi s4,s8,1
	mv s10,s10
	sd s10,-48(s0)
	mv s10,s10
	sd s10,-40(s0)
	mv s10,s7
	sd s10,-32(s0)
	mv s10,s10
	sd s10,-24(s0)
	mv s8,s4
	j .Bselect_sort12
.Bselect_sort28:
	mv s7,s8
	j .Bselect_sort25
.Bselect_sort29:
	ld s10,-32(s0)
	ld s9,-40(s0)
	subw s4,s10,s9
	seqz s1,s4
	xori s1,s1,1
	bne s1,zero,.Bselect_sort33
	j .Bselect_sort31
.Bselect_sort31:
	ld s10,-40(s0)
	addi s4,s10,1
	ld s10,-48(s0)
	mv t2,s10
	mv t3,s4
	ld s10,-24(s0)
	mv t4,s10
	j .Bselect_sort3
.Bselect_sort33:
	li s2,4
	ld s10,-32(s0)
	mulw s2,s2,s10
	ld s10,-48(s0)
	add t2,s10,s2
	lw s4,0(t2)
	li s3,4
	ld s10,-32(s0)
	mulw s3,s3,s10
	ld s10,-48(s0)
	add t2,s10,s3
	li s10,4
	sd s10,-64(s0)
	ld s9,-40(s0)
	mulw s10,s10,s9
	sd s10,-64(s0)
	ld s10,-48(s0)
	ld s9,-64(s0)
	add s5,s10,s9
	lw s6,0(s5)
	sw s6,0(t2)
	li s10,4
	sd s10,-56(s0)
	ld s9,-40(s0)
	mulw s10,s10,s9
	sd s10,-56(s0)
	ld s10,-48(s0)
	ld s9,-56(s0)
	add t2,s10,s9
	sw s4,0(t2)
	j .Bselect_sort31
	.size	select_sort, .-select_sort
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-96
	sd ra,88(sp)
	sd s0,80(sp)
	addi s0,sp,96
.Bmain0:
	lla t3,n
	li t5,10
	sw t5,0(t3)
	li t3,4
	sw t3,-56(s0)
	li t3,3
	sw t3,-52(s0)
	li t3,9
	sw t3,-48(s0)
	li t3,2
	sw t3,-44(s0)
	sw zero,-40(s0)
	li t3,1
	sw t3,-36(s0)
	li t3,6
	sw t3,-32(s0)
	li t3,5
	sw t3,-28(s0)
	li t3,7
	sw t3,-24(s0)
	li t3,8
	sw t3,-20(s0)
	lla t3,n
	lw t5,0(t3)
	addi t3,s0,-56
	mv a0,t3
	mv a1,t5
	sd t2,-72(s0)
	sd t4,-80(s0)
	call select_sort
	ld t4,-80(s0)
	ld t2,-72(s0)
	mv t3,a0
	mv t2,t3
	j .Bmain15
.Bmain15:
	lla t3,n
	lw t5,0(t3)
	slt t3,t2,t5
	bne t3,zero,.Bmain19
	j .Bmain23
.Bmain19:
	li t4,4
	mulw t4,t4,t2
	addi t4,t4,-56
	add t3,s0,t4
	lw t5,0(t3)
	mv a0,t5
	sd t2,-72(s0)
	sd t4,-80(s0)
	call putint
	ld t4,-80(s0)
	ld t2,-72(s0)
	mv t3,a0
	li a0,10
	sd t2,-72(s0)
	sd t4,-80(s0)
	call putch
	ld t4,-80(s0)
	ld t2,-72(s0)
	mv t3,a0
	addi t3,t2,1
	mv t2,t3
	j .Bmain15
.Bmain23:
	mv a0,zero
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
