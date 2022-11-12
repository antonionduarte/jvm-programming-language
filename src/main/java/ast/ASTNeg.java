package ast;

import codeblock.CodeBlock;
import environment.Environment;
import environment.FrameManager;

public class ASTNeg implements ASTNode {

	ASTNode exp;

	public int eval(Environment<Integer> environment) {
		return -exp.eval(environment);
	}

	@Override
	public void compile(FrameManager frameManager, CodeBlock codeBlock) {
		codeBlock.emit(JVMOps.NEG);
	}

	public ASTNeg(ASTNode exp) {
		this.exp = exp;
	}
}

