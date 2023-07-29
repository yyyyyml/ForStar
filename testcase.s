	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	float_abs
	.type	float_abs	@function
	slti
float_abs:
0	addi sp,sp,-48
1	sd ra,40(sp)
2	sd s0,32(sp)
3	addi s0,sp,48
.Bfloat_abs1:
4	fsw f10,-24(s0)
5	flw f0,-24(s0)
6	fcvt.s.w f1,zero
7	flt.s t2,f0,f1
8	bne t2,zero,.Bfloat_abs8
9	j .Bfloat_abs6
.Bfloat_abs6:
10	flw f0,-24(s0)
11	fmv.s f10,f0
12	ld ra,40(sp)
13	ld s0,32(sp)
14	addi sp,sp,48
15	jr ra
.Bfloat_abs8:
16	flw f0,-24(s0)
17	fneg.s f1,f0
18	fmv.s f10,f1
19	ld ra,40(sp)
20	ld s0,32(sp)
21	addi sp,sp,48
22	jr ra
	.size	float_abs, .-float_abs
	.align	1
	.global	circle_area
	.type	circle_area	@function
circle_area:
0	addi sp,sp,-48
1	sd ra,40(sp)
2	sd s0,32(sp)
3	addi s0,sp,48
.Bcircle_area1:
4	sw a0,-24(s0)
5	lw t2,-24(s0)
6	fcvt.s.w f0,t2
7	lla t2,.F0
8	flw f1,0(t2)
9	fmul.s f2,f1,f0
10	lw t2,-24(s0)
11	fcvt.s.w f0,t2
12	fmul.s f1,f2,f0
13	lw t2,-24(s0)
14	lw t3,-24(s0)
15	mulw t4,t2,t3
16	fcvt.s.w f0,t4
17	lla t2,.F0
18	flw f2,0(t2)
19	fmul.s f3,f0,f2
20	fadd.s f0,f1,f3
21	li t2,2
22	fcvt.s.w f1,t2
23	fdiv.s f2,f0,f1
24	fmv.s f10,f2
25	ld ra,40(sp)
26	ld s0,32(sp)
27	addi sp,sp,48
28	jr ra
	.size	circle_area, .-circle_area
	.align	1
	.global	float_eq
	.type	float_eq	@function
float_eq:
0	addi sp,sp,-80
1	sd ra,72(sp)
2	sd s0,64(sp)
3	addi s0,sp,80
.Bfloat_eq2:
4	fsw f10,-32(s0)
5	fsw f11,-24(s0)
6	flw f0,-32(s0)
7	flw f1,-24(s0)
8	fsub.s f2,f0,f1
9	fmv.s f10,f2
	fsd f0,-56(s0)
	fsd f1,-64(s0)
	fsd f2,-72(s0)
10	call float_abs
	fld f2,-72(s0)
	fld f1,-64(s0)
	fld f0,-56(s0)
11	fmv.s f0,f10
12	lla t2,.F1
13	flw f1,0(t2)
14	flt.s t2,f0,f1
15	bne t2,zero,.Bfloat_eq10
16	j .Bfloat_eq16
.Bfloat_eq10:
17	li t2,1
18	fcvt.s.w f0,t2
19	lla t2,.F2
20	flw f1,0(t2)
21	fmul.s f2,f0,f1
22	li t2,2
23	fcvt.s.w f0,t2
24	fdiv.s f1,f2,f0
25	fcvt.w.s t2,f1,rtz
26	mv a0,t2
27	ld ra,72(sp)
28	ld s0,64(sp)
29	addi sp,sp,80
30	jr ra
.Bfloat_eq16:
31	mv a0,zero
32	ld ra,72(sp)
33	ld s0,64(sp)
34	addi sp,sp,80
35	jr ra
	.size	float_eq, .-float_eq
	.align	1
	.global	error
	.type	error	@function
