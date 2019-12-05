package Day5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Part5_2 {

    public static void main(String[] args) {
        try{
            InputStream s = new FileInputStream("src/main/resources/day5.txt");
            long startTime = System.nanoTime();
            System.out.println("Answer: " + solution(s));
            long endTime = System.nanoTime();
            System.out.println("Solved in " + ((endTime - startTime) / 100000000.0f) + " seconds");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int solution(InputStream input) {
        int[] data = opcodes(input);
        int pointer = 0;
        String parameterMode;
        int result = 0;

        while(true) {
            // Get operation
            String opCode = data[pointer] + "";
            int operation;
            if (opCode.length() == 1) {
                operation = data[pointer];
                parameterMode = "";
            } else {
                operation = Integer.parseInt(opCode.substring(opCode.length() - 2));
                parameterMode = opCode.substring(0, opCode.length() - 2);
            }


            switch (operation) {
                case 1: // SUM
                    data[data[pointer + 3]] = getParameter(parameterMode, data, 1, pointer) + getParameter(parameterMode, data, 2, pointer);
                    pointer += 4;
                    break;
                case 2: // MULTIPLICATION
                    data[data[pointer + 3]] = getParameter(parameterMode, data, 1, pointer) * getParameter(parameterMode, data, 2, pointer);
                    pointer += 4;
                    break;
                case 3: // INPUT
                    data[data[pointer + 1]] = 5;
                    pointer += 2;
                    break;
                case 4: // OUTPUT
                    result = getParameter(parameterMode, data, 1, pointer);
                    pointer += 2;
                    break;
                case 5: // JUMP IF TRUE
                    if (getParameter(parameterMode, data, 1, pointer) != 0)
                        pointer = getParameter(parameterMode, data,2, pointer);
                    else pointer += 3;

                    break;
                case 6: // JUMP IF FALSE
                    if (getParameter(parameterMode, data, 1, pointer) == 0)
                        pointer = getParameter(parameterMode, data,2, pointer);
                    else pointer += 3;
                    break;
                case 7: // IF LESS
                    if (getParameter(parameterMode, data, 1, pointer) < getParameter(parameterMode, data, 2, pointer)) data[data[pointer + 3]] = 1;
                    else data[data[pointer + 3]] = 0;
                    pointer += 4;
                    break;
                case 8: // IF EQUAL
                    if (getParameter(parameterMode, data, 1, pointer) == getParameter(parameterMode, data, 2, pointer)) data[data[pointer + 3]] = 1;
                    else data[data[pointer + 3]] = 0;
                    pointer += 4;
                    break;
                case 99:
                    return result;
                default:
                    System.out.println("Unknown opcode");
                    return -1;
            }
        }
    }

    private static int getParameter(String parameterMode, int[] data, int parameter, int pointer) {
        int mode = 0;
        if (parameter <= parameterMode.length()) {
            mode = Integer.parseInt(parameterMode.charAt(parameterMode.length() - parameter) + "");
        }
        if (mode == 0) {
            return data[data[pointer + parameter]];
        } else {
            return data[pointer + parameter];
        }
    }

    private static int[] opcodes(InputStream in) {
        Scanner sc = new Scanner(in);
        StringBuilder sb = new StringBuilder();
        while(sc.hasNextLine()) {
            sb.append(sc.nextLine());
        }
        sc.close();
        String inputStr = sb.toString();
        String[] opcodeStr = inputStr.split(",");
        int[] opcodes = new int[opcodeStr.length];
        for (int i = 0; i < opcodeStr.length; i++) {
            opcodes[i] = Integer.parseInt(opcodeStr[i]);
        }
        return opcodes;
    }


}
