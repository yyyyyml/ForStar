	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-624
	sd ra,616(sp)
	sd s0,608(sp)
	addi s0,sp,616
.Bmain0:
	lw t0,-612(s0)
	sw t0,-412(s0)
	lla t0,a
	lw t1,4(t0)
	sw t1,-408(s0)
	lw t0,-376(s0)
	mv a0,t0
	ld ra,616(sp)
	ld s0,608(sp)
	addi sp,sp,624
	jr ra
	.size	main, .-main
	.global	a
	.bss
	.align	3
	.type	a,@object
	.size	a,400
a:
	.zero	400
	.global	b
	.bss
	.align	3
	.type	b,@object
	.size	b,24
b:
	.zero	24
	.global	c1
	.bss
	.align	3
	.type	c1,@object
	.size	c1,8000
c1:
	.zero	8000
	.global	main_aaa
	.bss
	.align	3
	.type	main_aaa,@object
	.size	main_aaa,40
main_aaa:
	.zero	40
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
