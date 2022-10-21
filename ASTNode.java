public interface ASTNode {
    int eval(Environment e);
    void compile(CodeBlock block);
}