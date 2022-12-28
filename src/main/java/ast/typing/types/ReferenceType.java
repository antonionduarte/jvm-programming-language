package ast.typing.types;

import ast.references.ASTReference;
import compilation.CompilerUtils;

import java.util.Objects;

public class ReferenceType extends ObjectType {
	private final IType inner;

	public ReferenceType(IType type) {
		this.inner = type;
	}

	public IType getInnerType() {
		return inner;
	}

	@Override
	public void expect(IType other) {
		if (!this.equals(other)) {
			throw new TypeMismatchException(other, this);
		}
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof ReferenceType && ((ReferenceType) other).inner.equals(this.inner);
	}

	@Override
	public int hashCode() {
		return Objects.hash(inner);
	}

	@Override
	public String getClassName() {
		if (inner.equals(PrimitiveType.Int) || inner.equals(PrimitiveType.Bool)) {
			return ASTReference.REF_OF_INT;
		} else {
			return ASTReference.REF_OF_REF;
		}
	}

	@Override
	public String toString() {
		return "Ref<" + inner + ">";
	}
}
