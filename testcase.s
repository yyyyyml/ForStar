	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	float_abs
	.type	float_abs	@function
float_abs:
	addi sp,sp,-32
	sd ra,24(sp)
	sd s0,16(sp)
	addi s0,sp,32
.Bfloat_abs1:
	fsw f10,-20(s0)
	j .Bfloat_abs3
.Bfloat_abs3:
	flw f0,-20(s0)
	flt.s t2,f0,f1
	beq t2,zero,.Bfloat_abs9
	j .Bfloat_abs7
.Bfloat_abs7:
	flw f0,-20(s0)
	fmv.s f10,f0
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
.Bfloat_abs9:
	flw f0,-20(s0)
	fmv.s f10,f1
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
	.size	float_abs, .-float_abs
	.align	1
	.global	circle_area
	.type	circle_area	@function
circle_area:
	addi sp,sp,-32
	sd ra,24(sp)
	sd s0,16(sp)
	addi s0,sp,32
.Bcircle_area1:
	sw a0,-20(s0)
	lw t2,-20(s0)
	lla t3,.F0
	flw f0,0(t3)
	fmul.s f2,f0,f1
	lw t3,-20(s0)
	fmul.s f3,f2,f0
	lw t4,-20(s0)
	lw t5,-20(s0)
	mulw t6,t4,t5
	lla t4,.F0
	flw f2,0(t4)
	fmul.s f5,f4,f2
	fadd.s f2,f3,f5
	fdiv.s f3,f2,f1
	fmv.s f10,f3
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
	.size	circle_area, .-circle_area
	.align	1
	.global	float_eq
	.type	float_eq	@function
float_eq:
	addi sp,sp,-48
	sd ra,40(sp)
	sd s0,32(sp)
	addi s0,sp,48
.Bfloat_eq2:
	fsw f10,-24(s0)
	fsw f11,-20(s0)
	j .Bfloat_eq5
.Bfloat_eq5:
	flw f0,-24(s0)
	flw f1,-20(s0)
	fsub.s f10,f0,f1
	fsd f0,-32(s0)
	fsd f1,-40(s0)
	call float_abs
	fld f1,-40(s0)
	fld f0,-32(s0)
	lla t2,.F1
	flw f0,0(t2)
	flt.s t2,a0,f0
	beq t2,zero,.Bfloat_eq12
	j .Bfloat_eq18
.Bfloat_eq11:
	li a0,0
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
.Bfloat_eq12:
	lla t2,.F2
	flw f0,0(t2)
	fmul.s f2,f1,f0
	fdiv.s f3,f2,f0
	mv a0,t2
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
.Bfloat_eq18:
	li a0,0
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
	.size	float_eq, .-float_eq
	.align	1
	.global	error
	.type	error	@function
error:
	addi sp,sp,-16
	sd ra,8(sp)
	sd s0,0(sp)
	addi s0,sp,16
.Berror0:
	li a0,101
	call putch
	li a0,114
	call putch
	li a0,114
	call putch
	li a0,111
	call putch
	li a0,114
	call putch
	li a0,10
	call putch
	nop
	ld ra,8(sp)
	ld s0,0(sp)
	addi sp,sp,16
	jr ra
	.size	error, .-error
	.align	1
	.global	ok
	.type	ok	@function
ok:
	addi sp,sp,-16
	sd ra,8(sp)
	sd s0,0(sp)
	addi s0,sp,16
.Bok0:
	li a0,111
	call putch
	li a0,107
	call putch
	li a0,10
	call putch
	nop
	ld ra,8(sp)
	ld s0,0(sp)
	addi sp,sp,16
	jr ra
	.size	ok, .-ok
	.align	1
	.global	assert
	.type	assert	@function
assert:
	addi sp,sp,-48
	sd ra,40(sp)
	sd s0,32(sp)
	addi s0,sp,48
.Bassert1:
	sw a0,-20(s0)
	j .Bassert3
.Bassert3:
	lw t2,-20(s0)
	li t3,0
	beq t3,t2,.Bassert7
	j .Bassert8
.Bassert6:
	nop
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
.Bassert7:
	sd t2,-28(s0)
	sd t3,-36(s0)
	call error
	ld t3,-36(s0)
	ld t2,-28(s0)
	j .Bassert6
.Bassert8:
	sd t2,-28(s0)
	sd t3,-36(s0)
	call ok
	ld t3,-36(s0)
	ld t2,-28(s0)
	j .Bassert6
	.size	assert, .-assert
	.align	1
	.global	assert_not
	.type	assert_not	@function
assert_not:
	addi sp,sp,-48
	sd ra,40(sp)
	sd s0,32(sp)
	addi s0,sp,48
.Bassert_not1:
	sw a0,-20(s0)
	j .Bassert_not3
.Bassert_not3:
	lw t2,-20(s0)
	li t3,0
	bne t2,t3,.Bassert_not7
	j .Bassert_not8
.Bassert_not6:
	nop
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
.Bassert_not7:
	sd t2,-28(s0)
	sd t3,-36(s0)
	call error
	ld t3,-36(s0)
	ld t2,-28(s0)
	j .Bassert_not6
