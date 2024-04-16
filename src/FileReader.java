import java.io.File;
import java.nio.file.Files;

public class FileReader {
    private static File file;
    private static String fileContent;
    private static int currPos = 0;

    public FileReader(String fn) {
        file = new File(STR."src/\{fn}");
        try {
            fileContent = Files.readString(file.toPath());
        } catch (Exception e) {
            Error(e);
        }
    }

    public static char getNext() {
        char op;

        op = fileContent.charAt(currPos);

        if (currPos < fileContent.length() - 1) {
            currPos++;
        } else {
            isEOF();
        }

        return op;
    }

    public static void isEOF() {
        System.out.println("End of file reached.");
    }

    public static void Error(Exception e) {
        System.out.println("Error found: " + e);
    }

}
