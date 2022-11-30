package ast.types;

public record Parameter(String name, ValueType type) {
	public Parameter {
		if (name == null || type == null) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
	}
}
