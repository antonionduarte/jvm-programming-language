package ast;

import ast.typing.types.Type;
import ast.typing.types.ValueType;
import ast.typing.values.IValue;
import ast.typing.values.VoidValue;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;

public class ASTPrint implements ASTNode {
    private ASTNode node;

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
        return null;
    }

    @Override
    public ValueType typeCheck(Environment<ValueType> environment) {
        return node.typeCheck(environment);
    }
}
