package ast;

import ast.typing.types.IType;
import ast.typing.values.IValue;
import compilation.CodeBlock;
import compilation.FrameCompiler;
import environment.Environment;
import environment.Frame;
import environment.FrameVariable;

public class ASTId implements ASTNode {

	private final String id;

	public ASTId(String id) {
		this.id = id;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		return environment.find(this.id);
	}

	@Override
	public IType compile(Frame frame, CodeBlock codeBlock) {
		FrameVariable variable = frame.find(id);
		FrameCompiler.emitGetValue(codeBlock, variable, frame);
		return variable.getType();
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {
		return environment.find(id);
	}
}
