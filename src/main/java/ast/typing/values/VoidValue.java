package ast.typing.values;

import ast.typing.types.IType;
import ast.typing.types.PrimitiveType;

public class VoidValue implements IValue {
	private static final VoidValue instance = new VoidValue();

	private VoidValue() {

	}

	public static VoidValue getInstance() {
		return instance;
	}

	@Override
	public IType getType() {
		return PrimitiveType.Void;
	}
}
