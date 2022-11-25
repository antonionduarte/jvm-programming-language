import environment.Environment;
import parser.Parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Interpreter {
	/**
	 * Main entry point.
	 */
	public static void main(String[] args) throws IOException {
		String path = null;

		if (args.length == 1) {
			path = args[0];
		}

		InputStream in = path == null ? System.in : new FileInputStream(path);

		new Parser(in);

		while (path == null || in.available() > 0) {
			try {
				var exp = Parser.Start();
				System.out.println(exp.eval(new Environment<>()).toString());
			} catch (Exception e) {
				System.out.println("Syntax Error!");
				System.out.println(e.getMessage());
				Parser.ReInit(System.in);
			}
		}
	}
}
