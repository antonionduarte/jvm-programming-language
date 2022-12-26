package ast.typing.types;

import ast.references.ASTReference;
import compilation.CompilerUtils;

public class ReferenceType implements IType {
	private IType inner;
	public ReferenceType(IType type) {
		this.inner = type;
	}

	public IType getInnerType() {
		return inner;
	}

	@Override
	public void expect(IType other) {
		if(!this.equals(other)) {
			throw new TypeMismatchException(other, this);
		}
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof ReferenceType && ((ReferenceType) other).inner.equals(this.inner);
	}

	@Override
	public String getJvmId() {
		if(inner.equals(PrimitiveType.Int) || inner.equals(PrimitiveType.Bool)) {
			return CompilerUtils.toReferenceType(ASTReference.REF_OF_INT);
		} else {
			return CompilerUtils.toReferenceType(ASTReference.REF_OF_REF);
		}
	}

	@Override
	public String toString() {
		return "Ref<" + inner + ">";
	}
}
