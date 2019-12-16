package Day15;

public class Direction {
    public static final int NORTH = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;
    public static final int EAST = 4;

    public static final int[] rightMap = {4, 3, 1, 2};
    public static final int[] leftMap = {3, 4, 2, 1};

    private int currentDirection;

    public Direction(int direction) {
        currentDirection = direction;
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    public void turnRight() {
        currentDirection = rightMap[currentDirection - 1];
    }

    public void turnLeft() {
        currentDirection = leftMap[currentDirection - 1];
    }
}
