package environment;

import java.util.Collection;
import java.util.List;

public class Frame extends Environment<FrameVariable> {
	private static final String FRAME_NAME_FORMAT = "frame_%d";
	private final int id;

	private final List<Frame> allFrames;

	public Frame(int id, List<Frame> allFrames) {
		super();
		this.id = id;
		this.allFrames = allFrames;
	}

	public Frame(int id, Frame parentFrame) {
		super(parentFrame);
		this.id = id;
		this.allFrames = parentFrame.allFrames;
	}

	public int getId() {
		return id;
	}

	public Frame getParentFrame() {
		return (Frame) upperEnvironment;
	}

	public String getFrameName() {
		return String.format(FRAME_NAME_FORMAT, id);
	}

	@Override
	public Frame beginScope() {
		Frame newFrame = new Frame(allFrames.size(), this);
		allFrames.add(newFrame);
		return newFrame;
	}

	public Collection<FrameVariable> getVars() {
		return associations.values();
	}
}
