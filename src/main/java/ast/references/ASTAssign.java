package ast.references;

import ast.ASTId;
import ast.ASTNode;
import ast.typing.types.Type;
import ast.typing.types.ValueType;
import ast.typing.values.CellValue;
import ast.typing.values.IValue;
import compilation.CodeBlock;
import compilation.CompilerUtils;
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
		ValueType type = expression.compile(frame, codeBlock);
		codeBlock.emit(CompilerUtils.DUPLICATE);
		id.compile(frame, codeBlock);
		codeBlock.emit(CompilerUtils.SWAP);
		switch (type.getType()) {
			case Int, Bool -> codeBlock.emit(CompilerUtils.setField("Ref", "vi", Type.Int.getJvmId()));
			default -> codeBlock.emit(CompilerUtils.setField("Ref", "v",
					CompilerUtils.toReferenceType(CompilerUtils.OBJECT)));
		}
		return type;
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return null;
	}
}
