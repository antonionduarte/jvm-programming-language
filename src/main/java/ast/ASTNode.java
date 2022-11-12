package ast;

import codeblock.CodeBlock;
import environment.Environment;
import environment.FrameManager;

public interface ASTNode {

	int eval(Environment<Integer> environment);

	void compile(FrameManager frameManager, CodeBlock codeBlock);

}