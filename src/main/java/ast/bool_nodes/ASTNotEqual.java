package ast.bool_nodes;

import ast.ASTNode;
import ast.types.BoolValue;
import ast.types.IValue;
import ast.types.ValueType;
import codeblock.CodeBlock;
import environment.Environment;
import environment.FrameManager;
import environment.InterpretationEnvironment;

public class ASTNotEqual implements ASTNode {
    private ASTNode lhs, rhs;

    public ASTNotEqual(ASTNode lhs, ASTNode rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public IValue eval(InterpretationEnvironment environment) {
        IValue v1 = lhs.eval(environment);
        IValue v2 = rhs.eval(environment);
        return new BoolValue(!v1.equals(v2));
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


