package ast.typing.types;

import java.util.List;
import java.util.Objects;

public class FunctionType implements IType {
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
		if (!Objects.equals(returnType, that.returnType)) {
			return false;
		}
		if (this.parameters.size() != that.parameters.size()) {
			return false;
		}
		for (int i = 0; i < this.parameters.size(); i++) {
			if (!this.parameters.get(i).equals(that.parameters.get(i))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String getJvmId() {
		return null;
	}
}
