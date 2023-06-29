package primitives;

/**
 * this class serves a point with 3 coordinates, contains an object of three numbers of type Double3
 *
 * @author Yona and Aaron
 */
public class Point {
    /**
     * triad of coordinate values
     */
    final Double3 xyz;
    /**
     * The point of the center of coordinates
     */
    public static final Point ZERO = new Point(0, 0, 0);

    /**
     * constructor for the Point
     *
     * @param _x first number value
     * @param _y second number value
     * @param _z third number value
     */
    public Point(double _x, double _y, double _z) {
        xyz = new Double3(_x, _y, _z);
    }

    /**
     * constructor that gets Double3 and build point
     *
     * @param _xyz the xyz value
     */
    Point(Double3 _xyz) {
        xyz = _xyz;
    }

    /**
     * the function calculates vector plus point
     *
     * @param _vector the vector that we want to add
     * @return new point of the result
     */
    public Point add(Vector _vector) {
        return new Point(xyz.add(_vector.xyz));
    }

    /**
     * the function calculates point minus point
     *
     * @param _point the point that we want to subtract
     * @return new point of the result
     */

    public Vector subtract(Point _point) {
        return new Vector(xyz.subtract(_point.xyz));
    }

    /**
     * the function calculates the distance by minus between them and pow 2 every value
     *
     * @param _point the second point
     * @return the result of the distance in pow 2
     */
    public double distanceSquared(Point _point) {
        double dx = xyz.d1 - _point.xyz.d1;
        double dy = xyz.d2 - _point.xyz.d2;
        double dz = xyz.d3 - _point.xyz.d3;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * the function calculates the distance between 2 points by making root
     * to the value that return from distance squared
     *
     * @param _point the second point
     * @return the distance squared between 2 points
     */
    public double distance(Point _point) {
        return Math.sqrt(distanceSquared(_point));
    }

    /**
     * function that return the x coordinate of point
     *
     * @return the first coordinate of specifically point
     */
    @SuppressWarnings("unused")
    public double getX() {
        return this.xyz.d1;
    }
    /**
     * function that return the y coordinate of point
     *
     * @return the first coordinate of specifically point
     */
    public double getY() {
        return this.xyz.d2;
    }
    /**
     * function that return the z coordinate of point
     *
     * @return the first coordinate of specifically point
     */
    public double getZ() {
        return this.xyz.d3;
    }

    @Override
    public boolean equals(Object _obj) {
        if (this == _obj) return true;
        return _obj instanceof Point other && this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return "(" + xyz + ")";
    }
}
