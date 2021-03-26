package core;

public class Location {
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location(Location loc) {
        this.x = loc.x();
        this.y = loc.y();
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("(");
        str.append(x);
        str.append(", ");
        str.append(y);
        str.append(")");
        return str.toString();
    }
}
