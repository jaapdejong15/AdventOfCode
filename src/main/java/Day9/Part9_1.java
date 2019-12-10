package Day9;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Part9_1 {

    public static void main(String[] args) {
        try{
            InputStream s = new FileInputStream("src/main/resources/day9.txt");
            long startTime = System.nanoTime();
            System.out.println("Answer: " + solution(s));
            long endTime = System.nanoTime();
            System.out.println("Solved in " + ((endTime - startTime) / Math.pow(10, 9)) + " seconds");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static long solution(InputStream input) {
        long[] data = opcodes(input);
        ArrayList<Long> program = new ArrayList<>();

        for (long i : data) {
            program.add(i);
        }

        int pointer = 0;
        String parameterMode;
        long result = 0;
        int rBase = 0;

        while(true) {
            // Get operation
            String opCode = data[pointer] + "";
            int operation;
            if (opCode.length() == 1) {
                operation = Integer.parseInt(program.get(pointer) + "");
                parameterMode = "";
            } else {
                operation = Integer.parseInt(opCode.substring(opCode.length() - 2));
                parameterMode = opCode.substring(0, opCode.length() - 2);
            }
            long value;
            System.out.println("---PROGRAM DATA---");
            System.out.println("Operation: " + opCode);
            System.out.println("Pointer:   " + pointer);

            switch (operation) {
                case 1: // SUM
                    value = getParameter(parameterMode, program, 1, pointer, rBase) + getParameter(parameterMode, program, 2, pointer, rBase);
                    setParameter(parameterMode, program, 3, pointer, rBase, value);
                    pointer += 4;
                    break;
                case 2: // MULTIPLICATION
                    value = getParameter(parameterMode, program, 1, pointer, rBase) * getParameter(parameterMode, program, 2, pointer, rBase);
                    setParameter(parameterMode, program, 3, pointer, rBase, value);
                    pointer += 4;
                    break;
                case 3: // INPUT
                    setParameter(parameterMode, program, 1, pointer, rBase, 2);
                    pointer += 2;
                    break;
                case 4: // OUTPUT
                    result = getParameter(parameterMode, program, 1, pointer, rBase);
                    System.out.println("RESULT: " + result);
                    pointer += 2;
                    break;
                case 5: // JUMP IF TRUE
                    if (getParameter(parameterMode, program, 1, pointer, rBase) != 0)
                        pointer = (int) getParameter(parameterMode, program,2, pointer, rBase);
                    else pointer += 3;

                    break;
                case 6: // JUMP IF FALSE
                    if (getParameter(parameterMode, program, 1, pointer, rBase) == 0)
                        pointer = (int) getParameter(parameterMode, program,2, pointer, rBase);
                    else pointer += 3;
                    break;
                case 7: // IF LESS
                    if (getParameter(parameterMode, program, 1, pointer, rBase) < getParameter(parameterMode, program, 2, pointer, rBase)) {
                        setParameter(parameterMode, program, 3, pointer, rBase, 1);
                    } else {
                        setParameter(parameterMode, program, 3, pointer, rBase, 0);
                    }
                    pointer += 4;
                    break;
                case 8: // IF EQUAL
                    if (getParameter(parameterMode, program, 1, pointer, rBase) == getParameter(parameterMode, program, 2, pointer, rBase)) {
                        setParameter(parameterMode, program, 3, pointer, rBase, 1);
                    }
                    else {
                        setParameter(parameterMode, program, 3, pointer, rBase, 0);
                    }
                    pointer += 4;
                    break;
                case 9: // ADJUST RELATIVE BASE
                    rBase += (int) getParameter(parameterMode, program, 1, pointer, rBase);
                    System.out.println("Rbase = " + rBase);
                    pointer += 2;
                    break;
                case 99:
                    for (long l : program) {
                        System.out.print(l + " ");
                    }
                    System.out.println();
                    return result;
                default:
                    System.out.println("Unknown opcode!");
                    return -1;
            }
        }
    }

    private static long getParameter(String parameterMode, ArrayList<Long> program, int parameter, int pointer, int rBase) {
        int mode = 0;
        if (parameter <= parameterMode.length()) {
            mode = Integer.parseInt(parameterMode.charAt(parameterMode.length() - parameter) + "");
        }
        int index;
        if (mode == 0) {
            index = Integer.parseInt(program.get(pointer + parameter) + "");
        } else if (mode == 1){
            index = pointer + parameter;
        } else {
            index = rBase + Integer.parseInt(program.get(pointer + parameter) + "");
        }
        checkProgramSize(program, index);
        return program.get(index);
    }

    private static void setParameter(String parameterMode, ArrayList<Long> program, int parameter, int pointer, int rBase, long value) {
        int mode = 0;
        if (parameter <= parameterMode.length()) {
            mode = Integer.parseInt(parameterMode.charAt(parameterMode.length() - parameter) + "");
        }
        int index;
        if (mode == 0) {
            index = Integer.parseInt(program.get(pointer + parameter) + "");
        } else if (mode == 1) {
            index = pointer + parameter;
        } else {
            index = rBase + Integer.parseInt(program.get(pointer + parameter) + "");
        }
        checkProgramSize(program, index);
        program.set(index, value);
    }

    private static void checkProgramSize(ArrayList<Long> program, int size) {
        if (size < program.size()) return;
        while (program.size() <= size) {
            program.add(0L);
        }
    }

    private static long[] opcodes(InputStream in) {
        Scanner sc = new Scanner(in);
        StringBuilder sb = new StringBuilder();
        while(sc.hasNextLine()) {
            sb.append(sc.nextLine());
        }
        sc.close();
        String inputStr = sb.toString();
        String[] opcodeStr = inputStr.split(",");
        long[] opcodes = new long[opcodeStr.length];
        for (int i = 0; i < opcodeStr.length; i++) {
            opcodes[i] = Long.parseLong(opcodeStr[i]);
        }
        return opcodes;
    }


}
