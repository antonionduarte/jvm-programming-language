package ast.types;

public class BoolValue implements IValue {
	public static final BoolValue TRUE = new BoolValue(true);
	public static final BoolValue FALSE = new BoolValue(false);
	private final boolean value;

	public BoolValue(boolean value) {
		this.value = value;
	}

	public static boolean asBoolean(IValue value) {
		if (value instanceof BoolValue) {
			return ((BoolValue) value).getValue();
		} else {
			throw new TypeMismatchException(new ValueType(Type.BOOL), value.getType());
		}
	}

	public boolean getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(getValue());
	}

	@Override
	public ValueType getType() {
		return new ValueType(Type.BOOL);
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof BoolValue && ((BoolValue) other).getValue() == this.getValue();
	}
}