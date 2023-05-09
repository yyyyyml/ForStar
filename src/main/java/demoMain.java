//主函数
import ast.*;
import frontend.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;


public class demoMain {
    public static void main(String[] args) throws Exception {
        CharStream inputFile = CharStreams.fromFileName("src/main/java/test.txt");
        System.out.println(inputFile.toString()); // Test content read in.

        // lexical analysis
        SysY2022Lexer lexer = new SysY2022Lexer(inputFile);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);

        // parsing
        SysY2022Parser parser = new SysY2022Parser(tokenStream);
        ParseTree ast = parser.compUnit(); // Retrieve the parse tree (It's called AST but actually a CST)

        // print tree
        System.out.println(ast.toStringTree(parser));

        Visitor visitor = new Visitor();
        // Traversal the ast to build the IR.
        visitor.visit(ast);


    }
}