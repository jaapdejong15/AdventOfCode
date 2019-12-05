package Day2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Part2_1 {

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
