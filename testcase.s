	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	radixSort
	.type	radixSort	@function
radixSort:
	addi sp,sp,-512
	sd ra,504(sp)
	sd s0,496(sp)
	addi s0,sp,512
	mv t2,a0
	sd t2,-224(s0)
	mv t2,a1
	sd t2,-216(s0)
	mv t2,a2
	sd t2,-424(s0)
	mv s7,a3
.BradixSort4:
	addi s10,s0,-80
	mv a0,s10
	mv a1,zero
	li a2,64
	call memset
	addi s10,s0,-144
	mv a0,s10
	mv a1,zero
	li a2,64
	call memset
	addi s10,s0,-208
	mv a0,s10
	mv a1,zero
	li a2,64
	call memset
	li s10,-1
	ld t2,-224(s0)
	beq t2,s10,.BradixSort13
	j .BradixSort14
.BradixSort12:
	ld t2,-424(s0)
	mv s10,t2
	j .BradixSort17
.BradixSort13:
	ld ra,504(sp)
	ld s0,496(sp)
	addi sp,sp,512
	jr ra
.BradixSort14:
	ld t2,-424(s0)
	addi s9,t2,1
	bge s9,s7,.BradixSort13
	j .BradixSort12
.BradixSort17:
	blt s10,s7,.BradixSort20
	j .BradixSort54
.BradixSort20:
	slli t4,s10,2
	ld t2,-216(s0)
	add t5,t2,t4
	lw t4,0(t5)
	mv s9,t4
	mv s8,zero
	j .BradixSort23
.BradixSort23:
	ld t2,-224(s0)
	blt s8,t2,.BradixSort27
	j .BradixSort30
.BradixSort27:
	srli s6,s6,60
	add s6,s6,s9
	srai s6,s6,4
	addi t5,s8,1
	mv s9,s6
	mv s8,t5
	j .BradixSort23
.BradixSort30:
	srli s1,s1,60
	add s1,s1,s9
	srai s1,s1,4
	slli t5,s1,4
	subw s8,s9,t5
	j .BradixSort34
.BradixSort34:
	slli s2,s8,2
	addi s2,s2,-208
	add t4,s0,s2
	slli t6,s10,2
	ld t2,-216(s0)
	add s11,t2,t6
	lw t6,0(s11)
	mv s9,t6
	mv t5,zero
	j .BradixSort38
.BradixSort38:
	ld t2,-224(s0)
	blt t5,t2,.BradixSort42
	j .BradixSort45
.BradixSort42:
	srli s3,s3,60
	add s3,s3,s9
	srai s3,s3,4
	addi s8,t5,1
	mv s9,s3
	mv t5,s8
	j .BradixSort38
.BradixSort45:
	srli s4,s4,60
	add s4,s4,s9
	srai s4,s4,4
	slli s11,s4,4
	subw s8,s9,s11
	j .BradixSort49
.BradixSort49:
	slli s5,s8,2
	addi s5,s5,-208
	add s9,s0,s5
	lw s11,0(s9)
	addi s9,s11,1
	sw s9,0(t4)
	addi s9,s10,1
	mv s10,s9
	j .BradixSort17
.BradixSort54:
	ld t2,-424(s0)
	sw t2,-80(s0)
	lw s9,-208(s0)
	ld t2,-424(s0)
	addw s8,t2,s9
	sw s8,-144(s0)
	li s10,1
	j .BradixSort60
.BradixSort60:
	li s9,16
	blt s10,s9,.BradixSort63
	j .BradixSort73
.BradixSort63:
	slli t2,s10,2
	addi t2,t2,-80
	sd t2,-456(s0)
	add s9,s0,t2
	li s8,1
	subw s7,s10,s8
	slli t2,s7,2
	addi t2,t2,-144
	sd t2,-448(s0)
	add s8,s0,t2
	lw s7,0(s8)
	sw s7,0(s9)
	slli t2,s10,2
	addi t2,t2,-144
	sd t2,-440(s0)
	add s9,s0,t2
	slli t2,s10,2
	addi t2,t2,-208
	sd t2,-432(s0)
	add s8,s0,t2
	lw s6,0(s8)
	addw s8,s7,s6
	sw s8,0(s9)
	addi s9,s10,1
	mv s10,s9
	j .BradixSort60
.BradixSort73:
	mv s10,zero
	j .BradixSort74
.BradixSort74:
	li s9,16
	blt s10,s9,.BradixSort83
	j .BradixSort77
.BradixSort77:
	ld t2,-424(s0)
	sw t2,-80(s0)
	lw s8,-208(s0)
	ld t2,-424(s0)
	addw s7,t2,s8
	sw s7,-144(s0)
	mv s9,zero
	j .BradixSort176
.BradixSort83:
	slli t2,s10,2
	addi t2,t2,-80
	sd t2,-416(s0)
	add s8,s0,t2
	lw s7,0(s8)
	slli t2,s10,2
	addi t2,t2,-144
	sd t2,-408(s0)
	add s8,s0,t2
	lw s6,0(s8)
	blt s7,s6,.BradixSort89
	j .BradixSort94
