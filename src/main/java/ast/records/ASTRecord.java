package ast.records;

import ast.ASTNode;
import ast.typing.types.IType;
import ast.typing.types.RecordType;
import ast.typing.types.ReferenceType;
import ast.typing.utils.Parameter;
import ast.typing.values.IValue;
import ast.typing.values.RecordValue;
import compilation.CodeBlock;
import compilation.CompilerUtils;
import compilation.RecordManager;
import environment.Environment;
import environment.Frame;
import utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class ASTRecord implements ASTNode {
    private final List<Pair<String, ASTNode>> fields;

    public ASTRecord(List<Pair<String, ASTNode>> fields) {
        this.fields = fields;
    }

    @Override
    public IValue eval(Environment<IValue> environment) {
        List<Pair<String, IValue>> values = new ArrayList<>(this.fields.size());
        for(Pair<String, ASTNode> field : fields){
            IValue value = field.b().eval(environment);
            values.add(new Pair<>(field.a(), value));
        }
        return new RecordValue(values);
    }

    @Override
    public IType compile(Frame frame, CodeBlock codeBlock) {
        CodeBlock.DelayedOp init = codeBlock.delayEmit();
        codeBlock.emit(CompilerUtils.DUPLICATE);
        List<Parameter> parameters = new ArrayList<>(fields.size());
        for(var field : fields){
            String name = field.a();
            ASTNode node = field.b();
            IType type = node.compile(frame, codeBlock);
            parameters.add(new Parameter(name, type));
        }
        RecordType recordType = new RecordType(parameters);
        String className = RecordManager.getInstance().register(recordType);
        init.set("new " + className);
        codeBlock.emit(" invokespecial " + className + "/"
                + RecordManager.initSignature(recordType));
        return recordType;
    }

    @Override
    public IType typeCheck(Environment<IType> environment) {
        List<Parameter> parameters = new ArrayList<>(fields.size());
        for(var field : fields){
            String name = field.a();
            ASTNode node = field.b();
            parameters.add(new Parameter(name, node.typeCheck(environment)));
        }
        return new RecordType(parameters);
    }
}
