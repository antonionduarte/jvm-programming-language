package ast.typing.types;

public enum PrimitiveType implements IType {
	Int("I", "int"),
	Bool("I", "bool"),
	String("Ljava/lang/String;", "string"), /* TODO: Check if this is right */
	Void("V", "void");

	private final String jvmId;
	private final String jvmType;

	PrimitiveType(String jvmId, String jvmType) {
		this.jvmId = jvmId;
		this.jvmType = jvmType;
	}

	@Override
	public String getJvmId() {
		return jvmId;
	}

	public java.lang.String getJvmType() {
		return jvmType;
	}

	@Override
	public void expect(IType other) {
		if (!this.equals(other)) {
			throw new TypeMismatchException(other, this);
		}
	}
}

