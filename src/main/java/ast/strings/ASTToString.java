package ast.strings;

import ast.ASTNode;
import ast.typing.types.IType;
import ast.typing.types.PrimitiveType;
import ast.typing.types.StringType;
import ast.typing.values.IValue;
import ast.typing.values.StringValue;
import compilation.CodeBlock;
import compilation.CompilerUtils;
import environment.Environment;
import environment.Frame;

public class ASTToString implements ASTNode {
    private final ASTNode node;

    public ASTToString(ASTNode node) {
        this.node = node;
    }

    public static void checkValidTarget(IType type){
        if(!(type.equals(PrimitiveType.Int)
                || type.equals(PrimitiveType.Bool)
                || type.equals(StringType.Instance))) {
            throw new RuntimeException("Can't convert " + type + " to String");
        }
    }

    public static void emitToString(CodeBlock codeBlock, IType type) {
		if (type.equals(PrimitiveType.Int)) {
			codeBlock.emit("invokestatic java/lang/String/valueOf(I)Ljava/lang/String;");
		} else if (type.equals(PrimitiveType.Bool)) {
			CodeBlock.DelayedOp gotoElse = codeBlock.delayEmit();
			codeBlock.emit(CompilerUtils.pushString("true"));
			CodeBlock.DelayedOp skipElse = codeBlock.delayEmit();
			String label = codeBlock.emitLabel();
			codeBlock.emit(CompilerUtils.pushString("false"));
			skipElse.set(CompilerUtils.gotoAlways(codeBlock.emitLabel()));
			gotoElse.set(CompilerUtils.gotoIfFalse(label));
		} else if (!type.equals(StringType.Instance)) {
			throw new RuntimeException("Can't convert " + type + " to String");
		}
    }

    @Override
    public IValue eval(Environment<IValue> environment) {
        IValue value = node.eval(environment);
        checkValidTarget(value.getType());
        return new StringValue(value.toString());
    }

    @Override
    public IType compile(Frame frame, CodeBlock codeBlock) {
        emitToString(codeBlock, node.compile(frame, codeBlock));
        return StringType.Instance;
    }

    @Override
    public IType typeCheck(Environment<IType> environment) {
        checkValidTarget(node.typeCheck(environment));
        return StringType.Instance;
    }
}
