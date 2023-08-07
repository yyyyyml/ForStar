	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	main
	.type	main	@function
main:
0	addi sp,sp,-32
1	sd ra,24(sp)
2	sd s0,16(sp)
3	addi s0,sp,32
.Bmain0:
4	li t3,1
5	li t4,2
6	li t2,1
7	beq t3,t4,.Bmain2
8	j .Bmain3
.Bmain2:
9	li t2,2
10	j .Bmain3
.Bmain3:
11	li t4,2
12	mv t3,t2
13	bne t2,t4,.Bmain6
14	j .Bmain7
.Bmain6:
15	li t3,2
16	j .Bmain7
.Bmain7:
17	li t4,2
18	mv t2,t3
19	bgt t3,t4,.Bmain10
20	j .Bmain11
.Bmain10:
21	li t2,2
22	j .Bmain11
.Bmain11:
23	li t3,2
24	blt t2,t3,.Bmain14
25	j .Bmain15
.Bmain14:
26	j .Bmain15
.Bmain15:
27	lla t2,.F0
28	flw f0,0(t2)
29	lla t2,.F1
30	flw f1,0(t2)
31	fgt.s t2,f0,f1
32	bnez t2,.Bmain18
33	j .Bmain17
.Bmain17:
34	mv a0,zero
35	ld ra,24(sp)
36	ld s0,16(sp)
37	addi sp,sp,32
38	jr ra
.Bmain18:
39	j .Bmain17
	.size	main, .-main
	.align	2
.F0:
	.word	1080033280
	.align	2
.F1:
	.word	1083179008
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
