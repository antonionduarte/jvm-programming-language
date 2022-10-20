package ast;

import codeblock.CodeBlock;

import java.util.HashMap;
import java.util.Map;

public class ASTDef implements ASTNode {

	private Map<String, ASTNode> definitionList;
	private ASTNode body;

	public ASTDef() {
		this.definitionList = new HashMap<>();
	}

	@Override
	public int eval() {
		return 0;
	}

	@Override
	public void compile(CodeBlock codeBlock) {

	}
}
