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

public class ASTIf implements ASTNode {
    private ASTNode condition, bodyIf, bodyElse;

    public ASTIf(ASTNode condition, ASTNode bodyIf, ASTNode bodyElse) {
        this.condition = condition;
        this.bodyIf = bodyIf;
        this.bodyElse = bodyElse;
    }

    @Override
    public IValue eval(Environment<IValue> environment) {
        boolean condVal = BoolValue.asBoolean(condition.eval(environment));
        if(condVal){
            if(bodyElse!=null)
                return bodyIf.eval(environment);
            else return VoidValue.getInstance();
        } else if(bodyElse != null){
            return bodyElse.eval(environment);
        } else {
            return VoidValue.getInstance();
        }
    }

    @Override
    public ValueType compile(Frame frame, CodeBlock codeBlock) {
        return null;
    }

    @Override
    public ValueType typeCheck(Environment<ValueType> environment) {
        condition.typeCheck(environment).expect(new ValueType(Type.Bool));
        if(bodyElse == null){
            return new ValueType(Type.Void);
        } else {
            ValueType ifType = bodyIf.typeCheck(environment);
            ValueType elseType = bodyElse.typeCheck(environment);
            elseType.expect(ifType);
            return ifType;
        }
    }
}
