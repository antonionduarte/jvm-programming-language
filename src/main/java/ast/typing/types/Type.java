package ast.typing.types;

public enum Type {
	Int("I"),
	Bool("B"), /* TODO: Check if this is right */
	String("S"), /* TODO: Check if this is right */
	Fun("F"),
	Ref("R"); /* TODO: Check if this is right */

	private final String jvmId;

	Type(String jvmId) {
		this.jvmId = jvmId;
	}

	public String getJvmId() {
		return jvmId;
	}
}
