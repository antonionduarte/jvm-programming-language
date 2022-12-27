package ast.records;

import ast.ASTNode;
import ast.typing.types.IType;
import ast.typing.values.IValue;
import ast.typing.values.RecordValue;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;
import utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class ASTRecord implements ASTNode {
    private final List<Pair<String, ASTNode>> fields;

    public ASTRecord(List<Pair<String, ASTNode>> fields) {
        this.fields = fields;
    }

    @Override
    public IValue eval(Environment<IValue> environment) {
        List<Pair<String, IValue>> values = new ArrayList<>(this.fields.size());
        for(Pair<String, ASTNode> field : fields){
            IValue value = field.b().eval(environment);
            values.add(new Pair<>(field.a(), value));
        }
        return new RecordValue(values);
    }

    @Override
    public IType compile(Frame frame, CodeBlock codeBlock) {
        //TODO implement
        return null;
    }

    @Override
    public IType typeCheck(Environment<IType> environment) {
        return null;
    }
}
