package ast;

import ast.types.*;
import compilation.CodeBlock;
import environment.Closure;
import environment.Environment;
import environment.Frame;
import utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class ASTFunction implements ASTNode {

	private final ArrayList<Parameter> typedParameters;
	private final ASTNode body;
	private final HashMap<String, ASTNode> definitions;
	private final ValueType  returnType;

	public ASTFunction(ASTNode body, ArrayList<Pair<String, String>> parameters, HashMap<String, ASTNode> definitions, ValueType returnType) {
		this.typedParameters = convertParameters(parameters);
		this.body = body;
		this.definitions = definitions;
		this.returnType = returnType;
	}

	private ArrayList<Parameter> convertParameters(ArrayList<Pair<String, String>> parameters) {
		ArrayList<Parameter> convertedParameters = new ArrayList<>();
		for (Pair<String, String> pair : parameters) {
			String parameter = pair.a();
			String type = pair.b();
			// TODO: Well this won't work for parameters where the type is a function type
			convertedParameters.add(new Parameter(parameter, new ValueType(Type.valueOf(type))));
		}
		return convertedParameters;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		/* TODO: Typecheck and other funkywunky things probably */
		return new ClosureValue(environment, typedParameters, definitions, returnType, body);
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
