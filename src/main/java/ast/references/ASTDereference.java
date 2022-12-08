package ast.references;

import ast.ASTNode;
import ast.typing.values.CellValue;
import ast.typing.values.IValue;
import ast.typing.types.ValueType;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;

public class ASTDereference implements ASTNode {
	private final ASTNode node;

	public ASTDereference(ASTNode node) {
		this.node = node;
	}

	/* TODO: Typecheck this */
	@Override
	public IValue eval(Environment<IValue> environment) {
		return ((CellValue) this.node.eval(environment)).getValue();
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