error:
0	addi sp,sp,-48
1	sd ra,40(sp)
2	sd s0,32(sp)
3	addi s0,sp,48
.Berror0:
4	li a0,101
5	call putch
6	mv t2,a0
7	li a0,114
8	sd t2,-32(s0)
9	call putch
10	ld t2,-32(s0)
11	mv t2,a0
12	li a0,114
13	sd t2,-32(s0)
14	call putch
15	ld t2,-32(s0)
16	mv t2,a0
17	li a0,111
18	sd t2,-32(s0)
19	call putch
20	ld t2,-32(s0)
21	mv t2,a0
22	li a0,114
23	sd t2,-32(s0)
24	call putch
25	ld t2,-32(s0)
26	mv t2,a0
27	li a0,10
28	sd t2,-32(s0)
29	call putch
30	ld t2,-32(s0)
31	mv t2,a0
32	nop
33	ld ra,40(sp)
34	ld s0,32(sp)
35	addi sp,sp,48
36	jr ra
	.size	error, .-error
	.align	1
	.global	ok
	.type	ok	@function
ok:
0	addi sp,sp,-48
1	sd ra,40(sp)
2	sd s0,32(sp)
3	addi s0,sp,48
.Bok0:
4	li a0,111
5	call putch
6	mv t2,a0
7	li a0,107
8	sd t2,-32(s0)
9	call putch
10	ld t2,-32(s0)
11	mv t2,a0
12	li a0,10
13	sd t2,-32(s0)
14	call putch
15	ld t2,-32(s0)
16	mv t2,a0
17	nop
18	ld ra,40(sp)
19	ld s0,32(sp)
20	addi sp,sp,48
21	jr ra
	.size	ok, .-ok
	.align	1
	.global	assert
	.type	assert	@function
assert:
0	addi sp,sp,-64
1	sd ra,56(sp)
2	sd s0,48(sp)
3	addi s0,sp,64
.Bassert1:
4	sw a0,-24(s0)
5	lw t2,-24(s0)
6	subw t3,zero,t2
7	seqz t2,t3
8	bne t2,zero,.Bassert6
9	j .Bassert7
.Bassert5:
10	nop
11	ld ra,56(sp)
12	ld s0,48(sp)
13	addi sp,sp,64
14	jr ra
.Bassert6:
15	sd t2,-40(s0)
16	sd t3,-48(s0)
17	call error
18	ld t3,-48(s0)
19	ld t2,-40(s0)
20	mv t2,a0
21	j .Bassert5
.Bassert7:
22	sd t2,-40(s0)
23	call ok
24	ld t2,-40(s0)
25	mv t2,a0
26	j .Bassert5
	.size	assert, .-assert
	.align	1
	.global	assert_not
	.type	assert_not	@function
assert_not:
0	addi sp,sp,-64
1	sd ra,56(sp)
2	sd s0,48(sp)
3	addi s0,sp,64
.Bassert_not1:
4	sw a0,-24(s0)
5	lw t2,-24(s0)
6	subw t3,t2,zero
7	seqz t2,t3
8	xori t2,t2,1
9	bne t2,zero,.Bassert_not6
10	j .Bassert_not7
.Bassert_not5:
11	nop
12	ld ra,56(sp)
13	ld s0,48(sp)
14	addi sp,sp,64
15	jr ra
.Bassert_not6:
16	sd t2,-40(s0)
17	sd t3,-48(s0)
18	call error
19	ld t3,-48(s0)
20	ld t2,-40(s0)
21	mv t2,a0
22	j .Bassert_not5
.Bassert_not7:
23	sd t2,-40(s0)
24	call ok
25	ld t2,-40(s0)
26	mv t2,a0
27	j .Bassert_not5
	.size	assert_not, .-assert_not
	.align	1
	.global	main
	.type	main	@function
main:
0	addi sp,sp,-176
1	sd ra,168(sp)
2	sd s0,160(sp)
3	addi s0,sp,176
.Bmain0:
4	lla t2,.F3
5	flw f0,0(t2)
6	lla t2,.F3
7	flw f0,0(t2)
8	fmv.s f10,f0
9	lla t2,.F4
10	flw f0,0(t2)
11	lla t2,.F4
12	flw f0,0(t2)
13	fmv.s f11,f0
14	sd t2,-120(s0)
	fsd f0,-152(s0)
