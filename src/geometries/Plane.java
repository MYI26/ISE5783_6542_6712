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
public class Plane implements Geometry {
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

    /**
     * getting normal
     *
     * @param _p
     * @return normal of plane
     */
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
    public List<Point> findIntersections(Ray _ray) {
        // calculation of the scalar product between the normal plane vector and the ray direction vector
        double dotProduct = _ray.getDir().dotProduct(normal);
        //check if the ray is parallel to the plane
        if (isZero(dotProduct)) return null;

        Vector vec;
        //calculation of the vector between the point of the ray and the point of the plane
        try {
            vec = q0.subtract(_ray.getPoint());
        } catch (IllegalArgumentException ignore) {
            return null;
        }

        //calculation of the distance between the origin point of the ray and the intersection point
        double distance = vec.dotProduct(normal) / dotProduct;
        return alignZero(distance) <= 0 ? null : List.of(_ray.getPoint(distance)); //calculation of the intersection point
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }

}
