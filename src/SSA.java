import bb.BasicBlock;
import bb.ConstBasicBlock;
import result.Result;

import java.util.ArrayList;
import java.util.HashMap;

public class SSA {

    private static int sp = 0;
    private static int bbp = 1;
    private static ArrayList<BasicBlock> basicBlocks = new ArrayList<>();

    private static HashMap<Integer, Integer> constsMap = new HashMap<>();

    public SSA() {
        ConstBasicBlock consts = new ConstBasicBlock(); // index 0 in basicBlocks array will always have consts
        basicBlocks.add(consts);

        BasicBlock bb = new BasicBlock();
        basicBlocks.add(bb);
    }

    public static int addInstruction(String op, Result x, Result y) {

        // need methods to handle compare, branch, and return

        String instruct = "";
        int ykind = y.getKind();
        int xkind = x.getKind();
        System.out.println(xkind + " " + ykind);
        // check kind of x and y
        if (xkind == 0 || ykind == 0) {

            ConstBasicBlock consts = (ConstBasicBlock) basicBlocks.get(0);
            ArrayList<Integer> constsValues = consts.getConstValues();
            if (ykind == 0 && xkind == 0) {
                // both are constants, search ir for these consts if not found add them
                System.out.println("both consts");
                boolean yfound = false;
                boolean xfound = false;

                int yInstructNo = -1;
                int xInstructNo = -1;

                for (Integer constsValue : constsValues) {
                    if (constsValue.equals(y.getValue())) {
                        yfound = true;
                        yInstructNo = constsMap.get(constsValues.indexOf(constsValue));
                        System.out.println("all here: " + constsMap);
                        System.out.println(yInstructNo + " constant at " + y.getValue());
                    }
                    if (constsValue.equals(x.getValue())) {
                        xfound = true;
                        xInstructNo = constsMap.get(constsValues.indexOf(constsValue));
                        System.out.println(xInstructNo + " constant at " + x.getValue());
                    }
                }

                if (!xfound) {
                    int constInstructNo = consts.addConstValue(x.getValue());
                    // maps the sp to the index of the constant in the constsValues array
                    constsMap.put(constInstructNo, sp);
                    // so now when value is found when searching through constValues, the instruction number is known

                    consts.addInstruction(sp + ": const " + x.getValue());
                    xInstructNo = sp;
                    sp++;
                }
                if (!yfound) {
                    int constInstructNo = consts.addConstValue(y.getValue());
                    constsMap.put(constInstructNo, sp);

                    consts.addInstruction(sp + ": const " + y.getValue());
                    yInstructNo = sp;
                    sp++;
                }

                instruct = sp + ": " + op + " (" + xInstructNo + ") (" + yInstructNo + ")";

            }

//            instruct = getString(op, y, x, instruct, ykind, consts, constsValues);
//
//            instruct = getString(op, x, y, instruct, xkind, consts, constsValues);
        }

        if (xkind == 1 && ykind == 1) {
            // both are vars
            // extract x and y from symbol table
            int tempBbp = bbp;
            int xInstructNo = -1;
            int yInstructNo = -1;
            do {
                BasicBlock tempBb = basicBlocks.get(tempBbp);
                for (String symbol : tempBb.getVarTable().keySet()) {
                    if (symbol.equals(Tokenizer.Id2String(x.getValue()))) {
                        xInstructNo = tempBb.getVarTable().get(symbol);
                    }
                    if (symbol.equals(Tokenizer.Id2String(y.getValue()))) {
                        yInstructNo = tempBb.getVarTable().get(symbol);
                    }
                }
                tempBbp--;
            } while (tempBbp > 1);
            instruct = sp + ": " + op + " (" + xInstructNo + ") (" + yInstructNo + ")";

        }


        basicBlocks.get(bbp).addInstruction(instruct);
        int instructionSp = sp;
        sp++;
        // returns sp so that can add a symbol to the symbol table in current bb
        return instructionSp;
    }

    public static void addAssignment(String var, Result src) {
        // add var to symbol table at current bb pointer
        basicBlocks.get(bbp).addSymbol(var, src.getInstructionSp());

    }

    private static String getString(String op, Result x, Result y, String instruct, int xkind, ConstBasicBlock consts, ArrayList<Integer> constsValues) {
        if (xkind == 0) {
            // x is a constant, y is a var
            boolean found = false;
            int instructNo = -1123;
            for (Integer constsValue : constsValues) {
                if (constsValue.equals(x.getValue())) {
                    found = true;
                    instructNo = constsValues.indexOf(constsValue);
                }
            }
            if (!found) {
                instructNo = consts.addConstValue(x.getValue());
                consts.addInstruction(instructNo + ": const " + x.getValue());
                sp++;
            }
            // handle y var
            // extract y from symbol table
            int tempBbp = bbp;
            int yInstructNo = -1;
            do {
                BasicBlock tempBb = basicBlocks.get(tempBbp);
                for (String symbol : tempBb.getVarTable().keySet()) {
                    if (symbol.equals(Tokenizer.Id2String(y.getValue()))) {
                        yInstructNo = tempBb.getVarTable().get(symbol);
                    }
                }
                tempBbp--;
            } while (tempBbp > 1);
            instruct = sp + ": " + op + " (" + instructNo + ") (" + yInstructNo + ")";
        }
        return instruct;
    }

    public static void shiftBbpUp() {
        bbp++;
    }

    public static void shiftBbpDown() {
        bbp--;
    }

    public static void createBasicBlock() {
        BasicBlock bb = new BasicBlock();
        basicBlocks.add(bb);
    }

    public static void printAll() {
        for (BasicBlock basicBlock : basicBlocks) {
            System.out.println("Basic Block:");
            for (String instruction : basicBlock.getInstructions()) {
                System.out.println(instruction);
            }
            System.out.println("Symbol Table:");
            for (String symbol : basicBlock.getVarTable().keySet()) {
                System.out.println(symbol + " " + basicBlock.getVarTable().get(symbol));
            }
        }
    }



}
