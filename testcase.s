	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	next_char
	.type	next_char	@function
next_char:
	addi sp,sp,-16
	sd ra,8(sp)
	sd s0,0(sp)
	addi s0,sp,16
.Bnext_char0:
	call getch
	mv t2,a0
	lla t3,last_char
	sw t2,0(t3)
	lla t2,last_char
	lw t3,0(t2)
	mv a0,t3
	ld ra,8(sp)
	ld s0,0(sp)
	addi sp,sp,16
	jr ra
	.size	next_char, .-next_char
	.align	1
	.global	is_space
	.type	is_space	@function
is_space:
	addi sp,sp,-32
	sd ra,24(sp)
	sd s0,16(sp)
	addi s0,sp,32
.Bis_space1:
	sw a0,-24(s0)
	j .Bis_space3
.Bis_space3:
	lw t2,-24(s0)
	li t3,32
	subw t4,t2,t3
	seqz t2,t4
	bne t2,zero,.Bis_space7
	j .Bis_space9
.Bis_space6:
	li a0,0
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
.Bis_space7:
	li a0,1
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
.Bis_space8:
	li a0,0
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
.Bis_space9:
	lw t2,-24(s0)
	li t3,10
	subw t4,t2,t3
	seqz t2,t4
	bne t2,zero,.Bis_space7
	j .Bis_space8
	.size	is_space, .-is_space
	.align	1
	.global	is_num
	.type	is_num	@function
is_num:
	addi sp,sp,-32
	sd ra,24(sp)
	sd s0,16(sp)
	addi s0,sp,32
.Bis_num1:
	sw a0,-24(s0)
	j .Bis_num3
.Bis_num3:
	lw t2,-24(s0)
	li t3,48
	slt t4,t2,t3
	xori t4,t4,1
	bne t4,zero,.Bis_num9
	j .Bis_num8
.Bis_num6:
	li a0,0
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
.Bis_num7:
	li a0,1
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
.Bis_num8:
	li a0,0
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
.Bis_num9:
	lw t2,-24(s0)
	li t3,57
	subw t4,t2,t3
	sgtz t2,t4
	xori t2,t2,1
	bne t2,zero,.Bis_num7
	j .Bis_num8
	.size	is_num, .-is_num
	.align	1
	.global	next_token
	.type	next_token	@function
next_token:
	addi sp,sp,-48
	sd ra,40(sp)
	sd s0,32(sp)
	addi s0,sp,48
.Bnext_token0:
	j .Bnext_token1
.Bnext_token1:
	lla t2,last_char
	lw a0,0(t2)
	sd t2,-24(s0)
	call is_space
	ld t2,-24(s0)
	mv t2,a0
	li t3,0
	subw t4,t2,t3
	seqz t2,t4
	xori t2,t2,1
	bne t2,zero,.Bnext_token5
	j .Bnext_token7
.Bnext_token5:
	sd t2,-24(s0)
	sd t3,-32(s0)
	sd t4,-40(s0)
	call next_char
	ld t4,-40(s0)
	ld t3,-32(s0)
	ld t2,-24(s0)
	mv t2,a0
	j .Bnext_token1
.Bnext_token7:
	j .Bnext_token8
.Bnext_token8:
	lla t3,last_char
	lw a0,0(t3)
	sd t2,-24(s0)
	sd t3,-32(s0)
	sd t4,-40(s0)
	call is_num
	ld t4,-40(s0)
	ld t3,-32(s0)
	ld t2,-24(s0)
	mv t3,a0
	li t4,0
	subw t5,t3,t4
	seqz t3,t5
	xori t3,t3,1
	bne t3,zero,.Bnext_token14
	j .Bnext_token28
.Bnext_token12:
	lla t2,cur_token
	lw t3,0(t2)
	mv a0,t3
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
.Bnext_token14:
	lla t2,last_char
	lw t3,0(t2)
	li t2,48
	subw t4,t3,t2
	lla t2,num
	sw t4,0(t2)
	j .Bnext_token17
