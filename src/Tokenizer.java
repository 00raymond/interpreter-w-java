
import java.io.File;
import java.util.HashMap;
import java.util.Vector;

public class Tokenizer {
    private static char ch = FileReader.getNext();
    // need to map current variables to their respective integer values
    private static Vector<String> idTable = new Vector<>();
    private static int number;
    private static int identifier;
    private static boolean EOF = false;

    public static void printIdTable() {
        for (int i = 0; i < idTable.size(); i++) {
            System.out.println(i + ": " + idTable.elementAt(i));
        }
    }

    public static void next() {
        ch = FileReader.getNext();
    }

    public static int getNext() {

        if (ch == ' ' || ch == '\n' || ch == '\t' || ch == '\r') {
            next();
            return getNext();
        }

        if (ch >= 'a' && ch <= 'z') {
            return ident();
        }

        if (ch >= '0' && ch <= '9') {
            return number();
        }

        if (ch == '<') {
            next();
            if (ch == '-') {
                return assignmentOperator();
            }
        }

        if (ch == '*' || ch == '-' || ch == '/' || ch == '+' || ch == ';' || ch == '(' || ch == ')') {
            return operator();
        }

        if (ch == ',') {
            next();
            return String2Id(",");
        }

        if (ch == '{') {
            next();
            return String2Id("{");
        }

        if (ch == '}') {
            next();
            return String2Id("}");
        }

        if (ch == '.') {
            EOF = true;
            return String2Id(".");
        }

        return 0;
    }

    public static int assignmentOperator() {
        next();
        return String2Id("<-");
    }

    public static int operator() {
        char op = ch;
        next();
        return String2Id(String.valueOf(op));
    }

    public static int number() {
        int val = 0;
        do { val = 10 * val + (ch - '0'); next(); }
        while (ch >= '0' && ch <= '9');

        number = val;
        return String2Id(String.valueOf(val));
    }

    static int ident() {
        int i;
        StringBuffer str = new StringBuffer();

        i = 0;
        do {
            str.append(ch); i++; next();
        } while ( ((ch >= '0') && (ch <= '9')) || ((ch >= 'a') && (ch <= 'z')) );

        return String2Id(String.valueOf(str));
    }

    public static int String2Id(String str) {
        for (int i = 0; i < idTable.size(); i++) {
            String s = idTable.elementAt(i);
            if (s.equals(str)) {
                // identifier already exists in id table
                identifier = i;
                return i;
            }
        }

        idTable.add(str);

        identifier = idTable.size() - 1;
        return idTable.size() - 1;
    }

    public static String Id2String(int id) {
        return idTable.elementAt(id);
    }

    public static int getNumber() {
        return number;
    }

    public static boolean isEOF() {
        return EOF;
    }

}
