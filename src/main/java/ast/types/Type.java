package ast.types;

public enum Type {
	INT("I"),
	BOOL("B"), /* TODO: Check if this is right */
	STRING("S"), /* TODO: check if this is right */
	FUN("F");

	private final String jvmId;

	Type(String jvmId) {
		this.jvmId = jvmId;
	}

	public String getJvmId() {
		return jvmId;
	}
}