.Bnext_token17:
	sd t2,-24(s0)
	sd t3,-32(s0)
	sd t4,-40(s0)
	sd t5,-48(s0)
	call next_char
	ld t5,-48(s0)
	ld t4,-40(s0)
	ld t3,-32(s0)
	ld t2,-24(s0)
	mv t2,a0
	sd t2,-24(s0)
	sd t3,-32(s0)
	sd t4,-40(s0)
	sd t5,-48(s0)
	call is_num
	ld t5,-48(s0)
	ld t4,-40(s0)
	ld t3,-32(s0)
	ld t2,-24(s0)
	mv t3,a0
	li t4,0
	subw t5,t3,t4
	seqz t3,t5
	xori t3,t3,1
	bne t3,zero,.Bnext_token21
	j .Bnext_token27
.Bnext_token21:
	lla t3,num
	lw t2,0(t3)
	li t3,10
	mulw t4,t2,t3
	lla t2,last_char
	lw t3,0(t2)
	addw t2,t4,t3
	li t3,48
	subw t4,t2,t3
	lla t2,num
	sw t4,0(t2)
	j .Bnext_token17
.Bnext_token27:
	lla t2,cur_token
	li t3,0
	sw t3,0(t2)
	j .Bnext_token12
.Bnext_token28:
	lla t2,last_char
	lw t3,0(t2)
	lla t2,other
	sw t3,0(t2)
	sd t2,-24(s0)
	sd t3,-32(s0)
	sd t4,-40(s0)
	sd t5,-48(s0)
	call next_char
	ld t5,-48(s0)
	ld t4,-40(s0)
	ld t3,-32(s0)
	ld t2,-24(s0)
	mv t2,a0
	lla t3,cur_token
	li t4,1
	sw t4,0(t3)
	j .Bnext_token12
	.size	next_token, .-next_token
	.align	1
	.global	panic
	.type	panic	@function
panic:
	addi sp,sp,-16
	sd ra,8(sp)
	sd s0,0(sp)
	addi s0,sp,16
.Bpanic0:
	li a0,112
	call putch
	li a0,97
	call putch
	li a0,110
	call putch
	li a0,105
	call putch
	li a0,99
	call putch
	li a0,33
	call putch
	li a0,10
	call putch
	li t2,0
	li t3,1
	subw t4,t2,t3
	mv a0,t4
	ld ra,8(sp)
	ld s0,0(sp)
	addi sp,sp,16
	jr ra
	.size	panic, .-panic
	.align	1
	.global	get_op_prec
	.type	get_op_prec	@function
get_op_prec:
	addi sp,sp,-32
	sd ra,24(sp)
	sd s0,16(sp)
	addi s0,sp,32
.Bget_op_prec1:
	sw a0,-24(s0)
	j .Bget_op_prec3
.Bget_op_prec3:
	lw t2,-24(s0)
	li t3,43
	subw t4,t2,t3
	seqz t2,t4
	bne t2,zero,.Bget_op_prec7
	j .Bget_op_prec8
.Bget_op_prec6:
	j .Bget_op_prec11
.Bget_op_prec7:
	li a0,10
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
.Bget_op_prec8:
	lw t2,-24(s0)
	li t3,45
	subw t4,t2,t3
	seqz t2,t4
	bne t2,zero,.Bget_op_prec7
	j .Bget_op_prec6
.Bget_op_prec11:
	lw t2,-24(s0)
	li t3,42
	subw t4,t2,t3
	seqz t2,t4
	bne t2,zero,.Bget_op_prec15
	j .Bget_op_prec19
.Bget_op_prec14:
	li a0,0
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
.Bget_op_prec15:
	li a0,20
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
.Bget_op_prec16:
	lw t2,-24(s0)
	li t3,37
	subw t4,t2,t3
	seqz t2,t4
	bne t2,zero,.Bget_op_prec15
	j .Bget_op_prec14
.Bget_op_prec19:
	lw t2,-24(s0)
	li t3,47
	subw t4,t2,t3
	seqz t2,t4
	bne t2,zero,.Bget_op_prec15
	j .Bget_op_prec16
	.size	get_op_prec, .-get_op_prec
	.align	1
	.global	stack_push
	.type	stack_push	@function
stack_push:
	addi sp,sp,-32
	sd ra,24(sp)
	sd s0,16(sp)
	addi s0,sp,32
