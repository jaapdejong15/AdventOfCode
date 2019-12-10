package Day8;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Part8_2 {
    private static final int X_SIZE = 25;
    private static final int Y_SIZE = 6;

    public static void main(String[] args) {
        try{
            InputStream s = new FileInputStream("src/main/resources/day8.txt");
            long startTime = System.nanoTime();
            ArrayList<int[][]> input = inputParser(s);
            solution(input);
            long endTime = System.nanoTime();
            System.out.println("Solved in " + ((endTime - startTime) / Math.pow(10, 9)) + " seconds");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void solution(ArrayList<int[][]> input) {
        int[][] result = new int[X_SIZE][Y_SIZE];
        for (int x = 0; x < X_SIZE; x++) {
            for (int y = 0; y < Y_SIZE; y++) {
                int color = 2;
                int layer = 0;
                while(color == 2) {
                    color = input.get(layer)[x][y];
                    layer++;
                }
                result[x][y] = color;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < Y_SIZE; y++) {
            for (int x = 0; x < X_SIZE; x++) {
                if (result[x][y] == 1) sb.append('#');
                else sb.append(' ');
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    private static ArrayList<int[][]> inputParser(InputStream in) {
        ArrayList<int[][]> result = new ArrayList<>();
        Scanner sc = new Scanner(in);
        String input = sc.nextLine();
        sc.close();
        int counter = 0;
        while(counter < input.length()) {
            int[][] r = new int[X_SIZE][Y_SIZE];
            for (int y = 0; y < Y_SIZE; y++) {
                for (int x = 0; x < X_SIZE; x++) {
                    r[x][y] = Integer.parseInt(input.charAt(counter) + "");
                    counter+=1;
                }
            }
            result.add(r);

        }
        return result;
    }
}
