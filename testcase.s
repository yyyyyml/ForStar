	.file	"null"
	.option	pic
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	1
	.global	f
	.type	f	@function
f:
0	addi sp,sp,-32
1	sd ra,24(sp)
2	sd s0,16(sp)
3	addi s0,sp,32
4	mv t2,a0
5	mv t3,a1
.Bf2:
6	lla t4,sum
7	lw t5,0(t4)
8	addi t4,t5,1
9	lla t5,sum
10	sw t4,0(t5)
11	bge t2,t3,.Bf9
12	j .Bf10
.Bf6:
13	lla t3,array
14	li s9,4
15	mulw s9,s9,t2
16	add t4,t3,s9
17	li t3,1
18	sw t3,0(t4)
19	beq t2,zero,.Bf12
20	j .Bf15
.Bf9:
21	mv a0,zero
22	ld ra,24(sp)
23	ld s0,16(sp)
24	addi sp,sp,32
25	jr ra
.Bf10:
26	li t3,20
27	bge t2,t3,.Bf9
28	j .Bf6
.Bf12:
29	lla t3,array
30	lw t4,0(t3)
31	mv a0,t4
32	ld ra,24(sp)
33	ld s0,16(sp)
34	addi sp,sp,32
35	jr ra
.Bf15:
36	li t3,1
37	subw t4,t2,t3
38	lla t3,array
39	li s9,4
40	mulw s9,s9,t4
41	add t4,t3,s9
42	lw t3,0(t4)
43	mv a0,t3
44	ld ra,24(sp)
45	ld s0,16(sp)
46	addi sp,sp,32
47	jr ra
	.size	f, .-f
	.align	1
	.global	g
	.type	g	@function
g:
0	addi sp,sp,-32
1	sd ra,24(sp)
2	sd s0,16(sp)
3	addi s0,sp,32
4	mv t2,a0
5	mv t3,a1
.Bg2:
6	lla t4,sum
7	lw t5,0(t4)
8	addi t4,t5,2
9	lla t5,sum
10	sw t4,0(t5)
11	bge t2,t3,.Bg9
12	j .Bg10
.Bg6:
13	lla t3,array
14	li s9,4
15	mulw s9,s9,t2
16	add t4,t3,s9
17	sw zero,0(t4)
18	beq t2,zero,.Bg12
19	j .Bg15
.Bg9:
20	li a0,1
21	ld ra,24(sp)
22	ld s0,16(sp)
23	addi sp,sp,32
24	jr ra
.Bg10:
25	li t3,20
26	bge t2,t3,.Bg9
27	j .Bg6
.Bg12:
28	lla t3,array
29	lw t4,0(t3)
30	mv a0,t4
31	ld ra,24(sp)
32	ld s0,16(sp)
33	addi sp,sp,32
34	jr ra
.Bg15:
35	li t3,1
36	subw t4,t2,t3
37	lla t3,array
38	li s9,4
39	mulw s9,s9,t4
40	add t4,t3,s9
41	lw t3,0(t4)
42	mv a0,t3
43	ld ra,24(sp)
44	ld s0,16(sp)
45	addi sp,sp,32
46	jr ra
	.size	g, .-g
	.align	1
	.global	h
	.type	h	@function
h:
0	addi sp,sp,-32
1	sd ra,24(sp)
2	sd s0,16(sp)
3	addi s0,sp,32
4	mv t2,a0
.Bh1:
5	lla t3,sum
6	lw t4,0(t3)
7	addi t3,t4,3
8	lla t4,sum
9	sw t3,0(t4)
10	blt t2,zero,.Bh8
11	j .Bh9
.Bh5:
12	lla t3,array
13	li s9,4
14	mulw s9,s9,t2
15	add t4,t3,s9
16	lw t3,0(t4)
17	mv a0,t3
18	ld ra,24(sp)
19	ld s0,16(sp)
20	addi sp,sp,32
21	jr ra
.Bh8:
22	mv a0,zero
23	ld ra,24(sp)
24	ld s0,16(sp)
25	addi sp,sp,32
26	jr ra
.Bh9:
27	li t3,20
28	bge t2,t3,.Bh8
29	j .Bh5
	.size	h, .-h
	.align	1
	.global	main
	.type	main	@function
