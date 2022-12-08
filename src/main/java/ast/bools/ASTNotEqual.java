package ast.bools;

import ast.ASTNode;
import ast.typing.values.BoolValue;
import ast.typing.values.IValue;
import ast.typing.types.Type;
import ast.typing.types.ValueType;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;

public class ASTNotEqual implements ASTNode {
	private final ASTNode lhs;
	private final ASTNode rhs;

	public ASTNotEqual(ASTNode lhs, ASTNode rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		IValue v1 = lhs.eval(environment);
		IValue v2 = rhs.eval(environment);
		return new BoolValue(!v1.equals(v2));
	}

	@Override
	public ValueType compile(Frame frame, CodeBlock codeBlock) {
		throw new RuntimeException("Not implemented"); //TODO implement
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return new ValueType(Type.Bool);
	}
}

