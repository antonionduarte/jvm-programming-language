package ast.typing.types;

import ast.typing.utils.Parameter;

import java.util.List;

public class RecordType implements IType {
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
        if (this.parameters.size() != that.parameters.size()) {
            return false;
        }
        for (int i = 0; i < this.parameters.size(); i++) {
            if (!this.parameters.get(i).equals(that.parameters.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getJvmId() {
        //TODO implement
        return null;
    }
}
