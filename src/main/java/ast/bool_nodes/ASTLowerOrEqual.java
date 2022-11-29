package ast.bool_nodes;

import ast.ASTNode;
import ast.types.*;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;

public class ASTLowerOrEqual implements ASTNode {
	private final ASTNode lhs;
    private final ASTNode rhs;

	public ASTLowerOrEqual(ASTNode lhs, ASTNode rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		int v1 = IntValue.asInt(lhs.eval(environment));
		int v2 = IntValue.asInt(rhs.eval(environment));
		return new BoolValue(v1 <= v2);
	}

	@Override
	public ValueType compile(Frame frame, CodeBlock codeBlock) {
		throw new RuntimeException("Not implemented"); //TODO implement
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return new ValueType(Type.BOOL);
	}
}
