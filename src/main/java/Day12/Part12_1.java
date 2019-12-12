package Day12;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Part12_1 {
    public static void main(String[] args) {
        try{
            InputStream s = new FileInputStream("src/main/resources/day12.txt");
            long startTime = System.nanoTime();

            Moon[] moons = inputParser(s);
            int answer = solve(moons, 1000);

            long endTime = System.nanoTime();
            System.out.println("Answer: " + answer);
            System.out.println("Solved in " + ((endTime - startTime) / Math.pow(10, 9)) + " seconds");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }




    }

    private static int solve(Moon[] moons, int iterations) {
        for (int t = 0; t < iterations; t++) {
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

        int result = 0;
        for (Moon m : moons) {
            int potentialEnergy = 0;
            int kineticEnergy = 0;
            for (int i = 0; i < 3; i++) {
                potentialEnergy += Math.abs(m.coords[i]);
                kineticEnergy += Math.abs(m.velocity[i]);
            }

            result += potentialEnergy * kineticEnergy;
        }
        System.out.println(result);
        return result;
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

}

