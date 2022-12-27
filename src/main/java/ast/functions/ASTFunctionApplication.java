package ast.functions;

import ast.ASTNode;
import ast.typing.types.IType;
import ast.typing.values.ClosureValue;
import ast.typing.values.IValue;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;

import java.util.ArrayList;

public class ASTFunctionApplication implements ASTNode {

	private final ASTNode closure;
	private final ArrayList<ASTNode> arguments;

	public ASTFunctionApplication(ASTNode closure, ArrayList<ASTNode> arguments) {
		this.closure = closure;
		this.arguments = arguments;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		var closure = ClosureValue.asClosure(this.closure.eval(environment));
		var body = closure.getBody();
		var applicationEnvironment = new Environment<>(closure.getEnvironment());
		var parameters = closure.getParameters();

		for (var i = 0; i < parameters.size(); i++) {
			var parameterId = parameters.get(i).name();
			var argumentValue = this.arguments.get(i).eval(environment);
			applicationEnvironment.associate(parameterId, argumentValue);
		}

		return body.eval(applicationEnvironment);
	}

	@Override
	public IType compile(Frame frame, CodeBlock codeBlock) {
		return null;
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {
		return null;
	}
}
