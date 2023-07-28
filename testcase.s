	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	mul
	.type	mul	@function
mul:
	addi sp,sp,-48
1	sd ra,40(sp)
2	sd s0,32(sp)
3	addi s0,sp,48
.Bmul9:
4	flw f0,0(a0)
5	flw f1,0(a3)
6	fmul.s f2,f0,f1
7	flw f0,4(a0)
8	flw f1,0(a4)
9	fmul.s f3,f0,f1
10	fadd.s f0,f2,f3
11	flw f1,8(a0)
12	flw f2,0(a5)
13	fmul.s f3,f1,f2
14	fadd.s f1,f0,f3
15	fsw f1,0(a6)
16	flw f0,0(a0)
17	flw f1,4(a3)
18	fmul.s f2,f0,f1
19	flw f0,4(a0)
20	flw f1,4(a4)
21	fmul.s f3,f0,f1
22	fadd.s f0,f2,f3
23	flw f1,8(a0)
24	flw f2,4(a5)
25	fmul.s f3,f1,f2
26	fadd.s f1,f0,f3
27	fsw f1,4(a6)
28	flw f0,0(a0)
29	flw f1,8(a3)
30	fmul.s f2,f0,f1
31	flw f0,4(a0)
32	flw f1,8(a4)
33	fmul.s f3,f0,f1
34	fadd.s f0,f2,f3
35	flw f1,8(a0)
36	flw f2,8(a5)
37	fmul.s f3,f1,f2
38	fadd.s f1,f0,f3
39	fsw f1,8(a6)
40	flw f0,0(a1)
41	flw f1,0(a3)
42	fmul.s f2,f0,f1
43	flw f0,4(a1)
44	flw f1,0(a4)
45	fmul.s f3,f0,f1
46	fadd.s f0,f2,f3
47	flw f1,8(a1)
48	flw f2,0(a5)
49	fmul.s f3,f1,f2
50	fadd.s f1,f0,f3
51	fsw f1,0(a7)
52	flw f0,0(a1)
53	flw f1,4(a3)
54	fmul.s f2,f0,f1
55	flw f0,4(a1)
56	flw f1,4(a4)
57	fmul.s f3,f0,f1
58	fadd.s f0,f2,f3
59	flw f1,8(a1)
60	flw f2,4(a5)
61	fmul.s f3,f1,f2
62	fadd.s f1,f0,f3
63	fsw f1,4(a7)
64	flw f0,0(a1)
65	flw f1,8(a3)
66	fmul.s f2,f0,f1
67	flw f0,4(a1)
68	flw f1,8(a4)
69	fmul.s f3,f0,f1
70	fadd.s f0,f2,f3
71	flw f1,8(a1)
72	flw f2,8(a5)
73	fmul.s f3,f1,f2
74	fadd.s f1,f0,f3
75	fsw f1,8(a7)
76	ld t2,0(s0)
77	flw f0,0(a2)
78	flw f1,0(a3)
79	fmul.s f2,f0,f1
80	flw f0,4(a2)
81	flw f1,0(a4)
82	fmul.s f3,f0,f1
83	fadd.s f0,f2,f3
84	flw f1,8(a2)
85	flw f2,0(a5)
86	fmul.s f3,f1,f2
87	fadd.s f1,f0,f3
88	fsw f1,0(t2)
89	flw f0,0(a2)
90	flw f1,4(a3)
91	fmul.s f2,f0,f1
92	flw f0,4(a2)
93	flw f1,4(a4)
94	fmul.s f3,f0,f1
95	fadd.s f0,f2,f3
96	flw f1,8(a2)
97	flw f2,4(a5)
98	fmul.s f3,f1,f2
99	fadd.s f1,f0,f3
100	fsw f1,4(t2)
101	flw f0,0(a2)
102	flw f1,8(a3)
103	fmul.s f2,f0,f1
104	flw f0,4(a2)
105	flw f1,8(a4)
106	fmul.s f3,f0,f1
107	fadd.s f0,f2,f3
108	flw f1,8(a2)
109	flw f2,8(a5)
110	fmul.s f3,f1,f2
111	fadd.s f1,f0,f3
112	fsw f1,8(t2)
113	li a0,0
114	ld ra,40(sp)
115	ld s0,32(sp)
116	addi sp,sp,48
117	jr ra
	.size	mul, .-mul
	.align	1
	.global	main
	.type	main	@function
main:
	addi sp,sp,-240
