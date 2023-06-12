package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


/**
 * this class represent a plane defined by a point in space and a vertical vector
 *
 * @author Yona and Aaron
 */
public class Plane extends Geometry {
    private final Point q0;
    private final Vector normal;

    /**
     * constructor for plane by point and vector
     *
     * @param _point  point
     * @param _vector vector of normal
     */
    @SuppressWarnings("unused")
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
     * @return normal of the plane
     */
    public Vector getNormal() {
        return this.normal;
    }

    @Override
    public Vector getNormal(Point _p) {
        return this.normal;
    }

    /**
     * getting the intersection's points between the ray with the plane
     *
     * @param _ray (Ray)
     * @return the intersection's points
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray _ray) {
        Point p0 = _ray.getPoint();
        Vector v = _ray.getDir();

        Vector u;
        try {
            u = q0.subtract(p0);
        } catch (IllegalArgumentException ignore) {
            return null;
        }

        double nv = normal.dotProduct(v);
        //ray parallel to plane or ray begins in the same point which appears as the plane's reference point
        if (isZero(nv)) return null;

        double t = alignZero(normal.dotProduct(u) / nv);
        return t <= 0 ? null : List.of(new GeoPoint(this, _ray.getPoint(t)));
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }

}
