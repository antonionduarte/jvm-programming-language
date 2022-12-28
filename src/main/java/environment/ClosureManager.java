package environment;

import ast.ASTNode;
import ast.typing.types.FunctionType;
import ast.typing.types.IType;
import ast.typing.types.PrimitiveType;
import ast.typing.types.ReferenceType;

import java.util.*;

public class ClosureManager {
	public record ClosureInterface(String identifier, String jvmParameterTypes, String jvmReturnType, List<String> jvmParameterIdentifiers) {}
	public record Closure(int identifier, Frame parentFrame, Frame activationFrame, ClosureInterface closureInterface, ASTNode body) {}

	private static ClosureManager instance;

	private final List<Closure> allClosures; // honestly no idea if this is needed
	private final Map<FunctionType, ClosureInterface> closureInterfaces;

	private int currentInterfaceId;
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
		this.currentInterfaceId = 0;
	}

	public ClosureInterface getClosureInterface(FunctionType type) {
		return closureInterfaces.computeIfAbsent(type, k -> {
			String closureInterfaceString = String.valueOf(currentInterfaceId++);
			String closureParameterTypes = buildClosureParameters(type.getParameters());
			String closureReturnType = getJvmId(type.getReturnType());

			List<String> parameterJvmIdentifiers = parameterJvmIdentifiers(type.getParameters());

			return new ClosureInterface(
					closureInterfaceString,
					closureParameterTypes,
					closureReturnType,
					parameterJvmIdentifiers
			);
		});
	}

	/**
	 * Builds the parameters string, to be placed in the closure compilation (apply function and interface)
	 * e.g. (int, int) -> int would be: I,I
	 */
	private String buildClosureParameters(List<IType> parameters) {
		StringBuilder parametersString = new StringBuilder();

		for (IType parameter : parameters) {
			var jvmType = getJvmId(parameter);
			parametersString.append(jvmType);
		}

		return parametersString.toString();
	}

	/**
	 * TODO: Probably doesn't work for the same reason as the others: complex
	 *       identifiers for function types and reference types.
	 */
	private List<String> parameterJvmIdentifiers(List<IType> parameters) {
		List<String> parameterJvmIdentifiers = new ArrayList<>();
		for (IType parameter : parameters) {
			parameterJvmIdentifiers.add(getJvmId(parameter));
		}
		return parameterJvmIdentifiers;
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

	public void addClosure(ClosureInterface closureInterface, Frame parentFrame, Frame activationFrame, ASTNode body) {
		allClosures.add(new Closure(currentId, parentFrame, activationFrame, closureInterface, body));
		this.currentId++;
	}
}
