package ast;

import codeblock.CodeBlock;
import environment.Environment;
import environment.FrameManager;

public class ASTNum implements ASTNode {

	int val;

	public int eval(Environment<Integer> environment) {
		return val;
	}

	@Override
	public void compile(FrameManager frameManager, CodeBlock codeBlock) {
		codeBlock.emit(JVMOps.PUSH + " " + val);
	}

	public ASTNum(int n) {
		val = n;
	}
}
