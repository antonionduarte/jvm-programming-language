package ast;

import codeblock.CodeBlock;
import environment.Environment;
import environment.FrameManager;

public class ASTPlus implements ASTNode {

	ASTNode lhs, rhs;

	public int eval(Environment<Integer> environment) {
		int v1 = lhs.eval(environment);
		int v2 = rhs.eval(environment);
		return v1 + v2;
	}

	@Override
	public void compile(FrameManager frameManager, CodeBlock codeBlock) {
		lhs.compile(frameManager, codeBlock);
		rhs.compile(frameManager, codeBlock);
		codeBlock.emit(JVMOps.ADD);
	}

	public ASTPlus(ASTNode l, ASTNode r) {
		lhs = l;
		rhs = r;
	}
}

