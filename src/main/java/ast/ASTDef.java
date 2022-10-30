package ast;

import codeblock.CodeBlock;
import environment.Environment;

import java.util.HashMap;
import java.util.Map;

public class ASTDef implements ASTNode {

	private final Map<String, ASTNode> definitions;
	private final ASTNode body;

	public ASTDef(ASTNode body, HashMap<String, ASTNode> definitions) {
		this.definitions = definitions;
		this.body = body;
	}

	@Override
	public int eval(Environment environment) {
		var inner = environment.beginScope();
		for (var definition : definitions.entrySet()) {
			inner.associate(definition.getKey(), definition.getValue().eval(inner));
		}
		var value = body.eval(inner);
		environment.endScope();

		return value;
	}

	@Override
	public void compile(CodeBlock codeBlock) {

	}
}
