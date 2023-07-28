//测试用主函数

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


public class demoMain {
    public static void main(String[] args) throws Exception {
        CharStream inputFile = CharStreams.fromFileName("src/main/java/testcase.sy");
        System.out.println(inputFile.toString()); // Test content read in.

        // lexical analysis
        SysY2022Lexer lexer = new SysY2022Lexer(inputFile);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);

        // parsing
        SysY2022Parser parser = new SysY2022Parser(tokenStream);
        ParseTree ast = parser.compUnit(); // Retrieve the parse tree (It's called AST but actually a CST)

        // print tree
        System.out.println(ast.toStringTree(parser));

        Module module = new Module();
        Visitor visitor = new Visitor(module);
        // Traversal the ast to build the IR.
        visitor.visit(ast);

        Emitter emitter = new Emitter("testcase.ll");
        emitter.emit(module);

        PassDriver passDriver = new PassDriver(false);
        passDriver.runIR(module);

        RISCBuilder mcBuilder = RISCBuilder.get();
        mcBuilder.loadModule(module);
        RISCModule riscModule = mcBuilder.codeGeneration();

        // 优化前
        RISCEmitter mcEmitter = new RISCEmitter("testcase.s");
        mcEmitter.emit(riscModule);

        passDriver.runBackend(riscModule);

        /* Write file */
        mcEmitter.emit(riscModule);


    }
}