15	call float_eq
	fld f0,-152(s0)
16	ld t2,-120(s0)
17	mv t2,a0
18	mv a0,t2
19	sd t2,-120(s0)
	fsd f0,-152(s0)
20	call assert_not
	fld f0,-152(s0)
21	ld t2,-120(s0)
22	mv t2,a0
23	lla t2,.F5
24	flw f0,0(t2)
25	lla t2,.F5
26	flw f0,0(t2)
27	fmv.s f10,f0
28	lla t2,.F6
29	flw f0,0(t2)
30	lla t2,.F6
31	flw f0,0(t2)
32	fmv.s f10,f0
33	sd t2,-120(s0)
	fsd f0,-152(s0)
34	call float_eq
	fld f0,-152(s0)
35	ld t2,-120(s0)
36	mv t2,a0
37	mv a0,t2
38	sd t2,-120(s0)
	fsd f0,-152(s0)
39	call assert_not
	fld f0,-152(s0)
40	ld t2,-120(s0)
41	mv t2,a0
42	lla t2,.F6
43	flw f0,0(t2)
44	lla t2,.F6
45	flw f0,0(t2)
46	fmv.s f10,f0
47	lla t2,.F6
48	flw f0,0(t2)
49	lla t2,.F6
50	flw f0,0(t2)
51	fmv.s f11,f0
52	sd t2,-120(s0)
	fsd f0,-152(s0)
53	call float_eq
	fld f0,-152(s0)
54	ld t2,-120(s0)
55	mv t2,a0
56	mv a0,t2
57	sd t2,-120(s0)
	fsd f0,-152(s0)
58	call assert
	fld f0,-152(s0)
59	ld t2,-120(s0)
60	mv t2,a0
61	lla t2,.F7
62	flw f0,0(t2)
63	fcvt.w.s t2,f0,rtz
64	mv a0,t2
65	sd t2,-120(s0)
	fsd f0,-152(s0)
66	call circle_area
	fld f0,-152(s0)
67	ld t2,-120(s0)
68	fmv.s f0,f10
69	li a0,5
70	sd t2,-120(s0)
	fsd f0,-152(s0)
71	call circle_area
	fld f0,-152(s0)
72	ld t2,-120(s0)
73	fmv.s f1,f10
74	fmv.s f10,f0
75	fmv.s f11,f1
76	sd t2,-120(s0)
	fsd f0,-152(s0)
	fsd f1,-160(s0)
77	call float_eq
	fld f1,-160(s0)
	fld f0,-152(s0)
78	ld t2,-120(s0)
79	mv t2,a0
80	mv a0,t2
81	sd t2,-120(s0)
	fsd f0,-152(s0)
	fsd f1,-160(s0)
82	call assert
	fld f1,-160(s0)
	fld f0,-152(s0)
83	ld t2,-120(s0)
84	mv t2,a0
85	lla t2,.F8
86	flw f0,0(t2)
87	lla t2,.F8
88	flw f0,0(t2)
89	fmv.s f10,f0
90	lla t2,.F9
91	flw f0,0(t2)
92	lla t2,.F9
93	flw f0,0(t2)
94	fmv.s f11,f0
95	sd t2,-120(s0)
	fsd f0,-152(s0)
96	call float_eq
	fld f0,-152(s0)
97	ld t2,-120(s0)
98	mv t2,a0
99	mv a0,t2
100	sd t2,-120(s0)
	fsd f0,-152(s0)
101	call assert_not
	fld f0,-152(s0)
102	ld t2,-120(s0)
103	mv t2,a0
104	lla t2,.F10
105	flw f0,0(t2)
106	lla t2,.F11
107	flw f1,0(t2)
108	feq.s t2,f0,f1
109	xori t3,t2,1
110	bne t3,zero,.Bmain17
111	j .Bmain18
.Bmain17:
112	sd t2,-120(s0)
113	sd t3,-128(s0)
	fsd f0,-152(s0)
	fsd f1,-160(s0)
