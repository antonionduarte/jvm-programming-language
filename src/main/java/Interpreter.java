import ast.ASTNode;
import environment.Environment;
import parser.Parser;

public class Interpreter {
	/**
	 * Main entry point.
	 */
	public static void main(String[] args) {
		new Parser(System.in);

		while (true) {
			try {
				var exp = Parser.Start();
				System.out.println(exp.eval(new Environment()));
			} catch (Exception e) {
				System.out.println("Syntax Error!");
				System.out.println(e.getMessage());
				Parser.ReInit(System.in);
			}
		}
	}
}