.BradixSort89:
	slli t2,s10,2
	addi t2,t2,-80
	sd t2,-232(s0)
	add s7,s0,t2
	lw s6,0(s7)
	slli s7,s6,2
	ld t2,-216(s0)
	add s6,t2,s7
	lw s7,0(s6)
	mv s8,s7
	j .BradixSort96
.BradixSort94:
	addi s7,s10,1
	mv s10,s7
	j .BradixSort74
.BradixSort96:
	mv s7,s8
	mv s6,zero
	j .BradixSort98
.BradixSort98:
	ld t2,-224(s0)
	blt s6,t2,.BradixSort102
	j .BradixSort105
.BradixSort102:
	ld t2,-240(s0)
	srli t2,t2,60
	add t2,t2,s7
	srai t2,t2,4
	sd t2,-240(s0)
	addi s1,s6,1
	ld t2,-240(s0)
	mv s7,t2
	mv s6,s1
	j .BradixSort98
.BradixSort105:
	ld t2,-248(s0)
	srli t2,t2,60
	add t2,t2,s7
	srai t2,t2,4
	sd t2,-248(s0)
	slli s1,t2,4
	subw s6,s7,s1
	j .BradixSort109
.BradixSort109:
	bne s6,s10,.BradixSort111
	j .BradixSort171
.BradixSort111:
	mv s7,s8
	mv s6,zero
	j .BradixSort112
.BradixSort112:
	ld t2,-224(s0)
	blt s6,t2,.BradixSort116
	j .BradixSort119
.BradixSort116:
	ld t2,-256(s0)
	srli t2,t2,60
	add t2,t2,s7
	srai t2,t2,4
	sd t2,-256(s0)
	addi s1,s6,1
	ld t2,-256(s0)
	mv s7,t2
	mv s6,s1
	j .BradixSort112
.BradixSort119:
	ld t2,-264(s0)
	srli t2,t2,60
	add t2,t2,s7
	srai t2,t2,4
	sd t2,-264(s0)
	slli s1,t2,4
	subw s6,s7,s1
	j .BradixSort123
.BradixSort123:
	slli t2,s6,2
	addi t2,t2,-80
	sd t2,-272(s0)
	add s3,s0,t2
	lw s4,0(s3)
	slli s3,s4,2
	ld t2,-216(s0)
	add s4,t2,s3
	lw s2,0(s4)
	mv s7,s8
	mv s1,zero
	j .BradixSort128
.BradixSort128:
	ld t2,-224(s0)
	blt s1,t2,.BradixSort132
	j .BradixSort135
.BradixSort132:
	ld t2,-280(s0)
	srli t2,t2,60
	add t2,t2,s7
	srai t2,t2,4
	sd t2,-280(s0)
	addi s6,s1,1
	ld t2,-280(s0)
	mv s7,t2
	mv s1,s6
	j .BradixSort128
.BradixSort135:
	ld t2,-288(s0)
	srli t2,t2,60
	add t2,t2,s7
	srai t2,t2,4
	sd t2,-288(s0)
	slli s1,t2,4
	subw s6,s7,s1
	j .BradixSort139
.BradixSort139:
	slli t2,s6,2
	addi t2,t2,-80
	sd t2,-296(s0)
	add s3,s0,t2
	lw s4,0(s3)
	slli s3,s4,2
	ld t2,-216(s0)
	add s4,t2,s3
	sw s8,0(s4)
	mv s7,s8
	mv s1,zero
	j .BradixSort143
.BradixSort143:
	ld t2,-224(s0)
	blt s1,t2,.BradixSort147
	j .BradixSort150
.BradixSort147:
	ld t2,-304(s0)
	srli t2,t2,60
	add t2,t2,s7
	srai t2,t2,4
	sd t2,-304(s0)
	addi s6,s1,1
	ld t2,-304(s0)
	mv s7,t2
	mv s1,s6
	j .BradixSort143
.BradixSort150:
	ld t2,-312(s0)
	srli t2,t2,60
	add t2,t2,s7
	srai t2,t2,4
	sd t2,-312(s0)
	slli s1,t2,4
	subw s6,s7,s1
	j .BradixSort154
.BradixSort154:
	slli t2,s6,2
	addi t2,t2,-80
	sd t2,-320(s0)
	add s3,s0,t2
	mv s7,s8
	mv s1,zero
	j .BradixSort156
.BradixSort156:
	ld t2,-224(s0)
	blt s1,t2,.BradixSort160
	j .BradixSort163
.BradixSort160:
	ld t2,-328(s0)
	srli t2,t2,60
	add t2,t2,s7
	srai t2,t2,4
	sd t2,-328(s0)
	addi s6,s1,1
	ld t2,-328(s0)
	mv s7,t2
	mv s1,s6
	j .BradixSort156
.BradixSort163:
	ld t2,-336(s0)
	srli t2,t2,60
	add t2,t2,s7
	srai t2,t2,4
	sd t2,-336(s0)
	slli s1,t2,4
	subw s6,s7,s1
	j .BradixSort167
