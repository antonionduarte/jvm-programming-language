package ast;

import ast.types.IValue;
import ast.types.ValueType;
import codeblock.CodeBlock;
import environment.Environment;
import environment.InterpretationEnvironment;
import environment.FrameManager;

public class ASTId implements ASTNode {

	private String id;

	public ASTId(String id) {
		this.id = id;
	}

	@Override
	public IValue eval(InterpretationEnvironment environment) {
		
		return environment.find(this.id);
	}

	@Override
	public void compile(FrameManager frameManager, CodeBlock codeBlock) {
		frameManager.emitGetValue(codeBlock, id);
	}

	@Override
	public ValueType getReturnType(Environment environment) {
		return environment.getAssociatedType(id);
	}
}
