package environment;

import ast.ASTNode;
import codeblock.CodeBlock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class FrameManager {
    private static final String HEADER =
            """
                    .class public %s
                    .super java/lang/Object
                    """;
    private static final String NULL_TYPE = "java/lang/Object";
    private static final String SL_FIELD = ".field public sl L%s;";
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
                    putfield %s/sl L%s;
                    astore_3""";

    private static final String LOAD = "aload_3";
    private static final String STORE = "astore_3";
    private static final String END_ASSIGN = "putfield %s/v%d I";
    private static final String GET_VAR = "getfield %s/v%d I";
    private static final String GET_PARENT = "getfield %s/sl L%s;";

    private List<Frame> frames;

    private Frame current;

    public FrameManager() {
        frames = new ArrayList<>();
    }

    public void dumpAll(String path) throws FileNotFoundException {
        for(Frame frame : frames){
            PrintStream out = new PrintStream(path + "/" + frame.getName() + ".j");
            dump(frame, out);
        }
    }

    private void dump(Frame frame, PrintStream stream){
        String name = frame.getName();
        stream.printf(HEADER, name);
        stream.println();
        String parent = frame.getParentFrame() == null ? NULL_TYPE : frame.getParentFrame().getName();
        stream.printf(SL_FIELD, parent);
        stream.println();
        for (int i = 0; i < frame.getVarsLength(); i++) {
            stream.printf(VAR_FIELD, i);
            stream.println();
        }
        stream.println(INIT);
    }

    public void beginScope(CodeBlock block){
        String parent = current == null ? NULL_TYPE : current.getName();
        current = new Frame(frames.size(), current);
        frames.add(current);
        String name = current.getName();
        block.emit(String.format(SCOPE_INIT, name, name, name, parent));
    }

    public void endScope(CodeBlock block){
        if(current.getParentFrame()==null){
            current = null;
            return; //skip parent referencing for root frames
        }
        String name = current.getName();
        String parent = current.getParentFrame().getName();
        block.emit(LOAD);
        block.emit(String.format(GET_PARENT, name, parent));
        block.emit(STORE);
        current = current.getParentFrame();
    }

    public void emitAssign(CodeBlock block, String varName, ASTNode expression){
        String name = current.getName();
        block.emit(LOAD);
        expression.compile(this, block);
        int varNumber = current.addDeclaration(varName);
        block.emit(String.format(END_ASSIGN, name, varNumber));
    }

    public void emitGetValue(CodeBlock block, String varName){
        block.emit(LOAD);
        FrameVariable variable = current.find(varName);
        if(variable == null){
            throw new InvalidIdentifierException(varName);
        }
        Frame frame = current;
        while (frame != variable.getFrame()) {
            String name = frame.getName();
            Frame parentFrame = frame.getParentFrame();
            assert parentFrame != null; //variable would've been null
            String parent = parentFrame.getName();
            block.emit(String.format(GET_PARENT, name, parent));
            frame = parentFrame;
        }
        String name = frame.getName();
        block.emit(String.format(GET_VAR, name, variable.getId()));
    }
}
