package ast;

import codeblock.CodeBlock;

public class ASTNum implements ASTNode {

	int val;

	public int eval() {
		return val;
	}

	@Override
	public void compile(CodeBlock codeBlock) {

	}

	public ASTNum(int n) {
		val = n;
	}
}