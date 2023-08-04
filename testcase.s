	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	get_bf_char
	.type	get_bf_char	@function
get_bf_char:
	addi sp,sp,-128
	sd ra,120(sp)
	sd s0,112(sp)
	addi s0,sp,128
.Bget_bf_char0:
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
	call getch
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
	mv s6,a0
	mv t2,s6
	j .Bget_bf_char2
.Bget_bf_char2:
	li s6,62
	subw s9,t2,s6
	seqz t3,s9
	xori t3,t3,1
	bne t3,zero,.Bget_bf_char8
	j .Bget_bf_char7
.Bget_bf_char5:
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
	call getch
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
	mv s6,a0
	mv t2,s6
	j .Bget_bf_char2
.Bget_bf_char7:
	mv a0,t2
	ld ra,120(sp)
	ld s0,112(sp)
	addi sp,sp,128
	jr ra
.Bget_bf_char8:
	li s6,60
	subw s9,t2,s6
	seqz t4,s9
	xori t4,t4,1
	bne t4,zero,.Bget_bf_char10
	j .Bget_bf_char7
.Bget_bf_char10:
	li s6,43
	subw s9,t2,s6
	seqz t5,s9
	xori t5,t5,1
	bne t5,zero,.Bget_bf_char12
	j .Bget_bf_char7
.Bget_bf_char12:
	li s6,45
	subw s9,t2,s6
	seqz t6,s9
	xori t6,t6,1
	bne t6,zero,.Bget_bf_char14
	j .Bget_bf_char7
.Bget_bf_char14:
	li s6,91
	subw s9,t2,s6
	seqz s1,s9
	xori s1,s1,1
	bne s1,zero,.Bget_bf_char16
	j .Bget_bf_char7
.Bget_bf_char16:
	li s6,93
	subw s9,t2,s6
	seqz s2,s9
	xori s2,s2,1
	bne s2,zero,.Bget_bf_char18
	j .Bget_bf_char7
.Bget_bf_char18:
	li s6,46
	subw s9,t2,s6
	seqz s3,s9
	xori s3,s3,1
	bne s3,zero,.Bget_bf_char20
	j .Bget_bf_char7
.Bget_bf_char20:
	li s6,44
	subw s9,t2,s6
	seqz s4,s9
	xori s4,s4,1
	bne s4,zero,.Bget_bf_char22
	j .Bget_bf_char7
.Bget_bf_char22:
	li s6,35
	subw s9,t2,s6
	seqz s5,s9
	xori s5,s5,1
	bne s5,zero,.Bget_bf_char5
	j .Bget_bf_char7
	.size	get_bf_char, .-get_bf_char
	.align	1
	.global	read_program
	.type	read_program	@function
read_program:
	addi sp,sp,-80
	sd ra,72(sp)
	sd s0,64(sp)
	addi s0,sp,80
.Bread_program0:
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	call get_bf_char
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv t5,a0
	mv t2,t5
	j .Bread_program2
.Bread_program2:
	li t5,35
	subw s9,t2,t5
	seqz t3,s9
	xori t3,t3,1
	bne t3,zero,.Bread_program5
	j .Bread_program11
.Bread_program5:
	lla t5,program_length
	lw t6,0(t5)
	lla t5,program
	li s9,4
	mulw s9,s9,t6
	add t6,t5,s9
	sw t2,0(t6)
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	call get_bf_char
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv t5,a0
	lla t6,program_length
	lw s1,0(t6)
	addi t6,s1,1
	lla s1,program_length
	sw t6,0(s1)
	mv t2,t5
	j .Bread_program2
.Bread_program11:
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	sd s1,-72(s0)
	call getch
	ld s1,-72(s0)
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv t2,a0
	li t3,105
	subw s9,t2,t3
	seqz t4,s9
	xori t4,t4,1
	bne t4,zero,.Bread_program17
	j .Bread_program14
.Bread_program14:
	sd t2,-32(s0)
	call getint
	ld t2,-32(s0)
	mv t3,a0
	lla t4,input_length
	sw t3,0(t4)
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	call getch
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv t3,a0
	mv t2,zero
	j .Bread_program18
.Bread_program17:
	ld ra,72(sp)
	ld s0,64(sp)
	addi sp,sp,80
	jr ra
.Bread_program18:
	lla t3,input_length
	lw t4,0(t3)
	slt t3,t2,t4
	bne t3,zero,.Bread_program22
	j .Bread_program26
.Bread_program22:
	lla t3,input
	li s9,4
	mulw s9,s9,t2
	add t4,t3,s9
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	call getch
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv t3,a0
	sw t3,0(t4)
	addi t3,t2,1
	mv t2,t3
	j .Bread_program18
