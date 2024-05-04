import result.BasicBlock;

import java.util.ArrayList;

public class SSA {

    private static ArrayList<BasicBlock> basicBlocks = new ArrayList<>();

    public SSA() {
        BasicBlock consts = new BasicBlock();

    }
    public static void addInstruction(String op, String x, String y, String z) {

    }

    public static void putF1(String op, String x, String y) {
        System.out.println("putF1 " + x + " " + y);
    }

}
