package ast.typing.types;

public interface IType {

	/*
		TODO: Refactor this type system to work with this IType interface
		   - Different implementation for Cell Values
		   - Different implementation for Closure Values
	*/

	void expect(IType other);

	/**
	 * Returns the value of the type parameter for a field of this type.
	 * Used for records.
	 * @return The value of the type parameter for a field of this type.
	 */
	String getJvmId();

}
