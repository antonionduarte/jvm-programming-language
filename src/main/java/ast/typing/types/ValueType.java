package ast.typing.types;

import java.util.List;

public class ValueType {

	private final List<ValueType> parameterTypes;
	private final ValueType returnType;
	private final Type type;

	/**
	 * Creates a new ValueType, meant to be used for primitive types.
	 *
	 * @param type The type of the value.
	 */
	public ValueType(Type type) {
		this.type = type;
		this.parameterTypes = null;
		this.returnType = null;
	}

	/**
	 * Constructor meant to be used for Records and Function types.
	 *
	 * @param type           The type of the value. (FUNCTION or RECORD)
	 * @param parameterTypes The parameter types of the function.
	 * @param returnType     The return type of the function.
	 */
	public ValueType(Type type, List<ValueType> parameterTypes, ValueType returnType) {
		this.type = type;
		this.parameterTypes = parameterTypes;
		this.returnType = returnType;
	}

	public Type getType() {
		return type;
	}

	public String getJvmId() {
		return type.getJvmId();
	}

	public void expect(ValueType expected) {
		if (!expected.equals(this)) {
			throw new TypeMismatchException(expected, this);
		}
	}

	@Override
	public boolean equals(Object object) {
		if (object.getClass() != this.getClass()) {
			return false;
		}

		ValueType valueType = (ValueType) object;

		if (this.parameterTypes != null) {
			if (valueType.parameterTypes == null) {
				return false;
			}

			if (this.parameterTypes.size() != valueType.parameterTypes.size()) {
				return false;
			}

			for (int i = 0; i < this.parameterTypes.size(); i++) {
				if (!this.parameterTypes.get(i).equals(valueType.parameterTypes.get(i))) {
					return false;
				}
			}
		}

		if (this.returnType != null) {
			if (valueType.returnType == null) {
				return false;
			}

			if (!this.returnType.equals(valueType.returnType)) {
				return false;
			}
		}

		return valueType.type == this.type;
	}
}