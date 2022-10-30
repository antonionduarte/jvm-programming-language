import ast.ASTNode;
import environment.Environment;
import parser.Parser;

public class Interpreter {
	/**
	 * Main entry point.
	 */
	public static void main(String[] args) {
		ASTNode exp;
		new Parser(System.in);

		while (true) {
			try {
				exp = Parser.Start();
				System.out.println(exp.eval(new Environment()));
			} catch (Exception e) {
				System.out.println("Syntax Error!");
				Parser.ReInit(System.in);
			}
		}
	}
}
