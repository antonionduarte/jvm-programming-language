package environment;

import ast.types.IValue;
import ast.types.ValueType;

import java.util.HashMap;
import java.util.Map;

public class Environment<T> {

	protected final Map<String, T> associations;
	protected final Environment<T> upperEnvironment;

	public Environment() {
		this.upperEnvironment = null;
		this.associations = new HashMap<>();
	}

	public Environment(Environment<T> upperEnvironment) {
		this.upperEnvironment = upperEnvironment;
		this.associations = new HashMap<>();
	}

	public Environment<T> beginScope() {
		return new Environment<>(this);
	}

	public Environment<T> endScope() {
		return upperEnvironment;
	}

	public void associate(String id, T val) {
		if(this.associations.containsKey(id)){
			throw new InvalidIdentifierException(id);
		}
		this.associations.put(id, val);
	}

	public T find(String id) {
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

}
