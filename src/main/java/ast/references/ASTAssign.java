package ast.references;

import ast.ASTId;
import ast.ASTNode;
import ast.typing.types.IType;
import ast.typing.types.PrimitiveType;
import ast.typing.types.ReferenceType;
import ast.typing.types.TypeMismatchException;
import ast.typing.values.CellValue;
import ast.typing.values.IValue;
import compilation.CodeBlock;
import compilation.CompilerUtils;
import environment.Environment;
import environment.Frame;

public class ASTAssign implements ASTNode {
	private final ASTNode expression;
	private final ASTId ref;

	public ASTAssign(ASTId ref, ASTNode expression) {
		this.expression = expression;
		this.ref = ref;
	}

	/* TODO: Typecheck stuff */
	@Override
	public IValue eval(Environment<IValue> environment) {
		var cell = (CellValue) ref.eval(environment);
		var value = this.expression.eval(environment);
		cell.setValue(value);
		return value; // TODO: Is this correct? does it need to return anything?
	}

	@Override
	public IType compile(Frame frame, CodeBlock codeBlock) {
		IType type = expression.compile(frame, codeBlock);
		codeBlock.emit(CompilerUtils.DUPLICATE);
		IType refType = ref.compile(frame, codeBlock);
		if (!(refType instanceof ReferenceType referenceType)) {
			throw new TypeMismatchException("Reference", refType);
		} else {
			type.expect(referenceType.getInnerType());
		}
		codeBlock.emit(CompilerUtils.SWAP);
		String className, fieldType;
		if (type.equals(PrimitiveType.Bool) || type.equals(PrimitiveType.Int)) {
			className = ASTReference.REF_OF_INT;
			fieldType = PrimitiveType.Int.getJvmId();
		} else {
			className = ASTReference.REF_OF_REF;
			fieldType = CompilerUtils.toReferenceType(CompilerUtils.OBJECT);
		}
		codeBlock.emit(CompilerUtils.setField(className, ASTReference.FIELD_NAME, fieldType));
		return type;
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {
		return null;
	}
}
