package ast.bool_nodes;

import ast.ASTNode;
import ast.types.BoolValue;
import ast.types.IValue;
import ast.types.Type;
import ast.types.ValueType;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;

public class ASTNot implements ASTNode {
	private final ASTNode inner;

	public ASTNot(ASTNode inner) {
		this.inner = inner;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		boolean v = BoolValue.asBoolean(inner.eval(environment));
		return new BoolValue(!v);
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


