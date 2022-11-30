package ast.types;

import ast.ASTNode;
import environment.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClosureValue implements IValue {

	private final List<Parameter> parameters; /* Parameters of the function */
	private final Map<String, ASTNode> definitions; /* Definitions within the function */
	private final Environment<IValue> environment; /* Environment at the time of function decl. */
	private final ValueType returnType;
	private final ASTNode body;

	/* TODO: I think this still needs ASTNode as Body */

	public ClosureValue(Environment<IValue> environment, List<Parameter> parameters, Map<String, ASTNode> definitions, ValueType returnType, ASTNode body) {
		this.parameters = parameters;
		this.definitions = definitions;
		this.environment = environment;
		this.returnType = returnType;
		this.body = body;
	}

	public static ClosureValue asClosure(IValue value) {
		if (value instanceof ClosureValue) {
			return (ClosureValue) value;
		} else {
			throw new RuntimeException("Value is not a closure");
		}
	}

	ClosureValue getValue() {
		return this;
	}

	public Environment<IValue> getEnvironment() {
		return environment;
	}

	public Map<String, ASTNode> getDefinitions() {
		return definitions;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public ASTNode getBody() {
		return body;
	}

	@Override
	public ValueType getType() {
		var parameterTypes = new ArrayList<ValueType>();
		for (var parameter : parameters) {
			parameterTypes.add(parameter.type());
		}
		return new ValueType(Type.Fun, parameterTypes, returnType);
	}
}
