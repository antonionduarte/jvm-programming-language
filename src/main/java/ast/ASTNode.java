package ast;

import ast.typing.values.IValue;
import ast.typing.types.ValueType;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;

public interface ASTNode {

	IValue eval(Environment<IValue> environment);

	ValueType compile(Frame frame, CodeBlock codeBlock);

	ValueType typeCheck(Environment<ValueType> environment);
}