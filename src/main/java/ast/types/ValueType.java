package ast.types;

public enum ValueType {
    Int("I"),
    Bool("B"/*TODO: check if this is right*/),
    Void("V");

    private final String jvmId;
    ValueType(String jvmId){
        this.jvmId = jvmId;
    }

    public String getJvmId() {
        return jvmId;
    }

    public void expect(ValueType expected){
        if(expected != this){
            throw new TypeMismatchException(expected, this);
        }
    }
}
