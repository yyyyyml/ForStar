import ast.Emitter;
import ast.Visitor;
import backend.RISCBuilder;
import backend.RISCEmitter;
import backend.RISCModule;
import frontend.SysY2022Lexer;
import frontend.SysY2022Parser;
import ir.Module;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import pass.PassDriver;

import java.io.IOException;

public class Compiler {
    public static void main(String[] args) {
        // -emit-llvm testcase.ll testcase.sy
        // -S -o testcase.s testcase.sy

        String inputFile = "";
        String midOutputFile = "";
        String outputFile = "testcase.s";
        boolean emitAssembly;
        boolean isPass = false;

        // 处理命令行参数
        if (args[0].equals("-S")) {
            for (int i = 0; i < args.length; i++) {
                if (i == 3) {
                    inputFile = args[i];
                    continue;
                }
                if (args[i].equals("-S")) {
                    emitAssembly = true;
                } else if (args[i].equals("-o")) {
                    if (i + 1 < args.length) {
                        outputFile = args[i + 1];
                        i++; // 跳过下一个参数
                    } else {
                        System.out.println("Error: Output file not specified");
                        return;
                    }
                } else if (args[i].equals("-O1")) {
                    isPass = true;
                    System.out.println("加入优化");
                } else {
                    System.out.println("Error: Invalid argument: " + args[i]);
                    return;
                }
            }

            // 将midOutputFile设置为输入文件名的基础上加上.ll扩展名
            String baseName = inputFile.substring(0, inputFile.lastIndexOf('.'));
            midOutputFile = baseName + ".ll";

        } else if (args[0].equals("-emit-llvm")) {
            midOutputFile = args[1];
            inputFile = args[2];
            outputFile = null;
        } else {
            System.out.println("Error: Invalid argument in -emit-llvm");
            return;
        }


        // 读取输入文件
        CharStream input;
        try {
            input = CharStreams.fromFileName(inputFile);
        } catch (IOException e) {
            System.out.println("Error: Failed to read input file");
            return;
        }

        // 词法分析
        SysY2022Lexer lexer = new SysY2022Lexer(input);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);

        // 语法分析
        SysY2022Parser parser = new SysY2022Parser(tokenStream);
        ParseTree ast = parser.compUnit();

        // 构建AST
        Module module = new Module();
        Visitor visitor = new Visitor(module);
        visitor.visit(ast);

        // 运行IR优化
        PassDriver passDriver = new PassDriver();
        passDriver.runIR(module);

        // 生成LLVM IR
        Emitter emitter = new Emitter(midOutputFile);
        emitter.emit(module);

//        // 运行IR优化
//        PassDriver passDriver = new PassDriver();
//        passDriver.runIR(module);

        if (outputFile == null) return;

        // 生成RISC代码
        RISCBuilder mcBuilder = RISCBuilder.get();
        mcBuilder.loadModule(module);
        RISCModule riscModule = mcBuilder.codeGeneration();

        // 在优化前打印RISC代码
        RISCEmitter mcEmitter = new RISCEmitter(outputFile);
        mcEmitter.emit(riscModule);

        // 运行后端优化
        passDriver.runBackend(riscModule);

        // 在优化后打印RISC代码
        mcEmitter.emit(riscModule);
    }
}
