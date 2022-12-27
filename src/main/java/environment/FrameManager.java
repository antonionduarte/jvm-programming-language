package environment;

public class FrameManager {
	private static FrameManager instance;

	private int maxFrameId;

	public static FrameManager getInstance() {
		if (instance == null) {
			instance = new FrameManager();
		}
		return instance;
	}

	private FrameManager() {
		this.maxFrameId = 0;
	}

	public int getMaxFrameId() {
		return maxFrameId;
	}

	public void setMaxFrameId(int maxFrameId) {
		this.maxFrameId = maxFrameId;
	}

	public Frame closureFrame(Frame parentFrame) {
		var frameId = this.getMaxFrameId();
		Frame newFrame = new Frame(maxFrameId, parentFrame);
		FrameManager.getInstance().setMaxFrameId(frameId + 1);
		return newFrame;
	}
}
