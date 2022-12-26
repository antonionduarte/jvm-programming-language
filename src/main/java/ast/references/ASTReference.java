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
	public static final String REF_OF_INT = "ref_of_int";
	public static final String REF_OF_REF = "ref_of_ref";
	public static final String FIELD_NAME = "v";
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
		CodeBlock.DelayedOp classInit = codeBlock.delayEmit();
		codeBlock.emit(CompilerUtils.DUPLICATE);
		IType type = expression.compile(frame, codeBlock);
		String className, fieldType;
		if (type.equals(PrimitiveType.Bool) || type.equals(PrimitiveType.Int)) {
			className = REF_OF_INT;
			fieldType = PrimitiveType.Int.getJvmId();
		} else {
			className = REF_OF_REF;
			fieldType = CompilerUtils.toReferenceType(CompilerUtils.OBJECT);
		}
		classInit.set(CompilerUtils.initClass(className));
		codeBlock.emit(CompilerUtils.setField(className, FIELD_NAME, fieldType));
		return new ReferenceType(type);
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {
		return null;
	}
}
