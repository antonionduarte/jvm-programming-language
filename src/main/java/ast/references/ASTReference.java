package ast.references;

import ast.ASTNode;
import ast.typing.types.Type;
import ast.typing.types.ValueType;
import ast.typing.values.CellValue;
import ast.typing.values.IValue;
import compilation.CodeBlock;
import compilation.CompilerUtils;
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
		codeBlock.emit(CompilerUtils.initClass("Ref"));
		codeBlock.emit(CompilerUtils.DUPLICATE);
		ValueType type = expression.compile(frame, codeBlock);
		switch (type.getType()) {
			case Int, Bool -> codeBlock.emit(CompilerUtils.setField("Ref", "vi", Type.Int.getJvmId()));
			default -> codeBlock.emit(CompilerUtils.setField("Ref", "v",
					CompilerUtils.toReferenceType(CompilerUtils.OBJECT)));
		}
		return new ValueType(Type.Ref);
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return null;
	}
}
