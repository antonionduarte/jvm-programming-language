import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;

public class CodeBlock {
    private List<String> code = new ArrayList<>();
    public void emit(String opcode){
        code.add(opcode);
    }
    public void dump(PrintStream f) { 
        for(String instruction : code){
            f.println(instruction);
        }
    }
}