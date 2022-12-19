package ast;

import ast.typing.types.ValueType;
import ast.typing.values.IValue;
import compilation.CodeBlock;
import compilation.CompilerUtils;
import environment.Environment;
import environment.Frame;

public class ASTPrint implements ASTNode {
	private final ASTNode node;

	public ASTPrint(ASTNode node) {
		this.node = node;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		IValue value = node.eval(environment);
		System.out.println(value);
		return value;
	}

	@Override
	public ValueType compile(Frame frame, CodeBlock codeBlock) {
		ValueType type = node.compile(frame, codeBlock);
		//duplicate value to leave it on stack after printing
		codeBlock.emit(CompilerUtils.DUPLICATE);
		codeBlock.emit("getstatic java/lang/System/out Ljava/io/PrintStream;");
		codeBlock.emit(CompilerUtils.SWAP);
		switch (type.getType()) {
			case Int -> codeBlock.emit("invokestatic java/lang/String/valueOf(I)Ljava/lang/String;");
			case Bool -> {
				CodeBlock.DelayedOp gotoElse = codeBlock.delayEmit();
				codeBlock.emit(CompilerUtils.pushString("true"));
				CodeBlock.DelayedOp skipElse = codeBlock.delayEmit();
				String label = codeBlock.emitLabel();
				codeBlock.emit(CompilerUtils.pushString("false"));
				skipElse.set(CompilerUtils.gotoAlways(codeBlock.emitLabel()));
				gotoElse.set(CompilerUtils.gotoIfFalse(label));
			}
			default -> throw new RuntimeException("Not implemented");
		}
		codeBlock.emit("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
		return type;
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return node.typeCheck(environment);
	}
}
