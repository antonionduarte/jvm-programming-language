package ast;

import codeblock.CodeBlock;
import environment.Environment;

public class ASTNum implements ASTNode {

	int val;

	public int eval(Environment environment) {
		return val;
	}

	@Override
	public void compile(CodeBlock codeBlock) {
		codeBlock.emit(JVMOps.PUSH + " " + val);
	}

	public ASTNum(int n) {
		val = n;
	}
}
