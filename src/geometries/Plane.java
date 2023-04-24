package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * this class represent a plane defined by a point in space and a vertical vector
 *
 * @author Yona and Aaron
 */
public class Plane implements Geometry {
    final private Point q0;
    final private Vector normal;

    /**
     * constructor for plane by point and vector
     *
     * @param point  point
     * @param vector vector of normal
     */
    public Plane(Point point, Vector vector) {
        normal = vector.normalize();
        q0 = point;
    }

    /**
     * constructor for plane by 3 points
     *
     * @param point1 first point in the plane
     * @param point2 second point in the plane
     * @param point3 third point in the plane
     * @throws IllegalArgumentException when the points are on the same line
     */
    public Plane(Point point1, Point point2, Point point3) {
        q0 = point1;
        Vector v1 = point2.subtract(point1);
        Vector v2 = point3.subtract(point1);
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
    public Vector getNormal(Point p) {
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
