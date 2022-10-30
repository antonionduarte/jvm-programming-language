package ast;

import codeblock.CodeBlock;
import environment.Environment;

public interface ASTNode {

	int eval(Environment environment);

	void compile(CodeBlock codeBlock);

}