import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;


public class Compiler {
    public static void main(String args[]) throws FileNotFoundException {
        Scanner template = new Scanner(new File("template.txt"));
        PrintStream outfile = new PrintStream(args[0]);
        Parser parser = new Parser(System.in);
        CodeBlock code = new CodeBlock();
        while (true) {
            try {
                ASTNode ast = parser.Start();
                String tLine = template.nextLine();
                while (!tLine.equals("    ; CODE HERE")){
                    outfile.println(tLine);
                    tLine = template.nextLine();
                }
                ast.compile(code);
                code.dump(outfile);
                while (template.hasNextLine()){
                    outfile.println(template.nextLine());
                }
            } catch (ParseException e) {
                System.out.println ("Syntax Error!");
                parser.ReInit(System.in);
            }
        }
    }
}