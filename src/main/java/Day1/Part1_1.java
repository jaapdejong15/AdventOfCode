package Day1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Part1_1 {
    public static void main(String[] args) {
        try{
            InputStream s = new FileInputStream("src/main/resources/day1.txt");
            long startTime = System.nanoTime();
            System.out.println(solution(s));
            long endTime = System.nanoTime();
            System.out.println("Solved in " + ((endTime - startTime) / 100000000.f) + " seconds");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    public static String solution(InputStream s) {
        long totalMass = 0;
        Scanner sc = new Scanner(s);
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            int input = Integer.parseInt(line);
            totalMass += Math.floorDiv(input, 3) - 2;
            if (totalMass < 0) {
                System.out.println("Smaller than 0");
            }
        }

        sc.close();
        return "" + totalMass;
    }
}
