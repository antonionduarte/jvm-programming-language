package compilation;

import ast.typing.types.IType;
import ast.typing.types.PrimitiveType;
import ast.typing.types.RecordType;
import ast.typing.utils.Parameter;
import environment.Frame;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordManager {
    private static final String CLASS_NAME = "record_%d";
    private static final String FIELD_NAME = "f%d";
    private static RecordManager instance = null;

    public static RecordManager getInstance() {
        if (instance == null) {
            instance = new RecordManager();
        }
        return instance;
    }

    private RecordManager() {
    }

    private final Map<RecordType,Integer> recordTypes = new HashMap<>();

    public static String getClassName(int id){
        return String.format(CLASS_NAME, id);
    }

    public static String getFieldName(int id){
        return String.format(FIELD_NAME, id);
    }

    public static String initSignature(RecordType record){
        StringBuilder builder = new StringBuilder();
        builder.append("<init>(");
        for (Parameter field : record.getFields()) {
            String type = field.type().getJvmId();
            builder.append(type);
        }
        builder.append(")V");
        return builder.toString();
    }

    /**
     * Register a record type if not already registered.
     * Returns the name of the class that represents it
     * @param recordType the record type to register
     * @return the name of the record's class
     */
    public String register(RecordType recordType) {
        int id = recordTypes.size();
        Integer value = recordTypes.putIfAbsent(recordType, id);
        return getClassName(value != null ? value : id);
    }

    public void dumpAll(String path) throws FileNotFoundException {
        for (var entry : recordTypes.entrySet()) {
            String name = getClassName(entry.getValue());
            PrintStream out = new PrintStream(path + "/" + name + ".j");
            dump(entry.getKey(), name, out);
        }
    }

    private void dump(RecordType record, String name, PrintStream stream) {
        stream.println(CompilerUtils.classHeader(name));
        int i = 0;
        for (Parameter field : record.getFields()) {
            int fieldId = i++;
            String fieldName = getFieldName(fieldId);
            String jvmType = field.type().getJvmId();
            stream.println(CompilerUtils.defineField(fieldName, jvmType));
        }
        stream.println(".method public " + initSignature(record)); //TODO const
        stream.println(".limit locals " + (record.getFields().size() + 1)); //TODO const
        stream.println(".limit stack 2"); //TODO const
        stream.println(CompilerUtils.loadLocalVariable(0));
        stream.println("invokenonvirtual java/lang/Object/<init>()V"); //TODO const
        i = 0;
        for (Parameter field : record.getFields()) {
            int fieldId = i++;
            IType type = field.type();
            boolean isInt = type.equals(PrimitiveType.Int) || type.equals(PrimitiveType.Bool);
            stream.println(CompilerUtils.loadLocalVariable(0));
            if(isInt){
                stream.println("  iload " + (fieldId + 1)); //TODO const
            } else {
                stream.println("  aload " + (fieldId + 1)); //TODO const
            }
            String jvmType = type.getJvmId();
            stream.println(CompilerUtils.setField(name, getFieldName(fieldId), jvmType));
        }
        stream.println("  return");
        stream.println(".end method"); //TODO const
        stream.close();
    }
}
