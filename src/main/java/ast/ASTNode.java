package ast;

import ast.types.IValue;
import ast.types.ValueType;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;

public interface ASTNode {

	IValue eval(Environment<IValue> environment);

	ValueType compile(Frame frame, CodeBlock codeBlock);

	ValueType typeCheck(Environment<ValueType> environment);
}