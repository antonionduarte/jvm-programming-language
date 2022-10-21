import java.util.*;

public class ASTId implements ASTNode {
    List<Pair<String, ASTNode>> init;
    ASTNode body;
    public int eval(Environment e) {
        Environment inner = e.beginScope();
        for (Pair<String, ASTNode> def : init){
            e.assoc(def.getKey(), def.getValue().eval(inner))
        }
        int val = body.eval(inner);
        e.endScope();
        return val;
    }
    public void compile(CodeBlock block){
        throw new RuntimeException("Not implemented");
    }
    public ASTNeg(String id) {
        this.id = id;
    }
}