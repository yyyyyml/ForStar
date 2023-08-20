	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	main
	.type	main	@function
main:
	li t1,-16432
	add sp,sp,t1
	li t0,16424
	add t0,t0,sp
	sd ra,0(t0)
	sd s0,-8(t0)
	li t1,16432
	add s0,sp,t1
.Bmain0:
	call getint
	mv s4,a0
	mv a0,zero
	call _sysy_starttime
	lla s5,.F0
	fld f8,0(s5)
	mv s10,zero
	mv s9,s4
	lla s4,.F0
	fld f9,0(s4)
	mv s8,zero
	lla s4,.F1
	fld f18,0(s4)
	j .Bmain4
.Bmain4:
	li s4,500000
	blt s10,s4,.Bmain15
	j .Bmain12
.Bmain12:
	mv a0,zero
	call _sysy_stoptime
	lla s4,.F2
	flw f19,0(s4)
	fsub.s f20,f8,f19
	lla s4,.F3
	flw f19,0(s4)
	fle.s s4,f20,f19
	bnez s4,.Bmain58
	j .Bmain60
.Bmain15:
	srai s7,s10,2
	srli s7,s7,29
	add s7,s7,s10
	srai s7,s7,3
	slli s4,s7,3
	subw s5,s10,s4
	bne s5,zero,.Bmain20
	j .Bmain21
.Bmain20:
	fmv.s f19,f8
	mv s4,s10
	lla t6,.F0
	fld f20,0(t6)
	mv s5,s9
	mv s11,s8
	lla t6,.F1
	fld f21,0(t6)
	j .Bmain24
.Bmain21:
	lla t6,.F4
	flw f22,0(t6)
	fadd.s f23,f9,f22
	lla t6,.F5
	flw f22,0(t6)
	fadd.s f24,f18,f22
	fmv.s f19,f8
	mv s4,s10
	fmv.s f20,f23
	mv s5,s9
	mv s11,s8
	fmv.s f21,f24
	j .Bmain24
.Bmain24:
	blt s11,s5,.Bmain32
	j .Bmain40
.Bmain32:
	slli s6,s11,2
	li t1,-8208
	add s6,s6,t1
	add t6,s0,s6
	fcvt.s.w f22,s11
	fadd.s f23,f20,f22
	fsw f23,0(t6)
	slli s1,s11,2
	li t1,-16400
	add s1,s1,t1
	add t6,s0,s1
	fcvt.s.w f22,s11
	fadd.s f23,f21,f22
	fsw f23,0(t6)
	addi t6,s11,1
	mv s11,t6
	j .Bmain24
.Bmain40:
	mv t6,zero
	lla t5,.F0
	fld f22,0(t5)
	j .Bmain43
.Bmain43:
	blt t6,s5,.Bmain47
	j .Bmain55
.Bmain47:
	slli s2,t6,2
	li t1,-8208
	add s2,s2,t1
	add t5,s0,s2
	flw f23,0(t5)
	slli s3,t6,2
	li t1,-16400
	add s3,s3,t1
	add t5,s0,s3
	flw f24,0(t5)
	fmul.s f25,f23,f24
	fadd.s f23,f22,f25
	addi t5,t6,1
	mv t6,t5
	fmv.s f22,f23
	j .Bmain43
.Bmain55:
	fadd.s f23,f19,f22
	addi t6,s4,1
	fmv.s f8,f23
	mv s10,t6
	mv s9,s5
	fmv.s f9,f20
	mv s8,s11
	fmv.s f18,f21
	j .Bmain4
.Bmain58:
	mv a0,zero
	li t0,-16416
	add t0,t0,s0
	sd s11,0(t0)
	sd t6,-8(t0)
	call putint
	li t0,-16424
	add t0,t0,s0
	ld t6,0(t0)
	ld s11,8(t0)
	mv a0,zero
	li t0,16424
	add t0,t0,sp
	ld ra,0(t0)
	ld s0,-8(t0)
	li t1,16432
	add sp,sp,t1
	jr ra
.Bmain59:
	li a0,1
	li t0,-16416
	add t0,t0,s0
	sd s11,0(t0)
	sd t6,-8(t0)
	call putint
	li t0,-16424
	add t0,t0,s0
	ld t6,0(t0)
	ld s11,8(t0)
	li a0,1
	li t0,16424
	add t0,t0,sp
	ld ra,0(t0)
	ld s0,-8(t0)
	li t1,16432
	add sp,sp,t1
	jr ra
.Bmain60:
	lla s10,.F2
	flw f9,0(s10)
	fsub.s f18,f8,f9
	lla s10,.F6
	flw f9,0(s10)
	fge.s s10,f18,f9
	bnez s10,.Bmain58
	j .Bmain59
	.size	main, .-main
	.align	2
.F0:
	.word	0
	.align	2
.F1:
	.word	1065353216
	.align	2
.F2:
	.word	1487051712
	.align	2
.F3:
	.word	897988541
	.align	2
.F4:
	.word	1036831949
	.align	2
.F5:
	.word	1045220557
	.align	2
.F6:
	.word	-1249495107
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
