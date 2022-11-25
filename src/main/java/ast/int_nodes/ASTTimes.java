package ast.int_nodes;

import ast.ASTNode;
import compilation.CompilerUtils;
import ast.types.IValue;
import ast.types.IntValue;
import ast.types.ValueType;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;

public class ASTTimes implements ASTNode {

	ASTNode lhs, rhs;

	public IValue eval(Environment<IValue> environment) {
		int v1 = IntValue.asInt(lhs.eval(environment));
		int v2 = IntValue.asInt(rhs.eval(environment));
		return new IntValue(v1 * v2);
	}

	@Override
	public ValueType compile(Frame frame, CodeBlock codeBlock) {
		lhs.compile(frame, codeBlock).expect(ValueType.Int);
		rhs.compile(frame, codeBlock).expect(ValueType.Int);
		codeBlock.emit(CompilerUtils.MUL);
		return ValueType.Int;
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return ValueType.Int;
	}

	public ASTTimes(ASTNode l, ASTNode r) {
		lhs = l;
		rhs = r;
	}
}

