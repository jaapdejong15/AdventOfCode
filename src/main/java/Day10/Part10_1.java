package Day10;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

@SuppressWarnings("Duplicates")
public class Part10_1 {

    static int size = 33;

    static Set<Angle> angles = new HashSet<>();

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

            for (Angle a : angles) {
                System.out.println(a.toString());
            }
            boolean[][] asteroid = inputParser(s);

            int maxVisible = 0;
            int maxX = 0;
            int maxY = 0;

            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    if (asteroid[x][y]) {
                        int visible = findNumVisible(x, y, asteroid);
                        System.out.println("Looking at asteroid (" + x + " " + y + "), there are " + visible + " asteroids visible");
                        if (visible > maxVisible) {
                            maxVisible = visible;
                            maxX = x;
                            maxY = y;
                        }
                    }
                }
            }


            System.out.println("At:      (" + maxX + ", " + maxY + ")" );
            System.out.println("Answer: " + maxVisible);
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

    public static int findNumVisible(int posX, int posY, boolean[][] asteroids) {
        int[][] visualize = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                visualize[i][j] = asteroids[i][j] ? 1 : 0;
            }
        }

        int numVisible = 0;
        for (Angle a : angles) {
            int x = posX;
            int y = posY;
            while (true) {
                x += a.dx;
                y += a.dy;
                if (x >= 0 && x < size && y >= 0 && y < size) {
                    if (asteroids[x][y]) {
                        numVisible++;
                        visualize[x][y] = 2;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        visualize[posX][posY] = 3;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (visualize[i][j] == 0) {
                    sb.append(". ");
                } if (visualize[i][j] == 1) {
                    sb.append("* ");
                } if (visualize[i][j] == 2) {
                    sb.append("X ");
                } if (visualize[i][j] == 3) {
                    sb.append("o ");
                }
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
        return numVisible;
    }


    public static int GCD(int a, int b) {
        if (b==0) return a;
        return GCD(b,a%b);
    }
}