import ast.ASTNode;
import ast.typing.types.IType;
import ast.typing.types.PrimitiveType;
import compilation.CodeBlock;
import compilation.CompilerUtils;
import compilation.FrameCompiler;
import environment.Frame;
import parser.ParseException;
import parser.Parser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Compiler {
	private static final String HEADER_FILE = "header/Header.j";
	private static final String REF_INT_FILE = "header/ref_of_int.j";
	private static final String REF_REF_FILE = "header/ref_of_ref.j";
	private static final String ASSEMBLY_START = "; START";
	private static final String ASSEMBLY_END = "; END";

	private static final String OUT_DIR = "compiled";
	private static final String ASSEMBLY_OUT = OUT_DIR + "/out.j";
	private static final String REF_INT_OUT = OUT_DIR + "/ref_of_int.j";
	private static final String REF_REF_OUT = OUT_DIR + "/ref_of_ref.j";

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			System.err.println("Error:");
			System.err.println("\tCorrect usage: Compiler <input-file>");
			System.exit(0);
		}

		var inputPath = args[0];
		var initialHeaders = new StringBuilder();
		var finalHeaders = new StringBuilder();

		CodeBlock codeBlock = new CodeBlock();
		readHeaders(initialHeaders, finalHeaders);

		try (var fis = new FileInputStream(inputPath)) {
			var str = new String(fis.readAllBytes());
			System.out.println(str);
		}

		new Parser(new FileInputStream(inputPath));
		try (var outputFile = new PrintStream(ASSEMBLY_OUT)) {
			ASTNode exp = Parser.Start();
			outputFile.write(initialHeaders.toString().getBytes());
			List<Frame> frames = new ArrayList<>();
			Frame root = new Frame(0, frames);
			frames.add(root);
			IType left = exp.compile(root, codeBlock);

			if (left != PrimitiveType.Void) {
				// required: jvm will throw an error if the stack is not empty in the end
				codeBlock.emit(CompilerUtils.DISCARD);
			}

			codeBlock.dump(outputFile);

			FrameCompiler.dumpAll(OUT_DIR, frames);
			FrameCompiler.dumpAllClosuresInterfaces(OUT_DIR);

			Files.copy(Path.of(REF_INT_FILE), Path.of(REF_INT_OUT));
			Files.copy(Path.of(REF_REF_FILE), Path.of(REF_REF_OUT));

			outputFile.write(finalHeaders.toString().getBytes());
		} catch (ParseException e) {
			System.out.println("Syntax Error!");
			e.printStackTrace();
			Parser.ReInit(System.in);
		}
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
