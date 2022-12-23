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

public class ASTIf implements ASTNode {
	private final ASTNode condition;
	private final ASTNode bodyIf;
	private final ASTNode bodyElse;

	public ASTIf(ASTNode condition, ASTNode bodyIf, ASTNode bodyElse) {
		this.condition = condition;
		this.bodyIf = bodyIf;
		this.bodyElse = bodyElse;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		boolean condVal = BoolValue.asBoolean(condition.eval(environment));
		if (condVal) {
			IValue val = bodyIf.eval(environment);
			if (bodyElse != null) {
				return val;
			} else {
				return VoidValue.getInstance();
			}
		} else if (bodyElse != null) {
			return bodyElse.eval(environment);
		} else {
			return VoidValue.getInstance();
		}
	}

	@Override
	public IType compile(Frame frame, CodeBlock codeBlock) {
		condition.compile(frame, codeBlock);
		CodeBlock.DelayedOp gotoElse = codeBlock.delayEmit();
		IType ifType = bodyIf.compile(frame, codeBlock);
		CodeBlock.DelayedOp popIf = codeBlock.delayEmit();
		popIf.set(CompilerUtils.comment(CompilerUtils.DISCARD));
		CodeBlock.DelayedOp skipElse = codeBlock.delayEmit();
		skipElse.set(CompilerUtils.comment(" ;goto after else"));
		String label = codeBlock.emitLabel();
		IType elseType = null;
		CodeBlock.DelayedOp popElse = null;
		if (bodyElse != null) {
			elseType = bodyElse.compile(frame, codeBlock);
			popElse = codeBlock.delayEmit();
			popElse.set(CompilerUtils.comment(CompilerUtils.DISCARD));
			skipElse.set(CompilerUtils.gotoAlways(codeBlock.emitLabel()));
		}
		gotoElse.set(CompilerUtils.gotoIfFalse(label));

		//if this if does not return a value, it is required to discard the values
		//from the stack
		boolean isVoid = elseType == null || !elseType.equals(ifType);
		if (isVoid && !ifType.equals(PrimitiveType.Void)) {
			popIf.set(CompilerUtils.DISCARD);
		}
		if (isVoid && elseType != null && !elseType.equals(PrimitiveType.Void)) {
			popElse.set(CompilerUtils.DISCARD);
		}

		return isVoid ? PrimitiveType.Void : ifType;
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {
		condition.typeCheck(environment).expect(PrimitiveType.Bool);
		if (bodyElse == null) {
			return PrimitiveType.Void;
		} else {
			IType ifType = bodyIf.typeCheck(environment);
			IType elseType = bodyElse.typeCheck(environment);
			elseType.expect(ifType);
			return elseType.equals(ifType) ? ifType : PrimitiveType.Void;
		}
	}
}