main:
0	addi sp,sp,-80
1	sd ra,72(sp)
2	sd s0,64(sp)
3	addi s0,sp,80
.Bmain0:
4	mv t2,zero
5	j .Bmain1
.Bmain1:
6	li t5,20
7	blt t2,t5,.Bmain5
8	j .Bmain4
.Bmain4:
9	mv t5,zero
10	j .Bmain67
.Bmain5:
11	mv a0,zero
12	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
13	call f
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
14	mv t6,a0
15	bne t6,zero,.Bmain10
16	j .Bmain8
.Bmain8:
17	addi t6,t2,1
18	mv t2,t6
19	j .Bmain1
.Bmain10:
20	li a0,1
21	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
22	call f
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
23	mv t6,a0
24	bne t6,zero,.Bmain13
25	j .Bmain8
.Bmain13:
26	li a0,2
27	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
28	call f
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
29	mv t6,a0
30	bne t6,zero,.Bmain16
31	j .Bmain8
.Bmain16:
32	li a0,3
33	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
34	call f
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
35	mv t6,a0
36	bne t6,zero,.Bmain19
37	j .Bmain8
.Bmain19:
38	li a0,4
39	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
40	call f
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
41	mv t6,a0
42	bne t6,zero,.Bmain22
43	j .Bmain8
.Bmain22:
44	li a0,5
45	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
46	call f
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
47	mv t6,a0
48	bne t6,zero,.Bmain25
49	j .Bmain8
.Bmain25:
50	li a0,6
51	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
52	call f
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
53	mv t6,a0
54	bne t6,zero,.Bmain28
55	j .Bmain8
.Bmain28:
56	li a0,7
57	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
58	call f
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
59	mv t6,a0
60	bne t6,zero,.Bmain31
61	j .Bmain8
.Bmain31:
62	li a0,8
63	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
64	call f
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
65	mv t6,a0
66	bne t6,zero,.Bmain34
67	j .Bmain8
.Bmain34:
68	li a0,9
69	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
70	call f
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
71	mv t6,a0
72	bne t6,zero,.Bmain37
73	j .Bmain8
.Bmain37:
74	li a0,10
75	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
76	call f
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
77	mv t6,a0
78	bne t6,zero,.Bmain40
79	j .Bmain8
.Bmain40:
80	li a0,11
81	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
82	call f
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
83	mv t6,a0
84	bne t6,zero,.Bmain43
85	j .Bmain8
.Bmain43:
86	li a0,12
87	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
88	call f
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
89	mv t6,a0
90	bne t6,zero,.Bmain46
91	j .Bmain8
.Bmain46:
92	li a0,13
93	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
94	call f
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
95	mv t6,a0
96	bne t6,zero,.Bmain49
97	j .Bmain8
.Bmain49:
98	li a0,14
99	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
100	call f
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
101	mv t6,a0
102	bne t6,zero,.Bmain52
103	j .Bmain8
.Bmain52:
104	li a0,15
105	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
106	call f
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
107	mv t6,a0
108	bne t6,zero,.Bmain55
109	j .Bmain8
.Bmain55:
110	li a0,16
111	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
112	call f
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
113	mv t6,a0
114	bne t6,zero,.Bmain58
115	j .Bmain8
.Bmain58:
116	li a0,17
117	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
118	call f
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
119	mv t6,a0
120	bne t6,zero,.Bmain61
121	j .Bmain8
.Bmain61:
122	li a0,18
123	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
124	call f
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
125	mv t6,a0
126	bne t6,zero,.Bmain64
127	j .Bmain8
.Bmain64:
128	li a0,19
129	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
	sd t6,-64(s0)
