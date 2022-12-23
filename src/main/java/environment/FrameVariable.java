package environment;

import ast.typing.types.IType;

public class FrameVariable {
	private final Frame frame;
	private final int id;
	private IType type = null;

	public FrameVariable(Frame frame, int id) {
		this.frame = frame;
		this.id = id;
	}

	public Frame getFrame() {
		return frame;
	}

	public int getId() {
		return id;
	}

	public IType getType() {
		return type;
	}

	public void setType(IType type) {
		this.type = type;
	}
}
