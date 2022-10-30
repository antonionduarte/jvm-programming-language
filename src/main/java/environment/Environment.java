package environment;

import ast.ASTNode;

import java.util.HashMap;
import java.util.Map;

public class Environment {

	private Map<String, ASTNode> associations;
	private Environment upperEnvironment;

	public Environment() {
		this.upperEnvironment = null;
		this.associations = new HashMap<>();
	}

	public Environment(Environment upperEnvironment) {
		this.upperEnvironment = upperEnvironment;
		this.associations = new HashMap<>();
	}

	public Environment beginScope() {
		return new Environment(this);
	}

	public Environment endScope() {
		return upperEnvironment;
	}

	public void associate(String id, ASTNode val) {
		this.associations.put(id, val);
	}

	public ASTNode find(String id) {
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