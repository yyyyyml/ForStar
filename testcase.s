	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	rand
	.type	rand	@function
rand:
	addi sp,sp,-32
	sd ra,24(sp)
	sd s0,16(sp)
	addi s0,sp,32
.Brand0:
	lla t2,seed
	lw t3,0(t2)
	li t2,19980130
	mulw t4,t3,t2
	li t2,23333
	addw t3,t4,t2
	li t2,100000007
	divw t4,t3,t2
	li t2,100000007
	mulw t5,t4,t2
	subw t2,t3,t5
	lla t3,seed
	sw t2,0(t3)
	j .Brand7
.Brand7:
	lla t2,seed
	lw t3,0(t2)
	li t2,0
	slt t4,t3,t2
	bne t4,zero,.Brand12
	j .Brand10
.Brand10:
	lla t2,seed
	lw t3,0(t2)
	mv a0,t3
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
.Brand12:
	lla t2,seed
	lw t3,0(t2)
	li t2,100000007
	addw t4,t3,t2
	lla t2,seed
	sw t4,0(t2)
	j .Brand10
	.size	rand, .-rand
	.align	1
	.global	my_fabs
	.type	my_fabs	@function
my_fabs:
	addi sp,sp,-48
	sd ra,40(sp)
	sd s0,32(sp)
	addi s0,sp,48
.Bmy_fabs1:
	fsw f10,-24(s0)
	j .Bmy_fabs3
.Bmy_fabs3:
	flw f0,-24(s0)
	li t2,0
	fcvt.s.w f1,t2
	fgt.s t2,f0,f1
	bne t2,zero,.Bmy_fabs10
	j .Bmy_fabs7
.Bmy_fabs7:
	flw f0,-24(s0)
	fneg.s f1,f0
	fmv.s f10,f1
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
.Bmy_fabs10:
	flw f0,-24(s0)
	fmv.s f10,f0
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
	.size	my_fabs, .-my_fabs
	.align	1
	.global	my_sqrt
	.type	my_sqrt	@function
my_sqrt:
	addi sp,sp,-64
	sd ra,56(sp)
	sd s0,48(sp)
	addi s0,sp,64
.Bmy_sqrt1:
	fsw f10,-40(s0)
	flw f0,-40(s0)
	li t2,8
	fcvt.s.w f1,t2
	fdiv.s f2,f0,f1
	lla t2,.F0
	flw f0,0(t2)
	fadd.s f1,f2,f0
	flw f0,-40(s0)
	li t2,2
	fcvt.s.w f2,t2
	fmul.s f3,f2,f0
	flw f0,-40(s0)
	li t2,4
	fcvt.s.w f2,t2
	fadd.s f4,f2,f0
	fdiv.s f0,f3,f4
	fadd.s f2,f1,f0
	fsw f2,-32(s0)
	li t2,10
	sw t2,-24(s0)
	j .Bmy_sqrt17
.Bmy_sqrt17:
	lw t2,-24(s0)
	li t3,0
	subw t4,t2,t3
	seqz t2,t4
	xori t2,t2,1
	bne t2,zero,.Bmy_sqrt20
	j .Bmy_sqrt30
.Bmy_sqrt20:
	flw f0,-32(s0)
	flw f1,-40(s0)
	flw f2,-32(s0)
	fdiv.s f3,f1,f2
	fadd.s f1,f0,f3
	li t2,2
	fcvt.s.w f0,t2
	fdiv.s f2,f1,f0
	fsw f2,-32(s0)
	lw t2,-24(s0)
	li t3,1
	subw t4,t2,t3
	sw t4,-24(s0)
	j .Bmy_sqrt17
.Bmy_sqrt30:
	flw f0,-32(s0)
	fmv.s f10,f0
	ld ra,56(sp)
	ld s0,48(sp)
	addi sp,sp,64
	jr ra
	.size	my_sqrt, .-my_sqrt
	.align	1
	.global	p
	.type	p	@function
p:
	addi sp,sp,-48
	sd ra,40(sp)
	sd s0,32(sp)
	addi s0,sp,48
