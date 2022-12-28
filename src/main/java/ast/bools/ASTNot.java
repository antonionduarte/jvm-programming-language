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
	public IType compile(Frame frame, CodeBlock codeBlock) {
		inner.compile(frame, codeBlock).expect(PrimitiveType.Bool);
		codeBlock.emit(CompilerUtils.PUSH_TRUE);
		//bitwise xor of 1 with 1 will give 0, and 0 with 1 will give 1
		codeBlock.emit(CompilerUtils.XOR);
		return PrimitiveType.Bool;
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {
		inner.typeCheck(environment).expect(PrimitiveType.Bool);
		return PrimitiveType.Bool;
	}
}


