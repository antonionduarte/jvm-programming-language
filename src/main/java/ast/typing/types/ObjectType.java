package ast.typing.types;

import compilation.CompilerUtils;

public abstract class ObjectType implements IType {

    public abstract String getClassName();
    @Override
    public String getJvmId() {
        return CompilerUtils.toReferenceType(getClassName());
    }
}
