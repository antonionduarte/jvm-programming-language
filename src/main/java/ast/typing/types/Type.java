package ast.typing.types;

public enum Type {
	Int("I"),
	Bool("I"), /* TODO: Check if this is right */
	String("S"), /* TODO: Check if this is right */
	Fun("F"), /* TODO: Check if this is right */
	Ref("LRef;"),
	Void("V");

	private final String jvmId;

	Type(String jvmId) {
		this.jvmId = jvmId;
	}

	public String getJvmId() {
		return jvmId;
	}
}
