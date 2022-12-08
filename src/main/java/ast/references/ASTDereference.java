package ast.references;

import ast.ASTId;
import ast.ASTNode;
import ast.typing.values.CellValue;
import ast.typing.values.IValue;
import ast.typing.types.ValueType;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;

public class ASTDereference implements ASTNode {
	private final ASTId id;

	public ASTDereference(ASTId id) {
		this.id = id;
	}

	/* TODO: Typecheck this */
	@Override
	public IValue eval(Environment<IValue> environment) {
		return ((CellValue) this.id.eval(environment)).getValue();
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
