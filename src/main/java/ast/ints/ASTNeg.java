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

public class ASTNeg implements ASTNode {

	ASTNode exp;

	public ASTNeg(ASTNode exp) {
		this.exp = exp;
	}

	public IValue eval(Environment<IValue> environment) {
		int value = IntValue.asInt(exp.eval(environment));
		return new IntValue(-value);
	}

	@Override
	public IType compile(Frame frame, CodeBlock codeBlock) {
		exp.compile(frame, codeBlock).expect(PrimitiveType.Int);
		codeBlock.emit(CompilerUtils.NEG);
		return PrimitiveType.Int;
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {
		exp.typeCheck(environment).expect(PrimitiveType.Int);
		return PrimitiveType.Int;
	}
}

