package environment;

public class InvalidIdentifierException extends RuntimeException{
    public InvalidIdentifierException(String varName){
        super("Invalid identifier " + varName);
    }
}
