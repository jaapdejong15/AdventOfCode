package Day8;

import com.sun.tools.jdeprscan.scan.Scan;

import javax.print.attribute.standard.PresentationDirection;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Part8_1 {
    private static final int X_SIZE = 25;
    private static final int Y_SIZE = 6;

    public static void main(String[] args) {
        try{
            InputStream s = new FileInputStream("src/main/resources/day8.txt");
            long startTime = System.nanoTime();
            inputParser(s);
            //System.out.println("Answer: " + solution(s));
            long endTime = System.nanoTime();
            System.out.println("Solved in " + ((endTime - startTime) / Math.pow(10, 9)) + " seconds");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int solution() {
        return 0;
    }

    private static ArrayList<int[][]> inputParser(InputStream in) {
        ArrayList<int[][]> result = new ArrayList<>();
        Scanner sc = new Scanner(in);
        String input = sc.nextLine();
        sc.close();
        int minZero = Integer.MAX_VALUE;
        int minResult = 0;
        int counter = 0;
        while(counter < input.length()) {
            int[] count = new int[3];
            int[][] r = new int[X_SIZE][Y_SIZE];
            for (int y = 0; y < Y_SIZE; y++) {
                for (int x = 0; x < X_SIZE; x++) {
                    r[x][y] = Integer.parseInt(input.charAt(counter) + "");
                    counter+=1;
                    count[r[x][y]] += 1;
                }
            }
            if (count[0] < minZero) {
                minZero = count[0];
                minResult = count[1] * count[2];
            }
            result.add(r);

        }
        System.out.println(minResult);
        return result;
    }
}
