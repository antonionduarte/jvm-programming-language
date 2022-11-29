package ast.int_nodes;

import ast.ASTNode;
import ast.types.IValue;
import ast.types.IntValue;
import ast.types.Type;
import ast.types.ValueType;
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
		exp.compile(frame, codeBlock).expect(new ValueType(Type.INT));
		codeBlock.emit(CompilerUtils.NEG);
		return new ValueType(Type.INT);
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return new ValueType(Type.INT);
	}
}

