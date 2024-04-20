import java.util.HashMap;

public class Parser {
    private static int currTk;
    private static HashMap<String, Integer> varTable = new HashMap<>();
    private static String tkStr;

    public static void next() {
        currTk = Tokenizer.getNext();
        tkStr = Tokenizer.Id2String(currTk);
    }

    public static int number() {
        return Tokenizer.getNumber();
    }

    public static int identifier() {
        return varTable.get(tkStr);
    }

    public static int factor() throws Exception {
        int result;

        if (varTable.containsKey(tkStr)) { // need to check if the tkStr exists in the varTable. then use the identifiers numerical value
            result = identifier();
            next();
        } else if (onlyDigits(tkStr)) {
            result = number();
            next();
        } else if (tkStr.equals("(")) {
            next();
            result = expression();

            if (!tkStr.equals(")")) {
                syntaxError();
            } else {
                next();
            }
        } else {
            syntaxError();
            return 0;
        }
        return result;
    }

    public static int term() throws Exception {
        int result;
        result = factor();

        while (tkStr.equals("*") | tkStr.equals("/")) {
            if (tkStr.equals("*")) {
                next();
                result *= factor();
            } else {
                next();
                result /= factor();
            }
        }
        return result;
    }

    public static int expression() throws Exception {
        int result;
        result = term();
        while (tkStr.equals("+") || tkStr.equals("-")) {
            if (tkStr.equals("+")) {
                next();
                result += term();
            }
            else {
                next();
                result -= term();
            }
        }
        return result;
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
            int result = expression();

            // store result in varName
            varTable.put(varName, result);

            // if getNext isnt . , then run computation again
            if (!tkStr.equals(".")) {
                computation();
            }
        } else {
            // must be an expression
            // we print these out
            int result = expression();
            System.out.println(result);

            if (Tokenizer.isEOF()) {
                return;
            }

            if (!tkStr.equals(".")) {
                computation();
            }
        }
    }

    public static void syntaxError() throws Exception {
        throw new Exception(STR."Syntax error at character: \{tkStr}");
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
