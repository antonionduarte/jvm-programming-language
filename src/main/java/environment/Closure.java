package environment;

import ast.types.IValue;

import java.util.Map;

public class Closure<T> {
	private Map<String, T> parameters; /* Parameters of the function */
	private Map<String, T> definitions; /* Definitions within the function */
	private final Environment<T> declarationEnvironment; /* Environment at the time of function decl. */

	public Closure(Map<String, T> parameters, Environment<T> declarationEnvironment, Map<String, T> definitions) {
		this.parameters = parameters;
		this.declarationEnvironment = declarationEnvironment;
	}

	public Map<String, T> getParameters() {
		return parameters;
	}

}