114	call ok
	fld f1,-160(s0)
	fld f0,-152(s0)
115	ld t3,-128(s0)
116	ld t2,-120(s0)
117	mv t2,a0
118	j .Bmain18
.Bmain18:
119	lla t2,.F11
120	flw f0,0(t2)
121	lla t2,.F12
122	flw f1,0(t2)
123	feq.s t2,f0,f1
124	subw t3,zero,t2
125	seqz t2,t3
126	bne t2,zero,.Bmain22
127	j .Bmain23
.Bmain22:
128	sd t2,-120(s0)
129	sd t3,-128(s0)
	fsd f0,-152(s0)
	fsd f1,-160(s0)
130	call ok
	fld f1,-160(s0)
	fld f0,-152(s0)
131	ld t3,-128(s0)
132	ld t2,-120(s0)
133	mv t2,a0
134	j .Bmain23
.Bmain23:
135	lla t2,.F11
136	flw f0,0(t2)
137	lla t2,.F11
138	flw f1,0(t2)
139	feq.s t2,f0,f1
140	xori t3,t2,1
141	bne t3,zero,.Bmain26
142	j .Bmain28
.Bmain25:
143	sd t2,-120(s0)
144	sd t3,-128(s0)
	fsd f0,-152(s0)
	fsd f1,-160(s0)
145	call error
	fld f1,-160(s0)
	fld f0,-152(s0)
146	ld t3,-128(s0)
147	ld t2,-120(s0)
148	mv t2,a0
149	j .Bmain28
.Bmain26:
150	li t2,3
151	subw t3,t2,zero
152	seqz t2,t3
153	xori t2,t2,1
154	bne t2,zero,.Bmain25
155	j .Bmain28
.Bmain28:
156	subw t2,zero,zero
157	seqz t3,t2
158	xori t3,t3,1
159	bne t3,zero,.Bmain37
160	j .Bmain38
.Bmain30:
161	li t2,1
162	sw t2,-104(s0)
163	sw zero,-96(s0)
164	addi t2,s0,-88
165	mv a0,t2
166	mv a1,zero
167	li a2,40
168	sd t2,-120(s0)
	fsd f0,-152(s0)
	fsd f1,-160(s0)
169	call memset
	fld f1,-160(s0)
	fld f0,-152(s0)
170	ld t2,-120(s0)
171	mv t2,a0
172	lla t2,.F13
173	flw f0,0(t2)
174	fsw f0,-88(s0)
175	li t2,2
176	fcvt.s.w f0,t2
177	fsw f0,-84(s0)
178	addi t2,s0,-88
179	mv a0,t2
180	sd t2,-120(s0)
	fsd f0,-152(s0)
181	call getfarray
	fld f0,-152(s0)
182	ld t2,-120(s0)
183	mv t2,a0
184	sw t2,-48(s0)
185	j .Bmain40
.Bmain37:
186	sd t2,-120(s0)
	fsd f0,-152(s0)
187	call ok
	fld f0,-152(s0)
188	ld t2,-120(s0)
189	mv t2,a0
190	j .Bmain30
.Bmain38:
191	lla t2,.F14
192	flw f0,0(t2)
193	lla t2,.F11
194	flw f1,0(t2)
195	feq.s t2,f0,f1
196	xori t3,t2,1
197	bne t3,zero,.Bmain37
198	j .Bmain30
.Bmain40:
199	lw t2,-104(s0)
200	slti t3,t2,1000000000
201	bne t3,zero,.Bmain43
202	j .Bmain70
.Bmain43:
203	sd t2,-120(s0)
204	sd t3,-128(s0)
	fsd f0,-152(s0)
	fsd f1,-160(s0)
205	call getfloat
	fld f1,-160(s0)
	fld f0,-152(s0)
