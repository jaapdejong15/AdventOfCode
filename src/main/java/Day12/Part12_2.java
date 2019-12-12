package Day12;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Part12_2 {
    public static void main(String[] args) {
        System.out.println(leastCommonMultiple(12, 18));
        try {
            InputStream s = new FileInputStream("src/main/resources/day12.txt");
            long startTime = System.nanoTime();

            Moon[] moons = inputParser(s);
            long answer = solve(moons);

            long endTime = System.nanoTime();
            System.out.println("Answer: " + answer);
            System.out.println("Solved in " + ((endTime - startTime) / Math.pow(10, 9)) + " seconds");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }


    }

    private static void update(Moon[] moons) {
        for (int i = 0; i < moons.length; i++) {
            for (int j = 0; j < moons.length; j++) {
                if (i != j) {
                    for (int k = 0; k < 3; k++) {
                        if (moons[i].coords[k] < moons[j].coords[k]) {
                            moons[i].velocity[k]++;
                        } else if (moons[i].coords[k] > moons[j].coords[k]) {
                            moons[i].velocity[k]--;
                        }
                    }

                }
            }
        }
        for (int j = 0; j < moons.length; j++) {
            moons[j].update();
        }

    }

    private static long solve(Moon[] moons) {
        Moon[] initialMoons = new Moon[moons.length];
        ArrayList<HashMap<String, Integer>> states = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            states.add(new HashMap<>());
        }

        for (int i = 0; i < initialMoons.length; i++) {
            initialMoons[i] = new Moon(moons[i]);
        }

        boolean[] repeatsFound = new boolean[3];
        int[] repeatValues = new int[3];

        int found = 0;

        int t = 0;
        while (found < 3) {
            update(moons);
            t++;
            for (int axis = 0; axis < 3; axis++) {
                if (!repeatsFound[axis]) {
                    StringBuilder sb = new StringBuilder();
                    for (Moon moon : moons) {
                        sb.append(moon.state(axis));
                    }
                    String state = sb.toString();
                    if (states.get(axis).containsKey(state)) {
                        System.out.println("Found repeat for  " + axis + ": " + (t - states.get(axis).get(state)));
                        repeatsFound[axis] = true;
                        repeatValues[axis] = t - states.get(axis).get(state);
                        found++;
                    } else {
                        states.get(axis).put(state, t);
                    }
                }

            }
        }

        System.out.println("---i---");
        for (int i : repeatValues) {
            System.out.println("i = " + i);
        }

        return leastCommonMultiple(repeatValues[0], leastCommonMultiple(repeatValues[1], repeatValues[2]));
    }

    private static Moon[] inputParser(InputStream s) {
        Scanner sc = new Scanner(s);
        Moon[] moons = new Moon[4];

        for (int i = 0; i < 4; i++) {
            String line = sc.nextLine();
            moons[i] = new Moon(line);
        }
        sc.close();
        return moons;
    }

    private static long leastCommonMultiple(long x, long y) {
        if (x == 0 || y == 0) {
            return 0;
        }
        long absNumber1 = Math.abs(x);
        long absNumber2 = Math.abs(y);
        long absHigherNumber = Math.max(absNumber1, absNumber2);
        long absLowerNumber = Math.min(absNumber1, absNumber2);
        long lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }
}