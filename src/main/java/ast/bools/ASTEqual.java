package ast.bools;

import ast.ASTNode;
import ast.typing.types.Type;
import ast.typing.types.ValueType;
import ast.typing.values.BoolValue;
import ast.typing.values.IValue;
import compilation.CodeBlock;
import compilation.CompilerUtils;
import environment.Environment;
import environment.Frame;

public class ASTEqual implements ASTNode {
	private final ASTNode lhs;
	private final ASTNode rhs;

	public ASTEqual(ASTNode lhs, ASTNode rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		IValue v1 = lhs.eval(environment);
		IValue v2 = rhs.eval(environment);
		return new BoolValue(v1.equals(v2));
	}

	@Override
	public ValueType compile(Frame frame, CodeBlock codeBlock) {
		lhs.compile(frame, codeBlock).expect(rhs.compile(frame, codeBlock));
		CodeBlock.DelayedOp gotoIf = codeBlock.delayEmit();
		codeBlock.emit(CompilerUtils.PUSH_FALSE);
		CodeBlock.DelayedOp skipIf = codeBlock.delayEmit();
		String label = codeBlock.emitLabel();
		codeBlock.emit(CompilerUtils.PUSH_TRUE);
		gotoIf.set(CompilerUtils.gotoIfCompare(CompilerUtils.EQ, label));
		skipIf.set(CompilerUtils.gotoAlways(codeBlock.emitLabel()));
		return new ValueType(Type.Bool);
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return new ValueType(Type.Bool);
	}
}


