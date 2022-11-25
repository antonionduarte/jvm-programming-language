package ast.int_nodes;

import ast.ASTNode;
import compilation.CompilerUtils;
import ast.types.IValue;
import ast.types.IntValue;
import ast.types.ValueType;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;

public class ASTNum implements ASTNode {

	int val;

	public IValue eval(Environment<IValue> environment) {
		return new IntValue(val);
	}

	@Override
	public ValueType compile(Frame frame, CodeBlock codeBlock) {
		codeBlock.emit(CompilerUtils.PUSH + " " + val);
		return ValueType.Int;
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return ValueType.Int;
	}

	public ASTNum(int n) {
		val = n;
	}
}