.Bassert_not8:
	sd t2,-28(s0)
	sd t3,-36(s0)
	call ok
	ld t3,-36(s0)
	ld t2,-28(s0)
	j .Bassert_not6
	.size	assert_not, .-assert_not
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-160
	sd ra,152(sp)
	sd s0,144(sp)
	addi s0,sp,160
.Bmain0:
	lla t2,.F3
	flw f0,0(t2)
	fmv.s f10,f0
	lla t2,.F4
	flw f0,0(t2)
	fmv.s f11,f0
	sd t2,-88(s0)
	fsd f0,-128(s0)
	call float_eq
	fld f0,-128(s0)
	ld t2,-88(s0)
	sd t2,-88(s0)
	fsd f0,-128(s0)
	call assert_not
	fld f0,-128(s0)
	ld t2,-88(s0)
	lla t2,.F5
	flw f0,0(t2)
	fmv.s f10,f0
	lla t2,.F6
	flw f0,0(t2)
	fmv.s f10,f0
	sd t2,-88(s0)
	fsd f0,-128(s0)
	call float_eq
	fld f0,-128(s0)
	ld t2,-88(s0)
	sd t2,-88(s0)
	fsd f0,-128(s0)
	call assert_not
	fld f0,-128(s0)
	ld t2,-88(s0)
	lla t2,.F6
	flw f0,0(t2)
	fmv.s f10,f0
	lla t2,.F6
	flw f0,0(t2)
	fmv.s f11,f0
	sd t2,-88(s0)
	fsd f0,-128(s0)
	call float_eq
	fld f0,-128(s0)
	ld t2,-88(s0)
	sd t2,-88(s0)
	fsd f0,-128(s0)
	call assert
	fld f0,-128(s0)
	ld t2,-88(s0)
	sd t2,-88(s0)
	fsd f0,-128(s0)
	call circle_area
	fld f0,-128(s0)
	ld t2,-88(s0)
	li a0,5
	sd t2,-88(s0)
	fsd f0,-128(s0)
	call circle_area
	fld f0,-128(s0)
	ld t2,-88(s0)
	sd t2,-88(s0)
	fsd f0,-128(s0)
	call float_eq
	fld f0,-128(s0)
	ld t2,-88(s0)
	sd t2,-88(s0)
	fsd f0,-128(s0)
	call assert
	fld f0,-128(s0)
	ld t2,-88(s0)
	lla t2,.F7
	flw f0,0(t2)
	fmv.s f10,f0
	lla t2,.F8
	flw f0,0(t2)
	fmv.s f11,f0
	sd t2,-88(s0)
	fsd f0,-128(s0)
	call float_eq
	fld f0,-128(s0)
	ld t2,-88(s0)
	sd t2,-88(s0)
	fsd f0,-128(s0)
	call assert_not
	fld f0,-128(s0)
	ld t2,-88(s0)
	j .Bmain9
.Bmain9:
	lla t2,.F9
	flw f0,0(t2)
	lla t2,.F10
	flw f1,0(t2)
	fne.s t2,f0,f1
	beq t2,zero,.Bmain12
	j .Bmain11
.Bmain11:
	j .Bmain13
.Bmain12:
	sd t2,-88(s0)
	fsd f0,-128(s0)
	fsd f1,-136(s0)
	call ok
	fld f1,-136(s0)
	fld f0,-128(s0)
	ld t2,-88(s0)
	j .Bmain11
.Bmain13:
	lla t2,.F10
	flw f0,0(t2)
	feq.s t2,f0,f1
	beq t2,zero,.Bmain19
	j .Bmain18
.Bmain18:
	j .Bmain20
.Bmain19:
	sd t2,-88(s0)
	fsd f0,-128(s0)
	fsd f1,-136(s0)
	call ok
	fld f1,-136(s0)
	fld f0,-128(s0)
	ld t2,-88(s0)
	j .Bmain18
.Bmain20:
	lla t2,.F10
	flw f0,0(t2)
	lla t2,.F10
	flw f1,0(t2)
	fne.s t2,f0,f1
	beq t2,zero,.Bmain24
	j .Bmain22
.Bmain22:
	j .Bmain26
.Bmain23:
	sd t2,-88(s0)
	fsd f0,-128(s0)
	fsd f1,-136(s0)
	call error
	fld f1,-136(s0)
	fld f0,-128(s0)
	ld t2,-88(s0)
	j .Bmain22
.Bmain24:
	li t2,3
	li t3,0
	bne t2,t3,.Bmain23
	j .Bmain22
.Bmain26:
	li t2,0
	li t3,0
	bne t2,t3,.Bmain39
	j .Bmain40
