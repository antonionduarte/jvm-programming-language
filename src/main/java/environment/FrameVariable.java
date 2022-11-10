package environment;

public class FrameVariable {
    private Frame frame;
    private int id;

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
}
