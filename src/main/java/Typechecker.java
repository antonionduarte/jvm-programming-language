import ast.typing.types.IType;
import ast.typing.values.IValue;
import environment.Environment;
import parser.Parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Typechecker {
    /**
     * Main entry point.
     */
    public static void main(String[] args) throws IOException {
        String path = null;

        if (args.length == 1) {
            path = args[0];
        }

        InputStream in = path == null ? System.in : new FileInputStream(path);

        new Parser(in);
        Environment<IType> top = new Environment<>();
        int errors = 0;
        while (path == null || in.available() > 0) {
            try {
                var exp = Parser.Start();
                exp.typeCheck(top);
                //System.out.println(exp.eval(new Environment<>()).toString());
            } catch (Exception e) {
                System.out.println("Syntax Error!");
                System.out.println(e.getMessage());
                errors++;
                Parser.ReInit(System.in);
            }
        }
        System.out.println(errors + " errors found.");
    }
}
