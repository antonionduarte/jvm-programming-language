package environment;

import ast.types.ValueType;

import java.util.Collection;
import java.util.List;

public class Frame extends Environment<FrameVariable> {
    private static final String NAME_FORMAT = "frame_%d";
    private int id;

    private List<Frame> allFrames;


    public Frame(int id, List<Frame> allFrames){
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
        return (Frame)upperEnvironment;
    }

    public String getName(){
        return String.format(NAME_FORMAT, id);
    }

    @Override
    public Frame beginScope(){
        Frame newFrame = new Frame(allFrames.size(), this);
        allFrames.add(newFrame);
        return newFrame;
    }

    public Collection<FrameVariable> getVars(){
        return associations.values();
    }

}
