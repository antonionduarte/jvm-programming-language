package ast.flow;

import ast.ASTNode;
import ast.typing.types.IType;
import ast.typing.types.PrimitiveType;
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
	public IType compile(Frame frame, CodeBlock codeBlock) {
		String loop = codeBlock.emitLabel();
		condition.compile(frame, codeBlock);
		CodeBlock.DelayedOp loopIf = codeBlock.delayEmit();
		IType type = body.compile(frame, codeBlock);
		//discard iteration result to clear the stack
		if (type!= PrimitiveType.Void) {
			codeBlock.emit(CompilerUtils.DISCARD);
		}
		codeBlock.emit(CompilerUtils.gotoAlways(loop));
		String breakLoop = codeBlock.emitLabel();
		loopIf.set(CompilerUtils.gotoIfFalse(breakLoop));
		return PrimitiveType.Void;
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {
		condition.typeCheck(environment).expect(PrimitiveType.Bool);
		body.typeCheck(environment);
		return PrimitiveType.Void;
	}
}
