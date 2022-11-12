package ast;

import codeblock.CodeBlock;
import environment.Environment;
import environment.FrameManager;

public class ASTId implements ASTNode {

	private String id;

	public ASTId(String id) {
		this.id = id;
	}

	@Override
	public int eval(Environment<Integer> environment) {
		return environment.find(this.id);
	}

	@Override
	public void compile(FrameManager frameManager, CodeBlock codeBlock) {
		frameManager.emitGetValue(codeBlock, id);
	}
}
