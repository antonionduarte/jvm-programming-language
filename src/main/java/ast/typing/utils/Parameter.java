package ast.typing.utils;

import ast.typing.types.IType;

public record Parameter(String name, IType type) {
	public Parameter {
		if (name == null || type == null) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
	}
}
