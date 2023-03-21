package primitives;

import static primitives.Util.isZero;

public class Point {

    Double3 xyz;

    public Point(Double x, Double y, Double z) {
        xyz = new Double3(x, y, z);
    }

    Point(Double3 _xyz) {
        xyz = _xyz;
    }

    public void normalize() {
        Double length = Math.sqrt(xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3);
        if (length != 0.0) {
            xyz.reduce(length);
        }
    }

    public Vector substract(Point _p){
        if(_p.equals(this)){
            throw new IllegalArgumentException("unable to create a vector with (0,0,0) as coordinate");
        }
        return new Vector(new Double3(xyz.d1-_p.xyz.d1,xyz.d2-_p.xyz.d2,xyz.d3-_p.xyz.d3));
    }
    public Point add(Vector vector) {
        return new Point(
                xyz.d1 + vector.xyz.d1,
                xyz.d2 + vector.xyz.d2,
                xyz.d3 + vector.xyz.d3
        );

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Double3 other)
            return isZero(xyz.d1 - other.d1)
                    && isZero(xyz.d2 - other.d2)
                    && isZero(xyz.d3 - other.d3);
        return false;
    }

    @Override
    public String toString() {
        return "(" + xyz.d1 + ", " + xyz.d2 + ", " + xyz.d3 + ")";
    }
}