.Bread_program26:
	ld ra,72(sp)
	ld s0,64(sp)
	addi sp,sp,80
	jr ra
	.size	read_program, .-read_program
	.align	1
	.global	run_program
	.type	run_program	@function
run_program:
	li t1,-2176
	add sp,sp,t1
	li t0,2168
	add t0,t0,sp
	sd ra,0(t0)
	sd s0,-8(t0)
	li t1,2176
	add s0,sp,t1
.Brun_program0:
	li t1,-2064
	add t6,s0,t1
	mv a0,t6
	mv a1,zero
	li a2,2048
	li t0,-2136
	add t0,t0,s0
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t6,-24(t0)
	call memset
	li t0,-2160
	add t0,t0,s0
	ld t6,0(t0)
	ld t4,8(t0)
	ld t3,16(t0)
	ld t2,24(t0)
	lla t6,output_length
	sw zero,0(t6)
	mv t2,zero
	mv t3,zero
	mv t4,zero
	mv s10,zero
	sd s10,40(t0)
	j .Brun_program3
.Brun_program3:
	lla t6,program_length
	lw s3,0(t6)
	slt t6,t2,s3
	bne t6,zero,.Brun_program10
	j .Brun_program14
.Brun_program10:
	lla t6,program
	li s9,4
	mulw s9,s9,t2
	add s4,t6,s9
	lw s3,0(s4)
	li t6,62
	subw s9,s3,t6
	seqz t6,s9
	bne t6,zero,.Brun_program21
	j .Brun_program23
.Brun_program14:
	li t0,2168
	add t0,t0,sp
	ld ra,0(t0)
	ld s0,-8(t0)
	li t1,2176
	add sp,sp,t1
	jr ra
.Brun_program15:
	li t0,-2096
	add t0,t0,s0
	ld s10,0(t0)
	addi s7,s10,1
	mv t2,s7
	ld s10,8(t0)
	mv t3,s10
	ld s10,16(t0)
	mv t4,s10
	ld s9,24(t0)
	mv s10,s9
	sd s10,-24(t0)
	j .Brun_program3
.Brun_program21:
	addi s7,t3,1
	mv s10,t2
	li t0,-2096
	add t0,t0,s0
	sd s10,0(t0)
	mv s10,s7
	sd s10,8(t0)
	mv s10,t4
	sd s10,16(t0)
	ld s9,-24(t0)
	mv s10,s9
	sd s10,24(t0)
	j .Brun_program15
.Brun_program23:
	li s7,60
	subw s9,s3,s7
	seqz s7,s9
	bne s7,zero,.Brun_program25
	j .Brun_program27
.Brun_program25:
	li s7,1
	subw s8,t3,s7
	mv s10,t2
	li t0,-2096
	add t0,t0,s0
	sd s10,0(t0)
	mv s10,s8
	sd s10,8(t0)
	mv s10,t4
	sd s10,16(t0)
	ld s9,-24(t0)
	mv s10,s9
	sd s10,24(t0)
	j .Brun_program15
.Brun_program27:
	li s7,43
	subw s9,s3,s7
	seqz s7,s9
	bne s7,zero,.Brun_program29
	j .Brun_program34
.Brun_program29:
	lla s7,tape
	li s9,4
	mulw s9,s9,t3
	add s8,s7,s9
	lla s7,tape
	li s9,4
	mulw s9,s9,t3
	add s6,s7,s9
	lw s7,0(s6)
	addi s6,s7,1
	sw s6,0(s8)
	mv s10,t2
	li t0,-2096
	add t0,t0,s0
	sd s10,0(t0)
	mv s10,t3
	sd s10,8(t0)
	mv s10,t4
	sd s10,16(t0)
	ld s9,-24(t0)
	mv s10,s9
	sd s10,24(t0)
	j .Brun_program15
.Brun_program34:
	li s6,45
	subw s9,s3,s6
	seqz s6,s9
	bne s6,zero,.Brun_program36
	j .Brun_program41
.Brun_program36:
	lla s6,tape
	li s9,4
	mulw s9,s9,t3
	add s7,s6,s9
	lla s6,tape
	li s9,4
	mulw s9,s9,t3
	add s8,s6,s9
	lw s6,0(s8)
	li s8,1
	subw s5,s6,s8
	sw s5,0(s7)
	mv s10,t2
	li t0,-2096
	add t0,t0,s0
	sd s10,0(t0)
	mv s10,t3
	sd s10,8(t0)
	mv s10,t4
	sd s10,16(t0)
	ld s9,-24(t0)
	mv s10,s9
	sd s10,24(t0)
	j .Brun_program15