130	call f
	ld t6,-64(s0)
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
131	mv t6,a0
132	subw s9,t6,zero
133	seqz t3,s9
134	xori t3,t3,1
135	j .Bmain8
.Bmain67:
136	li t2,20
137	blt t5,t2,.Bmain71
138	j .Bmain70
.Bmain70:
139	li t2,1
140	j .Bmain133
.Bmain71:
141	mv a0,zero
142	mv a1,t5
	sd t2,-32(s0)
	sd t4,-40(s0)
	sd t5,-48(s0)
143	call g
	ld t5,-48(s0)
	ld t4,-40(s0)
	ld t2,-32(s0)
144	mv t3,a0
145	bne t3,zero,.Bmain74
146	j .Bmain76
.Bmain74:
147	addi t3,t5,1
148	mv t5,t3
149	j .Bmain67
.Bmain76:
150	li a0,1
151	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
152	call g
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
153	mv t3,a0
154	bne t3,zero,.Bmain74
155	j .Bmain79
.Bmain79:
156	li a0,2
157	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
158	call g
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
159	mv t3,a0
160	bne t3,zero,.Bmain74
161	j .Bmain82
.Bmain82:
162	li a0,3
163	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
164	call g
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
165	mv t3,a0
166	bne t3,zero,.Bmain74
167	j .Bmain85
.Bmain85:
168	li a0,4
169	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
170	call g
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
171	mv t3,a0
172	bne t3,zero,.Bmain74
173	j .Bmain88
.Bmain88:
174	li a0,5
175	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
176	call g
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
177	mv t3,a0
178	bne t3,zero,.Bmain74
179	j .Bmain91
.Bmain91:
180	li a0,6
181	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
182	call g
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
183	mv t3,a0
184	bne t3,zero,.Bmain74
185	j .Bmain94
.Bmain94:
186	li a0,7
187	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
188	call g
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
189	mv t3,a0
190	bne t3,zero,.Bmain74
191	j .Bmain97
.Bmain97:
192	li a0,8
193	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
194	call g
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
195	mv t3,a0
196	bne t3,zero,.Bmain74
197	j .Bmain100
.Bmain100:
198	li a0,9
199	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
200	call g
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
201	mv t3,a0
202	bne t3,zero,.Bmain74
203	j .Bmain103
.Bmain103:
204	li a0,10
205	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
206	call g
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
207	mv t3,a0
208	bne t3,zero,.Bmain74
209	j .Bmain106
.Bmain106:
210	li a0,11
211	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
212	call g
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
213	mv t3,a0
214	bne t3,zero,.Bmain74
215	j .Bmain109
.Bmain109:
216	li a0,12
217	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
218	call g
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
219	mv t3,a0
220	bne t3,zero,.Bmain74
221	j .Bmain112
.Bmain112:
222	li a0,13
223	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
224	call g
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
225	mv t3,a0
226	bne t3,zero,.Bmain74
227	j .Bmain115
.Bmain115:
228	li a0,14
229	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
230	call g
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
231	mv t3,a0
232	bne t3,zero,.Bmain74
233	j .Bmain118
.Bmain118:
234	li a0,15
235	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
236	call g
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
237	mv t3,a0
238	bne t3,zero,.Bmain74
239	j .Bmain121
.Bmain121:
240	li a0,16
241	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
242	call g
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
243	mv t3,a0
244	bne t3,zero,.Bmain74
245	j .Bmain124
.Bmain124:
246	li a0,17
247	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
248	call g
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
249	mv t3,a0
250	bne t3,zero,.Bmain74
251	j .Bmain127
.Bmain127:
252	li a0,18
253	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
254	call g
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
255	mv t3,a0
256	bne t3,zero,.Bmain74
257	j .Bmain130
.Bmain130:
258	li a0,19
259	mv a1,t5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
	sd t5,-56(s0)