.Bstack_push2:
	sd a0,-32(s0)
	sw a1,-24(s0)
	ld t2,-32(s0)
	ld t3,-32(s0)
	lw t4,0(t3)
	li t3,1
	addw t5,t4,t3
	sw t5,0(t2)
	ld t2,-32(s0)
	ld t3,-32(s0)
	lw t4,0(t3)
	li t3,4
	mulw t3,t3,t4
	addi t3,t3,0
	add t4,t2,t3
	lw t2,-24(s0)
	sw t2,0(t4)
	nop
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
	.size	stack_push, .-stack_push
	.align	1
	.global	stack_pop
	.type	stack_pop	@function
stack_pop:
	addi sp,sp,-32
	sd ra,24(sp)
	sd s0,16(sp)
	addi s0,sp,32
.Bstack_pop1:
	sd a0,-32(s0)
	ld t2,-32(s0)
	ld t3,-32(s0)
	lw t4,0(t3)
	li t3,4
	mulw t3,t3,t4
	addi t3,t3,0
	add t4,t2,t3
	lw t2,0(t4)
	sw t2,-24(s0)
	ld t2,-32(s0)
	ld t3,-32(s0)
	lw t4,0(t3)
	li t3,1
	subw t5,t4,t3
	sw t5,0(t2)
	lw t2,-24(s0)
	mv a0,t2
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
	.size	stack_pop, .-stack_pop
	.align	1
	.global	stack_peek
	.type	stack_peek	@function
stack_peek:
	addi sp,sp,-32
	sd ra,24(sp)
	sd s0,16(sp)
	addi s0,sp,32
.Bstack_peek1:
	sd a0,-24(s0)
	ld t2,-24(s0)
	ld t3,-24(s0)
	lw t4,0(t3)
	li t3,4
	mulw t3,t3,t4
	addi t3,t3,0
	add t4,t2,t3
	lw t2,0(t4)
	mv a0,t2
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
	.size	stack_peek, .-stack_peek
	.align	1
	.global	stack_size
	.type	stack_size	@function
stack_size:
	addi sp,sp,-32
	sd ra,24(sp)
	sd s0,16(sp)
	addi s0,sp,32
.Bstack_size1:
	sd a0,-24(s0)
	ld t2,-24(s0)
	lw t3,0(t2)
	mv a0,t3
	ld ra,24(sp)
	ld s0,16(sp)
	addi sp,sp,32
	jr ra
	.size	stack_size, .-stack_size
	.align	1
	.global	eval_op
	.type	eval_op	@function
eval_op:
	addi sp,sp,-48
	sd ra,40(sp)
	sd s0,32(sp)
	addi s0,sp,48
.Beval_op3:
	sw a0,-40(s0)
	sw a1,-32(s0)
	sw a2,-24(s0)
	j .Beval_op7
.Beval_op7:
	lw t2,-40(s0)
	li t3,43
	subw t4,t2,t3
	seqz t2,t4
	bne t2,zero,.Beval_op11
	j .Beval_op10
.Beval_op10:
	j .Beval_op15
.Beval_op11:
	lw t2,-32(s0)
	lw t3,-24(s0)
	addw t4,t2,t3
	mv a0,t4
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
.Beval_op15:
	lw t2,-40(s0)
	li t3,45
	subw t4,t2,t3
	seqz t2,t4
	bne t2,zero,.Beval_op19
	j .Beval_op18
.Beval_op18:
	j .Beval_op23
.Beval_op19:
	lw t2,-32(s0)
	lw t3,-24(s0)
	subw t4,t2,t3
	mv a0,t4
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
.Beval_op23:
	lw t2,-40(s0)
	li t3,42
	subw t4,t2,t3
	seqz t2,t4
	bne t2,zero,.Beval_op27
	j .Beval_op26
.Beval_op26:
	j .Beval_op31
.Beval_op27:
	lw t2,-32(s0)
	lw t3,-24(s0)
	mulw t4,t2,t3
	mv a0,t4
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
.Beval_op31:
	lw t2,-40(s0)
	li t3,47
	subw t4,t2,t3
	seqz t2,t4
	bne t2,zero,.Beval_op35
	j .Beval_op34
