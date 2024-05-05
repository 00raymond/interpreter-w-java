package bb;

import java.util.ArrayList;
import java.util.HashMap;

public class BasicBlock {
    private ArrayList<String> instructions = new ArrayList<>();
    private HashMap<String, Integer> varTable = new HashMap<>();
    private boolean isBranch = false; // if condition false, branch left, else branch right

    public BasicBlock() {

    }

    public void addInstruction(String instruction) {
        instructions.add(instruction);
    }

    public void addSymbol(String symbol, int instructionNo) {
        varTable.put(symbol, instructionNo);
    }

    public HashMap<String, Integer> getVarTable() {
        return varTable;
    }

    public ArrayList<String> getInstructions () {
        return instructions;
    }


}
