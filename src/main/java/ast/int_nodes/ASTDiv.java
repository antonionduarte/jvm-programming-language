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

public class ASTDiv implements ASTNode {

	ASTNode lhs, rhs;

	public IValue eval(InterpretationEnvironment environment) {
		int v1 = IntValue.asInt(lhs.eval(environment));
		int v2 = IntValue.asInt(rhs.eval(environment));
		return new IntValue(v1 / v2);
	}

	@Override
	public void compile(FrameManager frameManager, CodeBlock codeBlock) {
		lhs.compile(frameManager, codeBlock);
		rhs.compile(frameManager, codeBlock);
		codeBlock.emit(JVMOps.DIV);
	}

	@Override
	public ValueType getReturnType(Environment environment) {
		return ValueType.Int;
	}

	public ASTDiv(ASTNode l, ASTNode r) {
		lhs = l;
		rhs = r;
	}
}

