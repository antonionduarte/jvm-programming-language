package ast.references;

import ast.ASTNode;
import ast.typing.values.CellValue;
import ast.typing.values.IValue;
import ast.typing.types.ValueType;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;

public class ASTReference implements ASTNode {
	private final ASTNode expression;

	public ASTReference(ASTNode expression) {
		this.expression = expression;
	}

	/* TODO: Typecheck this or smth */
	@Override
	public IValue eval(Environment<IValue> environment) {
		return new CellValue(this.expression.eval(environment));
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
