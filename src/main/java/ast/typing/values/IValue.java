package ast.typing.values;

import ast.typing.types.ValueType;

public interface IValue {
	String toString();

	ValueType getType();
}
