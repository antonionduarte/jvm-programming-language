package ast;

import codeblock.CodeBlock;
import environment.Environment;

public class ASTId implements ASTNode {

	private String id;

	public ASTId(String id) {
		this.id = id;
	}

	@Override
	public int eval(Environment environment) {
		return environment.find(this.id);
	}

	@Override
	public void compile(CodeBlock codeBlock) {
	}
}
