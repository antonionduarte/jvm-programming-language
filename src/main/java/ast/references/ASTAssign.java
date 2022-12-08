package ast.references;

import ast.ASTId;
import ast.ASTNode;
import ast.typing.values.CellValue;
import ast.typing.values.IValue;
import ast.typing.types.ValueType;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;

public class ASTAssign implements ASTNode {
	private final ASTNode expression;
	private final ASTId id;

	public ASTAssign(ASTId id, ASTNode expression) {
		this.expression = expression;
		this.id = id;
	}

	/* TODO: Typecheck stuff */
	@Override
	public IValue eval(Environment<IValue> environment) {
		var cell = (CellValue) id.eval(environment);
		var value = this.expression.eval(environment);
		cell.setValue(value);
		return value; // TODO: Is this correct? does it need to return anything?
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
