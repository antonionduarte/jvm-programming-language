package ast;

import codeblock.CodeBlock;

public class ASTNeg implements ASTNode {

	ASTNode exp;

	public int eval() {
		return -exp.eval();
	}

	@Override
	public void compile(CodeBlock codeBlock) {
		exp.compile(codeBlock);
		// codeBlock.emit();
	}

	public ASTNeg(ASTNode exp) {
		this.exp = exp;
	}
}

