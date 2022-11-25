package ast.bool_nodes;

import ast.ASTNode;
import ast.types.BoolValue;
import ast.types.IValue;
import ast.types.ValueType;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;

public class ASTOr implements ASTNode {
    private ASTNode lhs, rhs;

    public ASTOr(ASTNode lhs, ASTNode rhs){
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public IValue eval(Environment<IValue> environment) {
        boolean v1 = BoolValue.asBoolean(lhs.eval(environment));
        boolean v2 = BoolValue.asBoolean(rhs.eval(environment));
        return new BoolValue(v1 || v2);
    }

    @Override
    public ValueType compile(Frame frame, CodeBlock codeBlock) {
        throw new RuntimeException("Not implemented"); //TODO implement
    }

    @Override
    public ValueType typeCheck(Environment<ValueType> environment) {
        return ValueType.Bool;
    }
}
