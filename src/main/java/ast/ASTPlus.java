package ast;

import codeblock.CodeBlock;
import environment.Environment;

public class ASTPlus implements ASTNode {

	ASTNode lhs, rhs;

	public int eval(Environment environment) {
		int v1 = lhs.eval(environment);
		int v2 = rhs.eval(environment);
		return v1 + v2;
	}

	@Override
	public void compile(CodeBlock codeBlock) {
		lhs.compile(codeBlock);
		rhs.compile(codeBlock);
		codeBlock.emit(JVMOps.ADD);
	}

	public ASTPlus(ASTNode l, ASTNode r) {
		lhs = l;
		rhs = r;
	}
}

