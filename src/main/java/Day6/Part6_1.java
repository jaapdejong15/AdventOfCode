package Day6;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Part6_1 {
    private static HashMap<String, Planet> planets = new HashMap<>();

    public static void main(String[] args) {
        try{
            InputStream s = new FileInputStream("src/main/resources/day6.txt");
            long startTime = System.nanoTime();
            inputParser(s);
            System.out.println("Answer: " + counter());
            long endTime = System.nanoTime();
            System.out.println("Solved in " + ((endTime - startTime) / Math.pow(10, 9)) + " seconds");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void inputParser(InputStream in) {
        Scanner sc = new Scanner(in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] planetStr = line.split("\\)");

            Planet p1;
            if (planets.containsKey(planetStr[1]))
                p1 = planets.get(planetStr[1]);
            else p1 = new Planet(planetStr[1]);

            Planet parent;
            if (planets.containsKey(planetStr[0])) {
                parent = planets.get(planetStr[0]);
            } else {
                parent = new Planet(planetStr[0]);
                planets.put(planetStr[0], parent);
            }
            p1.orbits = parent;
            planets.put(planetStr[1], p1);
        }
    }

    private static int counter() {
        int count = 0;
        for (String K : planets.keySet()) {
            Planet node = planets.get(K);
            while (node.hasParent()) {
                count++;
                node = node.orbits;
            }
        }
        return count;
    }
}


