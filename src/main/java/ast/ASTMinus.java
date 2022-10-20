package ast;

import codeblock.CodeBlock;
import jdk.jfr.internal.JVM;

public class ASTMinus implements ASTNode {

	ASTNode lhs, rhs;

	public int eval() {
		int v1 = lhs.eval();
		int v2 = rhs.eval();
		return v1 - v2;
	}

	@Override
	public void compile(CodeBlock codeBlock) {
		lhs.compile(codeBlock);
		rhs.compile(codeBlock);
		codeBlock.emit(JVMOps.MINUS);
	}

	public ASTMinus(ASTNode l, ASTNode r) {
		lhs = l;
		rhs = r;
	}
}

