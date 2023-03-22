package primitives;

import static primitives.Util.isZero;

/**
 * this class serve a point with 3 coordinates, contains an object of three numbers of type Double3
 *
 * @author Yona and Aaron
 */
public class Point {

    Double3 xyz;

    public Point(Double x, Double y, Double z) {
        xyz = new Double3(x, y, z);
    }

    Point(Double3 _xyz) {
        xyz = _xyz;
    }

    public Point add(Vector vector) {
        return new Point(
                xyz.d1 + vector.xyz.d1,
                xyz.d2 + vector.xyz.d2,
                xyz.d3 + vector.xyz.d3
        );}

    public Vector substract(Point _p){
        if(_p.equals(this)){
            throw new IllegalArgumentException("unable to create a vector with (0,0,0) as coordinate");
        }
        return new Vector(new Double3(xyz.d1-_p.xyz.d1,xyz.d2-_p.xyz.d2,xyz.d3-_p.xyz.d3));
    }

        public Double distanceSquared(Point other) {
            final Double x1 = xyz.d1;
            final Double y1 = xyz.d2;
            final Double z1 = xyz.d3;
            final Double x2 = other.xyz.d1;
            final Double y2 = other.xyz.d2;
            final Double z2 = other.xyz.d3;

            return ((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)) + ((z2 - z1) * (z2 - z1));
        }

    public double distance(Point p) {
        return Math.sqrt(distanceSquared(p));
    }

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
