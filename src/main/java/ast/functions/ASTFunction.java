package ast.functions;

import ast.ASTNode;
import ast.typing.types.IType;
import ast.typing.types.PrimitiveType;
import ast.typing.utils.Parameter;
import ast.typing.values.ClosureValue;
import ast.typing.values.IValue;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;
import utils.Pair;

import java.util.ArrayList;

public class ASTFunction implements ASTNode {

	private final ArrayList<Parameter> typedParameters;
	private final ASTNode body;
	private final IType returnType;

	public ASTFunction(ASTNode body, ArrayList<Pair<String, String>> parameters, IType returnType) {
		this.typedParameters = convertParameters(parameters);
		this.body = body;
		this.returnType = returnType;
	}

	// TODO: Well this won't work for parameters where the type is a function type
	private ArrayList<Parameter> convertParameters(ArrayList<Pair<String, String>> parameters) {
		ArrayList<Parameter> convertedParameters = new ArrayList<>();
		for (Pair<String, String> pair : parameters) {
			String parameter = pair.a();
			String type = pair.b();
			convertedParameters.add(new Parameter(parameter, PrimitiveType.valueOf(type)));
		}
		return convertedParameters;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		return new ClosureValue(environment, typedParameters, returnType, body);
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
