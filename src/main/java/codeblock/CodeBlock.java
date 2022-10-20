package codeblock;

import java.io.PrintStream;

public class CodeBlock {
	private String[] code;
	private int pc;

	public void emit(String opcode) {
		code[pc++] = opcode;
	}

	public void dump(PrintStream printStream) {
		// dumps code to PrintStream
	}

}
