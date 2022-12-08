package ast.typing.types;

public interface IType {

	/*
		TODO: Refactor this type system to work with this IType interface
		   - Different implementation for Cell Values
		   - Different implementation for Closure Values
	*/

	void expect();

}
