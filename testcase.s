	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	move
	.type	move	@function
move:
	addi sp,sp,-48
	sd ra,40(sp)
	sd s0,32(sp)
	addi s0,sp,48
	mv t2,a0
	mv t3,a1
.Bmove2:
	mv a0,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	call putint
	ld t3,-40(s0)
	ld t2,-32(s0)
	li a0,32
	sd t2,-32(s0)
	sd t3,-40(s0)
	call putch
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv a0,t3
	sd t2,-32(s0)
	sd t3,-40(s0)
	call putint
	ld t3,-40(s0)
	ld t2,-32(s0)
	li a0,44
	sd t2,-32(s0)
	sd t3,-40(s0)
	call putch
	ld t3,-40(s0)
	ld t2,-32(s0)
	li a0,32
	sd t2,-32(s0)
	sd t3,-40(s0)
	call putch
	ld t3,-40(s0)
	ld t2,-32(s0)
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
	.size	move, .-move
	.align	1
	.global	hanoi
	.type	hanoi	@function
hanoi:
	addi sp,sp,-80
	sd ra,72(sp)
	sd s0,64(sp)
	addi s0,sp,80
	mv t2,a0
	mv t3,a1
	mv t4,a2
	mv t5,a3
.Bhanoi4:
	li t6,1
	subw s1,t2,t6
	seqz t6,s1
	bne t6,zero,.Bhanoi7
	j .Bhanoi8
.Bhanoi6:
	ld ra,72(sp)
	ld s0,64(sp)
	addi sp,sp,80
	jr ra
.Bhanoi7:
	mv a0,t3
	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	sd s1,-72(s0)
	call move
	ld s1,-72(s0)
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	j .Bhanoi6
.Bhanoi8:
	li t6,1
	subw s1,t2,t6
	mv a0,s1
	mv a1,t3
	mv a2,t5
	mv a3,t4
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	sd s1,-72(s0)
	call hanoi
	ld s1,-72(s0)
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv a0,t3
	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	sd s1,-72(s0)
	call move
	ld s1,-72(s0)
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	li t6,1
	subw s1,t2,t6
	mv a0,s1
	mv a1,t4
	mv a2,t3
	mv a3,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	sd s1,-72(s0)
	call hanoi
	ld s1,-72(s0)
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	j .Bhanoi6
	.size	hanoi, .-hanoi
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-64
	sd ra,56(sp)
	sd s0,48(sp)
	addi s0,sp,64
.Bmain0:
	sd t2,-32(s0)
	call getint
	ld t2,-32(s0)
	mv t3,a0
	mv t2,t3
	j .Bmain2
.Bmain2:
	subw t3,t2,zero
	sgtz t4,t3
	bne t4,zero,.Bmain5
	j .Bmain8
.Bmain5:
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	call getint
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv t3,a0
	mv a0,t3
	li a1,1
	li a2,2
	li a3,3
	sd t2,-32(s0)
	sd t3,-40(s0)
	call hanoi
	ld t3,-40(s0)
	ld t2,-32(s0)
	li a0,10
	sd t2,-32(s0)
	sd t3,-40(s0)
	call putch
	ld t3,-40(s0)
	ld t2,-32(s0)
	li t3,1
	subw t4,t2,t3
	mv t2,t4
	j .Bmain2
.Bmain8:
	mv a0,zero
	ld ra,56(sp)
	ld s0,48(sp)
	addi sp,sp,64
	jr ra
	.size	main, .-main
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
