package environment;

import ast.ASTNode;
import codeblock.CodeBlock;

import java.io.PrintStream;

public class Frame {
    private static final String NAME_FORMAT = "frame_%d";
    private static final String HEADER =
            """
                    .class public %s
                    .super java/lang/Object
                    """;
    private static final String NULL_TYPE = "Ljava/lang/Object;";
    private static final String SL_FIELD = ".field public sl L%sd;";
    private static final String VAR_FIELD = ".field public v%d I";

    private static final String INIT =
            """
                    .method public <init>()V
                    aload_0
                    invokenonvirtual java/lang/Object/<init>()V
                    return
                    .end method""";

    private static final String SCOPE_INIT =
            """
                    new %s
                    dup
                    invokespecial %s/<init>()V
                    dup
                    aload_3
                    putfield %s/sl Ljava/lang/Object;
                    astore_3""";

    private static final String LOAD = "aload_3";
    private static final String END_ASSIGN = "putfield %s/v%d I";
    private static final String GET_VAR = "getfield %s/v%d I";
    private static final String GET_PARENT = "getfield %s/sl L%s;";

    private int id;
    private int numVars;
    private Frame parentFrame;

    public Frame(int id, int numVars, Frame parentFrame) {
        this.id = id;
        this.numVars = numVars;
        this.parentFrame = parentFrame;
    }

    public String getName(){
        return String.format(NAME_FORMAT, id);
    }

    private void dump(PrintStream stream){
        String name = getName();
        stream.printf(HEADER, name);
        stream.println();
        String parent = parentFrame == null ? NULL_TYPE : parentFrame.getName();
        stream.printf(SL_FIELD, parent);
        stream.println();
        for (int i = 0; i < numVars; i++) {
            stream.printf(VAR_FIELD, i);
            stream.println();
        }
        stream.println(INIT);
    }

    public void emitInitialization(CodeBlock block){
        String name = getName();
        block.emit(String.format(SCOPE_INIT, name, name, name));
    }

    public void emitAssign(CodeBlock block, int varNumber, ASTNode expression){
        String name = getName();
        block.emit(LOAD);
        expression.compile(block);
        block.emit(String.format(END_ASSIGN, name, varNumber));
    }

    private void emitGetValueRec(CodeBlock block, FrameVariable frameVariable) {
        String name = getName();
        if(this == frameVariable.getFrame()){
            block.emit(String.format(GET_VAR, name, frameVariable.getId()));
        } else {
            String parent = parentFrame.getName();
            block.emit(String.format(GET_PARENT, name, parent));
            parentFrame.emitGetValueRec(block, frameVariable);
        }
    }

    public void emitGetValue(CodeBlock block, FrameVariable variable){
        block.emit(LOAD);
        emitGetValueRec(block, variable);
    }
}
