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
- 实现后端结构定义
- 实现目标代码语句对象和操作数对象
- 实现目标代码生成

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

Compiler中核心代码如下，体现了编译器的执行过程：

```java
    // 词法分析
    SysY2022Lexer lexer=new SysY2022Lexer(input);
            CommonTokenStream tokenStream=new CommonTokenStream(lexer);

            // 语法分析
            SysY2022Parser parser=new SysY2022Parser(tokenStream);
            ParseTree ast=parser.compUnit();

            // 构建AST
            Module module =new Module();
        Visitor visitor=new Visitor(module);
        visitor.visit(ast);

        // 生成LLVM IR
        Emitter emitter=new Emitter(midOutputFile);
        emitter.emit(module);

        // 运行IR优化
        PassDriver passDriver=new PassDriver();
        passDriver.runIR(module);

        // 生成RISC代码
        RISCBuilder mcBuilder=RISCBuilder.get();
        mcBuilder.loadModule(module);
        RISCModule riscModule=mcBuilder.codeGeneration();

        // 在优化前打印RISC代码
        RISCEmitter mcEmitter=new RISCEmitter(outputFile);
        mcEmitter.emit(riscModule);

        // 运行后端优化
        passDriver.runBackend(riscModule);

        // 在优化后打印RISC代码
        mcEmitter.emit(riscModule);

```

## 4.1 前端

## 4.2 IR

IR参考llvm的IR结构和形式。

文件目录结构如下：

- ir：IR文件夹
    - Module：一个程序的顶层结构
    - User：继承自Value类 可有操作数
    - Use：表示User和Value的使用关系
    - Value：大部分类的基类
    - Type：一些复杂类型的基类 简单指令直接在此构造单例
    - Instruction：所有指令的基类
    - Values：包含一些继承自Value类或User类的类
        - BasicBlock：基本块类
        - Constant：常数类
        - Function：函数类
        - GlobalVariable：全局变量类
    - Types：包含一些复杂的继承自Type类的类
        - ArrayType：数组类型类
        - FunctionType：函数类型类
        - PointerType：指针类型类
    - Instructions：全部继承自Instruction类的指令
        - BinaryInst：二元运算指令类
        - ConversionInst：转换有关的指令类
        - MemoryInst：内存操作有关的指令类
        - TerminatorInst：基本块终结的指令类（call不是也放里面了）
        - FnegInst：fneg指令

## 4.3 后端

后端文件目录结构如下：
-backend:后端文件夹
    -RISCModule:目标代码整体的类，其余类为该类之下的属性
    -RISCBuilder:生成目标代码的类，输入irmodule，生成riscmodule
    -RISCFunction:目标代码的函数类，其中含有本函数的块
    -RISCBasicBlock:目标代码的基本块类，其中含有本块的指令列表
    -RISCInstruction:所有指令对象的父类
    -RISCOperand:所有操作数对象的父类
    -RISCGlobalvalBlock:为一个块，存放一个全局变量
    -RISCFloatBlock:存放一个浮点数值的块
    -RISCEmitter:打印目标代码
    -instructions：目标代码指令对象文件夹
        -LwInstruction
        -SwInstruction
        ...
    -operands:目标代码操作数对象文件夹
        -Immediate
        -Register
        ...

## 4.4 优化

优化部分目前只实现了后端的寄存器分配，采用线性扫描法分配寄存器，基本的文件结构已设计好。

文件目录结构如下：

- pass：优化文件夹
    - ir：IR优化文件夹
        - BaseIRPass：IR优化基类
    - backend：后端优化文件夹
        - BaseBackendPass：后端优化基类
        - RegisterAllocator：优化：寄存器分配文件夹
            - LiveInterval：生命周期类
            - RegisterUsage：寄存器使用情况类
            - RegisterUsageTracker：按时间记录寄存器使用情况的类
            - RegisterAllocator：整数寄存器分配类
            - FloatRegisterAllocator：浮点数寄存器分配类
    - PassDriver：优化驱动类

## 4.5 测试

测试包括前端测试和后端测试，前端测试用于测试生成的中间代码的正确性，后端测试用于测试生成的汇编文件的正确性，即编译器的正确性。后端测试包括两个步骤，先在linux环境下对测试用例生成对应的汇编文件，然后在risc-v环境下将这些汇编文件链接成可执行文件并运行，对比得到结果。

文件目录结构如下：

- ForStar-tester：测试文件夹
    - frontend_tester：前端测试
        - jdk-17.0.7：jdk
        - testcases：测试用例文件夹
        - sylib.ll：运行时库
        - ForStar.jar：编译器可执行文件
        - caseloader.py：读取测试用例类
        - frontend_tester.py：前端测试类
        - run.py：启动脚本
    - backend_tester_x86：后端测试x86
        - jdk-17.0.7：jdk
        - testcases：测试用例文件夹
        - ForStar.jar：编译器可执行文件
        - out：得到的全部结果
        - caseloader.py：读取测试用例类
        - backend_tester.py：后端测试类
        - run.py：启动脚本
    - backend_tester_riscv：后端测试risc-v
        - in：测试用例所需的可能的输入文件夹
        - std_out：测试用例的标准结果文件夹
        - asm：待测汇编代码文件夹
        - sylib.o：运行时库
        - caseloader.py：读取测试用例类
        - BackendTest.py：后端测试类
        - run.py：启动脚本

# 5 参考资料

https://github.com/BUAA-SE-Compiling/miniSysY_example_compiler

https://github.com/cabinz/cbias

https://github.com/cabinz/cbias-tester

https://llvm.org/docs/LangRef.html
