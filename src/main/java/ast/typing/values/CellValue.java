package ast.typing.values;

import ast.typing.types.IType;
import ast.typing.types.ReferenceType;
import ast.typing.types.TypeMismatchException;

public class CellValue implements IValue {
	private IValue value;

	public CellValue(IValue value) {
		this.value = value;
	}

	public static IValue asCell(IValue value) {
		if (value instanceof CellValue) {
			return ((CellValue) value).getValue();
		} else {
			throw new TypeMismatchException("Reference", value.getType());
		}
	}

	public IValue getValue() {
		return value;
	}

	public void setValue(IValue value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(getValue());
	}

	// TODO: The Type of a CellValue must be a combination of Type.Ref + the type of the value
	@Override
	public IType getType() {
		return new ReferenceType(value.getType());
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof CellValue && ((CellValue) other).getValue().equals(this.getValue());
	}
}