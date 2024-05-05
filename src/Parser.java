import java.util.HashMap;
import java.util.Objects;

import result.Result;

public class Parser {
    private static int currTk;
    private static HashMap<String, Integer> varTable = new HashMap<>();
    private static String tkStr;

    public static void next() {
        currTk = Tokenizer.getNext();
        tkStr = Tokenizer.Id2String(currTk);
    }

    public static Result number() {

        return new Result(0, (Tokenizer.getNumber()));
    }

    public static Result identifier() {

        // value will be the index of the identifier in the varTable
        return new Result(1, varTable.get(tkStr));
    }

    public static Result factor() throws Exception {

        Result x;

        if (varTable.containsKey(tkStr)) { // need to check if the tkStr exists in the varTable. then use the identifiers numerical value
            x = identifier();
            next();
        } else if (onlyDigits(tkStr)) {
            x = number();
            next();
        } else if (tkStr.equals("(")) {
            next();
            x = expression();

            if (!tkStr.equals(")")) {
                syntaxError();
            } else {
                next();
            }
        } else {
            syntaxError();
            return new Result(4);
        }
        return x;
    }

    public static Result term() throws Exception {
        Result x, y;

        x = factor();

        while (tkStr.equals("*") | tkStr.equals("/")) {

            System.out.println("this: " + tkStr);
            int op = Tokenizer.String2Id(tkStr);

            next();
            y = factor();
            x = Register.Compute(op, x, y);
        }
        return x;
    }

    public static Result expression() throws Exception {

        Result x, y;
        x = term();

        while (tkStr.equals("+") || tkStr.equals("-")) {

            int op = Tokenizer.String2Id(tkStr);

            next();
            y = term();
            x = Register.Compute(op, x, y);
        }
        return x;
    }

    public static void assignment() throws Exception {

        // this method adds an assignment to current symbolTable
        // the recursive expression call will add individual
        // constants and create an instruction for the symbol to point to if it doesnt exist already

        next(); // consume identifier
        String varName = tkStr;
        // retrieve the value of the variable
        next(); // "consume tkstr"
        next(); // consume "<-"

        Result src = expression();

        SSA.addAssignment(varName, src);

        if (!varTable.containsKey(varName)) {
            System.out.println("Warning: variable " + varName + " not declared before use.");
        }

        varTable.put(varName, src.getValue());
        next();
    }

    public static void varDecl() throws Exception {
        next(); // consume "var"
        String varName = tkStr;
        next(); // consume identifier
        varTable.put(varName, 0); // declared variables will initialize at -1
        System.out.println(varName);
        if (tkStr.equals(";")) {
            next();
        } else {
            varDecl();
        }
    }

    public static void computation() throws Exception {
        // first word was main
        // now at "var"
        next(); // consume "var", now at first variable name
        varDecl(); // now at the next statement
        next();

        while (!tkStr.equals(".")) {
            String statement = Tokenizer.Id2String(currTk);
            switch (statement) {
                case "let" -> {
                    assignment();
                    System.out.println("reached");
                }
                case "call" -> {

                }  // function call
                case "if" -> {
                }
                // if statement
                case "while" -> {
                }
                // while loop
                case "return" -> {
                }
                // return statement
                case "}" -> {
                    System.out.println("here");
                    next();
                }
                default -> {

                }
            }
        }
        SSA.printAll();
    }

    public static void syntaxError() throws Exception {
        throw new Exception("Syntax error at character:" + tkStr);
    }

    public static boolean onlyDigits(String str) {
        for (int i = 0; i < str.length(); i++) {

            if (str.charAt(i) < '0'
                    || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

}