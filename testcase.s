	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	main
	.type	main	@function
main:
	li t1,-16448
	add sp,sp,t1
	li t0,16440
	add t0,t0,sp
	sd ra,0(t0)
	sd s0,-8(t0)
	li t1,16448
	add s0,sp,t1
.Bmain0:
	call getint
	mv s5,a0
	mv a0,zero
	call _sysy_starttime
	mv s10,zero
	lla s11,.F0
	fld f8,0(s11)
	mv s9,s5
	mv s8,zero
	lla s5,.F1
	fld f9,0(s5)
	lla s5,.F0
	fld f18,0(s5)
	j .Bmain4
.Bmain4:
	li s5,500000
	blt s8,s5,.Bmain15
	j .Bmain12
.Bmain12:
	mv a0,zero
	call _sysy_stoptime
	lla s5,.F2
	flw f19,0(s5)
	fsub.s f20,f8,f19
	lla s5,.F3
	flw f19,0(s5)
	fle.s s5,f20,f19
	bnez s5,.Bmain58
	j .Bmain60
.Bmain15:
	li s7,1717986919
	mul s7,s7,s8
	srai s7,s7,34
	sraiw t3,s8,31
	subw s7,s7,t3
	mv t3,s7
	slli s5,t3,3
	mv s6,s5
	slli s5,t3,1
	add s6,s6,s5
	subw s5,s8,s6
	bne s5,zero,.Bmain20
	j .Bmain21
.Bmain20:
	mv s5,s10
	fmv.s f19,f8
	mv s11,s9
	mv t6,s8
	lla t5,.F1
	fld f20,0(t5)
	lla t5,.F0
	fld f21,0(t5)
	j .Bmain24
.Bmain21:
	lla t5,.F4
	flw f22,0(t5)
	fadd.s f23,f18,f22
	lla t5,.F5
	flw f22,0(t5)
	fadd.s f24,f9,f22
	mv s5,s10
	fmv.s f19,f8
	mv s11,s9
	mv t6,s8
	fmv.s f20,f24
	fmv.s f21,f23
	j .Bmain24
.Bmain24:
	blt s5,s11,.Bmain32
	j .Bmain40
.Bmain32:
	slli s1,s5,2
	li t1,-8208
	add s1,s1,t1
	add t5,s0,s1
	fcvt.s.w f22,s5
	fadd.s f23,f21,f22
	fsw f23,0(t5)
	slli s2,s5,2
	li t1,-16400
	add s2,s2,t1
	add t5,s0,s2
	fcvt.s.w f22,s5
	fadd.s f23,f20,f22
	fsw f23,0(t5)
	addi t5,s5,1
	mv s5,t5
	j .Bmain24
.Bmain40:
	mv t5,zero
	lla t4,.F0
	fld f22,0(t4)
	j .Bmain43
.Bmain43:
	blt t5,s11,.Bmain47
	j .Bmain55
.Bmain47:
	slli s3,t5,2
	li t1,-8208
	add s3,s3,t1
	add t4,s0,s3
	flw f23,0(t4)
	slli s4,t5,2
	li t1,-16400
	add s4,s4,t1
	add t4,s0,s4
	flw f24,0(t4)
	fmul.s f25,f23,f24
	fadd.s f23,f22,f25
	addi t4,t5,1
	mv t5,t4
	fmv.s f22,f23
	j .Bmain43
.Bmain55:
	fadd.s f23,f19,f22
	addi t5,t6,1
	mv s10,s5
	fmv.s f8,f23
	mv s9,s11
	mv s8,t5
	fmv.s f9,f20
	fmv.s f18,f21
	j .Bmain4
.Bmain58:
	mv a0,zero
	li t0,-16416
	add t0,t0,s0
	sd s11,0(t0)
	sd t6,-8(t0)
	sd t5,-16(t0)
	call putint
	li t0,-16432
	add t0,t0,s0
	ld t5,0(t0)
	ld t6,8(t0)
	ld s11,16(t0)
	mv a0,zero
	li t0,16440
	add t0,t0,sp
	ld ra,0(t0)
	ld s0,-8(t0)
	li t1,16448
	add sp,sp,t1
	jr ra
.Bmain59:
	li a0,1
	li t0,-16416
	add t0,t0,s0
	sd s11,0(t0)
	sd t6,-8(t0)
	sd t5,-16(t0)
	call putint
	li t0,-16432
	add t0,t0,s0
	ld t5,0(t0)
	ld t6,8(t0)
	ld s11,16(t0)
	li a0,1
	li t0,16440
	add t0,t0,sp
	ld ra,0(t0)
	ld s0,-8(t0)
	li t1,16448
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
