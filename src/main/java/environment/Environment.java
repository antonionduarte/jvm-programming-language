package environment;

import ast.types.ValueType;

public interface Environment {
    ValueType getAssociatedType(String id);
}
