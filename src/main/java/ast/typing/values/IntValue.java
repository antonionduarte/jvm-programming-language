package ast.typing.values;

import ast.typing.types.IType;
import ast.typing.types.PrimitiveType;
import ast.typing.types.TypeMismatchException;

public class IntValue implements IValue {
	private final int value;

	public IntValue(int value) {
		this.value = value;
	}

	public static int asInt(IValue value) {
		if (value instanceof IntValue) {
			return ((IntValue) value).getValue();
		} else {
			throw new TypeMismatchException(PrimitiveType.Int, value.getType());
		}
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(getValue());
	}

	@Override
	public IType getType() {
		return PrimitiveType.Int;
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof IntValue && ((IntValue) other).getValue() == this.getValue();
	}
}
