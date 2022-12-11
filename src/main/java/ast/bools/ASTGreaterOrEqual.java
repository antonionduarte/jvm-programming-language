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

public class ASTGreaterOrEqual implements ASTNode {
	private final ASTNode lhs;
	private final ASTNode rhs;

	public ASTGreaterOrEqual(ASTNode lhs, ASTNode rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		int v1 = IntValue.asInt(lhs.eval(environment));
		int v2 = IntValue.asInt(rhs.eval(environment));
		return new BoolValue(v1 >= v2);
	}

	@Override
	public ValueType compile(Frame frame, CodeBlock codeBlock) {
		lhs.compile(frame, codeBlock).expect(new ValueType(Type.Int));
		rhs.compile(frame, codeBlock).expect(new ValueType(Type.Int));
		CodeBlock.DelayedOp gotoIf = codeBlock.delayEmit();
		codeBlock.emit(CompilerUtils.PUSH_FALSE);
		CodeBlock.DelayedOp skipIf = codeBlock.delayEmit();
		String label = codeBlock.emitLabel();
		codeBlock.emit(CompilerUtils.PUSH_TRUE);
		gotoIf.set(CompilerUtils.gotoIfCompare(CompilerUtils.GE, label));
		skipIf.set(CompilerUtils.gotoAlways(codeBlock.emitLabel()));
		return new ValueType(Type.Bool);
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return new ValueType(Type.Bool);
	}
}
