package ast;

import codeblock.CodeBlock;
import environment.Environment;

public class ASTNeg implements ASTNode {

	ASTNode exp;

	public int eval(Environment<Integer> environment) {
		return -exp.eval(environment);
	}

	@Override
	public void compile(CodeBlock codeBlock) {
		codeBlock.emit(JVMOps.NEG);
	}

	public ASTNeg(ASTNode exp) {
		this.exp = exp;
	}
}

