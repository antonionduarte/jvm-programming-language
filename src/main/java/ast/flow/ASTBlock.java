package ast.flow;

import ast.ASTNode;
import ast.typing.types.Type;
import ast.typing.types.ValueType;
import ast.typing.values.IValue;
import ast.typing.values.VoidValue;
import compilation.CodeBlock;
import compilation.CompilerUtils;
import compilation.FrameCompiler;
import environment.Environment;
import environment.Frame;
import environment.FrameVariable;

import java.util.List;

public class ASTBlock implements ASTNode {
    private List<ASTNode> instructions;

    public ASTBlock(List<ASTNode> instructions){
        this.instructions = instructions;
    }
    @Override
    public IValue eval(Environment<IValue> environment) {
        var inner = environment.beginScope();
        IValue returnVal = VoidValue.getInstance();
        for (ASTNode instruction : instructions){
            returnVal = instruction.eval(inner);
        }
        inner.endScope();
        return returnVal;
    }

    @Override
    public ValueType compile(Frame frame, CodeBlock codeBlock) {
        Frame inner = frame.beginScope();
        FrameCompiler.emitBeginScope(codeBlock, inner);
        ValueType returnType = new ValueType(Type.Void);
        for (var instruction : instructions) {
            //discard result of last instruction to clear the stack
            if(returnType.getType() != Type.Void) codeBlock.emit(CompilerUtils.DISCARD);
            codeBlock.emit("\n" + CompilerUtils.comment("instruction"));
            returnType = instruction.compile(frame, codeBlock);
        }
        FrameCompiler.emitEndScope(codeBlock, inner);
        return returnType;
    }

    @Override
    public ValueType typeCheck(Environment<ValueType> environment) {
        return null;
    }
}