.Bp1:
	fsw f10,-24(s0)
	flw f0,-24(s0)
	li t2,3
	fcvt.s.w f1,t2
	fmul.s f2,f1,f0
	flw f0,-24(s0)
	li t2,4
	fcvt.s.w f1,t2
	fmul.s f3,f1,f0
	flw f0,-24(s0)
	fmul.s f1,f3,f0
	flw f0,-24(s0)
	fmul.s f3,f1,f0
	fsub.s f0,f2,f3
	fmv.s f10,f0
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
	.size	p, .-p
	.align	1
	.global	my_sin_impl
	.type	my_sin_impl	@function
my_sin_impl:
	addi sp,sp,-80
	sd ra,72(sp)
	sd s0,64(sp)
	addi s0,sp,80
.Bmy_sin_impl1:
	fsw f10,-24(s0)
	j .Bmy_sin_impl3
.Bmy_sin_impl3:
	flw f0,-24(s0)
	fmv.s f10,f0
	fsd f0,-56(s0)
	call my_fabs
	fld f0,-56(s0)
	fmv.s f0,f10
	lla t2,.F1
	flw f1,0(t2)
	fle.s t2,f0,f1
	bne t2,zero,.Bmy_sin_impl12
	j .Bmy_sin_impl7
.Bmy_sin_impl7:
	flw f0,-24(s0)
	lla t2,.F2
	flw f1,0(t2)
	fdiv.s f2,f0,f1
	fmv.s f10,f2
	sd t2,-40(s0)
	fsd f0,-56(s0)
	fsd f1,-64(s0)
	fsd f2,-72(s0)
	call my_sin_impl
	fld f2,-72(s0)
	fld f1,-64(s0)
	fld f0,-56(s0)
	ld t2,-40(s0)
	fmv.s f0,f10
	fmv.s f10,f0
	sd t2,-40(s0)
	fsd f0,-56(s0)
	call p
	fld f0,-56(s0)
	ld t2,-40(s0)
	fmv.s f0,f10
	fmv.s f10,f0
	ld ra,72(sp)
	ld s0,64(sp)
	addi sp,sp,80
	jr ra
.Bmy_sin_impl12:
	flw f0,-24(s0)
	fmv.s f10,f0
	ld ra,72(sp)
	ld s0,64(sp)
	addi sp,sp,80
	jr ra
	.size	my_sin_impl, .-my_sin_impl
	.align	1
	.global	my_sin
	.type	my_sin	@function
my_sin:
	addi sp,sp,-64
	sd ra,56(sp)
	sd s0,48(sp)
	addi s0,sp,64
.Bmy_sin1:
	fsw f10,-24(s0)
	j .Bmy_sin3
.Bmy_sin3:
	flw f0,-24(s0)
	lla t2,.F3
	flw f1,0(t2)
	fgt.s t2,f0,f1
	bne t2,zero,.Bmy_sin7
	j .Bmy_sin17
.Bmy_sin6:
	j .Bmy_sin21
.Bmy_sin7:
	flw f0,-24(s0)
	lla t2,.F3
	flw f1,0(t2)
	fdiv.s f2,f0,f1
	fcvt.w.s t2,f2,rtz
	sw t2,-32(s0)
	flw f0,-24(s0)
	lw t2,-32(s0)
	fcvt.s.w f1,t2
	lla t2,.F3
	flw f2,0(t2)
	fmul.s f3,f1,f2
	fsub.s f1,f0,f3
	fsw f1,-24(s0)
	j .Bmy_sin6
.Bmy_sin17:
	flw f0,-24(s0)
	lla t2,.F3
	flw f1,0(t2)
	fneg.s f2,f1
	flt.s t2,f0,f2
	bne t2,zero,.Bmy_sin7
	j .Bmy_sin6
.Bmy_sin21:
	flw f0,-24(s0)
	lla t2,.F4
	flw f1,0(t2)
	fgt.s t2,f0,f1
	bne t2,zero,.Bmy_sin25
	j .Bmy_sin24
