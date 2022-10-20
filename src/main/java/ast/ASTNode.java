package ast;

import codeblock.CodeBlock;

public interface ASTNode {

	int eval();

	void compile(CodeBlock codeBlock);

}