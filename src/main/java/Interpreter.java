import ast.ASTNode;
import parser.Parser;

public class Interpreter {
	/**
	 * Main entry point.
	 */
	public static void main(String[] args) {
		ASTNode exp;

		while (true) {
			try {
				exp = Parser.Start();
				System.out.println(exp.eval());
			} catch (Exception e) {
				System.out.println("Syntax Error!");
				Parser.ReInit(System.in);
			}
		}
	}
}
