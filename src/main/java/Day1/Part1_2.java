package Day1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Part1_2 {
    public static void main(String[] args) {
        try{
            InputStream s = new FileInputStream("src/main/resources/day1.txt");
            long startTime = System.nanoTime();
            System.out.println(solution(s));
            long endTime = System.nanoTime();
            System.out.println("Solved in " + ((endTime - startTime) / Math.pow(10, 9)) + " seconds");
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
            totalMass += fuelRequired(input);
        }

        sc.close();
        return "" + totalMass;
    }

    public static long fuelRequired(long mass) {
        if (Math.floor(mass / 3.0) - 2 <= 0) return 0;
        long fuel = (int)Math.floor(mass / 3.0) - 2;
        return fuel + fuelRequired(fuel);
    }
}
