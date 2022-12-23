package ast.references;

import ast.ASTNode;
import ast.typing.types.IType;
import ast.typing.types.PrimitiveType;
import ast.typing.types.ReferenceType;
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
	public IType compile(Frame frame, CodeBlock codeBlock) {
		codeBlock.emit(CompilerUtils.initClass("Ref"));
		codeBlock.emit(CompilerUtils.DUPLICATE);
		IType type = expression.compile(frame, codeBlock);
		if(type.equals(PrimitiveType.Bool) || type.equals(PrimitiveType.Int))
			codeBlock.emit(CompilerUtils.setField("Ref", "vi", PrimitiveType.Int.getJvmId()));
		else codeBlock.emit(CompilerUtils.setField("Ref", "v",
					CompilerUtils.toReferenceType(CompilerUtils.OBJECT)));
		return new ReferenceType(type);
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {
		return null;
	}
}
