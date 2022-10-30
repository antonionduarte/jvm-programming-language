package ast;

import codeblock.CodeBlock;
import environment.Environment;

public class ASTTimes implements ASTNode {

	ASTNode lhs, rhs;

	public int eval(Environment environment) {
		int v1 = lhs.eval(environment);
		int v2 = rhs.eval(environment);
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

