public class ASTNum implements ASTNode {
    int val;
    public int eval(Environment ignored) { return val; }
    public void compile(CodeBlock block){
        block.emit(String.format("sipush %d", val));
    }
    public ASTNum(int n)
    {
        val = n;
    }
}

