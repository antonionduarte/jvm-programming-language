package ast.flow;

import ast.ASTNode;
import ast.typing.types.IType;
import ast.typing.types.PrimitiveType;
import ast.typing.values.IValue;
import ast.typing.values.VoidValue;
import compilation.CodeBlock;
import compilation.CompilerUtils;
import compilation.FrameCompiler;
import environment.Environment;
import environment.Frame;

import java.util.List;

public class ASTBlock implements ASTNode {
	private final List<ASTNode> instructions;

	public ASTBlock(List<ASTNode> instructions) {
		this.instructions = instructions;
	}

	@Override
	public IValue eval(Environment<IValue> environment) {
		var inner = environment.beginScope();
		IValue returnVal = VoidValue.getInstance();
		for (ASTNode instruction : instructions) {
			returnVal = instruction.eval(inner);
		}
		inner.endScope();
		return returnVal;
	}

	@Override
	public IType compile(Frame frame, CodeBlock codeBlock) {
		Frame inner = frame.beginScope();
		FrameCompiler.emitBeginScope(codeBlock, inner);
		IType returnType = PrimitiveType.Void;
		for (var instruction : instructions) {
			// discard result of last instruction to clear the stack
			if (returnType != PrimitiveType.Void) {
				codeBlock.emit(CompilerUtils.DISCARD);
			}
			codeBlock.emit("\n" + CompilerUtils.comment("instruction"));
			returnType = instruction.compile(inner, codeBlock);
		}
		FrameCompiler.emitEndScope(codeBlock, inner);
		return returnType;
	}

	@Override
	public IType typeCheck(Environment<IType> environment) {
		IType last = PrimitiveType.Void;
		Environment<IType> inner = environment.beginScope();
		for (ASTNode instruction : instructions) {
			last = instruction.typeCheck(inner);
		}
		inner.endScope();
		return last;
	}
}
