package ast;

import ast.typing.types.ValueType;
import ast.typing.values.IValue;
import ast.typing.values.VoidValue;
import compilation.CodeBlock;
import compilation.FrameCompiler;
import environment.Environment;
import environment.Frame;
import environment.FrameVariable;

import java.util.List;

public class ASTBlock implements ASTNode{
    private List<ASTNode> instructions;
    private ASTNode returnNode;

    public ASTBlock(List<ASTNode> instructions, ASTNode returnNode){
        this.instructions = instructions;
        this.returnNode = returnNode;
    }
    @Override
    public IValue eval(Environment<IValue> environment) {
        var inner = environment.beginScope();
        for (ASTNode instruction : instructions){
            instruction.eval(inner);
        }
        IValue returnVal;
        if(returnNode != null) {
            returnVal = returnNode.eval(inner);
        } else {
            returnVal = VoidValue.getInstance();
        }
        inner.endScope();
        return returnVal;
    }

    @Override
    public ValueType compile(Frame frame, CodeBlock codeBlock) {
        Frame inner = frame.beginScope();
        FrameCompiler.emitBeginScope(codeBlock, inner);
        for (var instruction : instructions) {
            instruction.compile(frame, codeBlock);
            //TODO pop value if not void?
        }
        ValueType returnType = returnNode.compile(inner, codeBlock);
        FrameCompiler.emitEndScope(codeBlock, inner);
        return returnType;
    }

    @Override
    public ValueType typeCheck(Environment<ValueType> environment) {
        return null;
    }
}
