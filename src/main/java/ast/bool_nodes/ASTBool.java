package ast.bool_nodes;

import ast.ASTNode;
import ast.types.BoolValue;
import ast.types.IValue;
import ast.types.ValueType;
import codeblock.CodeBlock;
import environment.Environment;
import environment.FrameManager;
import environment.InterpretationEnvironment;

public class ASTBool implements ASTNode {
    private boolean value;

    public ASTBool(boolean value) {
        this.value = value;
    }

    @Override
    public IValue eval(InterpretationEnvironment environment) {
        return new BoolValue(value);
    }

    @Override
    public void compile(FrameManager frameManager, CodeBlock codeBlock) {
        throw new RuntimeException("Not implemented"); //TODO implement
    }

    @Override
    public ValueType getReturnType(Environment environment) {
        return ValueType.Bool;
    }
}
