package ast;

import ast.types.IValue;
import ast.types.ValueType;
import compilation.CodeBlock;
import environment.Closure;
import environment.Environment;
import environment.Frame;

public class ASTFunction implements ASTNode {

	private Closure<IValue> closure;

	@Override
	public IValue eval(Environment<IValue> environment) {
		return null;
	}

	@Override
	public ValueType compile(Frame frame, CodeBlock codeBlock) {
		return null;
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return null;
	}
}
