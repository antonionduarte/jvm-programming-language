package compilation;

import ast.ASTNode;
import ast.typing.types.IType;
import environment.ClosureManager;
import environment.ClosureManager.ClosureInterface;
import environment.ClosureManager.Closure;
import environment.Frame;
import environment.FrameVariable;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

public class FrameCompiler {

	public static final int SCOPE_VARIABLE = 3;
	private static final String PARENT_FIELD = "sl";
	private static final String VAR_FIELD = "v%d";

	private FrameCompiler() {
	}

	private static String getFieldName(int id) {
		return String.format(VAR_FIELD, id);
	}

	public static void dumpAll(String path, List<Frame> frames) throws FileNotFoundException {
		for (Frame frame : frames) {
			PrintStream out = new PrintStream(path + "/" + frame.getFrameName() + ".j");
			dump(frame, out);
		}
	}

	public static void dump(Frame frame, PrintStream stream) {
		String name = frame.getFrameName();
		stream.println(CompilerUtils.classHeader(name));
		String parent = frame.getParentFrame() == null ? CompilerUtils.OBJECT : frame.getParentFrame().getFrameName();
		String parentType = CompilerUtils.toReferenceType(parent);
		stream.println(CompilerUtils.defineField(PARENT_FIELD, parentType));
		for (FrameVariable variable : frame.getVars()) {
			String fieldName = getFieldName(variable.getId());
			String type = variable.getType().getJvmId();
			stream.println(CompilerUtils.defineField(fieldName, type));
		}
		stream.println(CompilerUtils.EMPTY_CONSTRUCTOR);
	}

	public static void dumpClosureInterface(ClosureInterface closureInterface, PrintStream stream) {
		stream.printf((CompilerUtils.INTERFACE_HEADER),
				closureInterface.identifier(),
				closureInterface.jvmParameterTypes(),
				closureInterface.jvmReturnType()
		);
	}

	public static void dumpAllClosuresInterfaces(String path) throws FileNotFoundException {
		for (var closureInterface : ClosureManager.getInstance().getClosureInterfaces().values()) {
			PrintStream out = new PrintStream(path + "/" + "closure_interface_" + closureInterface.identifier() + ".j");
			dumpClosureInterface(closureInterface, out);
		}
	}

	public static void dumpClosure(Closure closure, PrintStream stream) {
		stream.printf((CompilerUtils.CLOSURE_HEADER),
				closure.identifier(),
				closure.closureInterface().identifier(),
				closure.parentFrame().getFrameName()
		);
		stream.println(CompilerUtils.EMPTY_CONSTRUCTOR);

		ClosureInterface closureInterface = closure.closureInterface();

		StringBuilder applyLoadParameters = new StringBuilder();

		var parentFrameName = closure.parentFrame().getFrameName();
		var activationFrameName = closure.activationFrame().getFrameName();
		var closureIdentifier = closure.identifier();

		for (var i = 0; i < closure.closureInterface().jvmParameterIdentifiers().size(); i++) {
			var identifier = closureInterface.jvmParameterIdentifiers().get(i);
			applyLoadParameters
					.append(String.format("iload %d\n", i + 1))
					.append(String.format("putfield %s/v%d %s\n", activationFrameName, i, identifier));
		}

		CodeBlock codeBlock = new CodeBlock();
		closure.body().compile(closure.activationFrame(), codeBlock);
		var applyBody = codeBlock.dumpString();

		stream.printf((CompilerUtils.APPLY_METHOD_HEADER),
				closureInterface.jvmParameterTypes(),
				closureInterface.jvmReturnType(),
				closureInterface.jvmParameterIdentifiers().size(),
				activationFrameName,
				activationFrameName,
				closureIdentifier,
				parentFrameName,
				activationFrameName,
				parentFrameName,
				applyLoadParameters,
				applyBody
		);
	}

	public static void dumpAllClosures(String path) throws FileNotFoundException {
		for (var closure : ClosureManager.getInstance().getAllClosures()) {
			PrintStream out = new PrintStream(path + "/" + "closure_" + closure.identifier() + ".j");
			dumpClosure(closure, out);
		}
	}

	public static void beginFunctionCall(CodeBlock block, ClosureInterface closureInterface) {
		block.emit(String.format(CompilerUtils.INTERFACE_CHECK_CAST, closureInterface.identifier()));
	}

	public static void emitFunctionCall(CodeBlock block, ClosureInterface closureInterface) {
		block.emit(String.format(CompilerUtils.INVOKE_INTERFACE,
				closureInterface.identifier(),
				closureInterface.jvmParameterTypes(),
				closureInterface.jvmReturnType(),
				closureInterface.jvmParameterIdentifiers().size()));
	}

	public static void emitBeginScope(CodeBlock block, Frame newFrame) {
		String parent = newFrame.getParentFrame() == null ? CompilerUtils.OBJECT : newFrame.getParentFrame().getFrameName();
		String parentType = CompilerUtils.toReferenceType(parent);
		String name = newFrame.getFrameName();
		block.emit("\n\n" + CompilerUtils.comment("start of scope"));
		block.emit(CompilerUtils.initClass(name));
		block.emit(CompilerUtils.DUPLICATE);
		block.emit(CompilerUtils.loadLocalVariable(SCOPE_VARIABLE));
		block.emit(CompilerUtils.setField(name, PARENT_FIELD, parentType));
		block.emit(CompilerUtils.storeLocalVariable(SCOPE_VARIABLE));
	}

	public static void emitEndScope(CodeBlock block, Frame current) {
		if (current.getParentFrame() == null) {
			block.emit(CompilerUtils.PUSH_NULL);
			// skip parent referencing for root frames
		} else {
			String name = current.getFrameName();
			String parent = current.getParentFrame().getFrameName();
			String parentType = CompilerUtils.toReferenceType(parent);
			block.emit(CompilerUtils.loadLocalVariable(SCOPE_VARIABLE));
			block.emit(CompilerUtils.getField(name, PARENT_FIELD, parentType));
		}
		block.emit(CompilerUtils.storeLocalVariable(SCOPE_VARIABLE));
		block.emit(CompilerUtils.comment("end of scope") + "\n\n");
	}

	public static void emitAssign(CodeBlock block, FrameVariable variable, ASTNode expression) {
		String name = variable.getFrame().getFrameName();
		IType type = expression.compile(variable.getFrame(), block);
		block.emit(CompilerUtils.DUPLICATE);
		block.emit(CompilerUtils.loadLocalVariable(SCOPE_VARIABLE));
		block.emit(CompilerUtils.SWAP);
		int varNumber = variable.getId();
		variable.setType(type);
		block.emit(CompilerUtils.setField(name, getFieldName(varNumber), type.getJvmId()));
	}

	public static void emitGetValue(CodeBlock block, FrameVariable variable, Frame current) {
		block.emit(CompilerUtils.loadLocalVariable(SCOPE_VARIABLE));
		Frame frame = current;
		while (frame != variable.getFrame()) {
			String name = frame.getFrameName();
			Frame parentFrame = frame.getParentFrame();
			assert parentFrame != null; // variable would've been null
			String parent = parentFrame.getFrameName();
			String parentType = CompilerUtils.toReferenceType(parent);
			block.emit(CompilerUtils.getField(name, PARENT_FIELD, parentType));
			frame = parentFrame;
		}
		String name = frame.getFrameName();
		String fieldName = getFieldName(variable.getId());
		String type = variable.getType().getJvmId();
		block.emit(CompilerUtils.getField(name, fieldName, type));
	}
}
