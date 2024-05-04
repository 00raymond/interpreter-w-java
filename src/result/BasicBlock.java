package result;

import java.util.ArrayList;

public class BasicBlock {
    private ArrayList<String> instructions = new ArrayList<>();
    private ArrayList<String> symbols = new ArrayList<>(); // string is var name
    private boolean isBranch = false; // if condition false, branch left, else branch right

    public BasicBlock() {

    }

    public void addInstruction(String instruction) {
        instructions.add(instruction);
    }

    public void addSymbol(String symbol) {
        symbols.add(symbol);
    }




}
