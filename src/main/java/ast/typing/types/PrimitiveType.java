package ast.typing.types;

public enum PrimitiveType implements IType {
    Int("I"),
    Bool("I"),
    String("Ljava/lang/String;"), /* TODO: Check if this is right */
    Void("V");

    private final String jvmId;

    PrimitiveType(String jvmId) {
        this.jvmId = jvmId;
    }

    @Override
    public String getJvmId() {
        return jvmId;
    }

    @Override
    public void expect(IType other) {
        if(!this.equals(other)) {
            throw new TypeMismatchException(other, this);
        }
    }
}

