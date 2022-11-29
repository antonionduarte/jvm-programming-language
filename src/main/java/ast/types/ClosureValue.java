package ast.types;

import ast.ASTNode;
import environment.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClosureValue<T> implements IValue {

	private final List<Parameter> parameters; /* Parameters of the function */
	private final Map<String, T> definitions; /* Definitions within the function */
	private final Environment<T> declarationEnvironment; /* Environment at the time of function decl. */
	private final ValueType returnType;
	private final ASTNode body;

	/* TODO: I think this still needs ASTNode as Body */

	public ClosureValue(Environment<T> declarationEnvironment, List<Parameter> parameters, Map<String, T> definitions, ValueType returnType, ASTNode body) {
		this.parameters = parameters;
		this.definitions = definitions;
		this.declarationEnvironment = declarationEnvironment;
		this.returnType = returnType;
		this.body = body;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public Environment<T> getDeclarationEnvironment() {
		return declarationEnvironment;
	}

	public Map<String, T> getDefinitions() {
		return definitions;
	}

	@Override
	public ValueType getType() {
		var parameterTypes = new ArrayList<ValueType>();
		for (var parameter : parameters) {
			parameterTypes.add(parameter.type());
		}
		return new ValueType(Type.FUN, parameterTypes, returnType);
	}

	public record Parameter(String name, ValueType type) {
	}
}
