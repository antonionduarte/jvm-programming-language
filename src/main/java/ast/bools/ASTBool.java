package ast.bools;

import ast.ASTNode;
import ast.typing.values.BoolValue;
import ast.typing.values.IValue;
import ast.typing.types.Type;
import ast.typing.types.ValueType;
import compilation.CodeBlock;
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
	public ValueType compile(Frame frame, CodeBlock codeBlock) {
		throw new RuntimeException("Not implemented"); //TODO implement
	}

	@Override
	public ValueType typeCheck(Environment environment) {
		return new ValueType(Type.Bool);
	}
}