.BradixSort167:
	slli t2,s6,2
	addi t2,t2,-80
	sd t2,-344(s0)
	add s7,s0,t2
	lw s1,0(s7)
	addi s7,s1,1
	sw s7,0(s3)
	mv s8,s2
	j .BradixSort96
.BradixSort171:
	slli t2,s10,2
	addi t2,t2,-80
	sd t2,-352(s0)
	add s7,s0,t2
	lw s6,0(s7)
	slli s1,s6,2
	ld t2,-216(s0)
	add s2,t2,s1
	sw s8,0(s2)
	addi s1,s6,1
	sw s1,0(s7)
	j .BradixSort83
.BradixSort176:
	li s10,16
	blt s9,s10,.BradixSort180
	j .BradixSort179
.BradixSort179:
	ld ra,504(sp)
	ld s0,496(sp)
	addi sp,sp,512
	jr ra
.BradixSort180:
	bgt s9,zero,.BradixSort189
	j .BradixSort182
.BradixSort182:
	li s10,1
	ld t2,-224(s0)
	subw s8,t2,s10
	slli t2,s9,2
	addi t2,t2,-80
	sd t2,-360(s0)
	add s10,s0,t2
	lw s7,0(s10)
	slli t2,s9,2
	addi t2,t2,-144
	sd t2,-368(s0)
	add s10,s0,t2
	lw s6,0(s10)
	mv a0,s8
	ld t2,-216(s0)
	mv a1,t2
	mv a2,s7
	mv a3,s6
	sd s10,-472(s0)
	sd s9,-480(s0)
	sd s8,-488(s0)
	sd s7,-496(s0)
	sd s6,-504(s0)
	call radixSort
	ld s6,-504(s0)
	ld s7,-496(s0)
	ld s8,-488(s0)
	ld s9,-480(s0)
	ld s10,-472(s0)
	addi s10,s9,1
	mv s9,s10
	j .BradixSort176
.BradixSort189:
	slli t2,s9,2
	addi t2,t2,-80
	sd t2,-376(s0)
	add s10,s0,t2
	li s8,1
	subw s7,s9,s8
	slli t2,s7,2
	addi t2,t2,-144
	sd t2,-384(s0)
	add s8,s0,t2
	lw s7,0(s8)
	sw s7,0(s10)
	slli t2,s9,2
	addi t2,t2,-144
	sd t2,-392(s0)
	add s10,s0,t2
	slli t2,s9,2
	addi t2,t2,-208
	sd t2,-400(s0)
	add s8,s0,t2
	lw s6,0(s8)
	addw s8,s7,s6
	sw s8,0(s10)
	j .BradixSort182
	.size	radixSort, .-radixSort
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-64
	sd ra,56(sp)
	sd s0,48(sp)
	addi s0,sp,64
.Bmain0:
	lla s9,a
	addi s7,s9,0
	mv a0,s7
	call getarray
	mv s8,a0
	mv a0,zero
	call _sysy_starttime
	li a0,8
	addi s7,s9,0
	mv a1,s7
	mv a2,zero
	mv a3,s8
	sd s10,-32(s0)
	sd s9,-40(s0)
	sd s8,-48(s0)
	sd s7,-56(s0)
	call radixSort
	ld s7,-56(s0)
	ld s8,-48(s0)
	ld s9,-40(s0)
	ld s10,-32(s0)
	mv s10,zero
	j .Bmain3
.Bmain3:
	blt s10,s8,.Bmain6
	j .Bmain17
.Bmain6:
	lla s9,ans
	lw s7,0(s9)
	lla s9,a
	slli s6,s10,2
	add s1,s9,s6
	lw s9,0(s1)
	li s6,2
	addw s1,s6,s10
	divw s6,s9,s1
	mulw s2,s6,s1
	subw s6,s9,s2
	mulw s9,s10,s6
	addw s6,s7,s9
	addi s9,s10,1
	lla s7,ans
	sw s6,0(s7)
	mv s10,s9
	j .Bmain3
.Bmain17:
	lla s10,ans
	lw s9,0(s10)
	blt s9,zero,.Bmain22
	j .Bmain20
.Bmain20:
	mv a0,zero
	call _sysy_stoptime
	lla s10,ans
	lw s9,0(s10)
	mv a0,s9
	call putint
	li a0,10
	call putch
	mv a0,zero
	ld ra,56(sp)
	ld s0,48(sp)
	addi sp,sp,64
	jr ra
.Bmain22:
	lla s10,ans
	lw s9,0(s10)
	subw s10,zero,s9
	lla s9,ans
	sw s10,0(s9)
	j .Bmain20
	.size	main, .-main
	.global	a
	.bss
	.align	3
	.type	a,@object
	.size	a,120000040
a:
	.zero	120000040
	.global	ans
	.bss
	.align	2
	.type	ans,@object
	.size	ans,4
ans:
	.zero	4
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
