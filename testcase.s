	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	mul
	.type	mul	@function
mul:
0	addi sp,sp,-32
1	sd ra,24(sp)
2	sd s0,16(sp)
3	addi s0,sp,32
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
114	ld ra,24(sp)
115	ld s0,16(sp)
116	addi sp,sp,32
117	jr ra
	.size	mul, .-mul
	.align	1
	.global	main
	.type	main	@function
main:
0	addi sp,sp,-192
1	sd ra,184(sp)
2	sd s0,176(sp)
3	addi s0,sp,192
.Bmain0:
4	li t2,0
5	lla t3,N
6	li t4,3
7	sw t4,0(t3)
8	lla t3,M
9	li t4,3
10	sw t4,0(t3)
11	lla t3,L
12	li t4,3
13	sw t4,0(t3)
14	j .Bmain10
.Bmain10:
15	lla t3,M
16	lw t4,0(t3)
17	slt t3,t2,t4
18	bne t3,zero,.Bmain14
19	j .Bmain28
.Bmain14:
20	li t3,4
21	mulw t3,t3,t2
22	addi t3,t3,-136
23	add t4,s0,t3
24	fcvt.s.w f0,t2
25	fsw f0,0(t4)
26	li t3,4
27	mulw t3,t3,t2
28	addi t3,t3,-124
29	add t4,s0,t3
30	fcvt.s.w f0,t2
31	fsw f0,0(t4)
32	li t3,4
33	mulw t3,t3,t2
34	addi t3,t3,-112
35	add t4,s0,t3
36	fcvt.s.w f0,t2
37	fsw f0,0(t4)
38	li t3,4
39	mulw t3,t3,t2
40	addi t3,t3,-100
41	add t4,s0,t3
42	fcvt.s.w f0,t2
43	fsw f0,0(t4)
44	li t3,4
45	mulw t3,t3,t2
46	addi t3,t3,-88
47	add t4,s0,t3
48	fcvt.s.w f0,t2
49	fsw f0,0(t4)
50	li t3,4
51	mulw t3,t3,t2
52	addi t3,t3,-76
53	add t4,s0,t3
54	fcvt.s.w f0,t2
55	fsw f0,0(t4)
56	addi t3,t2,1
57	mv t2,t3
58	j .Bmain10
.Bmain28:
59	addi t3,s0,-136
60	mv a0,t3
61	addi t3,s0,-124
62	mv a1,t3
63	addi t3,s0,-112
64	mv a2,t3
65	addi t3,s0,-100
66	mv a3,t3
67	addi t3,s0,-88
68	mv a4,t3
69	addi t3,s0,-76
70	mv a5,t3
71	addi t3,s0,-64
72	mv a6,t3
73	addi t3,s0,-40
74	mv a7,t3
75	addi t3,s0,-28
76	sd t3,0(sp)
77	sd t2,-152(s0)
78	sd t3,-160(s0)
-1	fsd f0,-176(s0)
79	call mul
-1	fld f0,-176(s0)
80	ld t3,-160(s0)
81	ld t2,-152(s0)
82	mv t3,a0
83	mv t2,t3
84	j .Bmain39
.Bmain39:
85	lla t3,N
86	lw t4,0(t3)
87	slt t3,t2,t4
88	bne t3,zero,.Bmain43
89	j .Bmain48
.Bmain43:
90	li t3,4
91	mulw t3,t3,t2
92	addi t3,t3,-64
93	add t4,s0,t3
94	flw f0,0(t4)
95	fcvt.w.s t3,f0,rtz
96	mv a0,t3
97	sd t2,-152(s0)
98	sd t3,-160(s0)
-1	fsd f0,-176(s0)
99	call putint
-1	fld f0,-176(s0)
100	ld t3,-160(s0)
101	ld t2,-152(s0)
102	mv t3,a0
103	addi t3,t2,1
104	mv t2,t3
105	j .Bmain39
.Bmain48:
106	li t2,0
107	li a0,10
108	sd t2,-152(s0)
-1	fsd f0,-176(s0)
109	call putch
-1	fld f0,-176(s0)
110	ld t2,-152(s0)
111	mv t3,a0
112	j .Bmain49
.Bmain49:
113	lla t3,N
114	lw t4,0(t3)
115	slt t3,t2,t4
116	bne t3,zero,.Bmain53
117	j .Bmain58
.Bmain53:
118	li t3,4
119	mulw t3,t3,t2
120	addi t3,t3,-40
121	add t4,s0,t3
122	flw f0,0(t4)
123	fcvt.w.s t3,f0,rtz
124	mv a0,t3
125	sd t2,-152(s0)
126	sd t3,-160(s0)
-1	fsd f0,-176(s0)
127	call putint
-1	fld f0,-176(s0)
128	ld t3,-160(s0)
129	ld t2,-152(s0)
130	mv t3,a0
131	addi t3,t2,1
132	mv t2,t3
133	j .Bmain49
.Bmain58:
134	li t2,0
135	li a0,10
136	sd t2,-152(s0)
-1	fsd f0,-176(s0)
137	call putch
-1	fld f0,-176(s0)
138	ld t2,-152(s0)
139	mv t3,a0
140	j .Bmain59
.Bmain59:
141	lla t3,N
142	lw t4,0(t3)
143	slt t3,t2,t4
144	bne t3,zero,.Bmain63
145	j .Bmain68
.Bmain63:
146	li t3,4
147	mulw t3,t3,t2
148	addi t3,t3,-28
149	add t4,s0,t3
150	flw f0,0(t4)
151	fcvt.w.s t3,f0,rtz
152	mv a0,t3
153	sd t2,-152(s0)
154	sd t3,-160(s0)
-1	fsd f0,-176(s0)
155	call putint
-1	fld f0,-176(s0)
156	ld t3,-160(s0)
157	ld t2,-152(s0)
158	mv t3,a0
159	addi t3,t2,1
160	mv t2,t3
161	j .Bmain59
.Bmain68:
162	li a0,10
163	sd t2,-152(s0)
164	sd t3,-160(s0)
-1	fsd f0,-176(s0)
165	call putch
-1	fld f0,-176(s0)
166	ld t3,-160(s0)
167	ld t2,-152(s0)
168	mv t2,a0
169	li a0,0
170	ld ra,184(sp)
171	ld s0,176(sp)
172	addi sp,sp,192
173	jr ra
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
