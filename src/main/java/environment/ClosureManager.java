package environment;

import ast.typing.types.FunctionType;
import ast.typing.types.IType;
import ast.typing.types.PrimitiveType;
import ast.typing.types.ReferenceType;
import ast.typing.values.ClosureValue;

import java.util.*;

public class ClosureManager {
	public record ClosureInterface(String identifier, String jvmParameterTypes, String jvmReturnType) {}
	public record Closure(int identifier, ClosureInterface closureInterface) {}

	private static ClosureManager instance;

	private final List<Closure> allClosures; // honestly no idea if this is needed
	private final Map<FunctionType, ClosureInterface> closureInterfaces;

	private int currentId;

	public static ClosureManager getInstance() {
		if (instance == null) {
			instance = new ClosureManager();
		}
		return instance;
	}

	private ClosureManager() {
		this.allClosures = new LinkedList<>();
		this.closureInterfaces = new HashMap<>();
		this.currentId = 0;
	}

	public ClosureInterface getClosureInterface(FunctionType type) {
		return closureInterfaces.get(type);
	}

	public void addClosureInterface(FunctionType type) {
		String closureInterfaceString = buildClosureIdentifier(type);
		String closureParameterTypes = buildClosureParameters(type.getParameters());
		String closureReturnType = getJvmId(type.getReturnType());

		var closureInterface = new ClosureInterface(
				closureInterfaceString,
				closureParameterTypes,
				closureReturnType
		);
	}

	/**
	 * Builds the closure identifier, e.g. (int, int) -> int right now would be: int_int_int
	 * TODO: Maybe needs to be different because... functions as parameters(?) and return types(?)
	 */
	private String buildClosureIdentifier(FunctionType type) {
		var parameters = type.getParameters();
		var returnType = type.getReturnType();

		StringBuilder closureInterface = new StringBuilder();
		Iterator<IType> iterator = parameters.iterator();

		while (iterator.hasNext()) {
			var parameter = iterator.next();
			getJvmType(parameter);
			if (iterator.hasNext()) {
				closureInterface.append("_");
			}
		}

		closureInterface.append("_").append(((PrimitiveType) returnType).getJvmType());
		return closureInterface.toString();
	}

	/**
	 * Builds the parameters string, to be placed in the closure compilation (apply function and interface)
	 * e.g. (int, int) -> int would be: I,I
	 */
	private String buildClosureParameters(List<IType> parameters) {
		StringBuilder parametersString = new StringBuilder();
		Iterator<IType> iterator = parameters.iterator();

		while (iterator.hasNext()) {
			var parameter = iterator.next();
			var jvmType = getJvmType(parameter);
			parametersString.append(jvmType);
			if (iterator.hasNext()) {
				parametersString.append(",");
			}
		}

		return parametersString.toString();
	}

	/**
	 * Returns the JVM type string for a given type.
	 * TODO: I think this is needed because the JVM type string may be different for function and reference parameters?
	 *       honestly I'm still not sure how to deal with that.
	 */
	private String getJvmType(IType type) {
		String typeString;
		switch (type) {
			case PrimitiveType primitiveType -> typeString = primitiveType.getJvmType();
			case FunctionType functionType -> typeString = String.valueOf(functionType.getReturnType()); // TODO: Obviously this doesn't work
			case ReferenceType referenceType -> typeString = String.valueOf(referenceType.getJvmId()); // TODO: Obviously this doesn't work
			default -> throw new IllegalStateException("Unexpected value: " + type);
		}
		return typeString;
	}

	// TODO: FunctionType, RecordType and ReferenceType
	private String getJvmId(IType type) {
		String jvmId;
		switch (type) {
			case PrimitiveType primitiveType -> jvmId = primitiveType.getJvmId();
			case FunctionType functionType -> jvmId = functionType.getJvmId(); // TODO: Obviously this doesn't work
			case ReferenceType referenceType -> jvmId = referenceType.getJvmId(); // TODO: Obviously this doesn't work
			default -> throw new IllegalStateException("Unexpected value: " + type);
		}
		return jvmId;
	}

	public Map<FunctionType, ClosureInterface> getClosureInterfaces() {
		return closureInterfaces;
	}

	public int getCurrentId() {
		return currentId;
	}

	public List<Closure> getAllClosures() {
		return allClosures;
	}

	public void addClosure(Closure closure) {
		allClosures.add(closure);
		this.currentId++;
	}

	public void addClosure() {
		this.currentId++;
	}
}
