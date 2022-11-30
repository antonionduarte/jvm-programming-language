package ast.int_nodes;

import ast.ASTNode;
import ast.types.IValue;
import ast.types.IntValue;
import ast.types.Type;
import ast.types.ValueType;
import compilation.CodeBlock;
import compilation.CompilerUtils;
import environment.Environment;
import environment.Frame;

public class ASTPlus implements ASTNode {

	ASTNode lhs, rhs;

	public ASTPlus(ASTNode l, ASTNode r) {
		lhs = l;
		rhs = r;
	}

	public IValue eval(Environment<IValue> environment) {
		int v1 = IntValue.asInt(lhs.eval(environment));
		int v2 = IntValue.asInt(rhs.eval(environment));
		return new IntValue(v1 + v2);
	}

	@Override
	public ValueType compile(Frame frame, CodeBlock codeBlock) {
		lhs.compile(frame, codeBlock).expect(new ValueType(Type.Int));
		rhs.compile(frame, codeBlock).expect(new ValueType(Type.Int));
		codeBlock.emit(CompilerUtils.ADD);
		return new ValueType(Type.Int);
	}

	@Override
	public ValueType typeCheck(Environment<ValueType> environment) {
		return new ValueType(Type.Int);
	}
}

