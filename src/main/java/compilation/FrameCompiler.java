package compilation;

import ast.ASTNode;
import ast.typing.types.ValueType;
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
			PrintStream out = new PrintStream(path + "/" + frame.getName() + ".j");
			dump(frame, out);
		}
	}

	public static void dump(Frame frame, PrintStream stream) {
		String name = frame.getName();
		stream.println(CompilerUtils.classHeader(name));
		String parent = frame.getParentFrame() == null ? CompilerUtils.OBJECT : frame.getParentFrame().getName();
		String parentType = CompilerUtils.toReferenceType(parent);
		stream.println(CompilerUtils.defineField(PARENT_FIELD, parentType));
		for (FrameVariable variable : frame.getVars()) {
			String fieldName = getFieldName(variable.getId());
			String type = variable.getType().getJvmId();
			stream.println(CompilerUtils.defineField(fieldName, type));
		}
		stream.println(CompilerUtils.EMPTY_CONSTRUCTOR);
	}

	public static void emitBeginScope(CodeBlock block, Frame newFrame) {
		String parent = newFrame.getParentFrame() == null ? CompilerUtils.OBJECT : newFrame.getParentFrame().getName();
		String parentType = CompilerUtils.toReferenceType(parent);
		String name = newFrame.getName();
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
			//skip parent referencing for root frames
		} else {
			String name = current.getName();
			String parent = current.getParentFrame().getName();
			String parentType = CompilerUtils.toReferenceType(parent);
			block.emit(CompilerUtils.loadLocalVariable(SCOPE_VARIABLE));
			block.emit(CompilerUtils.getField(name, PARENT_FIELD, parentType));
		}
		block.emit(CompilerUtils.storeLocalVariable(SCOPE_VARIABLE));
		block.emit(CompilerUtils.comment("end of scope") + "\n\n");
	}

	public static void emitAssign(CodeBlock block, FrameVariable variable, ASTNode expression) {
		String name = variable.getFrame().getName();
		ValueType type = expression.compile(variable.getFrame(), block);
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
			String name = frame.getName();
			Frame parentFrame = frame.getParentFrame();
			assert parentFrame != null; //variable would've been null
			String parent = parentFrame.getName();
			String parentType = CompilerUtils.toReferenceType(parent);
			block.emit(CompilerUtils.getField(name, PARENT_FIELD, parentType));
			frame = parentFrame;
		}
		String name = frame.getName();
		String fieldName = getFieldName(variable.getId());
		String type = variable.getType().getJvmId();
		block.emit(CompilerUtils.getField(name, fieldName, type));
	}
}
