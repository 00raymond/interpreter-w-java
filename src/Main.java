// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

//        if (args.length == 0) {
//            System.out.println("No file name provided.");
//            return;
//     }

//        String fn = args[0];
        String fn = "Test.txt";
        new FileReader(fn);

        // tokenizer tests
//        for (int i = 0; i < 8; i++) {
//            int id = Tokenizer.getNext();
//            System.out.println(Tokenizer.Id2String(id));
//        }

        if (!Tokenizer.Id2String(Tokenizer.getNext()).equals("computation")) {
            System.out.println("Error: Program does not begin with computation.");
        } else {
            try {
                Parser.computation();
            } catch (Exception e) {
                FileReader.Error(e);
            }

        }

    }
}