package Day7;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Part7_1 {

    public static void main(String[] args) {
        try{
            InputStream s = new FileInputStream("src/main/resources/day7.txt");
            long startTime = System.nanoTime();


            System.out.println("Answer: " + solve(s));


            long endTime = System.nanoTime();
            System.out.println("Solved in " + ((endTime - startTime) / Math.pow(10, 9)) + " seconds");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int solve(InputStream s) {
        int maxSignal = 0;

        int[] code = opcodes(s);
        int[] inputs = new int[]{0, 1, 2, 3, 4};
        ArrayList<ArrayList<Integer>> permutations = permute(inputs);

        for (int i = 0; i < 120; i++) {
            int output = 0;
            for (int j = 0; j < 5; j++) {
                int[] newCode = code.clone();
                int[] parameters = new int[]{permutations.get(i).get(j), output};
                output = run(newCode, parameters);
            }

            if (output > maxSignal) maxSignal = output;
        }
        return maxSignal;
    }

    public static int run(int[] program, int[] inputs) {
        int pointer = 0;
        String parameterMode;
        int result = 0;

        int input = 0;

        while(true) {
            // Get operation
            String opCode = program[pointer] + "";
            int operation;
            if (opCode.length() == 1) {
                operation = program[pointer];
                parameterMode = "";
            } else {
                operation = Integer.parseInt(opCode.substring(opCode.length() - 2));
                parameterMode = opCode.substring(0, opCode.length() - 2);
            }



            switch (operation) {
                case 1: // SUM
                    program[program[pointer + 3]] = getParameter(parameterMode, program, 1, pointer) + getParameter(parameterMode, program, 2, pointer);
                    pointer += 4;
                    break;
                case 2: // MULTIPLICATION
                    program[program[pointer + 3]] = getParameter(parameterMode, program, 1, pointer) * getParameter(parameterMode, program, 2, pointer);
                    pointer += 4;
                    break;
                case 3: // INPUT
                    program[program[pointer + 1]] = inputs[input];
                    input++;
                    pointer += 2;
                    break;
                case 4: // OUTPUT
                    result = getParameter(parameterMode, program, 1, pointer);
                    pointer += 2;
                    break;
                case 5: // JUMP IF TRUE
                    if (getParameter(parameterMode, program, 1, pointer) != 0)
                        pointer = getParameter(parameterMode, program,2, pointer);
                    else pointer += 3;

                    break;
                case 6: // JUMP IF FALSE
                    if (getParameter(parameterMode, program, 1, pointer) == 0)
                        pointer = getParameter(parameterMode, program,2, pointer);
                    else pointer += 3;
                    break;
                case 7: // IF LESS
                    if (getParameter(parameterMode, program, 1, pointer) < getParameter(parameterMode, program, 2, pointer)) program[program[pointer + 3]] = 1;
                    else program[program[pointer + 3]] = 0;
                    pointer += 4;
                    break;
                case 8: // IF EQUAL
                    if (getParameter(parameterMode, program, 1, pointer) == getParameter(parameterMode, program, 2, pointer)) program[program[pointer + 3]] = 1;
                    else program[program[pointer + 3]] = 0;
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


    @SuppressWarnings("SuspiciousListRemoveInLoop")
    public static ArrayList<ArrayList<Integer>> permute(int[] num) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());

        for (int value : num) {
            ArrayList<ArrayList<Integer>> current = new ArrayList<>();
            for (ArrayList<Integer> l : result) {
                for (int j = 0; j < l.size() + 1; j++) {
                    l.add(j, value);
                    ArrayList<Integer> temp = new ArrayList<>(l);
                    current.add(temp);
                    l.remove(j);
                }
            }

            result = new ArrayList<>(current);
        }

        return result;
    }



}
