package ast.types;

public class TypeMismatchException extends RuntimeException {
    public TypeMismatchException(ValueType expected, ValueType got){
        super(String.format("Type Mistmatch: Was expecting %s but got %s", expected, got));
    }
}