1	sd ra,232(sp)
2	sd s0,224(sp)
3	addi s0,sp,240
.Bmain0:
4	li s9,0
5	sd s9,24(s0)
6	lla t2,N
7	li t3,3
8	sw t3,0(t2)
9	lla t2,M
10	li t3,3
11	sw t3,0(t2)
12	lla t2,L
13	li t3,3
14	sw t3,0(t2)
15	j .Bmain10
.Bmain10:
16	lla t2,M
17	lw t3,0(t2)
18	lw t2,24(s0)
19	slt t4,t2,t3
20	bne t4,zero,.Bmain14
21	j .Bmain28
.Bmain14:
22	li t2,4
23	lw t3,24(s0)
24	mulw t2,t2,t3
25	addi t2,t2,-168
26	add t3,s0,t2
27	lw t2,24(s0)
28	fcvt.s.w f0,t2
29	fsw f0,0(t3)
30	li t2,4
31	lw t3,24(s0)
32	mulw t2,t2,t3
33	addi t2,t2,-156
34	add t3,s0,t2
35	lw t2,24(s0)
36	fcvt.s.w f0,t2
37	fsw f0,0(t3)
38	li t2,4
39	lw t3,24(s0)
40	mulw t2,t2,t3
41	addi t2,t2,-144
42	add t3,s0,t2
43	lw t2,24(s0)
44	fcvt.s.w f0,t2
45	fsw f0,0(t3)
46	li t2,4
47	lw t3,24(s0)
48	mulw t2,t2,t3
49	addi t2,t2,-132
50	add t3,s0,t2
51	lw t2,24(s0)
52	fcvt.s.w f0,t2
53	fsw f0,0(t3)
54	li t2,4
55	lw t3,24(s0)
56	mulw t2,t2,t3
57	addi t2,t2,-120
58	add t3,s0,t2
59	lw t2,24(s0)
60	fcvt.s.w f0,t2
61	fsw f0,0(t3)
62	li t2,4
63	lw t3,24(s0)
64	mulw t2,t2,t3
65	addi t2,t2,-108
66	add t3,s0,t2
67	lw t2,24(s0)
68	fcvt.s.w f0,t2
69	fsw f0,0(t3)
70	lw t2,24(s0)
71	addi t3,t2,1
72	sd t3,24(s0)
73	j .Bmain10
.Bmain28:
74	addi t2,s0,-168
75	mv a0,t2
76	addi t2,s0,-156
77	mv a1,t2
78	addi t2,s0,-144
79	mv a2,t2
80	addi t2,s0,-132
81	mv a3,t2
82	addi t2,s0,-120
83	mv a4,t2
84	addi t2,s0,-108
85	mv a5,t2
86	addi t2,s0,-96
87	mv a6,t2
88	addi t2,s0,-72
89	mv a7,t2
90	addi t2,s0,-60
91	sd t2,0(sp)
	sd t2,-192(s0)
	fsd f0,-224(s0)
92	call mul
	fld f0,-224(s0)
	ld t2,-192(s0)
93	mv t2,a0
94	sd t2,32(s0)
95	j .Bmain39
.Bmain39:
96	lla t2,N
97	lw t3,0(t2)
98	lw t2,32(s0)
99	slt t4,t2,t3
100	bne t4,zero,.Bmain43
101	j .Bmain48
.Bmain43:
102	li t2,4
103	lw t3,32(s0)
104	mulw t2,t2,t3
105	addi t2,t2,-96
106	add t3,s0,t2
107	flw f0,0(t3)
108	fcvt.w.s t2,f0,rtz
109	mv a0,t2
	sd t2,-192(s0)
	fsd f0,-224(s0)
110	call putint
	fld f0,-224(s0)
	ld t2,-192(s0)
111	mv t2,a0
112	lw t2,32(s0)
113	addi t3,t2,1
114	sd t3,32(s0)
115	j .Bmain39
.Bmain48:
116	li s9,0
117	sd s9,40(s0)
118	li a0,10
	sd t2,-192(s0)
	sd t3,-200(s0)
	fsd f0,-224(s0)
119	call putch
	fld f0,-224(s0)
	ld t3,-200(s0)
	ld t2,-192(s0)
120	mv t2,a0
121	j .Bmain49
.Bmain49:
122	lla t2,N
123	lw t3,0(t2)
124	lw t2,40(s0)
125	slt t4,t2,t3
126	bne t4,zero,.Bmain53
127	j .Bmain58
.Bmain53:
128	li t2,4
129	lw t3,40(s0)
130	mulw t2,t2,t3
131	addi t2,t2,-72
132	add t3,s0,t2
133	flw f0,0(t3)
134	fcvt.w.s t2,f0,rtz
135	mv a0,t2
	sd t2,-192(s0)
	fsd f0,-224(s0)
136	call putint
	fld f0,-224(s0)
	ld t2,-192(s0)
137	mv t2,a0
138	lw t2,40(s0)
139	addi t3,t2,1
140	sd t3,40(s0)
141	j .Bmain49
.Bmain58:
142	li s9,0
143	sd s9,48(s0)
144	li a0,10
	sd t2,-192(s0)
	sd t3,-200(s0)
	fsd f0,-224(s0)
145	call putch
	fld f0,-224(s0)
	ld t3,-200(s0)
	ld t2,-192(s0)
146	mv t2,a0
147	j .Bmain59
.Bmain59:
148	lla t2,N
149	lw t3,0(t2)
150	lw t2,48(s0)
151	slt t4,t2,t3
152	bne t4,zero,.Bmain63
153	j .Bmain68
.Bmain63:
154	li t2,4
155	lw t3,48(s0)
156	mulw t2,t2,t3
157	addi t2,t2,-60
158	add t3,s0,t2
159	flw f0,0(t3)
160	fcvt.w.s t2,f0,rtz
161	mv a0,t2
	sd t2,-192(s0)
	fsd f0,-224(s0)
162	call putint
	fld f0,-224(s0)
	ld t2,-192(s0)
163	mv t2,a0
164	lw t2,48(s0)
165	addi t3,t2,1
166	sd t3,48(s0)
167	j .Bmain59
.Bmain68:
168	li a0,10
	sd t2,-192(s0)
	sd t3,-200(s0)
	fsd f0,-224(s0)
169	call putch
	fld f0,-224(s0)
	ld t3,-200(s0)
	ld t2,-192(s0)
170	mv t2,a0
171	li a0,0
172	ld ra,232(sp)
173	ld s0,224(sp)
174	addi sp,sp,240
175	jr ra
	.size	main, .-main
	.global	M
	.bss
	.align	2
	.type	M,@object
	.size	M,4
M:
	.zero	4
	.global	L
	.bss
	.align	2
	.type	L,@object
	.size	L,4
L:
	.zero	4
	.global	N
	.bss
	.align	2
	.type	N,@object
	.size	N,4
N:
	.zero	4
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
