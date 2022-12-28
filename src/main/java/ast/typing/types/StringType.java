package ast.typing.types;

public class StringType extends ObjectType {
    public static final StringType Instance = new StringType();

    private StringType() {

    }

    @Override
    public String toString() {
        return "String";
    }

    @Override
    public void expect(IType other) {
        if (!this.equals(other)) {
            throw new TypeMismatchException(other, this);
        }
    }

    @Override
    public String getClassName() {
        return "java/lang/String";
    }
}
