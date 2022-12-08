package ast.typing.values;

import ast.typing.types.Type;
import ast.typing.types.TypeMismatchException;
import ast.typing.types.ValueType;

public class IntValue implements IValue {
	private final int value;

	public IntValue(int value) {
		this.value = value;
	}

	public static int asInt(IValue value) {
		if (value instanceof IntValue) {
			return ((IntValue) value).getValue();
		} else {
			throw new TypeMismatchException(new ValueType(Type.Int), value.getType());
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
	public ValueType getType() {
		return new ValueType(Type.Int);
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof IntValue && ((IntValue) other).getValue() == this.getValue();
	}
}
