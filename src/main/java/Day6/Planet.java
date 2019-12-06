package Day6;

class Planet {
    String id;
    Planet orbits;

    Planet(String id) {
        this.id = id;
    }

    boolean hasParent() {
        return orbits != null;
    }
}
