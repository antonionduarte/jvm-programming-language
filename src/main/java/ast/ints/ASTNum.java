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

public class ASTNum implements ASTNode {

	int val;

	public ASTNum(int n) {
		val = n;
	}

	public IValue eval(Environment<IValue> environment) {
		return new IntValue(val);
	}

	@Override
	public IType compile(Frame frame, CodeBlock codeBlock) {
		codeBlock.emit(CompilerUtils.PUSH + " " + val);
		return PrimitiveType.Int;
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {
		return PrimitiveType.Int;
	}
}
