package ast.bool_nodes;

import ast.ASTNode;
import ast.types.BoolValue;
import ast.types.IValue;
import ast.types.IntValue;
import ast.types.ValueType;
import codeblock.CodeBlock;
import environment.Environment;
import environment.FrameManager;
import environment.InterpretationEnvironment;

public class ASTGreaterThan implements ASTNode {
    private ASTNode lhs, rhs;

    public ASTGreaterThan(ASTNode lhs, ASTNode rhs){
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public IValue eval(InterpretationEnvironment environment) {
        int v1 = IntValue.asInt(lhs.eval(environment));
        int v2 = IntValue.asInt(rhs.eval(environment));
        return new BoolValue(v1 > v2);
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