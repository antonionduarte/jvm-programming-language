public class ASTId implements ASTNode {
    String id;
    public int eval(Environment e) { return e.find(id); }
    public void compile(CodeBlock block){
        throw new RuntimeException("Not implemented");
    }
    public ASTNeg(String id) {
        this.id = id;
    }
}