package ast.typing.values;

import ast.typing.types.IType;
import ast.typing.types.StringType;

public class StringValue implements IValue {
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public IType getType() {
        return StringType.Instance;
    }

}
