package ast.typing.types;

import environment.ClosureManager;

import java.util.List;
import java.util.Objects;

public class FunctionType extends ObjectType {
	private final List<IType> parameters;
	private final IType returnType;

	public FunctionType(List<IType> parameters, IType returnType) {
		this.parameters = parameters;
		this.returnType = returnType;
	}

	@Override
	public void expect(IType other) {
		if (!this.equals(other)) {
			throw new TypeMismatchException(other, this);
		}
	}

	public List<IType> getParameters() {
		return parameters;
	}

	public IType getReturnType() {
		return returnType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof FunctionType that)) {
			return false;
		}
		return this.parameters.equals(that.parameters) && this.returnType.equals(that.returnType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(parameters, returnType);
	}

	@Override
	public String getClassName() {
		var interfaceId = ClosureManager.getInstance().getClosureInterface(this).identifier();
		return "closure_interface_" + interfaceId;
	}
}
