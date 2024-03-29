package ast;

import ast.typing.types.IType;
import ast.typing.values.IValue;
import compilation.CodeBlock;
import compilation.FrameCompiler;
import environment.Environment;
import environment.Frame;
import environment.FrameVariable;

public class ASTDef implements ASTNode {

	private final String id;
	private final ASTNode node;

	public ASTDef(String id, ASTNode node) {
		this.id = id;
		this.node = node;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		IValue value = node.eval(environment);
		environment.associate(id, value);
		return value;
	}

	@Override
	public IType compile(Frame frame, CodeBlock codeBlock) {
		int varNumber = frame.getVars().size();
		FrameVariable variable = new FrameVariable(frame, varNumber);
		FrameCompiler.emitAssign(codeBlock, variable, node);
		frame.associate(id, variable);
		return variable.getType();
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {
		IType type = node.typeCheck(environment);
		environment.associate(id, type);
		return type;
	}
}
