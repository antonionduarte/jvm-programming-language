package ast.records;

import ast.ASTNode;
import ast.typing.types.IType;
import ast.typing.types.TypeMismatchException;
import ast.typing.values.IValue;
import compilation.CodeBlock;
import environment.Environment;
import environment.Frame;
import environment.InvalidIdentifierException;

public class ASTGetField implements ASTNode {
    private final ASTNode record;
    private final String field;

    public ASTGetField(ASTNode record, String field) {
        this.record = record;
        this.field = field;
    }


    @Override
    public IValue eval(Environment<IValue> environment) {
        IValue value = record.eval(environment);
        if(!(value instanceof ast.typing.values.RecordValue recordValue)){
            throw new TypeMismatchException("struct", value.getType());
        }
        IValue fieldValue = recordValue.getField(field);
        if(fieldValue == null){
            throw new InvalidIdentifierException("struct has no field " + field);
        }
        return fieldValue;
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
