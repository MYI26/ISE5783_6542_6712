package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * this class represent a plane defined by a point in space and a vertical vector
 *
 * @author Yona and Aaron
 */
public class Plane implements Geometry {
    private final Point q0;
    private final Vector normal;

    /**
     * constructor for plane by point and vector
     *
     * @param _point  point
     * @param _vector vector of normal
     */
    public Plane(Point _point, Vector _vector) {
        normal = _vector.normalize();
        q0 = _point;
    }

    /**
     * constructor for plane by 3 points
     *
     * @param _point1 first point in the plane
     * @param _point2 second point in the plane
     * @param _point3 third point in the plane
     * @throws IllegalArgumentException when the points are on the same line
     */
    public Plane(Point _point1, Point _point2, Point _point3) {
        q0 = _point1;
        Vector v1 = _point2.subtract(_point1);
        Vector v2 = _point3.subtract(_point1);
        normal = v1.crossProduct(v2).normalize();
    }

    /**
     * getting point in the plane
     *
     * @return this point
     */
    public Point getQ0() {
        return this.q0;
    }

    /**
     * getting normal
     *
     * @return normal of plane
     */
    public Vector getNormal() {
        return this.normal;
    }

    @Override
    public Vector getNormal(Point _p) {
        return this.normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }
}
