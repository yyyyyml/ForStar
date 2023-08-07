	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	quick_read
	.type	quick_read	@function
quick_read:
0	addi sp,sp,-128
1	sd ra,120(sp)
2	sd s0,112(sp)
3	addi s0,sp,128
.Bquick_read0:
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	sd s1,-72(s0)
4	call getch
	ld s1,-72(s0)
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
5	mv s2,a0
6	mv t2,zero
7	mv t3,s2
8	mv t4,zero
9	j .Bquick_read2
.Bquick_read2:
10	slti s2,t3,48
11	bnez s2,.Bquick_read9
12	j .Bquick_read7
.Bquick_read7:
13	li s5,57
14	subw s9,t3,s5
15	sgtz s5,s9
16	mv s2,t2
17	mv s3,t3
18	mv s4,t4
19	bnez s5,.Bquick_read9
20	j .Bquick_read15
.Bquick_read9:
21	li s6,45
22	subw s9,t3,s6
23	seqz s6,s9
24	mv s5,t2
25	bnez s6,.Bquick_read14
26	j .Bquick_read11
.Bquick_read11:
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
27	call getch
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
28	mv s6,a0
29	mv t2,s5
30	mv t3,s6
31	j .Bquick_read2
.Bquick_read14:
32	li s5,1
33	j .Bquick_read11
.Bquick_read15:
34	slti t5,s3,48
35	xori t5,t5,1
36	bnez t5,.Bquick_read25
37	j .Bquick_read27
.Bquick_read20:
38	li t2,10
39	mulw t3,s4,t2
40	addw t2,t3,s3
41	li t3,48
42	subw t4,t2,t3
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	sd s1,-72(s0)
	sd s2,-80(s0)
	sd s3,-88(s0)
	sd s4,-96(s0)
43	call getch
	ld s4,-96(s0)
	ld s3,-88(s0)
	ld s2,-80(s0)
	ld s1,-72(s0)
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
44	mv t2,a0
45	mv s3,t2
46	mv s4,t4
47	j .Bquick_read15
.Bquick_read25:
48	li t2,57
49	subw s9,s3,t2
50	sgtz t6,s9
51	xori t6,t6,1
52	bnez t6,.Bquick_read20
53	j .Bquick_read27
.Bquick_read27:
54	subw s9,s2,zero
55	seqz s1,s9
56	xori s1,s1,1
57	bnez s1,.Bquick_read29
58	j .Bquick_read31
.Bquick_read29:
59	mv t2,s4
60	mv a0,t2
61	ld ra,120(sp)
62	ld s0,112(sp)
63	addi sp,sp,128
64	jr ra
.Bquick_read31:
65	mv a0,s4
66	ld ra,120(sp)
67	ld s0,112(sp)
68	addi sp,sp,128
69	jr ra
	.size	quick_read, .-quick_read
	.align	1
	.global	sortA
	.type	sortA	@function
sortA:
0	addi sp,sp,-32
1	sd ra,24(sp)
2	sd s0,16(sp)
3	addi s0,sp,32
4	mv t2,a0
.BsortA1:
5	mv t3,zero
6	mv t4,t2
7	j .BsortA2
.BsortA2:
8	lla t2,n
9	lw t5,0(t2)
10	slt t2,t3,t5
11	bnez t2,.BsortA7
12	j .BsortA9
.BsortA7:
13	addi s1,t3,1
14	mv t2,t3
15	mv t5,t4
16	mv t6,s1
17	j .BsortA10
.BsortA9:
18	ld ra,24(sp)
19	ld s0,16(sp)
20	addi sp,sp,32
21	jr ra
.BsortA10:
22	lla s1,n
23	lw s2,0(s1)
24	slt s1,t6,s2
25	bnez s1,.BsortA18
26	j .BsortA16
.BsortA16:
27	addi s1,t2,1
28	mv t3,s1
29	mv t4,t5
30	j .BsortA2
.BsortA18:
31	li s9,4
32	mulw s9,s9,t2
33	add t3,t5,s9
34	lw t4,0(t3)
35	li s9,4
36	mulw s9,s9,t6
37	add t3,t5,s9
38	lw s1,0(t3)
39	subw s9,t4,s1
40	sgtz t3,s9
41	bnez t3,.BsortA26
42	j .BsortA24
.BsortA24:
43	addi t3,t6,1
44	mv t6,t3
45	j .BsortA10
.BsortA26:
46	li s9,4
47	mulw s9,s9,t2
48	add t3,t5,s9
49	lw t4,0(t3)
50	li s9,4
51	mulw s9,s9,t2
52	add t3,t5,s9
53	li s9,4
54	mulw s9,s9,t6
55	add s1,t5,s9
56	lw s2,0(s1)
57	sw s2,0(t3)
58	li s9,4
59	mulw s9,s9,t6
60	add t3,t5,s9
61	sw t4,0(t3)
62	j .BsortA24
	.size	sortA, .-sortA
	.align	1
	.global	sortB
	.type	sortB	@function
