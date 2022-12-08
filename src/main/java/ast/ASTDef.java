package ast;

import ast.typing.values.IValue;
import ast.typing.types.ValueType;
import compilation.CodeBlock;
import compilation.FrameCompiler;
import environment.Environment;
import environment.Frame;
import environment.FrameVariable;

import java.util.HashMap;
import java.util.Map;

public class ASTDef implements ASTNode {

	private final Map<String, ASTNode> definitions;
	private final ASTNode body;

	public ASTDef(ASTNode body, HashMap<String, ASTNode> definitions) {
		this.definitions = definitions;
		this.body = body;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		var inner = environment.beginScope();
		for (var definition : definitions.entrySet()) {
			inner.associate(definition.getKey(), definition.getValue().eval(inner));
		}
		var value = body.eval(inner);
		environment.endScope();

		return value;
	}

	@Override
	public ValueType compile(Frame frame, CodeBlock codeBlock) {
		Frame inner = frame.beginScope();
		FrameCompiler.emitBeginScope(codeBlock, inner);
		for (var definition : definitions.entrySet()) {
			int varNumber = inner.getVars().size();
			FrameVariable variable = new FrameVariable(inner, varNumber);
			FrameCompiler.emitAssign(codeBlock, variable, definition.getValue());
			inner.associate(definition.getKey(), variable);
		}
		ValueType returnType = body.compile(inner, codeBlock);
		FrameCompiler.emitEndScope(codeBlock, inner);
		return returnType;
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return body.typeCheck(environment);
	}
}
