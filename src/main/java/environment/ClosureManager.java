package environment;

import ast.typing.types.FunctionType;
import ast.typing.types.IType;
import ast.typing.values.ClosureValue;
import compilation.CodeBlock;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ClosureManager {
	private static ClosureManager instance;

	private final List<ClosureValue> allClosures; // honestly no idea if this is needed
	private int currentId;
	private Map<FunctionType, String> closureInterfaces;

	public static ClosureManager getInstance() {
		if (instance == null) {
			instance = new ClosureManager();
		}
		return instance;
	}

	private ClosureManager() {
		this.allClosures = new LinkedList<>();
		this.currentId = 0; // the id, which is a counter
	}

	public String getClosureInterface(FunctionType type) {
		return closureInterfaces.get(type);
	}

	public String setClosureInterface(FunctionType type) {
		// Generate the closure interface type string, something like: int_int for a function of type (int) -> int
		// plop that closure interface string on the Map
		return null;
	}

	public int getCurrentId() {
		return currentId;
	}

	public void addClosure(ClosureValue closure) {
		allClosures.add(closure);
		this.currentId++;
	}

	public void addClosure() {
		this.currentId++;
	}

}