sortB:
0	addi sp,sp,-48
1	sd ra,40(sp)
2	sd s0,32(sp)
3	addi s0,sp,48
4	mv t2,a0
.BsortB1:
5	li s2,100
6	mv s3,s2
7	mv t5,zero
8	mv t6,s3
9	mv s1,t2
10	j .BsortB3
.BsortB3:
11	lla t2,n
12	lw s2,0(t2)
13	slt t2,t5,s2
14	bnez t2,.BsortB9
15	j .BsortB21
.BsortB9:
16	li s9,4
17	mulw s9,s9,t5
18	add s2,s1,s9
19	lw s3,0(s2)
20	lla s2,cnt
21	li s9,4
22	mulw s9,s9,s3
23	add s3,s2,s9
24	li s9,4
25	mulw s9,s9,t5
26	add s2,s1,s9
27	lw s4,0(s2)
28	lla s2,cnt
29	li s9,4
30	mulw s9,s9,s4
31	add s4,s2,s9
32	lw s2,0(s4)
33	addi s4,s2,1
34	sw s4,0(s3)
35	li s9,4
36	mulw s9,s9,t5
37	add s2,s1,s9
38	lw s3,0(s2)
39	subw s9,s3,t6
40	sgtz s2,s9
41	mv t2,t6
42	bnez s2,.BsortB25
43	j .BsortB22
.BsortB21:
44	mv s2,zero
45	mv s3,zero
46	mv s4,t6
47	mv s5,s1
48	j .BsortB28
.BsortB22:
49	addi s6,t5,1
50	mv t5,s6
51	mv t6,t2
52	j .BsortB3
.BsortB25:
53	li s9,4
54	mulw s9,s9,t5
55	add t6,s1,s9
56	lw s6,0(t6)
57	mv t2,s6
58	j .BsortB22
.BsortB28:
59	subw s9,s3,s4
60	sgtz t3,s9
61	xori t3,t3,1
62	bnez t3,.BsortB34
63	j .BsortB37
.BsortB34:
64	lla s7,cnt
65	li s9,4
66	mulw s9,s9,s3
67	add s8,s7,s9
68	lw s7,0(s8)
69	mv t2,s2
70	mv t5,s3
71	mv t6,s7
72	mv s1,s4
73	mv s10,s5
	sd s10,-24(s0)
74	j .BsortB38
.BsortB37:
75	ld ra,40(sp)
76	ld s0,32(sp)
77	addi sp,sp,48
78	jr ra
.BsortB38:
79	subw s9,t6,zero
80	seqz t4,s9
81	xori t4,t4,1
82	bnez t4,.BsortB45
83	j .BsortB49
.BsortB45:
84	li s9,4
85	mulw s9,s9,t2
	ld s10,-24(s0)
86	add s7,s10,s9
87	sw t5,0(s7)
88	addi s7,t2,1
89	li s8,1
90	subw s6,t6,s8
91	mv t2,s7
92	mv t6,s6
93	j .BsortB38
.BsortB49:
94	addi t6,t5,1
95	mv s2,t2
96	mv s3,t6
97	mv s4,s1
	ld s10,-24(s0)
98	mv s5,s10
99	j .BsortB28
	.size	sortB, .-sortB
	.align	1
	.global	sortC
	.type	sortC	@function
