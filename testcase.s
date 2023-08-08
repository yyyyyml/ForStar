	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	search
	.type	search	@function
search:
	addi sp,sp,-128
	sd ra,120(sp)
	sd s0,112(sp)
	addi s0,sp,128
	mv t2,a0
	mv t3,a1
	mv t4,a2
.Bsearch3:
	li t5,10
	bgt t4,t5,.Bsearch6
	j .Bsearch5
.Bsearch5:
	mv t5,zero
	li t6,1073741824
	j .Bsearch7
.Bsearch6:
	li a0,1073741824
	ld ra,120(sp)
	ld s0,112(sp)
	addi sp,sp,128
	jr ra
.Bsearch7:
	li s1,4
	blt t5,s1,.Bsearch11
	j .Bsearch82
.Bsearch11:
	mv s1,t3
	mv s2,zero
	mv s3,t2
	j .Bsearch12
.Bsearch12:
	lla s4,a
	li s9,120
	mulw s9,s9,s3
	add s5,s4,s9
	li s9,4
	mulw s9,s9,s1
	add s4,s5,s9
	lw s5,0(s4)
	li s4,1
	bne s5,s4,.Bsearch20
	j .Bsearch36
.Bsearch20:
	lla s4,x_1
	lw s5,0(s4)
	beq s3,s5,.Bsearch33
	j .Bsearch23
.Bsearch23:
	lla s4,step
	li s9,8
	mulw s9,s9,t5
	add s5,s4,s9
	lw s4,0(s5)
	addw s5,s3,s4
	lla s4,step
	li s9,8
	mulw s9,s9,t5
	add s6,s4,s9
	lw s4,4(s6)
	addw s6,s1,s4
	addi s4,s2,1
	mv s1,s6
	mv s2,s4
	mv s3,s5
	j .Bsearch12
.Bsearch33:
	lla s4,y_1
	lw s5,0(s4)
	beq s1,s5,.Bsearch36
	j .Bsearch23
.Bsearch36:
	lla s2,x_1
	lw s4,0(s2)
	beq s3,s4,.Bsearch40
	j .Bsearch43
.Bsearch39:
	li a0,1
	ld ra,120(sp)
	ld s0,112(sp)
	addi sp,sp,128
	jr ra
.Bsearch40:
	lla s2,y_1
	lw s4,0(s2)
	beq s1,s4,.Bsearch39
	j .Bsearch43
.Bsearch43:
	li s2,1
	ble s2,s2,.Bsearch45
	j .Bsearch47
.Bsearch45:
	addi s2,t5,1
	mv t5,s2
	j .Bsearch7
.Bsearch47:
	beq s3,zero,.Bsearch64
	j .Bsearch66
.Bsearch49:
	lla s4,a
	li s9,120
	mulw s9,s9,s3
	add s6,s4,s9
	li s9,4
	mulw s9,s9,s1
	add s4,s6,s9
	sw zero,0(s4)
	lla s4,step
	li s9,8
	mulw s9,s9,t5
	add s6,s4,s9
	lw s4,0(s6)
	subw s6,s3,s4
	lla s4,step
	li s9,8
	mulw s9,s9,t5
	add s7,s4,s9
	lw s4,4(s7)
	subw s7,s1,s4
	addi s4,t4,1
	mv a0,s6
	mv a1,s7
	mv a2,s4
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	sd s1,-72(s0)
	sd s2,-80(s0)
	sd s3,-88(s0)
	sd s4,-96(s0)
	sd s5,-104(s0)
	sd s6,-112(s0)
	sd s7,-120(s0)
	call search
	ld s7,-120(s0)
	ld s6,-112(s0)
	ld s5,-104(s0)
	ld s4,-96(s0)
	ld s3,-88(s0)
	ld s2,-80(s0)
	ld s1,-72(s0)
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv s4,a0
	addi s5,s4,1
	mv s2,t6
	blt s5,t6,.Bsearch81
	j .Bsearch76
.Bsearch64:
	addi s4,t5,1
	mv t5,s4
	j .Bsearch7
.Bsearch66:
	lla s4,h
	lw s6,0(s4)
	addi s4,s6,1
	beq s3,s4,.Bsearch64
	j .Bsearch70
.Bsearch70:
	beq s1,zero,.Bsearch64
	j .Bsearch72
.Bsearch72:
	lla s4,w
	lw s6,0(s4)
	addi s4,s6,1
	beq s1,s4,.Bsearch64
	j .Bsearch49
.Bsearch76:
	lla s4,a
	li s9,120
	mulw s9,s9,s3
	add s6,s4,s9
	li s9,4
	mulw s9,s9,s1
	add s4,s6,s9
	li s6,1
	sw s6,0(s4)
	addi s4,t5,1
	mv t5,s4
	mv t6,s2
	j .Bsearch7
.Bsearch81:
	mv s2,s5
	j .Bsearch76
.Bsearch82:
	li t2,10
	bgt t6,t2,.Bsearch85
	j .Bsearch84
.Bsearch84:
	mv a0,t6
	ld ra,120(sp)
	ld s0,112(sp)
	addi sp,sp,128
	jr ra
