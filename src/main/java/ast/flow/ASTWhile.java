package ast.flow;

import ast.ASTNode;
import ast.typing.types.Type;
import ast.typing.types.ValueType;
import ast.typing.values.BoolValue;
import ast.typing.values.IValue;
import ast.typing.values.VoidValue;
import compilation.CodeBlock;
import compilation.CompilerUtils;
import environment.Environment;
import environment.Frame;

public class ASTWhile implements ASTNode {
	private final ASTNode condition;
	private final ASTNode body;

	public ASTWhile(ASTNode condition, ASTNode body) {
		this.condition = condition;
		this.body = body;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		boolean condVal = BoolValue.asBoolean(condition.eval(environment));
		while (condVal) {
			body.eval(environment);
			condVal = BoolValue.asBoolean(condition.eval(environment));
		}
		return VoidValue.getInstance();
	}

	@Override
	public ValueType compile(Frame frame, CodeBlock codeBlock) {
		String loop = codeBlock.emitLabel();
		condition.compile(frame, codeBlock);
		CodeBlock.DelayedOp loopIf = codeBlock.delayEmit();
		ValueType type = body.compile(frame, codeBlock);
		//discard iteration result to clear the stack
		if (type.getType() != Type.Void) {
			codeBlock.emit(CompilerUtils.DISCARD);
		}
		codeBlock.emit(CompilerUtils.gotoAlways(loop));
		String breakLoop = codeBlock.emitLabel();
		loopIf.set(CompilerUtils.gotoIfFalse(breakLoop));
		return new ValueType(Type.Void);
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		condition.typeCheck(environment).expect(new ValueType(Type.Bool));
		body.typeCheck(environment);
		return new ValueType(Type.Void);
	}
}
