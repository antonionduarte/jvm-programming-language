import ast.ASTNode;
import codeblock.CodeBlock;

public class Compiler {
	public static void main(String[] args) {
		while (true) {
			CodeBlock codeBlock = new CodeBlock();
			try {
				System.out.println(">");
				ASTNode exp = Parser.Start();
				exp.compile(codeBlock);

			} catch (ParseException e) {
				System.out.println("Syntax Error!");
				Parser.ReInit(System.in);
			}
		}
	}
}