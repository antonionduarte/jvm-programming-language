package ast.bools;

import ast.ASTNode;
import ast.typing.types.IType;
import ast.typing.types.PrimitiveType;
import ast.typing.values.BoolValue;
import ast.typing.values.IValue;
import compilation.CodeBlock;
import compilation.CompilerUtils;
import environment.Environment;
import environment.Frame;

public class ASTBool implements ASTNode {
	private final boolean value;

	public ASTBool(boolean value) {
		this.value = value;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		return new BoolValue(value);
	}

	@Override
	public IType compile(Frame frame, CodeBlock codeBlock) {
		if (value) {
			codeBlock.emit(CompilerUtils.PUSH_TRUE);
		} else {
			codeBlock.emit(CompilerUtils.PUSH_FALSE);
		}
		return PrimitiveType.Bool;
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {
		return PrimitiveType.Bool;
	}
}
