package ast.strings;

import ast.ASTNode;
import ast.typing.types.IType;
import ast.typing.types.StringType;
import ast.typing.values.IValue;
import ast.typing.values.StringValue;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;

public class ASTConcat implements ASTNode {
    private final ASTNode left;
    private final ASTNode right;

    public ASTConcat(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public IValue eval(Environment<IValue> environment) {
        IValue leftVal = left.eval(environment);
        IValue rightVal = right.eval(environment);
        ASTToString.checkValidTarget(leftVal.getType());
        ASTToString.checkValidTarget(rightVal.getType());
        String leftStr = leftVal.toString();
        String rightStr = rightVal.toString();
        return new StringValue(leftStr + rightStr);
    }

    @Override
    public IType compile(Frame frame, CodeBlock codeBlock) {
        IType leftType = left.compile(frame, codeBlock);
        ASTToString.emitToString(codeBlock, leftType);
        IType rightType = right.compile(frame, codeBlock);
        ASTToString.emitToString(codeBlock, rightType);
        codeBlock.emit("invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;");
        return StringType.Instance;
    }

    @Override
    public IType typeCheck(Environment<IType> environment) {
        ASTToString.checkValidTarget(left.typeCheck(environment));
        ASTToString.checkValidTarget(right.typeCheck(environment));
        return StringType.Instance;
    }
}
