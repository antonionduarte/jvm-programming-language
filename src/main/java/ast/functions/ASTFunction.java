package ast.functions;

import ast.ASTNode;
import ast.typing.types.FunctionType;
import ast.typing.types.IType;
import ast.typing.utils.Parameter;
import ast.typing.values.ClosureValue;
import ast.typing.values.IValue;
import compilation.CodeBlock;
import environment.*;
import utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class ASTFunction implements ASTNode {

	private final ArrayList<Parameter> typedParameters;
	private final ASTNode body;
	private final IType returnType;

	public ASTFunction(ASTNode body, ArrayList<Pair<String, IType>> parameters, IType returnType) {
		this.typedParameters = convertParameters(parameters);
		this.body = body;
		this.returnType = returnType;
	}

	private ArrayList<Parameter> convertParameters(ArrayList<Pair<String, IType>> parameters) {
		ArrayList<Parameter> convertedParameters = new ArrayList<>();
		for (Pair<String, IType> pair : parameters) {
			String parameter = pair.a();
			IType type = pair.b();
			convertedParameters.add(new Parameter(parameter, type));
		}
		return convertedParameters;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		return new ClosureValue(environment, typedParameters, returnType, body);
	}

	@Override
	public IType compile(Frame frame, CodeBlock codeBlock) {
		// TODO: closure compilation here!

		List<IType> typeParameters = new ArrayList<>();
		for (var parameter : this.typedParameters) {
			typeParameters.add(parameter.type());
		}
		FunctionType functionType = new FunctionType(typeParameters, returnType);

		var closureFrame = frame.beginScope();
		for (Parameter parameter : typedParameters) {
			FrameVariable variable = new FrameVariable(closureFrame, closureFrame.getVars().size());
			closureFrame.associate(parameter.name(), variable);
		}
		frame.endScope();

		// create the interface if needed
		// TODO: Where do I get the FunctionType from? right now it's set to null
		var interfaceIdentifier = ClosureManager.getInstance().getClosureInterface(functionType);
		if (interfaceIdentifier == null) ClosureManager.getInstance().addClosureInterface(functionType);

		return null;
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {
		return null;
	}

}
