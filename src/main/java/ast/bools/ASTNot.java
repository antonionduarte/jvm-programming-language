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
		inner.compile(frame,codeBlock).expect(new ValueType(Type.Bool));
		codeBlock.emit(CompilerUtils.PUSH_TRUE);
		//bitwise xor of 1 with 1 will give 0, and 0 with 1 will give 1
		codeBlock.emit(CompilerUtils.XOR);
		return new ValueType(Type.Bool);
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return new ValueType(Type.Bool);
	}
}


