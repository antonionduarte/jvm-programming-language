package ast.typing.types;

import ast.typing.utils.Parameter;
import compilation.CompilerUtils;
import compilation.RecordManager;
import environment.InvalidIdentifierException;

import java.util.List;
import java.util.Objects;

public class RecordType extends ObjectType {
    private final List<Parameter> parameters;

    public RecordType(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public void expect(IType other) {
        if (!this.equals(other)) {
            throw new TypeMismatchException(other, this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RecordType that)) {
            return false;
        }
        return this.parameters.equals(that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters);
    }

    @Override
    public String getClassName() {
        return RecordManager.getInstance().register(this);
    }

    public int getId(String name){
        for (int i = 0; i < parameters.size(); i++) {
            if (parameters.get(i).name().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public IType getFieldType(int i){
        return parameters.get(i).type();
    }

    public List<? extends Parameter> getFields() {
        return this.parameters;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("struct {");
        for(var field : parameters){
            builder.append(field.name()).append(": ").append(field.type()).append(", ");
        }
        builder.append("}");
        return builder.toString();
    }
}
