package primitives;

public class Point {

    private Double3 xyz;

    Point(Double x, Double y, Double z) {
        Double3 xyz(x,y,z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Point other)
            return this.xyz.equals(other.xyz);
        return false;
    }

}
