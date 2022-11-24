package ast;

import ast.types.IValue;
import ast.types.ValueType;
import codeblock.CodeBlock;
import environment.Environment;
import environment.InterpretationEnvironment;
import environment.FrameManager;

public interface ASTNode {

	IValue eval(InterpretationEnvironment environment);

	void compile(FrameManager frameManager, CodeBlock codeBlock);

	ValueType getReturnType(Environment environment);

}