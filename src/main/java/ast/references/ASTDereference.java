package ast.references;

import ast.ASTNode;
import ast.typing.types.Type;
import ast.typing.values.CellValue;
import ast.typing.values.IValue;
import ast.typing.types.ValueType;
import compilation.CodeBlock;
import compilation.CompilerUtils;
import environment.Environment;
import environment.Frame;

public class ASTDereference implements ASTNode {
	private final ASTNode node;

	public ASTDereference(ASTNode node) {
		this.node = node;
	}

	/* TODO: Typecheck this */
	@Override
	public IValue eval(Environment<IValue> environment) {
		return ((CellValue) this.node.eval(environment)).getValue();
	}

	@Override
	public ValueType compile(Frame frame, CodeBlock codeBlock) {
		ValueType type = node.compile(frame, codeBlock);
		type.expect(new ValueType(Type.Ref));
		//TODO change to support non int refs
		codeBlock.emit(CompilerUtils.getField("Ref", "vi", Type.Int.getJvmId()));
		return new ValueType(Type.Int);
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return null;
	}
}
