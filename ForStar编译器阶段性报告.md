杨孟林 1120201032
王子信 1120201043
梅展郡 1120203553

# 1 目前进度

- 前端：
- IR：全部完成
- 后端：
- 优化：目前只有后端的寄存器分配，IR优化正在调研
- 测试：前端测试、后端测试均可用，参考cbias-tester

# 2 人员分工

### 梅展郡：

- 调研
-

### 杨孟林：

- 调研
- 实现IR结构定义
- 实现寄存器分配
- 实现测试

### 王子信：

- 调研
-

# 3 结果展示

## 3.1 前端测试

## 3.2 后端测试

## 3.3 大赛测评

# 4 设计与实现

项目全部代码位于：https://github.com/yyyyyml/ForStar
项目源码位于：https://gitlab.eduxiji.net/202310007201641/compiler2023-forstar
项目结构如下：
![42f79b9087b101cb9f123a3d3d3d89a8.png](:/fc0d3d854b6c46b7b3417a33bd87a40d)

- frontend：包含ANTLR生成的词法分析器和语法分析器等文件
- ast：实现将语法树转化为IR结构的相关文件
- ir：包含IR结构定义
- pass：包含IR优化和后端优化
- backend：包含后端结构定义以及翻译成汇编代码的实现
- util：自定义工具
- Compiler：启动文件

## 4.1 前端

## 4.2 IR

## 4.3 后端

## 4.4 优化

## 4.5 测试

# 5 参考资料

https://github.com/BUAA-SE-Compiling/miniSysY_example_compiler
https://github.com/cabinz/cbias
https://github.com/cabinz/cbias-tester
https://llvm.org/docs/LangRef.html