.Beval_op34:
	j .Beval_op39
.Beval_op35:
	lw t2,-32(s0)
	lw t3,-24(s0)
	divw t4,t2,t3
	mv a0,t4
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
.Beval_op39:
	lw t2,-40(s0)
	li t3,37
	subw t4,t2,t3
	seqz t2,t4
	bne t2,zero,.Beval_op43
	j .Beval_op42
.Beval_op42:
	li a0,0
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
.Beval_op43:
	lw t2,-32(s0)
	lw t3,-24(s0)
	divw t4,t2,t3
	mulw t5,t4,t3
	subw t3,t2,t5
	mv a0,t3
	ld ra,40(sp)
	ld s0,32(sp)
	addi sp,sp,48
	jr ra
	.size	eval_op, .-eval_op
	.align	1
	.global	eval
	.type	eval	@function
eval:
	li t1,-2160
	add sp,sp,t1
	li t0,2152
	add t0,t0,sp
	sd ra,0(t0)
	sd s0,-8(t0)
	li t1,2160
	add s0,sp,t1
.Beval0:
	li t1,-2064
	add a0,s0,t1
	li a1,0
	li a2,1024
	call memset
	addi a0,s0,-1040
	li a1,0
	li a2,1024
	call memset
	j .Beval5
.Beval5:
	lla t2,cur_token
	lw t3,0(t2)
	li t2,0
	subw t4,t3,t2
	seqz t2,t4
	xori t2,t2,1
	bne t2,zero,.Beval12
	j .Beval8
.Beval8:
	li t1,-2064
	add a0,s0,t1
	lla t2,num
	lw a1,0(t2)
	li t0,-2128
	add t0,t0,s0
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	call stack_push
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	call next_token
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t2,a0
	j .Beval14
.Beval12:
	li t0,-2128
	add t0,t0,s0
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	call panic
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t3,a0
	mv a0,t3
	li t0,2152
	add t0,t0,sp
	ld ra,0(t0)
	ld s0,-8(t0)
	li t1,2160
	add sp,sp,t1
	jr ra
.Beval14:
	lla t3,cur_token
	lw t2,0(t3)
	li t3,1
	subw t4,t2,t3
	seqz t2,t4
	bne t2,zero,.Beval17
	j .Beval20
.Beval17:
	lla t2,other
	lw t3,0(t2)
	li t0,-2072
	add t0,t0,s0
	sw t3,0(t0)
	j .Beval22
.Beval20:
	li t0,-2128
	add t0,t0,s0
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	call next_token
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t2,a0
	j .Beval68
.Beval22:
	lw a0,0(t0)
	li t0,-2128
	add t0,t0,s0
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	call get_op_prec
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t3,a0
	li t4,0
	subw t5,t4,t3
	seqz t3,t5
	bne t3,zero,.Beval28
	j .Beval26
.Beval26:
	li t0,-2128
	add t0,t0,s0
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call next_token
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t2,a0
	j .Beval30
.Beval28:
	j .Beval20
.Beval29:
	j .Beval26
.Beval30:
	addi a0,s0,-1040
	li t0,-2128
	add t0,t0,s0
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call stack_size
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t3,a0
	li t4,0
	subw t5,t3,t4
	seqz t3,t5
	xori t3,t3,1
	bne t3,zero,.Beval52
	j .Beval49
.Beval34:
	addi a0,s0,-1040
	li t0,-2128
	add t0,t0,s0
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call stack_pop
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t2,a0
	sw t2,32(t0)
	li t1,-2064
	add a0,s0,t1
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call stack_pop
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t2,a0
	sw t2,40(t0)
	li t1,-2064
	add a0,s0,t1
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call stack_pop
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t2,a0
	sw t2,48(t0)
	li t1,-2064
	add a0,s0,t1
	lw a0,32(t0)
	lw a1,48(t0)
	lw a2,40(t0)
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call eval_op
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t2,a0
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call stack_push
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	j .Beval30
.Beval49:
	addi a0,s0,-1040
	lw a1,0(t0)
	li t0,-2128
	add t0,t0,s0
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call stack_push
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	j .Beval59
.Beval52:
	addi a0,s0,-1040
	li t0,-2128
	add t0,t0,s0
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call stack_peek
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t3,a0
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call get_op_prec
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t2,a0
	lw a0,0(t0)
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call get_op_prec
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t4,a0
	slt t5,t2,t4
	xori t5,t5,1
	bne t5,zero,.Beval34
	j .Beval49
