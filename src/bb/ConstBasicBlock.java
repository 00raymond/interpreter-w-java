package bb;

import java.util.ArrayList;

public class ConstBasicBlock extends BasicBlock {
    private ArrayList<Integer> constValues = new ArrayList<>();

    public void addInstruction(String instruction, int value) {
        super.addInstruction(instruction);
        constValues.add(value);
    }

    public ArrayList<Integer> getConstValues() {
        return constValues;
    }

    public int addConstValue(int value) {
        constValues.add(value);
        return constValues.size() - 1;
    }

}
