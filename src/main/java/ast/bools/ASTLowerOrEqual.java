package ast.bools;

import ast.ASTNode;
import ast.typing.types.Type;
import ast.typing.types.ValueType;
import ast.typing.values.BoolValue;
import ast.typing.values.IValue;
import ast.typing.values.IntValue;
import compilation.CodeBlock;
import compilation.CompilerUtils;
import environment.Environment;
import environment.Frame;

public class ASTLowerOrEqual implements ASTNode {
	private final ASTNode lhs;
	private final ASTNode rhs;

	public ASTLowerOrEqual(ASTNode lhs, ASTNode rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		int v1 = IntValue.asInt(lhs.eval(environment));
		int v2 = IntValue.asInt(rhs.eval(environment));
		return new BoolValue(v1 <= v2);
	}

	@Override
	public ValueType compile(Frame frame, CodeBlock codeBlock) {
		CodeBlock.DelayedOp gotoIf = codeBlock.delayEmit();
		codeBlock.emit(CompilerUtils.PUSH_TRUE);
		String label = codeBlock.emitLabel();
		codeBlock.emit(CompilerUtils.PUSH_FALSE);
		gotoIf.set(CompilerUtils.gotoIfCompare(CompilerUtils.LE, label));
		return new ValueType(Type.Bool);
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return new ValueType(Type.Bool);
	}
}