.Bmain28:
	li t2,1
	sw t2,-68(s0)
	li t2,0
	sw t2,-64(s0)
	li a1,0
	li a2,40
	sd t2,-88(s0)
	fsd f0,-128(s0)
	fsd f1,-136(s0)
	call memset
	fld f1,-136(s0)
	fld f0,-128(s0)
	ld t2,-88(s0)
	lla t2,.F11
	flw f0,0(t2)
	fsw f0,-60(s0)
	fsw f0,-56(s0)
	addi a0,s0,-60
	sd t2,-88(s0)
	fsd f0,-128(s0)
	call getfarray
	fld f0,-128(s0)
	ld t2,-88(s0)
	sw a0,-20(s0)
	j .Bmain42
.Bmain39:
	sd t2,-88(s0)
	fsd f0,-128(s0)
	call ok
	fld f0,-128(s0)
	ld t2,-88(s0)
	j .Bmain28
.Bmain40:
	lla t2,.F12
	flw f0,0(t2)
	lla t2,.F10
	flw f1,0(t2)
	fne.s t2,f0,f1
	beq t2,zero,.Bmain39
	j .Bmain28
.Bmain42:
	lw t2,-68(s0)
	li t3,1000000000
	blt t2,t3,.Bmain45
	j .Bmain75
.Bmain45:
	sd t2,-88(s0)
	sd t3,-96(s0)
	fsd f0,-128(s0)
	fsd f1,-136(s0)
	call getfloat
	fld f1,-136(s0)
	fld f0,-128(s0)
	ld t3,-96(s0)
	ld t2,-88(s0)
	fsw a0,-80(s0)
	flw f0,-80(s0)
	lla t2,.F0
	flw f1,0(t2)
	fmul.s f2,f1,f0
	flw f0,-80(s0)
	fmul.s f1,f2,f0
	fsw f1,-76(s0)
	flw f0,-80(s0)
	sd t2,-88(s0)
	fsd f0,-128(s0)
	call circle_area
	fld f0,-128(s0)
	ld t2,-88(s0)
	fsw a0,-72(s0)
	lw t2,-64(s0)
	lw t3,-64(s0)
	flw f1,-80(s0)
	fadd.s f3,f2,f1
	fsw f3,t4
	flw f10,-76(s0)
	sd t2,-88(s0)
	sd t3,-96(s0)
	sd t4,-104(s0)
	fsd f0,-128(s0)
	fsd f1,-136(s0)
	fsd f2,-144(s0)
	fsd f3,-152(s0)
	call putfloat
	fld f3,-152(s0)
	fld f2,-144(s0)
	fld f1,-136(s0)
	fld f0,-128(s0)
	ld t4,-104(s0)
	ld t3,-96(s0)
	ld t2,-88(s0)
	li a0,32
	sd t2,-88(s0)
	sd t3,-96(s0)
	sd t4,-104(s0)
	fsd f0,-128(s0)
	fsd f1,-136(s0)
	fsd f2,-144(s0)
	fsd f3,-152(s0)
	call putch
	fld f3,-152(s0)
	fld f2,-144(s0)
	fld f1,-136(s0)
	fld f0,-128(s0)
	ld t4,-104(s0)
	ld t3,-96(s0)
	ld t2,-88(s0)
	flw f0,-72(s0)
	sd t2,-88(s0)
	sd t3,-96(s0)
	sd t4,-104(s0)
	fsd f0,-128(s0)
	call putint
	fld f0,-128(s0)
	ld t4,-104(s0)
	ld t3,-96(s0)
	ld t2,-88(s0)
	li a0,10
	sd t2,-88(s0)
	sd t3,-96(s0)
	sd t4,-104(s0)
	fsd f0,-128(s0)
	call putch
	fld f0,-128(s0)
	ld t4,-104(s0)
	ld t3,-96(s0)
	ld t2,-88(s0)
	lw t2,-68(s0)
	fmul.s f2,f0,f1
	sw t3,-68(s0)
	lw t4,-64(s0)
	li t5,1
	addw t6,t4,t5
	sw t6,-64(s0)
	j .Bmain42
.Bmain75:
	lw a0,-20(s0)
	addi a1,s0,-60
	sd t2,-88(s0)
	sd t3,-96(s0)
	sd t4,-104(s0)
	sd t5,-112(s0)
	sd t6,-120(s0)
	fsd f0,-128(s0)
	fsd f1,-136(s0)
	fsd f2,-144(s0)
	call putfarray
	fld f2,-144(s0)
	fld f1,-136(s0)
	fld f0,-128(s0)
	ld t6,-120(s0)
	ld t5,-112(s0)
	ld t4,-104(s0)
	ld t3,-96(s0)
	ld t2,-88(s0)
	li a0,0
	ld ra,152(sp)
	ld s0,144(sp)
	addi sp,sp,160
	jr ra
	.size	main, .-main
.F0:
	.word	1078530011
.F1:
	.word	897988541
.F2:
	.word	1073741824
.F3:
	.word	1033895936
.F4:
	.word	-956241920
.F5:
	.word	1119752446
.F6:
	.word	1107966695
.F7:
	.word	1130954752
.F8:
	.word	1166012416
.F9:
	.word	1069547520
.F10:
	.word	0
.F11:
	.word	1065353216
.F12:
	.word	1050253722
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
