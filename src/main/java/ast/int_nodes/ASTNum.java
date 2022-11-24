package ast.int_nodes;

import ast.ASTNode;
import ast.JVMOps;
import ast.types.IValue;
import ast.types.IntValue;
import ast.types.ValueType;
import codeblock.CodeBlock;
import environment.Environment;
import environment.InterpretationEnvironment;
import environment.FrameManager;

public class ASTNum implements ASTNode {

	int val;

	public IValue eval(InterpretationEnvironment environment) {
		return new IntValue(val);
	}

	@Override
	public void compile(FrameManager frameManager, CodeBlock codeBlock) {
		codeBlock.emit(JVMOps.PUSH + " " + val);
	}

	@Override
	public ValueType getReturnType(Environment environment) {
		return ValueType.Int;
	}

	public ASTNum(int n) {
		val = n;
	}
}
