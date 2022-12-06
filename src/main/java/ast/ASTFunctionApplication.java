package ast;

import ast.types.ClosureValue;
import ast.types.IValue;
import ast.types.Type;
import ast.types.ValueType;
import com.sun.jdi.Value;
import compilation.CodeBlock;
import environment.Closure;
import environment.Environment;
import environment.Frame;

import java.util.ArrayList;

public class ASTFunctionApplication implements ASTNode {

	private final String identifier;
		private final ArrayList<ValueType> arguments;

	public ASTFunctionApplication(String identifier, ArrayList<ASTNode> arguments) {
		this.identifier = identifier;
		this.arguments = arguments;
	}

	private ArrayList<ValueType> convertToArguments(ArrayList<String> arguments) {
		ArrayList<ValueType> convertedArguments = new ArrayList<>();
		for (String argument : arguments) {
			convertedArguments.add(new ValueType(Type.valueOf(argument)));
		}
		return convertedArguments;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		var closure = ClosureValue.asClosure(environment.find(identifier));
		var body = closure.getBody();
		var bodyEnvironment = new Environment<IValue>();

		/* for (var definition : closure.getDefinitions().keySet()) {
			bodyEnvironment.associate(definition, closure.getDefinitions().get(definition));
		} TODO: Ignore this for now, it fucks stuff up */

		/* for (var argument : arguments) {
			bodyEnvironment.associate(argument.toString(), argument);
		} */

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
