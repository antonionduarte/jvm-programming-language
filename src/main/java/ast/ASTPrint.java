package ast;

import ast.strings.ASTToString;
import ast.typing.types.IType;
import ast.typing.types.PrimitiveType;
import ast.typing.types.StringType;
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
	public IType compile(Frame frame, CodeBlock codeBlock) {
		IType type = node.compile(frame, codeBlock);
		// duplicate value to leave it on stack after printing
		codeBlock.emit(CompilerUtils.DUPLICATE);
		codeBlock.emit("getstatic java/lang/System/out Ljava/io/PrintStream;");
		codeBlock.emit(CompilerUtils.SWAP);
		ASTToString.emitToString(codeBlock, type);
		codeBlock.emit("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
		return type;
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {
		IType type = node.typeCheck(environment);
		ASTToString.checkValidTarget(type);
		return type;
	}
}
