package Day14;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Part14_1 {

    private static HashMap<String, Reaction> reactions = new HashMap<>();
    private static HashMap<String, Integer> amountLeft = new HashMap<>();

    public static void main(String[] args) {
        try{
            InputStream s = new FileInputStream("src/main/resources/day14.txt");
            long startTime = System.nanoTime();

            inputParser(s);
            long answer = solve(new Chemical(1, "FUEL"), 1);

            long endTime = System.nanoTime();
            System.out.println("Answer: " + answer);
            System.out.println("Solved in " + ((endTime - startTime) / Math.pow(10, 9)) + " seconds");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static long solve(Chemical c, int amount) {

        // Stop condition
        if (c.chemical.equals("ORE")) {
            return amount;
        }

        // Find the reaction
        Reaction r = reactions.get(c.chemical);

        // Calculate amount of reactions we need
        int numReactions = 0;
        int currentAmount = 0;
        if (amountLeft.containsKey(c.chemical)) {
            currentAmount = amountLeft.get(c.chemical);
            amountLeft.replace(c.chemical, 0);
        }

        while (currentAmount < amount) {
            numReactions++;
            currentAmount += r.result.amount;
        }
        //System.out.println("Amount of reactions for " + c.chemical + ": " + numReactions);

        // Calculate the amount of fuel we need for the chemical
        long fuelRequired = 0;
        for (Chemical ingredient : r.ingredients) {
            fuelRequired += solve(ingredient, ingredient.amount * numReactions);
        }

        // Update the amount of chemical left
        if (amountLeft.containsKey(c.chemical)) {
            amountLeft.replace(c.chemical, currentAmount - amount);
        } else {
            amountLeft.put(c.chemical, currentAmount - amount);
        }


        System.out.println("Amount of ore for " + (numReactions * r.result.amount) + " " + c.chemical + ": " + fuelRequired);
        return fuelRequired;
    }

    static void inputParser(InputStream s) {
        Scanner sc = new Scanner(s);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Reaction r = new Reaction(line);
            reactions.put(r.result.chemical, r);
        }
        sc.close();
    }

}

class Reaction {
    Chemical result;

    List<Chemical> ingredients;

    public Reaction(Chemical result, List<Chemical> ingredients) {
        this.ingredients = ingredients;
        this.result = result;
    }

    public Reaction(String line) {
        String[] reaction = line.split(" => ");
        this.result = new Chemical(reaction[1]);

        ingredients = new ArrayList<>();
        String[] ingredientsString = reaction[0].split(", ");
        for (String s : ingredientsString) {
            ingredients.add(new Chemical(s));
        }
    }

}

class Chemical {
    int amount;
    String chemical;

    public Chemical(int amount, String chemical) {
        this.amount = amount;
        this.chemical = chemical;
    }

    public Chemical(String line) {
        String[] result = line.split(" ");
        this.amount = Integer.parseInt(result[0]);
        this.chemical = result[1];
    }
}