.Brun_program41:
	li s5,91
	subw s9,s3,s5
	seqz s5,s9
	bne s5,zero,.Brun_program43
	j .Brun_program72
.Brun_program43:
	lla s5,tape
	li s9,4
	mulw s9,s9,t3
	add s6,s5,s9
	lw s5,0(s6)
	subw s9,s5,zero
	seqz s10,s9
	li t0,-2112
	add t0,t0,s0
	sd s10,0(t0)
	xori s10,s10,1
	sd s10,0(t0)
	ld s10,0(t0)
	bne s10,zero,.Brun_program47
	j .Brun_program50
.Brun_program47:
	li s9,4
	li t0,-2120
	add t0,t0,s0
	ld s10,0(t0)
	mulw s9,s9,s10
	li t1,-2064
	add s9,s9,t1
	add s5,s0,s9
	sw t2,0(s5)
	ld s10,0(t0)
	addi s5,s10,1
	mv s10,t2
	sd s10,24(t0)
	mv s10,t3
	sd s10,32(t0)
	mv s10,t4
	sd s10,40(t0)
	mv s10,s5
	sd s10,48(t0)
	j .Brun_program15
.Brun_program50:
	mv s5,t2
	mv s6,t3
	mv s7,t4
	li t0,-2120
	add t0,t0,s0
	ld s10,0(t0)
	mv s8,s10
	li s4,1
	j .Brun_program51
.Brun_program51:
	subw s9,s4,zero
	sgtz t6,s9
	mv s10,s5
	li t0,-2096
	add t0,t0,s0
	sd s10,0(t0)
	mv s10,s6
	sd s10,8(t0)
	mv s10,s7
	sd s10,16(t0)
	mv s10,s8
	sd s10,24(t0)
	bne t6,zero,.Brun_program58
	j .Brun_program15
.Brun_program58:
	addi s2,s5,1
	lla s1,program
	li s9,4
	mulw s9,s9,s2
	add t5,s1,s9
	lw s1,0(t5)
	li t5,93
	subw s9,s1,t5
	seqz t5,s9
	mv t6,s4
	bne t5,zero,.Brun_program63
	j .Brun_program65
.Brun_program63:
	li t5,1
	subw s1,s4,t5
	mv t6,s1
	j .Brun_program65
.Brun_program65:
	lla t5,program
	li s9,4
	mulw s9,s9,s2
	add s1,t5,s9
	lw t5,0(s1)
	li s1,91
	subw s9,t5,s1
	seqz t5,s9
	mv s5,s2
	mv s4,t6
	bne t5,zero,.Brun_program70
	j .Brun_program51
.Brun_program70:
	addi t5,t6,1
	mv s5,s2
	mv s4,t5
	j .Brun_program51
.Brun_program72:
	li t5,93
	subw s9,s3,t5
	seqz t5,s9
	bne t5,zero,.Brun_program74
	j .Brun_program84
.Brun_program74:
	lla t5,tape
	li s9,4
	mulw s9,s9,t3
	add t6,t5,s9
	lw t5,0(t6)
	subw s9,t5,zero
	seqz t5,s9
	bne t5,zero,.Brun_program78
	j .Brun_program80
.Brun_program78:
	li t5,1
	li t0,-2120
	add t0,t0,s0
	ld s10,0(t0)
	subw t6,s10,t5
	mv s10,t2
	sd s10,24(t0)
	mv s10,t3
	sd s10,32(t0)
	mv s10,t4
	sd s10,40(t0)
	mv s10,t6
	sd s10,48(t0)
	j .Brun_program15
.Brun_program80:
	li t5,1
	li t0,-2120
	add t0,t0,s0
	ld s10,0(t0)
	subw t6,s10,t5
	li s9,4
	mulw s9,s9,t6
	li t1,-2064
	add s9,s9,t1
	add t5,s0,s9
	lw t6,0(t5)
	mv s10,t6
	sd s10,24(t0)
	mv s10,t3
	sd s10,32(t0)
	mv s10,t4
	sd s10,40(t0)
	ld s9,0(t0)
	mv s10,s9
	sd s10,48(t0)
	j .Brun_program15
.Brun_program84:
	li t5,46
	subw s9,s3,t5
	seqz t5,s9
	bne t5,zero,.Brun_program86
	j .Brun_program93
