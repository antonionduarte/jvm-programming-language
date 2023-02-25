package ast.functions;

import ast.ASTNode;
import ast.typing.types.FunctionType;
import ast.typing.types.IType;
import ast.typing.types.TypeMismatchException;
import ast.typing.values.ClosureValue;
import ast.typing.values.IValue;
import compilation.CodeBlock;
import compilation.FrameCompiler;
import environment.ClosureManager;
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
		var closureType = closure.compile(frame, codeBlock);
		FrameCompiler.beginFunctionCall(codeBlock, ClosureManager.getInstance().getClosureInterface((FunctionType) closureType));
		for (var argument : arguments) {
			argument.compile(frame, codeBlock);
		}
		FrameCompiler.emitFunctionCall(codeBlock, ClosureManager.getInstance().getClosureInterface((FunctionType) closureType));
		return ((FunctionType) closureType).getReturnType();
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {

		IType type = closure.typeCheck(environment);
		if(!(type instanceof FunctionType functionType)){
			throw new TypeMismatchException("Function", type);
		}
		for (var i = 0; i < arguments.size(); i++) {
			var argumentType = arguments.get(i).typeCheck(environment);
			var parameterType = functionType.getParameters().get(i);
			if(!argumentType.equals(parameterType)){
				throw new TypeMismatchException(parameterType, argumentType);
			}
		}
		return functionType.getReturnType();
	}
}
