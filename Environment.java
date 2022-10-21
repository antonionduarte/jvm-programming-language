import java.util.*;

public class Environment{
    // initial capacity of the HashMap
    private static final int NUM_VARS=10;

    public class UndefinedVariableException extends Exception{}

    private Environment parent;
    private Map<String, Integer> declarations = new HashMap<>(NUM_VARS);

    public Environment(){
        this.parent = null;
    }

    private Environment(Environment parent){
        this.parent = parent;
    }

    public Environment beginScope(){
        return new Environment(parent);
    }

    public Environment endScope(){
        return parent;
    }

    void assoc(String id, int val){
        declarations.put(id, val);
    }

    int find(String id){
        Integer value = declarations.get(id);
        if(value == null){
            if(parent == null){
                throw new UndefinedVariableException();
            }
            return parent.find(id);
        }
        return value;
    }
}