260	call g
	ld t5,-56(s0)
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
261	mv t3,a0
262	subw s9,t3,zero
263	seqz t4,s9
264	xori t4,t4,1
265	j .Bmain74
.Bmain133:
266	li t3,20
267	blt t2,t3,.Bmain141
268	j .Bmain138
.Bmain136:
269	addi t3,t2,1
270	mv t2,t3
271	j .Bmain133
.Bmain138:
272	mv a0,zero
	sd t2,-32(s0)
	sd t3,-40(s0)
273	call h
	ld t3,-40(s0)
	ld t2,-32(s0)
274	mv t3,a0
275	bne t3,zero,.Bmain155
276	j .Bmain152
.Bmain141:
277	li t3,1
278	subw t4,t2,t3
279	mv a0,t4
280	mv a1,t2
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
281	call f
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
282	mv t3,a0
283	bne t3,zero,.Bmain136
284	j .Bmain138
.Bmain145:
285	lla t3,sum
286	lw t4,0(t3)
287	addw t3,t4,t2
288	li a0,4
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
289	call h
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
290	mv t3,a0
291	beq zero,t3,.Bmain167
292	j .Bmain168
.Bmain151:
293	li t2,1
294	j .Bmain145
.Bmain152:
295	li a0,2
	sd t2,-32(s0)
	sd t3,-40(s0)
296	call h
	ld t3,-40(s0)
	ld t2,-32(s0)
297	mv t3,a0
298	beq zero,t3,.Bmain151
299	j .Bmain158
.Bmain155:
300	li a0,1
	sd t2,-32(s0)
	sd t3,-40(s0)
301	call h
	ld t3,-40(s0)
	ld t2,-32(s0)
302	mv t3,a0
303	bne t3,zero,.Bmain151
304	j .Bmain152
.Bmain158:
305	li a0,3
	sd t2,-32(s0)
	sd t3,-40(s0)
306	call h
	ld t3,-40(s0)
	ld t2,-32(s0)
307	mv t3,a0
308	mv t2,zero
309	bne t3,zero,.Bmain151
310	j .Bmain145
.Bmain161:
311	lla t3,sum
312	lw t4,0(t3)
313	mulw t3,t4,t2
314	li a0,9
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
315	call h
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
316	mv t3,a0
317	bne t3,zero,.Bmain190
318	j .Bmain187
.Bmain167:
319	li t2,1
320	j .Bmain161
.Bmain168:
321	li a0,5
	sd t2,-32(s0)
	sd t3,-40(s0)
322	call h
	ld t3,-40(s0)
	ld t2,-32(s0)
323	mv t3,a0
324	bne t3,zero,.Bmain174
325	j .Bmain171
.Bmain171:
326	li a0,8
	sd t2,-32(s0)
	sd t3,-40(s0)
327	call h
	ld t3,-40(s0)
	ld t2,-32(s0)
328	mv t3,a0
329	mv t2,zero
330	beq zero,t3,.Bmain167
331	j .Bmain161
.Bmain174:
332	li a0,6
	sd t2,-32(s0)
	sd t3,-40(s0)
333	call h
	ld t3,-40(s0)
	ld t2,-32(s0)
334	mv t2,a0
335	beq zero,t2,.Bmain177
336	j .Bmain171
.Bmain177:
337	li a0,7
	sd t2,-32(s0)
338	call h
	ld t2,-32(s0)
339	mv t2,a0
340	bne t2,zero,.Bmain167
341	j .Bmain171
.Bmain180:
342	lla t2,sum
343	lw t4,0(t2)
344	subw t2,t4,t3
345	mv a0,zero
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
346	call h
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
347	mv t2,a0
348	bne t2,zero,.Bmain213
349	j .Bmain210
.Bmain186:
350	li t3,1
351	j .Bmain180
.Bmain187:
352	li a0,11
	sd t2,-32(s0)
	sd t3,-40(s0)
353	call h
	ld t3,-40(s0)
	ld t2,-32(s0)
354	mv t2,a0
355	beq zero,t2,.Bmain186
356	j .Bmain193
.Bmain190:
357	li a0,10
	sd t2,-32(s0)
	sd t3,-40(s0)
358	call h
	ld t3,-40(s0)
	ld t2,-32(s0)
