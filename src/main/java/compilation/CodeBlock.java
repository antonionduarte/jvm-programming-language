package compilation;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class CodeBlock {
	private final List<String> code;

	public CodeBlock() {
		this.code = new LinkedList<>();
	}

	public String emitLabel() {
		String label = String.format("Label%s", code.size());
		emit(label + ":");
		return label;
	}

	public DelayedOp delayEmit() {
		DelayedOp delayed = new DelayedOp(code.size());
		code.add("");
		return delayed;
	}

	public void emit(String opcode) {
		code.add(opcode);
	}

	public void dump(PrintStream printStream) {
		for (var elem : this.code) {
			printStream.println(elem);
		}
	}

	public class DelayedOp {
		private final int i;

		private DelayedOp(int i) {
			this.i = i;
		}

		public void set(String opcode) {
			code.set(i, opcode);
		}
	}
}
