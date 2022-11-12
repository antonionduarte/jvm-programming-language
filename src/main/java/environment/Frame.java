package environment;

import java.util.HashMap;
import java.util.Map;

public class Frame {
    private static final String NAME_FORMAT = "frame_%d";
    private int id;
    private Map<String, FrameVariable> varMap;
    private Frame parentFrame;

    public Frame(int id, Frame parentFrame) {
        this.id = id;
        this.varMap = new HashMap<>();
        this.parentFrame = parentFrame;
    }

    public int getId() {
        return id;
    }

    public Frame getParentFrame() {
        return parentFrame;
    }

    public String getName(){
        return String.format(NAME_FORMAT, id);
    }

    public int getVarsLength(){
        return varMap.size();
    }

    public int addDeclaration(String varName){
        if(varMap.containsKey(varName)){
            throw new RuntimeException("Repeated declaration of " + varName);
        }
        int varNumber = varMap.size();
        varMap.put(varName, new FrameVariable(this, varNumber));
        return varNumber;
    }

    public FrameVariable find(String varName){
        FrameVariable var = varMap.get(varName);
        if(var == null) {
            if(parentFrame != null){
                return parentFrame.find(varName);
            } else {
                return null;
            }
        } else {
            return var;
        }
    }
}