359	mv t2,a0
360	beq zero,t2,.Bmain186
361	j .Bmain187
.Bmain193:
362	li a0,12
	sd t2,-32(s0)
	sd t3,-40(s0)
363	call h
	ld t3,-40(s0)
	ld t2,-32(s0)
364	mv t2,a0
365	beq zero,t2,.Bmain186
366	j .Bmain196
.Bmain196:
367	li a0,13
	sd t2,-32(s0)
	sd t3,-40(s0)
368	call h
	ld t3,-40(s0)
	ld t2,-32(s0)
369	mv t2,a0
370	beq zero,t2,.Bmain186
371	j .Bmain199
.Bmain199:
372	li a0,14
	sd t2,-32(s0)
	sd t3,-40(s0)
373	call h
	ld t3,-40(s0)
	ld t2,-32(s0)
374	mv t2,a0
375	mv t3,zero
376	bne t2,zero,.Bmain202
377	j .Bmain180
.Bmain202:
378	li a0,15
	sd t2,-32(s0)
	sd t3,-40(s0)
379	call h
	ld t3,-40(s0)
	ld t2,-32(s0)
380	mv t2,a0
381	mv t3,zero
382	bne t2,zero,.Bmain186
383	j .Bmain180
.Bmain205:
384	lla t3,sum
385	lw t4,0(t3)
386	addw t3,t4,t2
387	mv a0,t3
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
388	call putint
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
389	mv a0,zero
390	ld ra,72(sp)
391	ld s0,64(sp)
392	addi sp,sp,80
393	jr ra
.Bmain209:
394	li t2,1
395	j .Bmain205
.Bmain210:
396	li a0,5
	sd t2,-32(s0)
	sd t3,-40(s0)
	sd t4,-48(s0)
397	call h
	ld t4,-48(s0)
	ld t3,-40(s0)
	ld t2,-32(s0)
398	mv t3,a0
399	bne t3,zero,.Bmain209
400	j .Bmain222
.Bmain213:
401	li a0,2
	sd t2,-32(s0)
	sd t3,-40(s0)
402	call h
	ld t3,-40(s0)
	ld t2,-32(s0)
403	mv t3,a0
404	bne t3,zero,.Bmain216
405	j .Bmain210
.Bmain216:
406	li a0,3
	sd t2,-32(s0)
	sd t3,-40(s0)
407	call h
	ld t3,-40(s0)
	ld t2,-32(s0)
408	mv t3,a0
409	beq zero,t3,.Bmain219
410	j .Bmain210
.Bmain219:
411	li a0,4
	sd t2,-32(s0)
	sd t3,-40(s0)
412	call h
	ld t3,-40(s0)
	ld t2,-32(s0)
413	mv t3,a0
414	beq zero,t3,.Bmain209
415	j .Bmain210
.Bmain222:
416	li a0,6
	sd t2,-32(s0)
	sd t3,-40(s0)
417	call h
	ld t3,-40(s0)
	ld t2,-32(s0)
418	mv t3,a0
419	bne t3,zero,.Bmain228
420	j .Bmain225
.Bmain225:
421	li a0,8
	sd t2,-32(s0)
	sd t3,-40(s0)
422	call h
	ld t3,-40(s0)
	ld t2,-32(s0)
423	mv t3,a0
424	mv t2,zero
425	bne t3,zero,.Bmain209
426	j .Bmain205
.Bmain228:
427	li a0,7
	sd t2,-32(s0)
	sd t3,-40(s0)
428	call h
	ld t3,-40(s0)
	ld t2,-32(s0)
429	mv t2,a0
430	beq zero,t2,.Bmain209
431	j .Bmain225
	.size	main, .-main
	.global	sum
	.bss
	.align	2
	.type	sum,@object
	.size	sum,4
sum:
	.zero	4
	.global	array
	.bss
	.align	3
	.type	array,@object
	.size	array,80
array:
	.zero	80
	.ident	"ForStar"
	.section	.note.GNU-stack,"",@progbits
