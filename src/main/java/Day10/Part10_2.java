package Day10;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

@SuppressWarnings("Duplicates")
public class Part10_2 {

    static int size = 33;

    static Set<Angle> angles = new TreeSet<>();

    public static void main(String[] args) {
        try{
            InputStream s = new FileInputStream("src/main/resources/day10.txt");
            long startTime = System.nanoTime();

            for (int i = -size; i < size; i++) {
                for (int j = -size; j < size; j++) {
                    int gcd = GCD(i, j);
                    if (gcd != 0) {
                        angles.add(new Angle(i / Math.abs(gcd), j / Math.abs(gcd)));
                    }
                }
            }

            ArrayList<Angle> angleList = new ArrayList<>(angles.size());
            angleList.addAll(angles);

            boolean[][] asteroid = inputParser(s);

            int locX = 22;
            int locY = 28;

            int asteroidX;
            int asteroidY;

            int counter = 0;
            int angleCounter = 0;

            mainLoop: while (true) {
                Angle a = angleList.get(angleCounter);
                int x = locX;
                int y = locY;
                while (true) {
                    x += a.dx;
                    y += a.dy;
                    if (x >= 0 && x < size && y >= 0 && y < size) {
                        if (asteroid[x][y]) {
                            asteroid[x][y] = false;
                            counter++;
                            if (counter == 200) {
                                asteroidX = x;
                                asteroidY = y;
                                break mainLoop;
                            }
                            break;
                        }
                    } else {
                        break;
                    }
                }

                angleCounter--;
                if (angleCounter < 0) {
                    angleCounter = angleList.size() - 1;
                }
            }
            System.out.println("Answer: " + (100 * asteroidX + asteroidY));
            long endTime = System.nanoTime();
            System.out.println("Solved in " + ((endTime - startTime) / Math.pow(10, 9)) + " seconds");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean[][] inputParser(InputStream s) {
        Scanner sc = new Scanner(s);

        boolean[][] asteroid = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            char[] line = sc.nextLine().toCharArray();
            for (int j = 0; j < size; j++) {
                asteroid[j][i] = line[j] == '#';
            }
        }

        sc.close();
        return asteroid;
    }


    public static int GCD(int a, int b) {
        if (b==0) return a;
        return GCD(b,a%b);
    }
}

