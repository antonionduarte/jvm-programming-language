package ast.bool_nodes;

import ast.ASTNode;
import ast.types.BoolValue;
import ast.types.IValue;
import ast.types.ValueType;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;

public class ASTBool implements ASTNode {
    private boolean value;

    public ASTBool(boolean value) {
        this.value = value;
    }

    @Override
    public IValue eval(Environment<IValue> environment) {
        return new BoolValue(value);
    }

    @Override
    public ValueType compile(Frame frame, CodeBlock codeBlock) {
        throw new RuntimeException("Not implemented"); //TODO implement
    }

    @Override
    public ValueType typeCheck(Environment environment) {
        return ValueType.Bool;
    }
}