sortC:
0	addi sp,sp,-32
1	sd ra,24(sp)
2	sd s0,16(sp)
3	addi s0,sp,32
4	mv t2,a0
.BsortC1:
5	mv t3,t2
6	mv t4,zero
7	j .BsortC2
.BsortC2:
8	lla t2,n
9	lw t5,0(t2)
10	slt t2,t4,t5
11	bnez t2,.BsortC7
12	j .BsortC9
.BsortC7:
13	addi s2,t4,1
14	mv t2,t4
15	mv t5,s2
16	mv t6,t3
17	mv s1,t4
18	j .BsortC10
.BsortC9:
19	ld ra,24(sp)
20	ld s0,16(sp)
21	addi sp,sp,32
22	jr ra
.BsortC10:
23	lla s2,n
24	lw s3,0(s2)
25	slt s2,t5,s3
26	bnez s2,.BsortC25
27	j .BsortC17
.BsortC17:
28	li s9,4
29	mulw s9,s9,s1
30	add s2,t6,s9
31	lw s3,0(s2)
32	li s9,4
33	mulw s9,s9,s1
34	add s2,t6,s9
35	li s9,4
36	mulw s9,s9,t2
37	add s4,t6,s9
38	lw s5,0(s4)
39	sw s5,0(s2)
40	li s9,4
41	mulw s9,s9,t2
42	add s2,t6,s9
43	sw s3,0(s2)
44	addi s2,s1,1
45	mv t3,t6
46	mv t4,s2
47	j .BsortC2
.BsortC25:
48	li s9,4
49	mulw s9,s9,t5
50	add t4,t6,s9
51	lw s2,0(t4)
52	li s9,4
53	mulw s9,s9,t2
54	add t4,t6,s9
55	lw s3,0(t4)
56	slt t4,s2,s3
57	mv t3,t2
58	bnez t4,.BsortC34
59	j .BsortC31
.BsortC31:
60	addi t4,t5,1
61	mv t2,t3
62	mv t5,t4
63	j .BsortC10
.BsortC34:
64	mv t3,t5
65	j .BsortC31
	.size	sortC, .-sortC
	.align	1
	.global	main
	.type	main	@function
main:
0	addi sp,sp,-96
1	sd ra,88(sp)
2	sd s0,80(sp)
3	addi s0,sp,96
.Bmain0:
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	sd s1,-72(s0)
4	call quick_read
	ld s1,-72(s0)
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
5	mv s2,a0
6	lla s3,n
7	sw s2,0(s3)
8	mv t2,zero
9	j .Bmain2
.Bmain2:
10	lla s2,n
11	lw s3,0(s2)
12	subw s9,t2,s3
13	seqz t3,s9
14	xori t3,t3,1
15	bnez t3,.Bmain6
16	j .Bmain16
.Bmain6:
17	lla s2,a
18	li s9,4
19	mulw s9,s9,t2
20	add s3,s2,s9
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	sd s1,-72(s0)
	sd s2,-80(s0)
	sd s3,-88(s0)
21	call quick_read
	ld s3,-88(s0)
	ld s2,-80(s0)
	ld s1,-72(s0)
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
22	mv s2,a0
23	sw s2,0(s3)
24	lla s2,b
25	li s9,4
26	mulw s9,s9,t2
27	add s3,s2,s9
28	lla s2,a
29	li s9,4
30	mulw s9,s9,t2
31	add s4,s2,s9
32	lw s2,0(s4)
33	sw s2,0(s3)
34	lla s2,c
35	li s9,4
36	mulw s9,s9,t2
37	add s3,s2,s9
38	lla s2,b
39	li s9,4
40	mulw s9,s9,t2
41	add s4,s2,s9
42	lw s2,0(s4)
43	sw s2,0(s3)
44	addi s2,t2,1
45	mv t2,s2
46	j .Bmain2
.Bmain16:
47	lla t3,a
48	addi s2,t3,0
49	mv a0,s2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	sd s1,-72(s0)
	sd s2,-80(s0)
50	call sortA
	ld s2,-80(s0)
	ld s1,-72(s0)
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
51	lla t3,b
52	addi s2,t3,0
53	mv a0,s2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	sd s1,-72(s0)
	sd s2,-80(s0)
