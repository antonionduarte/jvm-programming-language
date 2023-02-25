package environment;

import ast.ASTNode;
import ast.typing.types.FunctionType;
import ast.typing.types.IType;

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
		ClosureInterface closureInterface = closureInterfaces.get(type);
		if(closureInterface == null){
			String closureInterfaceString = String.valueOf(currentInterfaceId++);
			String closureParameterTypes = buildClosureParameters(type.getParameters());
			String closureReturnType = getJvmId(type.getReturnType());

			List<String> parameterJvmIdentifiers = parameterJvmIdentifiers(type.getParameters());

			closureInterface = new ClosureInterface(
					closureInterfaceString,
					closureParameterTypes,
					closureReturnType,
					parameterJvmIdentifiers
			);
			closureInterfaces.put(type, closureInterface);
		}
		return closureInterface;
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

	private String getJvmId(IType type) {
		return type.getJvmId();
	}

	public Map<FunctionType, ClosureInterface> getClosureInterfaces() {
		return closureInterfaces;
	}

	public List<Closure> getAllClosures() {
		return allClosures;
	}

	public Closure addClosure(ClosureInterface closureInterface, Frame parentFrame, Frame activationFrame, ASTNode body) {
		var closure = new Closure(currentId, parentFrame, activationFrame, closureInterface, body);
		allClosures.add(closure);
		this.currentId++;
		return closure;
	}
}
