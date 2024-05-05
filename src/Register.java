import result.Result;

public class Register {

    // R29 and 30 are used as register stack pointers

    public static Result Compute(int operator, Result x, Result y) {
        String op = Tokenizer.Id2String(operator);

        System.out.println("current expression:" + x.getValue() + " " + op + " " + y.getValue());
        System.out.println("x kind: " + x.getKind() + " y kind: " + y.getKind() + "\n");

        String ssaOp = switch (op) {
            case "+" -> "add";
            case "-" -> "sub";
            case "*" -> "mul";
            case "/" -> "div";
            default -> "";
        };

        Result z = new Result();

        z.setInstructionSp(SSA.addInstruction(ssaOp, x, y));


        if (y.getKind() == 0 && x.getKind() == 0) {
            z.setKind(0);
            z.setValue(switch (op) {
                case "+" -> x.getValue() + y.getValue();
                case "-" -> x.getValue() - y.getValue();
                case "*" -> x.getValue() * y.getValue();
                case "/" -> x.getValue() / y.getValue();
                default -> 0;
            });
            System.out.println("performed z switch: " + z.getValue());
        } else {

            if (y.getKind() == 0) {

            } else {

            }
        }
        return z;
    }

    public static int AllocateReg() {
        // allocate a register
        return 0;
    }

    public static void load(Result x) {
        if (x.getKind() == 1) {
            x.setRegno(AllocateReg());
            // putF1 LDW
            x.setKind(2);

        } else if (x.getKind() == 0) {
            if (x.getValue()==0) x.setRegno(0);
            else {
                x.setRegno(AllocateReg());
                // putF1 ADDI
            }
            x.setKind(2);
        }
    }

}
