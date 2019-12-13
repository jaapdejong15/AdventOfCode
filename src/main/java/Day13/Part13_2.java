package Day13;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Part13_2 {
    final static int xSize = 43;
    final static int ySize = 26;
    static int score = 0;


    private static int[][] grid = new int[xSize][ySize];
    int numBlocks = 0;

    public static void main(String[] args) {
        try{
            InputStream s = new FileInputStream("src/main/resources/day13.txt");
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
        ArrayList<Long> program = new ArrayList<>(data.length);

        for (long i : data) {
            program.add(i);
        }

        int pointer = 0;
        String parameterMode;
        long result = 0;
        int rBase = 0;

        int outputValue = 0;

        int x = 0;
        int y = 0;
        int tile;

        program.set(0, 2L);

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
            /*
            System.out.println("---PROGRAM DATA---");
            System.out.println("Operation: " + opCode);
            System.out.println("Pointer:   " + pointer);
            */


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
                    int joystickPosition = calculateJoystick();
                    setParameter(parameterMode, program, 1, pointer, rBase, joystickPosition);
                    pointer += 2;
                    break;
                case 4: // OUTPUT
                    result = getParameter(parameterMode, program, 1, pointer, rBase);

                    switch (outputValue) {
                        case 0:
                            x = (int)result;
                            outputValue++;
                            break;
                        case 1:
                            y = (int)result;
                            outputValue++;
                            break;
                        case 2:
                            tile = (int)result;
                            if (x == -1 && y == 0) {
                                score = tile;
                            } else {
                                draw(x, y, tile);
                            }
                            outputValue = 0;
                            break;
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
                    //System.out.println("Rbase = " + rBase);
                    pointer += 2;
                    break;
                case 99: // EXIT
                    drawGrid();
                    return result;
                default:
                    System.out.println("Unknown opcode!");
                    return -1;
            }
        }
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


    private static void draw(int x, int y, int object) {
        try {
            grid[x][y] = object;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("(" + x + ", " + y + ")");
        }
    }

    private static void drawGrid() {
        int result = 0;
        clearScreen();
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                if (grid[x][y] == 2) result++;
                sb.append(getString(grid[x][y]));
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
        System.out.println("Number of blocks: " + result);

    }

    private static String getString(int i) {
        switch (i) {
            case 0: return " ";
            case 1: return "█";
            case 2: return "#";
            case 3: return "_";
            case 4: return "O";
        }
        System.out.println("Invalid character");
        return "  ";
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static int calculateJoystick() {
        int xBall = 0;
        int xPaddle = 0;
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                if (grid[x][y] == 4) {
                    xBall = x;
                } else if (grid[x][y] == 3) {
                    xPaddle = x;
                }
            }
        }
        if (xBall > xPaddle) {
            return 1;
        } else if (xBall < xPaddle) {
            return -1;
        } else {
            return 0;
        }
    }





}
