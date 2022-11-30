package ast.types;

public enum Type {
	Int("I"),
	Bool("B"), /* TODO: Check if this is right */
	String("S"), /* TODO: check if this is right */
	Fun("F");

	private final String jvmId;

	Type(String jvmId) {
		this.jvmId = jvmId;
	}

	public String getJvmId() {
		return jvmId;
	}
}
