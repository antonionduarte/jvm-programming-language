package ast.records;

import ast.ASTNode;
import ast.typing.types.IType;
import ast.typing.types.RecordType;
import ast.typing.types.TypeMismatchException;
import ast.typing.values.IValue;
import compilation.CodeBlock;
import compilation.CompilerUtils;
import compilation.RecordManager;
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
        IType type = record.compile(frame, codeBlock);
        if(!(type instanceof RecordType recordType)){
            throw new TypeMismatchException("struct", type);
        }
        int fieldId = recordType.getId(field);
        IType fieldType = recordType.getFieldType(fieldId);
        String className = RecordManager.getInstance().register(recordType);
        if(fieldId == -1){
            throw new InvalidIdentifierException("struct has no field " + field);
        }
        codeBlock.emit(CompilerUtils.getField(className,
                RecordManager.getFieldName(fieldId), fieldType.getJvmId()));
        return fieldType;
    }

    @Override
    public IType typeCheck(Environment<IType> environment) {
        return null;
    }
}
