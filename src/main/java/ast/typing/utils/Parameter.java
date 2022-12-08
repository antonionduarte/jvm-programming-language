package ast.typing.utils;

import ast.typing.types.ValueType;

public record Parameter(String name, ValueType type) {
	public Parameter {
		if (name == null || type == null) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
	}
}
