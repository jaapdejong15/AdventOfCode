package Day15;

class Location implements Comparable<Location> {
    int x, y;

    Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Location getLeft() {
        return new Location(x - 1, y);
    }

    Location getRight() {
        return new Location(x + 1, y);
    }

    Location getTop() {
        return new Location(x, y + 1);
    }

    Location getBottom() {
        return new Location(x, y - 1);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x &&
                y == location.y;
    }

    @Override
    public int hashCode() {
        return 100000 + 1000 * x + y;
    }

    public Location getLocation(Direction d) {
        switch (d.getCurrentDirection()) {
            case Direction.NORTH:
                return new Location(x, y + 1);
            case Direction.SOUTH:
                return new Location(x, y - 1);
            case Direction.EAST:
                return new Location(x + 1, y);
            case Direction.WEST:
                return new Location(x - 1, y);
            default:
                return new Location(x, y);
        }
    }

    @Override
    public int compareTo(Location o) {
        if (o.y != this.y) return Integer.compare(o.y, this.y);
        return Integer.compare(o.x, this.x);
    }
}
