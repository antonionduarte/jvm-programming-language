package ast.bool_nodes;

import ast.ASTNode;
import ast.types.BoolValue;
import ast.types.IValue;
import ast.types.ValueType;
import codeblock.CodeBlock;
import environment.Environment;
import environment.FrameManager;
import environment.InterpretationEnvironment;

public class ASTNot implements ASTNode {
    private ASTNode inner;

    public ASTNot(ASTNode inner) {
        this.inner = inner;
    }

    @Override
    public IValue eval(InterpretationEnvironment environment) {
        boolean v = BoolValue.asBoolean(inner.eval(environment));
        return new BoolValue(!v);
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


