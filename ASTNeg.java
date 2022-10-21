public class ASTNeg implements ASTNode {
    ASTNode inner;
    public int eval(Environment e) { return -inner.eval(e); }
    public void compile(CodeBlock block){
        inner.compile(block);
        block.emit("sipush -1");
        block.emit("imul");
    }
    public ASTNeg(ASTNode n) {
        inner = n;
    }
}

