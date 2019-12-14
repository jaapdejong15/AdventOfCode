package Day14;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Part14_2 {

    private static HashMap<String, Reaction> reactions = new HashMap<>();
    private static HashMap<String, Long> amountLeft = new HashMap<>();

    public static void main(String[] args) {
        try{
            InputStream s = new FileInputStream("src/main/resources/day14.txt");
            long startTime = System.nanoTime();

            inputParser(s);
            double ore_per_fuel = solve(new Chemical(1, "FUEL"), 1.0);
            System.out.println("Ore per fuel:" + ore_per_fuel);
            double amount_of_ore = 1000000000000.0;
            int upperBound = (int) (amount_of_ore / ore_per_fuel);;
            int lowerBound = 0;

            int answer = binary_search(upperBound, lowerBound);

            long endTime = System.nanoTime();
            System.out.println("Answer: " + answer);
            System.out.println("Solved in " + ((endTime - startTime) / Math.pow(10, 9)) + " seconds");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int binary_search(int upper, int lower) {
        System.out.println("Upper: " + upper);
        System.out.println("Lower: " + lower);
        int center = (lower + upper) / 2;
        if (upper - lower == 1) return lower;
        double result = solve(new Chemical(1, "FUEL"), center);
        if (result < 1000000000000.0) {
            lower = center + 1;
        } else if (result > 1000000000000.0) {
            upper = center - 1;
        } else {
            return center;
        }
        return binary_search(upper, lower);
    }

    private static double solve(Chemical c, double amount) {
        // Stop condition
        if (c.chemical.equals("ORE")) {
            return amount;
        }

        // Find the reaction
        Reaction r = reactions.get(c.chemical);

        // Calculate amount of reactions we need
        double amountPerReaction = r.result.amount;
        double amountOfReactions = amount / amountPerReaction;

        // Calculate the amount of fuel we need for the chemical
        long fuelRequired = 0;
        for (Chemical ingredient : r.ingredients) {
            fuelRequired += solve(ingredient, ingredient.amount * amountOfReactions);
        }

        return fuelRequired;
    }

    private static void inputParser(InputStream s) {
        Scanner sc = new Scanner(s);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Reaction r = new Reaction(line);
            reactions.put(r.result.chemical, r);
        }
        sc.close();
    }

}
