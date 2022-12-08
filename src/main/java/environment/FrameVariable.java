package environment;

import ast.typing.types.ValueType;

public class FrameVariable {
	private final Frame frame;
	private final int id;
	private ValueType type = null;

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

	public ValueType getType() {
		return type;
	}

	public void setType(ValueType type) {
		this.type = type;
	}
}
