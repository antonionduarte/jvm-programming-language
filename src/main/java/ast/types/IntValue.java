package ast.types;

public class IntValue implements IValue {
    private final int value;

    public static int asInt(IValue value){
        if (value instanceof IntValue){
            return ((IntValue) value).getValue();
        } else {
            throw new TypeMismatchException(ValueType.Int, value.getType());
        }
    }

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }

    @Override
    public ValueType getType() {
        return ValueType.Int;
    }

    @Override
    public boolean equals(Object other){
        return other instanceof IntValue && ((IntValue) other).getValue() == this.getValue();
    }
}
