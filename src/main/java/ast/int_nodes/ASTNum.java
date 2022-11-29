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

public class ASTNum implements ASTNode {

	int val;

	public ASTNum(int n) {
		val = n;
	}

	public IValue eval(Environment<IValue> environment) {
		return new IntValue(val);
	}

	@Override
	public ValueType compile(Frame frame, CodeBlock codeBlock) {
		codeBlock.emit(CompilerUtils.PUSH + " " + val);
		return new ValueType(Type.INT);
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return new ValueType(Type.INT);
	}
}
