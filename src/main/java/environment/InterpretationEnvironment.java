package environment;

import ast.types.IValue;
import ast.types.ValueType;

import java.util.HashMap;
import java.util.Map;

public class InterpretationEnvironment implements Environment {

	private final Map<String, IValue> associations;
	private final InterpretationEnvironment upperEnvironment;

	public InterpretationEnvironment() {
		this.upperEnvironment = null;
		this.associations = new HashMap<>();
	}

	public InterpretationEnvironment(InterpretationEnvironment upperEnvironment) {
		this.upperEnvironment = upperEnvironment;
		this.associations = new HashMap<>();
	}

	public InterpretationEnvironment beginScope() {
		return new InterpretationEnvironment(this);
	}

	public InterpretationEnvironment endScope() {
		return upperEnvironment;
	}

	public void associate(String id, IValue val) {
		this.associations.put(id, val);
	}

	public IValue find(String id) {
		var value = associations.get(id);
		if (value != null) {
			return value;
		} else {
			if (upperEnvironment != null) {
				return upperEnvironment.find(id);
			} else {
				throw new InvalidIdentifierException(id);
			}
		}
	}

	@Override
	public ValueType getAssociatedType(String id) {
		return find(id).getType();
	}
}
