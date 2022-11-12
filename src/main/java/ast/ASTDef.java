package ast;

import codeblock.CodeBlock;
import environment.Environment;
import environment.FrameManager;

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
	public int eval(Environment<Integer> environment) {
		var inner = environment.beginScope();
		for (var definition : definitions.entrySet()) {
			inner.associate(definition.getKey(), definition.getValue().eval(inner));
		}
		var value = body.eval(inner);
		environment.endScope();

		return value;
	}

	@Override
	public void compile(FrameManager frameManager, CodeBlock codeBlock) {
		frameManager.beginScope(codeBlock);
		for (var definition : definitions.entrySet()) {
			frameManager.emitAssign(codeBlock, definition.getKey(), definition.getValue());
		}
		body.compile(frameManager, codeBlock);
		frameManager.endScope(codeBlock);
	}
}
