package Day3;

class Line {
    int xA, yA, xB, yB;
    boolean horizontal;

    public Line(int xA, int yA, int xB, int yB) {
        this.xA = xA;
        this.yA = yA;
        this.xB = xB;
        this.yB = yB;
        horizontal = xA - xB != 0;
    }

    public Pair<Integer> getIntersection(Line l) {
        if ((l.horizontal && horizontal || (!l.horizontal && !horizontal))) {
            return null;
        } else if (l.horizontal) {
            int iX = xA;
            int iY = l.yA;
            if ((Math.min(l.xA, l.xB) <= iX && iX <= Math.max(l.xA, l.xB)) && (Math.min(yA, yB) <= iY && iY <= Math.max(yA, yB))) {
                return new Pair<Integer>(iX, iY);
            }
        } else if (horizontal) {
            int iX = l.xA;
            int iY = yA;
            if ((Math.min(xA, xB) <= iX && iX <= Math.max(xA, xB)) && (Math.min(l.yA, l.yB) <= iY && iY <= Math.max(l.yA, l.yB))) {
                return new Pair <Integer> (iX, iY);
            }
        }
        return null;
    }

    public int length() {
        return Math.abs(xB - xA) + Math.abs(yB - yA);
    }

    public int pointOnLine(Pair<Integer> point) {
        if (horizontal) {
            if (point.b == yA && point.a >= Math.min(xA, xB) && point.a <= Math.max(xA, xB)) {
                return Math.abs(point.a - xA);
            }
        } else {
            if (point.a == xA && point.b >= Math.min(yA, yB) && point.b <= Math.max(yA, yB)) {
                return Math.abs(point.b - yA);
            }
        }
        System.out.println("POINT NOT ON LINE!");
        return -1;
    }
}