package Day2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Part2_2 {

    public static void main(String[] args) {
        try{
            InputStream s = new FileInputStream("src/main/resources/day2.txt");
            long startTime = System.nanoTime();
            System.out.println("Answer: " + solution(s));
            long endTime = System.nanoTime();
            System.out.println("Solved in " + ((endTime - startTime) / 100000000.0f) + " seconds");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }



    public static int solution(InputStream input) {
        Scanner sc = new Scanner(input);
        StringBuilder sb = new StringBuilder();
        while(sc.hasNextLine()) {
            sb.append(sc.nextLine());
        }
        sc.close();
        String inputStr = sb.toString();
        String[] opcodeStr = inputStr.split(",");

        int[] opcode = new int[opcodeStr.length];
        for (int i = 0; i < opcodeStr.length; i++) {
            opcode[i] = Integer.parseInt(opcodeStr[i]);
        }
        // Fix error:
        opcode[1] = 12;
        opcode[2] = 2;

        for (int a = 0; a < 100; a++) {
            for (int b = 0; b < 100; b++) {
                int[] opcodeNew = opcode.clone();
                opcodeNew[1] = a;
                opcodeNew[2] = b;
                if (calculateOpcodeResult(opcodeNew) == 19690720) {
                    return 100 * a + b;
                }
            }
        }
        return -1;




    }

    private static int calculateOpcodeResult(int[] opcode) {
        int i = 0;
        loop: while (i + 3 < opcode.length) {
            int operation = (opcode[i]);
            switch (operation) {
                case 1:
                    opcode[opcode[i + 3]] = opcode[opcode [i + 1]] + opcode[opcode[i + 2]];
                    i += 4;
                    break;
                case 2:
                    opcode[opcode[i + 3]] = opcode[opcode [i + 1]] * opcode[opcode[i + 2]];
                    i += 4;
                    break;
                case 99:
                    break loop;
                default:
                    System.out.println("Unknown opcode!");
                    break loop;
            }
        }
        return opcode[0];
    }


}
