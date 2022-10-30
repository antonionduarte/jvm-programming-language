package ast;

import codeblock.CodeBlock;
import environment.Environment;

import java.util.HashMap;
import java.util.Map;

public class ASTDef implements ASTNode {

	private Map<String, ASTNode> definitionList;
	private ASTNode body;

	public ASTDef() {
		this.definitionList = new HashMap<>();
	}

	@Override
	public int eval(Environment environment) {
		environment.beginScope();
		for (var definition : definitionList.entrySet()) {
			environment.associate(definition.getKey(), definition.getValue());
		}
		var value = body.eval(environment);
		environment.endScope();

		return value;
	}

	@Override
	public void compile(CodeBlock codeBlock) {

	}
}
