public class ASTPlus implements ASTNode {
    ASTNode lhs, rhs;
    public int eval(Environment e) {
        int v1 = lhs.eval(e);
        int v2 = rhs.eval(e);
        return v1+v2; 
    }
    public void compile(CodeBlock block){
        lhs.compile(block);
        rhs.compile(block);
        block.emit("iadd");
    }
    
    public ASTPlus(ASTNode l, ASTNode r)
    {
        lhs = l; rhs = r;
    }
}