206	ld t3,-128(s0)
207	ld t2,-120(s0)
208	fmv.s f0,f10
209	fsw f0,-40(s0)
210	flw f0,-40(s0)
211	lla t2,.F0
212	flw f1,0(t2)
213	fmul.s f2,f1,f0
214	flw f0,-40(s0)
215	fmul.s f1,f2,f0
216	fsw f1,-32(s0)
217	flw f0,-40(s0)
218	fcvt.w.s t2,f0,rtz
219	mv a0,t2
220	sd t2,-120(s0)
	fsd f0,-152(s0)
221	call circle_area
	fld f0,-152(s0)
222	ld t2,-120(s0)
223	fmv.s f0,f10
224	fsw f0,-24(s0)
225	lw t2,-96(s0)
226	li t3,4
227	mulw t3,t3,t2
228	addi t3,t3,-88
229	add t2,s0,t3
230	lw t3,-96(s0)
231	li t4,4
232	mulw t4,t4,t3
233	addi t4,t4,-88
234	add t3,s0,t4
235	flw f0,0(t3)
236	flw f1,-40(s0)
237	fadd.s f2,f0,f1
238	fsw f2,0(t2)
239	flw f0,-32(s0)
240	fmv.s f10,f0
241	sd t2,-120(s0)
242	sd t3,-128(s0)
243	sd t4,-136(s0)
	fsd f0,-152(s0)
244	call putfloat
	fld f0,-152(s0)
245	ld t4,-136(s0)
246	ld t3,-128(s0)
247	ld t2,-120(s0)
248	mv t2,a0
249	li a0,32
250	sd t2,-120(s0)
	fsd f0,-152(s0)
251	call putch
	fld f0,-152(s0)
252	ld t2,-120(s0)
253	mv t2,a0
254	flw f0,-24(s0)
255	fcvt.w.s t2,f0,rtz
256	mv a0,t2
257	sd t2,-120(s0)
	fsd f0,-152(s0)
258	call putint
	fld f0,-152(s0)
259	ld t2,-120(s0)
260	mv t2,a0
261	li a0,10
262	sd t2,-120(s0)
	fsd f0,-152(s0)
263	call putch
	fld f0,-152(s0)
264	ld t2,-120(s0)
265	mv t2,a0
266	lw t2,-104(s0)
267	lla t3,.F15
268	flw f0,0(t3)
269	fneg.s f1,f0
270	fneg.s f0,f1
271	fcvt.s.w f1,t2
272	fmul.s f2,f1,f0
273	fcvt.w.s t2,f2,rtz
274	sw t2,-104(s0)
275	lw t2,-96(s0)
276	addi t3,t2,1
277	sw t3,-96(s0)
278	j .Bmain40
.Bmain70:
279	lw t2,-48(s0)
280	mv a0,t2
281	addi t2,s0,-88
282	mv a1,t2
283	sd t2,-120(s0)
	fsd f0,-152(s0)
	fsd f1,-160(s0)
	fsd f2,-168(s0)
284	call putfarray
	fld f2,-168(s0)
	fld f1,-160(s0)
	fld f0,-152(s0)
285	ld t2,-120(s0)
286	mv t2,a0
287	mv a0,zero
288	ld ra,168(sp)
289	ld s0,160(sp)
290	addi sp,sp,176
291	jr ra
	.size	main, .-main
	.align	2
.F0:
	.word	1078530011
	.align	2
.F1:
	.word	897988541
	.align	2
.F2:
	.word	1073741824
	.align	2
.F3:
	.word	1033895936
	.align	2
.F4:
	.word	-956241920
	.align	2
.F5:
	.word	1119752446
	.align	2
.F6:
	.word	1107966695
	.align	2
.F7:
	.word	1085276160
	.align	2
.F8:
	.word	1130954752
	.align	2
.F9:
	.word	1166012416
	.align	2
.F10:
	.word	1069547520
	.align	2
.F11:
	.word	0
	.align	2
.F12:
	.word	1079194419
	.align	2
.F13:
	.word	1065353216
	.align	2
.F14:
	.word	1050253722
	.align	2
.F15:
	.word	1092616192
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
