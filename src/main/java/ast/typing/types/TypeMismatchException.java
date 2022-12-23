package ast.typing.types;

public class TypeMismatchException extends RuntimeException {
	public TypeMismatchException(Object expected, Object got) {
		super(String.format("Type Mistmatch: Was expecting %s but got %s",
				expected, got));
	}
}
