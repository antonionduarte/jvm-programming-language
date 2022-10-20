package ast;

import codeblock.CodeBlock;

public class ASTDiv implements ASTNode {

	ASTNode lhs, rhs;

	public int eval() {
		int v1 = lhs.eval();
		int v2 = rhs.eval();
		return v1 / v2;
	}

	@Override
	public void compile(CodeBlock codeBlock) {

	}

	public ASTDiv(ASTNode l, ASTNode r) {
		lhs = l;
		rhs = r;
	}
}

