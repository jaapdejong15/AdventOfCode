package Day3;

class Pair<T> {
    T a;
    T b;
    public Pair(T a, T b) {
        this.a = a;
        this.b = b;
    }

    public boolean equals(Pair<T> pair) {
        return pair.a.equals(a) && pair.b.equals(b);
    }
}