.Bsearch85:
	li a0,1073741824
	ld ra,120(sp)
	ld s0,112(sp)
	addi sp,sp,128
	jr ra
	.size	search, .-search
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-80
	sd ra,72(sp)
	sd s0,64(sp)
	addi s0,sp,80
.Bmain0:
	call getint
	mv t2,a0
	lla t3,w
	sw t2,0(t3)
	sd t2,-32(s0)
	sd t3,-40(s0)
	call getint
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv t2,a0
	lla t3,h
	sw t2,0(t3)
	j .Bmain3
.Bmain3:
	lla t2,w
	lw t3,0(t2)
	bne t3,zero,.Bmain6
	j .Bmain7
.Bmain6:
	mv t2,zero
	j .Bmain8
.Bmain7:
	mv a0,zero
	ld ra,72(sp)
	ld s0,64(sp)
	addi sp,sp,80
	jr ra
.Bmain8:
	li t3,30
	blt t2,t3,.Bmain11
	j .Bmain12
.Bmain11:
	mv t3,zero
	j .Bmain13
.Bmain12:
	li t2,1
	j .Bmain22
.Bmain13:
	li t4,30
	blt t3,t4,.Bmain16
	j .Bmain20
.Bmain16:
	lla t4,a
	li s9,120
	mulw s9,s9,t2
	add t5,t4,s9
	li s9,4
	mulw s9,s9,t3
	add t4,t5,s9
	li t5,1
	sw t5,0(t4)
	addi t4,t3,1
	mv t3,t4
	j .Bmain13
.Bmain20:
	addi t3,t2,1
	mv t2,t3
	j .Bmain8
.Bmain22:
	lla t3,h
	lw t4,0(t3)
	ble t2,t4,.Bmain26
	j .Bmain27
.Bmain26:
	li t3,1
	j .Bmain32
.Bmain27:
	lla t3,x_0
	lw t5,0(t3)
	lla t3,y_0
	lw t6,0(t3)
	mv a0,t5
	mv a1,t6
	li a2,1
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	call search
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv t4,a0
	li t3,10
	ble t4,t3,.Bmain58
	j .Bmain59
.Bmain32:
	lla t5,w
	lw t6,0(t5)
	ble t3,t6,.Bmain36
	j .Bmain44
.Bmain36:
	lla t5,a
	li s9,120
	mulw s9,s9,t2
	add t6,t5,s9
	li s9,4
	mulw s9,s9,t3
	add t5,t6,s9
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	call getint
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv t6,a0
	sw t6,0(t5)
	lla t5,a
	li s9,120
	mulw s9,s9,t2
	add t6,t5,s9
	li s9,4
	mulw s9,s9,t3
	add t5,t6,s9
	lw t6,0(t5)
	li t5,2
	beq t6,t5,.Bmain48
	j .Bmain49
.Bmain44:
	addi t5,t2,1
	mv t2,t5
	j .Bmain22
.Bmain46:
	addi t5,t3,1
	mv t3,t5
	j .Bmain32
.Bmain48:
	lla t5,x_0
	sw t2,0(t5)
	lla t5,y_0
	sw t3,0(t5)
	j .Bmain46
.Bmain49:
	lla t5,a
	li s9,120
	mulw s9,s9,t2
	add t6,t5,s9
	li s9,4
	mulw s9,s9,t3
	add t5,t6,s9
	lw t6,0(t5)
	li t5,3
	beq t6,t5,.Bmain54
	j .Bmain46
.Bmain54:
	lla t5,x_1
	sw t2,0(t5)
	lla t5,y_1
	sw t3,0(t5)
	j .Bmain46
.Bmain55:
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	call getint
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv t2,a0
	lla t3,w
	sw t2,0(t3)
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	call getint
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv t2,a0
	lla t3,h
	sw t2,0(t3)
	j .Bmain3
.Bmain58:
	mv a0,t4
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	call putint
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	li a0,10
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	call putch
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	j .Bmain55
.Bmain59:
	li a0,-1
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	call putint
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	li a0,10
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	call putch
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	j .Bmain55
	.size	main, .-main
	.global	a
	.bss
	.align	3
	.type	a,@object
	.size	a,3600
a:
	.zero	3600
	.global	step
	.data
	.align	3
	.type	step,@object
	.size	step,32
step:
	.word	1
	.zero	4
	.word	-1
	.zero	4
	.zero	4
	.word	1
	.zero	4
	.word	-1

	.global	w
	.bss
	.align	2
	.type	w,@object
	.size	w,4
w:
	.zero	4
	.global	h
	.bss
	.align	2
	.type	h,@object
	.size	h,4
h:
	.zero	4
	.global	x_0
	.bss
	.align	2
	.type	x_0,@object
	.size	x_0,4
x_0:
	.zero	4
	.global	y_0
	.bss
	.align	2
	.type	y_0,@object
	.size	y_0,4
y_0:
	.zero	4
	.global	x_1
	.bss
	.align	2
	.type	x_1,@object
	.size	x_1,4
x_1:
	.zero	4
	.global	y_1
	.bss
	.align	2
	.type	y_1,@object
	.size	y_1,4
y_1:
	.zero	4
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
