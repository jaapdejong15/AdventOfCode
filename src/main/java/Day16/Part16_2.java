package Day16;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

@SuppressWarnings("Duplicates")
public class Part16_2 {

    private static List<int[]> patterns = new ArrayList<>();

    public static void main(String[] args) {
        try{
            InputStream s = new FileInputStream("src/main/resources/day16.txt");
            long startTime = System.nanoTime();
            int[] input = inputParser(s);

            // Calculate offset
            StringBuilder offsetBuilder = new StringBuilder();
            for (int i = 0; i < 7; i++) {
                offsetBuilder.append(input[i]);
            }
            int offset = Integer.parseInt(offsetBuilder.toString());

            if (offset < input.length / 2) {
                System.out.println("This calculation only works when the offset is in the second half of the input.");
            }
            // Calculate result
            int[] result = solve(input, 100);


            StringBuilder sb = new StringBuilder();
            for (int i = offset; i < offset + 8; i++) {
                sb.append(result[i]);
            }

            System.out.println("Answer: " + sb.toString());
            long endTime = System.nanoTime();
            System.out.println("Solved in " + ((endTime - startTime) / Math.pow(10, 9)) + " seconds");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int[] solve(int[] input, int phases) {
        /*
        Pattern for position [length - n] will always end in n -1s. For the last value, the result will be the same as
        the input. |(-1 * input[length - 1]) % 10| = input[length - 1]. For position [length - 2], the result will be
        |(-1 * input[length - 1] + -1 * input[length-2] | % 10. We can change the -1s into 1s and remove the Abs,
        because the input is always positive. So the value for the second half of the function can be calculated by
        taking the last digit of the sum of all input values above the position. To speed this up, we can iterate
        through the array backwards and keep track of a cumulative sum. The %10 is taken over the cumulative sum,
        but that shouldn't be necessary, it will avoid large numbers however.
         */
        for (int i = 0; i < phases; i++) {
            int cumulativeSum = 0;
            for (int j = input.length - 1; j >= 0; j--) {
                cumulativeSum = (cumulativeSum + input[j]) % 10;
                input[j] = cumulativeSum;
            }
        }
        return input;
    }
    private static int[] inputParser(InputStream in) {
        Scanner sc = new Scanner(in);
        String originalInput = sc.nextLine();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append(originalInput);
        }
        String input = sb.toString();
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
