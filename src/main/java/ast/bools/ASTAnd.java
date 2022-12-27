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

public class ASTAnd implements ASTNode {
	private final ASTNode lhs;
	private final ASTNode rhs;

	public ASTAnd(ASTNode lhs, ASTNode rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		boolean v1 = BoolValue.asBoolean(lhs.eval(environment));
		boolean v2 = BoolValue.asBoolean(rhs.eval(environment));
		return new BoolValue(v1 && v2);
	}

	@Override
	public IType compile(Frame frame, CodeBlock codeBlock) {
		lhs.compile(frame, codeBlock).expect(PrimitiveType.Bool);
		rhs.compile(frame, codeBlock).expect(PrimitiveType.Bool);
		codeBlock.emit(CompilerUtils.AND);
		return PrimitiveType.Bool;
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {
		lhs.typeCheck(environment).expect(PrimitiveType.Bool);
		rhs.typeCheck(environment).expect(PrimitiveType.Bool);
		return PrimitiveType.Bool;
	}
}
