package ast.bools;

import ast.ASTNode;
import ast.typing.types.IType;
import ast.typing.types.PrimitiveType;
import ast.typing.types.StringType;
import ast.typing.values.BoolValue;
import ast.typing.values.IValue;
import compilation.CodeBlock;
import compilation.CompilerUtils;
import environment.Environment;
import environment.Frame;

public class ASTNotEqual implements ASTNode {
	private final ASTNode lhs;
	private final ASTNode rhs;

	public ASTNotEqual(ASTNode lhs, ASTNode rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		IValue v1 = lhs.eval(environment);
		IValue v2 = rhs.eval(environment);
		return new BoolValue(!v1.equals(v2));
	}

	@Override
	public IType compile(Frame frame, CodeBlock codeBlock) {
		IType leftType = lhs.compile(frame, codeBlock);
		IType rightType = rhs.compile(frame, codeBlock);
		leftType.expect(rightType);
		if(leftType.equals(StringType.Instance)) {
			codeBlock.emit("invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z");
		} else  if (leftType.equals(PrimitiveType.Int) || leftType.equals(PrimitiveType.Bool)){
			CodeBlock.DelayedOp gotoIf = codeBlock.delayEmit();
			codeBlock.emit(CompilerUtils.PUSH_FALSE);
			CodeBlock.DelayedOp skipIf = codeBlock.delayEmit();
			String label = codeBlock.emitLabel();
			codeBlock.emit(CompilerUtils.PUSH_TRUE);
			gotoIf.set(CompilerUtils.gotoIfCompare(CompilerUtils.NOT_EQ, label));
			skipIf.set(CompilerUtils.gotoAlways(codeBlock.emitLabel()));
		}
		else {
			throw new RuntimeException("Invalid type " + lhs + " for equals");
		}
		return PrimitiveType.Bool;
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {
		IType type = lhs.typeCheck(environment);
		if(!(type.equals(PrimitiveType.Int) || type.equals(PrimitiveType.Bool) || type.equals(StringType.Instance))) {
			throw new RuntimeException("Invalid type " + type + " for equals");
		}
		rhs.typeCheck(environment).expect(type);
		return PrimitiveType.Bool;
	}
}


