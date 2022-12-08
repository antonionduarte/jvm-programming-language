package ast.ints;

import ast.ASTNode;
import ast.typing.values.IValue;
import ast.typing.values.IntValue;
import ast.typing.types.Type;
import ast.typing.types.ValueType;
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
	public ValueType compile(Frame frame, CodeBlock codeBlock) {
		exp.compile(frame, codeBlock).expect(new ValueType(Type.Int));
		codeBlock.emit(CompilerUtils.NEG);
		return new ValueType(Type.Int);
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return new ValueType(Type.Int);
	}
}

