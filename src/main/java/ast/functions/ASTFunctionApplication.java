package ast.functions;

import ast.ASTNode;
import ast.typing.values.ClosureValue;
import ast.typing.values.IValue;
import ast.typing.types.ValueType;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;

import java.util.ArrayList;

public class ASTFunctionApplication implements ASTNode {

	private final String identifier;
	private final ArrayList<ASTNode> arguments;

	public ASTFunctionApplication(String identifier, ArrayList<ASTNode> arguments) {
		this.identifier = identifier;
		this.arguments = arguments;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		var closure = ClosureValue.asClosure(environment.find(identifier));
		var body = closure.getBody();
		var applicationEnvironment = new Environment<>(closure.getEnvironment());
		var parameters = closure.getParameters();

		for (var i = 0; i < parameters.size(); i++) {
			var parameterId = parameters.get(i).name();
			var argumentValue = this.arguments.get(i).eval(environment);
			applicationEnvironment.associate(parameterId, argumentValue);
		}

		for (var definition : closure.getDefinitions().keySet()) {
			applicationEnvironment.associate(
					definition, closure.getDefinitions().get(definition).eval(applicationEnvironment)
			);
		}

		return body.eval(applicationEnvironment);
	}

	@Override
	public ValueType compile(Frame frame, CodeBlock codeBlock) {
		return null;
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return null;
	}
}