.Bmy_sin24:
	j .Bmy_sin28
.Bmy_sin25:
	flw f0,-24(s0)
	lla t2,.F3
	flw f1,0(t2)
	fsub.s f2,f0,f1
	fsw f2,-24(s0)
	j .Bmy_sin24
.Bmy_sin28:
	flw f0,-24(s0)
	lla t2,.F4
	flw f1,0(t2)
	fneg.s f2,f1
	flt.s t2,f0,f2
	bne t2,zero,.Bmy_sin35
	j .Bmy_sin32
.Bmy_sin32:
	flw f0,-24(s0)
	fmv.s f10,f0
	sd t2,-48(s0)
	fsd f0,-64(s0)
	call my_sin_impl
	fld f0,-64(s0)
	ld t2,-48(s0)
	fmv.s f0,f10
	fmv.s f10,f0
	ld ra,56(sp)
	ld s0,48(sp)
	addi sp,sp,64
	jr ra
.Bmy_sin35:
	flw f0,-24(s0)
	lla t2,.F3
	flw f1,0(t2)
	fadd.s f2,f0,f1
	fsw f2,-24(s0)
	j .Bmy_sin32
	.size	my_sin, .-my_sin
	.align	1
	.global	my_cos
	.type	my_cos	@function
my_cos:
	addi sp,sp,-80
	sd ra,72(sp)
	sd s0,64(sp)
	addi s0,sp,80
.Bmy_cos1:
	fsw f10,-24(s0)
	flw f0,-24(s0)
	li t2,2
	fcvt.s.w f1,t2
	lla t2,.F4
	flw f2,0(t2)
	fdiv.s f3,f2,f1
	fadd.s f1,f0,f3
	fmv.s f10,f1
	sd t2,-40(s0)
	fsd f0,-56(s0)
	fsd f1,-64(s0)
	fsd f3,-72(s0)
	call my_sin
	fld f3,-72(s0)
	fld f1,-64(s0)
	fld f0,-56(s0)
	ld t2,-40(s0)
	fmv.s f0,f10
	fmv.s f10,f0
	ld ra,72(sp)
	ld s0,64(sp)
	addi sp,sp,80
	jr ra
	.size	my_cos, .-my_cos
	.align	1
	.global	circle_sdf
	.type	circle_sdf	@function
circle_sdf:
	addi sp,sp,-112
	sd ra,104(sp)
	sd s0,96(sp)
	addi s0,sp,112
.Bcircle_sdf5:
	fsw f10,-72(s0)
	fsw f11,-64(s0)
	fsw f12,-56(s0)
	fsw f13,-48(s0)
	fsw f14,-40(s0)
	flw f0,-72(s0)
	flw f1,-56(s0)
	fsub.s f2,f0,f1
	fsw f2,-32(s0)
	flw f0,-64(s0)
	flw f1,-48(s0)
	fsub.s f2,f0,f1
	fsw f2,-24(s0)
	flw f0,-32(s0)
	flw f1,-32(s0)
	fmul.s f2,f0,f1
	flw f0,-24(s0)
	flw f1,-24(s0)
	fmul.s f3,f0,f1
	fadd.s f0,f2,f3
	fmv.s f10,f0
	fsd f0,-96(s0)
	fsd f2,-104(s0)
	fsd f3,-112(s0)
	call my_sqrt
	fld f3,-112(s0)
	fld f2,-104(s0)
	fld f0,-96(s0)
	fmv.s f0,f10
	flw f1,-40(s0)
	fsub.s f2,f0,f1
	fmv.s f10,f2
	ld ra,104(sp)
	ld s0,96(sp)
	addi sp,sp,112
	jr ra
	.size	circle_sdf, .-circle_sdf
	.align	1
	.global	scene
	.type	scene	@function
scene:
	addi sp,sp,-96
	sd ra,88(sp)
	sd s0,80(sp)
	addi s0,sp,96
