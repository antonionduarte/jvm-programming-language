import ast.ASTNode;
import codeblock.CodeBlock;
import parser.ParseException;
import parser.Parser;

import java.io.*;
import java.util.Scanner;

public class TerminalCompiler {

	private static final String HEADER_FILE = "header/Header.j";
	private static final String ASSEMBLY_START = "; START";
	private static final String ASSEMBLY_END = "; END";
	private static final String ASSEMBLY_OUT = "./out.icl";

	public static void main(String[] args) throws IOException {
		var initialHeaders = new StringBuilder();
		var finalHeaders = new StringBuilder();
		readHeaders(initialHeaders, finalHeaders);

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

		/*try (var fileWriter = new FileWriter(ASSEMBLY_OUT)) {
			fileWriter.write(initialHeaders.toString());
			fileWriter.write(finalHeaders.toString());
		}*/
	}

	private static void readHeaders(StringBuilder initialHeaders, StringBuilder finalHeaders) throws FileNotFoundException {
		var file = new File(HEADER_FILE);

		try (var scanner = new Scanner(file)) {
			var inAssemblyZone = false;
			var afterAssemblyZone = false;
			while (scanner.hasNextLine()) {
				var next = scanner.nextLine();

				if (next.equals(ASSEMBLY_START)) {
					inAssemblyZone = true;
					initialHeaders.append(next).append('\n');
				} else if (next.equals(ASSEMBLY_END)) {
					inAssemblyZone = false;
					afterAssemblyZone = true;
				}

				if (!inAssemblyZone) {
					if (!afterAssemblyZone) {
						initialHeaders.append(next).append("\n");
					} else {
						finalHeaders.append(next).append("\n");
					}
				}
			}
		}
	}
}
