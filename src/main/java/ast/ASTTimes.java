package ast;

import codeblock.CodeBlock;

public class ASTTimes implements ASTNode {

	ASTNode lhs, rhs;

	public int eval() {
		int v1 = lhs.eval();
		int v2 = rhs.eval();
		return v1 * v2;
	}

	@Override
	public void compile(CodeBlock codeBlock) {
		lhs.compile(codeBlock);
		rhs.compile(codeBlock);
		codeBlock.emit(JVMOps.MUL);
	}

	public ASTTimes(ASTNode l, ASTNode r) {
		lhs = l;
		rhs = r;
	}
}

