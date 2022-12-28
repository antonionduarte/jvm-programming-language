package ast.ints;

import ast.ASTNode;
import ast.typing.types.IType;
import ast.typing.types.PrimitiveType;
import ast.typing.values.IValue;
import ast.typing.values.IntValue;
import compilation.CodeBlock;
import compilation.CompilerUtils;
import environment.Environment;
import environment.Frame;

public class ASTDiv implements ASTNode {

	ASTNode lhs, rhs;

	public ASTDiv(ASTNode l, ASTNode r) {
		lhs = l;
		rhs = r;
	}

	public IValue eval(Environment<IValue> environment) {
		int v1 = IntValue.asInt(lhs.eval(environment));
		int v2 = IntValue.asInt(rhs.eval(environment));
		return new IntValue(v1 / v2);
	}

	@Override
	public IType compile(Frame frame, CodeBlock codeBlock) {
		lhs.compile(frame, codeBlock).expect(PrimitiveType.Int);
		rhs.compile(frame, codeBlock).expect(PrimitiveType.Int);
		codeBlock.emit(CompilerUtils.DIV);
		return PrimitiveType.Int;
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {
		lhs.typeCheck(environment).expect(PrimitiveType.Int);
		rhs.typeCheck(environment).expect(PrimitiveType.Int);
		return PrimitiveType.Int;
	}
}

