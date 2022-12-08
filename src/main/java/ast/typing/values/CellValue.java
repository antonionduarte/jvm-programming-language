package ast.typing.values;

import ast.typing.types.Type;
import ast.typing.types.TypeMismatchException;
import ast.typing.types.ValueType;

public class CellValue implements IValue {
	private final IValue value;

	public CellValue(IValue value) {
		this.value = value;
	}

	public static IValue asCell(IValue value) {
		if (value instanceof CellValue) {
			return ((CellValue) value).getValue();
		} else {
			throw new TypeMismatchException(new ValueType(Type.Ref), value.getType());
		}
	}

	public IValue getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(getValue());
	}

	// TODO: The Type of a CellValue must be a combination of Type.Ref + the type of the value
	@Override
	public ValueType getType() {
		return new ValueType(Type.Ref);
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof CellValue && ((CellValue) other).getValue().equals(this.getValue());
	}
}