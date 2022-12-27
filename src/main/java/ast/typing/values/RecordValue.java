package ast.typing.values;

import ast.typing.types.IType;
import ast.typing.types.RecordType;
import ast.typing.utils.Parameter;
import utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecordValue implements IValue {
    private final HashMap<String, IValue> fields;

    private final RecordType type;

    public RecordValue(List<Pair<String, IValue>> fields) {
        this.fields = new HashMap<>();
        List<Parameter> parameters = new ArrayList<>(fields.size());
        for (Pair<String, IValue> field : fields) {
            this.fields.put(field.a(), field.b());
            parameters.add(new Parameter(field.a(), field.b().getType()));
        }
        this.type = new RecordType(parameters);
    }

    public IValue getField(String name) {
        return fields.get(name);
    }

    @Override
    public IType getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("struct {");
        for (var field : fields.entrySet()) {
            builder.append(field.getKey()).append(" = ").append(field.getValue()).append("; ");
        }
        builder.append("}");
        return builder.toString();
    }
}
