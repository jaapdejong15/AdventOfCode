package Day10;

class Angle implements Comparable<Day10.Angle> {
    int dx;
    int dy;

    public Angle(int x, int y) {
        this.dx = x;
        this.dy = y;
    }

    public boolean equals(Object a) {
        if (a instanceof Day10.Angle) {
            Day10.Angle angle = (Day10.Angle) a;
            return angle.dx == dx && angle.dy == dy;
        }
        return false;
    }

    public boolean equals(Day10.Angle a) {
        return a.dx == dx && a.dy == dy;
    }

    public String toString() {
        return "Angle: " + dx + " " + dy;
    }

    public double getAngle() {


        double angle = Math.atan((double)dx / (double)dy) - (Math.PI);
        if (dy < 0) angle = angle + Math.PI;

        if (angle > Math.PI * 2) {
            angle -= Math.PI * 2;
        } else if (angle < 0) {
            angle += Math.PI * 2;
        }

        return angle;
    }

    @Override
    public int hashCode() {
        return 10000 + 100 * dx + dy;
    }

    @Override
    public int compareTo(Day10.Angle o) {
        return Double.compare(this.getAngle(), o.getAngle());
    }
}
