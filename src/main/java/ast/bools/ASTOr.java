package ast.bools;

import ast.ASTNode;
import ast.typing.values.BoolValue;
import ast.typing.values.IValue;
import ast.typing.types.Type;
import ast.typing.types.ValueType;
import compilation.CodeBlock;
import compilation.CompilerUtils;
import environment.Environment;
import environment.Frame;

public class ASTOr implements ASTNode {
	private final ASTNode lhs;
	private final ASTNode rhs;

	public ASTOr(ASTNode lhs, ASTNode rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		boolean v1 = BoolValue.asBoolean(lhs.eval(environment));
		boolean v2 = BoolValue.asBoolean(rhs.eval(environment));
		return new BoolValue(v1 || v2);
	}

	@Override
	public ValueType compile(Frame frame, CodeBlock codeBlock) {
		codeBlock.emit(CompilerUtils.OR);
		return new ValueType(Type.Bool);
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return new ValueType(Type.Bool);
	}
}
