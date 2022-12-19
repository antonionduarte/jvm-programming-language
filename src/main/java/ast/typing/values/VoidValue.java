package ast.typing.values;

import ast.typing.types.Type;
import ast.typing.types.ValueType;

public class VoidValue implements IValue {
	private static final VoidValue instance = new VoidValue();

	private VoidValue() {

	}

	public static VoidValue getInstance() {
		return instance;
	}

	@Override
	public ValueType getType() {
		return new ValueType(Type.Void);
	}
}
