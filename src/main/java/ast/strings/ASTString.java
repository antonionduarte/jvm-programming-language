package ast.strings;

import ast.ASTNode;
import ast.typing.types.IType;
import ast.typing.types.StringType;
import ast.typing.values.IValue;
import ast.typing.values.StringValue;
import compilation.CodeBlock;
import compilation.CompilerUtils;
import environment.Environment;
import environment.Frame;

public class ASTString implements ASTNode {

    private final String value;
    private final String escaped;

    public ASTString(String escaped) {
        this.escaped = escaped;
        this.value = parseEscapes(escaped);
    }

    public static String parseEscapes(String rawValue){
        StringBuilder builder = new StringBuilder(rawValue.length());
        for(int i = 1; i < rawValue.length() - 1; i++) {
            char c = rawValue.charAt(i);
            if (c == '\\') {
                char escaped = rawValue.charAt(++i);
                switch (escaped) {
                    case 't' -> builder.append('\t');
                    case 'n' -> builder.append('\n');
                    case 'r' -> builder.append('\r');
                    case '"' -> builder.append('"');
                    case '\\' -> builder.append('\\');
                    default ->
                            throw new RuntimeException("Invalid escape character: \\" + escaped);
                }
            } else{
                builder.append(c);
            }
        }
        return builder.toString();

    }

    @Override
    public IValue eval(Environment<IValue> environment) {
        return new StringValue(value);
    }

    @Override
    public IType compile(Frame frame, CodeBlock codeBlock) {
        codeBlock.emit(CompilerUtils.pushString(escaped, false));
        return StringType.Instance;
    }

    @Override
    public IType typeCheck(Environment<IType> environment) {
        return StringType.Instance;
    }
}
