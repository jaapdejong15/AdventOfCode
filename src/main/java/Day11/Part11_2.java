package Day11;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

@SuppressWarnings("Duplicates")
public class Part11_2 {

    public static void main(String[] args) {
        try{
            InputStream s = new FileInputStream("src/main/resources/day11.txt");
            long startTime = System.nanoTime();
            System.out.println("Answer: " + solution(s));
            long endTime = System.nanoTime();
            System.out.println("Solved in " + ((endTime - startTime) / Math.pow(10, 9)) + " seconds");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static long solution(InputStream input) {
        long[] data = opcodes(input);
        ArrayList<Long> program = new ArrayList<>(data.length + 1000);

        for (long i : data) {
            program.add(i);
        }

        int pointer = 0;
        String parameterMode;
        long result = 0;
        int rBase = 0;

        int counter = 0;



        boolean nextOutputIsColor = true;
        Location currentLocation = new Location(0, 0);
        HashMap<Location, Integer> locations = new HashMap<>();
        locations.put(currentLocation, 1);

        int dirY = 1;
        int dirX = 0;

        while(true) {
            // Get operation
            String opCode = data[pointer] + "";
            int operation;
            if (opCode.length() == 1) {
                operation = Integer.parseInt(program.get(pointer) + "");
                parameterMode = "";
            } else {
                operation = Integer.parseInt(opCode.substring(opCode.length() - 2));
                parameterMode = opCode.substring(0, opCode.length() - 2);
            }
            long value;
            switch (operation) {
                case 1: // SUM
                    value = getParameter(parameterMode, program, 1, pointer, rBase) + getParameter(parameterMode, program, 2, pointer, rBase);
                    setParameter(parameterMode, program, 3, pointer, rBase, value);
                    pointer += 4;
                    break;
                case 2: // MULTIPLICATION
                    value = getParameter(parameterMode, program, 1, pointer, rBase) * getParameter(parameterMode, program, 2, pointer, rBase);
                    setParameter(parameterMode, program, 3, pointer, rBase, value);
                    pointer += 4;
                    break;
                case 3: // INPUT
                    int color = 0;
                    if (locations.containsKey(currentLocation)) {
                        color = locations.get(currentLocation);
                    }
                    setParameter(parameterMode, program, 1, pointer, rBase, color);
                    pointer += 2;
                    break;
                case 4: // OUTPUT
                    result = getParameter(parameterMode, program, 1, pointer, rBase);
                    if (nextOutputIsColor) {
                        if (locations.containsKey(currentLocation)) {
                            locations.replace(currentLocation, (int) result);
                        } else {
                            locations.put(new Location(currentLocation.x, currentLocation.y), (int) result);
                            counter++;
                        }
                        nextOutputIsColor = false;
                    } else {
                        if (result == 1) { // TURN RIGHT
                            if (dirX == 1 || dirX == -1) {
                                dirY = -dirX;
                                dirX = 0;
                            } else if (dirY == 1 || dirY == -1) {
                                dirX = dirY;
                                dirY = 0;
                            }
                        } else if (result == 0) { // TURN LEFT
                            if (dirX == 1 || dirX == -1) {
                                dirY = dirX;
                                dirX = 0;
                            } else if (dirY == 1 || dirY == -1) {
                                dirX = -dirY;
                                dirY = 0;
                            }
                        }
                        currentLocation.x += dirX;
                        currentLocation.y += dirY;
                        nextOutputIsColor = true;
                    }
                    pointer += 2;
                    break;
                case 5: // JUMP IF TRUE
                    if (getParameter(parameterMode, program, 1, pointer, rBase) != 0)
                        pointer = (int) getParameter(parameterMode, program,2, pointer, rBase);
                    else pointer += 3;

                    break;
                case 6: // JUMP IF FALSE
                    if (getParameter(parameterMode, program, 1, pointer, rBase) == 0)
                        pointer = (int) getParameter(parameterMode, program,2, pointer, rBase);
                    else pointer += 3;
                    break;
                case 7: // IF LESS
                    if (getParameter(parameterMode, program, 1, pointer, rBase) < getParameter(parameterMode, program, 2, pointer, rBase)) {
                        setParameter(parameterMode, program, 3, pointer, rBase, 1);
                    } else {
                        setParameter(parameterMode, program, 3, pointer, rBase, 0);
                    }
                    pointer += 4;
                    break;
                case 8: // IF EQUAL
                    if (getParameter(parameterMode, program, 1, pointer, rBase) == getParameter(parameterMode, program, 2, pointer, rBase)) {
                        setParameter(parameterMode, program, 3, pointer, rBase, 1);
                    }
                    else {
                        setParameter(parameterMode, program, 3, pointer, rBase, 0);
                    }
                    pointer += 4;
                    break;
                case 9: // ADJUST RELATIVE BASE
                    rBase += (int) getParameter(parameterMode, program, 1, pointer, rBase);
                    pointer += 2;
                    break;
                case 99:
                    printPanel(locations);

                    return locations.size();
                default:
                    System.out.println("Unknown opcode!");
                    return -1;
            }
        }
    }

    private static void printPanel(HashMap<Location, Integer> paint) {
        int minX = 0;
        int maxX = 0;
        int minY = 0;
        int maxY = 0;

        for (Location l : paint.keySet()) {
            minX = Math.min(minX, l.x);
            minY = Math.min(minY, l.y);
            maxX = Math.max(maxX, l.x);
            maxY = Math.max(maxY, l.y);
        }
        StringBuilder sb = new StringBuilder();
        for (int y = maxY; y >= minY; y--) {
            for (int x = minX; x <= maxX; x++) {
                Location key = new Location(x, y);
                if (paint.containsKey(key)) {
                    sb.append(paint.get(key) == 1 ? "██" : "  ");
                } else {
                    sb.append("  ");
                }
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    private static long getParameter(String parameterMode, ArrayList<Long> program, int parameter, int pointer, int rBase) {
        int mode = 0;
        if (parameter <= parameterMode.length()) {
            mode = Integer.parseInt(parameterMode.charAt(parameterMode.length() - parameter) + "");
        }
        int index;
        if (mode == 0) {
            index = Integer.parseInt(program.get(pointer + parameter) + "");
        } else if (mode == 1){
            index = pointer + parameter;
        } else {
            index = rBase + Integer.parseInt(program.get(pointer + parameter) + "");
        }
        checkProgramSize(program, index);
        return program.get(index);
    }

    private static void setParameter(String parameterMode, ArrayList<Long> program, int parameter, int pointer, int rBase, long value) {
        int mode = 0;
        if (parameter <= parameterMode.length()) {
            mode = Integer.parseInt(parameterMode.charAt(parameterMode.length() - parameter) + "");
        }
        int index;
        if (mode == 0) {
            index = Integer.parseInt(program.get(pointer + parameter) + "");
        } else if (mode == 1) {
            index = pointer + parameter;
        } else {
            index = rBase + Integer.parseInt(program.get(pointer + parameter) + "");
        }
        checkProgramSize(program, index);
        program.set(index, value);
    }

    private static void checkProgramSize(ArrayList<Long> program, int size) {
        if (size < program.size()) return;
        while (program.size() <= size) {
            program.add(0L);
        }
    }

    private static long[] opcodes(InputStream in) {
        Scanner sc = new Scanner(in);
        StringBuilder sb = new StringBuilder();
        while(sc.hasNextLine()) {
            sb.append(sc.nextLine());
        }
        sc.close();
        String inputStr = sb.toString();
        String[] opcodeStr = inputStr.split(",");
        long[] opcodes = new long[opcodeStr.length];
        for (int i = 0; i < opcodeStr.length; i++) {
            opcodes[i] = Long.parseLong(opcodeStr[i]);
        }
        return opcodes;
    }


}