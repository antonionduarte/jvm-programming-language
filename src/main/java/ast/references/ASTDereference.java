package ast.references;

import ast.ASTNode;
import ast.typing.types.*;
import ast.typing.values.CellValue;
import ast.typing.values.IValue;
import compilation.CodeBlock;
import compilation.CompilerUtils;
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
		IValue val = this.node.eval(environment);
		if(!(val instanceof CellValue cellValue)){
			throw new TypeMismatchException("Reference", val.getType());
		}
		return cellValue.getValue();
	}

	@Override
	public IType compile(Frame frame, CodeBlock codeBlock) {
		IType type = node.compile(frame, codeBlock);
		if (!(type instanceof ReferenceType refType)) {
			throw new TypeMismatchException("Reference", type);
		}
		String className, fieldType;
		IType inner = refType.getInnerType();
		//TODO rework this
		if (inner.equals(PrimitiveType.Bool) || inner.equals(PrimitiveType.Int)) {
			className = ASTReference.REF_OF_INT;
			fieldType = PrimitiveType.Int.getJvmId();
			codeBlock.emit(CompilerUtils.getField(className, ASTReference.FIELD_NAME, fieldType));
		} else if (inner instanceof ObjectType objType) {
			className = ASTReference.REF_OF_REF;
			fieldType = CompilerUtils.toReferenceType(CompilerUtils.OBJECT);
			codeBlock.emit(CompilerUtils.getField(className, ASTReference.FIELD_NAME, fieldType));
			codeBlock.emit("checkcast " + objType.getClassName());
		}
		return inner;
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {
		return null;
	}
}
