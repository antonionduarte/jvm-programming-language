package ast;

import ast.typing.types.IType;
import ast.typing.values.IValue;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;

public interface ASTNode {

	IValue eval(Environment<IValue> environment);

	IType compile(Frame frame, CodeBlock codeBlock);

	IType typeCheck(Environment<IType> environment);
}