.Bscene3:
	fsw f10,-56(s0)
	fsw f11,-48(s0)
	sd a0,-40(s0)
	flw f0,-56(s0)
	flw f1,-48(s0)
	fmv.s f10,f0
	fmv.s f11,f1
	lla t2,.F5
	flw f0,0(t2)
	fmv.s f12,f0
	lla t2,.F5
	flw f0,0(t2)
	fmv.s f13,f0
	lla t2,.F6
	flw f0,0(t2)
	fmv.s f14,f0
	sd t2,-72(s0)
	fsd f0,-88(s0)
	call circle_sdf
	fld f0,-88(s0)
	ld t2,-72(s0)
	fmv.s f0,f10
	fsw f0,-32(s0)
	flw f0,-56(s0)
	flw f1,-48(s0)
	fmv.s f10,f0
	fmv.s f11,f1
	lla t2,.F7
	flw f0,0(t2)
	fmv.s f12,f0
	lla t2,.F7
	flw f0,0(t2)
	fmv.s f13,f0
	lla t2,.F8
	flw f0,0(t2)
	fmv.s f14,f0
	sd t2,-72(s0)
	fsd f0,-88(s0)
	call circle_sdf
	fld f0,-88(s0)
	ld t2,-72(s0)
	fmv.s f0,f10
	fsw f0,-24(s0)
	j .Bscene15
.Bscene15:
	flw f0,-32(s0)
	flw f1,-24(s0)
	flt.s t2,f0,f1
	bne t2,zero,.Bscene20
	j .Bscene26
.Bscene19:
	nop
	ld ra,88(sp)
	ld s0,80(sp)
	addi sp,sp,96
	jr ra
.Bscene20:
	ld t2,-40(s0)
	flw f0,-32(s0)
	fsw f0,0(t2)
	ld t2,-40(s0)
	lla t3,.F2
	flw f0,0(t3)
	fsw f0,4(t2)
	j .Bscene19
.Bscene26:
	ld t2,-40(s0)
	flw f0,-24(s0)
	fsw f0,0(t2)
	ld t2,-40(s0)
	lla t3,.F9
	flw f0,0(t3)
	fsw f0,4(t2)
	j .Bscene19
	.size	scene, .-scene
	.align	1
	.global	trace
	.type	trace	@function
trace:
	addi sp,sp,-128
	sd ra,120(sp)
	sd s0,112(sp)
	addi s0,sp,128
.Btrace4:
	fsw f10,-64(s0)
	fsw f11,-56(s0)
	fsw f12,-48(s0)
	fsw f13,-40(s0)
	lla t2,.F9
	flw f0,0(t2)
	fsw f0,-32(s0)
	li t2,0
	sw t2,-24(s0)
	j .Btrace11
.Btrace11:
	lw t2,-24(s0)
	li t3,10
	slt t4,t2,t3
	bne t4,zero,.Btrace28
	j .Btrace27
.Btrace14:
	flw f0,-64(s0)
	flw f1,-48(s0)
	flw f2,-32(s0)
	fmul.s f3,f1,f2
	fadd.s f1,f0,f3
	flw f0,-56(s0)
	flw f2,-40(s0)
	flw f3,-32(s0)
	fmul.s f4,f2,f3
	fadd.s f2,f0,f4
	fmv.s f10,f1
	fmv.s f11,f2
	addi t2,s0,-72
	mv a0,t2
	sd t2,-88(s0)
	fsd f0,-104(s0)
	fsd f1,-112(s0)
	fsd f2,-120(s0)
	fsd f4,-128(s0)
	call scene
	fld f4,-128(s0)
	fld f2,-120(s0)
	fld f1,-112(s0)
	fld f0,-104(s0)
	ld t2,-88(s0)
	mv t2,a0
	j .Btrace31
.Btrace27:
	lla t3,.F9
	flw f0,0(t3)
	fmv.s f10,f0
	ld ra,120(sp)
	ld s0,112(sp)
	addi sp,sp,128
	jr ra
.Btrace28:
	flw f0,-32(s0)
	lla t2,.F10
	flw f1,0(t2)
	flt.s t2,f0,f1
	bne t2,zero,.Btrace14
	j .Btrace27
