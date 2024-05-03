import java.util.HashMap;
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

    public static void computation() throws Exception {

        next();
        if (Tokenizer.Id2String(currTk).equals("var")) {
            // variable declaration
            next(); // consume "var", next tk will be identifier name
            String varName = Tokenizer.Id2String(currTk);
            next();
            // currTk will be -> guaranteed
            next();
            // now we will be in expression. maybe tokenize expression so we can just run the expression using existing code?
            // or just run expression on the current token until it reaches ;, since when there is var there will have to be ; at end
            Result x = expression();

            // store result in varName
            varTable.put(varName, x.getValue());

            // if getNext isnt . , then run computation again
            if (!tkStr.equals(".")) {
                computation();
            }
        } else {
            // must be an expression
            // we print these out
            Result x = expression();
            System.out.println(x.getValue());

            if (Tokenizer.isEOF()) {
                return;
            }

            if (!tkStr.equals(".")) {
                computation();
            }
        }
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