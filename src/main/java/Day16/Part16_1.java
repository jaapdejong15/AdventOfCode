package Day16;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

@SuppressWarnings("Duplicates")
public class Part16_1 {

    public static void main(String[] args) {
        try{
            InputStream s = new FileInputStream("src/main/resources/day16.txt");
            long startTime = System.nanoTime();

            int[] basePattern = {0, 1, 0, -1};

            arrPrinter(basePatternMaker(basePattern, 8, 2));



            int[] input = inputParser(s);

            int[] result = solve(input, basePattern, 100);
            arrPrinter(result);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                sb.append(result[i]);
            }

            System.out.println("Answer: " + sb.toString());
            long endTime = System.nanoTime();
            System.out.println("Solved in " + ((endTime - startTime) / Math.pow(10, 9)) + " seconds");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int[] solve(int[] input, int[] basePattern, int phases) {
        int[] previousOutput = input;
        for (int i = 1; i <= phases; i++) {
            int[] output = new int[input.length];
            for (int k = 0; k < input.length; k++) {
                int[] pattern = basePatternMaker(basePattern, input.length, k + 1);
                for (int j = 0; j < input.length; j++) {
                    output[k] += pattern[j] * previousOutput[j];
                }
                output[k] = Math.abs(output[k] % 10);
            }
            previousOutput = output;

        }
        return previousOutput;
    }

    private static int[] basePatternMaker(int[] basePattern, int length, int step) {
        int[] result = new int[length];
        int currentPos = 0;
        int amount = 1;
        for (int i = 0; i < length; i++) {
            if (amount >= step) {
                currentPos++;
                amount = 0;
                if (currentPos >= basePattern.length) {
                    currentPos = 0;
                }
            }
            result[i] = basePattern[currentPos];
            amount++;
        }
        return result;
    }

    private static int[] inputParser(InputStream in) {
        Scanner sc = new Scanner(in);
        String input = sc.nextLine();
        int[] result = new int[input.length()];
        for (int i = 0; i < input.length(); i++) {
            result[i] = Integer.parseInt(input.charAt(i) + "");
        }
        sc.close();
        return result;
    }

    static void arrPrinter(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i : arr) {
            sb.append(i).append(" ");
        }
        System.out.println(sb.toString());
    }
}