.Btrace31:
	flw f0,-72(s0)
	lla t2,.F1
	flw f1,0(t2)
	flt.s t2,f0,f1
	bne t2,zero,.Btrace42
	j .Btrace35
.Btrace35:
	flw f0,-32(s0)
	flw f1,-72(s0)
	fadd.s f2,f0,f1
	fsw f2,-32(s0)
	lw t2,-24(s0)
	li t3,1
	addw t4,t2,t3
	sw t4,-24(s0)
	j .Btrace11
.Btrace42:
	flw f0,-68(s0)
	fmv.s f10,f0
	ld ra,120(sp)
	ld s0,112(sp)
	addi sp,sp,128
	jr ra
	.size	trace, .-trace
	.align	1
	.global	sample
	.type	sample	@function
sample:
	addi sp,sp,-144
	sd ra,136(sp)
	sd s0,128(sp)
	addi s0,sp,144
.Bsample2:
	fsw f10,-48(s0)
	fsw f11,-40(s0)
	lla t2,.F9
	flw f0,0(t2)
	fsw f0,-32(s0)
	li t2,0
	sw t2,-24(s0)
	j .Bsample7
.Bsample7:
	lw t2,-24(s0)
	li t3,24
	slt t4,t2,t3
	bne t4,zero,.Bsample10
	j .Bsample35
.Bsample10:
	sd t2,-80(s0)
	sd t3,-88(s0)
	sd t4,-96(s0)
	fsd f0,-112(s0)
	call rand
	fld f0,-112(s0)
	ld t4,-96(s0)
	ld t3,-88(s0)
	ld t2,-80(s0)
	mv t2,a0
	fcvt.s.w f0,t2
	fsw f0,-64(s0)
	lw t2,-24(s0)
	flw f0,-64(s0)
	li t3,100000006
	fcvt.s.w f1,t3
	fdiv.s f2,f0,f1
	fcvt.s.w f0,t2
	fadd.s f1,f0,f2
	lla t2,.F3
	flw f0,0(t2)
	fmul.s f2,f0,f1
	li t2,24
	fcvt.s.w f0,t2
	fdiv.s f1,f2,f0
	fsw f1,-56(s0)
	flw f0,-32(s0)
	flw f1,-48(s0)
	flw f2,-40(s0)
	flw f3,-56(s0)
	fmv.s f10,f3
	sd t2,-80(s0)
	fsd f0,-112(s0)
	fsd f1,-120(s0)
	fsd f2,-128(s0)
	fsd f3,-136(s0)
	call my_cos
	fld f3,-136(s0)
	fld f2,-128(s0)
	fld f1,-120(s0)
	fld f0,-112(s0)
	ld t2,-80(s0)
	fmv.s f3,f10
	flw f4,-56(s0)
	fmv.s f10,f4
	sd t2,-80(s0)
	fsd f0,-112(s0)
	fsd f1,-120(s0)
	fsd f2,-128(s0)
	fsd f3,-136(s0)
	fsd f4,-144(s0)
	call my_sin
	fld f4,-144(s0)
	fld f3,-136(s0)
	fld f2,-128(s0)
	fld f1,-120(s0)
	fld f0,-112(s0)
	ld t2,-80(s0)
	fmv.s f4,f10
	fmv.s f10,f1
	fmv.s f11,f2
	fmv.s f12,f3
	fmv.s f13,f4
	sd t2,-80(s0)
	fsd f0,-112(s0)
	fsd f1,-120(s0)
	fsd f2,-128(s0)
	fsd f3,-136(s0)
	fsd f4,-144(s0)
	call trace
	fld f4,-144(s0)
	fld f3,-136(s0)
	fld f2,-128(s0)
	fld f1,-120(s0)
	fld f0,-112(s0)
	ld t2,-80(s0)
	fmv.s f1,f10
	fadd.s f2,f0,f1
	fsw f2,-32(s0)
	lw t2,-24(s0)
	li t3,1
	addw t4,t2,t3
	sw t4,-24(s0)
	j .Bsample7
