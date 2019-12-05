package Day3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@SuppressWarnings("Duplicates")
public class Part3_1 {
    static Set<Line> path1 = new HashSet<>();
    static Set<Line> path2 = new HashSet<>();

    public static void main(String[] args) {
        InputStream in;
        try {
            in = new FileInputStream("src/main/resources/day3.txt");
            long startTime = System.nanoTime();
            inputParser(in);
            System.out.println(solve());
            long endTime = System.nanoTime();
            System.out.println("Solved in " + ((endTime - startTime) / 100000000.f) + " seconds");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }


    }

    public static void inputParser(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);
        String path1Str = sc.nextLine();
        String path2Str = sc.nextLine();
        sc.close();

        String[] p1 = path1Str.split(",");
        path1 = pathParser(p1);
        String[] p2 = path2Str.split(",");
        path2 = pathParser(p2);
    }

    public static int solve() {
        int minDistance = Integer.MAX_VALUE;
        for (Line l1 : path1) {
            for (Line l2 : path2) {
                Pair<Integer> intersection = l1.getIntersection(l2);
                if (intersection != null) {
                    int distance = Math.abs(intersection.a) + Math.abs(intersection.b);
                    if (distance < minDistance) minDistance = distance;
                }
            }
        }
        return minDistance;
    }

    public static Set<Line> pathParser (String[] pathString) {
        int currentX = 0;
        int currentY = 0;
        Set<Line> result = new HashSet<>();
        int nextY;
        int nextX;
        for (String command : pathString) {
            char key = command.charAt(0);
            int length = Integer.parseInt(command.substring(1));
            switch (key) {
                case 'U':
                    nextY = currentY + length;
                    result.add(new Line(currentX, currentY, currentX, nextY));
                    currentY = nextY;
                    break;
                case 'D':
                    nextY = currentY - length;
                    result.add(new Line(currentX, currentY, currentX, nextY));
                    currentY = nextY;
                    break;
                case 'L':
                    nextX = currentX - length;
                    result.add(new Line(currentX, currentY, nextX, currentY));
                    currentX = nextX;
                    break;
                case 'R':
                    nextX = currentX + length;
                    result.add(new Line(currentX, currentY, nextX, currentY));
                    currentX = nextX;
                    break;
                default:
                    System.out.println("No such thing!");
                    break;
            }
        }
        return result;
    }



}