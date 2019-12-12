package Day12;

class Moon {
    int[] coords;
    int[] velocity;

    public Moon(int x, int y, int z) {
        coords = new int[3];
        coords[0] = x;
        coords[1] = y;
        coords[2] = z;
        velocity = new int[3];
    }

    public Moon(String s) {
        velocity = new int[3];
        coords = new int[3];
        s = s.replaceAll("[xyz= <>]", "");
        String[] locationString = s.split(",");
        for (int i = 0; i < 3; i++) {
            coords[i] = Integer.parseInt(locationString[i]);
        }
    }

    public void update() {
        for (int i = 0; i < 3; i++) {
            coords[i] += velocity[i];
        }
    }

    public String state(int dimension) {
        StringBuilder sb = new StringBuilder();
        sb.append(".").append(velocity[dimension]).append(".").append(coords[dimension]);
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("pos=< ");
        for (int i = 0; i < 3; i++) {
            sb.append(coords[i]).append(" ");
        }
        sb.append(">, vel=< ");
        for (int i = 0; i < 3; i++) {
            sb.append(velocity[i]).append(" ");
        }
        sb.append(">");
        return sb.toString();

    }

    // PART 2:
    public Moon(Moon m) {
        this.coords = m.coords.clone();
        this.velocity = m.velocity.clone();
    }

    public boolean equals(Moon m) {
        for (int i = 0; i < 3; i++) {
            if (coords[i] != m.coords[i]) return false;
            if (velocity[i] != m.velocity[i]) return false;
        }
        return true;
    }
}