.Brun_program86:
	lla t5,output_length
	lw t6,0(t5)
	lla t5,output
	li s9,4
	mulw s9,s9,t6
	add t6,t5,s9
	lla t5,tape
	li s9,4
	mulw s9,s9,t3
	add s1,t5,s9
	lw t5,0(s1)
	sw t5,0(t6)
	lla t5,output_length
	lw t6,0(t5)
	addi t5,t6,1
	lla t6,output_length
	sw t5,0(t6)
	mv s10,t2
	li t0,-2096
	add t0,t0,s0
	sd s10,0(t0)
	mv s10,t3
	sd s10,8(t0)
	mv s10,t4
	sd s10,16(t0)
	ld s9,-24(t0)
	mv s10,s9
	sd s10,24(t0)
	j .Brun_program15
.Brun_program93:
	li t5,44
	subw s9,s3,t5
	seqz t5,s9
	mv s10,t2
	li t0,-2096
	add t0,t0,s0
	sd s10,0(t0)
	mv s10,t3
	sd s10,8(t0)
	mv s10,t4
	sd s10,16(t0)
	ld s9,-24(t0)
	mv s10,s9
	sd s10,24(t0)
	bne t5,zero,.Brun_program95
	j .Brun_program15
.Brun_program95:
	lla t5,input_length
	lw t6,0(t5)
	slt s10,t4,t6
	li t0,-2104
	add t0,t0,s0
	sd s10,0(t0)
	xori s10,s10,1
	sd s10,0(t0)
	ld s10,0(t0)
	bne s10,zero,.Brun_program98
	j .Brun_program100
.Brun_program98:
	lla t5,tape
	li s9,4
	mulw s9,s9,t3
	add t6,t5,s9
	sw zero,0(t6)
	mv s10,t2
	li t0,-2096
	add t0,t0,s0
	sd s10,0(t0)
	mv s10,t3
	sd s10,8(t0)
	mv s10,t4
	sd s10,16(t0)
	ld s9,-24(t0)
	mv s10,s9
	sd s10,24(t0)
	j .Brun_program15
.Brun_program100:
	lla t5,tape
	li s9,4
	mulw s9,s9,t3
	add t6,t5,s9
	lla t5,input
	li s9,4
	mulw s9,s9,t4
	add s1,t5,s9
	lw t5,0(s1)
	sw t5,0(t6)
	addi t5,t4,1
	mv s10,t2
	li t0,-2096
	add t0,t0,s0
	sd s10,0(t0)
	mv s10,t3
	sd s10,8(t0)
	mv s10,t5
	sd s10,16(t0)
	ld s9,-24(t0)
	mv s10,s9
	sd s10,24(t0)
	j .Brun_program15
	.size	run_program, .-run_program
	.align	1
	.global	output_
	.type	output_	@function
output_:
	addi sp,sp,-64
	sd ra,56(sp)
	sd s0,48(sp)
	addi s0,sp,64
.Boutput_0:
	mv t2,zero
	j .Boutput_1
.Boutput_1:
	lla t3,output_length
	lw t4,0(t3)
	slt t3,t2,t4
	bne t3,zero,.Boutput_5
	j .Boutput_9
.Boutput_5:
	lla t3,output
	li s9,4
	mulw s9,s9,t2
	add t4,t3,s9
	lw t3,0(t4)
	mv a0,t3
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	call putch
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	addi t3,t2,1
	mv t2,t3
	j .Boutput_1
.Boutput_9:
	ld ra,56(sp)
	ld s0,48(sp)
	addi sp,sp,64
	jr ra
	.size	output_, .-output_
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-32
	sd ra,24(sp)
	sd s0,16(sp)
	addi s0,sp,32
.Bmain0:
	call read_program
	mv a0,zero
	call _sysy_starttime
	call run_program
	mv a0,zero
	call _sysy_stoptime
	call output_
	mv a0,zero
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
	.size	main, .-main
	.global	program_length
	.bss
	.align	2
	.type	program_length,@object
	.size	program_length,4
program_length:
	.zero	4
	.global	program
	.bss
	.align	3
	.type	program,@object
	.size	program,262144
program:
	.zero	262144
	.global	tape
	.bss
	.align	3
	.type	tape,@object
	.size	tape,262144
tape:
	.zero	262144
	.global	input
	.bss
	.align	3
	.type	input,@object
	.size	input,262144
input:
	.zero	262144
	.global	input_length
	.bss
	.align	2
	.type	input_length,@object
	.size	input_length,4
input_length:
	.zero	4
	.global	output
	.bss
	.align	3
	.type	output,@object
	.size	output,262144
output:
	.zero	262144
	.global	output_length
	.bss
	.align	2
	.type	output_length,@object
	.size	output_length,4
output_length:
	.zero	4
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