.Bsample35:
	flw f0,-32(s0)
	li t2,24
	fcvt.s.w f1,t2
	fdiv.s f2,f0,f1
	fmv.s f10,f2
	ld ra,136(sp)
	ld s0,128(sp)
	addi sp,sp,144
	jr ra
	.size	sample, .-sample
	.align	1
	.global	write_pgm
	.type	write_pgm	@function
write_pgm:
	addi sp,sp,-144
	sd ra,136(sp)
	sd s0,128(sp)
	addi s0,sp,144
.Bwrite_pgm0:
	li a0,80
	call putch
	mv t2,a0
	li a0,50
	sd t2,-72(s0)
	call putch
	ld t2,-72(s0)
	mv t3,a0
	li a0,10
	sd t2,-72(s0)
	sd t3,-80(s0)
	call putch
	ld t3,-80(s0)
	ld t2,-72(s0)
	mv t4,a0
	li a0,192
	sd t2,-72(s0)
	sd t3,-80(s0)
	sd t4,-88(s0)
	call putint
	ld t4,-88(s0)
	ld t3,-80(s0)
	ld t2,-72(s0)
	mv t5,a0
	li a0,32
	sd t2,-72(s0)
	sd t3,-80(s0)
	sd t4,-88(s0)
	sd t5,-96(s0)
	call putch
	ld t5,-96(s0)
	ld t4,-88(s0)
	ld t3,-80(s0)
	ld t2,-72(s0)
	mv t2,a0
	li a0,192
	sd t2,-72(s0)
	sd t3,-80(s0)
	sd t4,-88(s0)
	sd t5,-96(s0)
	call putint
	ld t5,-96(s0)
	ld t4,-88(s0)
	ld t3,-80(s0)
	ld t2,-72(s0)
	mv t3,a0
	li a0,32
	sd t2,-72(s0)
	sd t3,-80(s0)
	sd t4,-88(s0)
	sd t5,-96(s0)
	call putch
	ld t5,-96(s0)
	ld t4,-88(s0)
	ld t3,-80(s0)
	ld t2,-72(s0)
	mv t4,a0
	li a0,255
	sd t2,-72(s0)
	sd t3,-80(s0)
	sd t4,-88(s0)
	sd t5,-96(s0)
	call putint
	ld t5,-96(s0)
	ld t4,-88(s0)
	ld t3,-80(s0)
	ld t2,-72(s0)
	mv t5,a0
	li a0,10
	sd t2,-72(s0)
	sd t3,-80(s0)
	sd t4,-88(s0)
	sd t5,-96(s0)
	call putch
	ld t5,-96(s0)
	ld t4,-88(s0)
	ld t3,-80(s0)
	ld t2,-72(s0)
	mv t2,a0
	li t3,0
	sw t3,-24(s0)
	j .Bwrite_pgm2
.Bwrite_pgm2:
	lw t3,-24(s0)
	slt t6,t3,t4
	bne t6,zero,.Bwrite_pgm5
	j .Bwrite_pgm7
.Bwrite_pgm5:
	li t3,0
	sw t3,-32(s0)
	li a0,111
	sd t2,-72(s0)
	sd t3,-80(s0)
	sd t4,-88(s0)
	call putint
	ld t4,-88(s0)
	ld t3,-80(s0)
	ld t2,-72(s0)
	mv t2,a0
	j .Bwrite_pgm8
.Bwrite_pgm7:
	nop
	ld ra,104(sp)
	ld s0,96(sp)
	addi sp,sp,112
	jr ra
.Bwrite_pgm8:
	lw t3,-32(s0)
	slt t5,t3,t4
	bne t5,zero,.Bwrite_pgm11
	j .Bwrite_pgm28
