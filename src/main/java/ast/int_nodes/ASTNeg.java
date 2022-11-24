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

public class ASTNeg implements ASTNode {

	ASTNode exp;

	public IValue eval(InterpretationEnvironment environment) {
		int value = IntValue.asInt(exp.eval(environment));
		return new IntValue(-value);
	}

	@Override
	public void compile(FrameManager frameManager, CodeBlock codeBlock) {
		codeBlock.emit(JVMOps.NEG);
	}

	@Override
	public ValueType getReturnType(Environment environment) {
		return ValueType.Int;
	}

	public ASTNeg(ASTNode exp) {
		this.exp = exp;
	}
}

