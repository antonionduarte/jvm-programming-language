package ast.flow;
import ast.ASTNode;
import ast.typing.types.Type;
import ast.typing.types.ValueType;
import ast.typing.values.BoolValue;
import ast.typing.values.IValue;
import ast.typing.values.VoidValue;
import compilation.CodeBlock;
import compilation.CompilerUtils;
import environment.Environment;
import environment.Frame;

public class ASTIf implements ASTNode {
    private ASTNode condition, bodyIf, bodyElse;

    public ASTIf(ASTNode condition, ASTNode bodyIf, ASTNode bodyElse) {
        this.condition = condition;
        this.bodyIf = bodyIf;
        this.bodyElse = bodyElse;
    }

    @Override
    public IValue eval(Environment<IValue> environment) {
        boolean condVal = BoolValue.asBoolean(condition.eval(environment));
        if(condVal){
            IValue val = bodyIf.eval(environment);
            if(bodyElse!=null)
                return val;
            else return VoidValue.getInstance();
        } else if(bodyElse != null){
            return bodyElse.eval(environment);
        } else {
            return VoidValue.getInstance();
        }
    }

    @Override
    public ValueType compile(Frame frame, CodeBlock codeBlock) {
        condition.compile(frame, codeBlock);
        CodeBlock.DelayedOp gotoElse = codeBlock.delayEmit();
        ValueType ifType = bodyIf.compile(frame, codeBlock);
        CodeBlock.DelayedOp popIf = codeBlock.delayEmit();
        popIf.set(CompilerUtils.comment(CompilerUtils.DISCARD));
        CodeBlock.DelayedOp skipElse = codeBlock.delayEmit();
        skipElse.set(CompilerUtils.comment(" ;goto after else"));
        String label = codeBlock.emitLabel();
        ValueType elseType = null;
        CodeBlock.DelayedOp popElse = null;
        if(bodyElse != null) {
            elseType = bodyElse.compile(frame, codeBlock);
            popElse = codeBlock.delayEmit();
            popElse.set(CompilerUtils.comment(CompilerUtils.DISCARD));
            skipElse.set(CompilerUtils.gotoAlways(codeBlock.emitLabel()));
        }
        gotoElse.set(CompilerUtils.gotoIfFalse(label));

        //if this if does not return a value, it is required to discard the values
        //from the stack
        boolean isVoid = elseType == null || !elseType.equals(ifType);
        if(isVoid && ifType.getType() != Type.Void){
            popIf.set(CompilerUtils.DISCARD);
        }
        if(isVoid && elseType != null && elseType.getType() != Type.Void) {
            popElse.set(CompilerUtils.DISCARD);
        }

        return  isVoid ? new ValueType(Type.Void) : ifType;
    }

    @Override
    public ValueType typeCheck(Environment<ValueType> environment) {
        condition.typeCheck(environment).expect(new ValueType(Type.Bool));
        if(bodyElse == null){
            return new ValueType(Type.Void);
        } else {
            ValueType ifType = bodyIf.typeCheck(environment);
            ValueType elseType = bodyElse.typeCheck(environment);
            elseType.expect(ifType);
            return elseType.equals(ifType) ? ifType : new ValueType(Type.Void);
        }
    }
}