.Bwrite_pgm11:
	li a0,222
	sd t2,-72(s0)
	sd t3,-80(s0)
	sd t4,-88(s0)
	sd t5,-96(s0)
	call putint
	ld t5,-96(s0)
	ld t4,-88(s0)
	ld t3,-80(s0)
	ld t2,-72(s0)
	mv t2,a0
	lw t3,-32(s0)
	fcvt.s.w f0,t3
	fsw f0,-56(s0)
	lw t3,-24(s0)
	fcvt.s.w f0,t3
	fsw f0,-48(s0)
	flw f0,-56(s0)
	fcvt.s.w f1,t3
	fdiv.s f2,f0,f1
	flw f0,-48(s0)
	fcvt.s.w f1,t2
	fdiv.s f3,f0,f1
	fmv.s f10,f2
	fmv.s f11,f3
	sd t2,-72(s0)
	sd t3,-80(s0)
	fsd f0,-112(s0)
	fsd f1,-120(s0)
	fsd f2,-128(s0)
	fsd f3,-136(s0)
	call sample
	fld f3,-136(s0)
	fld f2,-128(s0)
	fld f1,-120(s0)
	fld f0,-112(s0)
	ld t3,-80(s0)
	ld t2,-72(s0)
	fmv.s f0,f10
	lla t4,.F11
	flw f1,0(t4)
	fmul.s f2,f0,f1
	fcvt.w.s t3,f2,rtz
	sw t3,-40(s0)
	j .Bwrite_pgm31
.Bwrite_pgm28:
	li a0,10
	sd t2,-72(s0)
	sd t3,-80(s0)
	fsd f0,-112(s0)
	fsd f1,-120(s0)
	fsd f2,-128(s0)
	call putch
	fld f2,-128(s0)
	fld f1,-120(s0)
	fld f0,-112(s0)
	ld t3,-80(s0)
	ld t2,-72(s0)
	mv t2,a0
	lw t3,-24(s0)
	li t4,1
	addw t5,t3,t4
	sw t5,-24(s0)
	j .Bwrite_pgm2
.Bwrite_pgm31:
	lw t3,-40(s0)
	li t4,255
	subw t5,t3,t4
	sgtz t3,t5
	bne t3,zero,.Bwrite_pgm37
	j .Bwrite_pgm34
.Bwrite_pgm34:
	li a0,1
	sd t2,-72(s0)
	sd t3,-80(s0)
	sd t5,-88(s0)
	fsd f0,-112(s0)
	fsd f1,-120(s0)
	fsd f2,-128(s0)
	call putint
	fld f2,-128(s0)
	fld f1,-120(s0)
	fld f0,-112(s0)
	ld t5,-88(s0)
	ld t3,-80(s0)
	ld t2,-72(s0)
	mv t2,a0
	li a0,32
	sd t2,-72(s0)
	fsd f0,-112(s0)
	fsd f1,-120(s0)
	fsd f2,-128(s0)
	call putch
	fld f2,-128(s0)
	fld f1,-120(s0)
	fld f0,-112(s0)
	ld t2,-72(s0)
	mv t3,a0
	lw t4,-32(s0)
	li t5,1
	addw t6,t4,t5
	sw t6,-32(s0)
	j .Bwrite_pgm8
.Bwrite_pgm37:
	li t4,255
	sw t4,-40(s0)
	j .Bwrite_pgm34
	.size	write_pgm, .-write_pgm
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-32
	sd ra,24(sp)
	sd s0,16(sp)
	addi s0,sp,32
.Bmain0:
	call write_pgm
	mv t2,a0
	li a0,0
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
	.size	main, .-main
	.align	2
.F0:
	.word	1056964608
	.align	2
.F1:
	.word	897988541
	.align	2
.F2:
	.word	1077936128
	.align	2
.F3:
	.word	1086918619
	.align	2
.F4:
	.word	1078530011
	.align	2
.F5:
	.word	1053609165
	.align	2
.F6:
	.word	1036831949
	.align	2
.F7:
	.word	1058642330
	.align	2
.F8:
	.word	1028443341
	.align	2
.F9:
	.word	0
	.align	2
.F10:
	.word	1073741824
	.align	2
.F11:
	.word	1132396544
	.global	seed
	.bss
	.align	2
	.type	seed,@object
	.size	seed,4
seed:
	.zero	4
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
