package ast.flow;
import ast.ASTNode;
import ast.typing.types.Type;
import ast.typing.types.ValueType;
import ast.typing.values.BoolValue;
import ast.typing.values.IValue;
import ast.typing.values.VoidValue;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;

public class ASTWhile implements ASTNode{
    private ASTNode condition, body;

    public ASTWhile(ASTNode condition, ASTNode body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public IValue eval(Environment<IValue> environment) {
        boolean condVal = BoolValue.asBoolean(condition.eval(environment));
        while(condVal){
            body.eval(environment);
            condVal = BoolValue.asBoolean(condition.eval(environment));
        }
        return VoidValue.getInstance();
    }

    @Override
    public ValueType compile(Frame frame, CodeBlock codeBlock) {
        return null;
    }

    @Override
    public ValueType typeCheck(Environment<ValueType> environment) {
        condition.typeCheck(environment).expect(new ValueType(Type.Bool));
        body.typeCheck(environment);
        return new ValueType(Type.Void);
    }
}
