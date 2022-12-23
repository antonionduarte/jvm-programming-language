package ast.typing.values;

import ast.typing.types.IType;

public interface IValue {
	String toString();

	IType getType();
}