54	call sortB
	ld s2,-80(s0)
	ld s1,-72(s0)
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
55	lla t3,c
56	addi s2,t3,0
57	mv a0,s2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
	sd s1,-72(s0)
	sd s2,-80(s0)
58	call sortC
	ld s2,-80(s0)
	ld s1,-72(s0)
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
59	mv t2,zero
60	j .Bmain20
.Bmain20:
61	lla t3,n
62	lw s2,0(t3)
63	subw t3,s2,t2
64	subw s9,t3,zero
65	seqz t4,s9
66	xori t4,t4,1
67	bnez t4,.Bmain25
68	j .Bmain42
.Bmain25:
69	lla t3,b
70	li s9,4
71	mulw s9,s9,t2
72	add s2,t3,s9
73	lla t3,b
74	li s9,4
75	mulw s9,s9,t2
76	add s3,t3,s9
77	lw t3,0(s3)
78	lla s3,a
79	li s9,4
80	mulw s9,s9,t2
81	add s4,s3,s9
82	lw s3,0(s4)
83	subw s4,t3,s3
84	sw s4,0(s2)
85	lla t3,c
86	li s9,4
87	mulw s9,s9,t2
88	add s2,t3,s9
89	lla t3,c
90	li s9,4
91	mulw s9,s9,t2
92	add s3,t3,s9
93	lw t3,0(s3)
94	lla s3,b
95	li s9,4
96	mulw s9,s9,t2
97	add s4,s3,s9
98	lw s3,0(s4)
99	subw s4,t3,s3
100	lla t3,a
101	li s9,4
102	mulw s9,s9,t2
103	add s3,t3,s9
104	lw t3,0(s3)
105	subw s3,s4,t3
106	sw s3,0(s2)
107	addi t3,t2,1
108	mv t2,t3
109	j .Bmain20
.Bmain42:
110	mv t2,zero
111	j .Bmain43
.Bmain43:
112	lla t3,n
113	lw t4,0(t3)
114	subw t3,t2,t4
115	subw s9,t3,zero
116	seqz t5,s9
117	xori t5,t5,1
118	bnez t5,.Bmain50
119	j .Bmain48
.Bmain48:
120	li t3,123
121	mv t4,t3
122	mv a0,t4
123	ld ra,88(sp)
124	ld s0,80(sp)
125	addi sp,sp,96
126	jr ra
.Bmain50:
127	lla t3,b
128	li s9,4
129	mulw s9,s9,t2
130	add t4,t3,s9
131	lw t3,0(t4)
132	subw s9,t3,zero
133	seqz t6,s9
134	xori t6,t6,1
135	bnez t6,.Bmain54
136	j .Bmain55
.Bmain54:
137	li a0,1
138	ld ra,88(sp)
139	ld s0,80(sp)
140	addi sp,sp,96
141	jr ra
.Bmain55:
142	lla t3,c
143	li s9,4
144	mulw s9,s9,t2
145	add t4,t3,s9
146	lw t3,0(t4)
147	subw s9,t3,zero
148	seqz s1,s9
149	xori s1,s1,1
150	bnez s1,.Bmain61
151	j .Bmain59
.Bmain59:
152	addi t3,t2,1
153	mv t2,t3
154	j .Bmain43
.Bmain61:
155	li a0,2
156	ld ra,88(sp)
157	ld s0,80(sp)
158	addi sp,sp,96
159	jr ra
	.size	main, .-main
	.global	n
	.bss
	.align	2
	.type	n,@object
	.size	n,4
n:
	.zero	4
	.global	cnt
	.bss
	.align	3
	.type	cnt,@object
	.size	cnt,1600080
cnt:
	.zero	1600080
	.global	a
	.bss
	.align	3
	.type	a,@object
	.size	a,400020
a:
	.zero	400020
	.global	b
	.bss
	.align	3
	.type	b,@object
	.size	b,400020
b:
	.zero	400020
	.global	c
	.bss
	.align	3
	.type	c,@object
	.size	c,400020
c:
	.zero	400020
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
