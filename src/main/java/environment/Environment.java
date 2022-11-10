package environment;

import java.util.HashMap;
import java.util.Map;

public class Environment<T> {

	private final Map<String, T> associations;
	private final Environment<T> upperEnvironment;

	public Environment() {
		this.upperEnvironment = null;
		this.associations = new HashMap<>();
	}

	public Environment(Environment<T> upperEnvironment) {
		this.upperEnvironment = upperEnvironment;
		this.associations = new HashMap<>();
	}

	public Environment<T> beginScope() {
		return new Environment<T>(this);
	}

	public Environment<T> endScope() {
		return upperEnvironment;
	}

	public void associate(String id, T val) {
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
				throw new RuntimeException("Error: Association not found.");
			}
		}
	}
}