.Beval59:
	lla t2,cur_token
	lw t3,0(t2)
	li t2,0
	subw t4,t3,t2
	seqz t2,t4
	xori t2,t2,1
	bne t2,zero,.Beval66
	j .Beval62
.Beval62:
	li t1,-2064
	add a0,s0,t1
	lla t2,num
	lw a1,0(t2)
	li t0,-2128
	add t0,t0,s0
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call stack_push
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call next_token
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t2,a0
	j .Beval14
.Beval66:
	li t0,-2128
	add t0,t0,s0
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call panic
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t3,a0
	mv a0,t3
	li t0,2152
	add t0,t0,sp
	ld ra,0(t0)
	ld s0,-8(t0)
	li t1,2160
	add sp,sp,t1
	jr ra
.Beval68:
	addi a0,s0,-1040
	li t0,-2128
	add t0,t0,s0
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call stack_size
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t2,a0
	li t3,0
	subw t4,t2,t3
	seqz t2,t4
	xori t2,t2,1
	bne t2,zero,.Beval72
	j .Beval87
.Beval72:
	addi a0,s0,-1040
	li t0,-2128
	add t0,t0,s0
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call stack_pop
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t2,a0
	sw t2,8(t0)
	li t1,-2064
	add a0,s0,t1
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call stack_pop
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t2,a0
	sw t2,16(t0)
	li t1,-2064
	add a0,s0,t1
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call stack_pop
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t2,a0
	sw t2,24(t0)
	li t1,-2064
	add a0,s0,t1
	lw a0,8(t0)
	lw a1,24(t0)
	lw a2,16(t0)
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call eval_op
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t2,a0
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call stack_push
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	j .Beval68
.Beval87:
	li t1,-2064
	add a0,s0,t1
	li t0,-2128
	add t0,t0,s0
	sd t2,0(t0)
	sd t3,-8(t0)
	sd t4,-16(t0)
	sd t5,-24(t0)
	call stack_peek
	ld t5,-24(t0)
	ld t4,-16(t0)
	ld t3,-8(t0)
	ld t2,0(t0)
	mv t3,a0
	mv a0,t3
	li t0,2152
	add t0,t0,sp
	ld ra,0(t0)
	ld s0,-8(t0)
	li t1,2160
	add sp,sp,t1
	jr ra
	.size	eval, .-eval
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-64
	sd ra,56(sp)
	sd s0,48(sp)
	addi s0,sp,64
.Bmain0:
	call getint
	mv t2,a0
	sw t2,-24(s0)
	sd t2,-32(s0)
	call getch
	ld t2,-32(s0)
	mv t2,a0
	sd t2,-32(s0)
	call next_token
	ld t2,-32(s0)
	mv t3,a0
	j .Bmain5
.Bmain5:
	lw t4,-24(s0)
	li t5,0
	subw t6,t4,t5
	seqz t4,t6
	xori t4,t4,1
	bne t4,zero,.Bmain8
	j .Bmain12
.Bmain8:
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	call eval
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	mv t2,a0
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	call putint
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	li a0,10
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	call putch
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
	lw t3,-24(s0)
	li t4,1
	subw t5,t3,t4
	sw t5,-24(s0)
	j .Bmain5
.Bmain12:
	li a0,0
	ld ra,56(sp)
	ld s0,48(sp)
	addi sp,sp,64
	jr ra
	.size	main, .-main
	.global	last_char
	.data
	.align	2
	.type	last_char,@object
	.size	last_char,4
last_char:
	.word	32
	.global	num
	.bss
	.align	2
	.type	num,@object
	.size	num,4
num:
	.zero	4
	.global	other
	.bss
	.align	2
	.type	other,@object
	.size	other,4
other:
	.zero	4
	.global	cur_token
	.bss
	.align	2
	.type	cur_token,@object
	.size	cur_token,4
cur_token:
	.zero	4
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
